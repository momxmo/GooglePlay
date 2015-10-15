package mo.com.googleplay.manager;/**
 * Created by  on
 */

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mo.com.googleplay.bean.DetailInfo;
import mo.com.googleplay.conf.Constants;
import mo.com.googleplay.factory.ThreadPoolFactory;
import mo.com.googleplay.utils.CommonUtils;
import mo.com.googleplay.utils.FileUtils;
import mo.com.googleplay.utils.LogUtils;
import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/15:19:20
 * @描述 负责下载的相关功能
 * @描述 1.需要时刻记录当前的状态
 * @描述 2.根据appInfoData返回具体的DownLoadInfo(状态)
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class DownLoadManager {
    private static final java.lang.String TAG = "DownLoadManager";
    /**
     * 单例:
     * 1.一个类只有一个实例的情况
     * 2.一个类里面成为变量,只需要创建一次的情况
     */
    private static DownLoadManager instance;

    public static final int STATE_UNDOWNLOAD = 0;                                    // 未下载
    public static final int STATE_DOWNLOADING = 1;                                    // 下载中
    public static final int STATE_PAUSEDOWNLOAD = 2;                                    // 暂停下载
    public static final int STATE_WAITINGDOWNLOAD = 3;                                    // 等待中
    public static final int STATE_DOWNLOADFAILED = 4;                                    // 下载失败
    public static final int STATE_DOWNLOADED = 5;                                    // 下载完成
    public static final int STATE_INSTALLED = 6;                                    // 已安装

    /**
     * 保存用户点击了下载按钮之后创建的downLoadInfo(下载中,下载失败,暂停下载,下载失败 )
     */
    private Map<String, DownLoadInfo> mSaveDownLoadInfoMap = new HashMap<String, DownLoadInfo>();

    private DownLoadManager() {

    }

    public static DownLoadManager getInstance() {
        if (instance == null) {
            // 只有第一次初始化的时候才启用同步机制,提高效率
            synchronized (DownLoadManager.class) {
                if (instance == null) {
                    instance = new DownLoadManager();
                }
            }
        }
        return instance;
    }

    /**
     * @param downLoadInfo
     * @des 触发下载
     * @call 用户触发下载
     */
    public void downLoad(DownLoadInfo downLoadInfo) {
        // 保存创建的downLoadInfo
        mSaveDownLoadInfoMap.put(downLoadInfo.packageName, downLoadInfo);

        /*############### 当前状态:未下载 ###############*/
        downLoadInfo.curState = STATE_UNDOWNLOAD;
        // downLoadInfo改变了.需要发布消息.通知所有的观察者
        notifyObservers(downLoadInfo);
        /*#######################################*/


        /**
         * 预先把状态置为"等待状态"
         * 1.假如一个新的下载任务,可以放到"工作线程中",状态会被修改为"下载中"
         * 2.假如一个新的下载任务,不可以放到"工作线程中",一定为放到"任务队列",状态还是会保留为"等待状态"
         */

		/*############### 当前状态:等待下载 ###############*/
        downLoadInfo.curState = STATE_WAITINGDOWNLOAD;

        // downLoadInfo改变了.需要发布消息.通知所有的观察者
        notifyObservers(downLoadInfo);
        // downLoadInfo改变了.需要发布消息.通知所有的观察者
        /*#######################################*/

         /*使用现在线程池*/
        ThreadProxy threadPool = ThreadPoolFactory.getDownloadThreadPool();
        threadPool.execute(new DownloadTask(downLoadInfo));
    }


    /**
     * @des 根据具体的AppInfoBean,返回具体DownLoadInfo
     * @call 1.需要根据不同状态展示ui的时候调用
     */
    public DownLoadInfo getDownLoadInfo(DetailInfo mData) {
        DownLoadInfo downLoadInfo = new DownLoadInfo();
        downLoadInfo.downloadUrl = mData.downloadUrl;

        /*文件保存目录*/
        String dir = FileUtils.getDir("apk");//mnt/Andorid/data/包路径/apk
        String fileName = mData.packageName + ".apk";
        File saveFile = new File(dir, fileName);

        downLoadInfo.savePath = saveFile.getAbsolutePath();
        downLoadInfo.packageName = mData.packageName;
        downLoadInfo.max = new Long(mData.size);

        /*--------------- 赋予downLoadInfo不同的状态---------------*/
        // 已安装
        boolean installed = CommonUtils.isInstalled(UIUtils.getContext(), mData.packageName);
        if (installed) {
            downLoadInfo.curState = STATE_INSTALLED;
            return downLoadInfo;
        }

        //下载完成
        //1.文件已经存在
        //2.文件已经现在长度 == 应有长度
        if (saveFile.exists() && saveFile.length() == downLoadInfo.max) {
            downLoadInfo.curState = STATE_DOWNLOADED;
            return downLoadInfo;
        }
        /**
         * 下载中
         * 下载失败
         * 等待下载
         * 暂停下载
         */

        if (mSaveDownLoadInfoMap.containsKey(mData.packageName)) {
            return mSaveDownLoadInfoMap.get(mData.packageName);
        }

        //未下载
        downLoadInfo.curState = STATE_UNDOWNLOAD;
        return downLoadInfo;
    }

    /**
     * 下载任务
     */
    private class DownloadTask implements Runnable {
        DownLoadInfo mDownLoadInfo;

        public DownloadTask(DownLoadInfo downLoadInfo) {
            mDownLoadInfo = downLoadInfo;
        }

        @Override
        public void run() {

            /*############### 当前状态:下载中 ###############*/
            mDownLoadInfo.curState = STATE_DOWNLOADING;

            // downLoadInfo改变了.需要发布消息.通知所有的观察者
            notifyObservers(mDownLoadInfo);
             /*#######################################*/

            //  使用OkHttp框架
            OkHttpClient client = new OkHttpClient();
            //http://localhost:8080/GooglePlayServer/download?
            // name=
            long initRange = 0;
            File cacheFile = new File(mDownLoadInfo.savePath);
            if (cacheFile.exists()) {
                initRange = cacheFile.length();
            }
            // ②进度需要根据initRange进行初始化
            mDownLoadInfo.progress = initRange;

            /*http://localhost:8080/GooglePlayServer/
            download?name=app/com.itheima.www/com.itheima.www.apk&range=0*/

            //注意，这里有断点续传
            String dowmloadUrl = Constants.Req.DOWNLOAD_URL + mDownLoadInfo.downloadUrl + "&range=" + initRange;

            Request request = new Request.Builder()
                    .url(dowmloadUrl)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();

                boolean isPause = false;
                //访问网络成功
                if (response.isSuccessful()) {
                    //获取网络数据
                    InputStream inputStream = response.body().byteStream();

                    File savefile = new File(mDownLoadInfo.savePath);
                    LogUtils.i(TAG,"文件保存路径："+savefile.getAbsolutePath());
                    FileOutputStream fileOutputStream = new FileOutputStream(savefile,true);// ③以追加的形式写文件
                    
                    int len = -1;
                    byte[] bytes = new byte[1024];
                    /*写到文件中*/
                    while ((len = inputStream.read(bytes)) != -1) {
                        //判断当前状态
                        if (mDownLoadInfo.curState == STATE_PAUSEDOWNLOAD) {
                            isPause = true;
                            break;
                        }
                        mDownLoadInfo.progress += len;
                        fileOutputStream.write(bytes, 0, len);
                        /*############### 当前状态:下载中 ###############*/
                        mDownLoadInfo.curState = STATE_DOWNLOADING;
                        // downLoadInfo改变了.需要发布消息.通知所有的观察者
                        notifyObservers(mDownLoadInfo);
                        /*#######################################*/
                    }
                    if (isPause) {  //用户暂停下载，走到这里
                    } else {
                        // 下载完成
						/*############### 当前状态:下载完成 ###############*/
                        mDownLoadInfo.curState = STATE_DOWNLOADED;

                        // downLoadInfo改变了.需要发布消息.通知所有的观察者
                        notifyObservers(mDownLoadInfo);
						/*#######################################*/
                    }
                } else {
                    //网路访问失败
                     /*############### 当前状态:下载失败 ###############*/
                    mDownLoadInfo.curState = STATE_DOWNLOADFAILED;
                    // downLoadInfo改变了.需要发布消息.通知所有的观察者
                    notifyObservers(mDownLoadInfo);
                    /*#######################################*/
                }
            } catch (IOException e) {
                e.printStackTrace();
                 /*############### 当前状态:下载异常 ###############*/
                mDownLoadInfo.curState = STATE_DOWNLOADFAILED;
                // downLoadInfo改变了.需要发布消息.通知所有的观察者
                notifyObservers(mDownLoadInfo);
				/*#######################################*/
            }

        }
    }
    /*--------------- 自己实现观察者设计模式 ---------------*/

    public interface DownLoadInfoObserver {
        void onDownLoadInfoChange(DownLoadInfo downLoadInfo);
    }

    /**
     * 观察者(接口变量)集合
     */
    List<DownLoadInfoObserver> observers = new LinkedList<DownLoadInfoObserver>();

    /**
     * 添加观察者
     */
    public synchronized void addObserver(DownLoadInfoObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    /**
     * 移除观察者
     */
    public synchronized void deleteObserver(DownLoadInfoObserver o) {
        observers.remove(o);
    }

    /**
     * 通知所有的观察者数据已经改变
     */
    public void notifyObservers(DownLoadInfo downLoadInfo) {
        for (DownLoadInfoObserver o : observers) {
            o.onDownLoadInfoChange(downLoadInfo);
        }
    }

    /**
     * @param downLoadInfo
     * @des 暂停下载
     * @call 如果apk正在下载的时候, 用户点击了暂停下载
     */
    public void pauseDownLoad(DownLoadInfo downLoadInfo) {
		/*############### 当前状态:暂停下载###############*/
        downLoadInfo.curState = STATE_PAUSEDOWNLOAD;

        // downLoadInfo改变了.需要发布消息.通知所有的观察者
        notifyObservers(downLoadInfo);
		/*#######################################*/

    }


}

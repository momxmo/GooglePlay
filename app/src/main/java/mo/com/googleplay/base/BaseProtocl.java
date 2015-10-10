package mo.com.googleplay.base;/**
 * Created by  on
 */

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import mo.com.googleplay.conf.Constants;
import mo.com.googleplay.utils.IOUtils;
import mo.com.googleplay.utils.LogUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/8:18:28
 * @描述 网络加载数据的协议的基类(这里使用了缓存的方式)
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public abstract class BaseProtocl<T> {

    private static final java.lang.String TAG = "BaseProtocl";

    /**
     * 初始化和加载更多数据的时候   数据的获取
     *
     * @param index
     * @return
     */
    public T loadData(int index) throws Exception {
        /*获取缓存数据*/
        T t = getDataFromLocal(index);
        if (t != null) {

            return t;
        }
        LogUtils.i(TAG,"网络加载数据");

        //没有缓存数据，或者缓存数据已经过期，执行网络加载数据
        return getDataFromNet(index);
    }

    /**
     * 获取本地缓存数据
     *
     * @return
     */
    public T getDataFromLocal(int index) {
        /*判断缓存是否有效*/
        /*if(文件存在){
            //读取插入时间
			//判断是否过期
			if(未过期){
				//读取缓存内容
				//Json解析解析内容
				if(不为null){
					//返回并结束
				}
			}
		}*/
        // 判断缓存是否存在
        // 缓存的目录


        File cacheFile = getCacheFile(index);

        /*判断缓存文件是否存在*/
        if (cacheFile.exists()) {
            // 读取文件中的第一行信息：第一行信息表示数据插入的时间
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));

                // 解读第一行数据获取插入的时间进行有效时间的对比
                String insertTime = reader.readLine();


                if (System.currentTimeMillis() - Long.parseLong(insertTime) < Constants.PROTOCOLTIMEOUT) {
                    /*没有过期，读取缓存文件中的数据*/

                    String line = null;
                    StringBuffer sb = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    String jsonCacheData = sb.toString();

                    LogUtils.i(TAG, "读取到的缓存数据：" + jsonCacheData);

                    /*解析json数据并返回*/
                    T t = parseJson(jsonCacheData);
                    if (t != null) {
                        return t;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                //关闭资源
                IOUtils.close(reader);
            }
        }
        return null;
    }

        /**
         * 让子类去解析自己的数据
         * @param jsonCacheData
         * @return
         */

    protected abstract T parseJson(String jsonCacheData);

    /**
     * 通过index标记获缓存文件
     *
     * @param index
     * @return
     */
    private File getCacheFile(int index) {
        /**
         * 获取缓存的路径
         */
        String dir = Constants.GET_FILE_CACHE_DIR;
        String filename = getInterfaceKey() + "." + index;
        //缓存文件
        File cacheFile = new File(dir, filename);
        return cacheFile;
    }


    /**
     * 网络获取数据
     *
     * @return
     */
    public T getDataFromNet(int index) throws Exception {

        String url = Constants.Req.HOME_URL + getInterfaceKey();
        //  使用OkHttp框架
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "?index=" + index)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        /*-----------数据进行缓存------------------------*/

        if (jsonString != null) {
            File cacheFile = getCacheFile(index);

            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(cacheFile));
                //插入当前时间
                writer.write(System.currentTimeMillis() + "");
                writer.newLine();
                //插入数据
                writer.write(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(writer);
            }
        /*-----------解析数据------------------------*/
            T t = parseJson(jsonString);
            if (t!=null) {
                return t;
            }
        }

        return null;
    }

        /**
         * 获取接口的名称：比如Home 主页的连接地址  app 应用的连接地址
         * @return
         */

    public abstract String getInterfaceKey();
}

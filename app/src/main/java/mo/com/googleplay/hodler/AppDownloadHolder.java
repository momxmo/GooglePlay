package mo.com.googleplay.hodler;/**
 * Created by  on
 */

import android.view.View;

import java.io.File;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.bean.DetailInfo;
import mo.com.googleplay.manager.DownLoadInfo;
import mo.com.googleplay.manager.DownLoadManager;
import mo.com.googleplay.utils.CommonUtils;
import mo.com.googleplay.utils.PrintDownLoadInfo;
import mo.com.googleplay.utils.UIUtils;
import mo.com.googleplay.view.ProgressButton;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/15:17:57
 * @描述 TODO
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class AppDownloadHolder extends BaseHolder<DetailInfo> implements View.OnClickListener,DownLoadManager.DownLoadInfoObserver {

    private ProgressButton mPbDownload;
    private DetailInfo mData;

    @Override
    protected void refreshHolderView(DetailInfo data) {
        mData = data;
        /*--------------- 2.根据不同的状态(DownLoadInfo)给用户提示 ---------------*/
        // DownLoadInfo-->curState
        // 谁可以提供DownLoadInfo
        DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(mData);
        refreshProgressBtnUI(downLoadInfo);
    }

    @Override
    protected View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.app_download, null);
        mPbDownload = (ProgressButton) view.findViewById(R.id.pb_download);
        mPbDownload.setOnClickListener(this);
        return view;
    }

    /**
     * 点击下载App
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(mData);
        switch (downLoadInfo.curState) {
            /**
             状态(编程记录)  	| 用户行为(触发操作)
             ----------------| -----------------
             未下载			| 去下载
             下载中			| 暂停下载
             暂停下载			| 断点继续下载
             等待下载			| 取消下载
             下载失败 			| 重试下载
             下载完成 			| 安装应用
             已安装 			| 打开应用
             */
            case DownLoadManager.STATE_UNDOWNLOAD:// 未下载
                doDownLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_DOWNLOADING:// 下载中
                pauseDownLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_PAUSEDOWNLOAD:// 暂停下载
                doDownLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_WAITINGDOWNLOAD:// 等待下载
                cancleDownLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_DOWNLOADFAILED:// 下载失败
                doDownLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_DOWNLOADED:// 下载完成
                installApk(downLoadInfo);
                break;
            case DownLoadManager.STATE_INSTALLED:// 已安装
                openApk(downLoadInfo);
                break;

            default:
                break;
        }
    }

    /**
     * 开始下载
     *
     * @param downLoadInfo
     */
    private void doDownLoad(DownLoadInfo downLoadInfo) {
        DownLoadManager.getInstance().downLoad(downLoadInfo);
    }


    /**
     * 打开apk
     *
     * @param downLoadInfo
     */
    private void openApk(DownLoadInfo downLoadInfo) {
        CommonUtils.openApp(UIUtils.getContext(), downLoadInfo.packageName);
    }

    /**
     * 安装apk
     *
     * @param downLoadInfo
     */
    private void installApk(DownLoadInfo downLoadInfo) {
        File apkFile = new File(downLoadInfo.savePath);
        CommonUtils.installApp(UIUtils.getContext(), apkFile);
    }

    /**
     * 取消下载
     *
     * @param downLoadInfo
     */
    private void cancleDownLoad(DownLoadInfo downLoadInfo) {
        // TODO

    }

    /**
     * 暂停下载
     *
     * @param downLoadInfo
     */
    private void pauseDownLoad(DownLoadInfo downLoadInfo) {
        DownLoadManager.getInstance().pauseDownLoad(downLoadInfo);
    }

    /*--------------- 收到downLoadInfo改变的通知 ---------------*/
    @Override
    public void onDownLoadInfoChange(final DownLoadInfo downLoadInfo) {

        // 过滤
        if (!downLoadInfo.packageName.equals(mData.packageName)) {
            return;
        }
        PrintDownLoadInfo.printDownLoadInfo(downLoadInfo);
        UIUtils.postTaskSafely(new Runnable() {
            @Override
            public void run() {
                // 更新ui
                refreshProgressBtnUI(downLoadInfo);
            }
        });
    }

    /**
     * @param downLoadInfo
     * @des 更新UI
     * @des 刷新进度条按钮的UI
     * @dse 1.走到refreshHolderView方法的时候会调用一次
     * @des 2.收到downLoadInfo改变的时候
     */
    private void refreshProgressBtnUI(DownLoadInfo downLoadInfo) {
        mPbDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);
        switch (downLoadInfo.curState) {
            /**
             状态(编程记录)  	| 用户行为(触发操作)
             ----------------| -----------------
             未下载			| 去下载
             下载中			| 暂停下载
             暂停下载			| 断点继续下载
             等待下载			| 取消下载
             下载失败 			| 重试下载
             下载完成 			| 安装应用
             已安装 			| 打开应用
             */
            case DownLoadManager.STATE_UNDOWNLOAD:// 未下载
                mPbDownload.setText("下载");
                break;
            case DownLoadManager.STATE_DOWNLOADING:// 下载中
                mPbDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);
                mPbDownload.setmMax(downLoadInfo.max);
                mPbDownload.setProgress(downLoadInfo.progress);
                int progress = (int) (downLoadInfo.progress * 100.0f / downLoadInfo.max + .5f);
                mPbDownload.setText(progress+"%");
                break;
            case DownLoadManager.STATE_PAUSEDOWNLOAD:// 暂停下载
                mPbDownload.setText("继续下载");
                break;
            case DownLoadManager.STATE_WAITINGDOWNLOAD:// 等待下载
                mPbDownload.setText("等待中...");
                break;
            case DownLoadManager.STATE_DOWNLOADFAILED:// 下载失败
                mPbDownload.setText("重试");
                break;
            case DownLoadManager.STATE_DOWNLOADED:// 下载完成
                mPbDownload.setIsEnableProgress(false);
                mPbDownload.setText("安装");
                break;
            case DownLoadManager.STATE_INSTALLED:// 已安装
                mPbDownload.setText("打开");
                break;
            default:
                break;
        }

    }

    /**
     * 添加观察者，（收到别人发布downInfo）
     * 自己手动的发布最新状态
     */
    public void addObserverAndNotify() {
        DownLoadManager.getInstance().addObserver(this);

        DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(mData);

        DownLoadManager.getInstance().notifyObservers(downLoadInfo);
    }
}

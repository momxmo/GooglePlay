package mo.com.googleplay.utils;/**
 * Created by  on
 */

import android.content.Context;
import android.content.res.Resources;
import android.os.*;

import mo.com.googleplay.base.BaseApplication;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/6:19:10
 * @描述 UI工具类
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class UIUtils {
    /**
     * 获取上下文
     */
    public static Context getContext() {
        return BaseApplication.getContext();
    }

    /**
     * 获取资源对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取资源的color资源
     */
    public static int getColor(int resID) {
        return getResources().getColor(resID);
    }

    /**
     * 获取资源string.xml文件中的字符
     */
    public static String getString(int resID) {
        return getResources().getString(resID);
    }

    /**
     * 获取资源文件string.xml中的字符数组
     */
    public static String[] getStringArray(int resID) {
        return getResources().getStringArray(resID);
    }

    /**
     * 获取程序的包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 获取程序的主线程ID
     */
    public static long getMainThreadID() {
        return BaseApplication.getMainThreadID();
    }

    /**
     * 获取主线程中的handler对象
     */
    public static Handler getMainHandler() {
        return BaseApplication.getHandler();
    }

    /**
     * 安全的执行一个task任务
     *
     * @param task
     */
    public static void postTaskSafely(Runnable task) {
        // 获取当前线程的id
        int currentThreadID = android.os.Process.myTid();

        if (currentThreadID == getMainThreadID()) {
            // 如果当前线程是主线程，执行该任务
            task.run();
        } else {
            //当前不是主线程
            getMainHandler().post(task);
        }
    }
}


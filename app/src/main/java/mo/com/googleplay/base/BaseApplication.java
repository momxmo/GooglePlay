package mo.com.googleplay.base;

import android.app.Application;
import android.content.Context;
import android.os.*;

/**
 * Created by MoMxMo on 2015/10/6.
 */
public class BaseApplication extends Application {
    private static Handler mHandler;
    private static Context mContext;
    private static long mMainThreadID;

    /**
     * 获取上下文
     */
    public static  Context getContext() {
       return mContext;
    }
    public static  long getMainThreadID() {
        return mMainThreadID;
    }
    public static Handler getHandler() {
        return mHandler;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        //获取上下文
        mContext = getApplicationContext();
        //在主线程中创建handler
        mHandler = new Handler();
        //获取主线程ID
        mMainThreadID = android.os.Process.myTid();
    }
}

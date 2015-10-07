package mo.com.googleplay.factory;/**
 * Created by  on
 */

import mo.com.googleplay.manager.ThreadProxy;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/7:12:24
 * @描述 线程池工厂类（这里提供了----普通线程池和下载线程池）
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class ThreadPoolFactory {
    private static ThreadProxy mNormalThreadPool;  //普通线程池
    private static ThreadProxy mDownloadThreadPool; //下载线程池

    /**
     * 获取现在线程池
     * @return
     */
    public static ThreadProxy getDownloadThreadPool() {

        //使用单例模式进行优化处理，避免多次创建线程池(使用线程同步)
        if (mDownloadThreadPool == null) {
            synchronized (ThreadPoolFactory.class) {
                //创建线程池，设置池子的大小为5，最大线程数为5，线程保存时间为3秒
                mDownloadThreadPool = new ThreadProxy(5,5,3000);
            }
        }
        return mDownloadThreadPool;
    }

    /**
     * 获取普通线程池（单利模式创建使用）
     * @return
     */
    public static ThreadProxy getNormalThreadPool() {
        if (mNormalThreadPool==null) {
            synchronized (ThreadPoolFactory.class) {
                /*
                创建线程池的大小为3个，最大线程数量为3个，线程保持的时间为3000毫秒
                 */
                mNormalThreadPool = new ThreadProxy(3, 3, 3000);
            }
        }
        return mNormalThreadPool;
    }
}

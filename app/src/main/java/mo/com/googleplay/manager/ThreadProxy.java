package mo.com.googleplay.manager;/**
 * Created by  on
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/7:10:37
 * @描述 线程池的代理类
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class ThreadProxy {

    private ThreadPoolExecutor poolExecutor;
    private int corePoolSize;       //线程池数量
    private int maximumPoolSize;    //线程最大数量
    private long keepAliveTime;    //线程存活时间

    public ThreadProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
        initThreadPool();
    }

    /**
     * 初始化线程池子
     */
    private void initThreadPool() {
        TimeUnit unit = TimeUnit.MILLISECONDS;  //时间单位
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();      //阻塞 队列，这里我们使用的是无界队列
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler =new ThreadPoolExecutor.DiscardPolicy();    //添加任务出错的的异常处理

        /**
         * 使用单利模式（懒汉式，有线程安全问题，需要同步）
         *
         * 线程池为空，或者关闭，或者已经结束时，重新创建线程池
         */
        if (poolExecutor == null || poolExecutor.isShutdown() || poolExecutor.isTerminated()) {
            synchronized (ThreadProxy.class) {
                if (poolExecutor == null || poolExecutor.isShutdown() || poolExecutor.isTerminated()) {
                    poolExecutor = new ThreadPoolExecutor(
                            corePoolSize,   //线程池大小
                            maximumPoolSize,    //线程池最大数
                            keepAliveTime,      //线程保持存活时间
                            unit,           //时间单位
                            workQueue,      //任务队列
                            threadFactory,  //线程池工厂
                            handler         //消息处理
                    );
                }
            }
        }
    }

    /*----------------提交任务----------------------*/
    public void submit(Runnable task) {
        poolExecutor.submit(task);
    }

    /*----------------执行任务----------------------*/
    public void execute(Runnable task) {
        poolExecutor.execute(task);
    }

    /*-----------------移除任务---------------------*/
    public void remove(Runnable task) {
        poolExecutor.remove(task);
    }
}

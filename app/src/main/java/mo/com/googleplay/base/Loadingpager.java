package mo.com.googleplay.base;/**
 * Created by  on
 */

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import mo.com.googleplay.R;
import mo.com.googleplay.factory.ThreadPoolFactory;
import mo.com.googleplay.utils.LogUtils;
import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/6:20:29
 * @描述 TODO
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public abstract class Loadingpager extends FrameLayout {

    private static final int STATE_NONE = -1;       //默认的状态
    private static final int STATE_LOADING = 0;     //加载状态
    private static final int STATE_ERROR = 1;          //错误状态
    private static final int STATE_EMPTY = 2;           //空页面状态
    private static final int STATE_SUCESS = 3;          //加载成功状态
    private static final java.lang.String TAG = "Loadingpager";

    private int mCurrentState = STATE_NONE;         //当前显示默认状态

    private View mLoadView;
    private View mEmptyView;
    private View mErrorView;
    private View mSuccessView;

    public Loadingpager(Context context) {
        super(context);
        initView();

        // 数据加载的流程

    }

    /**
     * 加载填充布局
     */
    public void initView() {
        /**
         任何应用其实就只有4种页面类型
         ① 加载页面
         ② 错误页面
         ③ 空页面
         ④ 成功页面
         ①②③三种页面一个应用基本是固定的
         每一个fragment/activity对应的页面④就不一样
         进入应用的时候显示①,②③④需要加载数据之后才知道显示哪个
         */


        //加载页面
        mLoadView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
        this.addView(mLoadView);

        //空页面
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
        this.addView(mEmptyView);

        //错误页面
        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
        /*设置重新加载点击事件*/
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*点击加载触发加载数据*/
                triggerLoadData();
            }
        });
        this.addView(mErrorView);


        /*刷新UI通过状态*/
        refreshUIByState();
    }


    private void refreshUIByState() {

        /**
         *   ①②③三种页面一个应用基本是固定的
         每一个fragment/activity对应的页面④就不一样
         进入应用的时候显示①,②③④需要加载数据之后才知道显示哪个
         */


        /*默认状态时，显示加载*/
        mLoadView.setVisibility(mCurrentState == STATE_LOADING || mCurrentState == STATE_NONE ? VISIBLE : GONE);

        //显示空页面
        mEmptyView.setVisibility(mCurrentState == STATE_EMPTY ? VISIBLE : GONE);

        //显示错误页面
        mErrorView.setVisibility(mCurrentState == STATE_ERROR ? VISIBLE : GONE);

        //成功页面
        if (mCurrentState == STATE_SUCESS && mSuccessView == null) {
            mSuccessView = initSuccessView();
            this.addView(mSuccessView);
        }

        if (mSuccessView != null) {
            //显示成功页面
            mSuccessView.setVisibility(mCurrentState == STATE_SUCESS ? VISIBLE : GONE);
        }

    }

    public abstract View initSuccessView();

    /**
     * 触发加载数据
     */
    public void triggerLoadData() {
        /**
         ① 触发加载  	进入页面开始加载/点击某一个按钮的时候加载
         ② 异步加载数据  -->显示加载视图
         ③ 处理加载结果
         ① 成功-->显示成功视图
         ② 失败
         ① 数据为空-->显示空视图
         ② 数据加载失败-->显示加载失败的视图
         */
        /**
         * @des 触发加载
         * @call 如果外部需要加载数据的时候, 调用该方法加载数据
         */

        /**
         * 如果当前状态不是成功状态，并且不是正在加载状态，可以触发加载事件
         */
        if (mCurrentState != STATE_SUCESS && mCurrentState != STATE_LOADING) {  //没有成功 && 不加载
            // 第一次 loading-->empty
            // 第二次 empty-->应有状态
            // 重置当前状态为加载中的状态

            /*改变状态*/
            mCurrentState = STATE_LOADING;

            //刷新UI
            refreshUIByState();
            LogUtils.i(TAG, "加载数据");

            //使用线程加载数据
//            new Thread(new LoadDataTask()).start();
            /*这里使用线程池的方式去完成加载数据，避免了上面多次创建线程浪费资源的现象（线程创建+销毁的时间>线程执行的时间）*/
            ThreadPoolFactory.getNormalThreadPool().execute(new LoadDataTask());

        }
    }

    private class LoadDataTask implements Runnable {
        @Override
        public void run() {
            /*返回一个临时状态*/
            LoadedResult result = initData();

            mCurrentState = result.getState();
            /*通知刷新*/
            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    refreshUIByState();
                }
            });

        }
    }

    /**
     * 让子类去实现
     * @return
     */
    protected abstract LoadedResult initData();


    /**
     * 使用枚举对加载数据成功
     */
    public enum LoadedResult {
        SUCCESS(STATE_SUCESS), EMPTY(STATE_EMPTY), ERROR(STATE_ERROR);

        public int getState() {
            return mState;
        }

        private  int mState;
        private LoadedResult(int state) {
            mState = state;
        }
    }

}

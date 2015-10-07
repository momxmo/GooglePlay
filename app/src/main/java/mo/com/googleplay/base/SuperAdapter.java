package mo.com.googleplay.base;/**
 * Created by  on
 */

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import mo.com.googleplay.factory.ThreadPoolFactory;
import mo.com.googleplay.hodler.LoadMoreHolder;
import mo.com.googleplay.utils.LogUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/7:19:47
 * @描述 Adapter的基类
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public abstract class SuperAdapter<T> extends BaseAdapter {

    private static final int VIEWTYPE_LOADMORE = 0; //加载更多的viewtype标识
    private static final int VIEWTYPE_NORMAL = 1;   //普通item的viewtype标识
    private static final java.lang.String TAG = "SuperAdapter";
    private List<T> resData;
    private Object loadMoreHolder;
    private LoadMoreHolder mLoadMoreHolder;
    private LoadMoreTask mLoadMoreTask;

    public SuperAdapter(List<T> resData) {
        this.resData = resData;
    }

    @Override
    public int getCount() {
        if (resData != null) {
            return resData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (resData != null) {
            return resData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * listview中可以显示不同种类的ViewType
     * <p/>
     * 获得view item的样式总数（这里我们只有两种样式：一种的加载更多，另一种的普通类型）
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        //1(jiazai9更多数据)+1（普通的类型）
        return super.getViewTypeCount() + 1;
    }

    /**
     * 获取每一个item 条目的 ViewType (类型不一样，复用的缓存也是不一样的)
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //工具不同的具体情况返回不同的item的具体类型
        if (position == getCount() - 1) {
            return VIEWTYPE_LOADMORE;
        } else {
            return VIEWTYPE_NORMAL;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;
        if (convertView == null) {
    /*---------------视图---------------*/
            //根据当前位置判断ViewType类型
            if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
                //当前是加载更多viewtype类型的时候

                // 返回一个加载更多的holdre
                holder = (BaseHolder<T>) getLoadMoreHolder();

            } else {
                //返回一个BaseHolder的子类
                holder = getSpecialHolder();
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }

        /*---------------数据的绑定---------------*/
        //根据当前位置判断ViewType类型
        if (getItemViewType(position) == VIEWTYPE_LOADMORE) {

            //触发加载更多的数据
            if (hasLoadMore()) {    //有
                //  网络加载数据
                triggerLoadMoreData();
            } else {//没有
                //控制loadMore视图的显示----没有加载更多
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.STATE_NONE);
            }

        } else {
            holder.setDataAndRefreshHolderView(resData.get(position));
        }


        //返回Hodler中的View
        return holder.mHolderView;
    }

    /**
     * @des 触发加载更多数据
     */
    private void triggerLoadMoreData() {

        //让mLoadMore显示在正在加载状态
        int state = LoadMoreHolder.STATE_LOADING;
        mLoadMoreHolder.setDataAndRefreshHolderView(state);

        //网络加载数据
        if (mLoadMoreTask == null) {    //用于标记，如果当前正在刷新加载,则不进行重复网络加载数据（优化）
            mLoadMoreTask = new LoadMoreTask();
            LogUtils.i(TAG,"正在加载更多数据");
            ThreadPoolFactory.getNormalThreadPool().execute(mLoadMoreTask);
        }


    }

    /**
     * 网络加载更多数据的任务
     */
    class LoadMoreTask implements Runnable {

        //设置指定每页加载数据
        public static final int PAGESIZE = 20;

        @Override
        public void run() {

            int state;  //加载之后的结果
            List<T> loadmoreList = null;    //加载更多之后得到的网络数据

            //TODO  今天先到这里吧

        }
    }

    /**
     * @return
     * @des 决定加载更多数据(默认有更多加载)
     * @des 子类可以根据选择覆写这个方法，
     */
    protected boolean hasLoadMore() {
        return true;
    }

    /**
     * @return
     * @dec 让子类去实现具体的BaseHodler，并且返回BaseHolder
     */
    protected abstract BaseHolder getSpecialHolder();


    /**
     * 返回一个加载很多的holder(不同类型的converview的复用不会影响彼此)
     *
     * @return
     */
    public Object getLoadMoreHolder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }
}

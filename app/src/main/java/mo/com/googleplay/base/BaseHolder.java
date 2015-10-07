package mo.com.googleplay.base;/**
 * Created by  on
 */

import android.view.View;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/7:21:02
 * @描述 Holder的基类
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public abstract class BaseHolder<T> {
    //设置一个视图View
    public  View mHolderView;
    public  T mData;


    /**
     * @des 1.创建holder
     */
    public BaseHolder() {
        //2.获取根视图，让子类去实现
        mHolderView = initHolderView();
        
        //3.设置标记
        mHolderView.setTag(this);
    }

    /**
     * @des 设置数据和刷新视图
     * @param data
     */
    public void setDataAndRefreshHolderView(T data) {
        mData = data;
        
        //刷新视图
        refreshHolderView(data);
    }

    /**
     * @des 视图展示
     * @call 外部需要设置数据和展示数据时候调用
     * @param data
     */
    protected abstract void refreshHolderView(T data);

    /**
     * @des 初始化一个视图，使用抽象方法的方式，让子类去实现
     * @return
     */
    protected abstract View initHolderView();
}

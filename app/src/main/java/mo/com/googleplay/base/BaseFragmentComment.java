package mo.com.googleplay.base;/**
 * Created by  on
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/6:20:16
 * @描述 Fragment常见的基类
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public abstract class BaseFragmentComment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    /**
     * 初始化加载View,子类必须初始化View返回对象
     * @return
     */
    protected abstract View initView() ;
    /**
     *加载数据，子类可以选择性 是否加载数据
     */
    public  void initData(){

    }

    /**
     * 初始化监听事件
     */
    public  void initListener(){

    }
    /**
     * 初始化
     */
    public  void init(){

    }
}

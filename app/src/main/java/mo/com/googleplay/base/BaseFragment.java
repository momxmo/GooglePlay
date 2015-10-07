package mo.com.googleplay.base;/**
 * Created by  on
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.List;
import java.util.Map;

import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/6:20:25
 * @描述 Google Fragmen的业务基类
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public abstract class BaseFragment extends Fragment {

    private Loadingpager mLoadingPager;
//    private OnInitSucess mListener;

    public Loadingpager getLoadingPager() {
        return mLoadingPager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mLoadingPager == null) {
            //第一次加载
            mLoadingPager = new Loadingpager(UIUtils.getContext()) {
                @Override
                public View initSuccessView() {
                    //设置抽象方法：让子类设置View
                    return BaseFragment.this.initSuccessView();
                }

                @Override
                protected LoadedResult initData() {
                    //设置抽象方法，让子类去加载数据
                    return BaseFragment.this.initData();
                }
            };
        } else {
            //第二次初始化Fragment
            ViewParent parent = mLoadingPager.getParent();
            if (parent!=null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(mLoadingPager);
            }
        }

        return mLoadingPager;
    }


    /**
     * 初始化数据
     * @return
     */
    protected abstract Loadingpager.LoadedResult initData();

    /**
     * 初始化布局
     * @return
     */
    public abstract View initSuccessView();

    /**
     * 检查状态
     */
    protected Loadingpager.LoadedResult checkState(Object resResult) {
        if (resResult == null) {
            return Loadingpager.LoadedResult.EMPTY;
        }
        if (resResult instanceof List) {
            if (((List) resResult).size()==0) {
                return Loadingpager.LoadedResult.EMPTY;
            }
        }

        if (resResult instanceof Map) {
            if (((Map) resResult).size() == 0) {
                return Loadingpager.LoadedResult.EMPTY;
            }
        }

        //都通过验证，返回成功
        return Loadingpager.LoadedResult.SUCCESS;
    }
}

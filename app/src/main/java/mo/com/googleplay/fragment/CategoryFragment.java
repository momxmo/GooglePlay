package mo.com.googleplay.fragment;/**
 * Created by  on
 */

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

import mo.com.googleplay.base.BaseFragment;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.base.Loadingpager;
import mo.com.googleplay.base.SuperAdapter;
import mo.com.googleplay.bean.CategoryInfoBean;
import mo.com.googleplay.factory.ListViewFactory;
import mo.com.googleplay.hodler.CategoryHolder;
import mo.com.googleplay.hodler.CategoryTitleHolder;
import mo.com.googleplay.protocol.CategoryProtocol;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/6:19:45
 * @描述
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class CategoryFragment extends BaseFragment {
    private static final String TAG = "CategoryFragment";
    private List<CategoryInfoBean> mData;
    private CategoryProtocol mProtocol;

    /**
     * 加载数据
     *
     * @return
     */
    @Override
    protected Loadingpager.LoadedResult initData() {

        mProtocol = new CategoryProtocol();
        try {
            List<CategoryInfoBean> listbean = mProtocol.loadData(0);
            Loadingpager.LoadedResult state = checkState(listbean);
            if (state != Loadingpager.LoadedResult.SUCCESS) {
                return state;
            }
            mData = listbean;
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            //访问数据出错
            return Loadingpager.LoadedResult.ERROR;
        }

    }

    /**
     * 初始化View
     *
     * @return
     */

    @Override
    public View initSuccessView() {
        ListView mListView = ListViewFactory.create();

        mListView.setAdapter(new CategoryAdapter(mListView, mData));
        return mListView;
    }

    private class CategoryAdapter extends SuperAdapter {
        public CategoryAdapter(AbsListView adsListView, List<CategoryInfoBean> resData) {
            super(adsListView, resData);
        }

        @Override
        protected BaseHolder getSpecialHolder(int position) {
            if (mData.get(position).isTitle) {
                return new CategoryTitleHolder();
            } else {
                return new CategoryHolder();
            }
        }

        /**
         * 覆写SuperBaseAdapter中定义的"得到ViewType总数的方法"
         * 让ListView中实际的ViewType和返回的ViewType总数一致
         */
        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;// 2+1=3
        }

        @Override
        protected int getNormalViewType(int position) {
            if (mData.get(position).isTitle) {
                //如果是标题，显示
                return 2;
            } else {
                return 1;
            }
        }
    }
}

package mo.com.googleplay.fragment;/**
 * Created by  on
 */

import android.graphics.Color;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseFragment;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.base.Loadingpager;
import mo.com.googleplay.base.SuperAdapter;
import mo.com.googleplay.bean.HomeBean;
import mo.com.googleplay.hodler.AppHolder;
import mo.com.googleplay.protocol.AppProtocol;
import mo.com.googleplay.utils.UIUtils;

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
public class AppFragment extends BaseFragment {

    private AppAdapter mAppAdapter;
    private AppProtocol mAppProtocol;
    private List<HomeBean.AppInfo> mAppListData;

    /**
     * 加载数据
     *
     * @return
     */
    @Override
    protected Loadingpager.LoadedResult initData() {
        mAppProtocol = new AppProtocol();
        //加载数据
        try {
            List<HomeBean.AppInfo> mAppInfos = mAppProtocol.loadData(0);


            //校验数据的状态
            Loadingpager.LoadedResult state = checkState(mAppInfos);

            //有问题
            if (state != Loadingpager.LoadedResult.SUCCESS) {
                return state;
            }

            mAppListData = mAppInfos;

            if (mAppAdapter != null) {
                mAppAdapter.notifyDataSetChanged();
            }
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            //加载数据失败
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
        ListView mListView = new ListView(UIUtils.getContext());
        mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.setFadingEdgeLength(0);
        mListView.setFastScrollEnabled(true);

        mAppAdapter = new AppAdapter(mListView, mAppListData);
        mListView.setAdapter(mAppAdapter);

        return mListView;
    }

    private class AppAdapter extends SuperAdapter {
        public AppAdapter(AbsListView adsListView, List resData) {
            super(adsListView, resData);
        }

        @Override
        protected BaseHolder getSpecialHolder() {
            return new AppHolder();
        }

        /**
         * 加载更多数据
         *
         * @return
         * @throws Exception
         */
        @Override
        protected List<HomeBean.AppInfo> onLoadMore() throws Exception {
            /*网络加载更多数据*/
            SystemClock.sleep(2000);
            return performLoadMore();
        }

        //点击listView item条目时的事件
        @Override
        public void onNormalItemClick(AdapterView parent, View view, int position, long id) {

            Snackbar.make(view, "您点击了：" + position, Snackbar.LENGTH_LONG)
                    .setAction("Ok", null)
                    .setActionTextColor(UIUtils.getColor(R.color.colorAccent))
                    .show(); // Don’t forget to show!
        }

        /**
         * 网络操作加载更多数据
         *
         * @return
         */
        private List<HomeBean.AppInfo> performLoadMore() throws Exception {
            //解析数据
            List<HomeBean.AppInfo> appInfos = mAppProtocol.loadData(mAppListData.size());

            if (appInfos != null) {
                return appInfos;
            } else {
                return null;
            }

        }
    }
}

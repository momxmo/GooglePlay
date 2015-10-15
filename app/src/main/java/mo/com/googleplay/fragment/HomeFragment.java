package mo.com.googleplay.fragment;/**
 * Created by  on
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import mo.com.googleplay.R;
import mo.com.googleplay.activity.DetailsActivity;
import mo.com.googleplay.base.BaseFragment;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.base.Loadingpager;
import mo.com.googleplay.base.SuperAdapter;
import mo.com.googleplay.bean.HomeBean;
import mo.com.googleplay.factory.ListViewFactory;
import mo.com.googleplay.hodler.HomeHeadPicHolder;
import mo.com.googleplay.hodler.HomeHolder;
import mo.com.googleplay.protocol.HomeProtocol;
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
public class HomeFragment extends BaseFragment {

    private static final java.lang.String TAG = "HomeFragment";
    private List<HomeBean.AppInfo> mListAppInfo;
    private List<String> mListPic;
    private HomeAdapter mHomeAdapter;
    private HomeProtocol mProtocol;
    public static final String APP_INFO = "aapInfo";

    /**
     * 初始化加载数据
     *
     * @return
     */
    @Override
    protected Loadingpager.LoadedResult initData() {
        mProtocol = new HomeProtocol();
        HomeBean homeBean = null;
        try {
            //在主页协议中加载数据（包含缓存数据的处理）
            homeBean = mProtocol.loadData(0);

            //校验数据的状态
            Loadingpager.LoadedResult state = checkState(homeBean);

            //有问题
            if (state != Loadingpager.LoadedResult.SUCCESS) {
                return state;
            }

            //检验AppInfo中的状态
            state = checkState(homeBean.list);
            if (state != Loadingpager.LoadedResult.SUCCESS) {
                return state;
            }

            //数据没有问题
            mListAppInfo = homeBean.list;
            mListPic = homeBean.picture;

            if (mHomeAdapter != null) {
                mHomeAdapter.notifyDataSetChanged();
            }

            return state;
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常，显示加载错误状态
            return Loadingpager.LoadedResult.ERROR;
        }


       /* try {
        *//*请求的URL地址
        * http://localhost:8080/GooglePlayServer/home?index=0
        * *//*
            String url = Constants.Req.HOME_URL + "home";

            *//*使用OkHttp框架*//*
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url + "?index=0")
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
//                String jsonString = response.body().string();
            }
            String jsonString = response.body().string();

            //解析数据
            Gson gson = new Gson();
            HomeBean homeBean = gson.fromJson(jsonString, HomeBean.class);

            //校验数据的状态
            Loadingpager.LoadedResult state = checkState(homeBean);

            //有问题
            if (state != Loadingpager.LoadedResult.SUCCESS) {
                return state;
            }

            //检验AppInfo中的状态
            state = checkState(homeBean.list);
            if (state != Loadingpager.LoadedResult.SUCCESS) {
                return state;
            }

            //数据没有问题
            mListAppInfo = homeBean.list;
            mListPic = homeBean.picture;

            if (mHomeAdapter != null) {
                mHomeAdapter.notifyDataSetChanged();
            }

            return state;
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常，显示加载错误状态
            return Loadingpager.LoadedResult.ERROR;
        }*/
    }


    /**
     * 初始化View
     *
     * @return
     */

    @Override
    public View initSuccessView() {
        //没有数据
        ListView mListView = ListViewFactory.create();

        //添加头部的轮播图
        HomeHeadPicHolder headPicHolder = new HomeHeadPicHolder();
        mListView.addHeaderView(headPicHolder.mHolderView);
        headPicHolder.setDataAndRefreshHolderView(mListPic);


        mHomeAdapter = new HomeAdapter(mListView, mListAppInfo);
        //设置adapter
        mListView.setAdapter(mHomeAdapter);


        return mListView;
    }

    private class HomeAdapter extends SuperAdapter {
        public HomeAdapter(AbsListView adsListView, List resData) {
            super(adsListView, resData);
        }

        @Override
        protected BaseHolder getSpecialHolder(int positin) {
            return new HomeHolder();
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

            Intent intent = new Intent(UIUtils.getContext(), DetailsActivity.class);
            HomeBean.AppInfo appInfo = mListAppInfo.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable(APP_INFO,  appInfo);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        /**
         * 网络操作加载更多数据
         *
         * @return
         */
        private List<HomeBean.AppInfo> performLoadMore() throws Exception {
             /*请求的URL地址
        * http://localhost:8080/GooglePlayServer/home?index=0
        * */
 /*           String url = Constants.Req.HOME_URL + "home";
            *//*使用OkHttp框架*//*
            OkHttpClient client = new OkHttpClient();

            LogUtils.i(TAG, "size:" + mListAppInfo.size());
            Request request = new Request.Builder()
                    .url(url + "?index=" + mListAppInfo.size())
                    .build();
            Response response = client.newCall(request).execute();
            String jsonString = response.body().string();*/

            //解析数据
            HomeBean homeBean = mProtocol.loadData(mListAppInfo.size());

            if (homeBean != null) {
                return homeBean.list;
            } else {
                return null;
            }

        }

    }
}
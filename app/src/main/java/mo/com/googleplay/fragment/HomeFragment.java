package mo.com.googleplay.fragment;/**
 * Created by  on
 */

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;

import mo.com.googleplay.base.BaseFragment;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.base.Loadingpager;
import mo.com.googleplay.base.SuperAdapter;
import mo.com.googleplay.bean.HomeBean;
import mo.com.googleplay.conf.Constants;
import mo.com.googleplay.hodler.HomeHolder;
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

    private List<String> mListData;
    private List<HomeBean.AppInfo> mListAppInfo;
    private List<String> mListPic;
    private HomeAdapter mHomeAdapter;

    /**
     * 初始化加载数据
     *
     * @return
     */
    @Override
    protected Loadingpager.LoadedResult initData() {
        try {
        /*请求的URL地址
        * http://localhost:8080/GooglePlayServer/home?index=0
        * */
            String url = Constants.Req.HOME_URL + "home";

            /*使用OkHttp框架*/
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
        }
    }


    /**
     * 初始化View
     *
     * @return
     */

    @Override
    public View initSuccessView() {
        //没有数据
        ListView mListView = new ListView(UIUtils.getContext());

        mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.setFadingEdgeLength(0);
        mListView.setFastScrollEnabled(true);

        mHomeAdapter = new HomeAdapter(mListAppInfo);
        //设置adapter
        mListView.setAdapter(mHomeAdapter);

        return mListView;
    }

    private class HomeAdapter extends SuperAdapter {
        public HomeAdapter(List resData) {
            super(resData);
        }

        @Override
        protected BaseHolder getSpecialHolder() {
            return new HomeHolder();
        }
    }

}

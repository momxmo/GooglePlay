package mo.com.googleplay.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseActivity;
import mo.com.googleplay.base.Loadingpager;
import mo.com.googleplay.bean.DetailInfo;
import mo.com.googleplay.bean.HomeBean;
import mo.com.googleplay.fragment.HomeFragment;
import mo.com.googleplay.hodler.AppDetaiInfoHolder;
import mo.com.googleplay.hodler.AppDetaiPicHolder;
import mo.com.googleplay.hodler.AppDetaiSafeHolder;
import mo.com.googleplay.hodler.AppDetailHolder;
import mo.com.googleplay.hodler.AppDownloadHolder;
import mo.com.googleplay.manager.DownLoadManager;
import mo.com.googleplay.protocol.DetailProtocol;
import mo.com.googleplay.utils.UIUtils;

/**
 * A login screen that offers login via email/password.
 */
public class DetailsActivity extends BaseActivity {


    private static final java.lang.String TAG = "DetailsActivity";
    private FrameLayout mApp_info;
    private FrameLayout mApp_safe;
    private FrameLayout mApp_pic;
    private FrameLayout mApp_detai;
    private FrameLayout mApp_download;
    private HomeBean.AppInfo mAppInfo;
    private DetailProtocol mProtocol;
    private DetailInfo mData;
    private AppDetaiInfoHolder mAppDetaiInfoHolder;
    private Loadingpager mLoadingPager;
    private AppDownloadHolder appDownloadHolder;
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mAppInfo = (HomeBean.AppInfo) getIntent().getSerializableExtra(HomeFragment.APP_INFO);

        init();
        initView();
        initData();
        initEvent();
    }*/

    @Override
    protected void init() {
        mAppInfo = (HomeBean.AppInfo) getIntent().getSerializableExtra(HomeFragment.APP_INFO);
        super.init();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
                /*--------头部的Holder-------*/
        mAppDetaiInfoHolder = new AppDetaiInfoHolder();
        mApp_info.addView(mAppDetaiInfoHolder.mHolderView);
    }

    /**
     * 触发加载数据
     */
    @Override
    protected void initData() {
        mLoadingPager.triggerLoadData();
    }

    /**
     * 初始化View
     */
    protected void initView() {
        mLoadingPager = new Loadingpager(this) {
            @Override
            public View initSuccessView() {
                return DetailsActivity.this.initSuccessView();
            }

            @Override
            protected LoadedResult initData() {
                return DetailsActivity.this.loadData();
            }
        };
        setContentView(mLoadingPager);

    }

    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.activity_details, null);
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.id_toolbar);
        mToolbar.setTitleTextColor(UIUtils.getColor(R.color.window_background));
        mToolbar.setTitle("Google");
        setSupportActionBar(mToolbar);

        mApp_info = (FrameLayout) view.findViewById(R.id.info_app_head);
        mApp_safe = (FrameLayout) view.findViewById(R.id.safe_app);
        mApp_pic = (FrameLayout) view.findViewById(R.id.pic_app);
        mApp_detai = (FrameLayout) view.findViewById(R.id.detai_app);
        mApp_download = (FrameLayout) view.findViewById(R.id.download_app);

        /*1.应用信息部分*/
        AppDetaiInfoHolder appDetaiInfoHolder = new AppDetaiInfoHolder();
        mApp_info.addView(appDetaiInfoHolder.mHolderView);
        appDetaiInfoHolder.setDataAndRefreshHolderView(mData);

        /*2.应用安全部分*/
        AppDetaiSafeHolder appDetaiSafeHolder = new AppDetaiSafeHolder();
        mApp_safe.addView(appDetaiSafeHolder.mHolderView);
        appDetaiSafeHolder.setDataAndRefreshHolderView(mData);

        /*3.应用截图部分*/
        AppDetaiPicHolder appDetaiPicHolder = new AppDetaiPicHolder();
        mApp_pic.addView(appDetaiPicHolder.mHolderView);
        appDetaiPicHolder.setDataAndRefreshHolderView(mData);

        /*4.应用描述部分*/
        AppDetailHolder appDetailHolder = new AppDetailHolder();
        mApp_detai.addView(appDetailHolder.mHolderView);
        appDetailHolder.setDataAndRefreshHolderView(mData);


        /*5.应用下载部分*/
        appDownloadHolder = new AppDownloadHolder();
        mApp_download.addView(appDownloadHolder.mHolderView);
        appDownloadHolder.setDataAndRefreshHolderView(mData);

        //添加观察者到观察者集合中
        DownLoadManager.getInstance().addObserver(appDownloadHolder);
        return view;

    }

    /**
     * 加载数据
     *
     * @return
     */
    public Loadingpager.LoadedResult loadData() {
        mProtocol = new DetailProtocol(mAppInfo.packageName);
        try {
            mData = mProtocol.loadData(0);
            if (mData != null) {
                return Loadingpager.LoadedResult.SUCCESS;
            }
            return Loadingpager.LoadedResult.EMPTY;
        } catch (Exception e) {
            e.printStackTrace();
            return Loadingpager.LoadedResult.ERROR;
        }
    }

    @Override
    protected void onPause() {
        /**移除监听*/
        if (appDownloadHolder != null) {
            DownLoadManager.getInstance().deleteObserver(appDownloadHolder);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**添加监听
         */
        if (appDownloadHolder != null) {
            // 1.添加观察者(收到别人发布downLoadinfo)
            // 2.自己手动的发布最新状态}
            appDownloadHolder.addObserverAndNotify();
        }
    }

}


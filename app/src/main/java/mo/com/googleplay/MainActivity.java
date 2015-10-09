package mo.com.googleplay;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;

import mo.com.googleplay.base.BaseFragment;
import mo.com.googleplay.base.Loadingpager;
import mo.com.googleplay.factory.FragmentFactory;
import mo.com.googleplay.utils.UIUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout mTabs;
    private ViewPager viewPager;
    private String[] mTitleList;
    private MainOnPageChangeListener mListener;
    private TabFragmentAdapter mFragmentAdapter;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initToolBar();
        initData();
        initListener();
    }

    private void initToolBar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mToolbar.setTitleTextColor(UIUtils.getColor(R.color.window_background));
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * 设置监听事件的初始化
     */
    private void initListener() {
        mListener = new MainOnPageChangeListener();
        //设置Tab选择监听事件
        mTabs.setOnTabSelectedListener(mListener);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabs.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                BaseFragment fragment = FragmentFactory.createFragment(0);
                    Loadingpager loadingPager = fragment.getLoadingPager();
                    //触发加载数据
                    loadingPager.triggerLoadData();
            }
        });
    }
    private void initData() {
        //获取当行显示的标题
        mTitleList = UIUtils.getStringArray(R.array.main_titles);
        for (int i = 0; i < mTitleList.length; i++) {
            //设置tab的标题
            mTabs.addTab(mTabs.newTab().setText(mTitleList[i]));
        }
        //创建适配器
        mFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), mTitleList);
        viewPager.setAdapter(mFragmentAdapter);//给ViewPager设置适配器
        mTabs.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        mTabs.setTabsFromPagerAdapter(mFragmentAdapter);//给Tabs设置适配器
    }

    private void initView() {
        mTabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mDrawer = (DrawerLayout) findViewById(R.id.main_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.main_navigationview);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public class TabFragmentAdapter extends FragmentStatePagerAdapter {

        private String[] mTitles;

        public TabFragmentAdapter(FragmentManager fm, String[] arr) {
            super(fm);
            mTitles = arr;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = FragmentFactory.createFragment(position);
            if (fragment != null) {
                return fragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            if (mTitles != null) {
                return mTitles.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }


    /**
     * 监听TabLayout的滑动
     */
    private class MainOnPageChangeListener implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();
            viewPager.setCurrentItem(position);
            BaseFragment fragment = FragmentFactory.createFragment(position);
            if (fragment != null) {
                 Loadingpager loadingPager = fragment.getLoadingPager();
                //触发加载数据
                loadingPager.triggerLoadData();
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }
}
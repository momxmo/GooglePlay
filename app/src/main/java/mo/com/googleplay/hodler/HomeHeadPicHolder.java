package mo.com.googleplay.hodler;/**
 * Created by  on
 */

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.conf.Constants;
import mo.com.googleplay.utils.ImageHelper;
import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/9:10:36
 * @描述 home 图片的轮播的Holder
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class HomeHeadPicHolder extends BaseHolder<List<String>> {

    private static final java.lang.String TAG = "HomeHeadPicHolder";
    private ViewPager mViewPager;
    private LinearLayout mContainer;
    private HomeHeadPicAdapter mhomeHeadPicAdapter;
    private List<String> mPicData;
    private int mCurrentPosition;

    @Override
    protected void refreshHolderView(List<String> data) {
        mPicData = data;
        mhomeHeadPicAdapter = new HomeHeadPicAdapter();
        mViewPager.setAdapter(mhomeHeadPicAdapter);
        /*通知数据改变*/
        mhomeHeadPicAdapter.notifyDataSetChanged();

        //添加轮播原点
        for (int i = 0; i < data.size(); i++) {
            View view = new View(UIUtils.getContext());
            view.setBackgroundResource(R.drawable.indicator_normal);

            int width = UIUtils.dp2px(10);
            int height = UIUtils.dp2px(10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);

            params.leftMargin = UIUtils.dp2px(10);
            params.bottomMargin = UIUtils.dp2px(10);
            mContainer.addView(view, params);

            if (i == 0) {
                /*默认选中第一个*/
                view.setBackgroundResource(R.drawable.indicator_selected);
            }
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                position = position % mPicData.size();
                /*设置无限轮播*/
                for (int i = 0; i < mPicData.size(); i++) {
                    View view = mContainer.getChildAt(i);
                    if (i == position) {
                        view.setBackgroundResource(R.drawable.indicator_selected);
                    } else {
                        view.setBackgroundResource(R.drawable.indicator_normal);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

//        进入的时候选中中间的position
        //设置无限轮播
        mCurrentPosition = Integer.MAX_VALUE / 2;
        int dif = mCurrentPosition % mPicData.size();
        mCurrentPosition = mCurrentPosition - dif;
        mViewPager.setCurrentItem(mCurrentPosition);

        //设置自动轮播
        final AutoScrollTask task = new AutoScrollTask();
        task.run();

        //设置触摸点击事件的监听
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        task.stop();
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        task.start();
                    default:
                        break;
                }
                return false;
            }
        });

    }

    /**
     * 自动轮播图的任务
     */
    private class AutoScrollTask implements Runnable {
        /**
         * 开始轮播
         */
        public void start() {
            UIUtils.postTaskSafely(this, 2000);
        }

        /**
         * 停止轮播
         */
        public void stop() {
            //将任务在主线程中移除
            UIUtils.removeTask(this);

        }

        @Override
        public void run() {
            //主线程中执行
            int curIndex = mViewPager.getCurrentItem();
            curIndex++;
            mViewPager.setCurrentItem(curIndex);

            start();
        }
    }

    @Override
    protected View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.home_head_pic, null);
        mContainer = (LinearLayout) view.findViewById(R.id.ll_point_container);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_head_pic);
        return view;
    }

    private class HomeHeadPicAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mPicData != null) {
//                return mPicData.size();
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(UIUtils.getContext());
            position = position % mPicData.size();

            String picUrl = Constants.Req.HOME_IMAGE_URL + mPicData.get(position);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageHelper.displayImage(imageView, picUrl);

            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

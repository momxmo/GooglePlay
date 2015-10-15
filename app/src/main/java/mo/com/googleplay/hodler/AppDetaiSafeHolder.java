package mo.com.googleplay.hodler;/**
 * Created by  on
 */

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.bean.DetailInfo;
import mo.com.googleplay.conf.Constants;
import mo.com.googleplay.utils.ImageHelper;
import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/12:13:45
 * @描述 TODO
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class AppDetaiSafeHolder extends BaseHolder<DetailInfo> {

    private LinearLayout mSafePicContainer;
    private ImageView mIv_show;
    private LinearLayout mSafeContent;
    private boolean isShow = true;

    @Override
    protected void refreshHolderView(DetailInfo data) {

        /*加载显示安全的图片*/
        List<DetailInfo.SafeInfo> safe = data.safe;
        for (DetailInfo.SafeInfo bean : safe) {

            /*添加安全的绿色标志*/
            ImageView mIVSafePic = new ImageView(UIUtils.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dp2px(48), UIUtils.dp2px(24));
            ImageHelper.displayImage(mIVSafePic, Constants.Req.HOME_IMAGE_URL + bean.safeUrl);
            mSafePicContainer.addView(mIVSafePic, params);

            /*添加安全的具体内容*/

            LinearLayout ll = new LinearLayout(UIUtils.getContext());
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ImageView mIvhead = new ImageView(UIUtils.getContext());
            LinearLayout.LayoutParams params_ = new LinearLayout.LayoutParams(UIUtils.dp2px(24), UIUtils.dp2px(24));
            ImageHelper.displayImage(mIvhead, Constants.Req.HOME_IMAGE_URL + bean.safeDesUrl);

            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(bean.safeDes);
            ll.addView(mIvhead, params_);
            ll.addView(tv);
            mSafeContent.addView(ll);

        }


    }

    @Override
    protected View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.app_detail_safe, null);
        mSafePicContainer = (LinearLayout) view.findViewById(R.id.ll_pic_container);
        mIv_show = (ImageView) view.findViewById(R.id.iv_safe_show);
        mSafeContent = (LinearLayout) view.findViewById(R.id.ll_safe_content);


        /**
         * 点击显示更多事件的监听
         */
        mIv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*设置点击展开和折叠事件*/
                toggle(true);
                isShow = !isShow;
            }
        });
        return view;
    }

    /**
     * 是否执行动画
     * @param isAnmation
     */
    public void toggle(boolean isAnmation) {
        if (isShow) {//展开--->折叠
            mSafeContent.measure(0, 0);
            int measuredHeight = mSafeContent.getMeasuredHeight();

            int start = measuredHeight;
            int end = 0;
            if (isAnmation) {
                doAnimation(start, end);
            } else {
                /*直接修改高度*/
                ViewGroup.LayoutParams layoutParams = mSafeContent.getLayoutParams();
                layoutParams.height = end;
                mSafeContent.setLayoutParams(layoutParams);
            }
        } else { //折叠   ---》 展开
            mSafeContent.measure(0, 0);
            int measuredHeight = mSafeContent.getMeasuredHeight();
            int start = 0;
            int end = measuredHeight;
            if (isAnmation) {
                doAnimation(start, end);
            } else {
                /*直接修改高度*/
                ViewGroup.LayoutParams layoutParams = mSafeContent.getLayoutParams();
                layoutParams.height = end;
                mSafeContent.setLayoutParams(layoutParams);
            }

        }
    }

    /**
     * 执行动画
     * @param start
     * @param end
     */
    public void doAnimation(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        /*设置执行时间*/
        animator.setDuration(300);
        animator.start();

        /*动画的监听*/
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator value) {
                /*得到渐变值*/
                int values = (Integer) value.getAnimatedValue();

                /*修改高度*/
                ViewGroup.LayoutParams params = mSafeContent.getLayoutParams();
                params.height = values;
                /*设置layoutParams*/
                mSafeContent.setLayoutParams(params);
            }
        });

        //箭头方向改变
        if (isShow) {
            ObjectAnimator.ofFloat(mIv_show, "rotation", 0, 180).start();
        } else {
            ObjectAnimator.ofFloat(mIv_show, "rotation", 180, 0).start();
        }

    }

}

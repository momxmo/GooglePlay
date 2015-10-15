package mo.com.googleplay.hodler;/**
 * Created by  on
 */

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.bean.DetailInfo;
import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/14:21:34
 * @描述 TODO
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class AppDetailHolder extends BaseHolder<DetailInfo> implements View.OnClickListener {

    private ImageView mIVShow;
    private TextView mTvAuthor;
    private TextView mTvDes;
    private boolean isOpen = true;
    private int mTvDesMeasuredHeight;
    private DetailInfo mData;

    @Override
    protected void refreshHolderView(DetailInfo data) {
        mData = data;
        mTvDes.setText(data.des);
        mTvAuthor.setText("作者：" + data.author);
    }

    @Override
    protected View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_detail, null);
        mTvDes = (TextView) view.findViewById(R.id.tv_des);
        mTvAuthor = (TextView) view.findViewById(R.id.tv_author);
        mIVShow = (ImageView) view.findViewById(R.id.iv_safe_show);

        mTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTvDesMeasuredHeight = mTvDes.getMeasuredHeight();
                mTvDes.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                //默认折叠
                toggle(false);
            }
        });

        /*设置z展开和折叠的点击事件*/
        view.setOnClickListener(this);
        return view;
    }

    /**
     * 展开和折叠点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        toggle(true);

    }

    private void toggle(boolean isAnmation) {
        if (isOpen) { //折开--->折叠
            mTvDes.measure(0, 0);
            /*获取高度,需要获取到测量之后的高度*/
            int start = mTvDesMeasuredHeight;
            int end = getShortHeight(mData.des, 7);

            if (isAnmation) {
                doAnimation(start, end);
            } else {
                /*直接修改高度*/
              mTvDes.setHeight(end);
            }
        } else {    //折叠------------>打开

            mTvDes.measure(0, 0);
            /*获取高度*/
            int measuredHeight = mTvAuthor.getMeasuredHeight();

            /*获取文本7行的高度值*/
            int start = getShortHeight(mData.des,7);
            int end = mTvDesMeasuredHeight;
            if (isAnmation) {
                doAnimation(start, end);
            } else {
               mTvDes.setHeight(end);
            }
        }
        isOpen = !isOpen;
    }

    /**
     * 得到一个TextView具体的内容的高度
     *
     * @param content
     * @param line
     */
    public int getShortHeight(String content, int line) {
        TextView tempTextView = new TextView(UIUtils.getContext());
        tempTextView.setText(content);
        tempTextView.setLines(line);
        tempTextView.measure(0, 0);
        int measuredHeight = tempTextView.getMeasuredHeight();

        return measuredHeight;
    }

    /**
     * 带有动画的折叠
     *
     * @param start
     * @param end
     */
    private void doAnimation(int start, final int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(300);
        animator.start();

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int values = (Integer) animation.getAnimatedValue();
                //修改高度
                mTvDes.setHeight(values);
            }
        });

        /*箭头的方向改变*/
        if (isOpen) {
            ObjectAnimator.ofFloat(mIVShow, "rotation", 0, 180).start();
        } else {
            ObjectAnimator.ofFloat(mIVShow, "rotation", 180, 0).start();
        }

    }
}

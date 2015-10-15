package mo.com.googleplay.view;/**
 * Created by  on
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import mo.com.googleplay.R;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/10:20:32
 * @描述 测量Fragment
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class RatioLayout extends FrameLayout {
    private float mPicRatio = 0.0f; //宽高比例    mPicRatin = 宽/高
    private int mRelative = RELATIVE_WIDTH;
    public static final int RELATIVE_HEIGHT = 1;
    public static final int RELATIVE_WIDTH = 0;

    public RatioLayout(Context context) {
        super(context);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray tr = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mPicRatio = tr.getFloat(R.styleable.RatioLayout_picratio, 0.0f);
        mRelative = tr.getInt(R.styleable.RatioLayout_relative, RELATIVE_WIDTH);
        //一定要回收资源
        tr.recycle();

    }

    public float getmPicRatio() {
        return mPicRatio;
    }

    public void setmPicRatio(float mPicRatio) {
        this.mPicRatio = mPicRatio;
    }

    public float getmRelative() {
        return mRelative;
    }

    public void setmRelative(int mRelative) {
        this.mRelative = mRelative;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //模式+ size
        /**
         MeasureSpec.EXACTLY;//match_parent 100dp
         MeasureSpec.UNSPECIFIED;//wrap_content
         MeasureSpec.AT_MOST;
         */

        int parentWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int parentHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        //如果宽度是精确的，通过宽度计算高度--->使用高度和宽度的比例值 （同时存在高度和宽度精确的情况下，默认使用宽度计算高度）

        if (parentWidthMode == MeasureSpec.EXACTLY && mRelative == RELATIVE_WIDTH) {
            /*获取宽度*/
            int parentLayoutWidth = MeasureSpec.getSize(widthMeasureSpec);
//            通过比例值来计算宽度  mPicRatin = 宽/高
            int parentLayoutHeight = (int) (parentLayoutWidth / mPicRatio + 0.5f);

            /*获取child的布局大小*/
            int childWidth = parentLayoutWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = parentLayoutHeight - getPaddingTop() - getPaddingBottom();

            int childMeasureSpecHeight = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            int childMeasureSpecWidth = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);

            //让孩子测量自己
            measureChildren(childMeasureSpecWidth, childMeasureSpecHeight);

            /*设置RatioLayout的高度和宽度*/
            setMeasuredDimension(parentLayoutWidth, parentLayoutHeight);


        } else if (parentHeightMode == MeasureSpec.EXACTLY && mRelative == RELATIVE_HEIGHT) {// 知道高度-->动态计算宽度
//            如果高度是精确的，通过高度计算宽度----------》
            int parentHeightSize = MeasureSpec.getSize(heightMeasureSpec);
//            通过比例值來计算 mPicRatin = 宽 / 高
            int parentWidthSize = (int) (parentHeightSize * mPicRatio + 0.5f);

            /*子布局大小*/
            int childWidth = parentHeightSize - getPaddingLeft() - getPaddingRight();
            int childHeight = parentWidthSize - getPaddingTop() - getPaddingBottom();
            int childMeasureSpecHeight = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            int childMeasureSpecWidth = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);

            measureChildren(childMeasureSpecHeight, childMeasureSpecWidth);

            /*设置RatioLayout的高度和宽度*/
            setMeasuredDimension(parentWidthSize, parentHeightSize);

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }


    }
}

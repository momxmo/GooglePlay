package mo.com.progressbutton_demo;/**
 * Created by  on
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/15:18:05
 * @描述 自定义带有进度显示的Button
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class ProgressButton extends Button {
    private long progress;  //进度
    private long mMax = 100;  //进度最大值,默认100
    private boolean isEnableProgress = true; //是否可以更新进度

    public ProgressButton(Context context) {
        super(context);
    }
    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 绘制本身
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (isEnableProgress) {
            // canvas.drawText("内容", getLeft(), getTop(), getPaint());
            Drawable drawable = new ColorDrawable(Color.BLUE);
            int left = 0;
            int top = 0;
            //实时进度
            int right = (int) (progress * 1.0f / mMax * getMeasuredWidth() + 0.5f);
            int bottom = getBottom();
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
        super.onDraw(canvas);
    }

    /**
     * 是否可以更新进度
     * @param isEnableProgress
     */
    public void setIsEnableProgress(boolean isEnableProgress) {
        this.isEnableProgress = isEnableProgress;
    }

    /**
     * 设置进度
     * @param progress
     */
    public void setProgress(long progress) {
        this.progress = progress;
        //重新绘制
        invalidate();
    }
}



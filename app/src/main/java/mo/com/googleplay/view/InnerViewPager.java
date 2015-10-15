package mo.com.googleplay.view;/**
 * Created by  on
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/11:8:37
 * @描述 内部viewpager触摸事件拦截
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class InnerViewPager extends ViewPager{

    private float mDownX;
    private float mDownY;
    public InnerViewPager(Context context) {
        super(context);
    }
    public InnerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                /*申请父容器不要拦截*/
                float mMoveX = ev.getX();
                float mMoveY = ev.getY();

                int difX = (int) Math.abs(mMoveX - mDownX);
                int difY = (int) Math.abs(mMoveY - mDownY);

                if (difX > difY) {
                    //当控件水平滑动的时候，请求父容器不要拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(ev);
    }
}

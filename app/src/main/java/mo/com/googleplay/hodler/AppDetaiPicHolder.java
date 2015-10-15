package mo.com.googleplay.hodler;/**
 * Created by  on
 */

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.bean.DetailInfo;
import mo.com.googleplay.conf.Constants;
import mo.com.googleplay.utils.ImageHelper;
import mo.com.googleplay.utils.UIUtils;
import mo.com.googleplay.view.RatioLayout;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/14:20:59
 * @描述 TODO
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class AppDetaiPicHolder extends BaseHolder<DetailInfo> {

    private LinearLayout mPicContainer;

    @Override
    protected void refreshHolderView(DetailInfo data) {
        List<String> screen = data.screen;

        //加载图片数据
        for (int i = 0; i < screen.size(); i++) {
            String url = screen.get(i);

            RatioLayout ratioLayout = new RatioLayout(UIUtils.getContext());

            /*图片的高度比*/
            ratioLayout.setmPicRatio((float) 150 / 250);
            /*相对誰计算*/
            ratioLayout.setmRelative(RatioLayout.RELATIVE_WIDTH);

            ImageView iv = new ImageView(UIUtils.getContext());
            ImageHelper.displayImage(iv, Constants.Req.HOME_IMAGE_URL + url);

            /*获取屏幕的宽度*/
            int screenWidth = UIUtils.getResources().getDisplayMetrics().widthPixels;
            screenWidth = screenWidth - UIUtils.dp2px(5) - UIUtils.dp2px(5) - UIUtils.dp2px(5);

            /*分三张图片显示*/
            int width = screenWidth / 3;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;//动态计算高度

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            if (i != 0) {
                params.leftMargin = UIUtils.dp2px(5);
            }

            /*图片加入到容器中*/
            ratioLayout.addView(iv);

            mPicContainer.addView(ratioLayout,params);
        }

    }

    @Override
    protected View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.vp_detai_pic, null);
        mPicContainer = (LinearLayout) view.findViewById(R.id.ll_pic_container);
        return view;
    }
}

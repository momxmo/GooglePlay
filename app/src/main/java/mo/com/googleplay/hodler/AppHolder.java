package mo.com.googleplay.hodler;/**
 * Created by  on
 */

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.bean.HomeBean;
import mo.com.googleplay.conf.Constants;
import mo.com.googleplay.utils.ImageHelper;
import mo.com.googleplay.utils.StringUtils;
import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/7:21:23
 * @描述 App holde 具体对象
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class AppHolder extends BaseHolder<HomeBean.AppInfo>{

    private ImageView mAppIcon;     //App图标
    private ImageView mAppDownload;     //下载图标
    private RatingBar mAppStars;        //App星级别
    private TextView mAppTvDes;     //描述
    private TextView mAppTvName;    //App名称
    private TextView mAppTvSize;    //App大小

    /**
     * @des 初始化一个View的对象
     * @return
     */
    @Override
    protected View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_home_info, null);
        mAppIcon = (ImageView) view.findViewById(R.id.item_app_icon);
        mAppDownload = (ImageView) view.findViewById(R.id.iv_app_download);
        mAppStars = (RatingBar) view.findViewById(R.id.rb_app_stars);
        mAppTvDes = (TextView) view.findViewById(R.id.tv_app_des);
        mAppTvName = (TextView) view.findViewById(R.id.tv_app_name);
        mAppTvSize = (TextView) view.findViewById(R.id.tv_app_size);

        return view;
    }
    @Override
    protected void refreshHolderView(HomeBean.AppInfo data) {
       mAppTvDes.setText(data.des);
        mAppTvName.setText(data.name);
        mAppTvSize.setText(StringUtils.formatFileSize(data.size));
        mAppStars.setRating(data.stars);
        mAppIcon.setImageResource(R.drawable.ic_launcher);
        ImageHelper.displayImage(mAppIcon, Constants.Req.HOME_IMAGE_URL+ data.iconUrl);
    }
}

package mo.com.googleplay.hodler;/**
 * Created by  on
 */

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.bean.DetailInfo;
import mo.com.googleplay.conf.Constants;
import mo.com.googleplay.utils.ImageHelper;
import mo.com.googleplay.utils.LogUtils;
import mo.com.googleplay.utils.StringUtils;
import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/12:13:43
 * @描述 TODO
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class AppDetaiInfoHolder extends BaseHolder<DetailInfo> {

    private static final java.lang.String TAG = "AppDetaiInfoHolder";
    private TextView mApp_size;
    private TextView mApp_pulish_time;
    private TextView mApp_version;
    private TextView mApp_download_sum;
    private TextView mApp_name;
    private RatingBar mApp_stars;
    private ImageView mApp_icon;
    private DetailInfo mData;

    @Override
    protected void refreshHolderView(DetailInfo data) {
        mData = data;
        LogUtils.i(TAG, Constants.Req.HOME_IMAGE_URL + data.iconUrl);
        mApp_download_sum.setText("下载:" + data.downloadNum);
        mApp_size.setText("大小:"+ StringUtils.formatFileSize(new Integer(data.size)));
        mApp_pulish_time.setText("时间:"+data.date);
        mApp_version.setText("版本:"+data.version);
        mApp_name.setText(data.name);
        mApp_stars.setRating(data.stars);

        ImageHelper.displayImage(mApp_icon, Constants.Req.HOME_IMAGE_URL + data.iconUrl);

    }

    @Override
    protected View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.app_detail_header, null);
        mApp_name = (TextView) view.findViewById(R.id.tv_app_name);
        mApp_download_sum = (TextView) view.findViewById(R.id.tv_download_sum);
        mApp_version = (TextView) view.findViewById(R.id.tv_app_version);
        mApp_pulish_time = (TextView) view.findViewById(R.id.tv_pulish_time);
        mApp_size = (TextView) view.findViewById(R.id.tv_app_size);
        mApp_icon = (ImageView) view.findViewById(R.id.iv_app_icon);
        mApp_stars = (RatingBar) view.findViewById(R.id.rb_app_stars);
        return view;
    }
}

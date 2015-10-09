package mo.com.googleplay.hodler;/**
 * Created by  on
 */

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.bean.SubjectBean;
import mo.com.googleplay.conf.Constants;
import mo.com.googleplay.utils.ImageHelper;
import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/9:13:23
 * @描述 专题holder对象
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class SubjectHolder extends BaseHolder<SubjectBean>{

    private ImageView mIcon;
    private TextView mTvTitle;

    @Override
    protected void refreshHolderView(SubjectBean data) {
        mTvTitle.setText(data.des);
        String IV_url = Constants.Req.HOME_IMAGE_URL + data.url;
        ImageHelper.displayImage(mIcon,IV_url);
    }

    @Override
    protected View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
        mIcon = (ImageView) view.findViewById(R.id.item_iv_icon);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }
}

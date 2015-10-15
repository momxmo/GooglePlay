package mo.com.googleplay.hodler;/**
 * Created by  on
 */

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.bean.CategoryInfoBean;
import mo.com.googleplay.conf.Constants;
import mo.com.googleplay.utils.ImageHelper;
import mo.com.googleplay.utils.StringUtils;
import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/11:20:21
 * @描述 分类的Holder
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class CategoryHolder extends BaseHolder<CategoryInfoBean> {

    private static final java.lang.String TAG = "CategoryHolder";
    private TextView mName1;
    private TextView mName2;
    private TextView mName3;
    private ImageView mIcon1;
    private ImageView mIcon2;
    private ImageView mIcon3;

    @Override
    protected void refreshHolderView(CategoryInfoBean data) {
        setData(data.name1, data.url1, mName1, mIcon1);
        setData(data.name2, data.url2, mName2, mIcon2);
        setData(data.name3, data.url3, mName3, mIcon3);

    }

    protected void setData(final String name, String url, TextView mName, ImageView mIcon) {
        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(url)) {
            mName.setText(name);
            ImageHelper.displayImage(mIcon, Constants.Req.HOME_IMAGE_URL + url);

            ViewGroup view = (ViewGroup) mName.getParent();
            view.setVisibility(View.VISIBLE);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), name, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ViewGroup view = (ViewGroup) mName.getParent();
            view.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    protected View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_category, null);
        mName1 = (TextView) view.findViewById(R.id.item_category_name_1);
        mName2 = (TextView) view.findViewById(R.id.item_category_name_2);
        mName3 = (TextView) view.findViewById(R.id.item_category_name_3);

        mIcon1 = (ImageView) view.findViewById(R.id.item_category_icon_1);
        mIcon2 = (ImageView) view.findViewById(R.id.item_category_icon_2);
        mIcon3 = (ImageView) view.findViewById(R.id.item_category_icon_3);
        return view;
    }
}
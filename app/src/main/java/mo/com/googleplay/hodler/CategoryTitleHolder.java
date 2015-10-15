package mo.com.googleplay.hodler;/**
 * Created by  on
 */

import android.view.View;
import android.widget.TextView;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.bean.CategoryInfoBean;
import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/12:11:23
 * @描述 TODO
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class CategoryTitleHolder extends BaseHolder<CategoryInfoBean> {

    private TextView mTvTitle;

    @Override
    protected void refreshHolderView(CategoryInfoBean data) {
        mTvTitle.setText(data.title);
    }
    @Override
    protected View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.category_title, null);
         mTvTitle = (TextView) view.findViewById(R.id.tv_category_title);
/*        mTvTitle = new TextView(UIUtils.getContext());
        mTvTitle.setBackgroundColor(Color.TRANSPARENT);
        mTvTitle.setTextSize(UIUtils.dp2px(8));
        int padding = UIUtils.dp2px(18);
        mTvTitle.setPadding(padding, padding, padding, padding);*/
        return view;
    }
}

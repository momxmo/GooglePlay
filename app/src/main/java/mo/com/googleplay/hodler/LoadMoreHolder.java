package mo.com.googleplay.hodler;/**
 * Created by  on
 */

import android.view.View;
import android.widget.RelativeLayout;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/7:23:42
 * @描述 TODO
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class LoadMoreHolder extends BaseHolder<Integer> {

    public static final int	STATE_LOADING	= 0;	// 显示loading视图
    public static final int	STATE_RETRY		= 1;	// 显示retry视图
    public static final int	STATE_NONE		= 2;	// 什么也不显示(没有加载更多)
    private RelativeLayout mRetry;
    private RelativeLayout mLoading;

    @Override
    protected void refreshHolderView(Integer data) {
        //先全部隐藏
        mRetry.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);

        if (data == STATE_LOADING) {
            //显示加载
            mLoading.setVisibility(View.VISIBLE);
        } else if (data == STATE_RETRY) {
            //显示retry视图
            mRetry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.loadmore, null);
        mRetry = (RelativeLayout) view.findViewById(R.id.retry_loading);
        mLoading = (RelativeLayout) view.findViewById(R.id.loading);

        return view;
    }
}

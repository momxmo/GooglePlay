package mo.com.googleplay.fragment;/**
 * Created by  on
 */

import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

import mo.com.googleplay.base.BaseFragment;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.base.Loadingpager;
import mo.com.googleplay.base.SuperAdapter;
import mo.com.googleplay.bean.GameInfoBean;
import mo.com.googleplay.hodler.GameHolder;
import mo.com.googleplay.protocol.GameProtocol;
import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/6:19:45
 * @描述
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class GameFragment extends BaseFragment {
    private List<GameInfoBean> mGameListDate;
    private GameAdapter mAdapter;
    private GameProtocol mGameProtocol;

    /**
     * 加载数据
     *
     * @return
     */
    @Override
    protected Loadingpager.LoadedResult initData() {
        /*使用协议加载数据*/
        mGameProtocol = new GameProtocol();
        try {
            List<GameInfoBean> beans = mGameProtocol.loadData(0);
            Loadingpager.LoadedResult state = checkState(beans);

            //有问题
            if (state != Loadingpager.LoadedResult.SUCCESS) {
                return state;
            }
            mGameListDate = beans;

            //通知适配器加载数据完成
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            //加载数据失败
            return Loadingpager.LoadedResult.ERROR;
        }
    }

    /**
     * 初始化View
     *
     * @return
     */

    @Override
    public View initSuccessView() {
        ListView mListView = new ListView(UIUtils.getContext());

        //设置滑动的背景颜色是透明的
        mListView.setCacheColorHint(Color.TRANSPARENT);
        //设置滑动的显示滑动
        mListView.setFastScrollEnabled(true);
        mListView.setFadingEdgeLength(0);

        mAdapter = new GameAdapter(mListView, mGameListDate);
        mListView.setAdapter(mAdapter);
        return mListView;
    }

    private class GameAdapter extends SuperAdapter {

        public GameAdapter(AbsListView adsListView, List resData) {
            super(adsListView, resData);
        }

        @Override
        protected BaseHolder getSpecialHolder(int positin) {
            return new GameHolder();
        }

        /**
         * 加载更多数据
         *
         * @return
         * @throws Exception
         */
        @Override
        protected List onLoadMore() throws Exception {
            List<GameInfoBean> beans = mGameProtocol.loadData(0);

            if (beans != null) {
                return beans;
            } else {
                return null;
            }
        }
    }
}
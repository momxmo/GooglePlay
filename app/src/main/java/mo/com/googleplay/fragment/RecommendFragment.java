package mo.com.googleplay.fragment;/**
 * Created by  on
 */

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

import mo.com.googleplay.base.BaseFragment;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.base.Loadingpager;
import mo.com.googleplay.base.SuperAdapter;
import mo.com.googleplay.factory.ListViewFactory;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/6:19:45
 * @描述
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class RecommendFragment  extends BaseFragment {
    private List mListData;

    /**
     * 加载数据
     * @return
     */
    @Override
    protected Loadingpager.LoadedResult initData() {

        /*随机返回结果*/
        Random random = new Random();
        int i = random.nextInt(3);
        Loadingpager.LoadedResult[] results = {Loadingpager.LoadedResult.EMPTY,
                Loadingpager.LoadedResult.ERROR,
                Loadingpager.LoadedResult.SUCCESS,
        };

        return results[i];
    }

    /**
     * 初始化View
     * @return
     */

    @Override
    public View initSuccessView() {
        ListView mListView = ListViewFactory.create();


        mListView.setAdapter(new RecommendAdapter(mListView,mListData));

        return mListView;
    }

    private class RecommendAdapter extends SuperAdapter {

        public RecommendAdapter(AbsListView adsListView, List resData) {
            super(adsListView, resData);
        }

        @Override
        protected BaseHolder getSpecialHolder(int positin) {
            return null;
        }
    }
}

package mo.com.googleplay.fragment;/**
 * Created by  on
 */

import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

import mo.com.googleplay.base.BaseFragment;
import mo.com.googleplay.base.BaseHolder;
import mo.com.googleplay.base.Loadingpager;
import mo.com.googleplay.base.SuperAdapter;
import mo.com.googleplay.bean.SubjectBean;
import mo.com.googleplay.hodler.SubjectHolder;
import mo.com.googleplay.protocol.SubjectProtocol;
import mo.com.googleplay.utils.UIUtils;

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
public class SubjectFragment extends BaseFragment {

    private SubjectProtocol mSubjectProtocol;
    private List<SubjectBean> mListData;
    private SubjectAdapter mAdapter;

    /**
     * 加载数据
     * @return
     */
    @Override
    protected Loadingpager.LoadedResult initData() {
       /*使用协议加载数据*/
        mSubjectProtocol = new SubjectProtocol();

        try {
            List<SubjectBean> subjectBeanList = mSubjectProtocol.loadData(0);
            Loadingpager.LoadedResult state = checkState(subjectBeanList);

            if (state!= Loadingpager.LoadedResult.SUCCESS) {
                return state;
            }

            if (state!=null) {
                mListData = subjectBeanList;
            }

            /*通知数据改变*/
            if (mAdapter!=null) {
                mAdapter.notifyDataSetChanged();
            }
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            /*有问题，直接放回错误*/
            return Loadingpager.LoadedResult.ERROR;
        }
    }

    /**
     * 初始化View
     * @return
     */

    @Override
    public View initSuccessView() {
        ListView mListView = new ListView(UIUtils.getContext());
        mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.setFastScrollEnabled(true);
        mListView.setFadingEdgeLength(0);

        mAdapter = new SubjectAdapter(mListView, mListData);
        mListView.setAdapter(mAdapter);

        return mListView;
    }

    private class  SubjectAdapter extends SuperAdapter {
        public SubjectAdapter(AbsListView adsListView, List resData) {
            super(adsListView, resData);
        }


        @Override
        protected BaseHolder getSpecialHolder() {
            return new SubjectHolder();
        }

        /**
         * 加载更多
         * @return
         * @throws Exception
         */
        @Override
        protected List onLoadMore() throws Exception {

            SystemClock.sleep(2000);
            //解析数据
            List<SubjectBean> subjectBeans = mSubjectProtocol.loadData(mListData.size());

            if (subjectBeans != null) {
                return subjectBeans;
            } else {
                /*没有更多数据加载*/
                return null;
            }
        }

    /*    *//**
         * 没有更多数据加载
         * @return
         *//*
        @Override
        protected boolean hasLoadMore() {
            return false;
        }*/
    }


}


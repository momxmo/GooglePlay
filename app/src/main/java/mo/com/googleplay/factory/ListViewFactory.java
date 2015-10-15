package mo.com.googleplay.factory;/**
 * Created by  on
 */

import android.graphics.Color;
import android.widget.ListView;

import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/11:20:03
 * @描述 ListView创建工厂
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class ListViewFactory {

    /**
     * 创建ListView的方法
     * @return
     */
    public static ListView create() {
        ListView mListView = new ListView(UIUtils.getContext());
        //去除listview的拖动背景色
        mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.setFadingEdgeLength(0);
        /*设想这样一个场景，当ListView的内容有大于100页的情况下，
        如果想滑动到第80页，用手指滑动到指定位置，
        无疑是一件很费时的事情，如果想快速滑动到指定的位置，
        只需加上ListView的fastScrollEnabled属性等于true，
        启用快速滑动功能*/
        mListView.setFastScrollEnabled(true);

        return mListView;
    }
}

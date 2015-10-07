package mo.com.googleplay.base;/**
 * Created by  on
 */

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/7:19:47
 * @描述 TODO
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public abstract class SuperAdapter<T> extends BaseAdapter {

    private List<T> resData;

    public SuperAdapter(List<T> resData) {
        this.resData = resData;
    }

    @Override
    public int getCount() {
        if (resData != null) {
            return resData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (resData != null) {
            return resData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;
        if (convertView == null) {
            //返回一个BaseHolder的子类
            holder = getSpecialHolder();
        } else {
            holder = (BaseHolder) convertView.getTag();
        }

        holder.setDataAndRefreshHolderView(resData.get(position));

        /*---------------数据的绑定---------------*/

        //返回Hodler中的View
        return holder.mHolderView;
    }

    /**
     * @dec 让子类去实现具体的BaseHodler，并且返回BaseHolder
     * @return
     */
    protected abstract BaseHolder getSpecialHolder();
}

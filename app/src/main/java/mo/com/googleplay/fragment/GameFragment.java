package mo.com.googleplay.fragment;/**
 * Created by  on
 */

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import mo.com.googleplay.R;
import mo.com.googleplay.base.BaseFragment;
import mo.com.googleplay.base.Loadingpager;
import mo.com.googleplay.utils.Cheeses;
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
public class GameFragment extends BaseFragment {

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
        View view = View.inflate(UIUtils.getContext(), R.layout.item, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_tab_content);
        ImageView iv = (ImageView) view.findViewById(R.id.header);
        iv.setImageResource(Cheeses.getRandomCheeseDrawable());
        tv.setText(this.getClass().getSimpleName());
        tv.setGravity(Gravity.CENTER);
        return view;
    }
}
package mo.com.googleplay.factory;/**
 * Created by  on
 */


import java.util.HashMap;
import java.util.Map;

import mo.com.googleplay.base.BaseFragment;
import mo.com.googleplay.fragment.AppFragment;
import mo.com.googleplay.fragment.CategoryFragment;
import mo.com.googleplay.fragment.GameFragment;
import mo.com.googleplay.fragment.HomeFragment;
import mo.com.googleplay.fragment.HotFragment;
import mo.com.googleplay.fragment.RecommendFragment;
import mo.com.googleplay.fragment.SubjectFragment;
import mo.com.googleplay.utils.LogUtils;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/6:19:42
 * @描述 Fragment创建工厂
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class FragmentFactory {

      /* <item>首页</item>
        <item>应用</item>
        <item>游戏</item>
        <item>专题</item>
        <item>推荐</item>
        <item>分类</item>
        <item>排行</item>*/

    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_APP = 1;
    public static final int FRAGMENT_GAME = 2;
    public static final int FRAGMENT_SUJECT = 3;
    public static final int FRAGMENT_RECOMMENT = 4;
    public static final int FRAGMENT_CATEGORY = 5;
    public static final int FRAGMENT_HOT = 6;
    private static final String TAG = "FragmentFactory";

    /*使用集合将Fragment缓存*/
   private static Map<Integer, BaseFragment> fragmentsMap = new HashMap<Integer, BaseFragment>();
    /**
     * 创建对应的Fragment
     *
     * @param position
     * @return
     */
    public static BaseFragment createFragment(int position) {
        //1.从缓存中获取
        if (fragmentsMap.containsKey(position)) {
            return fragmentsMap.get(position);
        }
        LogUtils.i(TAG,"加载"+position);
        BaseFragment fragment = null;
        switch (position) {
            case FRAGMENT_HOME:
                fragment = new HomeFragment();
                break;
            case FRAGMENT_APP:
                fragment = new AppFragment();
                break;
            case FRAGMENT_GAME:
                fragment = new GameFragment();
                break;
            case FRAGMENT_SUJECT:
                fragment = new SubjectFragment();
                break;
            case FRAGMENT_RECOMMENT:
                fragment = new RecommendFragment();
                break;
            case FRAGMENT_CATEGORY:
                fragment = new CategoryFragment();
                break;
            case FRAGMENT_HOT:
                fragment = new HotFragment();
                break;
            default:
                break;
        }

        /**
         * 2.保存到缓存中
         */
        fragmentsMap.put(position, fragment);
        return fragment;
    }
}

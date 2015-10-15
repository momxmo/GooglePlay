package mo.com.googleplay.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.LinkedList;

import mo.com.googleplay.MainActivity;
import mo.com.googleplay.utils.SPUtil;


/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-11 下午5:21:55
 * @描述	     TODO
 *
 * @版本       $Rev: 55 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-11 17:34:30 +0800 (周日, 11 十月 2015) $
 * @更新描述    TODO
 */
public class BaseActivity extends AppCompatActivity {
	/**
	 * 1.不用关心onCreate只需关心我所一定的方法
	 * 2.定义了哪些方法是必须实现,哪些方法是选择性的实现
	 * 3.放置一些共有属性以及共有的方法
	 */
	// 1.activity的完成退出
	// 2.得到最前端的activity
	// 3.退出的时候toast

	// 4.操作sp
	public SPUtil spUtil;

	private long							mPreClickTime;
	public static LinkedList<BaseActivity>	allActivitys	= new LinkedList<BaseActivity>();
	public BaseActivity						mTopActivity;

	public BaseActivity getTopActivity() {
		return mTopActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		allActivitys.add(this);
		spUtil = new SPUtil(this);
		init();
		initView();
		initToolBar();
		initData();
		initListener();
	}

	@Override
	protected void onResume() {
		mTopActivity = this;
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		allActivitys.remove(this);
		super.onDestroy();
	}

	protected void init() {

	}

	protected void initView() {

	}

	protected void initToolBar() {

	}

	protected void initData() {

	}

	protected void initListener() {

	}

	/**完成退出*/
	public void exit() {
		for (BaseActivity activity : allActivitys) {
			activity.finish();
		}
	}

	@Override
	public void onBackPressed() {
		// 是否是主要
		if (this instanceof MainActivity) {// 主页
			if (System.currentTimeMillis() - mPreClickTime > 2000) {// 两次按下的时间间隔大于2s钟
				Toast.makeText(getApplicationContext(), "再按一次,退出谷歌市场", Toast.LENGTH_SHORT).show();
				mPreClickTime = System.currentTimeMillis();
				return;
			} else {// 用户点击速度非常快
				exit();
			}
		} else {
			super.onBackPressed();
		}
	}

}

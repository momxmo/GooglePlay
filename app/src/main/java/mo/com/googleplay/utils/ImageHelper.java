package mo.com.googleplay.utils;/**
 * Created by  on
 */

import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import mo.com.googleplay.R;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/7:22:01
 * @描述 图片加载的工具类（单利模式）
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class ImageHelper {


    static ImageLoader mImageLoader;

    /**
     * 配置框架
     */
    static {
        //获得实例对象
        mImageLoader = ImageLoader.getInstance();


        /**
         * 显示图片的操作配置
         */
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(20))// 设置成圆角图片
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).
                        build();


        /**
         *
         *  // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
         options = new DisplayImageOptions.Builder()
         .showStubImage(R.drawable.ic_stub)          // 设置图片下载期间显示的图片
         .showImageForEmptyUri(R.drawable.ic_empty)  // 设置图片Uri为空或是错误的时候显示的图片
         .showImageOnFail(R.drawable.ic_error)       // 设置图片加载或解码过程中发生错误显示的图片
         .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
         .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
         .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
         .build();                                   // 创建配置过得DisplayImageOption对象


         * 显示图片
         * 参数1：图片url
         * 参数2：显示图片的控件
         * 参数3：显示图片的设置
         * 参数4：监听器
        imageLoader.displayImage(imageUrls[position], holder.image, options, animateFirstListener);
        */


        /*框架的配置*/
        ImageLoaderConfiguration config
                = new ImageLoaderConfiguration.Builder(UIUtils.getContext())
                .threadPoolSize(3)
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .defaultDisplayImageOptions(options)
                .build();

        mImageLoader.init(config);

    }

    public static <T extends View> void displayImage(ImageView container, String uri) {
        mImageLoader.displayImage(uri, container);
    }


}

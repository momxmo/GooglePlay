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

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/7:22:01
 * @描述 图片加载的工具类（单利模式）
 * @项目名 GooglePlay
 *
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
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).
                        build();


        /*框架的配置*/
        ImageLoaderConfiguration config
                =  new ImageLoaderConfiguration.Builder(UIUtils.getContext())
                .threadPoolSize(3)
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .defaultDisplayImageOptions(options)
                .build();

        mImageLoader.init(config);

    }

    public static <T extends View> void displayImage(ImageView container, String uri) {
        mImageLoader.displayImage(uri,container);
    }


}

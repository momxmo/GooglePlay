package mo.com.googleplay.conf;


import mo.com.googleplay.utils.LogUtils;
import mo.com.googleplay.utils.UIUtils;

/**
 * @创建者 Administrator
 * @创时间 2015-10-6 下午2:07:18
 * @描述 TODO
 * @版本 $Rev: 3 $
 * @更新者 $Author: admin $
 * @更新时间 $Date: 2015-10-06 14:18:37 +0800 (周二, 06 十月 2015) $
 * @更新描述 TODO
 */
public class Constants {
    /**
     * LogUtils.LEVEL_ALL:显示所有日志
     * LogUtils.LEVEL_OFF:不显示所有日志
     */
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;

    /**
     * 数据的有效时间
     */
    public static final long PROTOCOLTIMEOUT = 2 * 1000;
    /**
     * 缓存数据存放的位置
     */
    public static String GET_FILE_CACHE_DIR = UIUtils.getContext().getCacheDir().getAbsolutePath();


    /**
     * 网络请求连接
     */
    public static class Req {
        //网络接口
        public static final String HOME_URL = "http://192.168.23.1:8080/GooglePlayServer/";
//        public static final String HOME_URL = "http://188.188.7.85:8080/GooglePlayServer/";

        //图片加载的数据
        public static final String HOME_IMAGE_URL = HOME_URL + "image?name=";

        //下载连接
        public static final String DOWNLOAD_URL = HOME_URL+"download?name=";
    }
}

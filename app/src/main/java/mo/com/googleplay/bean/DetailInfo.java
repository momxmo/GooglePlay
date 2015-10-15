package mo.com.googleplay.bean;/**
 * Created by  on
 */

import java.util.List;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/12:14:35
 * @描述 TODO
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class DetailInfo {
    public String author;
    public String date;
    public String des;
    public String downloadNum;
    public String downloadUrl;
    public String iconUrl;
    public long id;
    public String name;
    public String packageName;

    public List<SafeInfo> safe;
    public List<String> screen;

    public String size;
    public float stars;
    public String version;

    public static class SafeInfo {
        public String safeDes;
        public int safeDesColor;
        public String safeDesUrl;
        public String safeUrl;
    }
}

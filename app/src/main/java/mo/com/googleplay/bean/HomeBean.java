package mo.com.googleplay.bean;/**
 * Created by  on
 */

import java.io.Serializable;
import java.util.List;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/7:19:12
 * @描述 主页Bean对象
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class HomeBean {

    public List<AppInfo> list;
    public List<String> picture;    //App图片地址

    public static class AppInfo  implements Serializable {
        public String des;        //App描述
        public String downloadUrl; //下载链接
        public String iconUrl;     //图片连接
        public String id;          //id
        public String name;         //App名称
        public String packageName; //App包名
        public long size;        //App大小
        public float stars;       //App的级别


        @Override
        public String toString() {
            return "AppInfo{" +
                    "des='" + des + '\'' +
                    ", downloadUrl='" + downloadUrl + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    ", id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", size=" + size +
                    ", stars=" + stars +
                    '}';
        }
    }

}

package mo.com.googleplay.bean;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/11:20:24
 * @描述 TODO
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class CategoryInfoBean {

    public String name1;
    public String name2;
    public String name3;
    public String url1;
    public String url2;
    public String url3;

    public String title;   //分类标题名称

    // 自己加一个属性
    public boolean	isTitle;

    @Override
    public String toString() {
        return "CategoryInfoBean{" +
                "name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", name3='" + name3 + '\'' +
                ", url1='" + url1 + '\'' +
                ", url2='" + url2 + '\'' +
                ", url3='" + url3 + '\'' +
                ", title='" + title + '\'' +
                ", isTitle=" + isTitle +
                '}';
    }
}

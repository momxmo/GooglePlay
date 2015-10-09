package mo.com.googleplay.protocol;/**
 * Created by  on
 */

import com.google.gson.Gson;

import mo.com.googleplay.base.BaseProtocl;
import mo.com.googleplay.bean.HomeBean;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/8:18:17
 * @描述  主页协议
 * @项目名 GooglePlay
 *
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class HomeProtocol extends BaseProtocl<HomeBean> {

    @Override
    protected HomeBean parseJson(String jsonData) {
        Gson gson = new Gson();
        return gson.fromJson(jsonData, HomeBean.class);
    }

    @Override
    public String getInterfaceKey() {
        return "home";
    }
}

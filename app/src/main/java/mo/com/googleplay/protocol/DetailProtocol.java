package mo.com.googleplay.protocol;/**
 * Created by  on
 */

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import mo.com.googleplay.base.BaseProtocl;
import mo.com.googleplay.bean.DetailInfo;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/12:14:46
 * @描述 TODO
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class DetailProtocol extends BaseProtocl<DetailInfo> {

    private final String mPackageName;

    public DetailProtocol(String packageName) {
        mPackageName = packageName;
    }

    @Override
    protected DetailInfo parseJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, DetailInfo.class);
    }

    @Override
    public String getInterfaceKey() {
        return "detail";
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        Map<String, String> map = new HashMap<String,String>();
        map.put("packageName", mPackageName);
        return map;
    }
}

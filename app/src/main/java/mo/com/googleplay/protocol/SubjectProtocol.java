package mo.com.googleplay.protocol;/**
 * Created by  on
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import mo.com.googleplay.base.BaseProtocl;
import mo.com.googleplay.bean.SubjectBean;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/8:18:17
 * @描述  专题协议
 * @项目名 GooglePlay
 *
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class SubjectProtocol extends BaseProtocl<List<SubjectBean> > {

    @Override
    protected List<SubjectBean> parseJson(String jsonData) {
        Gson gson = new Gson();
        return gson.fromJson(jsonData, new TypeToken<List<SubjectBean>>() {
        }.getType());
    }

    @Override
    public String getInterfaceKey() {
        return "subject";
    }
}

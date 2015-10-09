package mo.com.googleplay.protocol;/**
 * Created by  on
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import mo.com.googleplay.base.BaseProtocl;
import mo.com.googleplay.bean.GameInfoBean;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/8:22:09
 * @描述 游戏数据加载协议
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class GameProtocol extends BaseProtocl<List<GameInfoBean>> {

    /**
     * 数据的解析
     *
     * @param jsonData
     * @return
     */
    @Override
    protected List<GameInfoBean> parseJson(String jsonData) {
        Gson gson = new Gson();
        return gson.fromJson(jsonData, new TypeToken<List<GameInfoBean>>() {
        }.getType());
    }

    @Override
    public String getInterfaceKey() {
        return "game";
    }
}

package mo.com.googleplay.protocol;/**
 * Created by  on
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mo.com.googleplay.base.BaseProtocl;
import mo.com.googleplay.bean.CategoryInfoBean;

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/11:20:42
 * @描述 TODO
 * @项目名 GooglePlay
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class CategoryProtocol extends BaseProtocl<List<CategoryInfoBean>> {


    @Override
    protected List<CategoryInfoBean> parseJson(String jsonString) {

        List<CategoryInfoBean> beanList = null;
        /*这里使用手动解析的方式*/
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            beanList = new ArrayList<CategoryInfoBean>();

            for (int i = 0; i < jsonArray.length(); i++) {
                CategoryInfoBean bean = new CategoryInfoBean();
                JSONObject itemJsonObjcet = (JSONObject) jsonArray.get(i);

                String title = itemJsonObjcet.getString("title");
                bean.isTitle = true;
                bean.title = title;
                beanList.add(bean);

                JSONArray array = itemJsonObjcet.getJSONArray("infos");
                for (int j = 0; j < array.length(); j++) {
                    CategoryInfoBean infobean = new CategoryInfoBean();

                    JSONObject infosArray = (JSONObject) array.get(j);
                    String name1 = infosArray.getString("name1");
                    String name2 = infosArray.getString("name2");
                    String name3 = infosArray.getString("name3");

                    String url1 = infosArray.getString("url1");
                    String url2 = infosArray.getString("url2");
                    String url3 = infosArray.getString("url3");

                    infobean.name1 = name1;
                    infobean.name2 = name2;
                    infobean.name3 = name3;
                    infobean.url1 = url1;
                    infobean.url2 = url2;
                    infobean.url3 = url3;
                    beanList.add(infobean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return beanList;
    }


    @Override
    public String getInterfaceKey() {
        return "category";
    }
}

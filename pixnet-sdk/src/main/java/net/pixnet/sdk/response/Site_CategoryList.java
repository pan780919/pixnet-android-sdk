package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Site_CategoryList extends BaseListResponse {

    /**
     * PIXNET categories
     */
    public ArrayList<Category> categories;

    public Site_CategoryList(String str) {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if (obj.has("categories")) {
            categories = new ArrayList<Category>();
            JSONArray aobj = obj.getJSONArray("categories");
            for (int i = 0; i < aobj.length(); i++) {
                categories.add(new Category(aobj.getJSONObject(i)));
            }
        }
        return obj;
    }
}
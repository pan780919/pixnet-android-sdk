package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Categories List
 */
public class CategoryList extends BasicResponse {

    /**
     * List of categories
     */
    public ArrayList<Category> Categories;

    public CategoryList(String str) {
        super(str);
    }

    public CategoryList(JSONObject jo) {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if(obj.has("categories")) {
            Categories = new ArrayList<Category>();
            JSONArray ja = new JSONArray(obj.getString("categories"));
            for (int i = 0; i < ja.length(); i++) {
                Categories.add(new Category(ja.getJSONObject(i)));
            }
        }
        return obj;
    }
}
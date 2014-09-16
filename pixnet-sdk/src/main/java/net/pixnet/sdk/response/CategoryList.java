package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * categories List
 */
public class CategoryList extends BasicResponse {

    /**
     * List of categories
     */
    public ArrayList<Category> categories;

    public CategoryList(String str) throws JSONException {
        super(str);
    }

    public CategoryList(JSONObject jo) throws JSONException {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if(obj.has("categories")) {
            categories = new ArrayList<Category>();
            JSONArray ja = obj.getJSONArray("categories");
            for (int i = 0; i < ja.length(); i++) {
                categories.add(new Category(ja.getJSONObject(i)));
            }
        }
        return obj;
    }
}
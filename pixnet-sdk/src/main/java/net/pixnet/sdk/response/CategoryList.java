package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Categories List
 */
public class CategoryList extends BasicResponse {
    CategoryList(String response) {
        formatJson(response);
    }

    /**
     * List of categories
     */
    public ArrayList<Category> Categories;

    void formatJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if (obj.has("message")) {
                message = obj.getString("message");
            }
            if (obj.has("error")) {
                error = obj.getString("error");
            }
            if(obj.has("categories")) {
                Categories = new ArrayList<Category>();
                JSONArray ja = new JSONArray(obj.getString("categories"));
                for (int i = 0; i < ja.length(); i++) {
                    Categories.add(new Category(ja.getString(i)));
                }
            }
        } catch (JSONException e) {

        }
    }
}
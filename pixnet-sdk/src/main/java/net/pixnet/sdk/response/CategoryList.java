package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;

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
        Categories = new ArrayList<Category>();
        try {
            JSONArray ja = new JSONArray(response);
            for (int i = 0; i < ja.length(); i++) {
                Categories.add(new Category(ja.getString(i)));
            }
        } catch (JSONException e) {

        }
    }
}
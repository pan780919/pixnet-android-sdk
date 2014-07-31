package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Site_CategoryList extends BasicResponse {
    public Site_CategoryList(String response) {
        formatJson(response);
    }

    /**
     * PIXNET categories
     */
    public ArrayList<Category> categories;

    private void formatJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if (obj.has("message")) {
                message = obj.getString("message");
            }
            if (obj.has("error")) {
                error = obj.getString("error");
            }
            if (obj.has("categories")) {
                categories = new ArrayList<Category>();
                JSONArray aobj = obj.getJSONArray("categories");
                for (int i = 0; i < aobj.length(); i++) {
                    categories.add(new Category(aobj.getString(i)));
                }
            }
        } catch (JSONException e) {

        }
    }
}
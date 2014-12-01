package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Response for Category
 */
public class Category extends BasicResponse {
    /**
     * Category id
     */
    public String id;
    /**
     * Category type
     */
    public String type;
    /**
     * Order
     */
    public String order;
    /**
     * Category name
     */
    public String name;
    /**
     * Category description
     */
    public String description;
    /**
     * Show index or not
     */
    public String show_index;
    public String site_category_id;
    public String site_category_done;
    /**
     * Child categories list
     */
    public List<Category> child_categories;

    public Category(JSONObject jo) throws JSONException {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if (obj.has("id")) {
            id = obj.getString("id");
        }
        if (obj.has("type")) {
            type = obj.getString("type");
        }
        if (obj.has("order")) {
            order = obj.getString("order");
        }
        if (obj.has("name")) {
            name = obj.getString("name");
        }
        if (obj.has("description")) {
            description = obj.getString("description");
        }
        if (obj.has("show_index")) {
            show_index = obj.getString("show_index");
        }
        if (obj.has("child_categories")) {
            child_categories = new ArrayList<Category>();
            JSONArray ja = obj.getJSONArray("child_categories");
            for (int i = 0; i < ja.length(); i++) {
                child_categories.add(new Category(ja.getJSONObject(i)));
            }
        }

        if(obj.has("site_category_id"))
            site_category_id=obj.getString("site_category_id");
        if(obj.has("site_category_done"))
            site_category_done=obj.getString("site_category_done");

        return obj;
    }
}
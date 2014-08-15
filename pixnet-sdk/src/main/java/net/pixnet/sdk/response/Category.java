package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Response for Category
 */
public class Category extends BasicResponse{
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
    /**
     * site_category_id
     */
    public String site_category_id;

    /**
     * site_category_done
     */
    public String site_category_done;
    /**
     * Child categories list
     */
    public CategoryList child_categories;

    public Category(JSONObject jo) {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if(obj.has("id")){
            id = obj.getString("id");
        }
        if(obj.has("type")){
            type = obj.getString("type");
        }
        if(obj.has("order")){
            order = obj.getString("order");
        }
        if(obj.has("name")){
            name = obj.getString("name");
        }
        if(obj.has("description")){
            description = obj.getString("description");
        }
        if(obj.has("show_index")){
            show_index = obj.getString("show_index");
        }
        if(obj.has("site_category_id")){
            site_category_id = obj.getString("site_category_id");
        }
        if(obj.has("site_category_done")){
            site_category_done = obj.getString("site_category_done");
        }
        if(obj.has("child_categories")){
            child_categories = new CategoryList(obj.getString("child_categories"));
        }
        return obj;
    }
}
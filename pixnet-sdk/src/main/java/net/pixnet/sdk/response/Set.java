package net.pixnet.sdk.response;

import net.pixnet.sdk.proxy.DataProxy;

import org.json.JSONException;
import org.json.JSONObject;

public class Set extends AlbumContainer{
    /**
     * Set url
     */
    public String link;
    /**
     * Set permission
     */
    public String permission;
    /**
     * Set category id
     */
    public String category_id;
    /**
     * Set category
     */
    public String category;
    /**
     * is lock right
     */
    public boolean is_lockright;
    /**
     *Set licence
     */
    public License licence;
    /**
     * Show set can comment or not
     */
    public boolean cancomment;
    /**
     * Set parent id
     */
    public String parent_id;
    /**
     * Show this set is system set or not
     */
    public boolean is_system_set;
    public Hits hits;
    /**
     * Total elements count
     */
    public int total_elements;

    /**
     * Show elements in this set is taggable or not
     */
    public boolean is_taggable;

    public Set(String str) {
        super(str);
    }

    public Set(JSONObject jo){
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);

        if(jo.has("set"))
            jo=jo.getJSONObject("set");

        if(jo.has("link"))
            link=jo.getString("link");
        if(jo.has("permission"))
            permission =jo.getString("permission");
        if(jo.has("category_id"))
            category_id=jo.getString("category_id");
        if(jo.has("category"))
            category=jo.getString("category");
        if(jo.has("is_lockright"))
            is_lockright=jo.getInt("is_lockright")==0?false:true;
        if(jo.has("lincense"))
            licence=new License(jo.getJSONObject("lincense"));
        if(jo.has("cancomment"))
            cancomment=jo.getInt("cancomment")==0?false:true;
        if(jo.has("parent_id"))
            parent_id=jo.getString("parent_id");
        if(jo.has("is_system_set"))
            is_system_set= DataProxy.getJsonBoolean(jo, "is_system_set");
        if(jo.has("hits"))
            hits=new Hits(jo.getJSONObject("hits"));
        if(jo.has("total_elements"))
            total_elements=jo.getInt("total_elements");
        if(jo.has("is_taggable"))
            is_taggable=DataProxy.getJsonBoolean(jo, "is_taggable");

        return jo;
    }
}
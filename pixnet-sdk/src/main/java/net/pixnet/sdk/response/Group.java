package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Group
 */
public class Group{
    public Group(JSONObject jo){
        formatJson(jo);
    }
    /**
     * Group id
     */
    public String id;
    /**
     * Group name
     */
    public String name;
    /**
     * Group order
     */
    public String order;
    private void formatJson(JSONObject obj) {
        try {
            if(obj.has("id")){
                id = obj.getString("id");
            }
            if(obj.has("name")){
                name = obj.getString("name");
            }
            if(obj.has("order")){
                order = obj.getString("order");
            }
        }catch (JSONException e){

        }
    }
}
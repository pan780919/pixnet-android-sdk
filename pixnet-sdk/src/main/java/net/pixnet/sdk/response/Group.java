package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Group
 */
public class Group{
    public Group(String response){
        formatJson(response);
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
    private void formatJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
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
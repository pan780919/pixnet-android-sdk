package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class Tagged{
    Tagged(String response){
        formatJson(response);
    }
    /**
     * Tag id
     */
    public String id;
    /**
     * Tag position x
     */
    public String x;
    /**
     * Tag position y
     */
    public String y;
    /**
     * Tag width
     */
    public String w;
    /**
     * Tag height
     */
    public String h;
    /**
     * Tag creator
     */
    public String creator;
    /**
     * Tagged user
     */
    public String user;
    private void formatJson(String response){
        try{
            JSONObject obj = new JSONObject(response);
            if(obj.has("id")){
                id = obj.getString("id");
            }
            if(obj.has("x")){
                x = obj.getString("x");
            }
            if(obj.has("y")){
                y = obj.getString("y");
            }
            if(obj.has("w")){
                w = obj.getString("w");
            }
            if(obj.has("h")){
                h = obj.getString("h");
            }
            if(obj.has("creator")){
                creator = obj.getString("creator");
            }
            if(obj.has("user")){
                user = obj.getString("user");
            }
        }catch(JSONException e){

        }
    }
}
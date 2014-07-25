package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class Reply{
    Reply(String response){formatJson(response);}
    /**
     * Reply body
     */
    public String body;
    /**
     * time reply created
     */
    public String created_at;
    public void formatJson(String response){
        try {
            JSONObject obj = new JSONObject(response);
            if(obj.has("body")){
                body = obj.getString("body");
            }
            if(obj.has("created_at")){
                created_at = obj.getString("created_at");
            }
        }catch (JSONException e){

        }
    }
}
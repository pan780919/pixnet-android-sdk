package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class Blocks{
    public Blocks(String response){
        formatJson(response);
    }
    /**
     * blocked user info
     */
    public User user;
    /**
     * block time
     */
    public long created_at;
    private void formatJson(String response){
        try {
            JSONObject obj = new JSONObject(response);
            if(obj.has("user")){
                user = new User(obj);
            }
            if(obj.has("created_at")){
                created_at = obj.getInt("created_at")*1000l;
            }
        }catch(JSONException e){
        }
    }
}
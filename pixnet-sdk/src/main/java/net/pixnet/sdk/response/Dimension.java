package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Element dimension
 */
public class Dimension{
    public Dimension(String response){
        formatJson(response);
    }
    /**
     * Original dimension
     */
    public Original original;
    /**
     * Thumb dimension
     */
    public Thumb thumb;
    private void formatJson(String response){
        try {
            JSONObject obj = new JSONObject(response);
            if(obj.has("original")){
                original = new Original(obj.getString("original"));
            }
            if(obj.has("thumb")){
                thumb = new Thumb(obj.getString("thumb"));
            }
        }catch(JSONException e){

        }
    }
}
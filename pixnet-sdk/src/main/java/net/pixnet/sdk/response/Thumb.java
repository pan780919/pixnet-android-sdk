package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class Thumb{
    Thumb(String response){
        formatJson(response);
    }
    /**
     * Element thumb width
     */
    public int width;
    /**
     * Element thumb height
     */
    public int height;
    private void formatJson(String response){
        try {
            JSONObject obj = new JSONObject(response);
            if(obj.has("width")){
                width = obj.getInt("width");
            }
            if(obj.has("height")){
                height = obj.getInt("height");
            }
        }catch(JSONException e){

        }
    }
}
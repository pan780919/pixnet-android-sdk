package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class Original{
    public Original(String response){
        formatJson(response);
    }
    /**
     * Element original width
     */
    public String width;
    /**
     * Element original height
     */
    public String height;
    private void formatJson(String response){
        try {
            JSONObject obj = new JSONObject(response);
            if(obj.has("width")){
                width = obj.getString("width");
            }
            if(obj.has("height")){
                height = obj.getString("height");
            }
        }catch(JSONException e){

        }
    }
}
package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Image info
 */
public class Image {
    public Image(String response){
        formatJson(response);
    }
    /**
     * Image url
     */
    public String url;
    /**
     * Image width
     */
    public String width;
    /**
     * Image height
     */
    public String height;
    private void formatJson(String response){
        try {
            JSONObject imageobj  = new JSONObject(response);
            if (imageobj.has("url")) {
                url =  imageobj.getString("url");
            }
            if (imageobj.has("width")) {
                width =  imageobj.getString("width");
            }
            if (imageobj.has("height")) {
                height =  imageobj.getString("height");
            }
        }catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
}
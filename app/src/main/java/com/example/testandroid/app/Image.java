package com.example.testandroid.app;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Image info
 */
public class Image {
    public Image(JSONObject imageobj){
        try {
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
}
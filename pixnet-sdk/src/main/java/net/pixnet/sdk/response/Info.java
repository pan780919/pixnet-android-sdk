package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Article Info
 */
public class Info {
    public Info(JSONObject infoobj){
        try {
            if (infoobj.has("trackbacks_count")) {
                trackbacks_count = infoobj.getInt("trackbacks_count");
            }
            if (infoobj.has("comments_count")) {
                comments_count = infoobj.getInt("comments_count");
            }
            if (infoobj.has("hit")) {
                hit = infoobj.getInt("hit");
            }
        }catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Article trackbacks count
     */
    public int trackbacks_count;
    /**
     * Article comments count
     */
    public int comments_count;
    /**
     * article hits count
     */
    public int hit;
}
package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class FaceTag {
    /**
     * Tag id
     */
    public String id;
    /**
     * Tag position x
     */
    public int x;
    /**
     * Tag position y
     */
    public int y;
    /**
     * Tag width
     */
    public int w;
    /**
     * Tag height
     */
    public int h;
    /**
     * Tag creator
     */
    public String creator;
    /**
     * Tagged user
     */
    public String user;
    public FaceTag(JSONObject jo) throws JSONException {
        if(jo.has("id")){
            id = jo.getString("id");
        }
        if(jo.has("x")){
            x = jo.getInt("x");
        }
        if(jo.has("y")){
            y = jo.getInt("y");
        }
        if(jo.has("w")){
            w = jo.getInt("w");
        }
        if(jo.has("h")){
            h = jo.getInt("h");
        }
        if(jo.has("creator")){
            creator = jo.getString("creator");
        }
        if(jo.has("user")){
            user = jo.getString("user");
        }
    }
}
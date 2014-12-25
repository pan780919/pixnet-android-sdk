package net.pixnet.sdk.response;

import net.pixnet.sdk.proxy.DataProxy;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Config
 */
public class AlbumConfig extends BasicResponse{
    /**
     * Show user is vip or not
     */
    public boolean is_vip;
    /**
     * Rotate by exif
     */
    public boolean is_rotatebyexif;
    /**
     * Show is element first or not
     */
    public boolean is_elementfirst;
    /**
     * User avatar url
     */
    public String user_avatar;
    /**
     * User quota
     */
    public int user_quota;
    /**
     * User used quota
     */
    public int user_used_quota;

    public AlbumConfig(String str) throws JSONException {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);

        if(jo.has("is_vip"))
            is_vip= DataProxy.getJsonBoolean(jo, "is_vip");
        if(jo.has("is_rotatebyexif"))
            is_rotatebyexif=DataProxy.getJsonBoolean(jo, "is_rotatebyexif");
        if(jo.has("is_elementfirst"))
            is_elementfirst=DataProxy.getJsonBoolean(jo, "is_elementfirst");
        if(jo.has("user_avatar"))
            user_avatar=jo.getString("user_avatar");
        if(jo.has("user_quota"))
            user_quota=jo.getInt("user_quota");
        if(jo.has("user_used_quota"))
            user_used_quota=jo.getInt("user_used_quota");

        return jo;
    }
}
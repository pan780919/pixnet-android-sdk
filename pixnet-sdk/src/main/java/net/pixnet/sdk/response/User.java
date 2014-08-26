package net.pixnet.sdk.response;

import net.pixnet.sdk.proxy.DataProxy;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User info
 */
public class User extends BasicResponse {
    /**
     * User name
     */
    public String name;
    /**
     * User displayname
     */
    public String display_name;
    /**
     * User avatar url
     */
    public String avatar;
    /**
     * Cavatar url
     */
    public String cavatar;
    /**
     * User page url
     */
    public String link;
    /**
     * User is vip or not
     */
    public boolean is_vip;
    /**
     * User has ad or not
     */
    public boolean has_ad;

    public User(JSONObject jo) {
        super(jo);
    }

    public User(String str) {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);
        jo=jo.getJSONObject("user");

        if (jo.has("name")) {
            name = jo.getString("name");
        }
        if (jo.has("display_name")) {
            display_name = jo.getString("display_name");
        }
        if (jo.has("avatar")) {
            avatar = jo.getString("avatar");
        }
        if (jo.has("cavatar")) {
            cavatar = jo.getString("cavatar");
        }
        if (jo.has("link")) {
            link = jo.getString("link");
        }
        if (jo.has("is_vip")) {
            is_vip = DataProxy.getJsonBoolean(jo, "is_vip");
        }
        if (jo.has("has_ad")) {
            has_ad = DataProxy.getJsonBoolean(jo, "has_ad");
        }
        return jo;
    }
}
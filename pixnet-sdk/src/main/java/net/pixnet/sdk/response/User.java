package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User info
 */
public class User {
    public User(String response){
        formatJson(response);
    }
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
    private void formatJson(String response){
        try {
            JSONObject userobj = new JSONObject(response);
            if (userobj.has("name")) {
                name = userobj.getString("name");
            }
            if (userobj.has("display_name")) {
                display_name = userobj.getString("display_name");
            }
            if (userobj.has("avatar")) {
                avatar = userobj.getString("avatar");
            }
            if (userobj.has("cavatar")) {
                cavatar = userobj.getString("cavatar");
            }
            if (userobj.has("link")) {
                link = userobj.getString("link");
            }
            if (userobj.has("is_vip")) {
                is_vip = userobj.getBoolean("is_vip");
            }
            if (userobj.has("has_ad")) {
                has_ad = userobj.getBoolean("has_ad");
            }

        }catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
}
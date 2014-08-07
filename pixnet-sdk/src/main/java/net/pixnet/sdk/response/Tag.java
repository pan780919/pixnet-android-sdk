package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Koi 2014/6/13.
 */
public class Tag extends BasicResponse {

    /**
     * tag name
     */
    public String tag;
    /**
     * lock status
     */
    public int locked;
    /**
     * added by username
     */
    public String added_by;

    public Tag(JSONObject jo) {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);
        if ( jo.has("tag")) {
            tag =  jo.getString("tag");
        }
        if ( jo.has("locked")) {
            locked =  jo.getInt("locked");
        }
        if ( jo.has("added_by")) {
            added_by =  jo.getString("added_by");
        }
        return jo;
    }
}
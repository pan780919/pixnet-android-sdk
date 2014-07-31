package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Koi 2014/6/13.
 */
public class Tag {
    public Tag(String response) {
        formatJson(response);
    }

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
    private void formatJson(String response){
        try {
            JSONObject tagobj = new JSONObject(response);
            if ( tagobj.has("tag")) {
                tag =  tagobj.getString("tag");
            }
            if ( tagobj.has("locked")) {
                locked =  tagobj.getInt("locked");
            }
            if ( tagobj.has("added_by")) {
                added_by =  tagobj.getString("added_by");
            }
        }catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
}
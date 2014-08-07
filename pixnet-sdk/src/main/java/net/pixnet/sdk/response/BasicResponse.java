package net.pixnet.sdk.response;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * default response
 */
public class BasicResponse {
    /**
     * auxiliary message
     */
    public String message;

    /**
     * error code
     * 0 = success
     */
    public String error;

    public BasicResponse() {}

    public BasicResponse(String str) {
        if(TextUtils.isEmpty(str))
            return;
        try {
            parseJSON(new JSONObject(str));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public BasicResponse(JSONObject jo){
        if(jo==null)
            return;
        try {
            parseJSON(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        message=jo.getString("message");
        error=jo.getString("error");
        return jo;
    }

}

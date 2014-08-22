package net.pixnet.sdk.response;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * a basically response with error and message
 */
public class BasicResponse {

    /**
     * @return a json string by http response
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * json string
     */
    private String rawData;
    /**
     * auxiliary message
     */
    public String message;

    /**
     * error code
     * 0 = success
     */
    public int error;

    public BasicResponse() {}

    public BasicResponse(String str) {
        if(TextUtils.isEmpty(str))
            return;

        rawData=str;

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
        if(jo.has("message"))
            message=jo.getString("message");
        if(jo.has("error"))
            error=jo.getInt("error");
        return jo;
    }

}

package net.pixnet.sdk.response;

import android.text.TextUtils;

import net.pixnet.sdk.utils.Helper;

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
     * error
     * 0 = success
     */
    public int error;

    /**
     * error code
     * reference: https://www.gitbook.com/book/pixnet/api-error-codes/details
     */
    public int code;

    public String error_description;

    public BasicResponse() {}

    public BasicResponse(String str) throws JSONException {
//        Helper.log("response:"+str);
        if(TextUtils.isEmpty(str))
            return;

        rawData=str;

        parseJSON(new JSONObject(str));
    }

    public BasicResponse(JSONObject jo) throws JSONException {
        if(jo==null)
            return;
        parseJSON(jo);
    }

    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        if(jo.has("code")){
            String errCode = jo.getString("code");
            try{
                code = Integer.parseInt(errCode);
            } catch (NumberFormatException e){
                code = -1;
            }
        }
        if(jo.has("message"))
            message=jo.getString("message");
        if(jo.has("error")) {
            String errStr = jo.getString("error");
            try {
                error = Integer.parseInt(errStr);
            }catch (NumberFormatException e){
                error=-1;
            }
        }
        if(jo.has("error_description"))
            error_description=jo.getString("error_description");

        return jo;
    }

}

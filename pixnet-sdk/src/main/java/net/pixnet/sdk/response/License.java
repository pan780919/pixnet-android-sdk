package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class License extends BasicResponse {
    /**
     * Licence type
     */
    public String type;

    public License(JSONObject jo) throws JSONException {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);

        if(jo.has("type"))
            type=jo.getString("type");

        return jo;
    }
}
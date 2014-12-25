package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class Reply extends BasicResponse{
    /**
     * Reply body
     */
    public String body;
    /**
     * time reply created
     */
    public long created_at;

    public Reply(JSONObject jo) throws JSONException {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if(obj.has("body")){
            body = obj.getString("body");
        }
        if(obj.has("created_at")){
            created_at = obj.getInt("created_at")*1000l;
        }
        return obj;
    }
}
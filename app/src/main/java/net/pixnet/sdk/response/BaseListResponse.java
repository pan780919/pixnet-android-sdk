package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseListResponse extends BasicResponse {

    public int total;
    public int per_page;
    public int page;

    public BaseListResponse(String str) throws JSONException {
        super(str);
    }

    public BaseListResponse(JSONObject jo) throws JSONException {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo=super.parseJSON(jo);

        if(jo.has("total"))
            total=jo.getInt("total");
        if(jo.has("per_page"))
            per_page=jo.getInt("per_page");
        if(jo.has("page"))
            page=jo.getInt("page");

        return jo;
    }
}

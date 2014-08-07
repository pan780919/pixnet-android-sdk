package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseListResponse extends BasicResponse {

    public int total;
    public int per_page;
    public int page;

    public BaseListResponse(String str) {
        super(str);
    }

    public BaseListResponse(JSONObject jo) {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo=super.parseJSON(jo);

        total=jo.getInt("total");
        per_page=jo.getInt("per_page");
        page=jo.getInt("page");

        return jo;
    }
}

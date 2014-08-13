package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class Hits extends BasicResponse {
    /**
     * Total blog visits
     */
    public int total;
    /**
     * Daily blog visits
     */
    public int daily;

    /**
     * Weekly blog visits
     */
    public int weekly;

    /**
     * monthly blog visits
     */
    public int monthly;

    public Hits(JSONObject jo) {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if (obj.has("total")) {
            total = obj.getInt("total");
        }
        if (obj.has("daily")) {
            daily = obj.getInt("daily");
        }
        if (obj.has("weekly")) {
            weekly = obj.getInt("weekly");
        }
        if(obj.has("monthly"))
            monthly=obj.getInt("monthly");
        return obj;
    }
}
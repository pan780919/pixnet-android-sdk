package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class Hits {
    public Hits(String response){
        formatJson(response);
    }
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
    public void formatJson(String response){
        try {
            JSONObject obj = new JSONObject(response);
            if (obj.has("total")) {
                total = obj.getInt("total");
            }
            if (obj.has("daily")) {
                daily = obj.getInt("daily");
            }
            if (obj.has("weekly")) {
                weekly = obj.getInt("weekly");
            }
        } catch (JSONException e) {
        }
    }
}
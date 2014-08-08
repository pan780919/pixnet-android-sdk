package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubscriptionList extends BaseListResponse {
    public ArrayList<Subscription> subscriptions;
    public int per_page;
    public int page;
    public int total;

    public SubscriptionList(String str) {
        super(str);
    }

    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if (obj.has("per_page"))
            per_page = obj.getInt("per_page");
        if (obj.has("page"))
            page = obj.getInt("page");
        if (obj.has("total"))
            total = obj.getInt("total");
        if (obj.has("subscriptions")) {
            subscriptions = new ArrayList<Subscription>();
            JSONArray ja = obj.getJSONArray("subscriptions");
            for (int i = 0; i < ja.length(); i++) {
                subscriptions.add(new Subscription(ja.getString(i)));
            }
        }
        return obj;
    }
}
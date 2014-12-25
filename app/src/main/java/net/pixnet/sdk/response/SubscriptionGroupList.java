package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubscriptionGroupList extends BaseListResponse {
    public ArrayList<Subscription_group> subscription_groups;
    public int total;

    public SubscriptionGroupList(String str) throws JSONException {
        super(str);
    }

    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if (obj.has("total"))
            total = obj.getInt("total");
        if (obj.has("subscription_groups")) {
            subscription_groups = new ArrayList<Subscription_group>();
            JSONArray ja = obj.getJSONArray("subscription_groups");
            for (int i = 0; i < ja.length(); i++) {
                subscription_groups.add(new Subscription_group(ja.getJSONObject(i)));
            }
        }
        return obj;
    }
}
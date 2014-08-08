package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Subscription_groupList extends BaseListResponse {
    public ArrayList<Subscription_group> subscription_groups;
    public int total;

    public Subscription_groupList(String str) {
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
                subscription_groups.add(new Subscription_group(ja.getString(i)));
            }
        }
        return obj;
    }
}
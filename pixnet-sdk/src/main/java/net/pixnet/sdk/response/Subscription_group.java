package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class Subscription_group {
    public String id;
    public String name;
    public String position;

    public Subscription_group(JSONObject obj) {
        formatJson(obj);
    }

    private void formatJson(JSONObject obj) {
        try {
            if (obj.has("id"))
                id = obj.getString("id");
            if (obj.has("name"))
                name = obj.getString("name");
            if (obj.has("position"))
                position = obj.getString("position");
        } catch (JSONException e) {

        }
    }
}
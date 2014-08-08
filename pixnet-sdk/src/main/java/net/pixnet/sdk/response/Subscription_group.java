package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class Subscription_group {
    public String id;
    public String name;
    public String position;

    public Subscription_group(String response) {
        formatJson(response);
    }

    private void formatJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
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
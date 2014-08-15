package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Subscription {
    public User user;
    public ArrayList<Group> groups;

    public Subscription(String response) {
        formatJson(response);
    }

    private void formatJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if (obj.has("user"))
                user = new User(obj);
            if (obj.has("groups")) {
                groups = new ArrayList<Group>();
                JSONArray ja = obj.getJSONArray("groups");
                for (int i = 0; i < ja.length(); i++) {
                    groups.add(new Group(ja.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {

        }
    }
}
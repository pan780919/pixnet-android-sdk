package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Friendship
 */
public class Friendship {
    public Friendship(String response) {
        formatJson(response);
    }

    /**
     * Friendship id
     */
    public String id;
    /**
     * Friend name
     */
    public String user_name;
    /**
     * Friend display name
     */
    public String display_name;
    /**
     * Friend group list
     */
    public ArrayList<Group> groups;

    private void formatJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if (obj.has("id"))
                id = obj.getString("id");
            if (obj.has("user_name"))
                user_name = obj.getString("user_name");
            if (obj.has("display_name"))
                display_name = obj.getString("display_name");
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
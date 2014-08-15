package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * FriendshipList
 */
public class FriendshipList extends BaseListResponse {
    /**
     * List of friends
     */
    public ArrayList<Friendship> friend_pairs;
    /**
     * previous_cursor
     */
    public int previous_cursor;
    /**
     * next_cursor
     */
    public int next_cursor;

    public FriendshipList(JSONObject jo) {
        super(jo);
    }

    public FriendshipList(String str) {
        super(str);
    }

    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if (obj.has("friend_pairs")) {
            friend_pairs = new ArrayList<Friendship>();
            JSONArray ja = obj.getJSONArray("friend_pairs");
            for (int i = 0; i < ja.length(); i++) {
                friend_pairs.add(new Friendship(ja.getString(i)));
            }
        }
        if(obj.has("previous_cursor"))
            previous_cursor = obj.getInt("previous_cursor");
        if(obj.has("next_cursor"))
            next_cursor = obj.getInt("next_cursor");
        return obj;
    }
}
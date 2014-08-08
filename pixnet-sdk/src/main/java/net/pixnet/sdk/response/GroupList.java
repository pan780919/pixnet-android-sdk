package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Group list
 */
public class GroupList extends BaseListResponse {
    /**
     * Total counts of group
     */
    public int total;
    /**
     * Group shows per page
     */
    public int per_page;
    /**
     * Current page
     */
    public int page;
    /**
     * Group list
     */
    public ArrayList<Group> friend_groups;

    public GroupList(String str) {
        super(str);
    }

    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if (obj.has("friend_groups")) {
            friend_groups = new ArrayList<Group>();
            JSONArray ja = obj.getJSONArray("friend_groups");
            for (int i = 0; i < ja.length(); i++) {
                friend_groups.add(new Group(ja.getString(i)));
            }
        }
        return obj;
    }
}

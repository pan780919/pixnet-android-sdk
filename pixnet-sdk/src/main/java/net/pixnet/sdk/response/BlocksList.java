package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Blocks list
 */
public class BlocksList extends BasicResponse {
    public BlocksList(String response) {
        formatJson(response);
    }

    /**
     * Blocks
     */
    public ArrayList<Blocks> blocks;

    private void formatJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if (obj.has("message")) {
                message = obj.getString("message");
            }
            if (obj.has("error")) {
                error = obj.getString("error");
            }
            if (obj.has("blocks")) {
                blocks = new ArrayList<Blocks>();
                JSONArray aobj = obj.getJSONArray("blocks");
                for (int i = 0; i < aobj.length(); i++) {
                    blocks.add(new Blocks(aobj.getString(i)));
                }
            }
        } catch (JSONException e) {
        }
    }
}
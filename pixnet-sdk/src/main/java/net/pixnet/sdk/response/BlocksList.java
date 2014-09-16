package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Blocks list
 */
public class BlocksList extends BaseListResponse {

    /**
     * Blocks
     */
    public ArrayList<Blocks> blocks;

    public BlocksList(String str) throws JSONException {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if (obj.has("blocks")) {
            blocks = new ArrayList<Blocks>();
            JSONArray aobj = obj.getJSONArray("blocks");
            for (int i = 0; i < aobj.length(); i++) {
                blocks.add(new Blocks(aobj.getString(i)));
            }
        }
        return obj;
    }
}
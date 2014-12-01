package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class Folder extends AlbumContainer{
    /**
     * Folder's total children sets
     */
    public int total_children_sets;

    public Folder(String str) throws JSONException {
        super(str);
    }

    public Folder(JSONObject jo) throws JSONException {
        super(jo);
    }
}
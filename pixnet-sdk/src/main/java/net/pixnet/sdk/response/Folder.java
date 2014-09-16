package net.pixnet.sdk.response;

import org.json.JSONException;

public class Folder extends AlbumContainer{
    /**
     * Folder's total children sets
     */
    public int total_children_sets;

    public Folder(String str) throws JSONException {
        super(str);
    }
}
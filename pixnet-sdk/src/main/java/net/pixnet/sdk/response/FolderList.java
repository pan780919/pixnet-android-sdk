package net.pixnet.sdk.response;

import org.json.JSONException;

import java.util.ArrayList;

public class FolderList extends BaseListResponse{
    public ArrayList<Folder> folders;

    public FolderList(String str) throws JSONException {
        super(str);
    }
}
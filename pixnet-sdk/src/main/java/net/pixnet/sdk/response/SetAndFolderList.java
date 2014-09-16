package net.pixnet.sdk.response;

import org.json.JSONException;

import java.util.List;

public class SetAndFolderList extends BaseListResponse {

    public List<AlbumContainer> setfolders;

    public SetAndFolderList(String str) throws JSONException {
        super(str);
    }
}

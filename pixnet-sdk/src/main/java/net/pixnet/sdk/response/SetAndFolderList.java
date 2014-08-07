package net.pixnet.sdk.response;

import java.util.List;

public class SetAndFolderList extends BaseListResponse {

    public List<AlbumContainer> setfolders;

    public SetAndFolderList(String str) {
        super(str);
    }
}

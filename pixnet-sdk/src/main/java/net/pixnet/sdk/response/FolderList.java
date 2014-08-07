package net.pixnet.sdk.response;

import java.util.ArrayList;

public class FolderList extends BaseListResponse{
    /**
     * Folders counts
     */
    public int total;
    /**
     * Folders shown per page
     */
    public int per_page;
    /**
     * Current page
     */
    public int page;
    /**
     * Folder list
     */
    public ArrayList<Folder> folders;

    public FolderList(String str) {
        super(str);
    }
}
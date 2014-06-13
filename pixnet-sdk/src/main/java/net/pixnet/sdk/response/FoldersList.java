package net.pixnet.sdk.response;

import java.util.ArrayList;

public class FoldersList extends BasicResponse{
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
}
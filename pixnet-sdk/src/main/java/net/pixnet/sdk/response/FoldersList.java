package net.pixnet.sdk.response;

import java.util.ArrayList;

public class FoldersList extends BasicResponse{
    public int total;
    public int per_page;
    public int page;
    public ArrayList<Folder> folders;
}
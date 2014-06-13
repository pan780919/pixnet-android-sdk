package net.pixnet.sdk.response;

import java.util.ArrayList;

public class GroupList extends BasicResponse{
    public int total;
    public int per_page;
    public int page;
    public ArrayList<Group> friend_groups;
}

package net.pixnet.sdk.response;

import java.util.ArrayList;

/**
 * Group list
 */
public class GroupList extends BasicResponse{
    /**
     * Total counts of group
     */
    public int total;
    /**
     * Group shows per page
     */
    public int per_page;
    /**
     * Current page
     */
    public int page;
    /**
     * Group list
     */
    public ArrayList<Group> friend_groups;
}

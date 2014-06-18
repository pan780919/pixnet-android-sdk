package net.pixnet.sdk.response;

import java.util.ArrayList;

/**
 * FriendshipList
 */
public class FriendshipList extends BasicResponse{
    /**
     * List of friends
     */
    public ArrayList<Friendship> friend_pairs;
    /**
     * previous_cursor
     */
    public int previous_cursor;
    /**
     * next_cursor
     */
    public int next_cursor;
}
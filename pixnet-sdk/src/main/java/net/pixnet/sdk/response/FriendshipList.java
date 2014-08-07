package net.pixnet.sdk.response;

import java.util.ArrayList;

/**
 * FriendshipList
 */
public class FriendshipList extends BaseListResponse{
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

    public FriendshipList(String str) {
        super(str);
    }
}
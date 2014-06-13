package net.pixnet.sdk.response;

import java.util.ArrayList;

public class FriendshipList extends BasicResponse{
    public ArrayList<Friendship> friend_pairs;
    public int previous_cursor;
    public int next_cursor;
}
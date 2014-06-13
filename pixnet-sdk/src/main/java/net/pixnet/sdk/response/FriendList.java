package net.pixnet.sdk.response;

import java.util.ArrayList;

public class FriendList extends BasicResponse{
    public ArrayList<Friend> friend_pairs;
    public int previous_cursor;
    public int next_cursor;
}
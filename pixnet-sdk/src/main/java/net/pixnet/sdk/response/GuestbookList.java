package net.pixnet.sdk.response;

import java.util.ArrayList;

public class GuestbookList extends BasicResponse{
    public int total;
    public ArrayList<Article> articles;
    public int per_page;
    public int previous_cursor;
    public String next_cursor;
}
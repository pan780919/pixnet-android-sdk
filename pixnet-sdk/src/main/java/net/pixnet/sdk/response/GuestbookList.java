package net.pixnet.sdk.response;

import java.util.ArrayList;

/**
 * Guestbook list
 */
public class GuestbookList extends BasicResponse{
    /**
     * Total counts of guestbook articles
     */
    public int total;
    /**
     *Guestbook articles list
     */
    public ArrayList<Article> articles;
    /**
     * Articles show per page
     */
    public int per_page;
    /**
     * previous_cursor
     */
    public int previous_cursor;
    /**
     * next_cursor
     */
    public String next_cursor;
}
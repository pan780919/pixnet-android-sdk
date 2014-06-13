package net.pixnet.sdk.response;

import java.util.ArrayList;

/**
 * NewsList
 */
public class NewsList extends BasicResponse{
    /**
     * News list
     */
    public ArrayList<News> news;
    /**
     * next_before_time
     */
    public String next_before_time;
}
package net.pixnet.sdk.response;

import java.util.ArrayList;

/**
 * NewsList
 */
public class NewsList extends BaseListResponse{
    /**
     * News list
     */
    public ArrayList<News> news;
    /**
     * next_before_time
     */
    public String next_before_time;

    public NewsList(String str) {
        super(str);
    }
}
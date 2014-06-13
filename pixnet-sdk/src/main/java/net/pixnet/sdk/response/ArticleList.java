package net.pixnet.sdk.response;

import java.util.ArrayList;
/**
 * Created by Koi 2014/6/13.
 */
public class Article extends BasicResponse {
    /**
     * Article list
     */
    public ArrayList<Article> articles;
    /**
     * total articles
     */
    public int total;
    /**
     * article shows per page
     */
    public int per_page;
    /**
     * current page
     */
    public int page;
}
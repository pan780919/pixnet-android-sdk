package net.pixnet.sdk.response;

import java.util.ArrayList;
/**
 * ArticleList
 */
public class ArticleList extends BasicResponse {
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
package net.pixnet.sdk.response;

/**
 * Blog info
 */
public class Blog extends BasicResponse{
    /**
     * Blog url
     */
    public String link;
    /**
     * Blog title
     */
    public String name;
    /**
     * Blog description
     */
    public String description;
    /**
     * Blog keyword
     */
    public String keyword;
    /**
     * Blog category
     */
    public String site_category;
    /**
     * Blog visited counts
     */
    public Hits hits;
}
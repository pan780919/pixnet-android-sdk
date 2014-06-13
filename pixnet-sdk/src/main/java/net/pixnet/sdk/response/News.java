package net.pixnet.sdk.response;

import java.util.ArrayList;

public class News{
    /**
     * content_type
     */
    public String content_type;
    /**
     * blog_article
     */
    public Article blog_article;
    /**
     * news owner
     */
    public User user;
    /**
     * Album set
     */
    public Set album_set;
    /**
     * latest_elements list
     */
    public ArrayList<Element> latest_elements;
}
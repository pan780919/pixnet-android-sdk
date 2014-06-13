package net.pixnet.sdk.response;

import java.util.ArrayList;
/**
 * Created by Koi 2014/6/13.
 */
public class Article extends BasicResponse{
    /**
     * article id
     */
    public String id;
    /**
     * article public time
     */
    public String public_at;
    /**
     * site_category
     */
    public String site_category;
    /**
     * category
     */
    public String category;
    /**
     * category id
     */
    public String category_id;
    /**
     * article url
     */
    public String link;
    /**
     * article status : 0~7
     */
    public String status;
    /**
     * Tag list
     */
    public ArrayList<Tag> tags;
    /**
     * is_top
     */
    public String is_top;
    /**
     * comment_perm
     */
    public String comment_perm;
    /**
     * comment_hidden
     */
    public String comment_hidden;
    /**
     * Article title
     */
    public String title;
    /**
     * thumb
     */
    public String thumb;
    /**
     * Article info
     */
    public Info info;
    /**
     * User who created the article
     */
    public User user;
    /**
     * Article body
     */
    public String body;
    /**
     * Image list
     */
    public ArrayList<Image> images;
}
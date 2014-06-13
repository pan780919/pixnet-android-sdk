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
    public String link;
    public String status;
    public ArrayList<Tag> tags;
    public String is_top;
    public String comment_perm;
    public String comment_hidden;
    public String title;
    public String thumb;
    public Info info;
    public User user;
    public String body;
    public ArrayList<Image> images;
}
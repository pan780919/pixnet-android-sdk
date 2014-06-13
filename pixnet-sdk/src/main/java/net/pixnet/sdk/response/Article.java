package net.pixnet.sdk.response;

import java.util.ArrayList;
/**
 * Response for Article
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
     * article status :
     * 0:delete
     * 1:draft
     * 2:public
     * 3:secret
     * 4:hide
     * 5:friends
     * 7:joint
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
     * 0:close
     * 1:open for anyone
     * 2:open for logged user
     * 3:open for friends
     */
    public String comment_perm;
    /**
     * comment_hidden
     * 0:public
     * 1:hide
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
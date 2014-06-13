package net.pixnet.sdk.response;

import java.util.ArrayList;

/**
 * Comments list
 */
public class CommentsList extends BasicResponse{
    /**
     * Total comments
     */
    public int total;
    /**
     * Article that comments come from
     */
    public Article article;
    /**
     * List of comments
     */
    public ArrayList<Comment> comments;
    /**
     * Comments show per page
     */
    public int pre_page;
    /**
     * Current page
     */
    public int page;
}
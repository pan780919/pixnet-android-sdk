package net.pixnet.sdk.response;

public class Comment extends BasicResponse{
    /**
     * Comment id
     */
    public String id;
    /**
     * Comment title
     */
    public String title;
    /**
     * Comment body
     */
    public String body;
    /**
     * Comment writer's email
     */
    public String email;
    /**
     * Comment writer's main page
     */
    public String url;
    /**
     * Comment writer
     */
    public String author;
    /**
     * Comment's url
     */
    public String link;
    /**
     * Comment open rate
     */
    public int is_open;
    /**
     * Comment spam rate
     */
    public boolean is_spam;
    /**
     * Time comment created
     */
    public String created_at;
    /**
     * Comment author login type
     */
    public String author_login_type;
    /**
     * Client ip
     */
    public String client_ip;
    /**
     * User
     */
    public User user;
    /**
     * Reply
     */
    public Reply reply;
    /**
     * Article this comment comes from
     */
    public Article article;
    /**
     * Set this comment comes from
     */
    public Set set;
    /**
     * Element this comment comes from
     */
    public Element element;
}
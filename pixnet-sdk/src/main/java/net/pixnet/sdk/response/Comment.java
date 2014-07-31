package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Response for comment
 */
public class Comment extends BasicResponse {
    public Comment(String response) {
        formatJson(response);
    }

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
    public int is_spam;
    /**
     * Time comment created
     */
    public String created_at;
    /**
     * Comment author oAuth1Login type
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

    public void formatJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if (obj.has("message")) {
                message = obj.getString("message");
            }
            if (obj.has("error")) {
                error = obj.getString("error");
            }
            if (obj.has("id")) {
                id = obj.getString("id");
            }
            if (obj.has("title")) {
                title = obj.getString("title");
            }
            if (obj.has("body")) {
                body = obj.getString("body");
            }
            if (obj.has("email")) {
                email = obj.getString("email");
            }
            if (obj.has("url")) {
                url = obj.getString("url");
            }
            if (obj.has("author")) {
                author = obj.getString("author");
            }
            if (obj.has("link")) {
                link = obj.getString("link");
            }
            if (obj.has("is_open")) {
                is_open = obj.getInt("is_open");
            }
            if (obj.has("is_spam")) {
                is_spam = obj.getInt("is_spam");
            }
            if (obj.has("created_at")) {
                created_at = obj.getString("created_at");
            }
            if (obj.has("author_login_type")) {
                author_login_type = obj.getString("author_login_type");
            }
            if (obj.has("client_ip")) {
                client_ip = obj.getString("client_ip");
            }
            if (obj.has("user")) {
                user = new User(obj.getString("user"));
            }
            if (obj.has("reply")) {
                reply = new Reply(obj.getString("reply"));
            }
            if (obj.has("article")) {
                article = new Article(obj.getString("article"));
            }
            if(obj.has("element")){
                element = new Element(obj.getString("element"));
            }
        } catch (JSONException e) {

        }
    }
}
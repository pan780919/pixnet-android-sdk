package net.pixnet.sdk.response;

import net.pixnet.sdk.proxy.DataProxy;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Response for comment
 */
public class Comment extends BasicResponse {

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

    public String avatar;
    /**
     * Comment's url
     */
    public String link;
    /**
     * Comment open rate
     */
    public boolean is_open;
    /**
     * Comment spam rate
     */
    public boolean is_spam;
    /**
     * Time comment created
     */
    public long created_at;

    public long read_at;
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

    public Comment(String str) {
        super(str);
    }

    public Comment(JSONObject jo) {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);

        if(obj.has("comment"))
            obj=obj.getJSONObject("comment");

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
        if(obj.has("avatar"))
            avatar=obj.getString("avatar");
        if (obj.has("link")) {
            link = obj.getString("link");
        }
        if (obj.has("is_open")) {
            is_open = DataProxy.getJsonBoolean(obj, "is_open");
        }
        if (obj.has("is_spam")) {
            is_spam = DataProxy.getJsonBoolean(obj, "is_spam");
        }
        if (obj.has("created_at")) {
            created_at = obj.getLong("created_at");
        }
        if(obj.has("read_at"))
            read_at=obj.getLong("read_at");
        if (obj.has("author_login_type")) {
            author_login_type = obj.getString("author_login_type");
        }
        if (obj.has("client_ip")) {
            client_ip = obj.getString("client_ip");
        }
        if (obj.has("user")) {
            user = new User(obj);
        }
        if (obj.has("reply")) {
            reply = new Reply(obj.getJSONObject("reply"));
        }
        if (obj.has("article")) {
            article = new Article(obj);
        }
        if(obj.has("element")){
            element = new Element(obj.getJSONObject("element"));
        }
        if(obj.has("set"))
            set=new Set(obj);

        return obj;
    }
}
package net.pixnet.sdk.response;

import net.pixnet.sdk.proxy.DataProxy;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Guestbook
 */
public class Guestbook extends BasicResponse {

    public String id;
    public String  author;
    public String avatar;
    public String title;
    public String body;
    public String email;
    public String url;
    public String ip;
    public boolean is_open;
    public boolean is_spam;
    public long created_at;
    public User user;

    public Guestbook(JSONObject jo) {
        super(jo);
    }

    public Guestbook(String str) {
        super(str);
    }

    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);

        if(obj.has("article"))
            obj=obj.getJSONObject("article");

        if(obj.has("id"))
            id=obj.getString("id");
        if(obj.has("author"))
            author=obj.getString("author");
        if(obj.has("avatar"))
            avatar=obj.getString("avatar");
        if(obj.has("title"))
            title=obj.getString("title");
        if(obj.has("body"))
            body=obj.getString("body");
        if(obj.has("email"))
            email=obj.getString("email");
        if(obj.has("url"))
            url=obj.getString("url");
        if(obj.has("ip"))
            ip=obj.getString("ip");
        if(obj.has("is_open"))
            is_open= DataProxy.getJsonBoolean(obj, "is_open");
        if(obj.has("is_spam"))
            is_spam=DataProxy.getJsonBoolean(obj, "is_spam");
        if(obj.has("created_at"))
            created_at=obj.getLong("created_at");
        if(obj.has("user"))
            user=new User(obj);

        return obj;
    }
}
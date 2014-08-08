package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class News extends BasicResponse {
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
    public String next_before_time;

    public News(String response) {
        super(response);
    }

    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if (obj.has("content_type"))
            content_type = obj.getString("content_type");
        if (obj.has("blog_article"))
            blog_article = new Article(obj.getString("blog_article"));
        if (obj.has("user"))
            user = new User(obj.getJSONObject("user"));
        if (obj.has("album_set"))
            album_set = new Set(obj.getJSONObject("album_set"));
        if (obj.has("latest_elements")) {
            latest_elements = new ArrayList<Element>();
            JSONArray ja = obj.getJSONArray("latest_elements");
            for (int i = 0; i < ja.length(); i++) {
                latest_elements.add(new Element(ja.getString(i)));
            }
        }
        if (obj.has("next_before_time"))
            next_before_time = obj.getString("next_before_time");
        return obj;
    }
}
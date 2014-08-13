package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Analytics extends BasicResponse {
    public BlogAnalytics blog;

    public Analytics(JSONObject jo) {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo=super.parseJSON(jo);

        if(!jo.has("blog"))
            return jo;
        JSONObject blogData=jo.getJSONObject("blog");
        blog=new BlogAnalytics();
        blog.hit_difference=blogData.getInt("hit_difference");
        blog.hit_difference_percentage=blogData.getString("hit_difference_percentage");
        if(!blogData.has("hits"))
            return jo;
        blog.hits=new Hits(blogData.getJSONObject("hits"));

        return jo;
    }

    public class BlogAnalytics {
        public Hits hits;
        public int hit_difference;
        public String hit_difference_percentage;
        public List<Article> hot_articles;
    }
}

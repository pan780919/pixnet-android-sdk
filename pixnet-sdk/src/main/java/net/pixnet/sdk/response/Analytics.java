package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Analytics extends BasicResponse {
    public BlogAnalytics blog;

    public Analytics(JSONObject jo) {
        super(jo);
    }

    public Analytics(String str) {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo=super.parseJSON(jo);

        if(!jo.has("analytics"))
            return jo;
        jo=jo.getJSONObject("analytics");
        if(!jo.has("blog"))
            return jo;

        JSONObject blogData=jo.getJSONObject("blog");
        blog=new BlogAnalytics();
        blog.hit_difference=blogData.getInt("hit_difference");
        blog.hit_difference_percentage=blogData.getString("hit_difference_percentage");
        blog.hits=new Hits(blogData.getJSONObject("hits"));
        blog.hot_articles=new ArrayList<BasicArticleInfo>();

        return jo;
    }

    public class BlogAnalytics {
        public Hits hits;
        public int hit_difference;
        public String hit_difference_percentage;
        public List<BasicArticleInfo> hot_articles;
        public Statistics statistics;
        public List<Referer> referer;
    }

    public class BasicArticleInfo{
        public String id;
        public String title;
    }

    public class Statistics{
        public int highest;
        public List<StatisticData> data;
    }

    public class StatisticData{
        public String date;
        public int value;
    }

    public class Referer{
        public String source;
        public String type;
        public String keyword;
        public int count;
        public String url;
    }
}

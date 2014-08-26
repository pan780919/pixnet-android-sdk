package net.pixnet.sdk.response;

import org.json.JSONArray;
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
        if(blogData.has("hit_difference"))
            blog.hit_difference=blogData.getInt("hit_difference");
        if(blogData.has("hit_difference_percentage"))
            blog.hit_difference_percentage=blogData.getString("hit_difference_percentage");
        if(blogData.has("hits"))
            blog.hits=new Hits(blogData.getJSONObject("hits"));

        if(blogData.has("hot_articles")){
            JSONArray hotArticlesData=blogData.getJSONArray("hot_articles");
            int i=0, len=hotArticlesData.length();
            if(len>0){
                blog.hot_articles=new ArrayList<BasicArticleInfo>();
                while (i<len){
                    JSONObject articleData=hotArticlesData.getJSONObject(i);
                    BasicArticleInfo article=new BasicArticleInfo();
                    if(articleData.has("id"))
                        article.id=articleData.getString("id");
                    if(articleData.has("title"))
                        article.title=articleData.getString("title");
                    if(articleData.has("hit"))
                        article.hit=articleData.getInt("hit");
                    article.public_at=articleData.getInt("public_at")*1000l;
                    blog.hot_articles.add(article);
                    i++;
                }
            }
        }

        if(blogData.has("statistics")){
            JSONObject statisticsData=blogData.getJSONObject("statistics");
            blog.statistics=new Statistics();

            if(statisticsData.has("highest"))
                blog.statistics.highest=statisticsData.getInt("highest");
            if(statisticsData.has("data")) {
                JSONArray datas=statisticsData.getJSONArray("data");
                int i=0, len=datas.length();
                if(len>0){
                    blog.statistics.data = new ArrayList<StatisticData>();
                    while (i<len){
                        JSONObject data=datas.getJSONObject(i);
                        StatisticData statisticData=new StatisticData();
                        if(data.has("date"))
                            statisticData.date=data.getString("date");
                        if(data.has("value"))
                            statisticData.value=data.getInt("value");
                        blog.statistics.data.add(statisticData);
                        i++;
                    }
                }
            }
        }

        if(blogData.has("referer")){
            JSONArray referersData=blogData.getJSONArray("referer");
            int i=0, len=referersData.length();
            if(len>0){
                blog.referer=new ArrayList<Referer>();
                while (i<len){
                    JSONObject refererData=referersData.getJSONObject(i);
                    Referer referer=new Referer();
                    if(refererData.has("source"))
                        referer.source=refererData.getString("source");
                    if(refererData.has("type"))
                        referer.type=refererData.getString("type");
                    if(refererData.has("keyword"))
                        referer.keyword=refererData.getString("keyword");
                    if(refererData.has("count"))
                        referer.count=refererData.getInt("count");
                    if(refererData.has("url"))
                        referer.url=refererData.getString("url");
                    blog.referer.add(referer);
                    i++;
                }
            }
        }

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
        public int hit;
        public long public_at;
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

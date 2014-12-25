package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PeriodArticleList extends BaseListResponse {

    public List<HotArticleList> hotArticleLists;

    /**
     * YYYYmmdd
     */
    public String start;

    /**
     * YYYYmmdd
     */
    public String end;

    public PeriodArticleList(String str) throws JSONException {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);

        if(obj.has("start"))
            start=obj.getString("start");
        if(obj.has("end"))
            end=obj.getString("end");
        if(obj.has("hot_articles")){
            hotArticleLists=new ArrayList<HotArticleList>();
            JSONArray ja=obj.getJSONArray("hot_articles");
           for(int i=0;i<ja.length();i++)
               hotArticleLists.add(new HotArticleList(ja.getJSONObject(i)));
        }


        return obj;
    }

    public class HotArticleList{
        public String time;
        public List<Article> articles;
        public HotArticleList(JSONObject obj) throws JSONException {
            if(obj.has("time"))
                time=obj.getString("time");
            if(obj.has("articles")){
                articles = new ArrayList<Article>();
                JSONArray ja = obj.getJSONArray("articles");
                for(int i =0;i<ja.length();i++)
                    articles.add(new Article(ja.getJSONObject(i)));
            }
        }
    }
}
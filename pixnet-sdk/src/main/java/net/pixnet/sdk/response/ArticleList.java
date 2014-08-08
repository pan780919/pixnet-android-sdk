package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * ArticleList
 */
public class ArticleList extends BaseListResponse {
    /**
     * Article list
     */
    public ArrayList<Article> articles;
    /**
     * total articles
     */
    public int total;
    /**
     * article shows per page
     */
    public int per_page;
    /**
     * current page
     */
    public int page;

    public ArticleList(JSONObject jo) {
        super(jo);
    }

    public ArticleList(String str) {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if(obj.has("total")){
            total = obj.getInt("total");
        }
        if(obj.has("per_page")){
            per_page = obj.getInt("per_page");
        }
        if(obj.has("page")){
            page = obj.getInt("page");
        }
        if(obj.has("articles")){
            articles = new ArrayList<Article>();
            JSONArray ja = obj.getJSONArray("articles");
            for(int i =0;i<ja.length();i++){
                articles.add(i,new Article(ja.getString(i)));
            }
        }
        return obj;
    }
}
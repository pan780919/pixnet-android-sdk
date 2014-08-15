package net.pixnet.sdk.response;

import net.pixnet.sdk.utils.Helper;

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

    public ArticleList(String str) {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if(obj.has("articles")){
            articles = new ArrayList<Article>();
            JSONArray ja = obj.getJSONArray("articles");
            for(int i =0;i<ja.length();i++){
                articles.add(new Article(ja.getJSONObject(i)));
            }
        }
        return obj;
    }
}
package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Guestbook list
 */
public class GuestbookList extends BaseListResponse{
    /**
     * Total counts of guestbook articles
     */
    public int total;
    /**
     *Guestbook articles list
     */
    public ArrayList<Article> articles;
    /**
     * Articles show per page
     */
    public int per_page;
    /**
     * previous_cursor
     */
    public int previous_cursor;
    /**
     * next_cursor
     */
    public String next_cursor;
    public GuestbookList(JSONObject jo) {
        super(jo);
    }
    public GuestbookList(String str) {
        super(str);
    }
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if(obj.has("articles")){
            JSONArray ja = obj.getJSONArray("articles");
            articles = new ArrayList<Article>();
            for(int i=0;i<ja.length();i++){
                articles.add(new Article(ja.getString(i)));
            }
        }
        if(obj.has("previous_cursor")){
            previous_cursor = obj.getInt("previous_cursor");
        }
        if(obj.has("next_cursor")){
            next_cursor = obj.getString("next_cursor");
        }
        return obj;
    }
}
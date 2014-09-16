package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * NewsList
 */
public class NewsList extends BaseListResponse{
    /**
     * News list
     */
    public ArrayList<News> news;
    /**
     * next_before_time
     */
    public String next_before_time;

    public NewsList(String str) throws JSONException {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        if(jo.has("next_before_time"))
            next_before_time = jo.getString("next_before_time");
        if(jo.has("news")){
            news = new ArrayList<News>();
            JSONArray ja = jo.getJSONArray("news");
            for(int i =0;i<ja.length();i++){
                news.add(new News(ja.getJSONObject(i)));
            }
        }
        return super.parseJSON(jo);
    }
}
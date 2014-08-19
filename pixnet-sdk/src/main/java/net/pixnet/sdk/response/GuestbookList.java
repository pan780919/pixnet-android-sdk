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
     *Guestbook articles list
     */
    public ArrayList<Guestbook> articles;
    /**
     * Articles show per page
     */
    public String previous_cursor;
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
            articles = new ArrayList<Guestbook>();
            for(int i=0;i<ja.length();i++){
                articles.add(new Guestbook(ja.getJSONObject(i)));
            }
        }
        if(obj.has("previous_cursor")){
            previous_cursor = obj.getString("previous_cursor");
        }
        if(obj.has("next_cursor")){
            next_cursor = obj.getString("next_cursor");
        }
        return obj;
    }
}
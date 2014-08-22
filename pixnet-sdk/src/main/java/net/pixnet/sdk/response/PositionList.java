package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PositionList extends BasicResponse {

    public List<Position> blog;
    public List<Position> article;

    public PositionList(String str) {
        super(str);
    }

    public PositionList(JSONObject jo) {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);

        if(jo.has("positions") && !jo.isNull("positions"))
            jo=jo.getJSONObject("positions");

        if(jo.has("blog") && !jo.isNull("blog")){
            JSONArray ja=jo.getJSONArray("blog");
            int i=0, len=ja.length();
            if(len>0)
                blog=new ArrayList<Position>();
            while (i<len){
                blog.add(new Position(ja.getJSONObject(i)));
                i++;
            }
        }
        if(jo.has("article") && !jo.isNull("article")){
            JSONArray ja=jo.getJSONArray("article");
            int i=0, len=ja.length();
            if(len>0)
                article=new ArrayList<Position>();
            while (i<len){
                article.add(new Position(ja.getJSONObject(i)));
                i++;
            }
        }

        return jo;
    }
}

package net.pixnet.sdk.response;

import net.pixnet.sdk.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tags extends BasicResponse {

    public List<String> hot, related;

    public Tags(String str) {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);

        if(jo.has("tags"))
            jo=jo.getJSONObject("tags");

        JSONArray ja;
        if(jo.has("hot")) {
            ja = jo.getJSONArray("hot");
            int i = 0, len = ja.length();
            if(len>0)
                hot=new ArrayList<String>();
            while (i<len){
                Helper.log(ja.getString(i));
                hot.add(ja.getString(i));
                i++;
            }
        }
        if(jo.has("related")) {
            ja = jo.getJSONArray("related");
            int i = 0, len = ja.length();
            if(len>0)
                related=new ArrayList<String>();
            while (i<len){
                related.add(ja.getString(i));
                i++;
            }
        }

        return jo;
    }
}

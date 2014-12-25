package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SetList extends BaseListResponse{
    public List<Set> sets;

    public SetList(String str) throws JSONException {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo=super.parseJSON(jo);

        if(jo.has("sets")){
            JSONArray ja=jo.getJSONArray("sets");
            int i=0, len=ja.length();
            if(len<1)
                return jo;
            sets=new ArrayList<Set>();
            while(i<len){
                Set set=new Set(ja.getJSONObject(i));
                sets.add(set);
                i++;
            }
        }

        return jo;
    }
}
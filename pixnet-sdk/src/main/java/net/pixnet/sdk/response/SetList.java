package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SetList extends BaseListResponse{
    public List<Set> sets;

    public SetList(String str) {
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
                JSONObject item;
                try {
                    item=ja.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                    item=null;
                }
                if(item==null){
                    i++;
                    continue;
                }
                Set set=new Set(item);
                sets.add(set);
                i++;
            }
        }

        return jo;
    }
}
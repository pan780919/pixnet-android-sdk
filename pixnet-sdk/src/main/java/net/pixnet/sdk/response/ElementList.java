package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Elements list
 */
public class ElementList extends BaseListResponse{

    public Set set;
    public List<Element> elements;

    public ElementList(String str) {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);

        if(jo.has("set"))
            set=new Set(jo.getJSONObject("set"));
        if(jo.has("elements")){
            JSONArray ja=jo.getJSONArray("elements");
            int i=0, len=ja.length();
            if(len>0)
                elements=new ArrayList<Element>();
            while(i<len){
                elements.add(new Element(ja.getJSONObject(i)));
                i++;
            }
        }
        return jo;
    }
}
package net.pixnet.sdk.response;

import net.pixnet.sdk.proxy.DataProxy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Position extends BasicResponse {
    public String category;
    public int revenue;
    public double difference;
    public double difference_percentage;
    public int imp;
    public int click;
    public int ctr;
    public History history;
    public String id;
    public String name;
    public boolean enabled;
    public boolean modifable;
    public boolean fixed_ad_box;

    public Position(String str) throws JSONException {
        super(str);
    }

    public Position(JSONObject jo) throws JSONException {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);
        if(jo.has("position") && !jo.isNull("position"))
            jo=jo.getJSONObject("position");
        if(jo.has("category") && !jo.isNull("category"))
            category=jo.getString("category");
        if(jo.has("revenue") && !jo.isNull("revenue"))
            revenue=jo.getInt("revenue");
        if(jo.has("difference") && !jo.isNull("difference"))
            difference=DataProxy.getJsonDouble(jo, "difference");
        if(jo.has("difference_percentage") && !jo.isNull("difference_percentage"))
            difference_percentage=DataProxy.getJsonDouble(jo, "difference_percentage");
        if(jo.has("imp") && !jo.isNull("imp"))
            imp=jo.getInt("imp");
        if(jo.has("click") && !jo.isNull("click"))
            click=jo.getInt("click");
        if(jo.has("ctr") && !jo.isNull("ctr"))
            ctr=jo.getInt("ctr");
        if(jo.has("history") && !jo.isNull("history"))
            history=new History(jo.getJSONObject("history"));
        if(jo.has("id") && !jo.isNull("id"))
            id=jo.getString("id");
        if(jo.has("name") && !jo.isNull("name"))
            name=jo.getString("name");
        if(jo.has("enabled") && !jo.isNull("enabled"))
            enabled= DataProxy.getJsonBoolean(jo, "enabled");
        if(jo.has("modifable") && !jo.isNull("modifable"))
            modifable=DataProxy.getJsonBoolean(jo, "modifable");
        if(jo.has("fixed_ad_box") && !jo.isNull("fixed_ad_box"))
            fixed_ad_box=DataProxy.getJsonBoolean(jo, "fixed_ad_box");
        return jo;
    }

    public class History{
        public List<Date> date;
        public int highest;
        public History(JSONObject jo) throws JSONException {
            if(jo.has("date") && !jo.isNull("date")){
                JSONArray datesData=jo.getJSONArray("date");
                int i=0, len=datesData.length();
                if(len>0){
                    date=new ArrayList<Date>();
                    while (i<len){
                        date.add(new Date(datesData.getJSONObject(i)));
                        i++;
                    }
                }
            }
            if(jo.has("highest") && !jo.isNull("highest"))
                highest=jo.getInt("highest");
        }
    }

    public class Date{
        public String date;
        public double value;
        public Date(JSONObject jo) throws JSONException {
            if(jo.has("date") && !jo.isNull("date"))
                date=jo.getString("date");
            if(jo.has("value"))
                value=DataProxy.getJsonDouble(jo, "value");
        }
    }
}

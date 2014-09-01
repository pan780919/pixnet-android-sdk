package net.pixnet.sdk.response;

import net.pixnet.sdk.proxy.DataProxy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationList extends BasicResponse{

    public List<Notification> notifications;

    public NotificationList(String str) {
        super(str);
    }

    public NotificationList(JSONObject jo) {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);
        if(!jo.has("notifications") || jo.isNull("notifications"))
            return jo;
        JSONArray notifyDataAry=jo.getJSONArray("notifications");
        if(notifyDataAry==null)
            return jo;
        int i=0, len=notifyDataAry.length();
        if(len>0){
            notifications=new ArrayList<Notification>();
            while (i<len){
                JSONObject notifyData=notifyDataAry.getJSONObject(i);
                Notification notification=new Notification();
                if(notifyData.has("id"))
                    notification.id=notifyData.getString("id");
                if(notifyData.has("title"))
                    notification.title=notifyData.getString("title");
                if(notifyData.has("is_read"))
                    notification.is_read= DataProxy.getJsonBoolean(notifyData, "is_read");
                if(notifyData.has("body"))
                    notification.body=notifyData.getString("body");
                if(notifyData.has("link"))
                    notification.link=notifyData.getString("link");
                if(notifyData.has("icon"))
                    notification.icon=notifyData.getString("icon");
                if(notifyData.has("date"))
                    notification.date=notifyData.getLong("date");
                notification.users=null;
                notifications.add(notification);
                i++;
            }
        }
        return jo;
    }

    public class Notification{
        public String id;
        public String title;
        public boolean is_read;
        public String body;
        public String link;
        public String icon;
        public long date;
        public User users;
    }

}


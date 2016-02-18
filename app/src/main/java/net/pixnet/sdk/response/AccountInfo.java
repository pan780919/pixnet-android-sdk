package net.pixnet.sdk.response;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.utils.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AccountInfo extends BasicResponse {

    public static enum Gender{
        F,
        M
    }

    public String area;
    public String sub_area;
    public String name;
    public String display_name;
    public String avatar;
    public String cavatar;
    public String link;
    public boolean is_vip;
    public boolean has_ad;
    public Gender gender;
    public String birth;
    public String identity;
    public long reg_date;
    public long last_login;
    public String email;
    public String address;
    public String phone;
    public String education;
    public VIPInfo vip_info;
    public Quota quota;
    public Preferences preferences;
    public BlogInfo blog;
    public NotificationList notifications;
    public MIB mib;
    public Analytics analytics;

    public AccountInfo(String str) throws JSONException {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);

        if(!jo.has("account"))
            return jo;
        jo=jo.getJSONObject("account");

        if(jo.has("area"))
            area=jo.getString("area");
        if(jo.has("sub_area"))
            sub_area=jo.getString("sub_area");
        if(jo.has("name"))
            name=jo.getString("name");
        if(jo.has("display_name"))
            display_name=jo.getString("display_name");
        if(jo.has("avatar"))
            avatar=jo.getString("avatar");
        if(jo.has("cavatar"))
            cavatar=jo.getString("cavatar");
        if(jo.has("link"))
            link=jo.getString("link");
        if(jo.has("is_vip"))
            is_vip= DataProxy.getJsonBoolean(jo, "is_vip");
        if(jo.has("has_ad"))
            has_ad=DataProxy.getJsonBoolean(jo, "has_ad");
        if(jo.has("gender"))
            gender=jo.getString("gender").equals("F")?Gender.F:Gender.M;
        if(jo.has("birth"))
            birth=jo.getString("birth");
        if(jo.has("identity"))
            identity=jo.getString("identity");
        if(jo.has("reg_date"))
            reg_date=jo.getLong("reg_date");
        if(jo.has("last_login"))
            last_login=jo.getLong("last_login");
        if(jo.has("email"))
            email=jo.getString("email");
        if(jo.has("address"))
            address=jo.getString("address");
        if(jo.has("phone"))
            phone=jo.getString("phone");
        if(jo.has("education"))
            education=jo.getString("education");

        if(jo.has("vip_info")) {
            JSONObject vipData=jo.getJSONObject("vip_info");
            vip_info = new VIPInfo();
            if(vipData.has("name"))
                vip_info.name=vipData.getString("name");
            if(vipData.has("has_ad"))
                vip_info.has_ad=DataProxy.getJsonBoolean(vipData, "has_ad");
            if(vipData.has("expire_date"))
                vip_info.expire_date=vipData.getString("expire_date");
        }
        if(jo.has("quota")){
            JSONObject quotaData=jo.getJSONObject("quota");
            quota=new Quota();
            if(quotaData.has("album_space_quota"))
                quota.album_space_quota=quotaData.getLong("album_space_quota");
            if(quotaData.has("album_space_usage"))
                quota.album_space_usage=quotaData.getLong("album_space_usage");
            if(quotaData.has("album_space_left"))
                quota.album_space_left=quotaData.getLong("album_space_left");
            if(quotaData.has("album_upload_quota"))
                quota.album_upload_quota=quotaData.getLong("album_upload_quota");
            if(quotaData.has("album_upload_usage"))
                quota.album_upload_usage=quotaData.getLong("album_upload_usage");
            if(quotaData.has("album_upload_left"))
                quota.album_upload_left=quotaData.getLong("album_upload_left");
        }
        if(jo.has("preferences")) {
            JSONObject prefercenceData = jo.getJSONObject("preferences");
            preferences = new Preferences();
            if(prefercenceData.has("enews")){
                JSONObject enewsData=prefercenceData.getJSONObject("enews");
                preferences.enews=new Enews();
                if(enewsData.has("news_letter"))
                    preferences.enews.news_letter=DataProxy.getJsonBoolean(enewsData, "news_letter");
                if(enewsData.has("event_letter"))
                    preferences.enews.event_letter=DataProxy.getJsonBoolean(enewsData, "event_letter");
                if(enewsData.has("new_feature_notify"))
                    preferences.enews.new_feature_notify=DataProxy.getJsonBoolean(enewsData, "new_feature_notify");
            }
            if(prefercenceData.has("functions")){
                JSONObject functionsData=prefercenceData.getJSONObject("functions");
                if(functionsData.has("functions"))
                    preferences.functions=new Functions();
                if(functionsData.has("functions"))
                    preferences.functions.new_feature_notify=DataProxy.getJsonBoolean(functionsData, "new_feature_notify");
                if(functionsData.has("functions"))
                    preferences.functions.hide_navigation_bar=DataProxy.getJsonBoolean(functionsData,"hide_navigation_bar");
            }
        }
        if(jo.has("notifications"))
            notifications=new NotificationList(jo);
        if(jo.has("blog"))
            blog = new BlogInfo(jo);
        if(jo.has("mib"))
            mib=new MIB(jo);
        if(jo.has("analytics"))
            analytics=new Analytics(jo);

        return jo;
    }

    public class VIPInfo{
        public String name;
        public boolean has_ad;
        public String expire_date;
    }

    public class Quota{
        public long album_space_quota;
        public long album_space_usage;
        public long album_space_left;
        public long album_upload_quota;
        public long album_upload_usage;
        public long album_upload_left;
    }

    public class Preferences{
        public Enews enews;
        public Functions functions;
    }

    public class Enews{
        public boolean news_letter;
        public boolean event_letter;
        public boolean new_feature_notify;
    }

    public class Functions {
        public boolean new_feature_notify;
        public boolean hide_navigation_bar;
    }

}

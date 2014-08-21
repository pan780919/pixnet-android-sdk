package net.pixnet.sdk.response;

import net.pixnet.sdk.proxy.DataProxy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MIB extends BasicResponse {
    public int applied;
    public int status;
    public int payable_revenue;
    public boolean payable;
    public UserInfo account;
    public List<Position> blog;
    public List<Position> article;
    public List<Revenue> revenue;

    public static enum PositionCategory{
        blog,
        article
    }

    public MIB(String str) {
        super(str);
    }

    public MIB(JSONObject jo) {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);

        if(!jo.has("mib"))
            return jo;
        jo=jo.getJSONObject("mib");

        if(jo.has("applied") && !jo.isNull("applied"))
            applied=jo.getInt("applied");
        if(jo.has("status") && !jo.isNull("status"))
            status=jo.getInt("status");
        if(jo.has("payable_revenue") && !jo.isNull("payable_revenue"))
            payable_revenue=jo.getInt("payable_revenue");
        if(jo.has("payable") && !jo.isNull("payable"))
            payable= DataProxy.getJsonBoolean(jo, "payable");
        if(jo.has("blog") && !jo.isNull("blog")){
            JSONObject blogData=jo.getJSONObject("blog");
            if(blogData.has("revenue")){
                JSONArray revenues=blogData.getJSONArray("revenue");
                int i=0, len=revenues.length();
                if(len>0){
                    revenue=new ArrayList<Revenue>();
                    while (i<len){
                        revenue.add(new Revenue(revenues.getJSONObject(i)));
                        i++;
                    }
                }
            }
            if(blogData.has("positions")){
                JSONObject posData=blogData.getJSONObject("positions");
                if(posData.has("blog") && !jo.isNull("blog")){
                    JSONArray ja=posData.getJSONArray("blog");
                    int i=0, len=ja.length();
                    if(len>0){
                        blog=new ArrayList<Position>();
                        while (i<len){
                            blog.add(new Position(ja.getJSONObject(i)));
                            i++;
                        }
                    }
                }
                if(posData.has("articles") && !jo.isNull("articles")){
                    JSONArray ja=posData.getJSONArray("articles");
                    int i=0, len=ja.length();
                    if(len>0){
                        article=new ArrayList<Position>();
                        while (i<len){
                            article.add(new Position(ja.getJSONObject(i)));
                            i++;
                        }
                    }
                }
            }
        }

        if(jo.has("account") && !jo.isNull("account"))
            account=new UserInfo(jo.getJSONObject("account"));

        return jo;
    }

    public class UserInfo{
        public String id_number;
        public boolean id_image_uploaded;
        public String email;
        public String cellphone;
        public String mail_address;
        public String domicile;
        public String invalid_fields;

        public UserInfo(JSONObject jo) throws JSONException {
            if(jo.has("id_number") && !jo.isNull("id_number"))
                id_number=jo.getString("id_number");
            if(jo.has("id_image_uploaded") && !jo.isNull("id_image_uploaded"))
                id_image_uploaded=DataProxy.getJsonBoolean(jo, "id_image_uploaded");
            if(jo.has("email") && !jo.isNull("email"))
                email=jo.getString("email");
            if(jo.has("cellphone") && !jo.isNull("cellphone"))
                cellphone=jo.getString("cellphone");
            if(jo.has("mail_address") && !jo.isNull("mail_address"))
                mail_address=jo.getString("mail_address");
            if(jo.has("domicile") && !jo.isNull("domicile"))
                domicile=jo.getString("domicile");
            if(jo.has("invalid_fields") && !jo.isNull("invalid_fields"))
                invalid_fields=jo.getString("invalid_fields");
        }
    }

    public class Revenue{
        public String year_month;
        public int amount;
        public int difference;
        public double difference_percentage;
        public int click;
        public int imp;

        public Revenue(JSONObject jo) throws JSONException {
            if(jo.has("year_month"))
                year_month=jo.getString("year_month");
            if(jo.has("amount"))
                amount=jo.getInt("amount");
            if(jo.has("difference"))
                difference=jo.getInt("difference");
            if(jo.has("difference_percentage"))
                difference_percentage=jo.getDouble("difference_percentage");
            if(jo.has("click"))
                click=jo.getInt("click");
            if(jo.has("imp"))
                imp=jo.getInt("imp");
        }
    }
}

package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class MIB extends BasicResponse {
    public int applied;
    public int status;
    public int payable_revenue;
    public boolean payable;
    public BlogInfo blog;
    public UserInfo account;

    public MIB(JSONObject jo) {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);

        if(jo.has("applied"))
            applied=jo.getInt("applied");
        if(jo.has("status"))
            status=jo.getInt("status");
        if(jo.has("payable_revenue"))
            payable_revenue=jo.getInt("payable_revenue");
        if(jo.has("payable"))
            payable=jo.getBoolean("payable");
        if(jo.has("blog"))
            blog=new BlogInfo(jo);

        if(jo.has("account")){
            JSONObject accountData=jo.getJSONObject("account");
            account=new UserInfo();
            if(accountData.has("id_number"))
                account.id_number=accountData.getString("id_number");
            if(accountData.has("id_image_uploaded"))
                account.id_image_uploaded=accountData.getBoolean("id_image_uploaded");
            if(accountData.has("email"))
                account.email=accountData.getString("email");
            if(accountData.has("cellphone"))
                account.cellphone=accountData.getString("cellphone");
            if(accountData.has("mail_address"))
                account.mail_address=accountData.getString("mail_address");
            if(accountData.has("domicile"))
                account.domicile=accountData.getString("domicile");
            if(accountData.has("invalid_fields"))
                account.invalid_fields=accountData.getString("invalid_fields");
        }


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
    }
}

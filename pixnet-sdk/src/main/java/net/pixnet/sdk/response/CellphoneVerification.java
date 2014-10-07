package net.pixnet.sdk.response;

import net.pixnet.sdk.proxy.DataProxy;

import org.json.JSONException;
import org.json.JSONObject;

public class CellphoneVerification extends BasicResponse {

    public boolean cellphone_verified;
    public Cellphone cellphone;

    public CellphoneVerification(String str) throws JSONException {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);

        if(jo.has("cellphone_verified"))
            cellphone_verified= DataProxy.getJsonBoolean(jo, "cellphone_verified");
        if(jo.has("cellphone"))
            cellphone=new Cellphone(jo.getJSONObject("cellphone"));

        return obj;
    }

    public class Cellphone {

        public String cellphone;
        public String calling_code;

        public Cellphone(JSONObject jo) throws JSONException {
            if(jo==null)
                return;
            if(jo.has("cellphone"))
                cellphone=jo.getString("cellphone");
            if(jo.has("calling_code"))
                calling_code=jo.getString("calling_code");
        }
    }
}
package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class AlbumMainPage extends BasicResponse {

    public MainPhoto mainphoto;
    public PhotoWall photowall;

    public AlbumMainPage(String str) throws JSONException {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);

        if(jo.has("mainphoto"))
            mainphoto=new MainPhoto(jo.getJSONObject("mainphoto"));
        if(jo.has("photowall"))
            photowall=new PhotoWall(jo.getJSONObject("photowall"));

        return jo;
    }

    public class MainPhoto{
        public String set_id;
        public String element_id;
        public MainPhoto(JSONObject jo) throws JSONException {
            if(jo==null)
                return;
            if(jo.has("set_id"))
                set_id=jo.getString("set_id");
            if(jo.has("element_id"))
                element_id=jo.getString("element_id");
        }

    }

    public class PhotoWall{
        public String type;
        public PhotoWall(JSONObject jo) throws JSONException {
            if(jo==null)
                return;
            if(jo.has("type"))
                type=jo.getString("type");
        }
    }
}

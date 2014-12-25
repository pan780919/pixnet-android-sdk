package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SetAndFolderList extends BaseListResponse {

    public List<AlbumContainer> setfolders;

    public SetAndFolderList(String str) throws JSONException {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);

        if(jo.has("setfolders")){
            JSONArray ja=jo.getJSONArray("setfolders");
            setfolders=new ArrayList<AlbumContainer>();
            int i=0, len=ja.length();
            while(i<len){
                AlbumContainer album;
                JSONObject d=ja.getJSONObject(i);
                if(d.getString("type").equals("folder"))
                    album=new Folder(d);
                else if(d.getString("type").equals("set"))
                    album=new Set(d);
                else album=null;
                setfolders.add(album);
                i++;
            }
        }

        return jo;
    }
}

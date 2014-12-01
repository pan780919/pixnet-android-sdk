package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FolderList extends BaseListResponse{
    public ArrayList<Folder> folders;

    public FolderList(String str) throws JSONException {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);

        if(jo.has("folders")){
            folders=new ArrayList<Folder>();
            JSONArray folderArray=jo.getJSONArray("folders");
            int i=0, len=folderArray.length();
            while(i<len){
                Folder folder=new Folder(folderArray.getJSONObject(i));
                folders.add(folder);
                i++;
            }
        }

        return jo;
    }
}
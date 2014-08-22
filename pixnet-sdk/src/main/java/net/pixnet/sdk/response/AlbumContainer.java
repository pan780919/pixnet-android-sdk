package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class AlbumContainer extends BasicResponse {

    public static enum Type{
        FOLDER,
        SET
    }

    public String id;
    /**
     * folder or set
     */
    public Type type;
    public String title;
    /**
     * Cover's thumbnail url
     */
    public String thumb;
    public int position;
    public List<Tag> tags;
    /**
     * timestamp
     */
    public long created_at;
    /**
     * Author information
     */
    public User user;
    /**
     * description
     */
    public String description;

    public AlbumContainer(String str) {
        super(str);
    }

    protected AlbumContainer(JSONObject jo) {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        jo = super.parseJSON(jo);
        JSONArray ja;

        if(jo.has("type")){
            if(jo.getString("type").equals("set"))
                type=Type.SET;
            else if(jo.getString("type").equals("folder"))
                type=Type.FOLDER;
        }
        if(jo.has("id"))
            id=jo.getString("id");
        if(jo.has("title"))
            title=jo.getString("title");
        if(jo.has("thumb"))
            thumb=jo.getString("thumb");
        if(jo.has("positoin"))
            position=jo.getInt("positoin");
        if(jo.has("create_at"))
            created_at=jo.getInt("created_at")*1000l;
        if(jo.has("user"))
            user=new User(jo);
        if(jo.has("description"))
            description=jo.getString("description");
        if(jo.has("tags")){
            ja=jo.getJSONArray("tags");
            int i=0, len=ja.length();
            if(len<1)
                return jo;
            tags=new ArrayList<Tag>();
            while (i<len){
                Tag tag=new Tag(ja.getJSONObject(i));
                tags.add(tag);
                i++;
            }
        }

        return jo;
    }
}

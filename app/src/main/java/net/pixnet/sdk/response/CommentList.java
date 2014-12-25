package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Comments list
 */
public class CommentList extends BaseListResponse{
    /**
     * Article that comments come from
     */
    public Article article;
    /**
     * List of comments
     */
    public List<Comment> comments;

    public CommentList(String str) throws JSONException {
        super(str);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if(obj.has("article")){
            article = new Article(obj.getJSONObject("article"));
        }
        if(obj.has("comments")){
            comments = new ArrayList<>();
            JSONArray aobj = obj.getJSONArray("comments");
            for(int i =0;i<aobj.length();i++){
                comments.add(new Comment(aobj.getJSONObject(i)));
            }
        }
        return obj;
    }
}
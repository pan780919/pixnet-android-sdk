package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Comments list
 */
public class CommentList extends BasicResponse{
    CommentList(String response){
        formatJson(response);
    }
    /**
     * Total comments
     */
    public int total;
    /**
     * Article that comments come from
     */
    public Article article;
    /**
     * List of comments
     */
    public ArrayList<Comment> comments;
    /**
     * Comments show per page
     */
    public int per_page;
    /**
     * Current page
     */
    public int page;
    private void formatJson(String response){
        try{
            JSONObject obj = new JSONObject(response);
            if (obj.has("message")) {
                message = obj.getString("message");
            }
            if (obj.has("error")) {
                error = obj.getString("error");
            }
            if(obj.has("total")){
                total = obj.getInt("total");
            }
            if(obj.has("article")){
                article = new Article(obj.getString("article"));
            }
            if(obj.has("comments")){
                comments = new ArrayList<Comment>();
                JSONArray aobj = obj.getJSONArray("comments");
                for(int i =0;i<aobj.length();i++){
                    comments.add(new Comment(aobj.getString(i)));
                }
            }
            if(obj.has("per_page")){
                per_page = obj.getInt("per_page");
            }
        }catch(JSONException e){

        }
    }
}
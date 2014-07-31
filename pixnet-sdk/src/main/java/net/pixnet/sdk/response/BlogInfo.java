package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Blog info
 */
public class BlogInfo extends BasicResponse{
    public BlogInfo(String response){
        formatJson(response);
    }
    /**
     * Blog url
     */
    public String link;
    /**
     * Blog title
     */
    public String name;
    /**
     * Blog description
     */
    public String description;
    /**
     * Blog keyword
     */
    public String keyword;
    /**
     * Blog category
     */
    public String site_category;
    /**
     * Blog visited counts
     */
    public Hits hits;
    private void formatJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if (obj.has("message")) {
                message = obj.getString("message");
            }
            if (obj.has("error")) {
                error = obj.getString("error");
            }
            if(obj.has("blog")){
                JSONObject blog = obj.getJSONObject("blog");
                if(blog.has("link")){
                    link = blog.getString("link");
                }
                if(blog.has("name")){
                    name = blog.getString("name");
                }
                if(blog.has("description")){
                    description = blog.getString("description");
                }
                if(blog.has("keyword")){
                    keyword = blog.getString("keyword");
                }
                if(blog.has("site_category")){
                    site_category = blog.getString("site_category");
                }
                if(blog.has("hits")){
                    hits = new Hits(blog.getString("hits"));
                }
            }
        } catch (JSONException e) {
        }
    }
}
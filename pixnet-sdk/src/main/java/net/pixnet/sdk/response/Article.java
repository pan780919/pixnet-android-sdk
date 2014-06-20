package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Response for Article
 */
public class Article extends BasicResponse {

    public Article(String response) {
        formatJson(response);
    }

    /**
     * article id
     */
    public String id;
    /**
     * article public time
     */
    public String public_at;
    /**
     * site_category
     */
    public String site_category;
    /**
     * category
     */
    public String category;
    /**
     * category id
     */
    public String category_id;
    /**
     * article url
     */
    public String link;
    /**
     * article status :
     * 0:delete
     * 1:draft
     * 2:public
     * 3:secret
     * 4:hide
     * 5:friends
     * 7:joint
     */
    public String status;
    /**
     * Tag list
     */
    public ArrayList<Tag> tags;
    /**
     * is_top
     */
    public String is_top;
    /**
     * comment_perm
     * 0:close
     * 1:open for anyone
     * 2:open for logged user
     * 3:open for friends
     */
    public String comment_perm;
    /**
     * comment_hidden
     * 0:public
     * 1:hide
     */
    public String comment_hidden;
    /**
     * Article title
     */
    public String title;
    /**
     * thumb
     */
    public String thumb;
    /**
     * Article info
     */
    public Info info;
    /**
     * User who created the article
     */
    public User user;
    /**
     * Article body
     */
    public String body;
    /**
     * Image list
     */
    public ArrayList<Image> images;

    public void formatJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if (obj.has("message")) {
                message = obj.getString("message");
            }
            if (obj.has("error")) {
                error = obj.getString("error");
            }
            if (obj.has("article")) {
                JSONObject article = obj.getJSONObject("article");
                if (article.has("id")) {
                    id = article.getString("id");
                }
                if (article.has("public_at")) {
                    public_at = article.getString("public_at");
                }
                if (article.has("site_category")) {
                    site_category = article.getString("site_category");
                }
                if (article.has("category")) {
                    category = article.getString("category");
                }
                if (article.has("category_id")) {
                    category_id = article.getString("category_id");
                }
                if (article.has("link")) {
                    link = article.getString("link");
                }
                if (article.has("status")) {
                    status = article.getString("status");
                }
                if (article.has("tags")) {
                    JSONArray taglist = article.getJSONArray("tags");
                    tags = new ArrayList<Tag>();
                    for (int i = 0; i < taglist.length(); i++) {
                        JSONObject tagObject = taglist.getJSONObject(i);
                        tags.add(new Tag(tagObject));
                    }
                }
                if (article.has("is_top")) {
                    is_top = article.getString("is_top");
                }
                if (article.has("comment_perm")) {
                    comment_perm = article.getString("comment_perm");
                }
                if (article.has("comment_hidden")) {
                    comment_hidden = article.getString("comment_hidden");
                }
                if (article.has("title")) {
                    title = article.getString("title");
                }
                if (article.has("thumb")) {
                    thumb = article.getString("thumb");
                }
                if (article.has("info")) {
                    info = new Info(article.getJSONObject("info"));
                }
                if (article.has("user")) {
                    user = new User(article.getJSONObject("user"));
                }
                if (article.has("body")) {
                    body = article.getString("body");
                }
                if (article.has("images")) {
                    JSONArray imagelist = article.getJSONArray("images");
                    images = new ArrayList<Image>();
                    for (int i = 0; i < imagelist.length(); i++) {
                        JSONObject imageObject = imagelist.getJSONObject(i);
                        images.add(new Image(imageObject));
                    }
                }
            }
        } catch (JSONException e) {

        }
    }
}
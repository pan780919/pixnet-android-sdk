package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Guestbook
 */
public class Guestbook extends BasicResponse {
    /**
     * Article in guestbook
     */
    public Article article;

    public Guestbook(JSONObject jo) {
        super(jo);
    }

    public Guestbook(String str) {
        super(str);
    }

    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if (obj.has("article")) {
            article = new Article(obj.getString("article"));
        }
        return obj;
    }
}
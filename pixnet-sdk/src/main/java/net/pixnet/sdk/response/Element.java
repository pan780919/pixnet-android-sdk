package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Element
 */
public class Element extends BasicResponse {
    public Element(String response) {
        formatJson(response);
    }

    /**
     * Element id
     */
    public String id;
    /**
     * Element title
     */
    public String title;
    /**
     * Element position
     */
    public String position;
    /**
     * Element size
     */
    public String size;
    /**
     * Element identifier code
     */
    public String identifier;
    /**
     * Element type
     */
    public String type;
    /**
     * Element url
     */
    public String url;
    /**
     * Element thumb url
     */
    public String thumb;
    /**
     * Element upload time
     */
    public String uploaded_at;
    /**
     * Element owner
     */
    public User user;
    /**
     * Element location
     */
    public Location location;
    /**
     * Element tags
     */
    public ArrayList<Tag> tags;
    /**
     * Element description
     */
    public String description;
    /**
     * Taken at
     */
    public String taken_at;
    /**
     * Element original url
     */
    public String original;
    /**
     * Element normal url
     */
    public String normal;
    /**
     * Element  small square url
     */
    public String small_square;
    /**
     * Element square url
     */
    public String square;
    /**
     * Element medium url
     */
    public String medium;
    /**
     * Element bigger url
     */
    public String bigger;
    /**
     * Element large url
     */
    public String large;
    /**
     * Element pimg
     */
    public String pimg;
    /**
     * Element Dimension
     */
    public Dimension dimension;
    /**
     * Faces tagged on element
     */
    public Face faces;

    private void formatJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if (obj.has("message")) {
                message = obj.getString("message");
            }
            if (obj.has("error")) {
                error = obj.getString("error");
            }
            if (obj.has("id")) {
                id = obj.getString("id");
            }
            if (obj.has("title")) {
                title = obj.getString("title");
            }
            if (obj.has("position")) {
                position = obj.getString("position");
            }
            if (obj.has("size")) {
                size = obj.getString("size");
            }
            if (obj.has("identifier")) {
                identifier = obj.getString("identifier");
            }
            if (obj.has("type")) {
                type = obj.getString("type");
            }
            if (obj.has("url")) {
                url = obj.getString("url");
            }
            if (obj.has("thumb")) {
                thumb = obj.getString("thumb");
            }
            if (obj.has("uploaded_at")) {
                uploaded_at = obj.getString("uploaded_at");
            }
            if (obj.has("user")) {
                user = new User(obj.getJSONObject("user"));
            }
            if (obj.has("location")) {
                location = new Location(obj.getString("location"));
            }
            if (obj.has("tags")) {
                tags = new ArrayList<Tag>();
                JSONArray atags = obj.getJSONArray("tags");
                for(int i =0;i<atags.length();i++){
                    tags.add(new Tag(atags.getJSONObject(i)));
                }
            }
            if (obj.has("description")) {
                description = obj.getString("description");
            }
            if (obj.has("taken_at")) {
                taken_at = obj.getString("taken_at");
            }
            if (obj.has("original")) {
                original = obj.getString("original");
            }
            if (obj.has("normal")) {
                normal = obj.getString("normal");
            }
            if (obj.has("small_square")) {
                small_square = obj.getString("small_square");
            }
            if (obj.has("square")) {
                square = obj.getString("square");
            }
            if (obj.has("medium")) {
                medium = obj.getString("medium");
            }
            if (obj.has("bigger")) {
                bigger = obj.getString("bigger");
            }
            if (obj.has("large")) {
                large = obj.getString("large");
            }
            if (obj.has("pimg")) {
                pimg = obj.getString("pimg");
            }
            if (obj.has("dimension")) {
                dimension = new Dimension(obj.getString("dimension"));
            }
            if (obj.has("faces")) {
                faces = new Face(obj.getString("faces"));
            }
        } catch (JSONException e) {

        }
    }
}
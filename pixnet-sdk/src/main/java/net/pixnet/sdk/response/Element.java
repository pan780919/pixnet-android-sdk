package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Element
 */
public class Element extends BasicResponse {

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
    public int position;
    /**
     * Element size
     */
    public long size;
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
    public long uploaded_at;
    public Hits hits;
    public String hit_uri;
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
    public long taken_at;
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
    public Exif exif;

    public Element(String str) throws JSONException {
        super(str);
    }

    public Element(JSONObject jo) throws JSONException {
        super(jo);
    }

    @Override
    protected JSONObject parseJSON(JSONObject jo) throws JSONException {
        JSONObject obj = super.parseJSON(jo);
        if (obj.has("id")) {
            id = obj.getString("id");
        }
        if (obj.has("title")) {
            title = obj.getString("title");
        }
        if (obj.has("position")) {
            position = obj.getInt("position");
        }
        if (obj.has("size")) {
            size = obj.getLong("size");
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
            uploaded_at = obj.getInt("uploaded_at")*1000l;
        }
        if (obj.has("user")) {
            user = new User(obj);
        }
        if (obj.has("location")) {
            location = new Location(obj.getJSONObject("location"));
        }
        if (obj.has("tags")) {
            JSONArray atags = obj.getJSONArray("tags");
            if(atags.length()>0)
                tags = new ArrayList<Tag>();
            for(int i =0;i<atags.length();i++){
                tags.add(new Tag(atags.getJSONObject(i)));
            }
        }
        if (obj.has("description")) {
            description = obj.getString("description");
        }
        if (obj.has("taken_at")) {
            taken_at = obj.getInt("taken_at")*1000l;
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
            dimension = new Dimension(obj.getJSONObject("dimension"));
        }
        if (obj.has("faces")) {
            faces = new Face(obj.getJSONObject("faces"));
        }
        if(obj.has("hits"))
            hits=new Hits(obj.getJSONObject("hits"));
        if(obj.has("hit_uri"))
            hit_uri=obj.getString("hit_uri");
        if(obj.has("exif"))
            exif=new Exif(obj.getJSONObject("exif"));

        return obj;
    }

    public class Dimension{
        public ImageDimension original;
        public ImageDimension thumb;
        public ImageDimension small_square;
        public ImageDimension square;
        public ImageDimension medium;
        public ImageDimension bigger;
        public ImageDimension large;
        public ImageDimension normal;
        public Dimension(JSONObject jo) throws JSONException {
            if(jo.has("original"))
                original = new ImageDimension(jo.getJSONObject("original"));
            if(jo.has("thumb"))
                thumb = new ImageDimension(jo.getJSONObject("thumb"));
            if(jo.has("small_square"))
                small_square=new ImageDimension(jo.getJSONObject("small_square"));
            if(jo.has("square"))
                square=new ImageDimension(jo.getJSONObject("square"));
            if(jo.has("medium"))
                medium=new ImageDimension(jo.getJSONObject("medium"));
            if(jo.has("bigger"))
                bigger=new ImageDimension(jo.getJSONObject("bigger"));
            if(jo.has("large"))
                large=new ImageDimension(jo.getJSONObject("large"));
            if(jo.has("normal"))
                normal=new ImageDimension(jo.getJSONObject("normal"));
        }
        public class ImageDimension{
            public int width;
            public int height;
            public ImageDimension(JSONObject jo) throws JSONException {
                if(jo.has("width"))
                    width = jo.getInt("width");
                if(jo.has("height"))
                    height = jo.getInt("height");
            }
        }
    }

    public class Exif{
        public String camera;
        public Date dateTaken;
        public String meteringMode;
        public String aperture;
        public String exposureTime;
        public String focalLength;

        public Exif(JSONObject jo) throws JSONException {
            if(jo.has("Camera"))
                camera=jo.getString("Camera");
            if(jo.has("DateTaken")) {
                String date = jo.getString("DateTaken");
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.getDefault());
                try {
                    dateTaken=sdf.parse(date);
                } catch (ParseException e) {
                }
            }
            if(jo.has("MeteringMode"))
                meteringMode=jo.getString("MeteringMode");
            if(jo.has("Aperture"))
                aperture=jo.getString("Aperture");
            if(jo.has("ExposureTime"))
                exposureTime=jo.getString("ExposureTime");
            if(jo.has("FocalLength"))
                focalLength=jo.getString("FocalLength");
        }
    }
}
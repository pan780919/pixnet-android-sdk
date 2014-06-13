package net.pixnet.sdk.response;

import java.util.ArrayList;

/**
 * Element
 */
public class Element extends BasicResponse{
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
}
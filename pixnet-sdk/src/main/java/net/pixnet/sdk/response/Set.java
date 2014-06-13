package net.pixnet.sdk.response;

import java.util.ArrayList;

public class Set extends BasicResponse{
    /**
     * Set id
     */
    public String id;
    /**
     * type:set
     */
    public String type;
    /**
     * Set title
     */
    public String title;
    /**
     * Set thumb url
     */
    public String thumb;
    /**
     * Set url
     */
    public String link;
    /**
     * Set permission
     */
    public String permission;
    /**
     * Set category id
     */
    public String category_id;
    /**
     * Set category
     */
    public String category;
    /**
     * Set tags
     */
    public ArrayList<Tag> tags;
    /**
     * is lock right
     */
    public String is_lockright;
    /**
     *Set licence
     */
    public Licence licence;
    /**
     * Show set can comment or not
     */
    public String cancomment;
    /**
     * Set parent id
     */
    public String parent_id;
    /**
     * Set position
     */
    public String position;
    /**
     * Set create time
     */
    public String created_at;
    /**
     * Show this set is system set or not
     */
    public String is_system_set;
    /**
     * Total elements count
     */
    public String total_elements;
    /**
     * Set owner info
     */
    public User user;
    /**
     * Set description
     */
    public String description;
    /**
     * Show elements in this set is taggable or not
     */
    public boolean is_taggable;
}
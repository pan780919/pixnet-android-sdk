package net.pixnet.sdk.response;

import java.util.ArrayList;

public class Folder extends BasicResponse{
    /**
     * Folder id
     */
    public String id;
    /**
     * Folder type
     */
    public String type;
    /**
     * Folder title
     */
    public String title;
    /**
     * Folder thumb url
     */
    public String thumb;
    /**
     * Folder position
     */
    public String position;
    /**
     * Folder tags
     */
    public ArrayList<Tag> tags;
    /**
     * Folder create time
     */
    public String created_at;
    /**
     * Folder's total children sets
     */
    public int total_children_sets;
    /**
     * Folder owner
     */
    public User user;
    /**
     * Folder description
     */
    public String description;
}
package net.pixnet.sdk.response;

import java.util.ArrayList;

public class Set extends BasicResponse{
    public String id;
    public String type;
    public String title;
    public String thumb;
    public String link;
    public String permission;
    public String category_id;
    public String category;
    public ArrayList<Tag> tags;
    public String is_lockright;
    public Licence licence;
    public String cancomment;
    public String parent_id;
    public String position;
    public String created_at;
    public String is_system_set;
    public String total_elements;
    public User user;
    public String description;
    public boolean is_taggable;
}
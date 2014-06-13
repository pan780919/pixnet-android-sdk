package net.pixnet.sdk.response;

import java.util.ArrayList;

public class Folder extends BasicResponse{
    public String id;
    public String type;
    public String title;
    public String thumb;
    public String position;
    public ArrayList<Tag> tags;
    public String created_at;
    public int total_children_sets;
    public User user;
    public String description;
}
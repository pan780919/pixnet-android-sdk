package net.pixnet.sdk.response;

import java.util.ArrayList;

public class Catagory{
    public String id;
    public String type;
    public String order;
    public String name;
    public String description;
    public String show_index;
    public String site_category_id;
    public String site_category_done;
    public ArrayList<Catagory> child_categories;
}
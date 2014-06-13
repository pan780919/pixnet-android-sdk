package net.pixnet.sdk.response;

import java.util.ArrayList;

public class Category{
    /**
     * Category id
     */
    public String id;
    /**
     * Category type
     */
    public String type;
    /**
     * Order
     */
    public String order;
    /**
     * Category name
     */
    public String name;
    /**
     * Category description
     */
    public String description;
    /**
     * Show index or not
     */
    public boolean show_index;
    /**
     * site_category_id
     */
    public String site_category_id;
    /**
     * site_category_done
     */
    public String site_category_done;
    /**
     * Child categories list
     */
    public ArrayList<Category> child_categories;
}
package net.pixnet.sdk.response;

import java.util.ArrayList;

/**
 * Friendship
 */
public class Friendship{
    /**
     * Friendship id
     */
    public String id;
    /**
     * Friend name
     */
    public String user_name;
    /**
     * Friend display name
     */
    public String display_name;
    /**
     * Friend group list
     */
    public ArrayList<Group> groups;
}
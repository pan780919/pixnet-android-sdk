package net.pixnet.sdk.response;

/**
 * Created by Koi 2014/6/13.
 */
public class Tag {
    public Tag(String tag, int locked, String added_by) {
        this.tag = tag;
        this.locked = locked;
        this.added_by = added_by;
    }

    /**
     * tag name
     */
    public String tag;
    /**
     * lock status
     */
    public int locked;
    /**
     * added by username
     */
    public String added_by;
}
package net.pixnet.sdk.response;

public class Folder extends AlbumContainer{
    /**
     * Folder's total children sets
     */
    public int total_children_sets;

    public Folder(String str) {
        super(str);
    }
}
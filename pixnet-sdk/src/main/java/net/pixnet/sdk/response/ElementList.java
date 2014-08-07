package net.pixnet.sdk.response;

import java.util.ArrayList;

/**
 * Elements list
 */
public class ElementList extends BaseListResponse{
    public int total;
    public int per_page;
    public int page;
    public ArrayList<Element> elements;

    public ElementList(String str) {
        super(str);
    }
}
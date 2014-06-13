package net.pixnet.sdk.response;

import java.util.ArrayList;

/**
 * Elements list
 */
public class ElementsList extends BasicResponse{
    public int total;
    public int per_page;
    public int page;
    public ArrayList<Element> elements;
}
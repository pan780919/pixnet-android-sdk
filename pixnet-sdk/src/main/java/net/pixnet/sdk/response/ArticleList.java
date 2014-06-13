package net.pixnet.sdk.response;

import java.util.ArrayList;
/**
 * Created by Koi 2014/6/13.
 */
public class Article extends BasicResponse {
    public ArrayList<Article> articles;
    public int total;
    public int per_page;
    public int page;
}
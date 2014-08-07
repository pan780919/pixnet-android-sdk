package net.pixnet.sdk.response;

/**
 * Guestbook
 */
public class Guestbook extends BasicResponse{
    /**
     * Article in guestbook
     */
    public Article article;

    public Guestbook(String str) {
        super(str);
    }
}
package net.pixnet.sdk.response;

/**
 * Config
 */
public class AlbumConfig extends BasicResponse{
    /**
     * Show user is vip or not
     */
    public int is_vip;
    /**
     * Rotate by exif
     */
    public int is_rotatebyexif;
    /**
     * Show is element first or not
     */
    public String is_elementfirst;
    /**
     * User avatar url
     */
    public String user_avatar;
    /**
     * User quota
     */
    public String user_quota;
    /**
     * User used quota
     */
    public String user_used_quota;

    public AlbumConfig(String str) {
        super(str);
    }
}
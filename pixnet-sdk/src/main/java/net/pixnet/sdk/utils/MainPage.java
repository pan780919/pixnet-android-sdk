package net.pixnet.sdk.utils;

import android.content.Context;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.R;

/**
 * Created by Koi on 2014/7/31.
 */
public class MainPage {
    private RequestController rc;

    public MainPage(Context c) {
        rc = RequestController.getInstance();
        OAuthConnectionTool.OAuthVersion ver= PIXNET.getOAuthVersion(c);
        OAuthConnectionTool tool;
        if(ver== OAuthConnectionTool.OAuthVersion.VER_1){
            tool=OAuthConnectionTool.newOaut1ConnectionTool(c.getString(R.string.consumer_key), c.getString(R.string.consumer_secret));
            tool.setAccessTokenAndSecret(PIXNET.getOauthAccessToken(c), PIXNET.getOauthAccessSecret(c));
        }
        else{
            tool=OAuthConnectionTool.newOauth2ConnectionTool();
            tool.setAccessToken(PIXNET.getOauthAccessToken(c));
        }
        rc.setHttpConnectionTool(tool);
    }
    public void getColumnCategoryList(){

    }
    public void getBlogColumnList(){

    }
    public void getAlbumColumnList(){

    }
    public void getArticleList(){

    }
    public void getAlbumList(){

    }
    public void getVideoList(){

    }
}

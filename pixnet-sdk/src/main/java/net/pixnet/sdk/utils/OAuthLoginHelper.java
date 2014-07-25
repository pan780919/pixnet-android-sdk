package net.pixnet.sdk.utils;

import android.graphics.Bitmap;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.pixnet.sdk.PIXNET;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OAuthLoginHelper {

    private String oauth1Url_request;
    private String oauth1Url_access;
    private String oauth2Url_auth;
    private String oauth2Url_grant;
    private String key;
    private String secret;
    private String oauthToken;
    private String oauthSecret;
    private String access_token;
    private String token_secret;

    private OAuthLoginListener listener;
    private WebView webView;
    private String accessUrl;

    public static OAuthLoginHelper newAoth1LoginHelper(String consumerKey, String consumerSecret, String requestUrl, String accessUrl){
        OAuthLoginHelper helper=new OAuthLoginHelper();
        helper.key =consumerKey;
        helper.secret =consumerSecret;
        helper.oauth1Url_request=requestUrl;
        helper.oauth1Url_access=accessUrl;
        return helper;
    }

    public static OAuthLoginHelper newAoth2LoginHelper(String clientId, String clientSecret, String authUrl, String grantUrl){
        OAuthLoginHelper helper=new OAuthLoginHelper();
        helper.key =clientId;
        helper.secret =clientSecret;
        helper.oauth2Url_auth=authUrl;
        helper.oauth2Url_grant=grantUrl;
        return helper;
    }

    public void setOAuthLoginListener(OAuthLoginListener listener){
        this.listener=listener;
    }

    public void loginByXauth(String userName, String passwd, String accessTokenUrl, final PIXNET.OnAccessTokenGotListener listener) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("x_auth_mode", "client_auth"));
        params.add(new BasicNameValuePair("x_auth_password", passwd));
        params.add(new BasicNameValuePair("x_auth_username", userName));

        Request request = new Request(accessTokenUrl);
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                HashMap<String, String> resParams = HttpHelper.parseParamsByResponse(response);
                String token = resParams.get("oauth_token");
                String secret = resParams.get("oauth_token_secret");
                listener.onAccessTokenGot(token, secret);
            }
        });
        RequestController rc = RequestController.getInstance();
        rc.addRequest(request);
    }

    public void loginByOauth1(){
        loginByOauth1(null);
    }

    public void loginByOauth1(WebView view){
        webView=view;
        getUrlForOauth1Request();
    }

    public void loginByOauth2(WebView view){
        webView=view;
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (url.contains("code=")) {
                    String code = url.replace(redirect_uri, "").replace("/?code=", "");
                    if(listener!=null){
                        listener.onRequestUrlGot();
                    }
                    getOauth2AccessToken(code);
                }
            }
        });
        webView.loadUrl(getOauth2RequestUrl());
    }

    /**
     * @param code Code from RequestUrl
     * @return Set code and Get AccessToken
     */
    public void getOauth2AccessToken(String code) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("redirect_uri", redirect_uri));
        params.add(new BasicNameValuePair("client_id", key));
        params.add(new BasicNameValuePair("client_secret", secret));

        Request request = new Request(oauth2Url_grant);
        request.setParams(params);
        request.setCallback(new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if(listener!=null)
                        listener.onAccessTokenGot(job.getString("access_token"), "OAuth2");
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
        RequestController rc = RequestController.getInstance();
        rc.addRequest(request);
    }

    private OAuthHelper getOAuthHelper(OAuthHelper.OAuthVersion version) {
        OAuthHelper oAuthHelper;
        switch (version){
            case VER_1:
                oAuthHelper=OAuthHelper.newOaut1hHelper(key, secret);
                break;
            case VER_2:
                oAuthHelper=OAuthHelper.newOauth2Helper(key, secret);
                break;
            default:
                oAuthHelper=null;
        }
        return oAuthHelper;
    }

    public void getUrlForOauth1Request(){
        RequestController rc=RequestController.getInstance();
        rc.setHttpConnectionTool(getOAuthHelper(OAuthHelper.OAuthVersion.VER_1));

        Request request = new Request(oauth1Url_request);
        request.setCallback(new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                HashMap<String, String> map=HttpHelper.parseParamsByResponse(response);
                oauthToken=map.get("oauth_token");
                oauthSecret=map.get("oauth_token_secret");
                String url=map.get("xoauth_request_auth_url");
                accessUrl=HttpHelper.decodeUrl(url);
                if(listener!=null)
                    listener.onRequestUrlGot();

                if(webView!=null){
                    oauth1Verify();
                }
            }
        });
        rc.addRequest(request);
    }

    public void oauth1Verify(){
        if(accessUrl==null || webView==null)
            return;
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
        webView.setWebViewClient(new WebViewClient() {
            @JavascriptInterface
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:try{window.HTMLOUT.showHTML" +
                        "(document.getElementById('oauth_verifier').innerHTML);}catch(err){}");
            }
        });
        Helper.log(accessUrl);
        accessUrl=accessUrl.replace("https","http");
        webView.loadUrl(accessUrl);

        if(listener!=null)
            listener.onVerify();
    }

    class MyJavaScriptInterface {

        @JavascriptInterface
        public void showHTML(String html) {
            Helper.log("showHTML");
            if (html.length() == 6) {
                access(html);
            }
        }

        public void access(final String verifier) {
            Helper.log("access");
            OAuthHelper oauthHelper= getOAuthHelper(OAuthHelper.OAuthVersion.VER_1);
            oauthHelper.setTokenAndSecret(oauthToken, oauthSecret);
            RequestController rc=RequestController.getInstance();
            rc.setHttpConnectionTool(oauthHelper);

            Request r=new Request(oauth1Url_access);
            Helper.log("oauth1Url_access:"+oauth1Url_access);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("oauth_verifier", verifier));
            r.setParams(params);
            r.setCallback(new Request.RequestCallback() {
                @Override
                public void onResponse(String response) {
                    HashMap<String, String> map = HttpHelper.parseParamsByResponse(response);
                    access_token = map.get("oauth_token");
                    token_secret = map.get("oauth_token_secret");

                    if (listener != null)
                        listener.onAccessTokenGot(access_token, token_secret);
                }
            });

            rc.addRequest(r);
        }
    }

    private String redirect_uri="http://oob";

    /**
     * The url that can auth and return the code for access token
     *
     * @return formatted request url
     */
    private String getOauth2RequestUrl() {
        return oauth2Url_auth
                + "?client_id=" + key
                + "&redirect_uri=" + redirect_uri
                + "&response_type=code";
    }

    public interface OAuthLoginListener {
        void onRequestUrlGot();
        void onVerify();
        void onAccessTokenGot(String token, String secret);
    }
}

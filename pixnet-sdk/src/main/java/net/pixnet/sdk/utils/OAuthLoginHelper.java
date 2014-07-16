package net.pixnet.sdk.utils;

import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OAuthLoginHelper {

    private static final String URL_OAUTH1_REQUEST = "http://emma.pixnet.cc/oauth/request_token";
    private static final String URL_OAUTH1_ACCESS = "http://emma.pixnet.cc/oauth/access_token";

    private String consumerKey;
    private String consumerSecret;
    private String oauthToken;
    private String oauthSecret;
    private String access_token;
    private String token_secret;

    private OAuthHelper oAuthHelper;
    private OAuthLoginListener listener;

    private WebView webView;

    private String accessUrl;

    public OAuthLoginHelper(String consumerKey, String consumerSecret){
        this.consumerKey=consumerKey;
        this.consumerSecret=consumerSecret;
    }

    public void setOAuthLoginListener(OAuthLoginListener listener){
        this.listener=listener;
    }

    public void login(){
        login(null);
    }

    public void login(WebView view){
        webView=view;
        getUrlForOauth1Request();
    }

    public void getUrlForOauth1Request(){
        RequestController rc=RequestController.getInstance();
        rc.setHttpConnectionTool(getOAuthHelper());

        Request request = new Request(URL_OAUTH1_REQUEST);
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
                    verify();
                }
            }
        });
        rc.addRequest(request);
    }

    public void verify(){
        if(accessUrl==null || webView==null)
            return;
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:window.HTMLOUT.showHTML" +
                        "(document.getElementById('oauth_verifier').innerHTML);");
            }
        });
        webView.loadUrl(accessUrl);

        if(listener!=null)
            listener.onVerify();
    }

    class MyJavaScriptInterface {

        @JavascriptInterface
        public void showHTML(String html) {
            if (html.length() == 6) {
                access(html);
            }
        }

        public void access(final String verifier) {
            OAuthHelper oauthHelper= getOAuthHelper();
            oauthHelper.setTokenAndSecret(oauthToken, oauthSecret);
            RequestController rc=RequestController.getInstance();
            rc.setHttpConnectionTool(oauthHelper);

            Request r=new Request(URL_OAUTH1_ACCESS);
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

    private OAuthHelper getOAuthHelper() {
        if(oAuthHelper==null)
            oAuthHelper=new OAuthHelper(OAuthHelper.OAuthVersion.VER_1, consumerKey, consumerSecret);
        return oAuthHelper;
    }

    public interface OAuthLoginListener {
        void onRequestUrlGot();
        void onVerify();
        void onAccessTokenGot(String token, String secret);
    }
}

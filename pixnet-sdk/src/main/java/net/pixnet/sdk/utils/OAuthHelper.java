package net.pixnet.sdk.utils;

import android.os.AsyncTask;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * OAuth connection tool
 */
public class OAuthHelper extends HttpHelper {

    private static final String SIGNATRUE_METHOD = "HMAC-SHA1";
    private static final String URL_AUTH = "https://emma.pixnet.cc/oauth2/authorize";
    private static final String URL_GRANT = "https://emma.pixnet.cc/oauth2/grant";
    private static final String URL_OAUTH1_REQUEST = "http://emma.pixnet.cc/oauth/request_token";
    private static final String URL_OAUTH1_ACCESS = "http://emma.pixnet.cc/oauth/access_token";
    private OAuthVersion ver = OAuthVersion.VER_1;

    private String access_token = "";
    private String client_id = "";
    private String client_secret = "";
    private String redirect_uri = "";
    private String consumer_secret;
    private String consumer_key;
    private String nonce = null;
    private String timestamp = null;
    private String accessToken = null;
    private String token_secret = null;

    @Override
    public String performRequest(Request request) {
        switch (ver) {
            case VER_1:
                computeNoceAndTimestamp();
                String signatrue = getSignatrue(HttpGet.METHOD_NAME, request.getUrl(), request.getParams());
                String headerStr = getHeaderString(signatrue);
                List<NameValuePair> headers = getHeader(headerStr);
                request.setHeaders(headers);
                break;
            case VER_2:
                break;
            default:
        }
        return super.performRequest(request);
    }

    public static enum OAuthVersion {
        VER_1,
        VER_2
    }

    /**
     * new helper for OAuth
     *
     * @param version        OAuthVersion.VER_1 or OAuthVersion.VER_2
     * @param consumerKey    your consume key / client id
     * @param consumerSecret your consumer secret
     */
    public OAuthHelper(OAuthVersion version, String consumerKey, String consumerSecret) {
        ver = version;
        consumer_secret = consumerSecret;
        consumer_key = consumerKey;
        client_id = consumerKey;
        client_secret = consumerSecret;
    }

    public boolean xAuthLogin(String userName, String passwd, String accessTokenUrl) {
        if (accessToken != null) return true;

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("x_auth_mode", "client_auth"));
        params.add(new BasicNameValuePair("x_auth_password", passwd));
        params.add(new BasicNameValuePair("x_auth_username", userName));

        Request request = new Request(accessTokenUrl);
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        String res = super.performRequest(request);

        HashMap<String, String> resParams = HttpHelper.parseParamsByResponse(res);
        String token = resParams.get("oauth_token");
        String secret = resParams.get("oauth_token_secret");

        if (token != null && secret != null) {
            setTokenAndSecret(token, secret);
            return true;
        } else return false;
    }

    public void setTokenAndSecret(String token, String secret) {
        accessToken = token;
        token_secret = secret;
    }

    public void login(final WebView wv, final Request.RequestCallback callback) {
        //request
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                Request request = new Request(URL_OAUTH1_REQUEST);
                request.setMethod(Request.Method.GET);
                try {
                    String authUrl = performRequest(request);
                    String[] reqToken = authUrl.split("&");
                    accessToken = reqToken[0].replace("oauth_token=", "");
                    token_secret = reqToken[1].replace("oauth_token_secret=", "");
                    reqToken[4] = URLDecoder.decode(reqToken[4], "utf-8");
                    String[] token = reqToken[4].split("request_auth_url=");
                    return token[1];
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            }
            //Authorize
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                WebSettings settings = wv.getSettings();
                settings.setSupportZoom(true);
                settings.setBuiltInZoomControls(true);
                settings.setJavaScriptEnabled(true);
                wv.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
                wv.setWebViewClient(new WebViewClient() {
                    public void onPageFinished(WebView view, String url) {
                        wv.loadUrl("javascript:window.HTMLOUT.showHTML" +
                                "(document.getElementById('oauth_verifier').innerHTML);");
                    }
                });
                wv.loadUrl(s);
            }

            class MyJavaScriptInterface {
                @JavascriptInterface
                public void showHTML(String html) {
                    if (html.length() == 6) {
                        access(html, callback);
                    }
                }
            }
        }.execute();
    }
    //access
    public void access(final String verifier, final Request.RequestCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                Request access = new Request(URL_OAUTH1_ACCESS);
                access.setMethod(Request.Method.GET);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("oauth_verifier", verifier));
                params.add(new BasicNameValuePair("oauth_callback", "oob"));
                access.setParams(params);
                return performRequest(access);
            }
            //set Tokens
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String[] oauthTokens = s.split("&");
                accessToken = oauthTokens[0].replace("oauth_token=", "");
                token_secret = oauthTokens[1].replace("oauth_token_secret=", "");
                //oAuth1Login done , call callback
                callback.onResponse("");
            }
        }.execute();
    }

    /**
     * The url that can auth and return the code for access token
     *
     * @return formatted request url
     */
    public String getRequestUrl() {
        return URL_AUTH
                + "?client_id=" + client_id
                + "&redirect_uri=" + redirect_uri
                + "&response_type=code";
    }

    /**
     * format grant url with parameters
     *
     * @param code
     * @return formatted grant url
     */
    private String getGrantUrl(String code) {
        return URL_GRANT
                + "?grant_type=authorization_code&code=" + code
                + "&redirect_uri=" + redirect_uri
                + "&client_id=" + client_id
                + "&client_secret=" + client_secret;
    }

    /**
     * @param code Code from RequestUrl
     * @return Set code and Get AccessToken
     */
    public String getAccessToken(String code) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("redirect_uri", redirect_uri));
        params.add(new BasicNameValuePair("client_id", client_id));
        params.add(new BasicNameValuePair("client_secret", client_secret));

        Request request = new Request(URL_GRANT);
        request.setParams(params);
        String response = super.performRequest(request);

        try {
            JSONObject obj = new JSONObject(response);
            access_token = obj.getString("access_token");
        } catch (JSONException e) {
            access_token = "Code error";
        }
        return access_token;
    }

    private void computeNoceAndTimestamp() {
        nonce = getNonce();
        timestamp = getTimeStamp();
    }

    private List<NameValuePair> getHeader(String headerStr) {
        List<NameValuePair> headers = new ArrayList<NameValuePair>();
        headers.add(new BasicNameValuePair("Authorization", headerStr));
        headers.add(new BasicNameValuePair("Content-Type", "application/x-www-form-urlencoded"));
        return headers;
    }

    private String getSignatrue(String method, String url, List<NameValuePair> params) {
        String paraStr = getParamsString(params);
//		Helper.log(paraStr);
        String baseStr = getBaseString(method, url, paraStr);
        String secret = HttpHelper.encodeUrl(consumer_secret) + "&";
        if (token_secret != null) secret += HttpHelper.encodeUrl(token_secret);
        return computeShaHash(baseStr, secret);
    }

    private String getParamsString(List<NameValuePair> params) {
        List<String> paraList = getBasicOAuthParameters();
        if (params != null) {
            for (NameValuePair item : params) {
                paraList.add(item.getName() + "=" + HttpHelper.encodeUrl(item.getValue()));
            }
        }
        return formatParameterString(paraList);
    }

    private ArrayList<String> getBasicOAuthParameters() {
        ArrayList<String> paraList = new ArrayList<String>();
        paraList.add("oauth_nonce=" + nonce);
        paraList.add("oauth_consumer_key=" + consumer_key);
        paraList.add("oauth_signature_method=" + SIGNATRUE_METHOD);
        paraList.add("oauth_timestamp=" + timestamp);
        paraList.add("oauth_version=1.0");
        if (accessToken != null) paraList.add("oauth_token=" + accessToken);

        return paraList;
    }

    private String getBaseString(String method, String url, String paraStr) {
        String baseStr = method
                + "&" + HttpHelper.encodeUrl(url)
                + "&" + HttpHelper.encodeUrl(paraStr);

        return baseStr;
    }

    private String getHeaderString(String signatrue) {
        String headerStr = "OAuth"
                + " oauth_nonce=\"" + nonce + "\""
                + ", oauth_signature_method=\"" + SIGNATRUE_METHOD + "\""
                + ", oauth_timestamp=\"" + timestamp + "\""
                + ", oauth_consumer_key=\"" + HttpHelper.encodeUrl(consumer_key) + "\""
                + ", oauth_signature=\"" + HttpHelper.encodeUrl(signatrue) + "\""
                + ", oauth_version=\"1.0\"";
        if (accessToken != null)
            headerStr += ", oauth_token=\"" + HttpHelper.encodeUrl(accessToken) + "\"";

        return headerStr;
    }

    private String formatParameterString(List<String> params) {
        String paraStr = "";

        Collections.sort(params);
        for (String str : params) {
            if (paraStr.length() > 0) paraStr += "&";
            paraStr += str;
        }

        return paraStr;
    }

    private String computeShaHash(String data, String key) {
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        String result = null;

        try {
            Key signingKey = new SecretKeySpec(key.getBytes("UTF-8"), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            result = new String(Base64.encode(rawHmac, Base64.NO_WRAP));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getNonce() {
        StringBuffer sb = new StringBuffer();
        Random generator = new Random();

        for (int i = 0; i < 32; i++) {
            Integer r = generator.nextInt();
            if (r < 0) {
                r = r * -1;
            }
            r = r % 16;

            sb.append(Integer.toHexString(r));
        }

        return sb.toString();
    }

    private String getTimeStamp() {
        long seconds = (long) (System.currentTimeMillis() / 1000d);
        String secondsString = String.valueOf(seconds);
        return secondsString;
    }
}

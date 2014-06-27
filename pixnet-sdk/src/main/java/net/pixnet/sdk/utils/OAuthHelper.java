package net.pixnet.sdk.utils;

import android.util.Base64;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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
public class OAuthHelper {

    private static final String SIGNATRUE_METHOD = "HMAC-SHA1";
    private static final String URL_AUTH = "https://emma.pixnet.cc/oauth2/authorize";
    private static final String URL_GRANT = "https://emma.pixnet.cc/oauth2/grant";
    private static final String URL_POST_ARTICLES = "https://emma.pixnet.cc/blog/articles";

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
    String postUrl;
    List<NameValuePair> param;
    HttpRequestList list = new HttpRequestList();
    private HttpHelper hh;

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
    }

    public boolean xAuthLogin(String userName, String passwd, String accessTokenUrl) {
        if (accessToken != null) return true;

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("x_auth_mode", "client_auth"));
        params.add(new BasicNameValuePair("x_auth_password", passwd));
        params.add(new BasicNameValuePair("x_auth_username", userName));

        String res = post(accessTokenUrl, params);

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
        final String incode = code;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("code", incode));
        params.add(new BasicNameValuePair("redirect_uri", redirect_uri));
        params.add(new BasicNameValuePair("client_id", client_id));
        params.add(new BasicNameValuePair("client_secret", client_secret));
        String response = new HttpHelper().get(URL_GRANT, params);
        try {
            JSONObject obj = new JSONObject(response);
            access_token = (String) obj.get("access_token");
        } catch (JSONException e) {
            access_token = "Code error";
        }
        return access_token;
    }

    /**
     * Post Article
     *
     * @param access_token Access_token
     * @param param        other param add with &type=param_value
     * @return The return String from server
     * @throws java.io.IOException
     */
    public String post(String postUrl, String access_token, List<NameValuePair> param) {
        final String inaccess_token = access_token;
        List<NameValuePair> params;
        if (param == null) {
            params = new ArrayList<NameValuePair>();
        } else {
            params = param;
        }
        params.add(new BasicNameValuePair("access_token", inaccess_token));
        this.postUrl = postUrl;
        this.param = params;
        hh = new HttpHelper();
        hh.timeout_connection = 10000;
        hh.timeout_socket = 30000;
        int num;
        while ((num = list.addRequest(hh)) == -1) ;
        String response = hh.post(postUrl, params);
        list.removeRequest(hh);

        // System.out.println(urlPost.toString());
        return response;
    }

    private void computeNoceAndTimestamp() {
        nonce = getNonce();
        timestamp = getTimeStamp();
    }

    /**
     * get without parameters
     *
     * @param url
     * @return
     */
    public String get(String url) {
        return get(url, null);
    }

    /**
     * perform http get method with OAuth
     *
     * @param url
     * @param params
     * @return
     */
    public String get(String url, ArrayList<NameValuePair> params) {
        switch (ver) {
            case VER_1:
                computeNoceAndTimestamp();
                String signatrue = getSignatrue(HttpGet.METHOD_NAME, url, params);
                String headerStr = getHeaderString(signatrue);
                Header[] headers = getHeader(headerStr);
                return getHttpHelper().get(url, params, headers);
            case VER_2:
                // perform oauth 2.0
                return null;
            default:
                return null;
        }
    }

    /**
     * perform http post method with OAuth
     *
     * @param url
     * @param params
     * @return
     */
    public String post(String url, ArrayList<NameValuePair> params) {
        switch (ver) {
            case VER_1:
                computeNoceAndTimestamp();
                String signatrue = getSignatrue(HttpPost.METHOD_NAME, url, params);
                String headerStr = getHeaderString(signatrue);
                Header[] headers = getHeader(headerStr);
                return getHttpHelper().post(url, params, headers);
            case VER_2:
                // perform oauth 2.0
                return null;
            default:
                return null;
        }
    }

    /**
     * perform http delete method with OAuth
     *
     * @param url
     * @param params
     * @return
     */
    public String delete(String url, ArrayList<NameValuePair> params) {
        switch (ver) {
            case VER_1:
                computeNoceAndTimestamp();
                String signatrue = getSignatrue(HttpDelete.METHOD_NAME, url, params);
                String headerStr = getHeaderString(signatrue);
                Header[] headers = getHeader(headerStr);
                return getHttpHelper().delete(url, params, headers);
            case VER_2:
                // perform oauth 2.0
                return null;
            default:
                return null;
        }
    }

    private Header[] getHeader(String headerStr) {
        Header[] headers = {
                new BasicHeader("Authorization", headerStr)
                , new BasicHeader("Content-Type", "application/x-www-form-urlencoded")
        };
        return headers;
    }

    private String getSignatrue(String method, String url, ArrayList<NameValuePair> params) {
        String paraStr = getParamsString(params);
//		Helper.log(paraStr);
        String baseStr = getBaseString(method, url, paraStr);
        String secret = HttpHelper.encodeUrl(consumer_secret) + "&";
        if (token_secret != null) secret += HttpHelper.encodeUrl(token_secret);
        return computeShaHash(baseStr, secret);
    }

    private String getParamsString(ArrayList<NameValuePair> params) {
        ArrayList<String> paraList = getBasicOAuthParameters();
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

    private String formatParameterString(ArrayList<String> params) {
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

    private HttpHelper getHttpHelper() {
        if (hh == null)
            hh = new HttpHelper();
        return hh;
    }

    public void cancel() {
        if (hh != null) {
            hh.cancel();
        }
    }
}

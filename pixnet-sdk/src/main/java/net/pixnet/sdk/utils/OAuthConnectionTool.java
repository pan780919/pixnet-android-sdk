package net.pixnet.sdk.utils;

import android.util.Base64;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * OAuth connection tool
 */
public class OAuthConnectionTool
        extends HttpConnectionTool {

    private static final String SIGNATRUE_METHOD = "HMAC-SHA1";
    private OAuthVersion ver = OAuthVersion.VER_1;

    private String key = null;
    private String secret = null;
    private String nonce = null;
    private String timestamp = null;
    private String accessToken = null;
    private String tokenSecret = null;

    public static enum OAuthVersion {
        VER_1,
        VER_2
    }

    /**
     * new helper for OAuth1
     * @param consumerKey
     * @param consumerSecret
     * @return OAuthHelepr for 1.0
     */
    public static OAuthConnectionTool newOaut1ConnectionTool(String consumerKey, String consumerSecret){
        OAuthConnectionTool tool=new OAuthConnectionTool();
        tool.ver=OAuthVersion.VER_1;
        tool.key=consumerKey;
        tool.secret=consumerSecret;
        return tool;
    }

    /**
     * new helper for OAuth2
     * @return OAuthConnectionTool 2.0
     */
    public static OAuthConnectionTool newOauth2ConnectionTool(){
        OAuthConnectionTool tool=new OAuthConnectionTool();
        tool.ver=OAuthVersion.VER_2;
        return tool;
    }

    public void setAccessToken(String token){
        accessToken=token;
    }

    public void setAccessTokenAndSecret(String token, String secret) {
        accessToken = token;
        tokenSecret = secret;
    }

    @Override
    public String performRequest(Request request) {
        switch (ver) {
            case VER_1:
                computeNoceAndTimestamp();
                String signatrue = getSignatrue(request.getMethod().name(), request.getUrl(), request.getParams());
                String headerStr = getHeaderString(signatrue);
                List<NameValuePair> headers = getHeader(headerStr);
                request.setHeaders(headers);
                break;
            case VER_2:
                ArrayList<NameValuePair> params=(ArrayList<NameValuePair>)request.getParams();
                if(params==null)
                    params=new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("access_token", key));
                request.setParams(params);
                break;
            default:
        }
        return super.performRequest(request);
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
        String secret = HttpConnectionTool.encodeUrl(this.secret) + "&";
        if (tokenSecret != null) secret += HttpConnectionTool.encodeUrl(tokenSecret);
        return computeShaHash(baseStr, secret);
    }

    private String getParamsString(List<NameValuePair> params) {
        List<String> paraList = getBasicOAuthParameters();
        if (params != null) {
            for (NameValuePair item : params) {
                paraList.add(item.getName() + "=" + HttpConnectionTool.encodeUrl(item.getValue()).replace("+","%20"));
            }
        }
        return formatParameterString(paraList);
    }

    private ArrayList<String> getBasicOAuthParameters() {
        ArrayList<String> paraList = new ArrayList<String>();
        paraList.add("oauth_nonce=" + nonce);
        paraList.add("oauth_consumer_key=" + key);
        paraList.add("oauth_signature_method=" + SIGNATRUE_METHOD);
        paraList.add("oauth_timestamp=" + timestamp);
        paraList.add("oauth_version=1.0");
        if (accessToken != null) paraList.add("oauth_token=" + accessToken);

        return paraList;
    }

    private String getBaseString(String method, String url, String paraStr) {
        String baseStr = method
                + "&" + HttpConnectionTool.encodeUrl(url)
                + "&" + HttpConnectionTool.encodeUrl(paraStr);
        return baseStr;
    }

    private String getHeaderString(String signatrue) {
        String headerStr = "OAuth"
                + " oauth_nonce=\"" + nonce + "\""
                + ", oauth_signature_method=\"" + SIGNATRUE_METHOD + "\""
                + ", oauth_timestamp=\"" + timestamp + "\""
                + ", oauth_consumer_key=\"" + HttpConnectionTool.encodeUrl(key) + "\""
                + ", oauth_signature=\"" + HttpConnectionTool.encodeUrl(signatrue) + "\""
                + ", oauth_version=\"1.0\"";
        if (accessToken != null)
            headerStr += ", oauth_token=\"" + HttpConnectionTool.encodeUrl(accessToken) + "\"";

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

package net.pixnet.sdk.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * OAuth connection tool
 */
public class OAuthHelper {
    private String access_token = "";
    private String client_id = "";
    private String client_secret = "";
    private String redirect_uri = "";

    /**
     * Constructor
     *
     * @param client_id
     *            your client id
     * @param client_secret
     *            your client secret
     * @param redirect_uri
     *            your redirect url
     * @throws IOException
     * @throws JSONException
     */
    public OAuthHelper(String client_id, String client_secret,
                              String redirect_uri) throws IOException, JSONException {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.redirect_uri = redirect_uri;
    }


    /**
     * The url that can auth and return the code for accesstoken
     *
     * @return RequestUrl
     */
    public String getRequestUrl() {
        String url = "https://emma.pixnet.cc/oauth2/authorize?client_id="
                + client_id + "&redirect_uri=" + redirect_uri
                + "&response_type=code";
        return url;
    }

    /**
     * Post Article
     *
     * @param access_token
     *            Access_token
     * @param title
     *            Article title
     * @param body
     *            Article body
     * @param param
     *            other param add with &type=param_value
     * @return The return String from server
     * @throws IOException
     */
    public String post(String access_token, String title, String body,
                       String param) throws IOException {
        URL urlPost = new URL(
                "https://emma.pixnet.cc/blog/articles?access_token="
                        + access_token + "&title="
                        + URLEncoder.encode(title, "UTF-8") + "&body="
                        + URLEncoder.encode(body, "UTF-8") + param);
        // System.out.println(urlPost.toString());
        HttpURLConnection con = (HttpURLConnection) urlPost.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        con.setRequestMethod("POST");
        con.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                con.getInputStream(), "UTF-8"));
        return br.readLine();
    }

    /**
     *
     * @param code
     *            Code from RequestUrl
     * @return Set code and Get AccessToken
     * @throws IOException
     * @throws JSONException
     */
    public String getAccessToken(String code) throws IOException, JSONException {
        String urlgrant = "https://emma.pixnet.cc/oauth2/grant?grant_type=authorization_code&code="
                + code
                + "&redirect_uri="
                + redirect_uri
                + "&client_id="
                + client_id + "&client_secret=" + client_secret;
        URL url = new URL(urlgrant);
        URLConnection conn = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), "UTF-8"));
        String json = br.readLine();
        JSONObject obj = new JSONObject(json);
        access_token = (String) obj.get("access_token");
        return access_token;
    }
}

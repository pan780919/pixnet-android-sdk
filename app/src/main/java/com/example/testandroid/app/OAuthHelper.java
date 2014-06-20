package com.example.testandroid.app;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * OAuth connection tool
 */
public class OAuthHelper {

    private static final String URL_AUTH = "https://emma.pixnet.cc/oauth2/authorize";
    private static final String URL_GRANT = "https://emma.pixnet.cc/oauth2/grant";

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
     * @throws java.io.IOException
     * @throws org.json.JSONException
     */
    public OAuthHelper(String client_id, String client_secret,
                              String redirect_uri) throws IOException, JSONException {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.redirect_uri = redirect_uri;
    }


    /**
     * The url that can auth and return the code for access token
     *
     * @return formatted request url
     */
    private String getRequestUrl() {
        return URL_AUTH
                + "?client_id=" + client_id
                + "&redirect_uri=" + redirect_uri
                + "&response_type=code";
    }

    /**
     * format grant url with parameters
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
     * @throws java.io.IOException
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
     * @throws java.io.IOException
     * @throws org.json.JSONException
     */
    public String getAccessToken(String code) throws IOException, JSONException {
        String urlgrant = getGrantUrl(code);
        URL url = new URL(urlgrant);
        String response = new HttpHelper().get(URL_GRANT);
        //URLConnection conn = url.openConnection();
        //BufferedReader br = new BufferedReader(new InputStreamReader(
        //        conn.getInputStream(), "UTF-8"));
        //String json = br.readLine();
        //JSONObject obj = new JSONObject(json);
        //access_token = (String) obj.get("access_token");
        return response;
    }
}

package net.pixnet.sdk.utils;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * OAuth connection tool
 */
public class OAuthHelper {

    private static final String URL_AUTH = "https://emma.pixnet.cc/oauth2/authorize";
    private static final String URL_GRANT = "https://emma.pixnet.cc/oauth2/grant";
    private static final String URL_POST_ARTICLES="https://emma.pixnet.cc/blog/articles";
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
     * @throws IOException
     */
    public String post(String access_token, String title, String body,
                       List<NameValuePair> param) throws IOException {
        final String inaccess_token = access_token;
        final String intitle = title;
        final String inbody  = body;
        URL urlPost = new URL(
                "https://emma.pixnet.cc/blog/articles?access_token="
                        + access_token + "&title="
                        + URLEncoder.encode(title, "UTF-8") + "&body="
                        + URLEncoder.encode(body, "UTF-8") + param);
        List<NameValuePair> params;
        if(param==null) {
            params = new ArrayList<NameValuePair>();
        }else{
            params = param;
        }
        params.add(new NameValuePair() {
            public String getName() {
                return "access_token";
            }
            public String getValue() {
                return inaccess_token;
            }
        });
        params.add(new NameValuePair() {
            public String getName() {
                return "title";
            }
            public String getValue() {
                return intitle;
            }
        });
        params.add(new NameValuePair() {
            public String getName() {
                return "body";
            }
            public String getValue() {
                return inbody;
            }
        });
        String response = new HttpHelper().post(URL_POST_ARTICLES,params);
        // System.out.println(urlPost.toString());
        return response;
    }

    /**
     *
     * @param code
     *            Code from RequestUrl
     * @return Set code and Get AccessToken
     */
    public String getAccessToken(String code)  {
        final String incode = code;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair() {
            public String getName() {
                return "grant_type";
            }
            public String getValue() {
                return "authorization_code";
            }
        });
        params.add(new NameValuePair() {
            public String getName() {
                return "code";
            }
            public String getValue() {
                return incode;
            }
        });
        params.add(new NameValuePair() {
            public String getName() {
                return "redirect_uri";
            }
            public String getValue() {
                return redirect_uri;
            }
        });
        params.add(new NameValuePair() {
            public String getName() {
                return "client_id";
            }
            public String getValue() {
                return client_id;
            }
        });
        params.add(new NameValuePair() {
            public String getName() {
                return "client_secret";
            }
            public String getValue() {
                return client_secret;
            }
        });
        try {
            String response = new HttpHelper().get(URL_GRANT, params);
            JSONObject obj = new JSONObject(response);
            access_token = (String) obj.get("access_token");
        }catch(JSONException e){
            access_token = "Code error";
        }
        return  access_token;
    }
}

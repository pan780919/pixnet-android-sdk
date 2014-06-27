package net.pixnet.sdk.utils;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * OAuth connection tool
 */
public class OAuthHelper {

    private static final String URL_AUTH = "https://emma.pixnet.cc/oauth2/authorize";
    private static final String URL_GRANT = "https://emma.pixnet.cc/oauth2/grant";
    private static final String URL_POST_ARTICLES = "https://emma.pixnet.cc/blog/articles";
    private String access_token = "";
    private String client_id = "";
    private String client_secret = "";
    private String redirect_uri = "";
    private HttpHelper hh;

    /**
     * Constructor
     *
     * @param client_id     your client id
     * @param client_secret your client secret
     * @param redirect_uri  your redirect url
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
     * Post Article
     *
     * @param access_token Access_token
     * @param param        other param add with &type=param_value
     * @return The return String from server
     * @throws java.io.IOException
     */
    public String post(String postUrl, String access_token, List<NameValuePair> param) throws IOException {
        final String inaccess_token = access_token;
        List<NameValuePair> params;
        if (param == null) {
            params = new ArrayList<NameValuePair>();
        } else {
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
        hh = new HttpHelper();
        hh.timeout_connection = 10000;
        hh.timeout_socket = 30000;
        String response = hh.post(postUrl, params);
        // System.out.println(urlPost.toString());
        return response;
    }

    /**
     * @param code Code from RequestUrl
     * @return Set code and Get AccessToken
     */
    public String getAccessToken(String code) {
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
        } catch (JSONException e) {
            access_token = "Code error";
        }
        return access_token;
    }

    public void cancel() {
        if(hh!=null){
            hh.cancel();
        }
    }
}

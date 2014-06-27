package net.pixnet.sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * http connection tools
 */
public class HttpHelper {

    /**
     * default connection timeout
     */
    public static final int DEF_TIMEOUT_CONNECTION=3000;
    /**
     * default socket timeout
     */
    public static final int DEF_TIMEOUT_SOCKET=5000;

    public int timeout_connection=DEF_TIMEOUT_CONNECTION;
    public int timeout_socket=DEF_TIMEOUT_SOCKET;

    /**
     * detect network is available or no
     * @param ctx is a Context
     * @return true if the network is fine or false if network don't work
     */
    public static boolean isAvailable(Context ctx){
        ConnectivityManager cm= (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=cm.getActiveNetworkInfo();
        if(info==null)
            return false;

        if(info.isAvailable())
            return true;
        else return false;
    }

    /**
     * detect network type is WIFI or not
     * @param ctx is a Context
     * @return true with currently network type is WIFI or false if others
     */
    public static boolean isWIFI(Context ctx){
        ConnectivityManager cm= (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=cm.getActiveNetworkInfo();
        if(info==null)
            return false;

        int type=info.getType();
        if(type==ConnectivityManager.TYPE_WIFI || type==ConnectivityManager.TYPE_WIMAX)
            return true;
        else return false;
    }

    /**
     * get without parameter and header
     */
    public String get(String url){
        return get(url, null, null);
    }

    /**
     * get without header
     */
    public String get(String url, ArrayList<NameValuePair> params){
        return get(url, params, null);
    }

    /**
     * get without parameter
     */
    public String get(String url, Header[] headers){
        return get(url, null, headers);
    }

    /**
     * perform http get method
     * @param url
     * @param params
     * @param headers
     * @return a string result
     */
    public String get(String url, ArrayList<NameValuePair> params, Header[] headers){
        url=formatUrl(url, params);
        Helper.log("GET:"+url);

        HttpGet get=new HttpGet(url);
        if(headers!=null) get.setHeaders(headers);
        InputStream in=request(get);

        return getStringFromInputStream(in);
    }

    /**
     * post without header
     */
    public String post(String url, List<NameValuePair> params){
        return post(url, params, null);
    }

    /**
     * post without parameter
     */
    public String post(String url, Header[] headers){
        return post(url, null, headers);
    }

    /**
     * perform http post method
     * @param url
     * @param params
     * @param headers
     * @return a string result
     */
    public String post(String url, List<NameValuePair> params, Header[] headers){
        url=formatUrl(url);
        Helper.log("POST:"+url);

        HttpPost post=new HttpPost(url);
        post.setHeaders(headers);
        if(params!=null){
            HttpEntity entity=null;
            try {
                entity=new UrlEncodedFormEntity(params, HTTP.UTF_8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if(entity!=null) post.setEntity(entity);
        }
        InputStream in=request(post);

        return getStringFromInputStream(in);
    }

    /**
     * delete without parameter
     */
    public String delete(String url, Header[] headers){
        return delete(url, null, headers);
    }

    /**
     * perform http delete method
     * @param url
     * @param params
     * @param headers
     * @return a string result
     */
    public String delete(String url, ArrayList<NameValuePair> params, Header[] headers){
        url=formatUrl(url, params);
        Helper.log("DELETE:"+url);
        HttpDelete del=new HttpDelete(url);
        del.setHeaders(headers);
        InputStream in=request(del);

        return getStringFromInputStream(in);
    }

    /**
     * formatUrl without parameter
     */
    private String formatUrl(String url){
        return formatUrl(url, null);
    }

    /**
     * append query string and prepend "http:" to url if need
     * @param url
     * @param params
     * @return
     */
    private String formatUrl(String url, ArrayList<NameValuePair> params){
        //ex. //example.com/xxx
        if(url.indexOf("http")<0)
            url="http:"+url;

        if(params!=null){
            String qs="";
            for(NameValuePair nv:params){
                if(qs.length()==0)
                    qs+="?";
                else
                    qs+="&";
                qs+=nv.getName()+"="+nv.getValue();
            }
            url+=qs;
        }

        return url;
    }

    private InputStream request(HttpUriRequest request){
        HttpClient client=createHttpClient();
        HttpResponse response=null;
        try {
            response=client.execute(request);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response==null) return null;

        HttpEntity entity=response.getEntity();
        InputStream in=null;
        try {
            in=entity.getContent();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return in;
    }

    private String getStringFromInputStream(InputStream in){
        if(in==null) return null;

        byte []data = new byte[1024];
        int length;
        ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
        try {
            while( (length = in.read(data)) != -1 )
                mByteArrayOutputStream.write(data, 0, length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(mByteArrayOutputStream.toByteArray());
    }

    private HttpClient createHttpClient(){
        HttpParams params = new BasicHttpParams();

        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);

        HttpConnectionParams.setConnectionTimeout(params, timeout_connection);
        HttpConnectionParams.setSoTimeout(params, timeout_socket);

        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);

        return new DefaultHttpClient(conMgr, params);
    }

    public void cancel(){
    }

    public static HashMap<String, String> parseParamsByResponse(String response){
        HashMap<String, String> map=new HashMap<String, String>();

        String[] strAry=response.split("&");
        int i=0, len=strAry.length;
        while(i<len){
            String tmpStr=strAry[i];
            int tmpIdx=tmpStr.indexOf("=");
            if(tmpIdx<1 || tmpIdx>(tmpStr.length()-2)) continue;

            String[] tmpAry=tmpStr.split("=");
            map.put(tmpAry[0], tmpAry[1]);
            i++;
        }

        return map;
    }

    /**
     * url encode by utf-8
     * @param url
     * @return encoded url
     */
    public static String encodeUrl(String url){
        String str;
        try {
            str = URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            str=url;
        }
        return str;
    }
}

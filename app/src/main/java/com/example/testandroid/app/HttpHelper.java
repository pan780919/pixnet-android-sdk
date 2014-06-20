package com.example.testandroid.app;

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
import java.util.List;

/**
 * http connection tools
 */
public class HttpHelper {

    public static final int DEF_TIMEOUT_CONNECTION=3000;
    public static final int DEF_TIMEOUT_SOCKET=5000;

    public int timeout_connection=DEF_TIMEOUT_SOCKET;
    public int timeout_socket=DEF_TIMEOUT_SOCKET;

    public static boolean isAvailable(Context ctx){
        ConnectivityManager cm= (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=cm.getActiveNetworkInfo();
        if(info==null)
            return false;

        if(info.isAvailable())
            return true;
        else return false;
    }

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

    public String get(String url){
        return get(url, null, null);
    }
    public String get(String url, ArrayList<NameValuePair> params){
        return get(url, params, null);
    }
    public String get(String url, Header[] headers){
        return get(url, null, headers);
    }
    public String get(String url, ArrayList<NameValuePair> params, Header[] headers){
        url=formatUrl(url, params);
        Helper.log("GET:"+url);

        HttpGet get=new HttpGet(url);
        if(headers!=null) get.setHeaders(headers);
        InputStream in=request(get);

        return getStringFromInputStream(in);
    }

    public String post(String url, List<NameValuePair> params){
        return post(url, params, null);
    }
    public String post(String url, Header[] headers){
        return post(url, null, headers);
    }
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

    public String delete(String url, Header[] headers){
        return delete(url, null, headers);
    }
    public String delete(String url, ArrayList<NameValuePair> params, Header[] headers){
        url=formatUrl(url, params);
        Helper.log("DELETE:"+url);
        HttpDelete del=new HttpDelete(url);
        del.setHeaders(headers);
        InputStream in=request(del);

        return getStringFromInputStream(in);
    }

    private String formatUrl(String url){
        return formatUrl(url, null);
    }
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

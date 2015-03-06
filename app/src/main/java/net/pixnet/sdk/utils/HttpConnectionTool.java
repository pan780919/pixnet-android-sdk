package net.pixnet.sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * http connection tools
 */
public class HttpConnectionTool implements ConnectionTool {

    /**
     * default connection timeout
     */
    public static final int DEF_TIMEOUT_CONNECTION = 5000;
    /**
     * default socket timeout
     */
    public static final int DEF_TIMEOUT_SOCKET = 10000;

    public int timeout_connection = DEF_TIMEOUT_CONNECTION;
    public int timeout_socket = DEF_TIMEOUT_SOCKET;
    private HttpUriRequest requestObj;
    private boolean userStop = false;

    /**
     * detect network is available or no
     *
     * @param ctx is a Context
     * @return true if the network is fine or false if network don't work
     */
    public static boolean isAvailable(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null)
            return false;

        if (info.isAvailable())
            return true;
        else return false;
    }

    /**
     * detect network type is WIFI or not
     *
     * @param ctx is a Context
     * @return true with currently network type is WIFI or false if others
     */
    public static boolean isWIFI(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null)
            return false;

        int type = info.getType();
        if (type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_WIMAX)
            return true;
        else return false;
    }

    /**
     * prepend "http:" to url if need
     *
     * @param url
     * @return
     */
    private String formatUrl(String url) {
        //ex. //example.com/xxx
        if (url.indexOf("http") < 0)
            url = "http:" + url;

        return url;
    }

    /**
     * append query string
     *
     * @param url
     * @param params
     * @return
     */
    private String appendQueryString(String url, List<NameValuePair> params){
        if (params != null) {
            String qs = "";
            for (NameValuePair nv : params) {
                if (qs.length() == 0)
                    qs += "?";
                else
                    qs += "&";
                qs += nv.getName() + "=" + nv.getValue();
            }
            url += qs;
        }
        return url;
    }

    protected InputStream request(HttpUriRequest request) throws IOException {
        requestObj = request;
        userStop = false;
        HttpClient client = createHttpClient();
        HttpResponse response = null;
        response = client.execute(request);
        if (response == null || userStop)
            return null;
        return  response.getEntity().getContent();
    }

    private String getStringFromInputStream(InputStream in) throws IOException {
        if (in == null) return null;

        byte[] data = new byte[1024];
        int length;
        ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
        while ((length = in.read(data)) != -1)
            mByteArrayOutputStream.write(data, 0, length);

        return new String(mByteArrayOutputStream.toByteArray());
    }

    private HttpClient createHttpClient() {
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

    public void cancel() {
        if (requestObj != null) {
            userStop = true;
            requestObj.abort();
        }
    }

    public static HashMap<String, String> parseParamsByResponse(String response){
        HashMap<String, String> map=new HashMap<>();

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
     *
     * @param url
     * @return encoded url
     */
    public static String encodeUrl(String url) {
        String str;
        try {
            str = URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            str = url;
        }
        str=str.replace("+", "%20");
        return str;
    }

    /**
     * url decode by utf-8
     *
     * @param url
     * @return decoded url
     */
    public static String decodeUrl(String url){
        String str;
        try {
            str = URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            str = url;
        }
        return str;
    }

    @Override
    public String performRequest(Request request) throws IOException {
        Helper.log("performRequest");
        String url=formatUrl(request.getUrl());
        Helper.log(request.getMethod().name());
        Helper.log(url);
        List<NameValuePair> params=request.getParams();
        List<NameValuePair> headerList=request.getHeaders();
        List<FileNameValuePair> files=request.getFiles();

        Request.Method method = request.getMethod();
        if(method != Request.Method.POST)
            url=appendQueryString(url, params);
        HttpUriRequest hur = getRequest(request);

        Header[] headers=null;
        if(headerList!=null){
            int i=0, len=headerList.size();
            if(len>0) {
                headers = new Header[len];
                while (i < len) {
                    NameValuePair header = headerList.get(i);
                    headers[i] = new BasicHeader(header.getName(), header.getValue());
                    i++;
                }
            }
        }
        if (headers != null)
            hur.setHeaders(headers);

        if(method == Request.Method.POST) {
            if (params != null) {
                HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                ((HttpPost) hur).setEntity(entity);
            }
        }

        onRequestReady(hur);

        if(method == Request.Method.POST && files!=null) {
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.STRICT);

            ContentType contentType=ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);

            if (params != null)
                for(NameValuePair v : params)
                    entityBuilder.addTextBody(v.getName(), v.getValue(), contentType);
            for (FileNameValuePair v : files)
                entityBuilder.addBinaryBody(v.getName(), v.getValue(), ContentType.DEFAULT_BINARY, v.getValue().getName());
            HttpEntity entity = entityBuilder.build();
            if (entity != null)
                ((HttpPost)hur).setEntity(entity);
        }

        InputStream in = request(hur);
        return getStringFromInputStream(in);
    }

    protected void onRequestReady(HttpUriRequest hur) {}

    protected HttpUriRequest getRequest(Request request){
        Request.Method method=request.getMethod();
        String url=method==Request.Method.POST?request.getUrl():appendQueryString(request.getUrl(), request.getParams());
        switch (request.getMethod()){
            case GET:
                return new HttpGet(url);
            case POST:
                return new HttpPost(url);
            case PUT:
                return new HttpPut(url);
            case DELETE:
                return new HttpDelete(url);
            default:
                return null;
        }
    }
}

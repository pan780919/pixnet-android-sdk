package net.pixnet.sdk.utils;

import android.text.TextUtils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.proxy.Error;
import net.pixnet.sdk.response.AlbumMainPage;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.CommentList;
import net.pixnet.sdk.response.Element;
import net.pixnet.sdk.response.ElementList;
import net.pixnet.sdk.response.Folder;
import net.pixnet.sdk.response.FolderList;
import net.pixnet.sdk.response.Set;
import net.pixnet.sdk.response.SetAndFolderList;
import net.pixnet.sdk.response.SetList;
import net.pixnet.sdk.utils.Request.Method;
import net.pixnet.sdk.utils.Request.RequestCallback;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AlbumHelper extends DataProxy {

    private static final String URL_MAIN="https://emma.pixnet.cc/album/main";
    private static final String URL_SETFOLDERS="https://emma.pixnet.cc/album/setfolders";
    private static final String URL_SET="https://emma.pixnet.cc/album/sets";
    private static final String URL_FOLDER="https://emma.pixnet.cc/album/folders";
    private static final String URL_ELEMENTS="https://emma.pixnet.cc/album/elements";
    private static final String URL_SET_COMMENT="https://emma.pixnet.cc/album/set_comments";
    private static final String URL_COMMENT="https://emma.pixnet.cc/album/comments";
    private static final String URL_SORT_SETFOLDERS="https://emma.pixnet.cc/album/setfolders/position";
    private static final String URL_SORT_SETS="https://emma.pixnet.cc/album/sets/position";
    private static final String URL_SORT_ELEMENTS="https://emma.pixnet.cc/album/elements/position";
    private static final String URL_SETS_NEAR="https://emma.pixnet.cc/album/sets/nearby";
    private static final String URL_FACES="https://emma.pixnet.cc/album/faces";
    private static final String URL_ALBUM_CONIFG="https://emma.pixnet.cc/album/config";
    private static final String URL_ELEMENTS_NEARBY = "https://emma.pixnet.cc/album/elements/nearby";

    @Override
    protected boolean handleBasicResponse(String response) {
        if(super.handleBasicResponse(response))
            return true;
        if(listener instanceof AlbumHelperListener)
            return false;
        return true;
    }

    public static enum ElementType{
        pic,
        video,
        audio
    }

    public static enum Permission{
        all,
        friend,
        password,
        hidden,
        friendGroup
    }

    public static enum CommentPermission{
        denied,
        allow,
        friend,
        member
    }


    /**
     * 列出相簿主圖及相片牆, 需要認證
     */
    public void getMain(){
        performAPIRequest(true, URL_MAIN, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                AlbumMainPage parsedResponse;
                try {
                    parsedResponse=new AlbumMainPage(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onGetMain(parsedResponse);
            }
        });
    }

    /**
     * 列出相簿全站分類
     */
    public void getAlbumCategoryList(){}

    /**
     * @see #getSetAndFolderList(String, int, int, boolean)
     */
    public void getSetAndFolderList(){
        getSetAndFolderList(1);
    }
    /**
     * @see #getSetAndFolderList(String, int, int, boolean)
     */
    public void getSetAndFolderList(int page){
        getSetAndFolderList(defaultUserName, defaultPerPage, page, defaultTrimUser);
    }
    /**
     * 相簿首頁 (與 http://:user.pixnet.net/album/list 同步)
     * @param userName 指定要回傳的使用者資訊
     * @param perPage 每頁幾筆
     * @param page 頁數
     * @param trimUser 是否每篇文章都要回傳作者資訊, 如果設定為 true, 則就不回傳.
     */
    public void getSetAndFolderList(String userName, int perPage, int page, boolean trimUser){
        if(userName==null || TextUtils.isEmpty(userName)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        params.add(new BasicNameValuePair("per_page", String.valueOf(perPage)));
        params.add(new BasicNameValuePair("page", String.valueOf(page)));
        params.add(new BasicNameValuePair("trim_user", trimUser?"1":"0"));

        performAPIRequest(false, URL_SETFOLDERS, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                SetAndFolderList parsedResponse;
                try {
                    parsedResponse=new SetAndFolderList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onGetSetAndFolderList(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #getFolder(String, String, int, int)
     */
    public void getFolder(String folderId){
        getFolder(folderId, 1);
    }
    /**
     * @see #getFolder(String, String, int, int)
     */
    public void getFolder(String folderId, int page){
        getFolder(folderId, defaultUserName, defaultPerPage, page);
    }
    /**
     * 讀取個人相簿單一資料夾
     * @param folderId folder id
     * @param userName 指定要回傳的使用者資訊
     * @param perPage 每頁幾筆
     * @param page 頁數
     */
    public void getFolder(String folderId, String userName, int perPage, int page){
        if(TextUtils.isEmpty(userName)) {
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        params.add(new BasicNameValuePair("per_page", String.valueOf(perPage)));
        params.add(new BasicNameValuePair("page", String.valueOf(page)));

        performAPIRequest(false, URL_FOLDER+"/"+folderId, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Folder parsedResponse;
                try {
                    parsedResponse=new Folder(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onGetFolder(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #getFolderList(String, int, int, boolean)
     */
    public void getFolderList(){
        getFolderList(1);
    }
    /**
     * @see #getFolderList(String, int, int, boolean)
     */
    public void getFolderList(int page){
        getFolderList(defaultUserName, defaultPerPage, page, defaultTrimUser);
    }
    /**
     * 列出個人相簿資料夾
     * @param userName 指定要回傳的使用者資訊
     * @param perPage 每頁幾筆
     * @param page 頁數
     * @param trimUser 是否取消每篇文章都要回傳作者資訊, 如果設定為 true, 則就不回傳
     */
    public void getFolderList(String userName, int perPage, int page, boolean trimUser){
        if(TextUtils.isEmpty(userName)) {
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        params.add(new BasicNameValuePair("per_page", String.valueOf(perPage)));
        params.add(new BasicNameValuePair("page", String.valueOf(page)));
        params.add(new BasicNameValuePair("trim_user", trimUser?"1":"0"));

        performAPIRequest(false, URL_FOLDER, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                FolderList parsedResponse;
                try {
                    parsedResponse=new FolderList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onGetFolderList(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #getSet(String, String, int, int)
     */
    public void getSet(String id){
        getSet(id, 1);
    }
    /**
     * @see #getSet(String, String, int, int)
     */
    public void getSet(String id, int page){
        getSet(defaultUserName, id, defaultPerPage, page);
    }
    /**
     * 讀取個人相簿單一 Set
     * @param userName 指定要回傳的使用者資訊
     * @param perPage 每頁幾筆
     * @param page 頁數
     */
    public void getSet(String userName, String id, int perPage, int page){
        if(userName==null || TextUtils.isEmpty(userName)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        params.add(new BasicNameValuePair("per_page", String.valueOf(perPage)));
        params.add(new BasicNameValuePair("page", String.valueOf(page)));

        performAPIRequest(false, URL_SET+"/"+id, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Set parsedResponse;
                try {
                    parsedResponse=new Set(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onGetSet(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #getSetList(String, String, boolean, int, int)
     */
    public void getSetList(){
        getSetList(1);
    }
    /**
     * @see #getSetList(String, String, boolean, int, int)
     */
    public void getSetList(String parentId){
        getSetList(parentId, 1);
    }
    /**
     * @see #getSetList(String, String, boolean, int, int)
     */
    public void getSetList(int page){
        getSetList(null, page);
    }
    /**
     * @see #getSetList(String, String, boolean, int, int)
     */
    public void getSetList(String parentId, int page){
        getSetList(defaultUserName, parentId, defaultTrimUser, defaultPerPage, page);
    }
    /**
     * 列出相簿個人 Set 清單
     * @param userName 指定要回傳的使用者資訊
     * @param parentId 可以藉此指定拿到特定相簿資料夾底下的相簿
     * @param trimUser 是否每篇文章都要回傳作者資訊, 如果設定為 true, 則就不回傳
     * @param perPage 頁數
     * @param page 每頁幾筆
     */
    public void getSetList(String userName, String parentId, boolean trimUser, int perPage, int page){
        if(TextUtils.isEmpty(userName)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        if(!TextUtils.isEmpty(parentId))
            params.add(new BasicNameValuePair("parent_id", parentId));
        params.add(new BasicNameValuePair("trim_user", trimUser?"1":"0"));
        params.add(new BasicNameValuePair("per_page", String.valueOf(perPage)));
        params.add(new BasicNameValuePair("page", String.valueOf(page)));

        performAPIRequest(false, URL_SET, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                SetList parsedResponse;
                try {
                    parsedResponse=new SetList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onGetSetList(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #getSetListByNear(String, double, double, int, int, int, int, boolean)
     */
    public void getSetListByNear(double lat, double lng){
        getSetListByNear(lat, lng, 1);
    }
    /**
     * @see #getSetListByNear(String, double, double, int, int, int, int, boolean)
     */
    public void getSetListByNear(double lat, double lng, int page){
        getSetListByNear(lat, lng, 0, page);
    }
    /**
     * @see #getSetListByNear(String, double, double, int, int, int, int, boolean)
     */
    public void getSetListByNear(double lat, double lng, int maxDistance, int page){
        getSetListByNear(lat, lng, 0, maxDistance, page);
    }
    /**
     * @see #getSetListByNear(String, double, double, int, int, int, int, boolean)
     */
    public void getSetListByNear(double lat, double lng, int minDistance, int maxDistance, int page){
        getSetListByNear(defaultUserName, lat, lng, minDistance, maxDistance, defaultPerPage, page, defaultTrimUser);
    }
    /**
     * 列出附近的相簿 Set
     * @param userName 指定要回傳的使用者資訊
     * @param lat WGS84 坐標系緯度，例如 23.973875
     * @param lng WGS84 坐標系經度，例如 120.982024
     * @param minDistance 回傳相簿所在地和指定的經緯度距離之最小值，單位為公尺，上限為 50000 公尺
     * @param maxDistance 回傳相簿所在地和指定的經緯度距離之最大值，單位為公尺，上限為 50000 公尺
     * @param perPage 頁數
     * @param page 每頁幾筆
     * @param trimUser 是否取消每本相簿都要回傳使用者資訊，若設定為 true 則不回傳
     */
    public void getSetListByNear(String userName, double lat, double lng, int minDistance, int maxDistance, int perPage, int page, boolean trimUser){
        if(TextUtils.isEmpty(userName)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        params.add(new BasicNameValuePair("lat", String.valueOf(lat)));
        params.add(new BasicNameValuePair("lon", String.valueOf(lng)));
        params.add(new BasicNameValuePair("distance_min", String.valueOf(minDistance)));
        params.add(new BasicNameValuePair("distance_max", String.valueOf(maxDistance)));
        params.add(new BasicNameValuePair("per_page", String.valueOf(perPage)));
        params.add(new BasicNameValuePair("page", String.valueOf(page)));

        performAPIRequest(false, URL_SETS_NEAR, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                SetList parsedResponse;
                try {
                    parsedResponse=new SetList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onGetSetListByNear(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #getElement(String, String, boolean, boolean, int, int)
     */
    public void getElement(String elementId){
        getElement(elementId, defaultUserName, false, false, 0, 0);
    }
    /**
     * 讀取相簿單篇圖片影片
     * @param userName 指定要回傳的使用者資訊
     * @param withSet 回傳這張相片所屬的相簿資訊
     * @param useIframe 影音的外嵌 tag 使用 iframe 格式
     * @param iframeWidth 影音的外嵌 iframe width
     * @param iframeHeight 影音的外嵌 iframe height
     */
    public void getElement(String elementId, String userName, boolean withSet, boolean useIframe, int iframeWidth, int iframeHeight){
        if(TextUtils.isEmpty(elementId) || TextUtils.isEmpty(userName)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        params.add(new BasicNameValuePair("withSet", withSet?"1":"0"));
        params.add(new BasicNameValuePair("use_iframe", useIframe?"1":"0"));
        if(iframeWidth>0)
            params.add(new BasicNameValuePair("iframe_width", String.valueOf(iframeWidth)));
        if(iframeHeight>0)
            params.add(new BasicNameValuePair("iframe_height", String.valueOf(iframeHeight)));

        performAPIRequest(false, URL_ELEMENTS+"/"+elementId, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Element parsedResponse;
                try {
                    parsedResponse=new Element(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onGetElement(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #getElementListBySet(String, String, AlbumHelper.ElementType, int, int, String, boolean, boolean, boolean, int, int)
     */
    public void getElementListBySet(String setId){
        getElementListBySet(setId, 1);
    }
    /**
     * @see #getElementListBySet(String, String, AlbumHelper.ElementType, int, int, String, boolean, boolean, boolean, int, int)
     */
    public void getElementListBySet(String setId, int page){
        getElementListBySet(setId, ElementType.pic, page);
    }
    /**
     * @see #getElementListBySet(String, String, AlbumHelper.ElementType, int, int, String, boolean, boolean, boolean, int, int)
     */
    public void getElementListBySet(String setId, ElementType type, int page){
        getElementListBySet(setId, type, page, false);
    }
    /**
     * @see #getElementListBySet(String, String, AlbumHelper.ElementType, int, int, String, boolean, boolean, boolean, int, int)
     */
    public void getElementListBySet(String setId, ElementType type, int page, boolean withDetail){
        getElementListBySet(defaultUserName, setId, type, defaultPerPage, page, null, withDetail, defaultTrimUser, false, 0, 0);
    }
    /**
     * 列出單一相簿 Set 的所有相片
     * @param userName 指定要回傳的使用者資訊
     * @param setId 相簿 id
     * @param type 指定要回傳的類別. pic: 只顯示圖片; video: 只顯示影片; audio: 只顯示音樂
     * @param perPage 每頁幾筆
     * @param page 頁數
     * @param password 相簿密碼，當使用者相簿設定為密碼相簿時使用
     * @param withDetail 傳回詳細資訊，指定為1時將會回傳完整圖片資訊
     * @param trimUser 不傳回相片擁有者資訊，指定為 true 時將不會回傳相片擁有者資訊
     * @param useIframe 影音的外嵌 tag 使用 iframe 格式
     * @param iframeWidth 影音的外嵌 iframe width
     * @param iframeHeight 影音的外嵌 iframe height
     */
    public void getElementListBySet(String userName, String setId, ElementType type, int perPage, int page, String password, boolean withDetail, boolean trimUser, boolean useIframe, int iframeWidth, int iframeHeight){
        if(TextUtils.isEmpty(userName)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        if(TextUtils.isEmpty(setId)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        params.add(new BasicNameValuePair("set_id", setId));
        params.add(new BasicNameValuePair("type", type.name()));
        params.add(new BasicNameValuePair("per_page", String.valueOf(perPage)));
        params.add(new BasicNameValuePair("page", String.valueOf(page)));
        if(!TextUtils.isEmpty(password))
            params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("with_detail", withDetail?"1":"0"));
        params.add(new BasicNameValuePair("trim_user", trimUser?"1":"0"));
        params.add(new BasicNameValuePair("use_iframe", useIframe?"1":"0"));
        if(iframeWidth>0)
            params.add(new BasicNameValuePair("iframe_width", String.valueOf(iframeWidth)));
        if(iframeHeight>0)
            params.add(new BasicNameValuePair("iframe_height", String.valueOf(iframeHeight)));

        performAPIRequest(false, URL_ELEMENTS, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                ElementList parsedResponse;
                try {
                    parsedResponse=new ElementList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onGetElementListBySet(parsedResponse);
            }
        }, params);
    }

    /**
     * 列出附近的圖片影片
     * @param userName 指定要回傳的使用者名稱
     * @param lat WGS84 坐標系緯度，例如 25.058172
     * @param lng WGS84 坐標系經度，例如 121.535304
     * @param distanceMin 回傳相片／影音所在地和指定經緯度距離之最小值，單位為公尺。最小為 0 公尺，上限為 50000 公尺
     * @param distanceMax 回傳相片／影音所在地和指定經緯度距離之最大值，單位為公尺。最小為 0 公尺，上限為 50000 公尺
     * @param perPage 頁數
     * @param page 每頁幾筆
     * @param withDetail 是否傳回詳細資訊, 指定為 true 時將傳回相片／影音完整資訊及所屬相簿資訊
     * @param type 指定要回傳的類別, pic ：只顯示圖片， video ：只顯示影片
     * @param trimUesr 是否取消每個相片／影音都要回傳使用者資訊，若設定為 true 則不回傳
     */
    public void getElementListByNear(String userName, double lat, double lng, int distanceMin, int distanceMax, int perPage, int page, boolean withDetail, ElementType type, boolean trimUesr){
        if(TextUtils.isEmpty(userName)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        if(lat<0 || lng<0){
            listener.onError(Error.PARAMETER_INVALID+":lat, lng");
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        params.add(new BasicNameValuePair("lat", String.valueOf(lat)));
        params.add(new BasicNameValuePair("lon", String.valueOf(lng)));
        if(distanceMin>0 && distanceMin<=50000)
            params.add(new BasicNameValuePair("distance_min", String.valueOf(distanceMin)));
        if(distanceMax>0 && distanceMax<=50000)
            params.add(new BasicNameValuePair("distance_max", String.valueOf(distanceMax)));
        if(page>0)
            params.add(new BasicNameValuePair("page", String.valueOf(page)));
        if(perPage>0)
            params.add(new BasicNameValuePair("perPage", String.valueOf(perPage)));
        if(withDetail)
           params.add(new BasicNameValuePair("with_detail", "1"));
        params.add(new BasicNameValuePair("type", type.name()));
        if(trimUesr)
            params.add(new BasicNameValuePair("trim_user","1"));
        performAPIRequest(false, URL_ELEMENTS_NEARBY, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                ElementList parsedResponse;
                try {
                    parsedResponse=new ElementList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onGetElementListByNear(parsedResponse);
            }
        }, params);
    }

    /**
     * 讀取 Set 單一留言
     */
    public void getCommentForSet(){}

    /**
     * 讀取相片單一留言
     */
    public void getCommentForElement(){}

    /**
     * @see #getCommentListBySet(String, String, String, int, int)
     */
    public void getCommentListBySet(String setId){
        getCommentListByrSet(setId, 1);
    }
    /**
     * @see #getCommentListBySet(String, String, String, int, int)
     */
    public void getCommentListByrSet(String setId, int page){
        getCommentListBySet(setId, null, page);
    }
    /**
     * @see #getCommentListBySet(String, String, String, int, int)
     */
    public void getCommentListBySet(String setId, String password, int page){
        getCommentListBySet(defaultUserName, setId, password, defaultPerPage, page);
    }
    /**
     * 列出 Set 的留言
     * @param userName 指定要回傳的使用者資訊
     * @param setId 指定要回傳的留言所在的相簿 set id
     * @param password 如果指定使用者的相簿被密碼保護，則需要指定這個參數以通過授權
     * @param perPage 每頁幾筆
     * @param page 頁數
     */
    public void getCommentListBySet(String userName, String setId, String password, int perPage, int page){
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(setId)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        params.add(new BasicNameValuePair("set_id", setId));
        params.add(new BasicNameValuePair("per_page", String.valueOf(perPage)));
        params.add(new BasicNameValuePair("page", String.valueOf(page)));
        if(!TextUtils.isEmpty(password))
            params.add(new BasicNameValuePair("password", password));

        performAPIRequest(false, URL_SET_COMMENT, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                CommentList parsedResponse;
                try {
                    parsedResponse=new CommentList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onGetCommentListBySet(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #getCommentListByElement(String, String, boolean, String, int, int)
     */
    public void getCommentListByElement(String id){
        getCommentListByElement(id, 1);
    }
    /**
     * @see #getCommentListByElement(String, String, boolean, String, int, int)
     */
    public void getCommentListByElement(String id, int page){
        getCommentListByElement(id, false, page);
    }
    /**
     * @see #getCommentListByElement(String, String, boolean, String, int, int)
     */
    public void getCommentListByElement(String id, boolean isSet, int page){
        getCommentListByElement(defaultUserName, id, isSet, null, defaultPerPage, page);
    }
    /**
     * 列出相片留言
     * @param userName 指定要回傳的使用者資訊
     * @param id 指定要回傳的留言相片或相簿 id
     * @param isSet true: id 為 set id, false: id 為 element id
     * @param password 如果指定使用者的相簿被密碼保護，則需要指定這個參數以通過授權
     * @param perPage 每頁幾筆
     * @param page 頁數
     */
    public void getCommentListByElement(String userName, String id, boolean isSet, String password, int perPage, int page){
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(id)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        if(isSet) params.add(new BasicNameValuePair("set_id", id));
        else params.add(new BasicNameValuePair("element_id", id));
        if(!TextUtils.isEmpty(password))
            params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("per_page", String.valueOf(perPage)));
        params.add(new BasicNameValuePair("page", String.valueOf(page)));

        performAPIRequest(false, URL_COMMENT, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                CommentList parsedResponse;
                try {
                    parsedResponse=new CommentList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onGetCommentListByElement(parsedResponse);
            }
        }, params);
    }

    /**
     * 新增個人相簿資料夾
     * @param title 標題文字
     * @param description 描述文字
     */
    public void addFolder(String title, String description){
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("description", description));

        performAPIRequest(true, URL_FOLDER, Method.POST, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Folder parsedResponse;
                try {
                    parsedResponse=new Folder(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onAddFolder(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #addSet(String, String, AlbumHelper.Permission, String, boolean, boolean, AlbumHelper.CommentPermission, String, String, java.util.List, boolean, boolean, String)
     */
    public void addSet(String title, String description){
        addSet(title, description, Permission.all, null, false, true, CommentPermission.allow, null, null, null, false, false, null);
    }
    /**
     * 新增相簿個人 Set，需要認證
     * @param title 標題文字
     * @param description 描述文字
     * @param permission 閱讀權限，all: 完全公開 / friend: 好友相簿 / password: 密碼相簿 / hidden: 隱藏相簿 / friendGroup: 好友群組相簿
     * @param categoryId 相簿分類
     * @param isLockRight 是否鎖右鍵
     * @param allowCC 是否採用CC授權
     * @param canComment 是否允許留言 denied: 禁止留言, allow: 開放留言, friend: 限好友留言, member: 限會員留言
     * @param password 相簿密碼 (當 permission 設為密碼相簿時需要)
     * @param passwordHint 密碼提示 (當 permission 設為密碼相簿時需要)
     * @param friendGroupIds 好友群組ID
     * @param allowCommercialUsr 是否允許商業使用
     * @param allowDerivatoin 是否允許創作衍生著作
     * @param parentId 如果這個 parent_id 被指定, 則此相簿會放置在這個相簿資料夾底下(只能放在資料夾底下)
     */
    public void addSet(String title, String description, Permission permission, String categoryId, boolean isLockRight, boolean allowCC, CommentPermission canComment, String password, String passwordHint, List<String> friendGroupIds, boolean allowCommercialUsr, boolean allowDerivatoin, String parentId){
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("description", description));
        params.add(new BasicNameValuePair("permission", String.valueOf(permission.ordinal())));
        if(TextUtils.isEmpty(categoryId))
            params.add(new BasicNameValuePair("category_id", categoryId));
        params.add(new BasicNameValuePair("is_lockright", isLockRight?"1":"0"));
        params.add(new BasicNameValuePair("allow_cc", allowCC?"cc":"copyrighted"));
        params.add(new BasicNameValuePair("cancomment", String.valueOf(canComment.ordinal())));
        if(TextUtils.isEmpty(password))
            params.add(new BasicNameValuePair("password", password));
        if(TextUtils.isEmpty(passwordHint))
            params.add(new BasicNameValuePair("password_hint", passwordHint));
        if(friendGroupIds!=null){
            String ids="";
            int i=0, len=friendGroupIds.size();
            while (i<len){
                ids+=friendGroupIds.get(i);
                i++;
                if(i<len)
                    ids+=",";
            }
            params.add(new BasicNameValuePair("friend_group_ids", ids));
        }
        params.add(new BasicNameValuePair("allow_commercial_usr", allowCommercialUsr?"1":"0"));
        params.add(new BasicNameValuePair("allow_derivation", allowDerivatoin?"1":"0"));
        if(TextUtils.isEmpty(parentId))
            params.add(new BasicNameValuePair("parent_id", parentId));

        performAPIRequest(true, URL_SET, Method.POST, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Set parsedResponse;
                try {
                    parsedResponse=new Set(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener) listener).onAddSet(parsedResponse);
            }
        }, params);
    }

    /**
     * 新增相簿圖片影片
     */
    public void addElement(){}

    /**
     * 新增 Set 留言
     */
    public void addCommentToSet(){}

    /**
     * 新增相片留言
     */
    public void addCommentToElement(){}

    /**
     * 新增人臉標記
     * @param userName 要標記的使用者帳號, 被標記者必須設定標記者為好友
     * @param recommendId 系統建議的 id, {@link #getElement(String, String, boolean, boolean, int, int)} 可取得此資訊
     */
    public void addFaceByRecommend(String userName, final String recommendId){
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(recommendId)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        params.add(new BasicNameValuePair("recommend_id", recommendId));
        performAPIRequest(true, URL_FACES, Method.POST, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Element parsedResponse;
                try {
                    parsedResponse=new Element(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onAddFaceByRecommend(parsedResponse);
            }
        }, params);
    }
    /**
     * 新增人臉標記
     * @param userName 要標記的使用者帳號, 被標記者必須設定標記者為好友
     * @param elementId 相片或影像的 id
     * @param x 標記起點距相片最左邊的距離，單位是 px 。所對應的座標基準是 normal 這張相片
     * @param y 標記起點距相片最上緣的距離，單位是 px 。所對應的座標基準是 normal 這張相片
     * @param width 標記的寬度，單位是 px 。所對應的座標基準是 normal 這張相片
     * @param height 標記的高度，單位是 px 。所對應的座標基準是 normal 這張相片
     */
    public void addFaceByElement(String userName, String elementId, int x, int y, int width, int height){
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(elementId)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        if(x<0 || y<0 || width<1 || height<1){
            listener.onError(Error.PARAMETER_INVALID+":x, y, width, height");
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        params.add(new BasicNameValuePair("element_id", elementId));
        params.add(new BasicNameValuePair("x", String.valueOf(x)));
        params.add(new BasicNameValuePair("y", String.valueOf(y)));
        params.add(new BasicNameValuePair("w", String.valueOf(width)));
        params.add(new BasicNameValuePair("h", String.valueOf(height)));
        performAPIRequest(true, URL_FACES, Method.POST, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Element parsedResponse;
                try {
                    parsedResponse=new Element(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onAddFaceByElement(parsedResponse);
            }
        }, params);
    }

    /**
     * 修改個人相簿資料夾
    * @param title 標題文字
    * @param description 描述文字
    */
    public void updateFolder(String folderId, String title, String description){
        if(TextUtils.isEmpty(folderId) || TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("description", description));

        performAPIRequest(true, URL_FOLDER+"/"+folderId, Method.POST, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Folder parsedResponse;
                try {
                    parsedResponse=new Folder(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onUpdateFolder(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #updateSet(String, String, String, AlbumHelper.Permission, String, boolean, boolean, AlbumHelper.CommentPermission, String, String, java.util.List, boolean, boolean, String)
     */
    public void updateSet(String setId, String title, String description){
        updateSet(setId, title, description, Permission.all, null, false, true, CommentPermission.allow, null, null, null, false, false, null);
    }

    /**
     * 修改相簿個人 Set
     * @param setId set id
     * @param title 標題文字
     * @param description 描述文字
     * @param permission 閱讀權限，all: 完全公開 / friend: 好友相簿 / password: 密碼相簿 / hidden: 隱藏相簿 / friendGroup: 好友群組相簿
     * @param categoryId 相簿分類
     * @param isLockRight 是否鎖右鍵
     * @param allowCC 是否採用CC授權
     * @param canComment 是否允許留言 denied: 禁止留言, allow: 開放留言, friend: 限好友留言, member: 限會員留言
     * @param password 相簿密碼 (當 permission 設為密碼相簿時需要)
     * @param passwordHint 密碼提示 (當 permission 設為密碼相簿時需要)
     * @param friendGroupIds 好友群組ID
     * @param allowCommercialUsr 是否允許商業使用
     * @param allowDerivatoin 是否允許創作衍生著作
     * @param parentId 如果這個 parent_id 被指定, 則此相簿會放置在這個相簿資料夾底下(只能放在資料夾底下)
     */
    public void updateSet(String setId, String title, String description, Permission permission, String categoryId, boolean isLockRight, boolean allowCC, CommentPermission canComment, String password, String passwordHint, List<String> friendGroupIds, boolean allowCommercialUsr, boolean allowDerivatoin, String parentId){
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("description", description));
        params.add(new BasicNameValuePair("permission", String.valueOf(permission.ordinal())));
        if(TextUtils.isEmpty(categoryId))
            params.add(new BasicNameValuePair("category_id", categoryId));
        params.add(new BasicNameValuePair("is_lockright", isLockRight?"1":"0"));
        params.add(new BasicNameValuePair("allow_cc", allowCC?"cc":"copyrighted"));
        params.add(new BasicNameValuePair("cancomment", String.valueOf(canComment.ordinal())));
        if(TextUtils.isEmpty(password))
            params.add(new BasicNameValuePair("password", password));
        if(TextUtils.isEmpty(passwordHint))
            params.add(new BasicNameValuePair("password_hint", passwordHint));
        if(friendGroupIds!=null){
            String ids="";
            int i=0, len=friendGroupIds.size();
            while (i<len){
                ids+=friendGroupIds.get(i);
                i++;
                if(i<len)
                    ids+=",";
            }
            params.add(new BasicNameValuePair("friend_group_ids", ids));
        }
        params.add(new BasicNameValuePair("allow_commercial_usr", allowCommercialUsr?"1":"0"));
        params.add(new BasicNameValuePair("allow_derivation", allowDerivatoin?"1":"0"));
        if(TextUtils.isEmpty(parentId))
            params.add(new BasicNameValuePair("parent_id", parentId));

        performAPIRequest(true, URL_SET+"/"+setId, Method.POST, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Set parsedResponse;
                try {
                    parsedResponse=new Set(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onUpdateSet(parsedResponse);
            }
        }, params);
    }

    /**
     * 修改相簿單篇圖片影片
     */
    public void updateElement(){}

    /**
     * 更新人臉標記
     * @param faceId 欲更動的人臉標記 id
     * @param userName 要更新標記的使用者帳號。被標記者必須設定標記者為好友
     * @param elementId 相片或影像的 id
     * @param x 標記起點距相片最左邊的距離，單位是 px 。所對應的座標基準是 normal 這張相片
     * @param y 標記起點距相片最上緣的距離，單位是 px 。所對應的座標基準是 normal 這張相片
     * @param width 標記的寬度，單位是 px 。所對應的座標基準是 normal 這張相片
     * @param height 標記的高度，單位是 px 。所對應的座標基準是 normal 這張相片
     */
    public void updateFace(String faceId, String userName, String elementId, int x, int y, int width, int height){
        if(TextUtils.isEmpty(faceId) || TextUtils.isEmpty(userName) || TextUtils.isEmpty(elementId)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        if(x<0 || y<0 || width<1 || height<1){
            listener.onError(Error.PARAMETER_INVALID+":x, y, width, height");
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        params.add(new BasicNameValuePair("element_id", elementId));
        params.add(new BasicNameValuePair("x", String.valueOf(x)));
        params.add(new BasicNameValuePair("y", String.valueOf(y)));
        params.add(new BasicNameValuePair("w", String.valueOf(width)));
        params.add(new BasicNameValuePair("h", String.valueOf(height)));
        performAPIRequest(true, URL_FACES+"/"+faceId, Method.POST, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Element parsedResponse;
                try {
                    parsedResponse=new Element(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onUpdateFace(parsedResponse);
            }
        }, params);
    }

    /**
     * 刪除個人相簿資料夾
     * @param folderId 要刪除的 folder id
     */
    public void deleteFolder(String folderId){
        if(TextUtils.isEmpty(folderId)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        performAPIRequest(true, URL_FOLDER+"/"+folderId, Method.DELETE, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onDeleteFolder(parsedResponse);
            }
        });
    }

    /**
     * 刪除相簿個人 Set
     * @param setId 要刪除的 set id
     */
    public void deleteSet(String setId){
        if(TextUtils.isEmpty(setId)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        performAPIRequest(true, URL_SET+"/"+setId, Method.DELETE, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onDeleteSet(parsedResponse);
            }
        });
    }

    /**
     * 刪除相簿單篇圖片影片
     * @param elementId 欲刪除的相片或影片 id
     */
    public void deleteElement(String elementId){
        if(TextUtils.isEmpty(elementId)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        performAPIRequest(true, URL_ELEMENTS+"/"+elementId, Method.DELETE, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onDeleteElement(parsedResponse);
            }
        });
    }

    /**
     * 刪除相本 Set 的留言
     * @param commentId 欲刪除的留言 id
     */
    public void deleteCommentFromSet(String commentId){
        if(TextUtils.isEmpty(commentId)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        performAPIRequest(true, URL_SET_COMMENT+"/"+commentId, Method.DELETE, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onDeleteCommentFromSet(parsedResponse);
            }
        });
    }

    /**
     * 刪除相片的留言
     * @param commentId 欲刪除的留言 id
     */
    public void deleteCommentFromElement(String commentId){
        if(TextUtils.isEmpty(commentId)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        performAPIRequest(true, URL_COMMENT+"/"+commentId, Method.DELETE, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onDeleteCommentFromElement(parsedResponse);
            }
        });
    }

    /**
     * 刪除人臉標記
     * @param faceId 欲刪除的人臉標記 id
     */
    public void deleteFace(String faceId){
        if(TextUtils.isEmpty(faceId)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        performAPIRequest(true, URL_FACES+"/"+faceId, Method.DELETE, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onDeleteFace(parsedResponse);
            }
        });
    }

    /**
     * 修改相簿首頁排序, 需要認證
     * @param ids 相簿 id 順序，放在越前面的表示圖片的順序越優先。
     */
    public void sortSetAndFolders(List<String> ids){
        if(ids==null || ids.size()<1){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        String idsString="";
        int i=0, len=ids.size();
        while(i<len){
            idsString+=ids.get(i);
            i++;
            if(i<len) idsString+=",";
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ids", idsString));
        performAPIRequest(true, URL_SORT_SETFOLDERS, Method.POST, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onSortSetAndFolders(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #sortSets(java.util.List)
     */
    public void sortSets(List<String> ids){
        sortSets("0", ids);
    }

    /**
     * 修改相簿 Set 排序
     * @param parentId 屬於哪一個相簿資料夾，沒有請填 "0"
     * @param ids 相簿 id 順序，放在越前面的表示圖片的順序越優先。
     */
    public void sortSets(String parentId, List<String> ids){
        if(TextUtils.isEmpty(parentId) || ids==null || ids.size()<1){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        String idsString="";
        int i=0, len=ids.size();
        while(i<len){
            idsString+=ids.get(i);
            i++;
            if(i<len) idsString+=",";
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("parent_id", parentId));
        params.add(new BasicNameValuePair("ids", idsString));
        performAPIRequest(true, URL_SORT_SETS, Method.POST, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onSortSets(parsedResponse);
            }
        }, params);
    }

    /**
     * 修改相簿圖片/影片排序
     * @param setId 相簿 id
     * @param ids 圖片/影片 id 順序，放在越前面的順序越優先
     */
    public void sortElementList(String setId, List<String> ids){
        if(TextUtils.isEmpty(setId) || ids==null){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        if(ids.size()<1){
            listener.onError(Error.PARAMETER_INVALID+":ids");
            return;
        }
        String idsString="";
        int i=0, len=ids.size();
        while(i<len){
            idsString+=ids.get(i);
            i++;
            if(i<len) idsString+=",";
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("setId", setId));
        params.add(new BasicNameValuePair("ids", idsString));
        performAPIRequest(true, URL_SORT_ELEMENTS, Method.POST, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onSortElementList(parsedResponse);
            }
        }, params);
    }

    /**
     * 將 Set 的留言設為廣告留言
     */
    public void markCommentIsSpamForSet(){}

    /**
     * 將相片的留言設為廣告留言
     */
    public void markCommentIsSpamByElement(){}

    /**
     * 將 Set 的留言設為非廣告留言
     */
    public void markCommentIsHamForSet(){}

    /**
     * 將相片的留言設為非廣告留言
     */
    public void markCommentIsHamByElement(){}

    /**
     * 列出相簿個人設定
     */
    public void getConfig(){
        performAPIRequest(true, URL_ALBUM_CONIFG, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((AlbumHelperListener)listener).onGetConfig(parsedResponse);
            }
        });
    }

}

package net.pixnet.sdk.utils;

import android.text.TextUtils;
import android.widget.ListAdapter;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.proxy.Error;
import net.pixnet.sdk.response.ElementList;
import net.pixnet.sdk.response.Set;
import net.pixnet.sdk.response.SetAndFolderList;
import net.pixnet.sdk.response.SetList;
import net.pixnet.sdk.utils.Request.Method;
import net.pixnet.sdk.utils.Request.RequestCallback;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Album extends DataProxy {

    private static final String URL_MAIN="https://emma.pixnet.cc/album/main";
    private static final String URL_SETFOLDERS="https://emma.pixnet.cc/album/setfolders";
    private static final String URL_SORT_SETFOLDERS="https://emma.pixnet.cc/album/setfolders/position";
    private static final String URL_SETS="https://emma.pixnet.cc/album/sets";
    private static final String URL_SET="https://emma.pixnet.cc/album/sets/";
    private static final String URL_ELEMENTS="https://emma.pixnet.cc/album/elements";

    public static enum ElementType{
        pic,
        video,
        audio
    }

    /**
     * 預設使用者名稱
     */
    private String defaultUserName="emmademo";
    public String getDefaultUserName() {
        return defaultUserName;
    }
    public void setDefaultUserName(String defaultUserName) {
        this.defaultUserName = defaultUserName;
    }

    /**
     * 預設每頁幾筆資料
     */
    private int defaultPerPage =20;
    public int getDefaultPerPage() {
        return defaultPerPage;
    }
    public void setDefaultPerPage(int defaultPerPage) {
        this.defaultPerPage = defaultPerPage;
    }

    /**
     * 預設是否每篇文章都要回傳作者資訊, 如果設定為 true, 則就不回傳
     */
    private boolean defaultTrimUser = false;
    public boolean isDefaultTrimUser() {
        return defaultTrimUser;
    }
    public void setDefaultTrimUser(boolean defaultTrimUser) {
        this.defaultTrimUser = defaultTrimUser;
    }

    /**
     * 列出相簿主圖及相片牆, 需要認證
     */
    public void getMain(){
        performAPIRequest(true, URL_MAIN, new RequestCallback() {
            @Override
            public void onResponse(String response) {
//                BasicResponse res=new BasicResponse();
//                listener.onDataResponse(res);
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
        getSetAndFolderList(defaultPerPage, page);
    }
    /**
     * @see #getSetAndFolderList(String, int, int, boolean)
     */
    public void getSetAndFolderList(int perPage, int page){
        getSetAndFolderList(defaultUserName, perPage, page, defaultTrimUser);
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
            listener.onError(Error.MISS_PARAMETER+":userName");
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
                SetAndFolderList res = new SetAndFolderList(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    /**
     * 讀取個人相簿單一資料夾
     */
    public void getFolder(){}

    /**
     * 列出個人相簿資料夾
     */
    public void getFolderList(){}

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
            listener.onError(Error.MISS_PARAMETER+":userName");
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        params.add(new BasicNameValuePair("per_page", String.valueOf(perPage)));
        params.add(new BasicNameValuePair("page", String.valueOf(page)));

        performAPIRequest(false, URL_SET+id, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                Set res = new Set(response);
                listener.onDataResponse(res);
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
    public void getSetList(int page){
        getSetList(defaultPerPage, page);
    }
    /**
     * @see #getSetList(String, String, boolean, int, int)
     */
    public void getSetList(int perPage, int page){
        getSetList(defaultUserName, null, defaultTrimUser, perPage, page);
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
            listener.onError(Error.MISS_PARAMETER+":userName");
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        if(!TextUtils.isEmpty(parentId))
            params.add(new BasicNameValuePair("parent_id", parentId));
        params.add(new BasicNameValuePair("trim_user", trimUser?"1":"0"));
        params.add(new BasicNameValuePair("per_page", String.valueOf(perPage)));
        params.add(new BasicNameValuePair("page", String.valueOf(page)));

        performAPIRequest(false, URL_SETS, new RequestCallback() {
            @Override
            public void onResponse(String response) {
                SetList res=new SetList(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    /**
     * 列出相簿資料夾內的相簿 Set
     */
    public void getSetListByFolder(){}

    /**
     * 列出附近的相簿 Set
     */
    public void getSetListByNear(){}

    /**
     * 讀取相簿單篇圖片影片
     */
    public void getElement(){}

    /**
     * 列出相簿所有圖片影片
     */
    public void getElementListByAlbum(){}

    /**
     * @see #getElementListBySet(String, String, net.pixnet.sdk.utils.Album.ElementType, int, int, String, boolean, boolean, boolean, int, int)
     */
    public void getElementListBySet(String setId){
        getElementListBySet(setId, 1);
    }
    /**
     * @see #getElementListBySet(String, String, net.pixnet.sdk.utils.Album.ElementType, int, int, String, boolean, boolean, boolean, int, int)
     */
    public void getElementListBySet(String setId, int page){
        getElementListBySet(setId, defaultPerPage, page);
    }
    /**
     * @see #getElementListBySet(String, String, net.pixnet.sdk.utils.Album.ElementType, int, int, String, boolean, boolean, boolean, int, int)
     */
    public void getElementListBySet(String setId, int perPage, int page){
        getElementListBySet(setId, ElementType.pic, perPage, page);
    }
    /**
     * @see #getElementListBySet(String, String, net.pixnet.sdk.utils.Album.ElementType, int, int, String, boolean, boolean, boolean, int, int)
     */
    public void getElementListBySet(String setId, ElementType type, int perPage, int page){
        getElementListBySet(setId, type, perPage, page, false, defaultTrimUser);
    }
    /**
     * @see #getElementListBySet(String, String, net.pixnet.sdk.utils.Album.ElementType, int, int, String, boolean, boolean, boolean, int, int)
     */
    public void getElementListBySet(String setId, ElementType type, int perPage, int page, boolean withDetail, boolean trimUser){
        getElementListBySet(defaultUserName, setId, type, perPage, page, null, withDetail, trimUser, false, 0, 0);
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
            listener.onError(Error.MISS_PARAMETER+":userName");
            return;
        }
        if(TextUtils.isEmpty(setId)){
            listener.onError(Error.MISS_PARAMETER+":setId");
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
                ElementList res=new ElementList(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    /**
     * 列出附近的相簿圖片影片
     */
    public void getElementListByNear(){}

    /**
     * 讀取 Set 單一留言
     */
    public void getCommentForSet(){}

    /**
     * 讀取相片單一留言
     */
    public void getCommentForElement(){}

    /**
     * 列出 Set 的留言
     */
    public void getCommentListForSet(){}

    /**
     * 列出相片留言
     */
    public void getCommentListForElement(){}

    /**
     * 列出單一相簿 Set 的留言
     */
    public void getCommentListBySet(){}

    /**
     * 讀取相簿單篇圖片影片留言
     */
    public void getCommentListByElement(){}

    /**
     * 新增個人相簿資料夾
     */
    public void addFolder(){}

    /**
     * 新增相簿個人 Set
     */
    public void addSet(){}

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
     */
    public void addFace(){}

    /**
     * 修改個人相簿資料夾
     */
    public void updateFolder(){}

    /**
     * 修改相簿個人 Set
     */
    public void updateSet(){}

    /**
     * 修改相簿單篇圖片影片
     */
    public void updateElement(){}

    /**
     * 更新人臉標記
     */
    public void updateFace(){}

    /**
     * 刪除個人相簿資料夾
     */
    public void removeFolder(){}

    /**
     * 刪除相簿個人 Set
     */
    public void removeSet(){}

    /**
     * 刪除相簿單篇圖片影片
     */
    public void removeElement(){}

    /**
     * 刪除相本 Set 的留言
     */
    public void removeCommentFromSet(){}

    /**
     * 刪除相片的留言
     */
    public void removeCommentFromElement(){}

    /**
     * 刪除人臉標記
     */
    public void removeFace(){}

    /**
     * 修改相簿首頁排序, 需要認證
     * @param ids 相簿 id 順序，放在越前面的表示圖片的順序越優先。
     */
    public void sortSetAndFolderList(String... ids){
        if(ids==null || ids.length<1){
            listener.onError(Error.MISS_PARAMETER+":ids");
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ids", ids.toString()));
        performAPIRequest(true, URL_SORT_SETFOLDERS, Method.POST, new RequestCallback() {
            @Override
            public void onResponse(String response) {
//                BasicResponse res=new BasicResponse();
//                listener.onDataResponse(res);
            }
        }, params);

    }

    /**
     * 修改相簿 Set 排序
     */
    public void sortSetList(){}

    /**
     * 修改相簿圖片影片排序
     */
    public void sortElementList(){}

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
    public void getConfig(){}

}

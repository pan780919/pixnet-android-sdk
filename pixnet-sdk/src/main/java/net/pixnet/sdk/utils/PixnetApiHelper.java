package net.pixnet.sdk.utils;

import android.text.TextUtils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.proxy.Error;
import net.pixnet.sdk.response.AccountInfo;
import net.pixnet.sdk.response.AlbumMainPage;
import net.pixnet.sdk.response.Analytics;
import net.pixnet.sdk.response.Article;
import net.pixnet.sdk.response.ArticleList;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.BlocksList;
import net.pixnet.sdk.response.BlogInfo;
import net.pixnet.sdk.response.CategoryList;
import net.pixnet.sdk.response.CellphoneVerification;
import net.pixnet.sdk.response.Comment;
import net.pixnet.sdk.response.CommentList;
import net.pixnet.sdk.response.Element;
import net.pixnet.sdk.response.ElementList;
import net.pixnet.sdk.response.Folder;
import net.pixnet.sdk.response.FolderList;
import net.pixnet.sdk.response.FriendshipList;
import net.pixnet.sdk.response.GroupList;
import net.pixnet.sdk.response.Guestbook;
import net.pixnet.sdk.response.GuestbookList;
import net.pixnet.sdk.response.MIB;
import net.pixnet.sdk.response.NewsList;
import net.pixnet.sdk.response.NotificationList;
import net.pixnet.sdk.response.PeriodArticleList;
import net.pixnet.sdk.response.Position;
import net.pixnet.sdk.response.PositionList;
import net.pixnet.sdk.response.Set;
import net.pixnet.sdk.response.SetAndFolderList;
import net.pixnet.sdk.response.SetList;
import net.pixnet.sdk.response.SiteCategoryList;
import net.pixnet.sdk.response.SubscriptionGroupList;
import net.pixnet.sdk.response.SubscriptionList;
import net.pixnet.sdk.response.Tags;
import net.pixnet.sdk.response.User;
import net.pixnet.sdk.utils.Request.Method;
import net.pixnet.sdk.utils.Request.RequestCallback;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PixnetApiHelper extends DataProxy {

    // mainpage
    public static final String URL_BEST_SELECTED_ALBUM = "https://emma.pixnet.cc/mainpage/album/best_selected/";

    // account
    public static final String URL_ACCOUNT="https://emma.pixnet.cc/account";
    public static final String URL_ACCOUNT_INFO="https://emma.pixnet.cc/account/info";
    public static final String URL_ACCOUNT_MIB="https://emma.pixnet.cc/account/mib";
    public static final String URL_ACCOUNT_MIB_POSTION="https://emma.pixnet.cc/account/mib/positions";
    public static final String URL_ACCOUNT_MIB_PAY="https://emma.pixnet.cc/account/mibpay";
    public static final String URL_ACCOUNT_ANALYTICS="https://emma.pixnet.cc/account/analytics";
    public static final String URL_ACCOUNT_PASSWD ="https://emma.pixnet.cc/account/password";
    public static final String URL_ACCOUNT_NOTIFICATIONS ="https://emma.pixnet.cc/account/notifications";
    public static final String URL_USER ="https://emma.pixnet.cc/users";
    public static final String URL_PHONE_VERIFY ="https://emma.pixnet.cc/account/cellphone_verification";

    // album
    private static final String URL_MAIN="https://emma.pixnet.cc/album/main";
    private static final String URL_SETFOLDERS="https://emma.pixnet.cc/album/setfolders";
    private static final String URL_SET="https://emma.pixnet.cc/album/sets";
    private static final String URL_FOLDER="https://emma.pixnet.cc/album/folders";
    private static final String URL_ELEMENTS="https://emma.pixnet.cc/album/elements";
    private static final String URL_SET_COMMENT="https://emma.pixnet.cc/album/set_comments";
    private static final String URL_ALBUM_COMMENT ="https://emma.pixnet.cc/album/comments";
    private static final String URL_SORT_SETFOLDERS="https://emma.pixnet.cc/album/setfolders/position";
    private static final String URL_SORT_SETS="https://emma.pixnet.cc/album/sets/position";
    private static final String URL_SORT_ELEMENTS="https://emma.pixnet.cc/album/elements/position";
    private static final String URL_SETS_NEAR="https://emma.pixnet.cc/album/sets/nearby";
    private static final String URL_FACES="https://emma.pixnet.cc/album/faces";
    private static final String URL_ALBUM_CONIFG="https://emma.pixnet.cc/album/config";
    private static final String URL_ELEMENTS_NEARBY = "https://emma.pixnet.cc/album/elements/nearby";

    // blog
    public static final String URL_CATEGORY = "https://emma.pixnet.cc/blog/categories";
    public static final String URL_SITE_CATEGORY = "https://emma.pixnet.cc/blog/site_categories";
    public static final String URL_ARTICLE = "https://emma.pixnet.cc/blog/articles";
    public static final String URL_BLOG_COMMENT = "https://emma.pixnet.cc/blog/comments";
    public static final String URL_BLOG = "https://emma.pixnet.cc/blog";
    public static final String URL_TAGS = "https://emma.pixnet.cc/blog/suggested_tags";

    // friend
    private static final String URL_NEWS = "https://emma.pixnet.cc/friend/news";
    private static final String URL_GROUP = "https://emma.pixnet.cc/friend/groups";
    private static final String URL_FRIENDSHIP = "https://emma.pixnet.cc/friendships";
    private static final String URL_SUBSCRIPTION = "https://emma.pixnet.cc/friend/subscriptions";
    private static final String URL_SUBSCRIPTION_GROUP = "https://emma.pixnet.cc/friend/subscription_groups";

    // block
    public static final String URL_BLOCK = "https://emma.pixnet.cc/blocks";

    // guestbook
    public static final String URL_GUESTBOOK = "https://emma.pixnet.cc/guestbook";

    // account
    public static enum NotificationType{
        friend,
        system,
        comment,
        appmarket
    }

    // album
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

    // mainpage
    /**
     * 列出精選相簿
     */
    public void getBestSelectedAlbum(){
        performAPIRequest(false, URL_BEST_SELECTED_ALBUM, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener)listener).onGetBestSelectedAlbum(parsedResponse);
            }
        });
    }

    //account
    /**
     * 取得手機驗證狀態
     */
    public void cellphoneVerification(){
        performCellphoneVerification(null);
    }

    /**
     * 收到認證碼後，進行認證
     * @param code
     */
    public void cellphoneVerification(String code){
        if(TextUtils.isEmpty(code)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("code", code));
        performCellphoneVerification(params);
    }

    /**
     * 傳送簡訊認證碼
     * @param phoneNumber
     * @param countryCode
     */
    public void cellphoneVerification(String phoneNumber, String countryCode){
        if(TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(countryCode)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        if(phoneNumber.startsWith("0"))
            phoneNumber=phoneNumber.replaceFirst("0", "");
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("cellphone", phoneNumber));
        params.add(new BasicNameValuePair("calling_code", countryCode));
        performCellphoneVerification(params);
    }

    private void performCellphoneVerification(List<NameValuePair> params){
        performAPIRequest(true, URL_PHONE_VERIFY, params==null?Request.Method.GET:Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                CellphoneVerification parsedResponse;
                try {
                    parsedResponse=new CellphoneVerification(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onCellphoneVerification(parsedResponse);
            }
        }, params);
    }

    /**
     * 讀取認證使用者資訊, 傳回結果：{@link net.pixnet.sdk.response.AccountInfo}
     * @param withNotification 傳回最近的 5 則通知
     * @param notificationType 限制傳回通知類型（friend: 好友互動，system: 系統通知，comment: 留言，appmarket:應用市集）
     * @param withBlogInfo 傳回部落格資訊
     * @param withMIB 傳回 MIB 相關資訊
     * @param withAnalytics 傳回人氣統計相關資訊、近期熱門文章、相片資訊
     */
    public void getAccountInfo(boolean withNotification, NotificationType notificationType, boolean withBlogInfo, boolean withMIB, boolean withAnalytics){
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("with_notification", withNotification?"1":"0"));
        if(notificationType!=null)
            params.add(new BasicNameValuePair("notification_type", notificationType.name()));
        params.add(new BasicNameValuePair("with_blog_info", withBlogInfo?"1":"0"));
        params.add(new BasicNameValuePair("with_mib", withMIB?"1":"0"));
        params.add(new BasicNameValuePair("with_analytics", withAnalytics?"1":"0"));

        performAPIRequest(true, URL_ACCOUNT, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                AccountInfo parsedResponse;
                try {
                    parsedResponse=new AccountInfo(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetAccountInfo(parsedResponse);
            }
        }, params);

    }

    /**
     * @see #updateAccountInfo(String, String, String, net.pixnet.sdk.response.AccountInfo.Gender, String, String, String, String, String, String, java.io.File)
     */
    public void updateAccountInfo(String password, String displayName){
        updateAccountInfo(password, displayName, null, null, null, null, null, null, null, null, null);
    }
    /**
     * 修改認證使用者資訊, 傳回結果：{@link net.pixnet.sdk.response.BasicResponse}
     * @param password 密碼，需要檢查與系統內儲存資訊相符後才能執行修改
     * @param displayName 使用者暱稱
     * @param email 信箱
     * @param gender 性別
     * @param area 縣市
     * @param subArea 區
     * @param address 住址
     * @param phone 電話
     * @param birthday 生日（YYYYMMDD）
     * @param education 教育程度 (中學以下, 高中/高職, 專科, 大學, 研究所)
     * @param avatar 大頭照 (base64)
     */
    public void updateAccountInfo(String password, String displayName, String email, AccountInfo.Gender gender, String area, String subArea, String address, String phone, String birthday, String education, File avatar){
        if(TextUtils.isEmpty(password)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("password", password));
        if(!TextUtils.isEmpty(displayName))
            params.add(new BasicNameValuePair("display_name", displayName));
        if(!TextUtils.isEmpty(email))
            params.add(new BasicNameValuePair("email", email));
        if(gender!=null)
            params.add(new BasicNameValuePair("gender", gender==AccountInfo.Gender.F?"F":"M"));
        if(!TextUtils.isEmpty(area))
            params.add(new BasicNameValuePair("area", area));
        if(!TextUtils.isEmpty(subArea))
            params.add(new BasicNameValuePair("sub_area", subArea));
        if(!TextUtils.isEmpty(address))
            params.add(new BasicNameValuePair("address", address));
        if(!TextUtils.isEmpty(phone))
            params.add(new BasicNameValuePair("phone", phone));
        if(!TextUtils.isEmpty(birthday))
            params.add(new BasicNameValuePair("birth", birthday));
        if(!TextUtils.isEmpty(education))
            params.add(new BasicNameValuePair("education", education));
        List<FileNameValuePair> files=null;
        if(avatar!=null) {
            files = new ArrayList<FileNameValuePair>();
            files.add(new FileNameValuePair("avatar", avatar));
        }

        performAPIRequest(true, URL_ACCOUNT_INFO, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onUpdateAccountInfo(parsedResponse);
            }
        }, params, files);
    }

    /**
     * @see #getMIBInfo(int)
     */
    public void getMIBInfo(){
        getMIBInfo(-1);
    }
    /**
     * 讀取認證使用者 MIB 帳戶資訊, 傳回結果：{@link net.pixnet.sdk.response.MIB}
     * @param historyDays 列出指定天數的歷史收益資訊，最少 0 天，最多 90 天
     */
    public void getMIBInfo(int historyDays){
        List<NameValuePair> params;
        if(historyDays>-1 && historyDays<=90){
            params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("history_days", String.valueOf(historyDays)));
        }
        else params=null;

        performAPIRequest(true, URL_ACCOUNT_MIB, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                MIB parsedResponse;
                try {
                    parsedResponse=new MIB(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetMIBInfo(parsedResponse);
            }
        }, params);
    }

    /**
     * 上傳認證使用者 MIB 帳戶資訊, 如果已經上傳過帳戶資料，除非需要補件，否則不開放再次上傳帳戶資料, 傳回結果：{@link net.pixnet.sdk.response.MIB}
     * @param idNumber 身分證字號
     * @param idImageFront 身分證正面
     * @param idImageBack 身分證反面
     * @param email
     * @param cellPhone 手機號碼
     * @param mailAddress 支票寄送地址
     * @param domicile 戶籍地
     * @param enableVideoAd 是否開啟影音廣告
     * @param name 真實姓名
     */
    public void updateMIBInfo(String idNumber, File idImageFront, File idImageBack, String email, String telephone, String cellPhone, String mailAddress, String domicile, boolean enableVideoAd, String name){
        if(TextUtils.isEmpty(idNumber) || idImageFront==null || idImageBack==null || TextUtils.isEmpty(email) || TextUtils.isEmpty(telephone) || TextUtils.isEmpty(cellPhone) || TextUtils.isEmpty(mailAddress) || TextUtils.isEmpty(domicile) || TextUtils.isEmpty(name)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id_number", idNumber));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("telephone", telephone));
        params.add(new BasicNameValuePair("cellphone", cellPhone));
        params.add(new BasicNameValuePair("mail_address", mailAddress));
        params.add(new BasicNameValuePair("domicile", domicile));
        params.add(new BasicNameValuePair("enable_video_ad", enableVideoAd?"1":"0"));
        params.add(new BasicNameValuePair("name", name));

        List<FileNameValuePair> files=new ArrayList<FileNameValuePair>();
        files.add(new FileNameValuePair("id_image_front", idImageFront));
        files.add(new FileNameValuePair("id_image_back", idImageBack));

        performAPIRequest(true, URL_ACCOUNT_MIB, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                MIB parsedResponse;
                try {
                    parsedResponse=new MIB(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onUpdateMIBInfo(parsedResponse);
            }
        }, params, files);
    }

    /**
     * 取得 MIB 版位資料, 傳回結果：{@link net.pixnet.sdk.response.PositionList}
     */
    public void getMIBPostionListInfo(){
        performAPIRequest(true, URL_ACCOUNT_MIB_POSTION, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                PositionList parsedResponse;
                try {
                    parsedResponse=new PositionList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetMIBPositionListInfo(parsedResponse);
            }
        });
    }
    /**
     * 取得 MIB 單一版位資料, 傳回結果：{@link net.pixnet.sdk.response.Position}
     * @param id 版位 ID, 可由 {@link #getMIBPostionListInfo()} 或 {@link #getMIBInfo()} 取得
     */
    public void getMIBPostionInfo(String id){
        if(TextUtils.isEmpty(id)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        performAPIRequest(true, URL_ACCOUNT_MIB_POSTION+"/"+id, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Position parsedResponse;
                try {
                    parsedResponse=new Position(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetMIBPositionInfo(parsedResponse);
            }
        });
    }

    /**
     * 修改 MIB 版位資料, 傳回結果：{@link net.pixnet.sdk.response.MIB}
     * @param id 版位 id
     * @param enabled 是否啟用
     * @param fixed 是否固定式廣告框
     */
    public void updateMIBPositionInfo(String id, boolean enabled, boolean fixed){
        if(TextUtils.isEmpty(id)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("enabled", enabled?"1":"0"));
        params.add(new BasicNameValuePair("fixedadbox", fixed?"1":"0"));

        performAPIRequest(true, URL_ACCOUNT_MIB_POSTION+"/"+id, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                MIB parsedResponse;
                try {
                    parsedResponse=new MIB(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onUpdateMIBPositionInfo(parsedResponse);
            }
        }, params);
    }

    /**
     * MIB 請款, 傳回結果：{@link net.pixnet.sdk.response.BasicResponse}
     */
    public void payMIB(){
        performAPIRequest(true, URL_ACCOUNT_MIB_PAY, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onPayMIB(parsedResponse);
            }
        });
    }

    /**
     * @see #getAnalyticData(int, int)
     */
    public void getAnalyticData(){
        getAnalyticData(-1, -1);
    }
    /**
     * 取得認證使用者拜訪紀錄分析資料, 傳回結果：{@link net.pixnet.sdk.response.Analytics}
     * @param statisticsDays 列出指定天數的歷史拜訪資訊，最少 0 天，最多 45 天
     * @param refererDays 列出指定天數的誰連結我資訊，最少 0 天，最多 7 天
     */
    public void getAnalyticData(int statisticsDays, int refererDays){
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        if(statisticsDays > -1 && statisticsDays < 46)
            params.add(new BasicNameValuePair("statistics_days", String.valueOf(statisticsDays)));
        if(refererDays > -1 && refererDays < 8)
            params.add(new BasicNameValuePair("referer_days", String.valueOf(refererDays)));

        performAPIRequest(true, URL_ACCOUNT_ANALYTICS, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Analytics parsedResponse;
                try {
                    parsedResponse=new Analytics(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetAnalyticData(parsedResponse);
            }
        }, params);
    }

    /**
     * 修改密碼, 傳回結果：{@link net.pixnet.sdk.response.BasicResponse}
     * @param oldPassword
     * @param newPassword
     */
    public void updatePassword(String oldPassword, String newPassword){
        if(TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("password", oldPassword));
        params.add(new BasicNameValuePair("new_password", newPassword));

        performAPIRequest(true, URL_ACCOUNT_PASSWD, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onUpdatePassword(parsedResponse);
            }
        }, params);
    }

    /**
     * 列出全部通知類型，通知數量限制為 10 筆, 傳回結果：{@link net.pixnet.sdk.response.NotificationList}
     * @see #getNotifications(NotificationType, int, boolean)
     */
    public void getNotifications(){
        getNotifications(null, -1, true);
    }
    /**
     * 列出全部通知類型，通知數量限制為 10 筆, 傳回結果：{@link net.pixnet.sdk.response.NotificationList}
     * @see #getNotifications(NotificationType, int, boolean)
     */
    public void getNotifications(NotificationType type){
        getNotifications(type, -1, true);
    }
    /**
     * 列出通知, 傳回結果：{@link net.pixnet.sdk.response.NotificationList}
     * @param type 限制傳回通知類型 （friend: 好友互動，system: 系統通知，comments: 留言，appmarket:應用市集）
     * @param limit 傳回通知數量限制
     */
    public void getNotifications(NotificationType type, int limit, boolean setRead){
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        if(type!=null)
            params.add(new BasicNameValuePair("notificatoin_type", type.name()));
        if(limit>0)
            params.add(new BasicNameValuePair("limit", String.valueOf(limit)));
        params.add(new BasicNameValuePair("skip_set_read", setRead?"0":"1"));

        performAPIRequest(true, URL_ACCOUNT_NOTIFICATIONS, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                NotificationList parsedResponse;
                try {
                    parsedResponse=new NotificationList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetNotifications(parsedResponse);
            }
        }, params);
    }

    /**
     * 將通知設為已讀
     * @param id notification id
     */
    public void markNotificationAsRead(String id){
        if(TextUtils.isEmpty(id)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        String url=URL_ACCOUNT_NOTIFICATIONS+"/"+id+"/read";
        performAPIRequest(true, url, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).markNotificationAsRead(parsedResponse);
            }
        });
    }

    /**
     * 讀取使用者公開資訊, 傳回結果：{@link net.pixnet.sdk.response.User}
     * @param userName 使用者名稱
     */
    public void getUserInfo(String userName){
        performAPIRequest(false, URL_USER+"/"+userName, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                User parsedResponse;
                try {
                    parsedResponse=new User(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetUserInfo(parsedResponse);
            }
        });
    }

    // album
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
                ((PixnetApiResponseListener) listener).onGetMain(parsedResponse);
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
                ((PixnetApiResponseListener) listener).onGetSetAndFolderList(parsedResponse);
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
                ((PixnetApiResponseListener) listener).onGetFolder(parsedResponse);
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
                ((PixnetApiResponseListener) listener).onGetFolderList(parsedResponse);
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
                ((PixnetApiResponseListener) listener).onGetSet(parsedResponse);
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
                ((PixnetApiResponseListener) listener).onGetSetList(parsedResponse);
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
                ((PixnetApiResponseListener) listener).onGetSetListByNear(parsedResponse);
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
                ((PixnetApiResponseListener) listener).onGetElement(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #getElementListBySet(String, String, ElementType, int, int, String, boolean, boolean, boolean, int, int)
     */
    public void getElementListBySet(String setId){
        getElementListBySet(setId, 1);
    }
    /**
     * @see #getElementListBySet(String, String, ElementType, int, int, String, boolean, boolean, boolean, int, int)
     */
    public void getElementListBySet(String setId, int page){
        getElementListBySet(setId, ElementType.pic, page);
    }
    /**
     * @see #getElementListBySet(String, String, ElementType, int, int, String, boolean, boolean, boolean, int, int)
     */
    public void getElementListBySet(String setId, ElementType type, int page){
        getElementListBySet(setId, type, page, false);
    }
    /**
     * @see #getElementListBySet(String, String, ElementType, int, int, String, boolean, boolean, boolean, int, int)
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
                ((PixnetApiResponseListener) listener).onGetElementListBySet(parsedResponse);
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
                ((PixnetApiResponseListener) listener).onGetElementListByNear(parsedResponse);
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
                ((PixnetApiResponseListener) listener).onGetCommentListBySet(parsedResponse);
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

        performAPIRequest(false, URL_ALBUM_COMMENT, new RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onGetCommentListByElement(parsedResponse);
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
                ((PixnetApiResponseListener) listener).onAddFolder(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #addSet(String, String, Permission, String, boolean, boolean, CommentPermission, String, String, List, boolean, boolean, String)
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
                ((PixnetApiResponseListener) listener).onAddSet(parsedResponse);
            }
        }, params);
    }

    /**
     * 新增相簿圖片影片
     */
    public void addElement(String setId, File file){
        if(TextUtils.isEmpty(setId)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("set_id", setId));
        List<FileNameValuePair> files=null;
        if(file!=null) {
            files = new ArrayList<FileNameValuePair>();
            files.add(new FileNameValuePair("upload_file", file));
        }

        performAPIRequest(true, URL_ELEMENTS, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onAddElement(parsedResponse);
            }
        }, params, files);
    }

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
                ((PixnetApiResponseListener)listener).onAddFaceByRecommend(parsedResponse);
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
                ((PixnetApiResponseListener)listener).onAddFaceByElement(parsedResponse);
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
                ((PixnetApiResponseListener)listener).onUpdateFolder(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #updateSet(String, String, String, Permission, String, boolean, boolean, CommentPermission, String, String, List, boolean, boolean, String)
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
                ((PixnetApiResponseListener)listener).onUpdateSet(parsedResponse);
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
                ((PixnetApiResponseListener)listener).onUpdateFace(parsedResponse);
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
                ((PixnetApiResponseListener)listener).onDeleteFolder(parsedResponse);
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
                ((PixnetApiResponseListener)listener).onDeleteSet(parsedResponse);
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
                ((PixnetApiResponseListener)listener).onDeleteElement(parsedResponse);
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
                ((PixnetApiResponseListener)listener).onDeleteCommentFromSet(parsedResponse);
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
        performAPIRequest(true, URL_ALBUM_COMMENT +"/"+commentId, Method.DELETE, new RequestCallback() {
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
                ((PixnetApiResponseListener)listener).onDeleteCommentFromElement(parsedResponse);
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
                ((PixnetApiResponseListener)listener).onDeleteFace(parsedResponse);
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
                ((PixnetApiResponseListener)listener).onSortSetAndFolders(parsedResponse);
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
                ((PixnetApiResponseListener)listener).onSortSets(parsedResponse);
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
                ((PixnetApiResponseListener)listener).onSortElementList(parsedResponse);
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
        performAPIRequest(true, URL_ALBUM_CONIFG, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener)listener).onGetConfig(parsedResponse);
            }
        });
    }

    // blog
    public void getBlogCategorieList() {
        getBlogCategorieList(defaultUserName);
    }

    public void getBlogCategorieList(String user) {
        getBlogCategorieList(user, null);
    }

    public void getBlogCategorieList(String user, String blog_password) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(blog_password))
            params.add(new BasicNameValuePair("blog_password", blog_password));
        performAPIRequest(false, URL_CATEGORY, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                CategoryList parsedResponse;
                try {
                    parsedResponse=new CategoryList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetBlogCategorieList(parsedResponse);
            }
        }, params);
    }

    public void getCategorieList() {
        getCategorieList(null, null);
    }

    public void getCategorieList(String include_groups, String include_thumbs) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(include_groups)) {
            params.add(new BasicNameValuePair("include_groups", include_groups));
        }
        if (!TextUtils.isEmpty(include_thumbs)) {
            params.add(new BasicNameValuePair("include_thumbs", include_thumbs));
        }
        performAPIRequest(false, URL_SITE_CATEGORY, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                SiteCategoryList parsedResponse;
                try {
                    parsedResponse=new SiteCategoryList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetCategorieList(parsedResponse);
            }
        }, params);
    }

    public void addCategory(String name) {
        addCategory(name, null, null, null, null, null);
    }

    public void addCategory(String name, String type, String description, String show_index, String site_category_id, String site_category_done) {
        if (name == null || TextUtils.isEmpty(name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        if (!TextUtils.isEmpty(type)) {
            params.add(new BasicNameValuePair("type", type));
        }
        if (!TextUtils.isEmpty(description)) {
            params.add(new BasicNameValuePair("description", description));
        }
        if (!TextUtils.isEmpty(show_index)) {
            params.add(new BasicNameValuePair("show_index", show_index));
        }
        if (!TextUtils.isEmpty(site_category_id)) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        if (!TextUtils.isEmpty(site_category_done)) {
            params.add(new BasicNameValuePair("site_category_done", site_category_done));
        }
        performAPIRequest(true, URL_CATEGORY, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onAddCategory(parsedResponse);
            }
        }, params);
    }

    public void updateCategory(String id, String name, String type, String description, String show_index, String site_category_id, String site_category_done) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(name)) {
            params.add(new BasicNameValuePair("name", name));
        }
        if (!TextUtils.isEmpty(type)) {
            params.add(new BasicNameValuePair("type", type));
        }
        if (!TextUtils.isEmpty(description)) {
            params.add(new BasicNameValuePair("description", description));
        }
        if (!TextUtils.isEmpty(show_index)) {
            params.add(new BasicNameValuePair("show_index", show_index));
        }
        if (!TextUtils.isEmpty(site_category_id)) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        if (!TextUtils.isEmpty(site_category_done)) {
            params.add(new BasicNameValuePair("site_category_done", site_category_done));
        }
        performAPIRequest(true, URL_CATEGORY + "/" + id, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onUpdateCategory(parsedResponse);
            }
        }, params);
    }

    public void deleteCategory(String id) {
        deleteCategory(id, null);
    }

    public void deleteCategory(String id, String type) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(type)) {
            params.add(new BasicNameValuePair("type", type));
        }
        performAPIRequest(true, URL_CATEGORY + "/" + id, Request.Method.DELETE, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onDeleteCategory(parsedResponse);
            }
        }, params);
    }

    public void sortCategorieList(String ids) {
        if (ids == null || TextUtils.isEmpty(ids)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ids", ids));
        performAPIRequest(true, URL_CATEGORY + "/position", Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onSortCategorieList(parsedResponse);
            }
        }, params);
    }

    public void getAllArticleList() {
        getAllArticleList(1);
    }

    public void getAllArticleList(int page) {
        getAllArticleList(page, defaultPerPage);
    }

    public void getAllArticleList(int page, int per_page) {
        getAllArticleList(defaultUserName, null, page, per_page, null, null, null, defaultTrimUser);
    }

    public void getAllArticleList(String user, String blog_password, int page, int per_page, String category_id, String status, String is_top, boolean trim_user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(blog_password)) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        params.add(new BasicNameValuePair("page", String.valueOf(page)));
        params.add(new BasicNameValuePair("per_page", String.valueOf(per_page)));

        if (!TextUtils.isEmpty(category_id)) {
            params.add(new BasicNameValuePair("category_id", category_id));
        }
        if (!TextUtils.isEmpty(status)) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (!TextUtils.isEmpty(is_top)) {
            params.add(new BasicNameValuePair("is_top", is_top));
        }
        params.add(new BasicNameValuePair("trim_user", trim_user ? "1" : "0"));
        performAPIRequest(false, URL_ARTICLE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                ArticleList parsedResponse;
                try {
                    parsedResponse=new ArticleList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetAllArticleList(parsedResponse);
            }
        }, params);
    }

    public void getArticle(String id) {
        getArticle(id, defaultUserName);
    }

    public void getArticle(String id, String user) {
        getArticle(id, user, null, null);
    }

    public void getArticle(String id, String user, String blog_password, String article_password) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(blog_password)) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (!TextUtils.isEmpty(article_password)) {
            params.add(new BasicNameValuePair("article_password", article_password));
        }
        performAPIRequest(false, URL_ARTICLE + "/" + id, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Article parsedResponse;
                try {
                    parsedResponse=new Article(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetArticle(parsedResponse);
            }
        }, params);
    }

    public void getRelatedArticleList(String id) {
        getRelatedArticleList(id, defaultUserName);
    }

    public void getRelatedArticleList(String id, String user) {
        getRelatedArticleList(id, user, null, null);
    }

    public void getRelatedArticleList(String id, String user, String with_body, String limit) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(with_body)) {
            params.add(new BasicNameValuePair("with_body", with_body));
        }
        if (!TextUtils.isEmpty(limit)) {
            params.add(new BasicNameValuePair("limit", limit));
        }
        performAPIRequest(false, URL_ARTICLE + "/" + id + "/related", new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                ArticleList parsedResponse;
                try {
                    parsedResponse=new ArticleList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetRelatedArticleList(parsedResponse);
            }
        }, params);
    }

    public void addArticle(String title, String body) {
        addArticle(title, body, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public void addArticle(String title, String body, String status, String public_at, String category_id, String site_category_id, String use_nl2br, String comment_perm, String comment_hidden, String tags, String thumb, String cover, String trackback, String password, String password_hint, String friend_group_ids, String notify_twitter, String notify_facebook) {
        if (title == null || TextUtils.isEmpty(title)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (body == null || TextUtils.isEmpty(body)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("body", body));
        if (!TextUtils.isEmpty(status)) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (!TextUtils.isEmpty(public_at)) {
            params.add(new BasicNameValuePair("public_at", public_at));
        }
        if (!TextUtils.isEmpty(category_id)) {
            params.add(new BasicNameValuePair("category_id", category_id));
        }
        if (!TextUtils.isEmpty(site_category_id)) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        if (!TextUtils.isEmpty(use_nl2br)) {
            params.add(new BasicNameValuePair("use_nl2br", use_nl2br));
        }
        if (!TextUtils.isEmpty(comment_perm)) {
            params.add(new BasicNameValuePair("comment_perm", comment_perm));
        }
        if (!TextUtils.isEmpty(comment_hidden)) {
            params.add(new BasicNameValuePair("comment_hidden", comment_hidden));
        }
        if (!TextUtils.isEmpty(tags)) {
            params.add(new BasicNameValuePair("tags", tags));
        }
        if (!TextUtils.isEmpty(thumb)) {
            params.add(new BasicNameValuePair("thumb", thumb));
        }
        if (!TextUtils.isEmpty(thumb)) {
            params.add(new BasicNameValuePair("cover", cover));
        }
        if (!TextUtils.isEmpty(trackback)) {
            params.add(new BasicNameValuePair("trackback", trackback));
        }
        if (!TextUtils.isEmpty(password)) {
            params.add(new BasicNameValuePair("password", password));
        }
        if (!TextUtils.isEmpty(password_hint)) {
            params.add(new BasicNameValuePair("password_hint", password_hint));
        }
        if (!TextUtils.isEmpty(friend_group_ids)) {
            params.add(new BasicNameValuePair("friend_group_ids", friend_group_ids));
        }
        if (!TextUtils.isEmpty(notify_twitter)) {
            params.add(new BasicNameValuePair("notify_twitter", notify_twitter));
        }
        if (!TextUtils.isEmpty(notify_facebook)) {
            params.add(new BasicNameValuePair("notify_facebook", notify_facebook));
        }
        performAPIRequest(true, URL_ARTICLE, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onAddArticle(parsedResponse);
            }
        }, params);
    }

    public void updateArticle(String id, String title, String body, String status, String public_at, String category_id, String site_category_id, String use_nl2br, String comment_perm, String comment_hidden, String tags, String thumb, String cover, String trackback, String password, String password_hint, String friend_group_ids, String notify_twitter, String notify_facebook) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(title)) {
            params.add(new BasicNameValuePair("title", title));
        }
        if (!TextUtils.isEmpty(body)) {
            params.add(new BasicNameValuePair("body", body));
        }
        if (!TextUtils.isEmpty(status)) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (!TextUtils.isEmpty(public_at)) {
            params.add(new BasicNameValuePair("public_at", public_at));
        }
        if (!TextUtils.isEmpty(category_id)) {
            params.add(new BasicNameValuePair("category_id", category_id));
        }
        if (!TextUtils.isEmpty(site_category_id)) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        if (!TextUtils.isEmpty(use_nl2br)) {
            params.add(new BasicNameValuePair("use_nl2br", use_nl2br));
        }
        if (!TextUtils.isEmpty(comment_perm)) {
            params.add(new BasicNameValuePair("comment_perm", comment_perm));
        }
        if (!TextUtils.isEmpty(comment_hidden)) {
            params.add(new BasicNameValuePair("comment_hidden", comment_hidden));
        }
        if (!TextUtils.isEmpty(tags)) {
            params.add(new BasicNameValuePair("tags", tags));
        }
        if (!TextUtils.isEmpty(thumb)) {
            params.add(new BasicNameValuePair("thumb", thumb));
        }
        if (!TextUtils.isEmpty(thumb)) {
            params.add(new BasicNameValuePair("cover", cover));
        }
        if (!TextUtils.isEmpty(trackback)) {
            params.add(new BasicNameValuePair("trackback", trackback));
        }
        if (!TextUtils.isEmpty(password)) {
            params.add(new BasicNameValuePair("password", password));
        }
        if (!TextUtils.isEmpty(password_hint)) {
            params.add(new BasicNameValuePair("password_hint", password_hint));
        }
        if (!TextUtils.isEmpty(friend_group_ids)) {
            params.add(new BasicNameValuePair("friend_group_ids", friend_group_ids));
        }
        if (!TextUtils.isEmpty(notify_twitter)) {
            params.add(new BasicNameValuePair("notify_twitter", notify_twitter));
        }
        if (!TextUtils.isEmpty(notify_facebook)) {
            params.add(new BasicNameValuePair("notify_facebook", notify_facebook));
        }
        performAPIRequest(true, URL_ARTICLE + "/" + id, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onUpdateArticle(parsedResponse);
            }
        }, params);
    }

    public void deleteArticle(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_ARTICLE + "/" + id, Request.Method.DELETE, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onDeleteArticle(parsedResponse);
            }
        }, params);
    }

    public void getArticleListByLatest() {
        getArticleListByLatest(defaultUserName, null, null, defaultTrimUser);
    }

    public void getArticleListByLatest(String user, String blog_password, String limit, boolean trim_user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(blog_password)) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (!TextUtils.isEmpty(limit)) {
            params.add(new BasicNameValuePair("limit", limit));
        }
        params.add(new BasicNameValuePair("trim_user", trim_user ? "1" : "0"));
        performAPIRequest(false, URL_ARTICLE + "/latest", new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                ArticleList parsedResponse;
                try {
                    parsedResponse=new ArticleList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetArticleListByLatest(parsedResponse);
            }
        }, params);
    }

    /**
     * 列出部落格熱門文章
     */
    public void getArticleListByHot() {
        getArticleListByHot(defaultUserName, null, 1, defaultTrimUser);
    }

    /**
     * 列出部落格熱門文章
     * @param user 指定要回傳的使用者資訊
     * @param blogPassword 如果指定使用者的 Blog 被密碼保護，則需要指定這個參數以通過授權
     * @param limit 回傳筆數
     * @param trim_user 是否每篇文章都要回傳作者資訊, 如果設定為 true, 則就不回傳
     */
    public void getArticleListByHot(String user, String blogPassword, int limit, boolean trim_user) {
        if (TextUtils.isEmpty(user)) {
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(blogPassword))
            params.add(new BasicNameValuePair("blog_password", blogPassword));
        if (limit>0)
            params.add(new BasicNameValuePair("limit", String.valueOf(limit)));
        params.add(new BasicNameValuePair("trim_user", trim_user ? "1" : "0"));

        performAPIRequest(false, URL_ARTICLE + "/hot", new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                ArticleList parsedResponse;
                try {
                    parsedResponse=new ArticleList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetArticleListByHot(parsedResponse);
            }
        }, params);
    }

    /**
     * 列出指定時間內的部落格熱門文章
     * @param limit 回傳筆數
     * @param period 起始日與結束日 "YYYYmmdd-YYYYmmdd"
     */
    public void getArticleListByHotWithin(int limit, String period) {
        getArticleListByHotWithin(defaultUserName, limit, defaultTrimUser, period);
    }

    /**
     * 列出指定時間內的部落格熱門文章
     * @param user 指定要回傳的使用者資訊
     * @param limit 回傳筆數
     * @param trim_user 是否每篇文章都要回傳作者資訊, 如果設定為 true, 則就不回傳
     * @param period 起始日與結束日 "YYYYmmdd-YYYYmmdd"
     */
    public void getArticleListByHotWithin(String user, int limit, boolean trim_user, String period) {
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(period)) {
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (limit>0)
            params.add(new BasicNameValuePair("limit", String.valueOf(limit)));
        params.add(new BasicNameValuePair("trim_user", trim_user ? "1" : "0"));

        String url=URL_ARTICLE + "/hot/" + period;

        performAPIRequest(true, url, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                PeriodArticleList parsedResponse;
                try {
                    parsedResponse=new PeriodArticleList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).getArticleListByHotWithin(parsedResponse);
            }
        }, params);
    }

    public void searchArticleList(String key) {
        searchArticleList(key, 1);
    }

    public void searchArticleList(String key, int page) {
        searchArticleList(key, defaultUserName, null, null, page, defaultPerPage);
    }

    public void searchArticleList(String key, String user, String site, String type, int page, int per_page) {
        if (key == null || TextUtils.isEmpty(key)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key", key));
        params.add(new BasicNameValuePair("user", user));

        if (!TextUtils.isEmpty(site)) {
            params.add(new BasicNameValuePair("site", site));
        }
        if (!TextUtils.isEmpty(type)) {
            params.add(new BasicNameValuePair("type", type));
        }
        params.add(new BasicNameValuePair("page", String.valueOf(page)));
        params.add(new BasicNameValuePair("per_page", String.valueOf(per_page)));

        performAPIRequest(false, URL_ARTICLE + "/search", new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                ArticleList parsedResponse;
                try {
                    parsedResponse=new ArticleList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onSearchArticleList(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #getCommentList(String, String, String, String, String, String, int, int)
     */
    public void getCommentList(){
        getCommentList(null);
    }
    /**
     * @see #getCommentList(String, String, String, String, String, String, int, int)
     */
    public void getCommentList(String article_id){
        getCommentList(article_id,1);
    }
    /**
     * @see #getCommentList(String, String, String, String, String, String, int, int)
     */
    public void getCommentList(String article_id,int page){
        getCommentList(defaultUserName,article_id,null,null,null,null,page,defaultPerPage);
    }
    /**
     *
     * @param filter 顯示特別屬性的留言. whisper: 只顯示悄悄話留言; nospam: 只顯示非廣告留言; noreply: 只顯示未回覆留言
     * @param sort 排序條件. date-posted-asc: 依照留言時間由舊到新排序; date-posted-desc: 依照留言時間由新到舊排序
     * @see #getCommentList(String, String, String, String, String, String, int, int)
     */
    public void getCommentList(String filter, String sort, int page) {
        getCommentList(defaultUserName,null,null,null,filter,sort,page,defaultPerPage);
    }
    /**
     * 列出部落格留言
     * @param user 指定要回傳的使用者資訊
     * @param article_id 指定要回傳的留言文章
     * @param blog_password 如果指定使用者的 Blog 被密碼保護，則需要指定這個參數以通過授權
     * @param article_password 如果指定使用者的文章被密碼保護，則需要指定這個參數以通過授權
     * @param filter 顯示特別屬性的留言. whisper: 只顯示悄悄話留言; nospam: 只顯示非廣告留言; noreply: 只顯示未回覆留言
     * @param sort 排序條件. date-posted-asc: 依照留言時間由舊到新排序; date-posted-desc: 依照留言時間由新到舊排序
     * @param page 頁數
     * @param per_page 每頁幾筆
     */
    public void getCommentList(String user, String article_id, String blog_password, String article_password, String filter, String sort, int page, int per_page) {
        if (TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if(!TextUtils.isEmpty(article_id))
            params.add(new BasicNameValuePair("article_id", article_id));
        if (!TextUtils.isEmpty(blog_password)) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (!TextUtils.isEmpty(article_password)) {
            params.add(new BasicNameValuePair("article_password", article_password));
        }
        if (!TextUtils.isEmpty(filter)) {
            params.add(new BasicNameValuePair("filter", filter));
        }
        if (!TextUtils.isEmpty(sort)) {
            params.add(new BasicNameValuePair("sort", sort));
        }
        if(page>0)
            params.add(new BasicNameValuePair("page", String.valueOf(page)));
        if(per_page>0)
            params.add(new BasicNameValuePair("per_page", String.valueOf(per_page)));
        performAPIRequest(false, URL_BLOG_COMMENT, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onGetCommentList(parsedResponse);
            }
        }, params);
    }

    /**
     * @see #addComment(String, String, String, String, String, String, String, String, String, String)
     */
    public void addComment(String article_id,String body){
        addComment(article_id, body, defaultUserName);
    }
    /**
     * @see #addComment(String, String, String, String, String, String, String, String, String, String)
     */
    public void addComment(String article_id,String body,String user){
        addComment(article_id,body,user,null,null,null,null,null,null,null);
    }
    /**
     * 新增部落格留言
     * @param article_id 要留言的文章 id
     * @param body 留言內容
     * @param user 要留言的部落格作者名稱
     * @param author 留言的暱稱, 不填入則預設代入認證使用者的 display_name
     * @param title 留言標題
     * @param url 個人網頁
     * @param is_open 公開留言/悄悄話
     * @param email 電子郵件
     * @param blog_password 如果指定使用者的 Blog 被密碼保護，則需要指定這個參數以通過授權
     * @param article_password 如果指定使用者的文章被密碼保護，則需要指定這個參數以通過授權
     */
    public void addComment(String article_id, String body, String user, String author, String title, String url, String is_open, String email, String blog_password, String article_password) {
        if (article_id == null || TextUtils.isEmpty(article_id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (body == null || TextUtils.isEmpty(body)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("article_id", article_id));
        params.add(new BasicNameValuePair("body", body));
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(author)) {
            params.add(new BasicNameValuePair("author", author));
        }
        if (!TextUtils.isEmpty(title)) {
            params.add(new BasicNameValuePair("title", title));
        }
        if (!TextUtils.isEmpty(url)) {
            params.add(new BasicNameValuePair("url", url));
        }
        if (!TextUtils.isEmpty(is_open)) {
            params.add(new BasicNameValuePair("is_open", is_open));
        }
        if (!TextUtils.isEmpty(email)) {
            params.add(new BasicNameValuePair("email", email));
        }
        if (!TextUtils.isEmpty(blog_password)) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (!TextUtils.isEmpty(article_password)) {
            params.add(new BasicNameValuePair("article_password", article_password));
        }
        performAPIRequest(true, URL_BLOG_COMMENT, Request.Method.POST,new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onAddComment(parsedResponse);
            }
        },params);
    }

    public void getComment(String id) {
        getComment(id, defaultUserName);
    }

    public void getComment(String id, String user) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        performAPIRequest(false, URL_BLOG_COMMENT + "/" + id, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Comment parsedResponse;
                try {
                    parsedResponse=new Comment(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetComment(parsedResponse);
            }
        }, params);
    }

    /**
     * 回覆部落格留言，可以重覆使用這個功能來修改回覆內容
     * @param body 回覆的內容
     * @param id 欲回覆的留言 id
     */
    public void replyComment(String body, String... id) {
        if(id==null || id.length<1){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        if (body == null || TextUtils.isEmpty(body)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        String ids="";
        for(String str : id){
            if(!TextUtils.isEmpty(str)) {
                if(ids.length()>0)
                    ids+=",";
                ids += str;
            }
        }
        if(ids.length()<1){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("api_version", "2"));
        params.add(new BasicNameValuePair("body", body));
        performAPIRequest(true, URL_BLOG_COMMENT + "/" + ids + "/reply", Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onReplyComment(parsedResponse);
            }
        }, params);
    }

    /**
     * 將留言設為悄悄話
     * @param id 欲更動的留言 id
     * @param visible 是否公開? false=悄悄話
     */
    public void setCommentVisibility(boolean visible, String... id) {
        if(id==null || id.length<1){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        String ids="";
        for(String str : id){
            if(!TextUtils.isEmpty(str)) {
                if(ids.length()>0)
                    ids+=",";
                ids += str;
            }
        }
        if(ids.length()<1){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("api_version", "2"));
        String url;
        if (visible) {
            url = URL_BLOG_COMMENT + "/" + ids + "/open";
        } else {
            url = URL_BLOG_COMMENT + "/" + ids + "/close";
        }
        performAPIRequest(true, url, Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onSetCommentVisibility(parsedResponse);
            }
        }, params);
    }

    /**
     * 刪除部落格留言
     * @param id 欲刪除的留言 id
     */
    public void deleteComment(String... id) {
        if(id==null || id.length<1){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        String ids="";
        for(String str : id){
            if(!TextUtils.isEmpty(str)) {
                if(ids.length()>0)
                    ids+=",";
                ids += str;
            }
        }
        if(ids.length()<1){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("api_version", "2"));
        performAPIRequest(true, URL_BLOG_COMMENT + "/" + ids, Request.Method.DELETE, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onDeleteComment(parsedResponse);
            }
        }, params);
    }

    public void getCommentListByLatest() {
        getCommentListByLatest(defaultUserName);
    }

    public void getCommentListByLatest(String user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        performAPIRequest(false, URL_BLOG_COMMENT + "/latest", new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onGetCommentListByLatest(parsedResponse);
            }
        }, params);
    }

    /**
     * 將留言設為廣告／非廣告留言
     * @param isSpam 是否廣告
     * @param id 欲更動的留言 id
     */
    public void markComment(boolean isSpam, String... id) {
        if(id==null || id.length<1){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        String ids="";
        for(String str : id){
            if(!TextUtils.isEmpty(str)) {
                if(ids.length()>0)
                    ids+=",";
                ids += str;
            }
        }
        if(ids.length()<1){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("api_version", "2"));
        String url= URL_BLOG_COMMENT +"/"+ids+"/";
        if(isSpam)
            url+="mark_spam";
        else url+="mark_ham";
        performAPIRequest(true, url, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onMarkComment(parsedResponse);
            }
        }, params);
    }

    public void getBlogInfo() {
        getBlogInfo(defaultUserName);
    }

    public void getBlogInfo(String user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));

        performAPIRequest(false, URL_BLOG, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BlogInfo parsedResponse;
                try {
                    parsedResponse=new BlogInfo(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetBlogInfo(parsedResponse);
            }
        }, params);
    }

    public void setBlogInfo(String name, String description, String keyword, String site_category_id) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(name)) {
            params.add(new BasicNameValuePair("name", name));
        }
        if (!TextUtils.isEmpty(description)) {
            params.add(new BasicNameValuePair("description", description));
        }
        if (!TextUtils.isEmpty(keyword)) {
            params.add(new BasicNameValuePair("keyword", keyword));
        }
        if (!TextUtils.isEmpty(site_category_id)) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        performAPIRequest(true, URL_BLOG, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onSetBlogInfo(parsedResponse);
            }
        }, params);
    }


    public void getTags(){
        getTags(defaultUserName);
    }
    /**
     * 部落格 熱門標籤 及 相關標籤
     * @param userName 使用者名稱
     */
    public void getTags(String userName){
        if (TextUtils.isEmpty(userName)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        performAPIRequest(false, URL_TAGS, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Tags parsedResponse;
                try {
                    parsedResponse=new Tags(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetTags(parsedResponse);
            }
        }, params);
    }

    // friend
    public void getFriendNews(String group_type, String group_id, String before_time) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(group_type))
            params.add(new BasicNameValuePair("group_type", group_type));
        if (!TextUtils.isEmpty(group_id))
            params.add(new BasicNameValuePair("group_id", group_id));
        if (!TextUtils.isEmpty(before_time))
            params.add(new BasicNameValuePair("before_time", before_time));
        performAPIRequest(true, URL_NEWS, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                NewsList parsedResponse;
                try {
                    parsedResponse=new NewsList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetFriendNews(parsedResponse);
            }
        }, params);
    }

    public void getGroupList(int page, int per_page) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("page", String.valueOf(page)));
        params.add(new BasicNameValuePair("per_page", String.valueOf(per_page)));
        performAPIRequest(true, URL_GROUP, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                GroupList parsedResponse;
                try {
                    parsedResponse=new GroupList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetGroupList(parsedResponse);
            }
        }, params);
    }

    public void addGroup(String name) {
        if (name == null || TextUtils.isEmpty(name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        performAPIRequest(true, URL_GROUP, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onAddGroup(parsedResponse);
            }
        }, params);
    }

    public void updateGroup(String id, String name) {
        if (name == null || TextUtils.isEmpty(name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        performAPIRequest(true, URL_GROUP + "/" + id, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onUpdateGroup(parsedResponse);
            }
        }, params);
    }

    public void removeGroup(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_GROUP + "/" + id, Request.Method.DELETE, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onRemoveGroup(parsedResponse);
            }
        }, params);
    }

    public void getFriendshipList(String cursor, String cursor_name, String bidirectional) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(cursor))
            params.add(new BasicNameValuePair("cursor", cursor));
        if (!TextUtils.isEmpty(cursor_name))
            params.add(new BasicNameValuePair("cursor_name", cursor_name));
        if (!TextUtils.isEmpty(bidirectional))
            params.add(new BasicNameValuePair("bidirectional", bidirectional));
        performAPIRequest(true, URL_FRIENDSHIP, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                FriendshipList parsedResponse;
                try {
                    parsedResponse=new FriendshipList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetFriendshipList(parsedResponse);
            }
        }, params);
    }

    public void addFriendship(String user_name) {
        if (user_name == null || TextUtils.isEmpty(user_name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        performAPIRequest(true, URL_FRIENDSHIP, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onAddFriendship(parsedResponse);
            }
        }, params);
    }

    public void removeFriendship(String user_name) {
        if (user_name == null || TextUtils.isEmpty(user_name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        performAPIRequest(true, URL_FRIENDSHIP + "/delete", Request.Method.DELETE, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onRemoveFriendship(parsedResponse);
            }
        }, params);
    }

    public void addFriendshipToGroup(String user_name, String group_id) {
        if (user_name == null || TextUtils.isEmpty(user_name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (group_id == null || TextUtils.isEmpty(group_id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        params.add(new BasicNameValuePair("group_id", group_id));
        performAPIRequest(true, URL_FRIENDSHIP + "/append_group", Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onAddFriendshipToGroup(parsedResponse);
            }
        }, params);
    }

    public void removeFriendshipFromGroup(String user_name, String group_id) {
        if (user_name == null || TextUtils.isEmpty(user_name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (group_id == null || TextUtils.isEmpty(group_id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        params.add(new BasicNameValuePair("group_id", group_id));
        performAPIRequest(true, URL_FRIENDSHIP + "/remove_group", Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onRemoveFriendshipFromGroup(parsedResponse);
            }
        }, params);
    }

    public void getSubscribedFriendship(int page, int per_page) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("page", String.valueOf(page)));
        params.add(new BasicNameValuePair("per_page", String.valueOf(per_page)));
        performAPIRequest(true, URL_SUBSCRIPTION, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                SubscriptionList parsedResponse;
                try {
                    parsedResponse=new SubscriptionList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetSubscribedFriendship(parsedResponse);
            }
        }, params);
    }

    public void addSubscription(String user, String group_id) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(group_id))
            params.add(new BasicNameValuePair("group_id", group_id));
        performAPIRequest(true, URL_SUBSCRIPTION, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onAddSubscription(parsedResponse);
            }
        }, params);
    }

    public void removeSubscription(String user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        performAPIRequest(true, URL_SUBSCRIPTION + "/" + user, Request.Method.DELETE, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onRemoveSubscription(parsedResponse);
            }
        }, params);
    }

    public void joinSubscriptionGroup(String user, String group_ids) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (group_ids == null || TextUtils.isEmpty(group_ids)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        params.add(new BasicNameValuePair("group_ids", group_ids));
        performAPIRequest(true, URL_SUBSCRIPTION + "/" + user + "/join_subscription_group", Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onJoinSubscriptionGroup(parsedResponse);
            }
        }, params);
    }

    public void leaveSubscriptionGroup(String user, String group_ids) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (group_ids == null || TextUtils.isEmpty(group_ids)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        params.add(new BasicNameValuePair("group_ids", group_ids));
        performAPIRequest(true, URL_SUBSCRIPTION + "/" + user + "/leave_subscription_group", Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onLeaveSubscriptionGroup(parsedResponse);
            }
        }, params);
    }

    public void getSubscriptionGroupList() {
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                SubscriptionGroupList parsedResponse;
                try {
                    parsedResponse=new SubscriptionGroupList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetSubscriptionGroupList(parsedResponse);
            }
        });
    }

    public void addSubscriptionGroup(String name) {
        if (name == null || TextUtils.isEmpty(name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onAddSubscriptionGroup(parsedResponse);
            }
        }, params);
    }

    public void updateSubscriptionGroup(String name, String id) {
        if (name == null || TextUtils.isEmpty(name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP + "/" + id, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onUpdateSubscriptionGroup(parsedResponse);
            }
        }, params);
    }

    public void removeSubscriptionGroup(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP + "/" + id, Request.Method.DELETE, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onRemoveSubscriptionGroup(parsedResponse);
            }
        }, params);
    }

    public void sortSubscriptionGroupList(String ids) {
        if (ids == null || TextUtils.isEmpty(ids)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ids", ids));
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP + "/position", Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onSortSubscriptionGroupList(parsedResponse);
            }
        }, params);
    }

    // block
    /**
     * 列出黑名單
     */
    public void getBlockList() {
        performAPIRequest(true, URL_BLOCK, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BlocksList parsedResponse;
                try {
                    parsedResponse=new BlocksList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetBlockList(parsedResponse);
            }
        });
    }

    /**
     * 新增黑名單
     * @param user 欲加入黑名單的使用者名稱
     */
    public void addBlock(String... user) {
        if(user==null || user.length<1){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        String userstr="";
        for(String str : user){
            if(!TextUtils.isEmpty(str)) {
                if(userstr.length()>0)
                    userstr+=",";
                userstr += str;
            }
        }
        if(userstr.length()<1){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("api_version", "2"));
        params.add(new BasicNameValuePair("user", userstr));
        performAPIRequest(true, URL_BLOCK + "/create", Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onAddBlock(parsedResponse);
            }
        }, params);
    }

    /**
     * 移除黑名單
     * @param user 欲移除黑名單的使用者名稱
     */
    public void deleteBlock(String user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        performAPIRequest(true, URL_BLOCK + "/delete", Request.Method.DELETE, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onDeleteBlock(parsedResponse);
            }
        }, params);
    }

    // guestbook
    public void getGuestbookList() {
        getGuestbookList(defaultUserName, null, null, defaultPerPage);
    }

    public void getGuestbookList(String user, String filter, String cursor, int per_page) {
        if (TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(filter))
            params.add(new BasicNameValuePair("filter", filter));
        if (!TextUtils.isEmpty(cursor))
            params.add(new BasicNameValuePair("cursor", cursor));
        params.add(new BasicNameValuePair("per_page", String.valueOf(per_page)));
        performAPIRequest(false, URL_GUESTBOOK, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                GuestbookList parsedResponse;
                try {
                    parsedResponse=new GuestbookList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetGuestbookList(parsedResponse);
            }
        }, params);
    }

    public void addGuestbook(String user, String title, String body) {
        addGuestbook(user, title, body, null, null, null, true);
    }

    public void addGuestbook(String user, String title, String body, String auther, String url, String email, boolean is_open) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (title == null || TextUtils.isEmpty(title)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (body == null || TextUtils.isEmpty(body)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("body", body));
        if (!TextUtils.isEmpty(auther))
            params.add(new BasicNameValuePair("auther", auther));
        if (!TextUtils.isEmpty(url))
            params.add(new BasicNameValuePair("url", url));
        if (!TextUtils.isEmpty(email))
            params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("is_open", is_open ? "1" : "0"));

        performAPIRequest(true, URL_GUESTBOOK, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onAddGuestbook(parsedResponse);
            }
        }, params);
    }

    public void getGuestbook(String id) {
        getGuestbook(defaultUserName, id);
    }

    public void getGuestbook(String user, String id) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        params.add(new BasicNameValuePair("id", id));
        performAPIRequest(false, URL_GUESTBOOK + "/" + id, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                Guestbook parsedResponse;
                try {
                    parsedResponse=new Guestbook(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((PixnetApiResponseListener) listener).onGetGuestbook(parsedResponse);
            }
        }, params);
    }

    public void replyGuestbook(String id, String reply) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (reply == null || TextUtils.isEmpty(reply)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("reply", reply));
        params.add(new BasicNameValuePair("id", id));
        performAPIRequest(true, URL_GUESTBOOK + "/" + id + "/reply", Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onReplyGuestbook(parsedResponse);
            }
        }, params);
    }

    public void setGuestbookVisibility(String id, boolean visible) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        String url;
        if (visible) {
            url = URL_GUESTBOOK + "/" + id + "/open";
        } else {
            url = URL_GUESTBOOK + "/" + id + "/close";
        }
        performAPIRequest(true, url, Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onSetGuestbookVisibility(parsedResponse);
            }
        }, params);
    }

    public void markGuestbookToSpam(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_GUESTBOOK + "/" + id + "/mark_spam", Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onMarkGuestbookToSpam(parsedResponse);
            }
        }, params);
    }

    public void markGuestbookHam(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_GUESTBOOK + "/" + id + "/mark_ham", Request.Method.POST, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onMarkGuestbookToHam(parsedResponse);
            }
        }, params);
    }

    public void deleteGuestbook(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_GUESTBOOK + "/" + id, Request.Method.DELETE, new Request.RequestCallback() {
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
                ((PixnetApiResponseListener) listener).onDeleteGuestbook(parsedResponse);
            }
        }, params);
    }
}

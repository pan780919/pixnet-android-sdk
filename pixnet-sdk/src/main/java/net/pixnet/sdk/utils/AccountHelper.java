package net.pixnet.sdk.utils;

import android.text.TextUtils;

import net.pixnet.sdk.proxy.*;
import net.pixnet.sdk.proxy.Error;
import net.pixnet.sdk.response.AccountInfo;
import net.pixnet.sdk.response.Analytics;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.MIB;
import net.pixnet.sdk.response.NotificationList;
import net.pixnet.sdk.response.Position;
import net.pixnet.sdk.response.PositionList;
import net.pixnet.sdk.response.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class AccountHelper extends DataProxy {

    public static final String URL_ACCOUNT="https://emma.pixnet.cc/account";
    public static final String URL_ACCOUNT_INFO="https://emma.pixnet.cc/account/info";
    public static final String URL_ACCOUNT_MIB="https://emma.pixnet.cc/account/mib";
    public static final String URL_ACCOUNT_MIB_POSTION="https://emma.pixnet.cc/account/mib/positions";
    public static final String URL_ACCOUNT_MIB_PAY="https://emma.pixnet.cc/account/mibpay";
    public static final String URL_ACCOUNT_ANALYTICS="https://emma.pixnet.cc/account/analytics";
    public static final String URL_ACCOUNT_PASSWD ="https://emma.pixnet.cc/account/password";
    public static final String URL_ACCOUNT_NOTIFICATIONS ="https://emma.pixnet.cc/account/notifications";
    public static final String URL_USER ="https://emma.pixnet.cc/users";

    public static enum NotificationType{
        friend,
        system,
        comment,
        appmarket
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof AccountHelperListener)
                        ((AccountHelperListener) listener).onGetAccountInfoResponse(new AccountInfo(response));
                }
                else listener.onError(res.message);
            }
        }, params);

    }

    /**
     * @see #updateAccountInfo(String, String, String, net.pixnet.sdk.response.AccountInfo.Gender, String, String, String, String, String)
     */
    public void updateAccountInfo(String password, String displayName){
        updateAccountInfo(password, displayName, null, null, null, null, null, null, null);
    }
    /**
     * 修改認證使用者資訊, 傳回結果：{@link net.pixnet.sdk.response.BasicResponse}
     * @param password 密碼，需要檢查與系統內儲存資訊相符後才能執行修改
     * @param displayName 使用者暱稱
     * @param email 信箱
     * @param gender 性別
     * @param address 住址
     * @param phone 電話
     * @param birthday 生日（YYYYMMDD）
     * @param education 教育程度 (中學以下, 高中/高職, 專科, 大學, 研究所)
     * @param avatar 大頭照 (base64)
     */
    public void updateAccountInfo(String password, String displayName, String email, AccountInfo.Gender gender, String address, String phone, String birthday, String education, String avatar){
        if(TextUtils.isEmpty(password)){
            listener.onError(Error.MISS_PARAMETER+":password");
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
        if(!TextUtils.isEmpty(address))
            params.add(new BasicNameValuePair("address", address));
        if(!TextUtils.isEmpty(phone))
            params.add(new BasicNameValuePair("phone", phone));
        if(!TextUtils.isEmpty(birthday))
            params.add(new BasicNameValuePair("birth", birthday));
        if(!TextUtils.isEmpty(education))
            params.add(new BasicNameValuePair("education", education));
        if(!TextUtils.isEmpty(avatar))
            params.add(new BasicNameValuePair("avatar", avatar));

        performAPIRequest(true, URL_ACCOUNT_INFO, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof AccountHelperListener)
                        ((AccountHelperListener) listener).onUpdateAccountInfoResponse(res);
                }
                else listener.onError(res.message);
            }
        }, params);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof AccountHelperListener)
                        ((AccountHelperListener) listener).onGetMIBInfoResponse(new MIB(response));
                }
                else listener.onError(res.message);
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
    public void updateMIBInfo(String idNumber, String idImageFront, String idImageBack, String email, String cellPhone, String mailAddress, String domicile, boolean enableVideoAd, String name){
        if(TextUtils.isEmpty(idNumber) || TextUtils.isEmpty(idImageFront) || TextUtils.isEmpty(idImageBack) || TextUtils.isEmpty(email) || TextUtils.isEmpty(cellPhone) || TextUtils.isEmpty(mailAddress) || TextUtils.isEmpty(domicile) || TextUtils.isEmpty(name)){
            listener.onError(Error.MISS_PARAMETER);
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id_number", idNumber));
        params.add(new BasicNameValuePair("id_image_front", idImageFront));
        params.add(new BasicNameValuePair("id_image_back", idImageBack));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("cellphone", cellPhone));
        params.add(new BasicNameValuePair("mail_address", mailAddress));
        params.add(new BasicNameValuePair("domicile", domicile));
        params.add(new BasicNameValuePair("enable_video_ad", enableVideoAd?"1":"0"));
        params.add(new BasicNameValuePair("name", name));

        performAPIRequest(true, URL_ACCOUNT_MIB, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof AccountHelperListener)
                        ((AccountHelperListener) listener).onUpdateMIBInfoResponse(new MIB(response));
                }
                else listener.onError(res.message);
            }
        }, params);
    }

    /**
     * 取得 MIB 版位資料, 傳回結果：{@link net.pixnet.sdk.response.PositionList}
     */
    public void getMIBPostionListInfo(){
        performAPIRequest(true, URL_ACCOUNT_MIB_POSTION, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof AccountHelperListener)
                        ((AccountHelperListener) listener).onGetMIBPositionListInfoResponse(new PositionList(response));
                }
                else listener.onError(res.message);
            }
        });
    }
    /**
     * 取得 MIB 單一版位資料, 傳回結果：{@link net.pixnet.sdk.response.Position}
     * @param id 版位 ID, 可由 {@link #getMIBPostionListInfo()} 或 {@link #getMIBInfo()} 取得
     */
    public void getMIBPostionInfo(String id){
        if(TextUtils.isEmpty(id)){
            listener.onError(Error.MISS_PARAMETER+":id");
            return;
        }
        performAPIRequest(true, URL_ACCOUNT_MIB_POSTION+"/"+id, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof AccountHelperListener)
                        ((AccountHelperListener) listener).onGetMIBPositionInfoResponse(new Position(response));
                }
                else listener.onError(res.message);
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
            listener.onError(Error.MISS_PARAMETER+":id");
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("enabled", enabled?"1":"0"));
        params.add(new BasicNameValuePair("fixedadbox", fixed?"1":"0"));

        performAPIRequest(true, URL_ACCOUNT_MIB_POSTION+"/"+id, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof AccountHelperListener)
                        ((AccountHelperListener) listener).onUpdateMIBPositionInfoResponse(new MIB(response));
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof AccountHelperListener)
                        ((AccountHelperListener) listener).onPayMIBResponse(res);
                }
                else listener.onError(res.message);
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
        if(refererDays > -1 && statisticsDays < 8)
            params.add(new BasicNameValuePair("referer_days", String.valueOf(refererDays)));

        performAPIRequest(true, URL_ACCOUNT_ANALYTICS, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof AccountHelperListener)
                        ((AccountHelperListener) listener).onGetAnalyticDataResponse(new Analytics(response));
                } else listener.onError(res.message);
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
            listener.onError(Error.MISS_PARAMETER+":oldPassword, newPassword");
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("password", oldPassword));
        params.add(new BasicNameValuePair("new_password", newPassword));

        performAPIRequest(true, URL_ACCOUNT_PASSWD, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof AccountHelperListener)
                        ((AccountHelperListener) listener).onUpdatePasswordResponse(res);
                } else listener.onError(res.message);
            }
        }, params);
    }

    /**
     * 列出全部通知類型，通知數量限制為 10 筆, 傳回結果：{@link net.pixnet.sdk.response.NotificationList}
     * @see #getNotifications(AccountHelper.NotificationType, int)
     */
    public void getNotifications(){
        getNotifications(null, -1);
    }
    /**
     * 列出通知, 傳回結果：{@link net.pixnet.sdk.response.NotificationList}
     * @param type 限制傳回通知類型 （friend: 好友互動，system: 系統通知，comments: 留言，appmarket:應用市集）
     * @param limit 傳回通知數量限制
     */
    public void getNotifications(NotificationType type, int limit){
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        if(type!=null)
            params.add(new BasicNameValuePair("notificatoin_type", type.name()));
        if(limit>0)
            params.add(new BasicNameValuePair("limit", String.valueOf(limit)));

        performAPIRequest(true, URL_ACCOUNT_NOTIFICATIONS, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof AccountHelperListener)
                        ((AccountHelperListener) listener).onGetNotificationsResponse(new NotificationList(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    /**
     * 讀取使用者公開資訊, 傳回結果：{@link net.pixnet.sdk.response.User}
     * @param userName 使用者名稱
     */
    public void getUserInfo(String userName){
        performAPIRequest(false, URL_USER+"/"+userName, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof AccountHelperListener)
                        ((AccountHelperListener) listener).onGetUserInfoResponse(new User(response));
                }
                else listener.onError(res.message);
            }
        });
    }

}

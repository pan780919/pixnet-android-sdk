package net.pixnet.sdk.utils;

import android.text.TextUtils;

import net.pixnet.sdk.proxy.*;
import net.pixnet.sdk.proxy.Error;
import net.pixnet.sdk.response.AccountInfo;
import net.pixnet.sdk.response.BasicResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Account extends DataProxy {

    public static final String URL_ACCOUNT="https://emma.pixnet.cc/account";
    public static final String URL_ACCOUNT_INFO="https://emma.pixnet.cc/account/info";

    public static enum NotificationType{
        friend,
        system,
        comment,
        appmarket
    }

    /**
     * 讀取認證使用者資訊
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
                Helper.log(response);
                listener.onDataResponse(new AccountInfo(response));
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
     * 修改認證使用者資訊
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
                Helper.log(response);
                listener.onDataResponse(new BasicResponse(response));
            }
        }, params);
    }

}

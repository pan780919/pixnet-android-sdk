package net.pixnet.sdk.utils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.AccountInfo;
import net.pixnet.sdk.response.Analytics;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.MIB;
import net.pixnet.sdk.response.NotificationList;
import net.pixnet.sdk.response.Position;
import net.pixnet.sdk.response.PositionList;
import net.pixnet.sdk.response.User;

public class AccountHelperListener implements DataProxy.DataProxyListener {
    @Override
    public void onError(String msg){}
    @Override
    public boolean onDataResponse(BasicResponse response) {
        return false;
    }
    public void onGetAccountInfoResponse(AccountInfo response){}
    public void onUpdateAccountInfoResponse(BasicResponse response){}
    public void onGetMIBInfoResponse(MIB response){}
    public void onUpdateMIBInfoResponse(MIB response){}
    public void onGetMIBPositionListInfoResponse(PositionList response){}
    public void onGetMIBPositionInfoResponse(Position response){}
    public void onUpdateMIBPositionInfoResponse(MIB response){}
    public void onPayMIBResponse(BasicResponse response){}
    public void onGetAnalyticDataResponse(Analytics response){}
    public void onUpdatePasswordResponse(BasicResponse response){}
    public void onGetNotificationsResponse(NotificationList response){}
    public void onGetUserInfoResponse(User response){}
}

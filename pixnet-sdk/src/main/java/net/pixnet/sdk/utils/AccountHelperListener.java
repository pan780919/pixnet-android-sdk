package net.pixnet.sdk.utils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.AccountInfo;
import net.pixnet.sdk.response.Analytics;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.CellphoneVerification;
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
    public void onCellphoneVerification(CellphoneVerification response){}
    public void onGetAccountInfo(AccountInfo response){}
    public void onUpdateAccountInfo(BasicResponse response){}
    public void onGetMIBInfo(MIB response){}
    public void onUpdateMIBInfo(MIB response){}
    public void onGetMIBPositionListInfo(PositionList response){}
    public void onGetMIBPositionInfo(Position response){}
    public void onUpdateMIBPositionInfo(MIB response){}
    public void onPayMIB(BasicResponse response){}
    public void onGetAnalyticData(Analytics response){}
    public void onUpdatePassword(BasicResponse response){}
    public void onGetNotifications(NotificationList response){}
    public void markNotificationAsRead(BasicResponse response){}
    public void onGetUserInfo(User response){}
}

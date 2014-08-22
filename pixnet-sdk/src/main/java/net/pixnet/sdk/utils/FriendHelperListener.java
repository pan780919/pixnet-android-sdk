package net.pixnet.sdk.utils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.FriendshipList;
import net.pixnet.sdk.response.GroupList;
import net.pixnet.sdk.response.NewsList;
import net.pixnet.sdk.response.SubscriptionList;
import net.pixnet.sdk.response.Subscription_groupList;

/**
 * Created by Koi on 2014/8/22.
 */
public class FriendHelperListener implements DataProxy.DataProxyListener {
    @Override
    public void onError(String msg) {

    }

    @Override
    public boolean onDataResponse(BasicResponse response) {
        return false;
    }
    public void onGetSubscribedFriendship(SubscriptionList res){}
    public void onGetFriendshipList(FriendshipList res){}
    public void onGetGroupList(GroupList res){}
    public void onGetFriendNews(NewsList res){}
    public void onGetSubscriptionGroupList(Subscription_groupList res){}
}

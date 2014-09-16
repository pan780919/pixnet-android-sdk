package net.pixnet.sdk.utils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.FriendshipList;
import net.pixnet.sdk.response.GroupList;
import net.pixnet.sdk.response.NewsList;
import net.pixnet.sdk.response.SubscriptionGroupList;
import net.pixnet.sdk.response.SubscriptionList;

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
    public void onAddFriendship(BasicResponse res){}
    public void onAddFriendshipToGroup(BasicResponse res){}
    public void onRemoveFriendshipFromGroup(BasicResponse res){}
    public void onRemoveFriendship(BasicResponse res){}
    public void onAddSubscription(BasicResponse res){}
    public void onRemoveSubscription(BasicResponse res){}
    public void onJoinSubscriptionGroup(BasicResponse res){}
    public void onLeaveSubscriptionGroup(BasicResponse res){}
    public void onSortSubscriptionGroupList(BasicResponse res){}
    public void onAddSubscriptionGroup(BasicResponse res){}
    public void onUpdateSubscriptionGroup(BasicResponse res){}
    public void onRemoveSubscriptionGroup(BasicResponse res){}
    public void onGetGroupList(GroupList res){}
    public void onAddGroup(BasicResponse res){}
    public void onUpdateGroup(BasicResponse res){}
    public void onRemoveGroup(BasicResponse res){}
    public void onGetFriendNews(NewsList res){}
    public void onGetSubscriptionGroupList(SubscriptionGroupList res){}
}

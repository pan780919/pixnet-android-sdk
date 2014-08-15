package net.pixnet.sdk.test;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.utils.Helper;

/**
 * Created by Koi on 2014/8/15.
 */
public class Friend extends ItemDetailFragment {
    private static enum Apis {
        getGroupList,
        addGroup,
        updateGroup,
        removeGroup,

        getFriendshipList,
        addFriendship,
        removeFriendship,
        addFriendshipToGroup,
        removeFriendshipFromGroup,

        getFriendNews,

        getSubscribedFriendship,
        addSubscription,
        removeSubscription,
        joinSubscriptionGroup,
        leaveSubscriptionGroup,

        getSubscriptionGroupList,
        addSubscriptionGroup,
        updateSubscriptionGroup,
        removeSubscriptionGroup,
        sortSubscriptionGroupList
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Apis[] data = Apis.values();

        setListAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return data.length;
            }

            @Override
            public String getItem(int position) {
                return data[position].name();
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v;

                if (convertView == null) {
                    v = View.inflate(getActivity(), android.R.layout.simple_list_item_1, null);
                } else {
                    v = convertView;
                }

                TextView t = (TextView) v.findViewById(android.R.id.text1);
                t.setText(getItem(position));

                return v;
            }
        });
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                net.pixnet.sdk.utils.Friend friend = PIXNET.getFriend(getActivity(), new DataProxy.DataProxyListener() {
                    @Override
                    public void onError(String msg) {
                        Helper.log("error:" + msg);
                    }

                    @Override
                    public void onDataResponse(BasicResponse response) {
                        Helper.log(response.message);
                        Helper.log("onDataResponse");
                    }
                });
                friend.setDefaultUserName("emmademo");
                friend.setDefaultPerPage(2);
                friend.setDefaultTrimUser(false);
                switch (Apis.values()[position]) {
                    case getFriendNews:
                        friend.getFriendNews(null,null,null);
                        break;
                    case getGroupList:
                        friend.getGroupList(1,1);
                        break;
                    case addGroup:
                        friend.addGroup("Test");
                        break;
                    case updateGroup:
                        friend.updateGroup("375056","TestUpdate");
                        break;
                    case removeGroup:
                        friend.removeGroup("375056");
                        break;
                    case getFriendshipList:
                        friend.getFriendshipList(null,null,null);
                        break;
                    case addFriendship:
                        friend.addFriendship("kkkoooiii2Test");
                        break;
                    case removeFriendship:
                        friend.removeFriendship("kkkoooiii2Test");
                        break;
                    case addFriendshipToGroup:
                        friend.addFriendshipToGroup("kkkoooiii2Test","375056");
                        break;
                    case removeFriendshipFromGroup:
                        friend.removeFriendshipFromGroup("kkkoooiii2Test","375056");
                        break;
                    case getSubscriptionGroupList:
                        friend.getSubscriptionGroupList();
                        break;
                    case addSubscription:
                        friend.addSubscription("kkkoooiii2Test",null);
                        break;
                    case removeSubscription:
                        friend.removeSubscription("kkkoooiii2Test");
                        break;
                    case joinSubscriptionGroup:
                        friend.joinSubscriptionGroup("kkkoooiii2Test","163370");
                        break;
                    case leaveSubscriptionGroup:
                        friend.leaveSubscriptionGroup("kkkoooiii2Test","163370");
                        break;
                    case getSubscribedFriendship:
                        friend.getSubscribedFriendship(1,1);
                        break;
                    case addSubscriptionGroup:
                        friend.addSubscriptionGroup("Test");
                        break;
                    case updateSubscriptionGroup:
                        friend.updateSubscriptionGroup("TestUpdate","163370");
                        break;
                    case removeSubscriptionGroup:
                        friend.removeSubscriptionGroup("163370");
                        break;
                    case sortSubscriptionGroupList:
                        break;
                    default:
                }
            }
        });
    }
}

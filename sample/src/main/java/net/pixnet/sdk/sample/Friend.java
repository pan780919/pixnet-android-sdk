package net.pixnet.sdk.sample;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.utils.Helper;
import net.pixnet.sdk.utils.PixnetApiHelper;
import net.pixnet.sdk.utils.PixnetApiResponseListener;

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
                PixnetApiHelper friendHelper = PIXNET.getApiHelper(getActivity(), new PixnetApiResponseListener() {
                    @Override
                    public void onError(String msg) {
                        Helper.log("error:" + msg);
                    }

                    @Override
                    public boolean onDataResponse(BasicResponse response) {
                        Helper.log(response.getRawData());
                        Helper.log(response.message);
                        Helper.log("onDataResponse");
                        return true;
                    }
                });
                friendHelper.setDefaultUserName("emmademo");
                friendHelper.setDefaultPerPage(2);
                friendHelper.setDefaultTrimUser(false);
                switch (Apis.values()[position]) {
                    case getFriendNews:
                        friendHelper.getFriendNews(null,null,null);
                        break;
                    case getGroupList:
                        friendHelper.getGroupList(1,1);
                        break;
                    case addGroup:
                        friendHelper.addGroup("Test");
                        break;
                    case updateGroup:
                        friendHelper.updateGroup("375056","TestUpdate");
                        break;
                    case removeGroup:
                        friendHelper.removeGroup("375056");
                        break;
                    case getFriendshipList:
                        friendHelper.getFriendshipList(null,null,null);
                        break;
                    case addFriendship:
                        friendHelper.addFriendship("kkkoooiii2Test");
                        break;
                    case removeFriendship:
                        friendHelper.removeFriendship("kkkoooiii2Test");
                        break;
                    case addFriendshipToGroup:
                        friendHelper.addFriendshipToGroup("kkkoooiii2Test","375056");
                        break;
                    case removeFriendshipFromGroup:
                        friendHelper.removeFriendshipFromGroup("kkkoooiii2Test","375056");
                        break;
                    case getSubscriptionGroupList:
                        friendHelper.getSubscriptionGroupList();
                        break;
                    case addSubscription:
                        friendHelper.addSubscription("kkkoooiii2Test",null);
                        break;
                    case removeSubscription:
                        friendHelper.removeSubscription("kkkoooiii2Test");
                        break;
                    case joinSubscriptionGroup:
                        friendHelper.joinSubscriptionGroup("kkkoooiii2Test","163370");
                        break;
                    case leaveSubscriptionGroup:
                        friendHelper.leaveSubscriptionGroup("kkkoooiii2Test","163370");
                        break;
                    case getSubscribedFriendship:
                        friendHelper.getSubscribedFriendship(1,1);
                        break;
                    case addSubscriptionGroup:
                        friendHelper.addSubscriptionGroup("Test");
                        break;
                    case updateSubscriptionGroup:
                        friendHelper.updateSubscriptionGroup("TestUpdate","163370");
                        break;
                    case removeSubscriptionGroup:
                        friendHelper.removeSubscriptionGroup("163370");
                        break;
                    case sortSubscriptionGroupList:
                        break;
                    default:
                }
            }
        });
    }
}

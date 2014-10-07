package net.pixnet.sdk.test;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.proxy.*;
import net.pixnet.sdk.proxy.Error;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.MIB;
import net.pixnet.sdk.utils.AccountHelper;
import net.pixnet.sdk.utils.AccountHelperListener;
import net.pixnet.sdk.utils.Helper;

public class Account extends ItemDetailFragment {

    private enum Apis{
        getAccountInfo,
        updateAccountInfo,
        getMIBInfo,
        updateMIBInfo,
        getMIBPositionInfo,
        updateMIBPositionInfo,
        payMIB,
        getAnalyticData,
        updatePassword,
        getNotificationList,
        getUserInfo
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Apis[] data=Apis.values();

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

                if(convertView==null){
                    v=View.inflate(getActivity(), android.R.layout.simple_list_item_1, null);
                }
                else{
                    v= convertView;
                }

                TextView t= (TextView) v.findViewById(android.R.id.text1);
                t.setText(getItem(position));

                return v;
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AccountHelper account=PIXNET.getAccountHelper(getActivity(), new AccountHelperListener() {
                    @Override
                    public void onError(String msg) {
                        Helper.log("error:" + msg);
                    }

                    @Override
                    public boolean onDataResponse(BasicResponse response) {
                        Helper.log("onDataResponse");
                        Helper.log(response.getRawData());
                        return false;
                    }

                    @Override
                    public void onGetMIBInfo(MIB response) {
                        Helper.log(response
                                .account
                                .id_number);
                    }
                });
                switch (Apis.values()[position]){
                    case getAccountInfo:
                        account.getAccountInfo(true, null, true, true, true);
                        break;
                    case updateAccountInfo:
                        account.updateAccountInfo("121111", "UncleBen");
                        break;
                    case getMIBInfo:
                        account.getMIBInfo();
                        break;
                    case updateMIBInfo:
                        break;
                    case getMIBPositionInfo:
                        account.getMIBPostionInfo("790");
                        break;
                    case updateMIBPositionInfo:
                        account.updateMIBPositionInfo("790", true, true);
                        break;
                    case payMIB:
                        account.payMIB();
                        break;
                    case getAnalyticData:
                        account.getAnalyticData();
                        break;
                    case updatePassword:
                        account.updatePassword("121111", "221111");
                        break;
                    case getNotificationList:
                        account.getNotifications();
                        break;
                    case getUserInfo:
                        account.getUserInfo("ben68");
                        break;
                    default:
                }
            }
        });
    }
}

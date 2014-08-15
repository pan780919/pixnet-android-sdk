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
                net.pixnet.sdk.utils.Account account=PIXNET.getAccount(getActivity(), new DataProxy.DataProxyListener() {
                    @Override
                    public void onError(String msg) {
                        Helper.log("error:" + msg);
                    }

                    @Override
                    public void onDataResponse(BasicResponse response) {
                        Helper.log("onDataResponse");
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
                        break;
                    case updateMIBPositionInfo:
                        break;
                    case payMIB:
                        account.payMIB();
                        break;
                    case getAnalyticData:
                        account.getAnalyticData(2, 2);
                        break;
                    case updatePassword:
                        break;
                    case getNotificationList:
                        break;
                    case getUserInfo:
                        break;
                    default:
                }
            }
        });
    }
}

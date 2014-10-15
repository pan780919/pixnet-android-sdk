package net.pixnet.sdk.test;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.MIB;
import net.pixnet.sdk.utils.Helper;
import net.pixnet.sdk.utils.PixnetApiHelper;
import net.pixnet.sdk.utils.PixnetApiResponseListener;

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
                PixnetApiHelper apiHelper=PIXNET.getApiHelper(getActivity(), new PixnetApiResponseListener() {
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
                        apiHelper.getAccountInfo(true, null, true, true, true);
                        break;
                    case updateAccountInfo:
                        apiHelper.updateAccountInfo("121111", "UncleBen");
                        break;
                    case getMIBInfo:
                        apiHelper.getMIBInfo();
                        break;
                    case updateMIBInfo:
                        break;
                    case getMIBPositionInfo:
                        apiHelper.getMIBPostionInfo("790");
                        break;
                    case updateMIBPositionInfo:
                        apiHelper.updateMIBPositionInfo("790", true, true);
                        break;
                    case payMIB:
                        apiHelper.payMIB();
                        break;
                    case getAnalyticData:
                        apiHelper.getAnalyticData();
                        break;
                    case updatePassword:
                        apiHelper.updatePassword("121111", "221111");
                        break;
                    case getNotificationList:
                        apiHelper.getNotifications();
                        break;
                    case getUserInfo:
                        apiHelper.getUserInfo("ben68");
                        break;
                    default:
                }
            }
        });
    }
}

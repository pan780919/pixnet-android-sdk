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

public class Login extends ItemDetailFragment {

    private static enum Apis{
        LOGIN_1,
        LOGIN_2,
        LOGOUT
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String[] data={"oauth 1.0 login", "oauth 2.0 login", "logout"};

        setListAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return data.length;
            }

            @Override
            public String getItem(int position) {
                return data[position];
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

                switch (Apis.values()[position]){
                    case LOGIN_1:
                        PIXNET.oAuth1Login(getActivity(), new PIXNET.OnAccessTokenGotListener() {
                            @Override
                            public void onAccessTokenGot(String token, String secret) {
                                Helper.log("onAccessTokenGot:"+token);
                            }

                            @Override
                            public void onError(String msg) {
                                Helper.toast(getActivity(), msg);
                            }
                        });
                        break;
                    case LOGIN_2:
                        PIXNET.oAuth2Login(getActivity(), new PIXNET.OnAccessTokenGotListener() {
                            @Override
                            public void onAccessTokenGot(String token, String secret) {
                                Helper.log("onAccessTokenGot:"+token);
                            }

                            @Override
                            public void onError(String msg) {
                                Helper.toast(getActivity(), msg);
                            }
                        });
                        break;
                    case LOGOUT:
                        PIXNET.setOauthAccessTokenAndSecret(getActivity(), null, null);
                        break;
                    default:
                }

            }
        });
    }

}

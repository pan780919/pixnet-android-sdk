package net.pixnet.sdk.sample;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.utils.Helper;

public class Login extends ItemDetailFragment {

    private static enum Apis{
        LOGIN_X,
        LOGIN_1,
        LOGIN_2,
        OPENID_2,
        REFRESH,
        LOGOUT
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return Apis.values().length;
            }

            @Override
            public String getItem(int position) {
                return Apis.values()[position].name();
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
                    case LOGIN_X:
                        PIXNET.xAuthLogin(getActivity(),new PIXNET.OnAccessTokenGotListener() {
                            @Override
                            public void onAccessTokenGot(String token, String secret) {
                                Helper.log("onAccessTokenGot:"+token);
                            }

                            @Override
                            public void onAccessTokenGot(String token, String refreshToken, long expires) {}

                            @Override
                            public void onError(String msg) {
                                Helper.toast(getActivity(), msg);
                            }
                        }, "ben68", "121111");
                        break;
                    case LOGIN_1:
                        PIXNET.oAuth1Login(getActivity(), new PIXNET.OnAccessTokenGotListener() {
                            @Override
                            public void onAccessTokenGot(String token, String secret) {
                                Helper.log("onAccessTokenGot:"+token);
                            }

                            @Override
                            public void onAccessTokenGot(String token, String refreshToken, long expires) {}

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
                            public void onAccessTokenGot(String token, String refreshToken, long expires) {}

                            @Override
                            public void onError(String msg) {
                                Helper.toast(getActivity(), msg);
                            }
                        });
                        break;
                    case OPENID_2:
                        PIXNET.oAuth2OpenIdLogin(getActivity(), new PIXNET.OnAccessTokenGotListener() {
                            @Override
                            public void onAccessTokenGot(String token, String secret) {}

                            @Override
                            public void onAccessTokenGot(String token, String refreshToken, long expires) {
                                Helper.toast(getActivity(), "token:"+token);
                            }

                            @Override
                            public void onError(String msg) {
                                Helper.toast(getActivity(), msg);
                            }
                        });
                        break;
                    case REFRESH:
                        Helper.log("refresh token");
                        PIXNET.refreshToken(PIXNET.getOauthAccessToken(getActivity()), getActivity(), new PIXNET.OnAccessTokenGotListener() {
                            @Override
                            public void onAccessTokenGot(String token, String secret) {
                            }

                            @Override
                            public void onAccessTokenGot(String token, String refreshToken, long expires) {}

                            @Override
                            public void onError(String msg) {}
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

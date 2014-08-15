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
import net.pixnet.sdk.response.GuestbookList;
import net.pixnet.sdk.utils.Helper;

/**
 * Created by Koi on 2014/8/15.
 */
public class GuestBook extends ItemDetailFragment{
    private static enum Apis {
        getGuestbookList,
        addGuestbook,
        getGuestbook,
        replyGuestbook,
        setGuestbookVisibility,
        markGuestbookSpam,
        markGuestbookHam,
        removeGuestbook
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
                net.pixnet.sdk.utils.GuestBook guestBook = PIXNET.getGuestBook(getActivity(),new DataProxy.DataProxyListener() {
                    @Override
                    public void onError(String msg)  {
                        Helper.log("error:" + msg);
                    }

                    @Override
                    public void onDataResponse(BasicResponse response) {
                        Helper.log(response.message);
                        Helper.log("onDataResponse");
                    }
                });
                guestBook.setDefaultUserName("emmademo");
                guestBook.setDefaultPerPage(2);
                guestBook.setDefaultTrimUser(false);
                switch (Apis.values()[position]){
                    case getGuestbookList:
                        guestBook.setListener(new DataProxy.DataProxyListener() {
                            @Override
                            public void onError(String msg) {

                            }

                            @Override
                            public void onDataResponse(BasicResponse response) {
                                GuestbookList guestbookList = (GuestbookList)response;
                                for(int i =0;i<guestbookList.total;i++){
                                    Helper.log(guestbookList.articles.get(i).id);
                                }
                            }
                        });
                        guestBook.getGuestbookList("kkkoooiii2",null,null,10);
                        break;
                    case addGuestbook:
                        guestBook.addGuestbook("kkkoooiii2","Hi","Bye");
                        break;
                    case getGuestbook:
                        guestBook.getGuestbook("kkkoooiii2","44152667");
                        break;
                    case replyGuestbook:
                        guestBook.replyGuestbook("44152667","HiHi");
                        break;
                    case setGuestbookVisibility:
                        guestBook.setGuestbookVisibility("44152667",false);
                        break;
                    case markGuestbookSpam:
                        break;
                    case markGuestbookHam:
                        break;
                    case removeGuestbook:
                        guestBook.removeGuestbook("44152667");
                        break;
                    default:
                }
            }
        });
    }
}

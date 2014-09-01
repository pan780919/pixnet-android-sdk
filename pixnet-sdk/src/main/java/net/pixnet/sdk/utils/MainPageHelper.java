package net.pixnet.sdk.utils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.SetList;

public class MainPageHelper extends DataProxy {

    private static final String URL_BEST_SELECTED_ALBUM = "https://emma.pixnet.cc/mainpage/album/best_selected/";

    /**
     * 列出精選相簿
     */
    public void getBestSelectedAlbum(){
        performAPIRequest(false, URL_BEST_SELECTED_ALBUM, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res=new BasicResponse();
                if(res.error!=0){
                    listener.onError(res.message);
                    return;
                }
                if(listener.onDataResponse(res))
                    return;
                if(listener instanceof MainPageHelperListener)
                    ((MainPageHelperListener)listener).onGetBestSelectedAlbum(new SetList(response));
            }
        });
    }
}

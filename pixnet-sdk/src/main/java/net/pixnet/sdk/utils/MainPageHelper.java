package net.pixnet.sdk.utils;

import net.pixnet.sdk.proxy.*;
import net.pixnet.sdk.proxy.Error;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.SetList;

import org.json.JSONException;

public class MainPageHelper extends DataProxy {

    private static final String URL_BEST_SELECTED_ALBUM = "https://emma.pixnet.cc/mainpage/album/best_selected/";

    /**
     * 列出精選相簿
     */
    public void getBestSelectedAlbum(){
        performAPIRequest(false, URL_BEST_SELECTED_ALBUM, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                SetList parsedResponse;
                try {
                    parsedResponse=new SetList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((MainPageHelperListener)listener).onGetBestSelectedAlbum(parsedResponse);
            }
        });
    }

    @Override
    protected boolean handleBasicResponse(String response) {
        if(super.handleBasicResponse(response))
            return true;
        if(listener instanceof MainPageHelperListener)
            return false;
        return true;
    }
}

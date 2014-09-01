package net.pixnet.sdk.utils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.SetList;

public class MainPageHelperListener implements DataProxy.DataProxyListener {
    @Override
    public void onError(String msg) {}

    @Override
    public boolean onDataResponse(BasicResponse response) {
        return false;
    }

    public void onGetBestSelectedAlbum(SetList response){}
}

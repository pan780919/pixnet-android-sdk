package net.pixnet.sdk.utils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.Element;

public class AlbumHelperListener implements DataProxy.DataProxyListener{

    @Override
    public void onError(String msg) {}

    @Override
    public boolean onDataResponse(BasicResponse response) {
        return false;
    }

    public void onAddFaceByRecommend(Element response){}
    public void onAddFaceByElement(Element response){}
    public void onUpdateFace(Element response){}
}

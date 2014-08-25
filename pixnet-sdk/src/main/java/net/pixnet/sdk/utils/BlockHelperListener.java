package net.pixnet.sdk.utils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.BlocksList;

/**
 * Created by Koi on 2014/8/22.
 */
public class BlockHelperListener implements DataProxy.DataProxyListener {

    @Override
    public void onError(String msg) {}

    @Override
    public boolean onDataResponse(BasicResponse response) {
        return false;
    }
    public void onGetBlockList(BlocksList res){}
    public void onAddBlockResponse(BasicResponse res){}
    public void onRemoveBlockResponse(BasicResponse res){}
}

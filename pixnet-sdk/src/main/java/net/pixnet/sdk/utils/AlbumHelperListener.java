package net.pixnet.sdk.utils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.AlbumMainPage;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.CommentList;
import net.pixnet.sdk.response.Element;
import net.pixnet.sdk.response.ElementList;
import net.pixnet.sdk.response.Folder;
import net.pixnet.sdk.response.FolderList;
import net.pixnet.sdk.response.Set;
import net.pixnet.sdk.response.SetAndFolderList;
import net.pixnet.sdk.response.SetList;

public class AlbumHelperListener implements DataProxy.DataProxyListener{

    @Override
    public void onError(String msg) {}

    @Override
    public boolean onDataResponse(BasicResponse response) {
        return false;
    }

    public void onGetMain(AlbumMainPage response){}
    public void onGetSetAndFolderList(SetAndFolderList response){}
    public void onGetFolder(Folder response){}
    public void onGetFolderList(FolderList response){}
    public void onGetSet(Set response){}
    public void onGetSetList(SetList response){}
    public void onGetSetListByNear(SetList response){}
    public void onGetElement(Element response){}
    public void onGetElementListBySet(ElementList response){}
    public void onGetElementListByNear(ElementList response){}
    public void onGetCommentListBySet(CommentList response){}
    public void onGetCommentListByElement(CommentList response){}
    public void onAddFolder(Folder response){}
    public void onAddSet(Set response){}
    public void onAddFaceByRecommend(Element response){}
    public void onAddFaceByElement(Element response){}
    public void onUpdateFolder(Folder response){}
    public void onUpdateSet(Set response){}
    public void onUpdateFace(Element response){}
    public void onDeleteFolder(BasicResponse response){}
    public void onDeleteSet(BasicResponse response){}
    public void onDeleteElement(BasicResponse response){}
    public void onDeleteCommentFromSet(BasicResponse response){}
    public void onDeleteCommentFromElement(BasicResponse response){}
    public void onDeleteFace(BasicResponse response){}
    public void onSortSetAndFolders(BasicResponse response){}
    public void onSortSets(BasicResponse response){}
    public void onSortElementList(BasicResponse response){}
    public void onGetConfig(BasicResponse response){}
}

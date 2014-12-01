package net.pixnet.sdk.utils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.AccountInfo;
import net.pixnet.sdk.response.AlbumMainPage;
import net.pixnet.sdk.response.Analytics;
import net.pixnet.sdk.response.Article;
import net.pixnet.sdk.response.ArticleList;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.BlocksList;
import net.pixnet.sdk.response.BlogInfo;
import net.pixnet.sdk.response.CategoryList;
import net.pixnet.sdk.response.CellphoneVerification;
import net.pixnet.sdk.response.Comment;
import net.pixnet.sdk.response.CommentList;
import net.pixnet.sdk.response.Element;
import net.pixnet.sdk.response.ElementList;
import net.pixnet.sdk.response.Folder;
import net.pixnet.sdk.response.FolderList;
import net.pixnet.sdk.response.FriendshipList;
import net.pixnet.sdk.response.GroupList;
import net.pixnet.sdk.response.Guestbook;
import net.pixnet.sdk.response.GuestbookList;
import net.pixnet.sdk.response.MIB;
import net.pixnet.sdk.response.NewsList;
import net.pixnet.sdk.response.NotificationList;
import net.pixnet.sdk.response.PeriodArticleList;
import net.pixnet.sdk.response.Position;
import net.pixnet.sdk.response.PositionList;
import net.pixnet.sdk.response.Set;
import net.pixnet.sdk.response.SetAndFolderList;
import net.pixnet.sdk.response.SetList;
import net.pixnet.sdk.response.SiteCategoryList;
import net.pixnet.sdk.response.SubscriptionGroupList;
import net.pixnet.sdk.response.SubscriptionList;
import net.pixnet.sdk.response.Tags;
import net.pixnet.sdk.response.User;

public class PixnetApiResponseListener implements DataProxy.DataProxyListener {
    @Override
    public void onError(String msg){}
    @Override
    public boolean onDataResponse(BasicResponse response) {
        return false;
    }

    // mainpage
    public void onGetBestSelectedAlbum(SetList response){}

    // account
    public void onCellphoneVerification(CellphoneVerification response){}
    public void onGetAccountInfo(AccountInfo response){}
    public void onUpdateAccountInfo(BasicResponse response){}
    public void onGetMIBInfo(MIB response){}
    public void onUpdateMIBInfo(MIB response){}
    public void onGetMIBPositionListInfo(PositionList response){}
    public void onGetMIBPositionInfo(Position response){}
    public void onUpdateMIBPositionInfo(MIB response){}
    public void onPayMIB(BasicResponse response){}
    public void onGetAnalyticData(Analytics response){}
    public void onUpdatePassword(BasicResponse response){}
    public void onGetNotifications(NotificationList response){}
    public void markNotificationAsRead(BasicResponse response){}
    public void onGetUserInfo(User response){}

    // album
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
    public void onAddElement(BasicResponse response){}
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

    // blog
    public void onGetBlogCategorieList(CategoryList res){}
    public void onGetCategorieList(SiteCategoryList res){}
    public void onAddCategory(BasicResponse res){}
    public void onUpdateCategory(BasicResponse res){}
    public void onDeleteCategory(BasicResponse res){}
    public void onSortCategorieList(BasicResponse res){}
    public void onGetAllArticleList(ArticleList res){}
    public void onGetArticle(Article res){}
    public void onGetRelatedArticleList(ArticleList res){}
    public void onAddArticle(BasicResponse res){}
    public void onUpdateArticle(BasicResponse res){}
    public void onDeleteArticle(BasicResponse res){}
    public void onGetArticleListByLatest(ArticleList res){}
    public void onGetArticleListByHot(ArticleList res){}
    public void getArticleListByHotWithin(PeriodArticleList res){}
    public void onGetCommentList(CommentList res){}
    public void onAddComment(BasicResponse res){}
    public void onGetComment(Comment res){}
    public void onGetCommentListByLatest(CommentList res){}
    public void onGetBlogInfo(BlogInfo res){}
    public void onSetBlogInfo(BasicResponse res){}
    public void onGetTags(Tags res){}
    public void onMarkComment(CommentList res){}
    public void onSetCommentVisibility(CommentList res){}
    public void onDeleteComment(BasicResponse res){}
    public void onReplyComment(CommentList res){}
    public void onSearchArticleList(ArticleList res){}

    // friend
    public void onGetSubscribedFriendship(SubscriptionList res){}
    public void onGetFriendshipList(FriendshipList res){}
    public void onAddFriendship(BasicResponse res){}
    public void onAddFriendshipToGroup(BasicResponse res){}
    public void onRemoveFriendshipFromGroup(BasicResponse res){}
    public void onRemoveFriendship(BasicResponse res){}
    public void onAddSubscription(BasicResponse res){}
    public void onRemoveSubscription(BasicResponse res){}
    public void onJoinSubscriptionGroup(BasicResponse res){}
    public void onLeaveSubscriptionGroup(BasicResponse res){}
    public void onSortSubscriptionGroupList(BasicResponse res){}
    public void onAddSubscriptionGroup(BasicResponse res){}
    public void onUpdateSubscriptionGroup(BasicResponse res){}
    public void onRemoveSubscriptionGroup(BasicResponse res){}
    public void onGetGroupList(GroupList res){}
    public void onAddGroup(BasicResponse res){}
    public void onUpdateGroup(BasicResponse res){}
    public void onRemoveGroup(BasicResponse res){}
    public void onGetFriendNews(NewsList res){}
    public void onGetSubscriptionGroupList(SubscriptionGroupList res){}

    // block
    public void onGetBlockList(BlocksList res){}
    public void onAddBlock(BasicResponse res){}
    public void onDeleteBlock(BasicResponse res){}

    // guestbook
    public void onGetGuestbookList(GuestbookList res){}
    public void onGetGuestbook(Guestbook res){}
    public void onAddGuestbook(BasicResponse response){}
    public void onReplyGuestbook(BasicResponse response){}
    public void onSetGuestbookVisibility(BasicResponse response){}
    public void onMarkGuestbookToSpam(BasicResponse response){}
    public void onMarkGuestbookToHam(BasicResponse response){}
    public void onDeleteGuestbook(BasicResponse response){}
}

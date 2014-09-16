package net.pixnet.sdk.utils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.Article;
import net.pixnet.sdk.response.ArticleList;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.BlogInfo;
import net.pixnet.sdk.response.CategoryList;
import net.pixnet.sdk.response.Comment;
import net.pixnet.sdk.response.CommentList;
import net.pixnet.sdk.response.SiteCategoryList;
import net.pixnet.sdk.response.Tags;

/**
 * Created by Koi on 2014/8/22.
 */
public class BlogHelperListener implements DataProxy.DataProxyListener {

    @Override
    public void onError(String msg) {

    }

    @Override
    public boolean onDataResponse(BasicResponse response) {
        return false;
    }
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
}

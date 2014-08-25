package net.pixnet.sdk.utils;


import android.text.TextUtils;

import net.pixnet.sdk.proxy.*;
import net.pixnet.sdk.proxy.Error;
import net.pixnet.sdk.response.Article;
import net.pixnet.sdk.response.ArticleList;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.BlogInfo;
import net.pixnet.sdk.response.CategoryList;
import net.pixnet.sdk.response.Comment;
import net.pixnet.sdk.response.CommentList;
import net.pixnet.sdk.response.Site_CategoryList;
import net.pixnet.sdk.response.Tags;
import net.pixnet.sdk.utils.Request.Method;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class BlogHelper extends DataProxy {
    private static final String URL_CATEGORY = "https://emma.pixnet.cc/blog/categories";
    private static final String URL_SITE_CATEGORY = "https://emma.pixnet.cc/blog/site_categories";
    private static final String URL_ARTICLE = "https://emma.pixnet.cc/blog/articles";
    private static final String URL_COMMENT = "https://emma.pixnet.cc/blog/comments";
    private static final String URL_BLOG = "https://emma.pixnet.cc/blog";
    private static final String URL_TAGS = "https://emma.pixnet.cc/blog/suggested_tags";
    /**
     * 預設使用者名稱
     */
    private String defaultUserName = "emmademo";

    public String getDefaultUserName() {
        return defaultUserName;
    }

    public void setDefaultUserName(String defaultUserName) {
        this.defaultUserName = defaultUserName;
    }

    /**
     * 預設每頁幾筆資料
     */
    private int defaultPerPage = 20;

    public int getDefaultPerPage() {
        return defaultPerPage;
    }

    public void setDefaultPerPage(int defaultPerPage) {
        this.defaultPerPage = defaultPerPage;
    }

    /**
     * 預設是否每篇文章都要回傳作者資訊, 如果設定為 true, 則就不回傳
     */
    private boolean defaultTrimUser = false;

    public boolean isDefaultTrimUser() {
        return defaultTrimUser;
    }

    public void setDefaultTrimUser(boolean defaultTrimUser) {
        this.defaultTrimUser = defaultTrimUser;
    }

    public void getBlogCategorieList() {
        getBlogCategorieList(defaultUserName);
    }

    public void getBlogCategorieList(String user) {
        getBlogCategorieList(user, null);
    }

    public void getBlogCategorieList(String user, String blog_password) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(blog_password))
            params.add(new BasicNameValuePair("blog_password", blog_password));
        performAPIRequest(false, URL_CATEGORY, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onGetBlogCategorieList(new CategoryList(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void getCategorieList() {
        getCategorieList(null, null);
    }

    public void getCategorieList(String include_groups, String include_thumbs) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(include_groups)) {
            params.add(new BasicNameValuePair("include_groups", include_groups));
        }
        if (!TextUtils.isEmpty(include_thumbs)) {
            params.add(new BasicNameValuePair("include_thumbs", include_thumbs));
        }
        performAPIRequest(false, URL_SITE_CATEGORY, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onGetCategorieList(new Site_CategoryList(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void addCategory(String name) {
        addCategory(name, null, null, null, null, null);
    }

    public void addCategory(String name, String type, String description, String show_index, String site_category_id, String site_category_done) {
        if (name == null || TextUtils.isEmpty(name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":name");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        if (!TextUtils.isEmpty(type)) {
            params.add(new BasicNameValuePair("type", type));
        }
        if (!TextUtils.isEmpty(description)) {
            params.add(new BasicNameValuePair("description", description));
        }
        if (!TextUtils.isEmpty(show_index)) {
            params.add(new BasicNameValuePair("show_index", show_index));
        }
        if (!TextUtils.isEmpty(site_category_id)) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        if (!TextUtils.isEmpty(site_category_done)) {
            params.add(new BasicNameValuePair("site_category_done", site_category_done));
        }
        performAPIRequest(true, URL_CATEGORY, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onAddCategory(res);
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void updateCategory(String id, String name, String type, String description, String show_index, String site_category_id, String site_category_done) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(name)) {
            params.add(new BasicNameValuePair("name", name));
        }
        if (!TextUtils.isEmpty(type)) {
            params.add(new BasicNameValuePair("type", type));
        }
        if (!TextUtils.isEmpty(description)) {
            params.add(new BasicNameValuePair("description", description));
        }
        if (!TextUtils.isEmpty(show_index)) {
            params.add(new BasicNameValuePair("show_index", show_index));
        }
        if (!TextUtils.isEmpty(site_category_id)) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        if (!TextUtils.isEmpty(site_category_done)) {
            params.add(new BasicNameValuePair("site_category_done", site_category_done));
        }
        performAPIRequest(true, URL_CATEGORY + "/" + id, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onUpdateCategory(res);
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void deleteCategory(String id) {
        deleteCategory(id, null);
    }

    public void deleteCategory(String id, String type) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(type)) {
            params.add(new BasicNameValuePair("type", type));
        }
        performAPIRequest(true, URL_CATEGORY + "/" + id, Request.Method.DELETE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onDeleteCategory(res);
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void sortCategorieList(String ids) {
        if (ids == null || TextUtils.isEmpty(ids)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":ids");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ids", ids));
        performAPIRequest(true, URL_CATEGORY + "/position", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onSortCategorieList(res);
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void getAllArticleList() {
        getAllArticleList(1);
    }

    public void getAllArticleList(int page) {
        getAllArticleList(page, defaultPerPage);
    }

    public void getAllArticleList(int page, int per_page) {
        getAllArticleList(defaultUserName, null, page, per_page, null, null, null, defaultTrimUser);
    }

    public void getAllArticleList(String user, String blog_password, int page, int per_page, String category_id, String status, String is_top, boolean trim_user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(blog_password)) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        params.add(new BasicNameValuePair("page", String.valueOf(page)));
        params.add(new BasicNameValuePair("per_page", String.valueOf(per_page)));

        if (!TextUtils.isEmpty(category_id)) {
            params.add(new BasicNameValuePair("category_id", category_id));
        }
        if (!TextUtils.isEmpty(status)) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (!TextUtils.isEmpty(is_top)) {
            params.add(new BasicNameValuePair("is_top", is_top));
        }
        params.add(new BasicNameValuePair("trim_user", trim_user ? "1" : "0"));
        performAPIRequest(false, URL_ARTICLE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onGetAllArticleList(new ArticleList(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void getArticle(String id) {
        getArticle(id, defaultUserName);
    }

    public void getArticle(String id, String user) {
        getArticle(id, user, null, null);
    }

    public void getArticle(String id, String user, String blog_password, String article_password) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(blog_password)) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (!TextUtils.isEmpty(article_password)) {
            params.add(new BasicNameValuePair("article_password", article_password));
        }
        performAPIRequest(false, URL_ARTICLE + "/" + id, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onGetArticle(new Article(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void getRelatedArticleList(String id) {
        getRelatedArticleList(id, defaultUserName);
    }

    public void getRelatedArticleList(String id, String user) {
        getRelatedArticleList(id, user, null, null);
    }

    public void getRelatedArticleList(String id, String user, String with_body, String limit) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(with_body)) {
            params.add(new BasicNameValuePair("with_body", with_body));
        }
        if (!TextUtils.isEmpty(limit)) {
            params.add(new BasicNameValuePair("limit", limit));
        }
        performAPIRequest(false, URL_ARTICLE + "/" + id + "/related", new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onGetRelatedArticleList(new ArticleList(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void addArticle(String title, String body) {
        addArticle(title, body, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public void addArticle(String title, String body, String status, String public_at, String category_id, String site_category_id, String use_nl2br, String comment_perm, String comment_hidden, String tags, String thumb, String trackback, String password, String password_hint, String friend_group_ids, String notify_twitter, String notify_facebook) {
        if (title == null || TextUtils.isEmpty(title)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":title");
            return;
        }
        if (body == null || TextUtils.isEmpty(body)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":body");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("body", body));
        if (!TextUtils.isEmpty(status)) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (!TextUtils.isEmpty(public_at)) {
            params.add(new BasicNameValuePair("public_at", public_at));
        }
        if (!TextUtils.isEmpty(category_id)) {
            params.add(new BasicNameValuePair("category_id", category_id));
        }
        if (!TextUtils.isEmpty(site_category_id)) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        if (!TextUtils.isEmpty(use_nl2br)) {
            params.add(new BasicNameValuePair("use_nl2br", use_nl2br));
        }
        if (!TextUtils.isEmpty(comment_perm)) {
            params.add(new BasicNameValuePair("comment_perm", comment_perm));
        }
        if (!TextUtils.isEmpty(comment_hidden)) {
            params.add(new BasicNameValuePair("comment_hidden", comment_hidden));
        }
        if (!TextUtils.isEmpty(tags)) {
            params.add(new BasicNameValuePair("tags", tags));
        }
        if (!TextUtils.isEmpty(thumb)) {
            params.add(new BasicNameValuePair("thumb", thumb));
        }
        if (!TextUtils.isEmpty(trackback)) {
            params.add(new BasicNameValuePair("trackback", trackback));
        }
        if (!TextUtils.isEmpty(password)) {
            params.add(new BasicNameValuePair("password", password));
        }
        if (!TextUtils.isEmpty(password_hint)) {
            params.add(new BasicNameValuePair("password_hint", password_hint));
        }
        if (!TextUtils.isEmpty(friend_group_ids)) {
            params.add(new BasicNameValuePair("friend_group_ids", friend_group_ids));
        }
        if (!TextUtils.isEmpty(notify_twitter)) {
            params.add(new BasicNameValuePair("notify_twitter", notify_twitter));
        }
        if (!TextUtils.isEmpty(notify_facebook)) {
            params.add(new BasicNameValuePair("notify_facebook", notify_facebook));
        }
        performAPIRequest(true, URL_ARTICLE, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onAddArticle(res);
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void updateArticle(String id, String title, String body, String status, String public_at, String category_id, String site_category_id, String use_nl2br, String comment_perm, String comment_hidden, String tags, String thumb, String trackback, String password, String password_hint, String friend_group_ids, String notify_twitter, String notify_facebook) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(title)) {
            params.add(new BasicNameValuePair("title", title));
        }
        if (!TextUtils.isEmpty(body)) {
            params.add(new BasicNameValuePair("body", body));
        }
        if (!TextUtils.isEmpty(status)) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (!TextUtils.isEmpty(public_at)) {
            params.add(new BasicNameValuePair("public_at", public_at));
        }
        if (!TextUtils.isEmpty(category_id)) {
            params.add(new BasicNameValuePair("category_id", category_id));
        }
        if (!TextUtils.isEmpty(site_category_id)) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        if (!TextUtils.isEmpty(use_nl2br)) {
            params.add(new BasicNameValuePair("use_nl2br", use_nl2br));
        }
        if (!TextUtils.isEmpty(comment_perm)) {
            params.add(new BasicNameValuePair("comment_perm", comment_perm));
        }
        if (!TextUtils.isEmpty(comment_hidden)) {
            params.add(new BasicNameValuePair("comment_hidden", comment_hidden));
        }
        if (!TextUtils.isEmpty(tags)) {
            params.add(new BasicNameValuePair("tags", tags));
        }
        if (!TextUtils.isEmpty(thumb)) {
            params.add(new BasicNameValuePair("thumb", thumb));
        }
        if (!TextUtils.isEmpty(trackback)) {
            params.add(new BasicNameValuePair("trackback", trackback));
        }
        if (!TextUtils.isEmpty(password)) {
            params.add(new BasicNameValuePair("password", password));
        }
        if (!TextUtils.isEmpty(password_hint)) {
            params.add(new BasicNameValuePair("password_hint", password_hint));
        }
        if (!TextUtils.isEmpty(friend_group_ids)) {
            params.add(new BasicNameValuePair("friend_group_ids", friend_group_ids));
        }
        if (!TextUtils.isEmpty(notify_twitter)) {
            params.add(new BasicNameValuePair("notify_twitter", notify_twitter));
        }
        if (!TextUtils.isEmpty(notify_facebook)) {
            params.add(new BasicNameValuePair("notify_facebook", notify_facebook));
        }
        performAPIRequest(true, URL_ARTICLE + "/" + id, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onUpdateArticle(res);
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void deleteArticle(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_ARTICLE + "/" + id, Request.Method.DELETE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onDeleteArticle(res);
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void getArticleListByLatest() {
        getArticleListByLatest(defaultUserName, null, null, defaultTrimUser);
    }

    public void getArticleListByLatest(String user, String blog_password, String limit, boolean trim_user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(blog_password)) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (!TextUtils.isEmpty(limit)) {
            params.add(new BasicNameValuePair("limit", limit));
        }
        params.add(new BasicNameValuePair("trim_user", trim_user ? "1" : "0"));
        performAPIRequest(false, URL_ARTICLE + "/latest", new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onGetArticleListByLatest(new ArticleList(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void getArticleListByHot() {
        getArticleListByHot(defaultUserName, null, null, defaultTrimUser);
    }

    public void getArticleListByHot(String user, String blog_password, String limit, boolean trim_user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(blog_password)) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (!TextUtils.isEmpty(limit)) {
            params.add(new BasicNameValuePair("limit", limit));
        }
        params.add(new BasicNameValuePair("trim_user", trim_user ? "1" : "0"));
        performAPIRequest(false, URL_ARTICLE + "/hot", new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onGetArticleListByHot(new ArticleList(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void searchArticleList(String key) {
        searchArticleList(key, 1);
    }

    public void searchArticleList(String key, int page) {
        searchArticleList(key, defaultUserName, null, null, page, defaultPerPage);
    }

    public void searchArticleList(String key, String user, String site, String type, int page, int per_page) {
        if (key == null || TextUtils.isEmpty(key)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":key");
            return;
        }
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key", key));
        params.add(new BasicNameValuePair("user", user));

        if (!TextUtils.isEmpty(site)) {
            params.add(new BasicNameValuePair("site", site));
        }
        if (!TextUtils.isEmpty(type)) {
            params.add(new BasicNameValuePair("type", type));
        }
        params.add(new BasicNameValuePair("page", String.valueOf(page)));
        params.add(new BasicNameValuePair("per_page", String.valueOf(per_page)));

        performAPIRequest(false, URL_ARTICLE + "/search", new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onSearchArticleList(new ArticleList(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    /**
     * @see #getCommentList(String, String, String, String, String, String, int, int)
     */
    public void getCommentList(){
        getCommentList(null);
    }
    /**
     * @see #getCommentList(String, String, String, String, String, String, int, int)
     */
    public void getCommentList(String article_id){
        getCommentList(article_id,1);
    }
    /**
     * @see #getCommentList(String, String, String, String, String, String, int, int)
     */
    public void getCommentList(String article_id,int page){
        getCommentList(defaultUserName,article_id,null,null,null,null,page,defaultPerPage);
    }
    /**
     *
     * @param filter 顯示特別屬性的留言. whisper: 只顯示悄悄話留言; nospam: 只顯示非廣告留言; noreply: 只顯示未回覆留言
     * @param sort 排序條件. date-posted-asc: 依照留言時間由舊到新排序; date-posted-desc: 依照留言時間由新到舊排序
     * @see #getCommentList(String, String, String, String, String, String, int, int)
     */
    public void getCommentList(String filter, String sort, int page) {
        getCommentList(defaultUserName,null,null,null,filter,sort,page,defaultPerPage);
    }
    /**
     * 列出部落格留言
     * @param user 指定要回傳的使用者資訊
     * @param article_id 指定要回傳的留言文章
     * @param blog_password 如果指定使用者的 Blog 被密碼保護，則需要指定這個參數以通過授權
     * @param article_password 如果指定使用者的文章被密碼保護，則需要指定這個參數以通過授權
     * @param filter 顯示特別屬性的留言. whisper: 只顯示悄悄話留言; nospam: 只顯示非廣告留言; noreply: 只顯示未回覆留言
     * @param sort 排序條件. date-posted-asc: 依照留言時間由舊到新排序; date-posted-desc: 依照留言時間由新到舊排序
     * @param page 頁數
     * @param per_page 每頁幾筆
     */
    public void getCommentList(String user, String article_id, String blog_password, String article_password, String filter, String sort, int page, int per_page) {
        if (TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if(!TextUtils.isEmpty(article_id))
            params.add(new BasicNameValuePair("article_id", article_id));
        if (!TextUtils.isEmpty(blog_password)) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (!TextUtils.isEmpty(article_password)) {
            params.add(new BasicNameValuePair("article_password", article_password));
        }
        if (!TextUtils.isEmpty(filter)) {
            params.add(new BasicNameValuePair("filter", filter));
        }
        if (!TextUtils.isEmpty(sort)) {
            params.add(new BasicNameValuePair("sort", sort));
        }
        if(page>0)
            params.add(new BasicNameValuePair("page", String.valueOf(page)));
        if(per_page>0)
            params.add(new BasicNameValuePair("per_page", String.valueOf(per_page)));
        performAPIRequest(false, URL_COMMENT, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onGetCommentList(new CommentList(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    /**
     * @see #addComment(String, String, String, String, String, String, String, String, String, String)
     */
    public void addComment(String article_id,String body){
        addComment(article_id, body, defaultUserName);
    }
    /**
     * @see #addComment(String, String, String, String, String, String, String, String, String, String)
     */
    public void addComment(String article_id,String body,String user){
        addComment(article_id,body,user,null,null,null,null,null,null,null);
    }
    /**
     * 新增部落格留言
     * @param article_id 要留言的文章 id
     * @param body 留言內容
     * @param user 要留言的部落格作者名稱
     * @param author 留言的暱稱, 不填入則預設代入認證使用者的 display_name
     * @param title 留言標題
     * @param url 個人網頁
     * @param is_open 公開留言/悄悄話
     * @param email 電子郵件
     * @param blog_password 如果指定使用者的 Blog 被密碼保護，則需要指定這個參數以通過授權
     * @param article_password 如果指定使用者的文章被密碼保護，則需要指定這個參數以通過授權
     */
    public void addComment(String article_id, String body, String user, String author, String title, String url, String is_open, String email, String blog_password, String article_password) {
        if (article_id == null || TextUtils.isEmpty(article_id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":article_id");
            return;
        }
        if (body == null || TextUtils.isEmpty(body)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":body");
            return;
        }
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("article_id", article_id));
        params.add(new BasicNameValuePair("body", body));
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(author)) {
            params.add(new BasicNameValuePair("author", author));
        }
        if (!TextUtils.isEmpty(title)) {
            params.add(new BasicNameValuePair("title", title));
        }
        if (!TextUtils.isEmpty(url)) {
            params.add(new BasicNameValuePair("url", url));
        }
        if (!TextUtils.isEmpty(is_open)) {
            params.add(new BasicNameValuePair("is_open", is_open));
        }
        if (!TextUtils.isEmpty(email)) {
            params.add(new BasicNameValuePair("email", email));
        }
        if (!TextUtils.isEmpty(blog_password)) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (!TextUtils.isEmpty(article_password)) {
            params.add(new BasicNameValuePair("article_password", article_password));
        }
        performAPIRequest(true,URL_COMMENT, Request.Method.POST,new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onAddComment(res);
                } else listener.onError(res.message);
            }
        },params);
    }

    public void getComment(String id) {
        getComment(id, defaultUserName);
    }

    public void getComment(String id, String user) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        performAPIRequest(false, URL_COMMENT + "/" + id, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onGetComment(new Comment(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    /**
     * 回覆部落格留言，可以重覆使用這個功能來修改回覆內容
     * @param body 回覆的內容
     * @param id 欲回覆的留言 id
     */
    public void replyComment(String body, String... id) {
        if(id==null || id.length<1){
            listener.onError(Error.MISS_PARAMETER + ":id");
            return;
        }
        if (body == null || TextUtils.isEmpty(body)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":body");
            return;
        }
        String ids="";
        for(String str : id){
            if(!TextUtils.isEmpty(str)) {
                if(ids.length()>0)
                    ids+=",";
                ids += str;
            }
        }
        if(ids.length()<1){
            listener.onError(Error.MISS_PARAMETER + ":id");
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("api_version", "2"));
        params.add(new BasicNameValuePair("body", body));
        performAPIRequest(true, URL_COMMENT + "/" + ids + "/reply", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onReplyComment(new CommentList(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    /**
     * 將留言設為悄悄話
     * @param id 欲更動的留言 id
     * @param visible 是否公開? false=悄悄話
     */
    public void setCommentVisibility(boolean visible, String... id) {
        if(id==null || id.length<1){
            listener.onError(Error.MISS_PARAMETER + ":id");
            return;
        }
        String ids="";
        for(String str : id){
            if(!TextUtils.isEmpty(str)) {
                if(ids.length()>0)
                    ids+=",";
                ids += str;
            }
        }
        if(ids.length()<1){
            listener.onError(Error.MISS_PARAMETER + ":id");
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("api_version", "2"));
        String url;
        if (visible) {
            url = URL_COMMENT + "/" + ids + "/open";
        } else {
            url = URL_COMMENT + "/" + ids + "/close";
        }
        performAPIRequest(true, url, Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onSetCommentVisibility(new CommentList(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    /**
     * 刪除部落格留言
     * @param id 欲刪除的留言 id
     */
    public void deleteComment(String... id) {
        if(id==null || id.length<1){
            listener.onError(Error.MISS_PARAMETER + ":id");
            return;
        }
        String ids="";
        for(String str : id){
            if(!TextUtils.isEmpty(str)) {
                if(ids.length()>0)
                    ids+=",";
                ids += str;
            }
        }
        if(ids.length()<1){
            listener.onError(Error.MISS_PARAMETER + ":id");
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("api_version", "2"));
        performAPIRequest(true, URL_COMMENT + "/" + ids, Request.Method.DELETE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onDeleteComment(res);
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void getCommentListByLatest() {
        getCommentListByLatest(defaultUserName);
    }

    public void getCommentListByLatest(String user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        performAPIRequest(false, URL_COMMENT + "/latest", new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onGetCommentListByLatest(new CommentList(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    /**
     * 將留言設為廣告／非廣告留言
     * @param isSpam 是否廣告
     * @param id 欲更動的留言 id
     */
    public void markComment(boolean isSpam, String... id) {
        if(id==null || id.length<1){
            listener.onError(Error.MISS_PARAMETER + ":id");
            return;
        }
        String ids="";
        for(String str : id){
            if(!TextUtils.isEmpty(str)) {
                if(ids.length()>0)
                    ids+=",";
                ids += str;
            }
        }
        if(ids.length()<1){
            listener.onError(Error.MISS_PARAMETER + ":id");
            return;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("api_version", "2"));
        String url=URL_COMMENT+"/"+ids+"/";
        if(isSpam)
            url+="mark_spam";
        else url+="mark_ham";
        performAPIRequest(true, url, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onMarkComment(new CommentList(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void getBlogInfo() {
        getBlogInfo(defaultUserName);
    }

    public void getBlogInfo(String user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));

        performAPIRequest(false, URL_BLOG, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onGetBlogInfo(new BlogInfo(response));
                } else listener.onError(res.message);
            }
        }, params);
    }

    public void setBlogInfo(String name, String description, String keyword, String site_category_id) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(name)) {
            params.add(new BasicNameValuePair("name", name));
        }
        if (!TextUtils.isEmpty(description)) {
            params.add(new BasicNameValuePair("description", description));
        }
        if (!TextUtils.isEmpty(keyword)) {
            params.add(new BasicNameValuePair("keyword", keyword));
        }
        if (!TextUtils.isEmpty(site_category_id)) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        performAPIRequest(true, URL_BLOG, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onSetBlogInfo(res);
                } else listener.onError(res.message);
            }
        }, params);
    }


    public void getTags(){
        getTags(defaultUserName);
    }
    /**
     * 部落格 熱門標籤 及 相關標籤
     * @param userName 使用者名稱
     */
    public void getTags(String userName){
        if (TextUtils.isEmpty(userName)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":userName");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", userName));
        performAPIRequest(false, URL_TAGS, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlogHelperListener)
                        ((BlogHelperListener) listener).onGetTags(new Tags(response));
                } else listener.onError(res.message);
            }
        }, params);
    }
}
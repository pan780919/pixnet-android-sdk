package net.pixnet.sdk.utils;

import android.content.Context;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class Blog {

    private RequestController rc;

    public Blog(Context c) {
        rc = RequestController.getInstance();
        OAuthConnectionTool.OAuthVersion ver=PIXNET.getOAuthVersion(c);
        OAuthConnectionTool tool;
        if(ver== OAuthConnectionTool.OAuthVersion.VER_1){
            tool=OAuthConnectionTool.newOaut1ConnectionTool(c.getString(R.string.consumer_key), c.getString(R.string.consumer_secret));
            tool.setAccessTokenAndSecret(PIXNET.getOauthAccessToken(c), PIXNET.getOauthAccessSecret(c));
        }
        else{
            tool=OAuthConnectionTool.newOauth2ConnectionTool();
            tool.setAccessToken(PIXNET.getOauthAccessToken(c));
        }
        rc.setHttpConnectionTool(tool);
    }

    public void getBlogCategorieList(String user, String format, String blog_password, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (blog_password != null) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/categories");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void getCategorieList(String format, String include_groups, String include_thumbs, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (include_groups != null) {
            params.add(new BasicNameValuePair("include_groups", include_groups));
        }
        if (include_thumbs != null) {
            params.add(new BasicNameValuePair("include_thumbs", include_thumbs));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/site_categories");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void addCategory(String name, String format, String type, String description, String show_index, String site_category_id, String site_category_done, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (type != null) {
            params.add(new BasicNameValuePair("type", type));
        }
        if (description != null) {
            params.add(new BasicNameValuePair("description", description));
        }
        if (show_index != null) {
            params.add(new BasicNameValuePair("show_index", show_index));
        }
        if (site_category_id != null) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        if (site_category_done != null) {
            params.add(new BasicNameValuePair("site_category_done", site_category_done));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/categories");
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void updateCategory(String id, String format, String name, String type, String description, String show_index, String site_category_id, String site_category_done, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (name != null) {
            params.add(new BasicNameValuePair("name", name));
        }
        if (type != null) {
            params.add(new BasicNameValuePair("type", type));
        }
        if (description != null) {
            params.add(new BasicNameValuePair("description", description));
        }
        if (show_index != null) {
            params.add(new BasicNameValuePair("show_index", show_index));
        }
        if (site_category_id != null) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        if (site_category_done != null) {
            params.add(new BasicNameValuePair("site_category_done", site_category_done));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/categories/" + id);
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void removeCategory(String id, String format, String type, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (type != null) {
            params.add(new BasicNameValuePair("type", type));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/categories/" + id);
        request.setMethod(Request.Method.DELETE);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void sortCategorieList(String ids, String format, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ids", ids));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/categories/position");
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void getAllArticleList(String user, String format, String blog_password, String page, String per_page, String category_id, String status, String is_top, String trim_user, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (blog_password != null) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (page != null) {
            params.add(new BasicNameValuePair("page", page));
        }
        if (per_page != null) {
            params.add(new BasicNameValuePair("per_page", per_page));
        }
        if (category_id != null) {
            params.add(new BasicNameValuePair("category_id", category_id));
        }
        if (status != null) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (is_top != null) {
            params.add(new BasicNameValuePair("is_top", is_top));
        }
        if (trim_user != null) {
            params.add(new BasicNameValuePair("trim_user", trim_user));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/articles");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void getArticle(String id, String user, String format, String blog_password, String article_password, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (blog_password != null) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (article_password != null) {
            params.add(new BasicNameValuePair("article_password", article_password));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/articles/" + id);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void getRelatedArticleList(String user, String id, String format, String with_body, String limit, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (with_body != null) {
            params.add(new BasicNameValuePair("with_body", with_body));
        }
        if (limit != null) {
            params.add(new BasicNameValuePair("limit", limit));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/articles/" + id + "/related");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void getCommentListByArticle(String user, String format, String article_id, String blog_password, String article_password, String filter, String sort, String page, String per_page, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (article_id != null) {
            params.add(new BasicNameValuePair("article_id", article_id));
        }
        if (blog_password != null) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (article_password != null) {
            params.add(new BasicNameValuePair("article_password", article_password));
        }
        if (filter != null) {
            params.add(new BasicNameValuePair("filter", filter));
        }
        if (sort != null) {
            params.add(new BasicNameValuePair("sort", sort));
        }
        if (page != null) {
            params.add(new BasicNameValuePair("page", page));
        }
        if (per_page != null) {
            params.add(new BasicNameValuePair("per_page", per_page));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/comments");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void addArticle(String title, String body, String format, String status, String public_at, String category_id, String site_category_id, String use_nl2br, String comment_perm, String comment_hidden, String tags, String thumb, String trackback, String password, String password_hint, String friend_group_ids, String notify_twitter, String notify_facebook, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("body", body));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (status != null) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (public_at != null) {
            params.add(new BasicNameValuePair("public_at", public_at));
        }
        if (category_id != null) {
            params.add(new BasicNameValuePair("category_id", category_id));
        }
        if (site_category_id != null) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        if (use_nl2br != null) {
            params.add(new BasicNameValuePair("use_nl2br", use_nl2br));
        }
        if (comment_perm != null) {
            params.add(new BasicNameValuePair("comment_perm", comment_perm));
        }
        if (comment_hidden != null) {
            params.add(new BasicNameValuePair("comment_hidden", comment_hidden));
        }
        if (tags != null) {
            params.add(new BasicNameValuePair("tags", tags));
        }
        if (thumb != null) {
            params.add(new BasicNameValuePair("thumb", thumb));
        }
        if (trackback != null) {
            params.add(new BasicNameValuePair("trackback", trackback));
        }
        if (password != null) {
            params.add(new BasicNameValuePair("password", password));
        }
        if (password_hint != null) {
            params.add(new BasicNameValuePair("password_hint", password_hint));
        }
        if (friend_group_ids != null) {
            params.add(new BasicNameValuePair("friend_group_ids", friend_group_ids));
        }
        if (notify_twitter != null) {
            params.add(new BasicNameValuePair("notify_twitter", notify_twitter));
        }
        if (notify_facebook != null) {
            params.add(new BasicNameValuePair("notify_facebook", notify_facebook));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/articles");
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void updateArticle(String id, String format, String title, String body, String status, String public_at, String category_id, String site_category_id, String use_nl2br, String comment_perm, String comment_hidden, String tags, String thumb, String trackback, String password, String password_hint, String friend_group_ids, String notify_twitter, String notify_facebook, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (title != null) {
            params.add(new BasicNameValuePair("title", title));
        }
        if (body != null) {
            params.add(new BasicNameValuePair("body", body));
        }
        if (status != null) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (public_at != null) {
            params.add(new BasicNameValuePair("public_at", public_at));
        }
        if (category_id != null) {
            params.add(new BasicNameValuePair("category_id", category_id));
        }
        if (site_category_id != null) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }
        if (use_nl2br != null) {
            params.add(new BasicNameValuePair("use_nl2br", use_nl2br));
        }
        if (comment_perm != null) {
            params.add(new BasicNameValuePair("comment_perm", comment_perm));
        }
        if (comment_hidden != null) {
            params.add(new BasicNameValuePair("comment_hidden", comment_hidden));
        }
        if (tags != null) {
            params.add(new BasicNameValuePair("tags", tags));
        }
        if (thumb != null) {
            params.add(new BasicNameValuePair("thumb", thumb));
        }
        if (trackback != null) {
            params.add(new BasicNameValuePair("trackback", trackback));
        }
        if (password != null) {
            params.add(new BasicNameValuePair("password", password));
        }
        if (password_hint != null) {
            params.add(new BasicNameValuePair("password_hint", password_hint));
        }
        if (friend_group_ids != null) {
            params.add(new BasicNameValuePair("friend_group_ids", friend_group_ids));
        }
        if (notify_twitter != null) {
            params.add(new BasicNameValuePair("notify_twitter", notify_twitter));
        }
        if (notify_facebook != null) {
            params.add(new BasicNameValuePair("notify_facebook", notify_facebook));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/articles/" + id);
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void removeArticle(String id, String format, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/articles/" + id);
        request.setMethod(Request.Method.DELETE);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void getArticleListByLatest(String user, String format, String blog_password, String limit, String trim_user, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (blog_password != null) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (limit != null) {
            params.add(new BasicNameValuePair("limit", limit));
        }
        if (trim_user != null) {
            params.add(new BasicNameValuePair("trim_user", trim_user));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/articles/latest");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void getArticleListByHot(String user, String format, String blog_password, String limit, String trim_user, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (blog_password != null) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (limit != null) {
            params.add(new BasicNameValuePair("limit", limit));
        }
        if (trim_user != null) {
            params.add(new BasicNameValuePair("trim_user", trim_user));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/articles/hot");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void searchArticleList(String key, String user, String format, String site, String type, String page, String per_page, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key", key));
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (site != null) {
            params.add(new BasicNameValuePair("site", site));
        }
        if (type != null) {
            params.add(new BasicNameValuePair("type", type));
        }
        if (page != null) {
            params.add(new BasicNameValuePair("page", page));
        }
        if (per_page != null) {
            params.add(new BasicNameValuePair("per_page", per_page));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/articles/search");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void getCommentList(String user, String format, String article_id, String blog_password, String article_password, String filter, String sort, String page, String per_page, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (article_id != null) {
            params.add(new BasicNameValuePair("article_id", article_id));
        }
        if (blog_password != null) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (article_password != null) {
            params.add(new BasicNameValuePair("article_password", article_password));
        }
        if (filter != null) {
            params.add(new BasicNameValuePair("filter", filter));
        }
        if (sort != null) {
            params.add(new BasicNameValuePair("sort", sort));
        }
        if (page != null) {
            params.add(new BasicNameValuePair("page", page));
        }
        if (per_page != null) {
            params.add(new BasicNameValuePair("per_page", per_page));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/comments");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void addComment(String article_id, String body, String user, String format, String author, String title, String url, String is_open, String email, String blog_password, String article_password, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("article_id", article_id));
        params.add(new BasicNameValuePair("body", body));
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (author != null) {
            params.add(new BasicNameValuePair("author", author));
        }
        if (title != null) {
            params.add(new BasicNameValuePair("title", title));
        }
        if (url != null) {
            params.add(new BasicNameValuePair("url", url));
        }
        if (is_open != null) {
            params.add(new BasicNameValuePair("is_open", is_open));
        }
        if (email != null) {
            params.add(new BasicNameValuePair("email", email));
        }
        if (blog_password != null) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (article_password != null) {
            params.add(new BasicNameValuePair("article_password", article_password));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/comments");
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void getComment(String id, String user, String format, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/comments/" + id);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void replyComment(String id, String body, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("body", body));

        Request request = new Request("https://emma.pixnet.cc/blog/comments/" + id + "/reply");
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void setCommentVisibility(String id, boolean visible, String format, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        String url;
        if (visible) {
            url = "https://emma.pixnet.cc/blog/comments/" + id + "/open";
        } else {
            url = "https://emma.pixnet.cc/blog/comments/" + id + "/close";
        }

        Request request = new Request(url);
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void removeComment(String id, String format, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/comments/" + id);
        request.setMethod(Request.Method.DELETE);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void getCommentListByLatest(String user, String format, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/comments/latest");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void markCommentIsSpam(String id, String format, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/comments/" + id + "/mark_spam");
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void markCommentIsHam(String id, String format, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }

        Request request = new Request("https://emma.pixnet.cc/blog/comments/" + id + "/mark_ham");
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void getBlogInfo(String user, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));

        Request request = new Request("https://emma.pixnet.cc/blog");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void setBlogInfo(String name, String description, String keyword, String site_category_id, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (name != null) {
            params.add(new BasicNameValuePair("name", name));
        }
        if (description != null) {
            params.add(new BasicNameValuePair("description", description));
        }
        if (keyword != null) {
            params.add(new BasicNameValuePair("keyword", keyword));
        }
        if (site_category_id != null) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }

        Request request = new Request("https://emma.pixnet.cc/blog");
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }
}
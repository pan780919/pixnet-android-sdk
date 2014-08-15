package net.pixnet.sdk.test;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.ArticleList;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.utils.Helper;

/**
 * Created by Koi on 2014/8/15.
 */
public class Blog extends ItemDetailFragment {
    private static enum Apis {
        getBlogInfo,
        setBlogInfo,

        getBlogCategorieList,
        addCategory,
        updateCategory,
        removeCategory,
        sortCategorieList,

        getAllArticleList,
        getArticle,
        getRelatedArticleList,
        getCommentListByArticle,
        addArticle,
        updateArticle,
        removeArticle,
        getArticleListByLatest,
        getArticleListByHot,
        searchArticleList,

        getCommentList,
        addComment,
        getComment,
        replyComment,
        setCommentVisibility,
        markCommentIsSpam,
        markCommentIsHam,
        removeComment,
        getCommentListByLatest,

        getCategorieList
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Apis[] data = Apis.values();

        setListAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return data.length;
            }

            @Override
            public String getItem(int position) {
                return data[position].name();
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v;

                if (convertView == null) {
                    v = View.inflate(getActivity(), android.R.layout.simple_list_item_1, null);
                } else {
                    v = convertView;
                }

                TextView t = (TextView) v.findViewById(android.R.id.text1);
                t.setText(getItem(position));

                return v;
            }
        });
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                net.pixnet.sdk.utils.Blog blog = PIXNET.getBlog(getActivity(), new DataProxy.DataProxyListener() {
                    @Override
                    public void onError(String msg) {
                        Helper.log("error:" + msg);
                    }

                    @Override
                    public void onDataResponse(BasicResponse response) {
                        Helper.log(response.message);
                        Helper.log("onDataResponse");
                    }
                });
                blog.setDefaultUserName("emmademo");
                blog.setDefaultPerPage(2);
                blog.setDefaultTrimUser(false);
                switch (Apis.values()[position]) {
                    case getBlogInfo:
                        blog.getBlogInfo();
                        break;
                    case setBlogInfo:
                        blog.setBlogInfo("Test Name", "Test description", null, null);
                        break;
                    case getBlogCategorieList:
                         blog.getBlogCategorieList();
                        break;
                    case addCategory:
                        blog.addCategory("Test");
                        break;
                    case updateCategory:
                        break;
                    case removeCategory:
                        break;
                    case sortCategorieList:
                        break;
                    case getAllArticleList:
                        blog.getAllArticleList();
                        break;
                    case getArticle:
                        blog.getArticle("61695293");
                        break;
                    case getRelatedArticleList:
                        blog.getRelatedArticleList("61695293");
                        break;
                    case getCommentListByArticle:
                        blog.getCommentListByArticle("61695293");
                        break;
                    case addArticle:
                        blog.addArticle("TestTitle","TestBody");
                        break;
                    case updateArticle:
                        blog.updateArticle("174157501","TestUpdateTitle","Body",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
                        break;
                    case removeArticle:
                        blog.removeArticle("174156916");
                        break;
                    case getArticleListByLatest:
                        break;
                    case getArticleListByHot:
                        break;
                    case searchArticleList:
                        break;
                    case getCommentList:
                        break;
                    case addComment:
                        break;
                    case replyComment:
                        break;
                    case setCommentVisibility:
                        break;
                    case markCommentIsSpam:
                        break;
                    case markCommentIsHam:
                        break;
                    case removeComment:
                        break;
                    case getCommentListByLatest:
                        break;
                    case getCategorieList:
                        blog.getCategorieList();
                        break;
                    default:
                }
            }
        });
    }
}

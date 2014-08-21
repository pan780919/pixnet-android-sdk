package net.pixnet.sdk.test;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.utils.BlogHelper;
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
        markComment,
        removeComment,
        getCommentListByLatest,

        getCategorieList,
        getTags
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
                BlogHelper blogHelper = PIXNET.getBlogHelper(getActivity(), "emmademo", new DataProxy.DataProxyListener() {
                    @Override
                    public void onError(String msg) {
                        Helper.log("error:" + msg);
                    }

                    @Override
                    public void onDataResponse(BasicResponse response) {
                        Helper.log(response.getRawData());
                        Helper.log(response.message);
                        Helper.log("onDataResponse");
                    }
                });
                blogHelper.setDefaultUserName("ben68");
                blogHelper.setDefaultPerPage(8);
                blogHelper.setDefaultTrimUser(false);
                switch (Apis.values()[position]) {
                    case getBlogInfo:
                        blogHelper.getBlogInfo();
                        break;
                    case setBlogInfo:
                        blogHelper.setBlogInfo("Test Name", "Test description", null, null);
                        break;
                    case getBlogCategorieList:
                         blogHelper.getBlogCategorieList();
                        break;
                    case addCategory:
                        blogHelper.addCategory("Test");
                        break;
                    case updateCategory:
                        break;
                    case removeCategory:
                        break;
                    case sortCategorieList:
                        break;
                    case getAllArticleList:
                        blogHelper.getAllArticleList();
                        break;
                    case getArticle:
                        blogHelper.getArticle("61695293");
                        break;
                    case getRelatedArticleList:
                        blogHelper.getRelatedArticleList("61695293");
                        break;
                    case addArticle:
                        blogHelper.addArticle("TestTitle","TestBody");
                        break;
                    case updateArticle:
                        blogHelper.updateArticle("174157501","TestUpdateTitle","Body",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
                        break;
                    case removeArticle:
                        blogHelper.deleteArticle("174156916");
                        break;
                    case getArticleListByLatest:
                        break;
                    case getArticleListByHot:
                        break;
                    case searchArticleList:
                        break;
                    case getCommentList:
                        blogHelper.getCommentList();
                        break;
                    case addComment:
                        blogHelper.addComment("185509473", "test");
                        break;
                    case getComment:
                        break;
                    case replyComment:
                        blogHelper.replyComment("multireply test", "33785508", "33784806", "33783552");
                        break;
                    case setCommentVisibility:
                        blogHelper.setCommentVisibility(false, "33785508", "33784806", "33783552");
                        break;
                    case markComment:
                        blogHelper.markComment(false, "33785508", "33784806", "33783552");
                        break;
                    case removeComment:
                        //blogHelper.deleteComment("33802053", "33802059", "33802065");
                        break;
                    case getCommentListByLatest:
                        break;
                    case getCategorieList:
                        blogHelper.getCategorieList();
                        break;
                    case getTags:
                        blogHelper.getTags();
                        break;
                    default:
                }
            }
        });
    }
}

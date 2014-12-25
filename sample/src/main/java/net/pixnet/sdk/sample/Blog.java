package net.pixnet.sdk.sample;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.Article;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.utils.Helper;
import net.pixnet.sdk.utils.PixnetApiHelper;

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
        getArticleListByHotWithin,
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
                PixnetApiHelper apiHelper = PIXNET.getApiHelper(getActivity(), new DataProxy.DataProxyListener() {
                    @Override
                    public void onError(String msg) {
                        Helper.log("error:" + msg);
                    }

                    @Override
                    public boolean onDataResponse(BasicResponse response) {
                        Helper.log(response.getRawData());
                        Helper.log(response.message);
                        Helper.log("onDataResponse");
                        return true;
                    }
                });
                apiHelper.setDefaultUserName("emmademo");
                apiHelper.setDefaultPerPage(8);
                apiHelper.setDefaultTrimUser(false);
                switch (Apis.values()[position]) {
                    case getBlogInfo:
                        apiHelper.getBlogInfo();
                        break;
                    case setBlogInfo:
                        apiHelper.setBlogInfo("Test Name", "Test description", null, null);
                        break;
                    case getBlogCategorieList:
                         apiHelper.getBlogCategorieList();
                        break;
                    case addCategory:
                        apiHelper.addCategory("Test");
                        break;
                    case updateCategory:
                        break;
                    case removeCategory:
                        break;
                    case sortCategorieList:
                        break;
                    case getAllArticleList:
                        apiHelper.getAllArticleList();
                        break;
                    case getArticle:
                        apiHelper.setListener(new DataProxy.DataProxyListener() {
                            @Override
                            public void onError(String msg) {

                            }

                            @Override
                            public boolean onDataResponse(BasicResponse response) {
                                Article article = (Article) response;
                                Helper.log(response.getRawData());
                                Bundle bundle = new Bundle();
                                if (article.body != null) {
                                    bundle.putString("body", article.body);
                                    bundle.putString("title", article.title);
                                    bundle.putString("user", article.user.name);
                                }
                                ShowArticleFragment newFragment = new ShowArticleFragment();
                                newFragment.setArguments(bundle);
                                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.item_detail_container, newFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                //bundle.putInt("error",article.error);
                                //bundle.putString("message",article.message);
                                //Intent intent = new Intent(Blog.this.getActivity(),Show_Article.class);
                                //intent.putExtra("bundle",bundle);
                                //startActivityForResult(intent, 0);
                                return true;
                            }
                        });
                        apiHelper.getArticle("165129609");

                        break;
                    case getRelatedArticleList:
                        apiHelper.getRelatedArticleList("61695293");
                        break;
                    case addArticle:
                        apiHelper.addArticle("TestTitle", "TestBody");
                        break;
                    case updateArticle:
                        apiHelper.updateArticle("174157501", "TestUpdateTitle", "Body", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
                        break;
                    case removeArticle:
                        apiHelper.deleteArticle("174156916");
                        break;
                    case getArticleListByLatest:
                        break;
                    case getArticleListByHot:
                        break;
                    case getArticleListByHotWithin:
                        apiHelper.getArticleListByHotWithin(1, "20140101-201401001");
                        break;
                    case searchArticleList:
                        break;
                    case getCommentList:
                        apiHelper.getCommentList();
                        break;
                    case addComment:
                        apiHelper.addComment("185508579", "test");
                        break;
                    case getComment:
                        apiHelper.getComment("33785508,33784806,33783552");
                        break;
                    case replyComment:
                        apiHelper.replyComment("multireply test", "33785508", "33784806", "33783552");
                        break;
                    case setCommentVisibility:
                        apiHelper.setCommentVisibility(false, "33785508", "33784806", "33783552");
                        break;
                    case markComment:
                        apiHelper.markComment(false, "33785508", "33784806", "33783552");
                        break;
                    case removeComment:
                        //blogHelper.deleteComment("33802053", "33802059", "33802065");
                        break;
                    case getCommentListByLatest:
                        break;
                    case getCategorieList:
                        apiHelper.getCategorieList();
                        break;
                    case getTags:
                        apiHelper.getTags();
                        break;
                    default:
                }
            }
        });
    }
}

package net.pixnet.sdk.sample;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.utils.Helper;
import net.pixnet.sdk.utils.PixnetApiHelper;
import net.pixnet.sdk.utils.PixnetApiResponseListener;

public class Album extends ItemDetailFragment {

    private static enum Apis{
        main,

        getSetAndFolderList,
        sortSetAndFolders,

        getSetList,
        getSet,
        getElementListBySet,
        getCommentListBySet,
        addSet,
        updateSet,
        deleteSet,
        sortSets,
        getSetListByNear,

        getFolderList,
        getFolder,
        addFolder,
        updateFolder,
        deleteFolder,

        getElement,
        getCommentListByElement,
        addElement,
        updateElement,
        deleteElement,
        sortElements,
        getElementListByNear,

        addCommentToSet,
        getCommentBySet,
        markSetCommentIsSpam,
        markSetCommentIsHam,
        deleteCommentFromSet,

        addCommentToElement,
        getCommentByElement,
        markElementCommentIsSpam,
        markElementCommentIsHam,
        deleteCommentFromElement,

        addFaceByRecommendId,
        addFaceByElementId,
        updateFace,
        deleteFace

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Apis[] data=Apis.values();

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

                if(convertView==null){
                    v=View.inflate(getActivity(), android.R.layout.simple_list_item_1, null);
                }
                else{
                    v= convertView;
                }

                TextView t= (TextView) v.findViewById(android.R.id.text1);
                t.setText(getItem(position));

                return v;
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PixnetApiHelper apiHelper =PIXNET.getApiHelper(getActivity(), new PixnetApiResponseListener() {
                    @Override
                    public void onError(String msg) {
                        Helper.log("error:" + msg);
                    }

                    @Override
                    public boolean onDataResponse(BasicResponse response) {
                        Helper.log("onDataResponse");
                        Helper.log(response.getRawData());
                        return true;
                    }
                });
                apiHelper.setBloggerName("emmedemo");
                apiHelper.setDefaultPerPage(2);
                apiHelper.setDefaultTrimUser(true);
                switch (Apis.values()[position]){
                    case main:
                        apiHelper.getAlbumMain();
                        break;
                    case getSetAndFolderList:
                        apiHelper.getSetAndFolderList();
                        break;
                    case sortSetAndFolders:
                        apiHelper.sortSetAndFolders(null);
                        break;
                    case getSetList:
                        apiHelper.getSetList();
                        break;
                    case getSet:
                        apiHelper.getSet("34260");
                        break;
                    case getElementListBySet:
                        apiHelper.getElementListBySet("34260");
//                        ben68
//                        albumHelper.getElementListBySet("5024073");
                        break;
                    case getCommentListBySet:
                        apiHelper.getCommentListBySet("34258");
                        break;
                    case addSet:
                        apiHelper.addSet("testSet", "setDescription");
                        break;
                    case updateSet:
                        break;
                    case deleteSet:
                        apiHelper.deleteSet("5024067");
                        break;
                    case sortSets:
                        break;
                    case getSetListByNear:
                        apiHelper.getSetListByNear(23.9738759, 120.982024);
                        break;
                    case getFolderList:
                        apiHelper.getFolderList();
                        break;
                    case getFolder:
                        apiHelper.getFolder("4953822");
                        break;
                    case addFolder:
                        apiHelper.addFolder("testFolder", "this is a test folder");
                        break;
                    case updateFolder:
                        apiHelper.updateFolder("5028546", "modify test", "this is a modified folder");
                        break;
                    case deleteFolder:
                        apiHelper.deleteFolder("5028546");
                        break;
                    case getElement:
                        apiHelper.getElement("413994");
//                        ben68
//                        albumHelper.getElement("174125439");
//                        albumHelper.getElement("174125448");
                        break;
                    case getCommentListByElement:
                        apiHelper.getCommentListByElement("413994");
                        break;
                    case addElement:
                        break;
                    case updateElement:
                        break;
                    case deleteElement:
                        break;
                    case sortElements:
                        break;
                    case getElementListByNear:
                        break;
                    case addCommentToSet:
                        break;
                    case getCommentBySet:
                        break;
                    case markSetCommentIsSpam:
                        break;
                    case markSetCommentIsHam:
                        break;
                    case deleteCommentFromSet:
                        break;
                    case addCommentToElement:
                        break;
                    case getCommentByElement:
                        break;
                    case markElementCommentIsSpam:
                        break;
                    case markElementCommentIsHam:
                        break;
                    case deleteCommentFromElement:
                        break;
                    case addFaceByRecommendId:
                        apiHelper.addFaceByRecommend("emmademo", "800");
                        break;
                    case addFaceByElementId:
                        apiHelper.addFaceByElement("ben68", "174125448", 10, 10, 200, 200);
                        break;
                    case updateFace:
                        apiHelper.updateFace("76914", "ben68", "174125448", 20, 20, 100, 100);
                        break;
                    case deleteFace:
                        break;
                    default:
                }
            }
        });
    }
}

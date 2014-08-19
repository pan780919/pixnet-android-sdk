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
import net.pixnet.sdk.utils.AlbumHelper;
import net.pixnet.sdk.utils.Helper;

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

        addFace,
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
                AlbumHelper albumHelper =PIXNET.getAlbumHelper(getActivity(), "emmademo", new DataProxy.DataProxyListener() {
                    @Override
                    public void onError(String msg) {
                        Helper.log("error:" + msg);
                    }

                    @Override
                    public void onDataResponse(BasicResponse response) {
                        Helper.log("onDataResponse");
                    }
                });
                albumHelper.setDefaultPerPage(2);
                albumHelper.setDefaultTrimUser(true);
                switch (Apis.values()[position]){
                    case main:
                        albumHelper.getMain();
                        break;
                    case getSetAndFolderList:
                        albumHelper.getSetAndFolderList();
                        break;
                    case sortSetAndFolders:
                        albumHelper.sortSetAndFolders(null);
                        break;
                    case getSetList:
                        albumHelper.getSetList();
                        break;
                    case getSet:
                        albumHelper.getSet("34260");
                        break;
                    case getElementListBySet:
                        albumHelper.getElementListBySet("34260");
                        break;
                    case getCommentListBySet:
                        albumHelper.getCommentListBySet("34258");
                        break;
                    case addSet:
                        albumHelper.addSet("testSet", "setDescription");
                        break;
                    case updateSet:
                        break;
                    case deleteSet:
                        albumHelper.deleteSet("5024067");
                        break;
                    case sortSets:
                        break;
                    case getSetListByNear:
                        albumHelper.getSetListByNear(23.9738759, 120.982024);
                        break;
                    case getFolderList:
                        albumHelper.getFolderList();
                        break;
                    case getFolder:
                        albumHelper.getFolder("4953822");
                        break;
                    case addFolder:
                        albumHelper.addFolder("testFolder", "this is a test folder");
                        break;
                    case updateFolder:
                        albumHelper.updateFolder("5028546", "modify test", "this is a modified folder");
                        break;
                    case deleteFolder:
                        albumHelper.deleteFolder("5028546");
                        break;
                    case getElement:
                        albumHelper.getElement("413994");
                        break;
                    case getCommentListByElement:
                        albumHelper.getCommentListByElement("413994");
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
                    case addFace:
                        break;
                    case updateFace:
                        break;
                    case deleteFace:
                        break;
                    default:
                }
            }
        });
    }
}

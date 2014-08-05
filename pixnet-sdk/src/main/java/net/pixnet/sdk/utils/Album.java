package net.pixnet.sdk.utils;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.proxy.*;
import net.pixnet.sdk.proxy.Error;
import net.pixnet.sdk.response.BasicResponse;

public class Album extends DataProxy{

    private static final String URL_MAIN="http://emma.pixnet.cc/album/main";

    /**
     * 列出相簿主圖及相片牆
     */
    public void getMain(){
        Helper.log("getMain");
        boolean isLogin=PIXNET.isLogin(c);
        if(!isLogin){
            listener.onError(Error.LOGIN_NEED);
            return;
        }

        Request r=new Request(URL_MAIN);
        r.setCallback(new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                Helper.log(response);
//                BasicResponse res=new BasicResponse();
//                listener.onDataResponse(res);
            }
        });

        RequestController rc=RequestController.getInstance();
        rc.setHttpConnectionTool(getConnectionTool(true));
        rc.addRequest(r);
    }

    /**
     * 列出相簿全站分類
     */
    public void getAlbumCategoryList(){}

    /**
     * 相簿首頁 (與 http://:user.pixnet.net/album/list 同步)
     */
    public void getSetAndFolderList(){}

    /**
     * 讀取個人相簿單一資料夾
     */
    public void getFolder(){}

    /**
     * 列出個人相簿資料夾
     */
    public void getFolderList(){}

    /**
     * 讀取個人相簿單一 Set
     */
    public void getSet(){}

    /**
     * 列出相簿個人 Set 清單
     */
    public void getSetList(){}

    /**
     * 列出相簿資料夾內的相簿 Set
     */
    public void getSetListByFolder(){}

    /**
     * 列出附近的相簿 Set
     */
    public void getSetListByNear(){}

    /**
     * 讀取相簿單篇圖片影片
     */
    public void getElement(){}

    /**
     * 列出相簿所有圖片影片
     */
    public void getElementListByAlbum(){}

    /**
     * 列出單一相簿 Set 的所有相片
     */
    public void getElementListBySet(){}

    /**
     * 列出附近的相簿圖片影片
     */
    public void getElementListByNear(){}

    /**
     * 讀取 Set 單一留言
     */
    public void getCommentForSet(){}

    /**
     * 讀取相片單一留言
     */
    public void getCommentForElement(){}

    /**
     * 列出 Set 的留言
     */
    public void getCommentListForSet(){}

    /**
     * 列出相片留言
     */
    public void getCommentListForElement(){}

    /**
     * 列出單一相簿 Set 的留言
     */
    public void getCommentListBySet(){}

    /**
     * 讀取相簿單篇圖片影片留言
     */
    public void getCommentListByElement(){}

    /**
     * 新增個人相簿資料夾
     */
    public void addFolder(){}

    /**
     * 新增相簿個人 Set
     */
    public void addSet(){}

    /**
     * 新增相簿圖片影片
     */
    public void addElement(){}

    /**
     * 新增 Set 留言
     */
    public void addCommentToSet(){}

    /**
     * 新增相片留言
     */
    public void addCommentToElement(){}

    /**
     * 新增人臉標記
     */
    public void addFace(){}

    /**
     * 修改個人相簿資料夾
     */
    public void updateFolder(){}

    /**
     * 修改相簿個人 Set
     */
    public void updateSet(){}

    /**
     * 修改相簿單篇圖片影片
     */
    public void updateElement(){}

    /**
     * 更新人臉標記
     */
    public void updateFace(){}

    /**
     * 刪除個人相簿資料夾
     */
    public void removeFolder(){}

    /**
     * 刪除相簿個人 Set
     */
    public void removeSet(){}

    /**
     * 刪除相簿單篇圖片影片
     */
    public void removeElement(){}

    /**
     * 刪除相本 Set 的留言
     */
    public void removeCommentFromSet(){}

    /**
     * 刪除相片的留言
     */
    public void removeCommentFromElement(){}

    /**
     * 刪除人臉標記
     */
    public void removeFace(){}

    /**
     * 修改相簿首頁排序
     */
    public void sortSetAndFolderList(){}

    /**
     * 修改相簿 Set 排序
     */
    public void sortSetList(){}

    /**
     * 修改相簿圖片影片排序
     */
    public void sortElementList(){}

    /**
     * 將 Set 的留言設為廣告留言
     */
    public void markCommentIsSpamForSet(){}

    /**
     * 將相片的留言設為廣告留言
     */
    public void markCommentIsSpamByElement(){}

    /**
     * 將 Set 的留言設為非廣告留言
     */
    public void markCommentIsHamForSet(){}

    /**
     * 將相片的留言設為非廣告留言
     */
    public void markCommentIsHamByElement(){}

    /**
     * 列出相簿個人設定
     */
    public void getConfig(){}

}

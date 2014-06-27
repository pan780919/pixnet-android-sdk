package net.pixnet.sdk.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koi on 2014/6/27.
 */
public class HttpRequestList {
    List<HttpHelper> list = new ArrayList<HttpHelper>();
    int count = 0;

    public int addRequest(HttpHelper request){
        if(count<3){
            list.add(count,request);
            count++;
            return count-1;
        }else
        return -1;
    }
    public void removeRequest(HttpHelper request){
        list.remove(request);
        count--;
    }
    public void cancelRequest(int num){
        try {
            HttpHelper hh = list.get(num);
            hh.cancel();
        }catch(IndexOutOfBoundsException e){

        }
    }
}

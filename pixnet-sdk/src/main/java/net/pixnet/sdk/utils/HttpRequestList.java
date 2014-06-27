package net.pixnet.sdk.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Koi on 2014/6/27.
 */
public class HttpRequestList {
    HashMap<Integer,HttpHelper> map = new HashMap<Integer,HttpHelper>();
    int count = 0;

    public int addRequest(HttpHelper request){
        if(count<3){
            map.put(count,request);
            count++;
            return count-1;
        }else
        return -1;
    }
    public void removeRequest(int key){
        map.remove(key);
        count--;
    }
    public void cancelRequest(int key){
        try {
            HttpHelper hh = map.get(key);
            hh.cancel();
        }catch(IndexOutOfBoundsException e){

        }
    }
}

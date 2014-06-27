package net.pixnet.sdk.utils;

import java.util.HashMap;

/**
 * A hash_map stored HttpRequests and control amount of requests
 */
public class HttpRequestList {
    private HashMap<Integer, HttpHelper> map = new HashMap<Integer, HttpHelper>();
    private int count = 0;
    private int limit = 3;

    /**
     * Set the limit of amount of requests
     * @param limit limit of requests
     */
    public void setLimit(int limit){
        this.limit = limit;
    }
    /**
     * If reach the limit then stuck the request , else put the request into the hash_map.
     * @param request Http_request
     * @return the position of the request
     */
    public int addRequest(HttpHelper request) {
        if (count < limit) {
            map.put(count, request);
            count++;
            return count - 1;
        } else
            return -1;
    }

    /**
     * remove the request at position - key from the hash_map.
     * @param key the position where you want to remove a request.
     */
    public void removeRequest(int key) {
        map.remove(key);
        count--;
    }

    /**
     * cancel the request at position - key from the hash_map.
     * @param key the position where you want to cancel a request.
     */
    public void cancelRequest(int key) {
        try {
            HttpHelper hh = map.get(key);
            hh.cancel();
        } catch (IndexOutOfBoundsException e) {

        }
    }
}

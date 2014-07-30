package net.pixnet.sdk.utils;

import android.util.SparseArray;

/**
 * A SparseArray stored HttpRequests and control amount of requests
 */
public class HttpRequestList {
    SparseArray<HttpConnectionTool> map = new SparseArray<HttpConnectionTool>();
    private int count = 0;
    private int limit = 3;

    /**
     * Set the limit of amount of requests
     *
     * @param limit limit of requests
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * If reach the limit then stuck the request , else put the request into the SparseArray.
     *
     * @param request Http_request
     * @return the position of the request
     */
    public int addRequest(HttpConnectionTool request) {
        if (map.size() < limit) {
            map.put(count, request);
            count++;
            return count - 1;
        } else
            return -1;
    }

    /**
     * remove the request at position - key from the SparseArray.
     *
     * @param key the position where you want to remove a request.
     */
    public void removeRequest(int key) {
        map.delete(key);
    }

    /**
     * cancel the request at position - key from the SparseArray.

     * @param key the position where you want to cancel a request.
     */
    public void cancelRequest(int key) {
        try {
            HttpConnectionTool hh = map.get(key, null);
            if (hh != null)
                hh.cancel();
        } catch (Exception e) {

        }
    }
}

package net.pixnet.sdk.response;

import net.pixnet.sdk.proxy.DataProxy;

import org.json.JSONException;
import org.json.JSONObject;

public class Location {

    /**
     * Location latitude
     */
    public double latitude;
    /**
     * Location longitude
     */
    public double longitude;

    public Location(JSONObject obj) throws JSONException {
        if (obj.has("latitude")) {
            latitude = DataProxy.getJsonDouble(obj, "latitude");
        }
        if (obj.has("longitude")) {
            longitude = DataProxy.getJsonDouble(obj, "longitude");
        }
    }
}
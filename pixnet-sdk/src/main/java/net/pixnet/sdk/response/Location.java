package net.pixnet.sdk.response;

import org.json.JSONException;
import org.json.JSONObject;

public class Location {
    public Location(String response) {
        formatJson(response);
    }

    /**
     * Location latitude
     */
    public double latitude;
    /**
     * Location longitude
     */
    public double longitude;

    private void formatJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if (obj.has("latitude")) {
                latitude = obj.getDouble("latitude");
            }
            if (obj.has("longitude")) {
                longitude = obj.getDouble("longitude");
            }
        } catch (JSONException e) {

        }
    }
}
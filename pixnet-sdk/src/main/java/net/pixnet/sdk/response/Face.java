package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Face {
    Face(String response) {
        formatJson(response);
    }

    /**
     * Faces tagged
     */
    public ArrayList<Tagged> tagged;

    private void formatJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if (obj.has("tagged")) {
                tagged = new ArrayList<Tagged>();
                JSONArray aobj = obj.getJSONArray("tagged");
                for (int i = 0; i < aobj.length(); i++) {
                    tagged.add(new Tagged(aobj.getString(i)));
                }
            }
        } catch (JSONException e) {

        }
    }
}
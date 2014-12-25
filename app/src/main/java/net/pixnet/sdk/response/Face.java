package net.pixnet.sdk.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Face {

    public List<FaceTag> tagged;
    public List<FaceTag> recommended;

    public Face(JSONObject obj) throws JSONException {
        if (obj.has("tagged")) {
            JSONArray aobj = obj.getJSONArray("tagged");
            int i=0, len=aobj.length();
            if(len>0){
                tagged = new ArrayList<>();
                while (i<len){
                    tagged.add(new FaceTag(aobj.getJSONObject(i)));
                    i++;
                }
            }
        }
        if (obj.has("recommended")) {
            JSONArray aobj = obj.getJSONArray("recommended");
            int i=0, len=aobj.length();
            if(len>0){
                recommended = new ArrayList<>();
                while (i<len){
                    recommended.add(new FaceTag(aobj.getJSONObject(i)));
                    i++;
                }
            }
        }
    }
}
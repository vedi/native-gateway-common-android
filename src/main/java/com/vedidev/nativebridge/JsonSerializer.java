package com.vedidev.nativebridge;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author vedi
 *         date 05/10/14
 */
public class JsonSerializer {
    public static String serialize(JSONObject dict) {
        return dict.toString();
    }

    public static JSONObject deserialize(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

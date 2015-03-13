package net.pixnet.sdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class Helper {
	
	private final static boolean DEBUG=true;

	public static void toast(Context c, String msg){
		Toast
		.makeText(c, msg, Toast.LENGTH_SHORT)
		.show();
	}
	
	public static void log(String msg){
		if(!DEBUG || msg==null)
            return;
        Log.d("DEBUG", msg);
	}

	public static void putPrefString(Context c, String key, String value){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
		sp.edit()
		.putString(key, value)
		.commit();
	}
	
	public static void putPrefInt(Context c, String key, int value){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
		sp.edit()
		.putInt(key, value)
		.commit();
	}

    public static void putPrefLong(Context c, String key, long value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        sp.edit()
        .putLong(key, value)
        .commit();
    }

	public static void putPrefBoolean(Context c, String key, boolean value){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
		sp.edit()
		.putBoolean(key, value)
		.commit();
	}
	
	public static String getPrefString(Context c, String key, String defValue){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
		return sp.getString(key, defValue);
	}
	
	public static int getPrefInt(Context c, String key, int defValue){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
		return sp.getInt(key, defValue);
	}

    public static long getPrefLong(Context c, String key, long defValue){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getLong(key, defValue);
    }

	public static boolean getPrefBoolean(Context c, String key, boolean defValue){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
		return sp.getBoolean(key, defValue);
	}

}

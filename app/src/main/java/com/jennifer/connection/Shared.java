package com.jennifer.connection;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * Created by felix on 20/02/2016.
 */
public class Shared {

    private static final String SHARED = "IdUser";

    public void setShared(Activity activity, Integer idUser, final String key) {
        SharedPreferences preferences = activity.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(key, idUser.toString());
        editor.commit();
    }

    public Integer getShared(Activity activity, final String key) {
        SharedPreferences preferences = activity.getSharedPreferences(SHARED, Context.MODE_PRIVATE);

        return Integer.parseInt(preferences.getString(key, ""));
    }
}
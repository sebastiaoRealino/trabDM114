package br.inatel.lojaonline.controller;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Seba on 23/06/2016.
 */
public class SharedPreferenceController {

    private static final String SHARED_PREF_NAME = "teachkids_pref";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //***********************SINGLETON FACTORY***********************************

    static SharedPreferenceController sharedPreferenceController = null;

    public static SharedPreferenceController getSharedPreferencesController(Activity activity) {
        if (sharedPreferenceController == null) {
            sharedPreferenceController = new SharedPreferenceController(activity);
        }
        return sharedPreferenceController;
    }
    //**************************************************************************

    //private constructor
    SharedPreferenceController(Activity activity) {
        sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, 0);
        editor = sharedPreferences.edit();
    }


    public SharedPreferences getSharedPreferences()
    {
        return this.sharedPreferences;
    }

    public SharedPreferences.Editor getSharedPreferencesEditor()
    {

        return this.editor;
    }



    public void putString(String preferenceName, String data)
    {
        editor.putString(preferenceName, data);
        editor.commit();
    }

    public String getString(String preferenceName)
    {
        return  sharedPreferences.getString(preferenceName, "");
    }

    public void removePreference(String preferenceName)
    {
        editor.remove(preferenceName);
        editor.commit();
    }

}
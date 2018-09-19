package com.alsalil.web.vote.Intro;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ma7MouD on 3/19/2018.
 */

public class PreferenceManager {

    private Context context ;
    private SharedPreferences sharedPreferences ;


    public PreferenceManager(Context contex) {

        this.context = contex;
        sharedPreferences = context.getSharedPreferences("file", Context.MODE_PRIVATE);
    }


    public void writePreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("preference_key", "INIT_OK");
        editor.commit();
        editor.apply();
    }

    public boolean check_preferences(){

        boolean status = false ;
        if (sharedPreferences.getString("preference_key", "null").equals("null")){

            status = false ;
        } else {

            status = true ;
        }

        return status ;
    }

    public void clearPreferences(){

        sharedPreferences.edit().clear().commit();
    }
}

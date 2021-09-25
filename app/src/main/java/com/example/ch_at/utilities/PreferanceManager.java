package com.example.ch_at.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.PublicKey;

public class PreferanceManager {
    private final SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    Context context;

    public PreferanceManager(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(Constants.KEY_PREFERANCE_NAME,context.MODE_PRIVATE);
         editor = sharedPreferences.edit();
    }
    public void putBoolean(String key , boolean value)
    {
        editor.putBoolean(key,value);
        editor.apply();
    }

    public Boolean getBoolean (String Key)
    {
        return sharedPreferences.getBoolean(Key,false);
    }

    public void putString(String key , String value)
    {
        editor.putString(key,value);
        editor.apply();
    }
    public String getString(String key)
    {
        return sharedPreferences.getString(key,null);
    }
    public void clear()
    {
        editor.clear();
        editor.apply();
    }


}

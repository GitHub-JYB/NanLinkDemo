package com.example.nanlinkdemo.util;

import android.content.Context;
import android.content.SharedPreferences;


public class SpUtil {

    private static SpUtil spUtil;
    private static SharedPreferences mSharedPreferences;
    public static final String PREFERENCE_NAME = "saveinfo";
    private static SharedPreferences.Editor editor;
    private String KEY_USERNAME ="key_username";
    private String KEY_LOGIN = "key_login";


    private SpUtil(Context context){
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
    }

    public static SpUtil getIntance(Context context){
        if (mSharedPreferences ==  null){
            spUtil = new SpUtil(context);
        }
        editor = mSharedPreferences.edit();
        return spUtil;
    }

    public void setUsername(String username){
        editor.putString(KEY_USERNAME,username);
        editor.commit();
    }

    public String getUsername(){
        return mSharedPreferences.getString(KEY_USERNAME,"");
    }

    public boolean isLogin() {
        return mSharedPreferences.getBoolean(KEY_LOGIN,false);
    }

    public void setLogin(boolean isLogin) {
        editor.putBoolean(KEY_LOGIN, isLogin);
        editor.commit();
    }
}

package com.example.nanlinkdemo.Application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.DB.bean.User;


public class MyApplication extends Application {

    private static MyApplication instance;
    public static int widthPixels;
    public static int heightPixels;
    public static int statusHigh;
    private static float scale;
    private static String versionName;
    private static User onlineUser, lastUser;

    public static User getOnlineUser() {
        return onlineUser;
    }

    public static void setOnlineUser(User onlineUser) {
        MyApplication.onlineUser = onlineUser;
    }

    public static User getLastUser() {
        return lastUser;
    }

    public static void setLastUser(User lastUser) {
        MyApplication.lastUser = lastUser;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        ARouter.init(this);

        DisplayMetrics density = getResources().getDisplayMetrics();
        widthPixels = density.widthPixels;
        heightPixels = density.heightPixels;
        scale = density.density;
        statusHigh = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height","dimen","android"));
        try {
            versionName = instance.getPackageManager().getPackageInfo("com.example.nanlinkdemo", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    public static MyApplication getInstance(){
        return instance;
    }

    public static int px2dip(float px){
        return (int)(px/scale + 0.5f);
    }

    public static int dip2px(float dip){
        return (int)(dip * scale + 0.5f);
    }

    public static CharSequence getVersion() {
        return versionName;
    }

    public boolean isOpenNetwork(){
        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null){
            return manager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
}

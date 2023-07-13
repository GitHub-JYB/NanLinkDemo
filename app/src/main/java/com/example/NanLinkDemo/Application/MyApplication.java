package com.example.NanLinkDemo.Application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.DB.bean.Device;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;
import com.example.NanLinkDemo.DB.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class MyApplication extends Application {

    private static MyApplication instance;
    public static int widthPixels;
    public static int heightPixels;
    public static int statusHigh;
    private static float scale;

    private static float fontScale;

    private static String versionName;
    private static User onlineUser, lastUser;

    private static Scene scene;
    private static SceneGroup sceneGroup;

    private static ArrayList<FixtureGroup> fixtureGroups = new ArrayList<>();

    private static ArrayList<Fixture> fixtures = new ArrayList<>();

    private static HashMap<String, Device> deviceHashMap= new HashMap<String, Device>();

    private static int deviceListVersion;

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

    public static HashMap<String, Device> getDeviceHashMap() {
        return deviceHashMap;
    }

    public static void setDeviceHashMap(HashMap<String, Device> deviceHashMap) {
        MyApplication.deviceHashMap = deviceHashMap;
    }

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        MyApplication.scene = scene;
    }

    public static SceneGroup getSceneGroup() {
        return sceneGroup;
    }

    public static void setSceneGroup(SceneGroup sceneGroup) {
        MyApplication.sceneGroup = sceneGroup;
    }

    public static ArrayList<FixtureGroup> getFixtureGroups() {
        return fixtureGroups;
    }

    public static void setFixtureGroups(ArrayList<FixtureGroup> fixtureGroups) {
        MyApplication.fixtureGroups = fixtureGroups;
    }

    public static ArrayList<Fixture> getFixtures() {
        return fixtures;
    }

    public static void setFixtures(ArrayList<Fixture> fixtures) {
        MyApplication.fixtures = fixtures;
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
        fontScale = density.scaledDensity;
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
    public static int px2sp(float px){
        return (int)(px/fontScale + 0.5f);
    }


    public static int dip2px(float dip){
        return (int)(dip * scale + 0.5f);
    }
    public static int sp2px(float sp){
        return (int)(sp * fontScale + 0.5f);
    }
    public static int dip2percentPx(float dip){
        return (int)(dip / 375 * widthPixels + 0.5f);
    }
    public static int sp2percentPx(float sp){
        return (int)(sp / 375 * widthPixels + 0.5f);
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

    public static int getDeviceListVersion() {
        return deviceListVersion;
    }

    public static void setDeviceListVersion(int deviceListVersion) {
        MyApplication.deviceListVersion = deviceListVersion;
    }
}

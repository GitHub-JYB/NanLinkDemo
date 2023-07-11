package com.example.NanLinkDemo.DB.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.NanLinkDemo.DB.bean.Controller;
import com.example.NanLinkDemo.DB.bean.Device;
import com.example.NanLinkDemo.DB.bean.DeviceData;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;
import com.example.NanLinkDemo.DB.bean.User;
import com.example.NanLinkDemo.DB.dao.ControllerDao;
import com.example.NanLinkDemo.DB.dao.DeviceDao;
import com.example.NanLinkDemo.DB.dao.DeviceDataDao;
import com.example.NanLinkDemo.DB.dao.FixtureDao;
import com.example.NanLinkDemo.DB.dao.FixtureGroupDao;
import com.example.NanLinkDemo.DB.dao.SceneDao;
import com.example.NanLinkDemo.DB.dao.SceneGroupDao;
import com.example.NanLinkDemo.DB.dao.UserDao;

@Database(entities = {Scene.class, SceneGroup.class, User.class, Fixture.class, FixtureGroup.class, Device.class, Controller.class, DeviceData.class}, version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {

    private static final String DB_NAME = "NanLinkDemo.db";

    private static volatile MyDataBase instance;

    public static synchronized MyDataBase getInstance(Context context){
        if (instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static MyDataBase create(Context context) {
        return Room.databaseBuilder(context, MyDataBase.class, DB_NAME).build();
    }

    public abstract SceneDao getSceneDao();

    public abstract SceneGroupDao getSceneGroupDao();

    public abstract UserDao getUserDao();

    public abstract FixtureDao getFixtureDao();

    public abstract FixtureGroupDao getFixtureGroupDao();

    public abstract DeviceDao getDeviceListDao();

    public abstract ControllerDao getControllerDao();

    public abstract DeviceDataDao getDeviceDataDao();

}

package com.example.nanlinkdemo.DB.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.DB.bean.FixtureGroup;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.DB.dao.FixtureDao;
import com.example.nanlinkdemo.DB.dao.FixtureGroupDao;
import com.example.nanlinkdemo.DB.dao.SceneDao;
import com.example.nanlinkdemo.DB.dao.SceneGroupDao;
import com.example.nanlinkdemo.DB.dao.UserDao;

@Database(entities = {Scene.class, SceneGroup.class, User.class, Fixture.class, FixtureGroup.class}, version = 1, exportSchema = false)
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

}

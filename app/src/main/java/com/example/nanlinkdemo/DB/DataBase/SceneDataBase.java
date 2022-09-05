package com.example.nanlinkdemo.DB.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.dao.SceneDao;

@Database(entities = {Scene.class}, version = 1, exportSchema = false)
public abstract class SceneDataBase extends RoomDatabase {

    private static final String DB_NAME = "SceneDataBase.db";

    private static volatile SceneDataBase instance;

    public static synchronized SceneDataBase getInstance(Context context){
        if (instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static SceneDataBase create(Context context) {
        return Room.databaseBuilder(context, SceneDataBase.class, DB_NAME).build();
    }

    public abstract SceneDao getSceneDao();

}

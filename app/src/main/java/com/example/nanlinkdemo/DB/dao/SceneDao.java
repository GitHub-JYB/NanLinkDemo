package com.example.nanlinkdemo.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nanlinkdemo.DB.bean.Scene;

import java.util.ArrayList;

@Dao
public interface SceneDao {

    @Query("select * from scene")
    ArrayList<Scene> getAllSceneInfo();

    @Query("select * from scene where name = :name")
    ArrayList<Scene> getSceneInfo(String name);

    @Insert
    void insert(ArrayList<Scene> sceneList);

    @Update
    void updateSceneInfo(Scene...scenes);

    @Delete
    void deleteInfo(Scene...scenes);


}

package com.example.nanlinkdemo.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nanlinkdemo.DB.bean.Scene;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface SceneDao {

    @Query("select * from scene")
    Single<List<Scene>> getAllSceneInfo();

    @Query("select * from scene where name = :name")
    Single<List<Scene>> getSceneInfo(String name);

    @Query("select * from scene where sceneGroup = :sceneGroup")
    Single<List<Scene>> getSceneFromSceneGroup(String sceneGroup);

    @Insert
    Single<Long> insert(Scene scenes);

    @Update
    Single<Integer> updateSceneInfo(Scene scenes);

    @Delete
    Single<Integer> deleteInfo(Scene scenes);


}

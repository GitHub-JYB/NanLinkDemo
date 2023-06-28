package com.example.NanLinkDemo.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.NanLinkDemo.DB.bean.Scene;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface SceneDao {


    @Query("select * from scene where email = :email AND sceneGroup = :sceneGroup")
    Single<List<Scene>> getSceneInfoFromSceneGroup(String email, String sceneGroup);

    @Query("select * from scene where email = :email AND name = :name")
    Single<List<Scene>> getSceneInfoFromName(String email, String name);

    @Query("select * from scene where email = :email")
    Single<List<Scene>> getAllSceneInfo(String email);

    @Insert
    Single<Long> insert(Scene scenes);

    @Update
    Single<Integer> updateSceneInfo(Scene... scenes);

    @Delete
    Single<Integer> deleteInfo(Scene... scenes);


}

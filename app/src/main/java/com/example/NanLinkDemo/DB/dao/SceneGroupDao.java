package com.example.NanLinkDemo.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.NanLinkDemo.DB.bean.SceneGroup;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface SceneGroupDao {

    @Query("select * from sceneGroup where email = :email")
    Single<List<SceneGroup>> getAllSceneGroupInfo(String email);

    @Query("select * from sceneGroup where email = :email AND name = :name")
    Single<List<SceneGroup>> getSceneGroupInfo(String email, String name);

    @Insert
    Single<Long> insert(SceneGroup sceneGroup);

    @Update
    Single<Integer> updateSceneInfo(SceneGroup sceneGroup);

    @Delete
    Single<Integer> deleteInfo(SceneGroup sceneGroup);


}

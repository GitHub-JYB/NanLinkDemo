package com.example.nanlinkdemo.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nanlinkdemo.DB.bean.SceneGroup;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface SceneGroupDao {

    @Query("select * from sceneGroup")
    Single<List<SceneGroup>> getAllSceneInfo();

    @Query("select * from sceneGroup where name = :name")
    Single<List<SceneGroup>> getSceneGroupInfo(String name);

    @Insert
    Single<Long> insert(SceneGroup sceneGroup);

    @Update
    Single<Integer> updateSceneInfo(SceneGroup sceneGroup);

    @Delete
    Single<Integer> deleteInfo(SceneGroup sceneGroup);


}

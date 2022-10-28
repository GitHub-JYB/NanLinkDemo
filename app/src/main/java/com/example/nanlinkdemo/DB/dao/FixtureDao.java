package com.example.nanlinkdemo.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nanlinkdemo.DB.bean.Fixture;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface FixtureDao {


    @Query("select * from fixture where sceneName = :sceneName AND fixtureGroupName = :fixtureGroupName")
    Single<List<Fixture>> getFixtureInfoFromFixtureGroup(String sceneName, String fixtureGroupName);

    @Query("select * from fixture where sceneName = :sceneName AND CH = :CH")
    Single<List<Fixture>> getFixtureInfoFromName(String sceneName, int CH);

    @Insert
    Single<Long> insert(Fixture fixtures);

    @Update
    Single<Integer> updateFixtureInfo(Fixture... fixtures);

    @Delete
    Single<Integer> deleteInfo(Fixture... fixtures);


}

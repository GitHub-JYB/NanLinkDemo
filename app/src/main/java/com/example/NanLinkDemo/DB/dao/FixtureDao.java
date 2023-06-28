package com.example.NanLinkDemo.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.NanLinkDemo.DB.bean.Fixture;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface FixtureDao {


    @Query("select * from fixture where email = :email AND sceneName = :sceneName")
    Single<List<Fixture>> getAllFixtureInfo(String email, String sceneName);

    @Query("select * from fixture where email = :email AND sceneName = :sceneName AND fixtureGroupName = :fixtureGroupName")
    Single<List<Fixture>> getFixtureInfoFromFixtureGroup(String email, String sceneName, String fixtureGroupName);

    @Query("select * from fixture where email = :email AND sceneName = :sceneName AND CH = :CH")
    Single<List<Fixture>> getFixtureInfoFromName(String email, String sceneName, int CH);

    @Insert
    Single<Long> insert(Fixture fixtures);

    @Update
    Single<Integer> updateFixtureInfo(Fixture... fixtures);

    @Delete
    Single<Integer> deleteInfo(Fixture... fixtures);


}

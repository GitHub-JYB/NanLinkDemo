package com.example.NanLinkDemo.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.NanLinkDemo.DB.bean.FixtureGroup;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface FixtureGroupDao {


    @Query("select * from fixturegroup where email = :email AND sceneName = :sceneName")
    Single<List<FixtureGroup>> getAllFixtureGroupInfo(String email, String sceneName);

    @Query("select * from fixturegroup where email = :email AND sceneName = :sceneName AND name = :name")
    Single<List<FixtureGroup>> getFixtureGroupInfoFromName(String email, String sceneName, String name);

    @Insert
    Single<Long> insert(FixtureGroup fixtureGroups);

    @Update
    Single<Integer> updateFixtureGroupInfo(FixtureGroup... fixtureGroups);

    @Delete
    Single<Integer> deleteInfo(FixtureGroup... fixtureGroups);


}

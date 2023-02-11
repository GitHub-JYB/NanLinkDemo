package com.example.nanlinkdemo.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nanlinkdemo.DB.bean.Controller;
import com.example.nanlinkdemo.DB.bean.Fixture;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ControllerDao {


    @Query("select * from controller where email = :email")
    Single<List<Controller>> getAllControllerInfo(String email);

    @Insert
    Single<Long> insert(Controller fixtures);

    @Update
    Single<Integer> updateControllerInfo(Controller... fixtures);

    @Delete
    Single<Integer> deleteInfo(Controller... fixtures);


}

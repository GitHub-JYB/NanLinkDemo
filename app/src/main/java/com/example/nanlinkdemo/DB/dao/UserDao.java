package com.example.nanlinkdemo.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

@Dao
public interface UserDao {

    @Query("select * from user")
    Single<List<User>> getAllUserInfo();

    @Query("select * from user where type = :type")
    Single<List<User>> getUserFromTypeInfo(String type);

    @Query("select * from user where email = :email")
    Single<List<User>> getUserFromEmailInfo(String email);

    @Insert
    Single<Long> insert(User user);

    @Update
    Single<Integer> updateInfo(User user);

    @Delete
    Single<Integer> deleteInfo(User user);


}

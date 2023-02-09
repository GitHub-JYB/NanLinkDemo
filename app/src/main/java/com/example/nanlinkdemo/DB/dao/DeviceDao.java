package com.example.nanlinkdemo.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nanlinkdemo.DB.bean.Device;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface DeviceDao {

    @Query("select * from device")
    Single<List<Device>> getDeviceListInfo();

    @Insert
    Single<Long> insert(Device device);

    @Update
    Single<Integer> updateInfo(Device... device);

    @Delete
    Single<Integer> deleteInfo(Device device);


}

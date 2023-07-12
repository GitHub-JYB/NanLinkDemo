package com.example.NanLinkDemo.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.NanLinkDemo.DB.bean.DeviceData;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface DeviceDataDao {

    @Query("select * from deviceData where deviceId = :deviceId AND contentVersion = :contentVersion")
    Single<List<DeviceData>> getDeviceDataInfo(String deviceId, int contentVersion);

    @Insert
    Single<Long> insert(DeviceData deviceData);

    @Update
    Single<Integer> updateInfo(DeviceData... deviceData);

    @Delete
    Single<Integer> deleteInfo(DeviceData deviceData);


}

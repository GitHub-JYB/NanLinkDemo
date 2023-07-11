package com.example.NanLinkDemo.DB.converter;

import androidx.room.TypeConverter;

import com.example.NanLinkDemo.DB.bean.Device;
import com.example.NanLinkDemo.DB.bean.DeviceData;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.google.gson.Gson;

public class DeviceDataConverter {

    Gson gson = new Gson();

    @TypeConverter
    public Fixture toDeviceData(String data){
        return data == null ? null : gson.fromJson(data, Fixture.class);
    }

    @TypeConverter
    public String FromDeviceData(Fixture deviceData){
        return deviceData == null ? null : gson.toJson(deviceData);
    }
}

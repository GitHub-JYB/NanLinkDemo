package com.example.NanLinkDemo.DB.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "device")
public class Device {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String deviceName;

    @ColumnInfo
    private String deviceId;

    @ColumnInfo
    private String deviceBrand;

    @ColumnInfo
    private String deviceSeries;

    @ColumnInfo
    private String deviceModel;

    public Device(String deviceName, String deviceId, String deviceBrand, String deviceSeries, String deviceModel){
        this.deviceName = deviceName;
        this.deviceId = deviceId;
        this.deviceBrand = deviceBrand;
        this.deviceSeries = deviceSeries;
        this.deviceModel = deviceModel;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceSeries() {
        return deviceSeries;
    }

    public void setDeviceSeries(String deviceSeries) {
        this.deviceSeries = deviceSeries;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

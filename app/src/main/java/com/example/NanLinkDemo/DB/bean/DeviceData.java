package com.example.NanLinkDemo.DB.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "deviceData")
public class DeviceData {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "deviceId",typeAffinity = ColumnInfo.TEXT)
    private String deviceId;
    @ColumnInfo(name = "contentVersion")
    private int contentVersion;
    @ColumnInfo
    private String deviceData;

    public DeviceData(String deviceId, int contentVersion, String deviceData){
        this.deviceId = deviceId;
        this.contentVersion = contentVersion;
        this.deviceData = deviceData;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getContentVersion() {
        return contentVersion;
    }

    public void setContentVersion(int contentVersion) {
        this.contentVersion = contentVersion;
    }

    public String getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(String deviceData) {
        this.deviceData = deviceData;
    }
}

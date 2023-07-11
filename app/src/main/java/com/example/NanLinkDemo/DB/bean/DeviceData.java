package com.example.NanLinkDemo.DB.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.NanLinkDemo.DB.converter.DeviceDataConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "deviceData")
@TypeConverters({DeviceDataConverter.class})
public class DeviceData {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "deviceId",typeAffinity = ColumnInfo.TEXT)
    private String deviceId;
    @ColumnInfo
    private String deviceName;
    @ColumnInfo
    private String contentVersion;
    @ColumnInfo
    private String deviceBrand;
    @ColumnInfo
    private String deviceSeries;
    @ColumnInfo
    private String deviceModel;
    @ColumnInfo
    private String agreementVersion;
    @ColumnInfo
    private int pixelsLengthX;
    @ColumnInfo
    private int pixelsLengthY;
    @ColumnInfo
    private String dimItem;
    @ColumnInfo
    private String dimDelay;
    @ColumnInfo
    private Fixture control;

    public DeviceData(String deviceId, String contentVersion, String dimItem, String dimDelay, Fixture control){
        this.deviceId = deviceId;
        this.contentVersion = contentVersion;
        this.dimItem = dimItem;
        this.dimDelay = dimDelay;
        this.control = control;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getContentVersion() {
        return contentVersion;
    }

    public void setContentVersion(String contentVersion) {
        this.contentVersion = contentVersion;
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

    public String getAgreementVersion() {
        return agreementVersion;
    }

    public void setAgreementVersion(String agreementVersion) {
        this.agreementVersion = agreementVersion;
    }

    public int getPixelsLengthX() {
        return pixelsLengthX;
    }

    public void setPixelsLengthX(int pixelsLengthX) {
        this.pixelsLengthX = pixelsLengthX;
    }

    public int getPixelsLengthY() {
        return pixelsLengthY;
    }

    public void setPixelsLengthY(int pixelsLengthY) {
        this.pixelsLengthY = pixelsLengthY;
    }

    public String getDimItem() {
        return dimItem;
    }

    public void setDimItem(String dimItem) {
        this.dimItem = dimItem;
    }

    public String getDimDelay() {
        return dimDelay;
    }

    public void setDimDelay(String dimDelay) {
        this.dimDelay = dimDelay;
    }

    public Fixture getControl() {
        return control;
    }

    public void setControl(Fixture control) {
        this.control = control;
    }
}

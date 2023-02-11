package com.example.nanlinkdemo.DB.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "controller")
public class Controller {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String email;
    @ColumnInfo
    private String controllerName;
    @ColumnInfo(name = "uuid")
    private String uuid;
    @ColumnInfo
    private String DEVICE_ID;
    @ColumnInfo
    private String connectType;
    @ColumnInfo
    private int electric_quantity;
    @ColumnInfo
    private String powerType;
    @ColumnInfo
    private int onlineType;




    public Controller(String email, String controllerName, String uuid, String DEVICE_ID, String connectType){
        this.email = email;
        this.controllerName = controllerName;
        this.uuid = uuid;
        this.DEVICE_ID = DEVICE_ID;
        this.connectType = connectType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getConnectType() {
        return connectType;
    }

    public void setConnectType(String connectType) {
        this.connectType = connectType;
    }

    public int getElectric_quantity() {
        return electric_quantity;
    }

    public void setElectric_quantity(int electric_quantity) {
        this.electric_quantity = electric_quantity;
    }


    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getDEVICE_ID() {
        return DEVICE_ID;
    }

    public void setDEVICE_ID(String DEVICE_ID) {
        this.DEVICE_ID = DEVICE_ID;
    }

    public int getOnlineType() {
        return onlineType;
    }

    public void setOnlineType(int onlineType) {
        this.onlineType = onlineType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

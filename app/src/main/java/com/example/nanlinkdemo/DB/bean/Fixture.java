package com.example.nanlinkdemo.DB.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fixture")
public class Fixture {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String email;
    @ColumnInfo
    private String sceneName;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String fixTureName;
    @ColumnInfo(name = "CH",typeAffinity = ColumnInfo.INTEGER)
    private int CH;
    @ColumnInfo
    private String DEVICE_ID;
    @ColumnInfo
    private String connectType;
    @ColumnInfo
    private String fixtureGroupName;
    @ColumnInfo
    private int electric_quantity;
    @ColumnInfo
    private int DIM;
    @ColumnInfo
    private String powerType;
    @ColumnInfo
    private int OnlineType;




    public Fixture(String email, String sceneName, String fixTureName, int CH, String DEVICE_ID, String connectType, String fixtureGroupName){
        this.email = email;
        this.sceneName = sceneName;
        this.name = this.fixTureName = fixTureName;
        this.CH = CH;
        this.DEVICE_ID = DEVICE_ID;
        this.connectType = connectType;
        this.fixtureGroupName = fixtureGroupName;
        this.DIM = 50;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFixTureName() {
        return fixTureName;
    }

    public void setFixTureName(String fixTureName) {
        this.fixTureName = fixTureName;
    }

    public int getCH() {
        return CH;
    }

    public void setCH(int CH) {
        this.CH = CH;
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

    public int getDIM() {
        return DIM;
    }

    public void setDIM(int DIM) {
        this.DIM = DIM;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        sceneName = sceneName;
    }

    public String getFixtureGroupName() {
        return fixtureGroupName;
    }

    public void setFixtureGroupName(String fixtureGroupName) {
        this.fixtureGroupName = fixtureGroupName;
    }

    public String getDEVICE_ID() {
        return DEVICE_ID;
    }

    public void setDEVICE_ID(String DEVICE_ID) {
        this.DEVICE_ID = DEVICE_ID;
    }

    public int getOnlineType() {
        return OnlineType;
    }

    public void setOnlineType(int onlineType) {
        OnlineType = onlineType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

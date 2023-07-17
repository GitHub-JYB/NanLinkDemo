package com.example.NanLinkDemo.DB.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fixtureGroup")
public class FixtureGroup {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String email;
    @ColumnInfo
    private String sceneName;
    @ColumnInfo(name = "name",typeAffinity = ColumnInfo.TEXT)
    private String name;
    @ColumnInfo
    private int fixtureNum;
    @ColumnInfo
    private int DIM;
    @ColumnInfo
    private String data;

    @ColumnInfo
    private int ModeIndex = 0;




    public FixtureGroup(String email, String sceneName, String name, int fixtureNum){
        this.email = email;
        this.sceneName = sceneName;
        this.name = name;
        this.fixtureNum = fixtureNum;
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

    public int getFixtureNum() {
        return fixtureNum;
    }

    public void setFixtureNum(int fixtureNum) {
        this.fixtureNum = fixtureNum;
    }

    public int getDIM() {
        return DIM;
    }

    public void setDIM(int DIM) {
        this.DIM = DIM;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getModeIndex() {
        return ModeIndex;
    }

    public void setModeIndex(int modeIndex) {
        ModeIndex = modeIndex;
    }
}

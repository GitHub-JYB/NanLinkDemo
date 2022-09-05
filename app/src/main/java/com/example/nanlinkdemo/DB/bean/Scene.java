package com.example.nanlinkdemo.DB.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scene")
public class Scene {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name",typeAffinity = ColumnInfo.TEXT)
    private String name;
    @ColumnInfo
    private int fixtureNum;
    @ColumnInfo
    private String remark;
    @ColumnInfo
    private String createdDate;
    @ColumnInfo
    private String modifiedDate;

    public Scene(int id, String name, int fixtureNum, String remark, String createdDate, String modifiedDate){
        this.id = id;
        this.name = name;
        this.fixtureNum = fixtureNum;
        this.remark = remark;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;

    }

    public int getFixtureNum() {
        return fixtureNum;
    }

    public void setFixtureNum(int fixtureNum) {
        this.fixtureNum = fixtureNum;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

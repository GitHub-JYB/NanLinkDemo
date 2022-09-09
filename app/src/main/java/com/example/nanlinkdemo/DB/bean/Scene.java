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
    private Long createdDate;
    @ColumnInfo
    private Long modifiedDate;
    @ColumnInfo(name = "sceneGroup",typeAffinity = ColumnInfo.TEXT)
    private String sceneGroup;


    public Scene(String name, int fixtureNum, String remark, Long createdDate, Long modifiedDate){
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

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Long modifiedDate) {
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

    public String getSceneGroup() {
        return sceneGroup;
    }

    public void setSceneGroup(String sceneGroup) {
        this.sceneGroup = sceneGroup;
    }
}

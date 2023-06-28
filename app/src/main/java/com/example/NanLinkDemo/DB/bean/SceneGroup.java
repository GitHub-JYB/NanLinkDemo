package com.example.NanLinkDemo.DB.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sceneGroup")
public class SceneGroup {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name",typeAffinity = ColumnInfo.TEXT)
    private String name;
    @ColumnInfo(name = "email",typeAffinity = ColumnInfo.TEXT)
    private String email;
    @ColumnInfo
    private int sceneNum;
    @ColumnInfo
    private String remark;
    @ColumnInfo
    private Long createdDate;
    @ColumnInfo
    private Long modifiedDate;



    public SceneGroup(String email, String name, int sceneNum, String remark, Long createdDate, Long modifiedDate){
        this.email = email;
        this.name = name;
        this.sceneNum = sceneNum;
        this.remark = remark;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;

    }

    public int getSceneNum() {
        return sceneNum;
    }

    public void setSceneNum(int sceneNum) {
        this.sceneNum = sceneNum;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

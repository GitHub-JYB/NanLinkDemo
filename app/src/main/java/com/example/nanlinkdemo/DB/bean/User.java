package com.example.nanlinkdemo.DB.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.Normalizer;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "email",typeAffinity = ColumnInfo.TEXT)
    private String email;
    @ColumnInfo
    private String nickName;
    @ColumnInfo
    private String vocation;
    @ColumnInfo
    private String token;
    @ColumnInfo(name = "type",typeAffinity = ColumnInfo.TEXT, defaultValue = "online")
    private String type;


    public User(String email, String nickName, String vocation, String type, String token){
        this.email = email;
        this.nickName = nickName;
        this.vocation = vocation;
        this.type = type;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

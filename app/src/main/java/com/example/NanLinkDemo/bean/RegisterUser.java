package com.example.NanLinkDemo.bean;



public class RegisterUser {



    private String email;
    private String password;
    private String code;
    private String nickName;

    public RegisterUser() {
    }

    public RegisterUser(String nickName, String email) {
        setNickName(nickName);
        setEmail(email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}

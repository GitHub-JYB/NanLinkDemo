package com.example.nanlinkdemo.bean;

public class Add24GFixture {

    private String name;
    private String CH;

    public Add24GFixture(String CH, String name){
        this.name = name;
        this.CH = CH;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCH() {
        return CH;
    }

    public void setCH(String CH) {
        this.CH = CH;
    }
}

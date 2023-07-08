package com.example.NanLinkDemo.bean;

public class Add24GFixture {

    private String name;
    private int CH;

    public Add24GFixture(int CH, String name){
        this.name = name;
        this.CH = CH;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCH() {
        return CH;
    }

    public void setCH(int CH) {
        this.CH = CH;
    }
}

package com.example.NanLinkDemo.bean;

public class AddFixtureType {

    private String type;
    private String typeRemark;

    public AddFixtureType(String type, String typeRemark){
        this.type = type;
        this.typeRemark = typeRemark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeRemark() {
        return typeRemark;
    }

    public void setTypeRemark(String typeRemark) {
        this.typeRemark = typeRemark;
    }
}

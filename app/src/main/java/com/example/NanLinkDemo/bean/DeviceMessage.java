package com.example.NanLinkDemo.bean;


import com.example.NanLinkDemo.DB.bean.Device;

import java.util.ArrayList;

public class DeviceMessage {


    private int code;

    private String msg;

    private Data data = new Data();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private int contentVersion;
        private ArrayList<Device> deviceList = new ArrayList<>();

        public int getContentVersion() {
            return contentVersion;
        }

        public void setContentVersion(int contentVersion) {
            this.contentVersion = contentVersion;
        }

        public ArrayList<Device> getDeviceList() {
            return deviceList;
        }

        public void setDeviceList(ArrayList<Device> deviceList) {
            this.deviceList = deviceList;
        }



    }




}

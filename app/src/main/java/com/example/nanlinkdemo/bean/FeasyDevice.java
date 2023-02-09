package com.example.nanlinkdemo.bean;


import com.example.nanlinkdemo.Application.MyApplication;

public class FeasyDevice {



    private String UUID;
    private String DEVICE_ID;
    private String NAME = "unknow";
    private int CH;
    private int TYPE;
    private String manufacturer_ID;

    public FeasyDevice(byte[] uuid){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++){
            stringBuilder.append(String.format("%02X", uuid[i]));
        }
        setUUID(stringBuilder.toString());
        if (uuid[7] >=0){
            setCH(uuid[6] * 256 + uuid[7]);
        }else {
            setCH((uuid[6] + 1) * 256 + uuid[7]);
        }
//        setCH(Integer.parseInt(stringBuilder.toString()));
        stringBuilder.delete(0, 12);
        stringBuilder.append(String.format("%02X", uuid[10]));
        stringBuilder.append(String.format("%02X", uuid[11]));
        stringBuilder.append(String.format("%02X", uuid[12]));
        setDEVICE_ID(stringBuilder.toString());
        if (!MyApplication.getDeviceHashMap().isEmpty()){
            setNAME(MyApplication.getDeviceHashMap().get(DEVICE_ID).getDeviceName());
        }
        setTYPE(uuid[13]);
        setManufacturer_ID(String.valueOf(uuid[14]));
    }




    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getDEVICE_ID() {
        return DEVICE_ID;
    }

    public void setDEVICE_ID(String DEVICE_ID) {
        this.DEVICE_ID = DEVICE_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getCH() {
        return CH;
    }

    public void setCH(int CH) {
        this.CH = CH;
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    public String getManufacturer_ID() {
        return manufacturer_ID;
    }

    public void setManufacturer_ID(String manufacturer_ID) {
        this.manufacturer_ID = manufacturer_ID;
    }
}

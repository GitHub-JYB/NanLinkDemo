package com.example.NanLinkDemo.bean;


import com.example.NanLinkDemo.Application.MyApplication;

public class Device {



    private String UUID;
    private String DEVICE_ID;
    private String NAME = "unknow";
    private String CH;
    private int TYPE = 0;
    private String manufacturer_ID = "78";

    private String manufacturer = "FEASY";

    private boolean selected = false;

    public Device(String address, String name){
        setUUID(address);
        setCH(name.substring(5,8));
        setDEVICE_ID(name.substring(9, 15));
        if (!MyApplication.getDeviceHashMap().isEmpty()){
            setNAME(MyApplication.getDeviceHashMap().get(DEVICE_ID).getDeviceName());
        }
        setManufacturer("USER");
    }


    public Device(String address, byte[] uuid){
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < 6; i++){
//            stringBuilder.append(String.format("%02X", uuid[i]));
//        }
//        setUUID(stringBuilder.toString());
        setUUID(address);
        int CH;
        if (uuid[7] >=0){
            CH = uuid[6] * 256 + uuid[7];
        }else {
            CH = (uuid[6] + 1) * 256 + uuid[7];
        }
        if (CH == 0){
            setCH("未设置");
        }else if (CH < 10){
            setCH("00" + CH);
        }else if (CH < 100){
            setCH("0" + CH);
        }else {
            setCH(String.valueOf(CH));
        }
//        setCH(Integer.parseInt(stringBuilder.toString()));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%02X", uuid[10]));
        stringBuilder.append(String.format("%02X", uuid[11]));
        stringBuilder.append(String.format("%02X", uuid[12]));
        setDEVICE_ID(stringBuilder.toString());
        if (!MyApplication.getDeviceHashMap().isEmpty()){
            setNAME(MyApplication.getDeviceHashMap().get(DEVICE_ID).getDeviceName());
        }
        if (String.format("%08X", uuid[13]).charAt(String.format("%08X", uuid[13]).length() - 1) == '1'){
            setTYPE(1);
        }else {
            setTYPE(0);
        }
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

    public String getCH() {
        return CH;
    }

    public void setCH(String CH) {
        this.CH = CH;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}

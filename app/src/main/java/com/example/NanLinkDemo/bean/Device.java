package com.example.NanLinkDemo.bean;


import com.example.NanLinkDemo.Application.MyApplication;

public class Device {



    private String UUID;
    private String DEVICE_ID;
    private String NAME = "unknow";
    private int CH;
    private int TYPE = 0;
    private String manufacturer_ID = "78";
    private int contentVersion = 1;
    private int agreementVersion = 0;
    private String manufacturer = "FEASY";

    private boolean selected = false;

    public Device(String address, String name){
        setUUID(address);
        setCH(Integer.parseInt(name.substring(5,8)));
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
        setCH(CH);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%08X", uuid[8]).substring(7));
        stringBuilder.append(String.format("%08X", uuid[9]));
        if (stringBuilder.toString().equals("000000000")){
            setAgreementVersion(0);
        }else {
            setAgreementVersion(Integer.parseInt(stringBuilder.toString()));
        }
        stringBuilder = new StringBuilder();
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
        if (Integer.parseInt(String.format("%08X",uuid[13]).substring(1, 7)) <= 1){
            setContentVersion(1);
        }else {
            setContentVersion(Integer.parseInt(String.format("%08X",uuid[13]).substring(1, 7)));
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

    public int getCH() {
        return CH;
    }

    public void setCH(int CH) {
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

    public int getContentVersion() {
        return contentVersion;
    }

    public void setContentVersion(int contentVersion) {
        this.contentVersion = contentVersion;
    }

    public int getAgreementVersion() {
        return agreementVersion;
    }

    public void setAgreementVersion(int agreementVersion) {
        this.agreementVersion = agreementVersion;
    }
}

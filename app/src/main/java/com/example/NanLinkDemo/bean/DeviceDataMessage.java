package com.example.NanLinkDemo.bean;


import java.util.ArrayList;

public class DeviceDataMessage {


    private int code;

    private String msg;

    private Data data;

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

    public static class Data {
        private String deviceName;
        private String deviceId;
        private String deviceBrand;
        private String deviceSeries;
        private String deviceModel;
        private int contentVersion;
        private String agreementVersion;
        private int pixelsLengthX;
        private int pixelsLengthY;
        private String dimItem;
        private String dimDelay;
        private ArrayList<String> control = new ArrayList<>();
        private String luminance;
        private ArrayList<String> fan = new ArrayList<>();

        private ArrayList<FlmMode> flmModeList = new ArrayList<>();
        private ArrayList<FlmMode> groupModeList = new ArrayList<>();
        private ArrayList<ColorPaper> coloredPaper = new ArrayList<>();

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public int getContentVersion() {
            return contentVersion;
        }

        public void setContentVersion(int contentVersion) {
            this.contentVersion = contentVersion;
        }

        public String getDeviceBrand() {
            return deviceBrand;
        }

        public void setDeviceBrand(String deviceBrand) {
            this.deviceBrand = deviceBrand;
        }

        public String getDeviceSeries() {
            return deviceSeries;
        }

        public void setDeviceSeries(String deviceSeries) {
            this.deviceSeries = deviceSeries;
        }

        public String getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
        }

        public String getAgreementVersion() {
            return agreementVersion;
        }

        public void setAgreementVersion(String agreementVersion) {
            this.agreementVersion = agreementVersion;
        }

        public int getPixelsLengthX() {
            return pixelsLengthX;
        }

        public void setPixelsLengthX(int pixelsLengthX) {
            this.pixelsLengthX = pixelsLengthX;
        }

        public int getPixelsLengthY() {
            return pixelsLengthY;
        }

        public void setPixelsLengthY(int pixelsLengthY) {
            this.pixelsLengthY = pixelsLengthY;
        }

        public String getDimItem() {
            return dimItem;
        }

        public void setDimItem(String dimItem) {
            this.dimItem = dimItem;
        }

        public String getDimDelay() {
            return dimDelay;
        }

        public void setDimDelay(String dimDelay) {
            this.dimDelay = dimDelay;
        }

        public ArrayList<String> getControl() {
            return control;
        }

        public void setControl(ArrayList<String> control) {
            this.control = control;
        }


        public String getLuminance() {
            return luminance;
        }

        public void setLuminance(String luminance) {
            this.luminance = luminance;
        }

        public ArrayList<String> getFan() {
            return fan;
        }

        public void setFan(ArrayList<String> fan) {
            this.fan = fan;
        }

        public ArrayList<FlmMode> getFlmModeList() {
            return flmModeList;
        }

        public void setFlmModeList(ArrayList<FlmMode> flmModeList) {
            this.flmModeList = flmModeList;
        }

        public ArrayList<FlmMode> getGroupModeList() {
            return groupModeList;
        }

        public void setGroupModeList(ArrayList<FlmMode> groupModeList) {
            this.groupModeList = groupModeList;
        }

        public ArrayList<ColorPaper> getColoredPaper() {
            return coloredPaper;
        }

        public void setColoredPaper(ArrayList<ColorPaper> coloredPaper) {
            this.coloredPaper = coloredPaper;
        }



    }
    public class FlmMode {
        private String modeName;
        private String cmd;
        private String remark;
        private String tip;
        private ArrayList<CMD> cmdList = new ArrayList<>();
        private ArrayList<Control> firstControls = new ArrayList<>();

        public String getModeName() {
            return modeName;
        }

        public void setModeName(String modeName) {
            this.modeName = modeName;
        }

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public ArrayList<CMD> getCmdList() {
            return cmdList;
        }

        public void setCmdList(ArrayList<CMD> cmdList) {
            this.cmdList = cmdList;
        }


        public ArrayList<Control> getFirstControls() {
            return firstControls;
        }

        public void setFirstControls(ArrayList<Control> firstControls) {
            this.firstControls = firstControls;
        }





    }

    public class CMD {
        private int id;
        private int modeId;
        private String cmdName;
        private String cmd;
        private String remark;
        private int byteCount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getModeId() {
            return modeId;
        }

        public void setModeId(int modeId) {
            this.modeId = modeId;
        }

        public String getCmdName() {
            return cmdName;
        }

        public void setCmdName(String cmdName) {
            this.cmdName = cmdName;
        }

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getByteCount() {
            return byteCount;
        }

        public void setByteCount(int byteCount) {
            this.byteCount = byteCount;
        }
    }
    public class Element {
        private String max;
        private String min;
        private String step;
        private String item;
        private String maxItem;
        private String minItem;
        private String toggle;
        private String delay;
        private String hue;
        private String hue1;
        private String hue2;
        private String hue3;
        private String hue4;
        private String hue5;
        private String hue6;
        private String sat;
        private String r;
        private String g;
        private String b;
        private String w;
        private String x;
        private String y;

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getMaxItem() {
            return maxItem;
        }

        public void setMaxItem(String maxItem) {
            this.maxItem = maxItem;
        }

        public String getMinItem() {
            return minItem;
        }

        public void setMinItem(String minItem) {
            this.minItem = minItem;
        }

        public String getToggle() {
            return toggle;
        }

        public void setToggle(String toggle) {
            this.toggle = toggle;
        }

        public String getDelay() {
            return delay;
        }

        public void setDelay(String delay) {
            this.delay = delay;
        }

        public String getHue() {
            return hue;
        }

        public void setHue(String hue) {
            this.hue = hue;
        }

        public String getSat() {
            return sat;
        }

        public void setSat(String sat) {
            this.sat = sat;
        }

        public String getR() {
            return r;
        }

        public void setR(String r) {
            this.r = r;
        }

        public String getG() {
            return g;
        }

        public void setG(String g) {
            this.g = g;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getW() {
            return w;
        }

        public void setW(String w) {
            this.w = w;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getHue1() {
            return hue1;
        }

        public void setHue1(String hue1) {
            this.hue1 = hue1;
        }

        public String getHue2() {
            return hue2;
        }

        public void setHue2(String hue2) {
            this.hue2 = hue2;
        }

        public String getHue3() {
            return hue3;
        }

        public void setHue3(String hue3) {
            this.hue3 = hue3;
        }

        public String getHue4() {
            return hue4;
        }

        public void setHue4(String hue4) {
            this.hue4 = hue4;
        }

        public String getHue5() {
            return hue5;
        }

        public void setHue5(String hue5) {
            this.hue5 = hue5;
        }

        public String getHue6() {
            return hue6;
        }

        public void setHue6(String hue6) {
            this.hue6 = hue6;
        }
    }

    public class Control {
        private int id;
        private int parentId;
        private String uuid;
        private String controlName;
        private String controlType;
        private int selectIndex;
        private String cmd;
        private String remark;
        private String tip;
        private Element elements = new Element();
        private ArrayList<CMD> cmdList = new ArrayList<>();
        private ArrayList<Control> controls = new ArrayList<>();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getControlName() {
            return controlName;
        }

        public void setControlName(String controlName) {
            this.controlName = controlName;
        }

        public String getControlType() {
            return controlType;
        }

        public void setControlType(String controlType) {
            this.controlType = controlType;
        }

        public int getSelectIndex() {
            return selectIndex;
        }

        public void setSelectIndex(int selectIndex) {
            this.selectIndex = selectIndex;
        }

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public Element getElements() {
            return elements;
        }

        public void setElements(Element elements) {
            this.elements = elements;
        }

        public ArrayList<CMD> getCmdList() {
            return cmdList;
        }

        public void setCmdList(ArrayList<CMD> cmdList) {
            this.cmdList = cmdList;
        }

        public ArrayList<Control> getControls() {
            return controls;
        }

        public void setControls(ArrayList<Control> controls) {
            this.controls = controls;
        }

    }
    public class ColorPaper {
        private String sn;
        private String brand;
        private String series;
        private String name;
        private String rgb;
        private String dmx;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getSeries() {
            return series;
        }

        public void setSeries(String series) {
            this.series = series;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRgb() {
            return rgb;
        }

        public void setRgb(String rgb) {
            this.rgb = rgb;
        }

        public String getDmx() {
            return dmx;
        }

        public void setDmx(String dmx) {
            this.dmx = dmx;
        }
    }
}

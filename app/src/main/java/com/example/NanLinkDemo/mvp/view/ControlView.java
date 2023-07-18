package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.bean.Menu;

import java.util.ArrayList;

public interface ControlView {

    void setData(ArrayList<DeviceDataMessage.Control> controls);

    void setSecondTitle(String secondTitle);

    void setFan(int resId);

    void setFanVisibility(int visibility);

    void setMode(String mode);

    void setModeList(ArrayList<DeviceDataMessage.FlmMode> modeList);

    void setTitle(String title);

    void initMenu();

    void showMenu(ArrayList<Menu> menuArrayList);

    void openDrawLayout();

    void closeDrawLayout();

    void finish();

    void setDimAndDelayTime(int dim, int delayTime);
}

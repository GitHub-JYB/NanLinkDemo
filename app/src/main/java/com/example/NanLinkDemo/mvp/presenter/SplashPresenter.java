package com.example.NanLinkDemo.mvp.presenter;

import com.example.NanLinkDemo.DB.bean.Device;
import com.example.NanLinkDemo.DB.bean.User;
import com.example.NanLinkDemo.bean.Message;

import java.util.List;

public interface SplashPresenter {
    void startCountDownFromModel();

    void endCountDown();

    void receiveOnlineUser(List<User> users);

    void sendMesToView(Message message);

    void receiveLastUser(List<User> users);

    void getDeviceListFromModel();

    void receiveDeviceList(List<Device> devices);
}

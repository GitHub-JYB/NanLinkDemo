package com.example.NanLinkDemo.mvp.presenter.Impl;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Device;
import com.example.NanLinkDemo.DB.bean.User;
import com.example.NanLinkDemo.bean.Message;
import com.example.NanLinkDemo.mvp.model.Impl.SplashModelImpl;
import com.example.NanLinkDemo.mvp.presenter.SplashPresenter;
import com.example.NanLinkDemo.mvp.view.SplashView;
import com.example.NanLinkDemo.util.Constant;

import java.util.HashMap;
import java.util.List;

public class SplashPresenterImpl implements SplashPresenter {
    private final SplashView view;
    private final SplashModelImpl model;

    public SplashPresenterImpl(SplashView view) {
        this.view = view;
        model = new SplashModelImpl(this);
    }

    @Override
    public void startCountDownFromModel() {
        model.startCountDown();
    }

    @Override
    public void endCountDown() {
        model.getLastUser();
    }

    @Override
    public void receiveOnlineUser(List<User> users) {
        if (!users.isEmpty()){
            MyApplication.setOnlineUser(users.get(0));
            if (MyApplication.getInstance().isOpenNetwork()){
                model.getUserInfo(MyApplication.getOnlineUser().getToken());
            }else {
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Main).navigation();
                view.finish();
            }
        }else {
            ARouter.getInstance().build(Constant.ACTIVITY_URL_Login).navigation();
            view.finish();
        }
    }

    @Override
    public void sendMesToView(Message message) {
        switch (message.getCode()) {
            case 200:
                MyApplication.getOnlineUser().setNickName(message.getData().getNickName());
                MyApplication.getOnlineUser().setVocation(message.getData().getVocation());
                model.updateUser(MyApplication.getOnlineUser());
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Main).navigation();
                view.finish();
                break;
            case 1001:
            case 1002:
            case 1003:
            case 1004:
            case 1005:
            case 1006:
            case 1007:
            case 1008:
            case 1009:
            case 1010:
            case 1011:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Main).navigation();
                view.finish();
                break;
        }
    }

    @Override
    public void receiveLastUser(List<User> users) {
        if (!users.isEmpty()){
            MyApplication.setLastUser(users.get(0));
        }
        model.getOnlineUser();

    }

    @Override
    public void getDeviceListFromModel() {
        model.getDeviceList();
    }

    @Override
    public void receiveDeviceList(List<Device> devices) {
        if (!devices.isEmpty()){
            HashMap<String, Device> deviceHashMap = new HashMap<String, Device>();
            for (Device device: devices){
                deviceHashMap.put(device.getDeviceId(), device);
            }
            MyApplication.setDeviceHashMap(deviceHashMap);
        }
    }
}

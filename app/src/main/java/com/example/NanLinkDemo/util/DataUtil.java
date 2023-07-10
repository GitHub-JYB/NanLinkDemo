package com.example.NanLinkDemo.util;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.Device;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.bean.DeviceMessage;
import com.example.NanLinkDemo.ui.MyDialog;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DataUtil {

    public static void getDeviceList() {
        if (MyApplication.getInstance().isOpenNetwork()) {
            getDeviceListFromNetwork();
        }else {
            getDeviceListFromDB();
        }
    }

    private static void getDeviceListFromNetwork() {
        Disposable disposable = ApiClient.getService(ApiClient.BASE_URL)
                .getDeviceLIst()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DeviceMessage>() {
                    @Override
                    public void accept(DeviceMessage deviceMessage) throws Exception {
                        switch (deviceMessage.getCode()){
                            case 200:
                                handleDeviceListData();
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
                                getDeviceListFromDB();
                                break;
                        }
                    }
                });
    }

    private static void handleDeviceListData() {
        if (deviceMessage.getData().getContentVersion() > MyApplication.getInstance().getDeviceListVersion()){
            MyApplication.getInstance().setDeviceListVersion(deviceMessage.getData().getContentVersion());
            HashMap<String, Device> deviceHashMap = new HashMap<String, Device>();
            for (Device device: deviceMessage.getData().getDeviceList()){
                deviceHashMap.put(device.getDeviceId(), device);
                if (MyApplication.getDeviceHashMap().isEmpty()){
                    model.addDevice(device);
                }else {
                    model.updateDevice(device);
                }
            }
            MyApplication.setDeviceHashMap(deviceHashMap);
        }
    }

    private static void getDeviceListFromDB() {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getDeviceListDao()
                .getDeviceListInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Device>>() {
                    @Override
                    public void accept(List<Device> devices) throws Exception {

                    }
                });
    }
}

package com.example.NanLinkDemo.util;

import android.content.Context;
import android.util.Log;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.Device;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.bean.DeviceMessage;
import com.example.NanLinkDemo.ui.MyDialog;
import com.google.gson.JsonArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DataUtil {

    public static void getDeviceList(Context context) {
        if (MyApplication.getInstance().isOpenNetwork()) {
            getDeviceListFromNetwork(context);
        }else {
            getDeviceListFromDB(context);
        }
    }

    private static void getDeviceListFromNetwork(Context context) {
        Disposable disposable = ApiClient.getService(ApiClient.BASE_URL)
                .getDeviceLIst()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DeviceMessage>() {
                    @Override
                    public void accept(DeviceMessage deviceMessage) throws Exception {
                        switch (deviceMessage.getCode()){
                            case 200:
                                handleDeviceListData(deviceMessage.getData().getDeviceList());
                                updateDataToDB(deviceMessage.getData().getDeviceList());
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
                                getDeviceListFromDB(context);
                                break;
                        }
                    }
                });
    }



    private static void getDeviceListFromDB(Context context) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getDeviceListDao()
                .getDeviceListInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Device>>() {
                    @Override
                    public void accept(List<Device> devices) throws Exception {
                        if (devices.isEmpty()){
                            getDeviceListFromAssets(context);
                        }else {
                            handleDeviceListData(devices);
                        }

                    }
                });
    }

    private static void getDeviceListFromAssets(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("deviceList.txt");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len = -1;
            byte[] buffer = new byte[1024000];
            while ((len = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, len);
            }
            String rel = outputStream.toString();
            Log.i("TAG", "getDeviceListFromAssets: " + rel);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private static void handleDeviceListData(List<Device> devices) {
        HashMap<String, Device> deviceHashMap = new HashMap<String, Device>();
        for (Device device: devices){
            deviceHashMap.put(device.getDeviceId(), device);
        }
        MyApplication.setDeviceHashMap(deviceHashMap);
    }


    private static void updateDataToDB(ArrayList<Device> deviceList) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getDeviceListDao()
                .getDeviceListInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Device>>() {
                    @Override
                    public void accept(List<Device> devices) throws Exception {
                        if (devices.isEmpty()){
                            for (Device device: deviceList){
                                addDeviceToDB(device);
                            }
                        }else {
                            for (Device device: deviceList){
                                for (Device device1 : devices){
                                    if (device.getDeviceId().equals(device1.getDeviceId())){
                                        device.setId(device1.getId());
                                        updateDeviceToDB(device);
                                        break;
                                    }
                                    addDeviceToDB(device);
                                }
                            }
                        }
                    }
                });
    }

    private static void addDeviceToDB(Device device) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getDeviceListDao()
                .insert(device)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });
    }


    private static void updateDeviceToDB(Device device) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getDeviceListDao()
                .updateInfo(device)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }
}

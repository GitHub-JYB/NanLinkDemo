package com.example.NanLinkDemo.util;

import android.content.Context;
import android.util.Log;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.Device;
import com.example.NanLinkDemo.DB.bean.DeviceData;
import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.bean.DeviceMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        } else {
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
                        switch (deviceMessage.getCode()) {
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
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getDeviceListFromDB(context);
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
                        if (devices.isEmpty()) {
                            getDeviceListFromAssets(context);
                        } else {
                            handleDeviceListData(devices);
                        }
                    }
                });
    }

    private static void getDeviceListFromAssets(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("deviceList.json");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len = -1;
            byte[] buffer = new byte[1024000];
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            String rel = outputStream.toString();
            Gson gson = new Gson();
            DeviceMessage deviceMessage = gson.fromJson(rel, DeviceMessage.class);
            handleDeviceListData(deviceMessage.getData().getDeviceList());
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleDeviceListData(List<Device> devices) {
        HashMap<String, Device> deviceHashMap = new HashMap<String, Device>();
        for (Device device : devices) {
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
                        if (devices.isEmpty()) {
                            for (Device device : deviceList) {
                                addDeviceToDB(device);
                            }
                        } else {
                            for (Device device : deviceList) {
                                for (Device device1 : devices) {
                                    if (device.getDeviceId().equals(device1.getDeviceId())) {
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

    public static void getDeviceData(Context context, String deviceId, int contentVersion, onReceiveDeviceDataListener listener) {
        if (MyApplication.getInstance().isOpenNetwork()) {
            getDeviceDataFromNetwork(context, deviceId, contentVersion, listener);
        } else {
            getDeviceDataFromDB(context, deviceId, contentVersion, listener);
        }
    }

    private static void getDeviceDataFromNetwork(Context context, String deviceId, int contentVersion, onReceiveDeviceDataListener listener) {
        Disposable disposable = ApiClient.getService(ApiClient.BASE_URL)
                .getDeviceData(deviceId, contentVersion)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DeviceDataMessage>() {
                    @Override
                    public void accept(DeviceDataMessage deviceDataMessage) throws Exception {
                        switch (deviceDataMessage.getCode()) {
                            case 200:
                                try {
                                    if (deviceDataMessage.getData() != null) {
                                        handleDeviceData(deviceDataMessage.getData(), listener);
                                        updateToDB(deviceDataMessage.getData());
                                    }
                                } catch (Exception e) {
                                    getDeviceDataFromDB(context, deviceId, contentVersion, listener);
                                }
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
                                getDeviceDataFromDB(context, deviceId, contentVersion, listener);
                                break;
                        }


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getDeviceDataFromDB(context, deviceId, contentVersion, listener);

                    }
                });
    }

    private static void getDeviceDataFromDB(Context context, String deviceId, int contentVersion, onReceiveDeviceDataListener listener) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getDeviceDataDao()
                .getDeviceDataInfo(deviceId, contentVersion)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DeviceData>>() {
                    @Override
                    public void accept(List<DeviceData> deviceDataList) throws Exception {
                        if (deviceDataList.isEmpty()) {
                            getDeviceDataFromAssets(context, deviceId, contentVersion, listener);
                        } else {
                            try {
                                Gson gson = new Gson();
                                DeviceDataMessage.Data deviceData = gson.fromJson(deviceDataList.get(0).getDeviceData(), DeviceDataMessage.Data.class);
                                handleDeviceData(deviceData, listener);
                            } catch (Exception e) {
                                getDeviceDataFromAssets(context, deviceId, contentVersion, listener);
                            }
                        }
                    }
                });
    }

    private static void getDeviceDataFromAssets(Context context, String deviceId, int contentVersion, onReceiveDeviceDataListener listener) {
        try {
            InputStream inputStream = context.getAssets().open(deviceId + "_" + contentVersion + ".json");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len = -1;
            byte[] buffer = new byte[1024000];
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            String rel = outputStream.toString();
            inputStream.close();
            Gson gson = new Gson();
            DeviceDataMessage deviceDataMessage = gson.fromJson(rel, DeviceDataMessage.class);
            handleDeviceData(deviceDataMessage.getData(), listener);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleDeviceData(DeviceDataMessage.Data deviceData, onReceiveDeviceDataListener listener) {
        listener.ReceiveDeviceData(deviceData);
    }

    public interface onReceiveDeviceDataListener {
        void ReceiveDeviceData(DeviceDataMessage.Data deviceData);
    }

    private static void updateToDB(DeviceDataMessage.Data deviceData) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String data = gson.toJson(deviceData);
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getDeviceDataDao()
                .getDeviceDataInfo(deviceData.getDeviceId(), deviceData.getContentVersion())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DeviceData>>() {
                    @Override
                    public void accept(List<DeviceData> deviceDataList) throws Exception {
                        if (deviceDataList.isEmpty()) {
                            addDeviceDataToDB(new DeviceData(deviceData.getDeviceId(), deviceData.getContentVersion(), data));
                        } else {
                            deviceDataList.get(0).setDeviceData(data);
                            updateDeviceDataToDB(deviceDataList.get(0));
                        }
                    }
                });
    }

    private static void addDeviceDataToDB(DeviceData deviceData) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getDeviceDataDao()
                .insert(deviceData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });
    }

    private static void updateDeviceDataToDB(DeviceData deviceData) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getDeviceDataDao()
                .updateInfo(deviceData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                    }
                });
    }
}

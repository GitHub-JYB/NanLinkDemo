package com.example.NanLinkDemo.mvp.presenter.Impl;


import android.content.Intent;
import android.view.View;


import androidx.collection.ArraySet;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Device;
import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.bleConnect.BleMeshManager;
import com.example.NanLinkDemo.bleConnect.ExtendedBluetoothDevice;
import com.example.NanLinkDemo.bleConnect.NrfMeshRepository;
import com.example.NanLinkDemo.mvp.model.Impl.ScanBleModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ScanBlePresenter;
import com.example.NanLinkDemo.mvp.view.ScanBleView;
import com.example.NanLinkDemo.mvp.widget.ScanBleActivity;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.util.DataUtil;
import com.example.NanLinkDemo.util.DateUtil;
import com.example.NanLinkDemo.util.SnackBarUtil;
import com.example.NanLinkDemo.util.TransformUtil;
import com.feasycom.feasymesh.library.MeshBeacon;
import com.feasycom.feasymesh.library.MeshManagerApi;

import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import no.nordicsemi.android.support.v18.scanner.ScanRecord;
import no.nordicsemi.android.support.v18.scanner.ScanResult;


public class ScanBlePresenterImpl implements ScanBlePresenter {
    private final ScanBleView view;
    private final ScanBleModelImpl model;
    private final NrfMeshRepository nrfMeshRepository;

    private boolean allSelected = false;

    ArrayList<ExtendedBluetoothDevice> deviceList = new ArrayList<>();
    private ArrayList<ExtendedBluetoothDevice> selectedDeviceList;
    private ArrayList<ExtendedBluetoothDevice> unsetCHList, unPassCHList, passCHList;

    private Set<String> meshProvisioningAddress = new HashSet<String>();

    public ScanBlePresenterImpl(ScanBleView view) {
        this.view = view;
        this.model = new ScanBleModelImpl(this);
        nrfMeshRepository = new NrfMeshRepository(new MeshManagerApi((ScanBleActivity) view), new BleMeshManager((ScanBleActivity) view));
    }

    @Override
    public void onClickSwitch(int position) {
        deviceList.get(position).setSelected(!deviceList.get(position).isSelected());
        view.showBle(deviceList);
    }

    @Override
    public void getListDataFromView() {
        model.getListData();
    }

    @Override
    public void onClickSwitch(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_btn:
                view.finish();
                break;
            case R.id.all_selected:
                allSelected = !allSelected;
                for (ExtendedBluetoothDevice device : deviceList) {
                    device.setSelected(!allSelected);
                }
                view.showBle(deviceList);
                break;
            case R.id.complete:
                selectedDeviceList = new ArrayList<>();
                for (ExtendedBluetoothDevice device : deviceList) {
                    if (device.isSelected()) {
                        selectedDeviceList.add(device);
                    }
                }
                checkDeviceList(selectedDeviceList);
                break;
            case R.id.toolbar_right_btn:
                deviceList.clear();
                view.showBle(deviceList);
                view.startScan();
        }
    }

    private void checkDeviceList(ArrayList<ExtendedBluetoothDevice> selectedDeviceList) {
        sortSelectedDeviceList(selectedDeviceList);
        notice();
    }

    private void notice() {
        if (unsetCHList.isEmpty() && unPassCHList.isEmpty()) {
            connectAndSetData();
        } else {
            if (!unsetCHList.isEmpty()) {
                view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, "设置地址码", "有" + unsetCHList.size() + "台灯光设备的地址码\n需要进行设置", "取消", null, "设置", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.dismissMyDialog();
                        setCH();
                    }
                });
                return;
            }
            view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, "更改地址码", "有" + unPassCHList.size() + "台灯光设备的地址码\n因与其它设备重复需要更改", "取消", null, "更改", new MyDialog.PositiveOnClickListener() {
                @Override
                public void onClick(View v) {
                    view.dismissMyDialog();
                    updateCH();
                }
            });
        }
    }

    private void connectAndSetData() {
        view.startLoading();
        ArrayList<ExtendedBluetoothDevice> successList = new ArrayList<>();
        for (ExtendedBluetoothDevice device : passCHList) {
            DataUtil.getDeviceData((ScanBleActivity) view, device.getDEVICE_ID(), device.getContentVersion(), new DataUtil.onReceiveDeviceDataListener() {
                @Override
                public void ReceiveDeviceData(String data) {
                    Fixture fixture = new Fixture(MyApplication.getOnlineUser().getEmail(), MyApplication.getScene().getName(), device.getName(), device.getCH(), device.getDEVICE_ID(), "蓝牙", "");
                    fixture.setAgreementVersion(device.getAgreementVersion());
                    fixture.setData(data);
                    model.addBleFixture(fixture);
                    successList.add(device);
                    if (successList.size() == passCHList.size()) {
                        view.stopLoading();
                        ARouter.getInstance().build(Constant.ACTIVITY_URL_Scene).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();
                        view.finish();
                    }
                }
            });
        }
    }

    private void setCH() {
        if (unsetCHList.isEmpty()) {
            notice();
        } else {
            ExtendedBluetoothDevice device = unsetCHList.get(0);
            view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_AddFixture_CH, "设置地址码", device.getName(), TransformUtil.updateCH(device.getCH()), "取消", null, "确定", new MyDialog.PositiveOnClickListener() {
                @Override
                public void onClick(View v) {
                    if (view.getInputTextMyDialog().isEmpty()) {
                        SnackBarUtil.show(v, "请输入介于1-512之间的数值");
                    } else {
                        if (isPassCH(Integer.parseInt(view.getInputTextMyDialog()))) {
                            view.dismissMyDialog();
                            device.setCH(Integer.parseInt(view.getInputTextMyDialog()));
                            unsetCHList.remove(device);
                            passCHList.add(device);
                            setCH();
                        } else {
                            view.dismissMyDialog();
                            view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, "设置地址码", "列表中有另一台设备正在使用该\n地址码, 请尝试其它地址码。", "取消", null, "重试", new MyDialog.PositiveOnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    view.dismissMyDialog();
                                    setCH();
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    private void updateCH() {
        if (unPassCHList.isEmpty()) {
            notice();
        } else {
            ExtendedBluetoothDevice device = unPassCHList.get(0);
            view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_AddFixture_CH, "更改地址码", device.getName(), TransformUtil.updateCH(device.getCH()), "取消", null, "确定", new MyDialog.PositiveOnClickListener() {
                @Override
                public void onClick(View v) {
                    if (view.getInputTextMyDialog().isEmpty()) {
                        SnackBarUtil.show(v, "请输入介于1-512之间的数值");
                    } else {
                        if (isPassCH(Integer.parseInt(view.getInputTextMyDialog()))) {
                            view.dismissMyDialog();
                            device.setCH(Integer.parseInt(view.getInputTextMyDialog()));
                            unPassCHList.remove(device);
                            passCHList.add(device);
                            updateCH();
                        } else {
                            view.dismissMyDialog();
                            view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, "更改地址码", "列表中有另一台设备正在使用该\n地址码, 请尝试其它地址码。", "取消", null, "重试", new MyDialog.PositiveOnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    view.dismissMyDialog();
                                    updateCH();
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    private void sortSelectedDeviceList(ArrayList<ExtendedBluetoothDevice> selectedDeviceList) {
        unPassCHList = new ArrayList<>();
        passCHList = new ArrayList<>();
        unsetCHList = new ArrayList<>();

        for (ExtendedBluetoothDevice device : selectedDeviceList) {
            if (device.getCH() == 0) {
                unsetCHList.add(device);
            } else {
                if (isPassCH(device.getCH())) {
                    passCHList.add(device);
                } else {
                    unPassCHList.add(device);
                }
            }

        }
    }

    private boolean isPassCH(int CH) {
        for (int i = 0; i < MyApplication.getFixtures().size(); i++) {
            if (CH == MyApplication.getFixtures().get(i).getCH()) {
                return false;
            }
        }
        for (int i = 0; i < passCHList.size(); i++) {
            if (CH == passCHList.get(i).getCH()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void handleResult(ScanResult result) {
        if (result.getScanRecord() == null) {
            return;
        }
        ExtendedBluetoothDevice device = FeasyFilter(result) == null ? UserFilter(result) == null ? null : UserFilter(result) : FeasyFilter(result);
        if (device == null) {
            return;
        }

        updateScannerLiveData(result);
    }

    private void updateScannerLiveData(ScanResult result) {
        view.checkPermission();
        ScanRecord scanRecord = result.getScanRecord();
        if (scanRecord != null) {
            if (scanRecord.getBytes() != null) {
                final byte[] beaconData = nrfMeshRepository.getMeshManagerApi().getMeshBeaconData(scanRecord.getBytes());
                ExtendedBluetoothDevice device;
                final int index = indexOf(result);
                if (index == -1) {
                    if (beaconData != null) {
                        MeshBeacon beacon = nrfMeshRepository.getMeshManagerApi().getMeshBeacon(beaconData);
                        device = new ExtendedBluetoothDevice(result, beacon);
                    } else {
                        device = new ExtendedBluetoothDevice(result);
                        device.setManufacturer("USER");
                    }

                    // Update RSSI and name
                    device.setRssi(result.getRssi());
                    if (result.getDevice().getName() == null) {
                        if (result.getScanRecord().getDeviceName() != null) {
                            device.setName(result.getScanRecord().getDeviceName());
                        }
                    } else {
                        device.setName(result.getDevice().getName());
                    }
                    deviceList.add(device);
                    view.showBle(deviceList);

                } else {
                    device = deviceList.get(index);
                    // Update RSSI and name
                    device.setRssi(result.getRssi());
                    if (device.getName() == null) {
                        if (result.getDevice().getName() != null) {
                            device.setName(result.getDevice().getName());
                            deviceList.set(index, device);
                            view.showBle(deviceList);

                        } else if (result.getScanRecord().getDeviceName() != null) {
                            device.setName(result.getScanRecord().getDeviceName());
                            deviceList.set(index, device);
                            view.showBle(deviceList);

                        }
                    }
                }


            }
        }
    }

    private int indexOf(ScanResult result) {
        int i = 0;
        for (final ExtendedBluetoothDevice device : deviceList) {
            if (device.matches(result))
                return i;
            i++;
        }
        return -1;
    }


    @Override
    public void updateScene() {
        MyApplication.getScene().setFixtureNum(MyApplication.getScene().getFixtureNum() + 1);
        MyApplication.getScene().setModifiedDate(DateUtil.getTime());
        model.updateScene(MyApplication.getScene());
    }

    private ExtendedBluetoothDevice UserFilter(ScanResult result) {
        ScanRecord scanRecord = result.getScanRecord();
        if (scanRecord != null && scanRecord.getDeviceName() != null && scanRecord.getDeviceName().startsWith("``NL")) {
            ExtendedBluetoothDevice device = new ExtendedBluetoothDevice(result);
            device.setManufacturer("USER");
            return device;
        }
        return null;
    }

    private ExtendedBluetoothDevice FeasyFilter(ScanResult result) {
        ScanRecord scanRecord = result.getScanRecord();
        if (scanRecord != null && scanRecord.getServiceData() != null && scanRecord.getServiceUuids() != null) {
            if (scanRecord.getServiceUuids().get(0).toString().equals(BleMeshManager.MESH_PROVISIONING_UUID.toString())) {
                final byte[] beaconData = nrfMeshRepository.getMeshManagerApi().getMeshBeaconData(scanRecord.getBytes());
                if (beaconData != null) {
                    MeshBeacon beacon = nrfMeshRepository.getMeshManagerApi().getMeshBeacon(beaconData);
                    byte[] uuid = scanRecord.getServiceData().get(scanRecord.getServiceUuids().get(0));
                    if ((uuid != null ? uuid.length : 0) > 14) {
                        if (uuid[14] != 78) {
                            return null;
                        }
                        byte checkNum = 0;
                        for (int i = 6; i < 15; i++) {
                            checkNum = (byte) (checkNum + uuid[i]);
                        }
                        if (checkNum != uuid[15]) {
                            return null;
                        }
                        if (String.format("%08X", uuid[13]).charAt(String.format("%08X", uuid[13]).length() - 1) == '1') {
                            return null;
                        }
                        ExtendedBluetoothDevice device = new ExtendedBluetoothDevice(result, beacon);
                        device.setUuid(uuid);
                        return device;
                    }
                }
            }
        }
        return null;
    }


}

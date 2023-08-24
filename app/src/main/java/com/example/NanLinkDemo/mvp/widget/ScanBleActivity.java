package com.example.NanLinkDemo.mvp.widget;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.bean.Device;
import com.example.NanLinkDemo.bleConnect.BleMeshManager;
import com.example.NanLinkDemo.bleConnect.ExtendedBluetoothDevice;
import com.example.NanLinkDemo.bleConnect.NrfMeshRepository;
import com.example.NanLinkDemo.mvp.adapter.ScanAdapter;
import com.example.NanLinkDemo.mvp.presenter.Impl.ScanBlePresenterImpl;
import com.example.NanLinkDemo.mvp.view.ScanBleView;
import com.example.NanLinkDemo.service.BleScanService;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.ActivityRecycleviewScanBinding;
import com.feasycom.feasymesh.library.MeshBeacon;
import com.feasycom.feasymesh.library.MeshManagerApi;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import no.nordicsemi.android.support.v18.scanner.ScanRecord;
import no.nordicsemi.android.support.v18.scanner.ScanResult;


@Route(path = Constant.ACTIVITY_URL_ScanBle)
public class ScanBleActivity extends BaseActivity<ActivityRecycleviewScanBinding> implements ScanBleView, View.OnClickListener {


    private ScanAdapter adapter;
    private ScanBlePresenterImpl presenter;
    private BroadcastReceiver broadcastReceiver;
    private NrfMeshRepository nrfMeshRepository;
    private ArrayList<ExtendedBluetoothDevice> mDevices;
    private Set<String> meshProvisioningAddress = new HashSet<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        checkPermission();
        nrfMeshRepository = new NrfMeshRepository(new MeshManagerApi(this), new BleMeshManager(this));
        initToolbar();
        initRecyclerView();
        initBtn();
        initBle();
    }

    private void initBle() {
        broadcastReceiver  = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(BleScanService.scanStateAction)) {
                    boolean isScanning = intent.getBooleanExtra("isScanning", false);
                    updateRightBtn(!isScanning);
                } else if (action.equals(BleScanService.resultAction)) {
                    Bundle bundle = intent.getExtras();
                    ScanResult result = (ScanResult) bundle.get("result");

                    try {
                        if (result.getScanRecord() != null) {
                            if (result.getScanRecord().getServiceUuids() != null) {
                                if (result.getScanRecord().getServiceUuids().get(0).toString().equals(BleMeshManager.MESH_PROVISIONING_UUID.toString())) {
                                    if (!meshProvisioningAddress.contains(result.getDevice().getAddress())) {
                                        meshProvisioningAddress.add(result.getDevice().getAddress());
                                    }
                                    updateScannerLiveData(result);
                                }
                            } else {
                                if (meshProvisioningAddress.contains(result.getDevice().getAddress())) {
                                    updateScannerLiveData(result);
                                }
                            }
                        }
                    } catch (
                            Exception ex) {
                    }
//                    presenter.handleResult(result);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BleScanService.scanStateAction);
        intentFilter.addAction(BleScanService.resultAction);
        registerReceiver(broadcastReceiver, intentFilter);
        startScan();
    }

    private void updateScannerLiveData(ScanResult result) {
        checkPermission();
        ScanRecord scanRecord = result.getScanRecord();
        if (scanRecord != null) {
            if (scanRecord.getBytes() != null) {
                final byte[] beaconData = nrfMeshRepository.getMeshManagerApi().getMeshBeaconData(scanRecord.getBytes());
                if (beaconData != null) {
                    ExtendedBluetoothDevice device;

                    MeshBeacon beacon = nrfMeshRepository.getMeshManagerApi().getMeshBeacon(beaconData);
                    final int index = indexOf(result);
                    if (index == -1) {
                        device = new ExtendedBluetoothDevice(result, beacon);
                        // Update RSSI and name
                        device.setRssi(result.getRssi());
                        if (result.getDevice().getName() == null) {
                            if (result.getScanRecord().getDeviceName() != null) {
                                device.setName(result.getScanRecord().getDeviceName());
                            }
                        } else {
                            device.setName(result.getDevice().getName());
                        }
                        mDevices.add(device);
                        adapter.setData(mDevices);
                    } else {
                        device = mDevices.get(index);
                        // Update RSSI and name
                        device.setRssi(result.getRssi());
                        if (device.getName() == null) {
                            if (result.getDevice().getName() != null) {
                                device.setName(result.getDevice().getName());
                                mDevices.set(index, device);
                                adapter.setData(mDevices);
                            } else if (result.getScanRecord().getDeviceName() != null) {
                                device.setName(result.getScanRecord().getDeviceName());
                                mDevices.set(index, device);
                                adapter.setData(mDevices);
                            }
                        }
                    }
                }
            }
        }
    }

    private int indexOf(no.nordicsemi.android.support.v18.scanner.ScanResult result) {
        int i = 0;
        for (final ExtendedBluetoothDevice device : mDevices) {
            if (device.matches(result))
                return i;
            i++;
        }
        return -1;
    }

    @Override
    public void startScan() {
        Intent intent = new Intent(this, BleScanService.class);
        startService(intent);
    }


    private void initBtn() {
        binding.twoBtn.setVisibility(View.VISIBLE);
        binding.allSelected.setOnClickListener(this);
        binding.complete.setOnClickListener(this);
    }

    private void initToolbar() {
        binding.toolbar.setTitle("通过蓝牙");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
        binding.toolbar.setRightBtnIcon(R.drawable.ic_rescan);
        binding.toolbar.setRightBtnOnClickListener(this);
        binding.toolbar.setRightBtnClickable(false);

    }

    @Override
    public void setPresenter() {
        presenter = new ScanBlePresenterImpl(this);
    }

    @Override
    public void showBle(ArrayList<Device> arrayList) {
        updateAllSelectedBtn(arrayList);
        updateFinishBtn(arrayList);
//        adapter.setData(arrayList);
    }

    @Override
    public void updateAllSelectedBtn(ArrayList<Device> arrayList) {
        binding.allSelected.setClickable(!arrayList.isEmpty());
        if (arrayList.isEmpty()) {
            binding.allSelected.setBackgroundResource(R.drawable.bg_unable_btn_selected);
        } else {
            binding.allSelected.setBackgroundResource(R.drawable.bg_able_btn_selected);
            boolean allSelected = false;
            for (Device device : arrayList) {
                if (!device.isSelected()) {
                    allSelected = false;
                    break;
                }
                allSelected = true;
            }
            updateAllSelectedText(allSelected);
        }
    }

    @Override
    public void updateFinishBtn(ArrayList<Device> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).isSelected()) {
                binding.complete.setClickable(true);
                binding.complete.setBackgroundResource(R.drawable.bg_able_btn_selected);
                return;
            }
            if (i >= arrayList.size() - 1) {
                binding.complete.setClickable(false);
                binding.complete.setBackgroundResource(R.drawable.bg_unable_btn_selected);
            }
        }
    }

    @Override
    public void updateAllSelectedText(boolean allSelected) {
        if (allSelected) {
            binding.allSelected.setText("取消全选");
        } else {
            binding.allSelected.setText("全选");
        }
    }

    private void initRecyclerView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter = new ScanAdapter();
        binding.recycleView.setAdapter(adapter);
        adapter.setOnClickListener(new ScanAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.onClickSwitch(position);
            }
        });
    }


    @Override
    public void onClick(View view) {
        presenter.onClickSwitch(view);

    }

    @Override
    public void startScanAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_loading);

        // 动画循环不卡顿
        animation.setInterpolator(new LinearInterpolator());

        // 无限循环
        animation.setRepeatCount(-1);

        binding.toolbar.startRightBtnAnimation(animation);
    }

    @Override
    public void stopScanAnimation() {
        binding.toolbar.stopRightBtnAnimation();
    }

    @Override
    public void updateRightBtn(boolean able) {
        binding.toolbar.setRightBtnClickable(able);
        if (able){
            stopScanAnimation();
        }else {
            startScanAnimation();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
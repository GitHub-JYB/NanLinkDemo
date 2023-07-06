package com.example.NanLinkDemo.mvp.widget;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.bean.Device;
import com.example.NanLinkDemo.mvp.adapter.ScanAdapter;
import com.example.NanLinkDemo.mvp.presenter.Impl.ScanBlePresenterImpl;
import com.example.NanLinkDemo.mvp.view.ScanBleView;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.ActivityRecycleviewScanBinding;


import java.util.ArrayList;


@Route(path = Constant.ACTIVITY_URL_ScanBle)
public class ScanBleActivity extends BaseActivity<ActivityRecycleviewScanBinding> implements ScanBleView, View.OnClickListener {


    private ScanAdapter adapter;
    private BluetoothAdapter bluetoothAdapter;
    private ScanBlePresenterImpl presenter;
    private BluetoothLeScanner scanner;
    private ScanCallback scanCallback;
    private boolean isScanning = false;
    private ScanSettings scanSettings;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        checkPermission();
        initBle();
        initToolbar();
        initRecyclerView();
        initBtn();
        if (!checkPermission()) {
            agreePermission();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        startScan();
    }

    private void initBle() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        scanner = bluetoothAdapter.getBluetoothLeScanner();
        ScanSettings.Builder builder = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES);
            builder.setMatchMode(ScanSettings.MATCH_MODE_STICKY);
        }
        if (bluetoothAdapter.isOffloadedScanBatchingSupported()) {
            builder.setReportDelay(0L);
        }
        scanSettings = builder.build();

        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                presenter.handleResult(result);
            }
        };
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                checkPermission();
                isScanning = false;
                stopScanAnimation();
                updateRightBtnClickable(true);
                scanner.stopScan(scanCallback);
            }
        };
        startScan();
    }

    @Override
    public void startScan() {
        if (isScanning) {
            return;
        }
        checkPermission();
        isScanning = true;

        scanner.startScan(null, scanSettings, scanCallback);
        updateRightBtnClickable(false);
        startScanAnimation();
        handler.postDelayed(runnable, 20000);

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
        adapter.setData(arrayList);
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
    public void updateRightBtnClickable(boolean able) {
        binding.toolbar.setRightBtnClickable(able);
    }

}
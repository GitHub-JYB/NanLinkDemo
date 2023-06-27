package com.example.nanlinkdemo.mvp.widget;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.FeasyDevice;
import com.example.nanlinkdemo.databinding.ActivityRecycleviewScanBinding;
import com.example.nanlinkdemo.mvp.adapter.ScanAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.ScanBlePresenterImpl;
import com.example.nanlinkdemo.mvp.view.ScanBleView;
import com.example.nanlinkdemo.util.Constant;
import com.example.nanlinkdemo.util.SnackBarUtil;


import java.util.ArrayList;


@Route(path = Constant.ACTIVITY_URL_ScanBle)
public class ScanBleActivity extends BaseActivity<ActivityRecycleviewScanBinding> implements ScanBleView, View.OnClickListener {


    private ScanAdapter adapter;
    private ScanBlePresenterImpl presenter;
    private BluetoothLeScanner scanner;
    private ScanCallback scanCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initRecyclerView();
        initBtn();
        if (!checkPermission()) {
            agreePermission();
        }


    }

    @Override
    public void StartScan() {

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        if (!adapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ScanBleActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
        }

        scanner = adapter.getBluetoothLeScanner();
        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                presenter.handleResult(result);
            }
        };
        scanner.startScan(scanCallback);
        updateRightBtnClickable(false);
        startScanAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(ScanBleActivity.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ScanBleActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
                }
                scanner.stopScan(scanCallback);
                stopScanAnimation();
                updateRightBtnClickable(true);
                Log.d("TAG", "onStopScan:");

            }
        }, 20000);

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
    public void showBle(ArrayList<FeasyDevice> arrayList) {
        updateAllSelectedBtn(arrayList);
        updateFinishBtn(arrayList);
        adapter.setData(arrayList);
    }

    @Override
    public void updateAllSelectedBtn(ArrayList<FeasyDevice> arrayList) {
        binding.allSelected.setClickable(!arrayList.isEmpty());
        if (arrayList.isEmpty()) {
            binding.allSelected.setBackgroundResource(R.drawable.bg_unable_btn_selected);
        } else {
            binding.allSelected.setBackgroundResource(R.drawable.bg_able_btn_selected);
        }
    }

    @Override
    public void updateFinishBtn(ArrayList<FeasyDevice> arrayList) {
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
        StartScan();
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

    @Override
    protected void onStop() {
        super.onStop();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ScanBleActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
        }
        scanner.stopScan(scanCallback);
    }
}
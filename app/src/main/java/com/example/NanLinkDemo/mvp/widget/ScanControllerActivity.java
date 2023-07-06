package com.example.NanLinkDemo.mvp.widget;

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

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Device;
import com.example.NanLinkDemo.databinding.ActivityRecycleviewScanBinding;
import com.example.NanLinkDemo.mvp.adapter.ScanAdapter;
import com.example.NanLinkDemo.mvp.presenter.Impl.ScanControllerPresenterImpl;
import com.example.NanLinkDemo.mvp.view.ScanControllerView;
import com.example.NanLinkDemo.util.Constant;

import java.util.ArrayList;


@Route(path = Constant.ACTIVITY_URL_ScanController)
public class ScanControllerActivity extends BaseActivity<ActivityRecycleviewScanBinding> implements ScanControllerView, View.OnClickListener {


    private ScanAdapter adapter;
    private ScanControllerPresenterImpl presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initRecyclerView();
        initBtn();
//        initPermission();
//        initBle();


    }

    private void initPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(ScanControllerActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
            }

        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(ScanControllerActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

        }

    }


    private void StartScan() {

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        if (!adapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
//            return;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(ScanControllerActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
            }
//            }

        }

        BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
        ScanCallback scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                presenter.handleResult(result);
            }
        };
        scanner.startScan(scanCallback);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (ActivityCompat.checkSelfPermission(ScanControllerActivity.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
//                    return;
                        ActivityCompat.requestPermissions(ScanControllerActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
                    }
                }
                scanner.stopScan(scanCallback);

                Log.d("TAG", "onStopScan:");

            }
        }, 20000);

    }

    private void initBtn() {
        binding.oneBtn.setVisibility(View.VISIBLE);
        binding.finish.setOnClickListener(this);
    }

    private void initToolbar() {
        binding.toolbar.setTitle("通过蓝牙");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
        binding.toolbar.setRightBtnIcon(R.drawable.ic_rescan);
        binding.toolbar.setRightBtnOnClickListener(this);
    }

    @Override
    public void setPresenter() {
        presenter = new ScanControllerPresenterImpl(this);
    }

    @Override
    public void showController(ArrayList<Device> arrayList) {
        updateFinishBtn(arrayList);
        adapter.setData(arrayList);
    }

    @Override
    public void updateFinishBtn(ArrayList<Device> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).isSelected()) {
                binding.finish.setClickable(true);
                binding.finish.setBackgroundResource(R.drawable.bg_able_btn_selected);
                return;
            }
            if (i >= arrayList.size() - 1) {
                binding.finish.setClickable(false);
                binding.finish.setBackgroundResource(R.drawable.bg_unable_btn_selected);
            }
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
}
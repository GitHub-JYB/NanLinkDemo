package com.example.NanLinkDemo.mvp.widget;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.bean.FeasyDevice;
import com.example.NanLinkDemo.mvp.adapter.ScanAdapter;
import com.example.NanLinkDemo.mvp.presenter.Impl.ScanBlePresenterImpl;
import com.example.NanLinkDemo.mvp.view.ScanBleView;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.util.SnackBarUtil;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.ActivityRecycleviewScanBinding;


import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


@Route(path = Constant.ACTIVITY_URL_ScanBle)
public class ScanBleActivity extends BaseActivity<ActivityRecycleviewScanBinding> implements ScanBleView, View.OnClickListener {


    private ScanAdapter adapter;
    private BluetoothAdapter bluetoothAdapter;
    private ScanBlePresenterImpl presenter;
    private BluetoothLeScanner scanner;
    private ScanCallback scanCallback;
    private Disposable subscribe;
    private boolean isScanning = false;
    private ScanSettings scanSettings;
    private HandlerThread handlerThread;
    private Handler handler;
    private BluetoothGatt bluetoothGatt;
    private int reConnectionNum = 0;
    private int maxConnectionNum = 3;
    private BluetoothDevice device;
    private ScanBleActivity.mBluetoothGattCallback mBluetoothGattCallback;
    private UUID mServiceUUID = UUID.fromString("0000xxxx-0000-1000-8000-00805f9b34fb");
    private UUID mCharacteristicUUID = UUID.fromString("0000yyyy-0000-1000-8000-00805f9b34fb");
    private byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
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
        StartScan();
    }

    private void initBle() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            SnackBarUtil.show(binding.getRoot(), "蓝牙不可用");
            return;
        }
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
        if (!isScanning) {
            StartScan();
        }

        gatt();
    }

    private void disConnectGatt() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        bluetoothGatt.disconnect();
        bluetoothGatt.close();
    }


        private void gatt() {
        // 参数mac地址
        device = bluetoothAdapter.getRemoteDevice("");


        mBluetoothGattCallback = new mBluetoothGattCallback();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            bluetoothGatt = device.connectGatt(getBaseContext(), false, mBluetoothGattCallback, BluetoothDevice.TRANSPORT_LE);

        } else {
            bluetoothGatt = device.connectGatt(getBaseContext(), false, mBluetoothGattCallback);
        }
    }

    public class mBluetoothGattCallback extends BluetoothGattCallback {


        //连接状态回调
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);

            // 操作成功的情况下
            if (status == BluetoothGatt.GATT_SUCCESS) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 获取指定uuid的service
                        BluetoothGattService gattService = bluetoothGatt.getService(mServiceUUID);
                        // 获取到特定的服务不为空
                        if (gattService != null) {
                            // 获取指定uuid的characteristic
                            BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(mCharacteristicUUID);
                            // 获取特定特征成功
                            if (characteristic != null) {
                                // 写入你需要传递给外设的特征值(即传递给外设的信息)
                                characteristic.setValue(bytes);
                                // 通过GATT实体类将特征值写入到外设中
                                if (ActivityCompat.checkSelfPermission(ScanBleActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                bluetoothGatt.writeCharacteristic(characteristic);

                                // 如果只是需要读取外设的特征值；
                                // 通过Gatt 对象读取特定特征（characteristic）的特征值
                                bluetoothGatt.readCharacteristic(characteristic);
                            }

                        }else {
                            // 获取特定服务失败
                        }
                    }
                });

                // 判断是否连接码
                if (status == BluetoothProfile.STATE_CONNECTED) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (ActivityCompat.checkSelfPermission(ScanBleActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            bluetoothGatt.discoverServices();
                        }
                    });
                } else if (status == BluetoothProfile.STATE_DISCONNECTED) {
                    // 判断是否断开连接码
                }
            } else {
                // 异常码

                // 重连次数不大于最大重连数
                if (reConnectionNum < maxConnectionNum) {
                    // 重连次数自增
                    reConnectionNum++;
                    // 连接设备
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(ScanBleActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        bluetoothGatt = device.connectGatt(getBaseContext(), false, mBluetoothGattCallback, BluetoothDevice.TRANSPORT_LE);

                    }else {
                        bluetoothGatt = device.connectGatt(getBaseContext(),false, mBluetoothGattCallback);
                    }
                }
            }
        }

        // 服务发现回调
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
//            //设置订阅notificationGattCharacteristic值改变的通知
//            bluetoothGatt.setCharacteristicNotification(gatt.c, true);
//            //获取其对应的通知Descriptor
//            BluetoothGattDescriptor descriptor = notificationGattCharacteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
//            if (descriptor != null){
//                //设置通知值
//                descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
//                boolean descriptorResult = mBluetoothGatt.writeDescriptor(descriptor);
//            }
        }

        // 特征读取回调
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            if(status == BluetoothGatt.GATT_SUCCESS){
                // 成功读取到特征值
                characteristic.getValue();

            }
        }

        // 特征写入回调
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            if(status == BluetoothGatt.GATT_SUCCESS){
                // 成功读取到特征值
                characteristic.getValue();

            }
        }

        // 外设特效值改变回调
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            String value = String.valueOf(characteristic.getValue());
        }

        // 描述写入回调
        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }
    }



    @Override
    public void StartScan() {

//        removeHandler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }
        isScanning = true;
        if (scanner == null) {
            scanner = bluetoothAdapter.getBluetoothLeScanner();
        }


        scanner.startScan(null, scanSettings, scanCallback);
        updateRightBtnClickable(false);
        startScanAnimation();

        subscribe = Observable.timer(20000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            if (ActivityCompat.checkSelfPermission(ScanBleActivity.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(ScanBleActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
                            }
                        }
                        isScanning = false;
                        scanner.stopScan(scanCallback);
                        stopScanAnimation();
                        updateRightBtnClickable(true);
                        Log.d("TAG", "onStopScan:");
                    }
                });
    }

    @Override
    public void removeHandler() {
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
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
            boolean allSelected = false;
            for (FeasyDevice device : arrayList) {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ScanBleActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
            }
        }
//        scanner.stopScan(scanCallback);
//        removeHandler();
    }

}
package com.example.NanLinkDemo.mvp.widget;

import static android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;


import static com.example.NanLinkDemo.util.Constant.PERMISSION_REQUEST_CODE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewbinding.ViewBinding;

import android.Manifest;
import android.app.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.mvp.adapter.ThreePointAdapter;
import com.example.NanLinkDemo.ui.LoadingDialog;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.ui.SettingDialog;
import com.example.NanLinkDemo.util.SnackBarUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    protected T binding;
    private LoadingDialog loadingDialog;
    private MyDialog myDialog;
    private SettingDialog settingDialog;

    private ArrayList<String> permissionList;


    @Override
    protected void onStart() {
        super.onStart();
//        getWindow().setFlags(FLAG_KEEP_SCREEN_ON, FLAG_KEEP_SCREEN_ON);
        if (MyApplication.getOnlineUser() != null) {
            if (MyApplication.getOnlineUser().isKeepScreenOn()) {
                getWindow().addFlags(FLAG_KEEP_SCREEN_ON);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        getWindow().clearFlags(FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ARouter.getInstance().inject(this);

        fullScreen(this);

        try {
            Type type = getClass().getGenericSuperclass();
            Class clazz = (Class<T>) ((java.lang.reflect.ParameterizedType) type).getActualTypeArguments()[0];
            Method method1 = clazz.getMethod("inflate", LayoutInflater.class);
            binding = (T) method1.invoke(null, getLayoutInflater());


        } catch (Exception e) {
            e.printStackTrace();
        }


        setContentView(binding.getRoot());
        overridePendingTransition(0, 0);
    }


    /**
     * 通过设置全屏，设置状态栏透明
     */
    private void fullScreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void startLoading() {
        loadingDialog = new LoadingDialog();
        loadingDialog.show(getSupportFragmentManager(), "LoadingDialog");
    }

    public void stopLoading() {
        loadingDialog.dismiss();
    }

    public void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener) {
        myDialog = new MyDialog(type, title, message, neutralText, neutralListener);
        myDialog.show(getSupportFragmentManager(), "MyDialog");
    }

    public void showSettingDialog(ArrayList<String> settingList, ThreePointAdapter.OnClickListener listener) {
        settingDialog = new SettingDialog();
        settingDialog.setData(settingList);
        settingDialog.setOnClickListener(listener);
        settingDialog.show(getSupportFragmentManager(), "SettingDialog");
    }

    public void showMyDialog(int type, String title, String message, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener) {
        myDialog = new MyDialog(type, title, message, negativeText, negativeListener, positiveText, positiveListener);
        myDialog.show(getSupportFragmentManager(), "MyDialog");
    }

    public void showMyDialog(int type, String title, String bigSizeMessageOne, String smallSizeMessageOne, MyDialog.MessageOneOnClickListener messageOneListener, String bigSizeMessageTwo, String smallSizeMessageTwo, MyDialog.MessageTwoOnClickListener messageTwoListener, String neutralText, MyDialog.NeutralOnClickListener neutralListener) {
        myDialog = new MyDialog(type, title, bigSizeMessageOne, smallSizeMessageOne, messageOneListener, bigSizeMessageTwo, smallSizeMessageTwo, messageTwoListener, neutralText, neutralListener);
        myDialog.show(getSupportFragmentManager(), "MyDialog");
    }

    public void showMyDialog(int type, String title, String bigSizeMessage, String smallSizeMessage, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener) {
        myDialog = new MyDialog(type, title, bigSizeMessage, smallSizeMessage, negativeText, negativeListener, positiveText, positiveListener);
        myDialog.show(getSupportFragmentManager(), "MyDialog");
    }


    public String getInputTextMyDialog() {
        return myDialog.getInputText();
    }

    public void dismissMyDialog() {
        myDialog.dismiss();
    }

    public void dismissSettingDialog() {
        settingDialog.dismiss();
    }

    public boolean checkPermission() {
        permissionList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            }
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.BLUETOOTH_SCAN);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {

                permissionList.add(Manifest.permission.BLUETOOTH_ADVERTISE);

            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                permissionList.add(Manifest.permission.BLUETOOTH_CONNECT);

            }
        }
        if (permissionList.isEmpty()){
            return true;
        }else {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[]{}), PERMISSION_REQUEST_CODE);
            return false;
        }
    }


    public boolean checkBle() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        if (adapter.isEnabled()) {
            return true;
        }
        tipBle();
        return false;
    }

    public boolean checkLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        tipLocation();
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = permissions.length - 1; i >= 0; i--) {
                if (grantResults[i] == -1) {
                    switch (permissions[i]) {
                        case Manifest.permission.BLUETOOTH_SCAN:
                        case Manifest.permission.BLUETOOTH_ADVERTISE:
                        case Manifest.permission.BLUETOOTH_CONNECT:
                            tipBle();
                            return;
                        case Manifest.permission.ACCESS_COARSE_LOCATION:
                        case Manifest.permission.ACCESS_FINE_LOCATION:
                            tipLocation();
                            return;
                    }
                }
            }
        }


    }

    private void tipBle() {
        SnackBarUtil.show(binding.getRoot(), "NANLINK需要使用蓝牙连接灯具，请打开蓝牙权限");
    }

    private void tipLocation() {
        SnackBarUtil.show(binding.getRoot(), "NANLINK需要使用位置信息连接灯具，请打开位置信息权限");
    }
}

package com.example.nanlinkdemo.mvp.widget;

import static android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewbinding.ViewBinding;

import android.Manifest;
import android.app.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.mvp.adapter.ThreePointAdapter;
import com.example.nanlinkdemo.ui.LoadingDialog;
import com.example.nanlinkdemo.ui.MyDialog;
import com.example.nanlinkdemo.ui.SettingDialog;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    protected T binding;
    private LoadingDialog loadingDialog;
    private MyDialog myDialog;
    private SettingDialog settingDialog;

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
        if (myDialog.getType() == MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_Remark) {
            return myDialog.getInputTextRemark();
        } else {
            return myDialog.getInputText();
        }
    }

    public void dismissMyDialog() {
        myDialog.dismiss();
    }

    public void dismissSettingDialog() {
        settingDialog.dismiss();
    }

    public void agreePermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(BaseActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(BaseActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
            }

        }

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
                ActivityCompat.requestPermissions(BaseActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
            }

        }
    }

    public boolean checkPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        } else {
            return true;
        }
    }


    public boolean checkBle() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        return adapter.isEnabled();
    }

    public boolean checkLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

}
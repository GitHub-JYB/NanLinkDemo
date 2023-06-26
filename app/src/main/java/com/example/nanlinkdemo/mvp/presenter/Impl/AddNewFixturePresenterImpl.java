package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.DB.bean.Controller;
import com.example.nanlinkdemo.bean.AddFixtureType;
import com.example.nanlinkdemo.mvp.model.Impl.AddNewFixtureModelImpl;
import com.example.nanlinkdemo.mvp.presenter.AddNewFixturePresenter;
import com.example.nanlinkdemo.mvp.view.AddNewFixtureView;
import com.example.nanlinkdemo.ui.MyDialog;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class AddNewFixturePresenterImpl implements AddNewFixturePresenter {
    private final AddNewFixtureView view;
    private final AddNewFixtureModelImpl model;

    public AddNewFixturePresenterImpl(AddNewFixtureView view) {
        this.view = view;
        this.model = new AddNewFixtureModelImpl(this);
    }

    @Override
    public void getListDataFromView() {
        model.getListData();
    }

    @Override
    public void onClickSwitch(int position) {
        switch (position) {
            case 0:
                if (!view.checkPermission()) {
                    view.agreePermission();
                    break;
                }
                if (!view.checkBle()) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    (Activity)view.startActivityForResult(intent, 1);
                    break;
                }
                if (!view.checkLocation()) {
                    break;
                }
                ARouter.getInstance().build(Constant.ACTIVITY_URL_ScanBle).navigation();
                break;
            case 1:
//                model.getControllerList();
                //测试，所以先跳过控制器的检测直接添加2.4G设备
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Add24GFixture).navigation();
                break;
        }
    }

    @Override
    public void receiveListData(ArrayList<AddFixtureType> arrayList) {
        view.showAddType(arrayList);
    }

    @Override
    public void receiveControllerList(List<Controller> controllers) {
        if (controllers.isEmpty()) {
            view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, "信号控制器", "需要连接信号控制器才能\n使用该选项", "取消", null, "连接", new MyDialog.PositiveOnClickListener() {
                @Override
                public void onClick(View v) {
                    view.dismissMyDialog();
                    // 跳转到信号控制器列表界面
                    ARouter.getInstance().build(Constant.ACTIVITY_URL_Controller).navigation();
                }
            });
        } else {
            // 跳转到2.4G添加界面
            ARouter.getInstance().build(Constant.ACTIVITY_URL_Add24GFixture).navigation();
        }
    }
}

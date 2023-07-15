package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.view.View;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.bean.DeviceMessage;
import com.example.NanLinkDemo.mvp.model.Impl.ControlModeModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ControlModePresenter;
import com.example.NanLinkDemo.mvp.view.ControlModeView;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.util.SnackBarUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class ControlModePresenterImpl implements ControlModePresenter {
    private final ControlModeView view;
    private final ControlModeModelImpl model;
    private ArrayList<Fixture> noGroupFixtureList = new ArrayList<>();
    private ArrayList<Fixture> hasGroupFixtureList = new ArrayList<>();
    private ArrayList<FixtureGroup> hasFixtureGroupList = new ArrayList<>();

    public ControlModePresenterImpl(ControlModeView view) {
        this.view = view;
        this.model = new ControlModeModelImpl(this);
    }

    @Override
    public void updateDim(int position, String dim) {
        if (position < hasFixtureGroupList.size()){
            FixtureGroup fixtureGroup = hasFixtureGroupList.get(position);
            String data = fixtureGroup.getData();
            Gson gson = new Gson();
            DeviceDataMessage.Data deviceData = gson.fromJson(data, DeviceDataMessage.Data.class);
            deviceData.setDimItem(dim);
            gson = new GsonBuilder().setPrettyPrinting().create();
            data = gson.toJson(deviceData);
            fixtureGroup.setData(data);
            model.updateFixtureGroup(fixtureGroup);
        }else {
            Fixture fixture = noGroupFixtureList.get(position - hasFixtureGroupList.size());
            String data = fixture.getData();
            Gson gson = new Gson();
            DeviceDataMessage.Data deviceData = gson.fromJson(data, DeviceDataMessage.Data.class);
            deviceData.setDimItem(dim);
            gson = new GsonBuilder().setPrettyPrinting().create();
            data = gson.toJson(deviceData);
            fixture.setData(data);
            model.updateFixture(fixture);
        }
    }

    @Override
    public void getDataList() {
        for (Fixture fixture : MyApplication.getFixtures()) {
            if (fixture.getFixtureGroupName().equals("")) {
                noGroupFixtureList.add(fixture);
            } else {
                hasGroupFixtureList.add(fixture);
            }
        }
        for (FixtureGroup fixtureGroup : MyApplication.getFixtureGroups()) {
            if (fixtureGroup.getFixtureNum() != 0) {
                hasFixtureGroupList.add(fixtureGroup);
            }
        }
        view.showControls(hasFixtureGroupList, hasGroupFixtureList, noGroupFixtureList);
    }

    @Override
    public void clickDelayTime(int position, String delayTime) {
        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_Data, "渐变", "请输入一个介于0和10之间的数值", delayTime, "取消", null, "确定", new MyDialog.PositiveOnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.getInputTextMyDialog().isEmpty() || Integer.parseInt(view.getInputTextMyDialog()) > 10){
                    SnackBarUtil.show(v, "请输入一个介于0和10之间的数值");
                    view.clearInputText();
                }else {
                    if (view.getInputTextMyDialog().equals(delayTime)){
                        view.dismissMyDialog();
                        return;
                    }
                    if (position < hasFixtureGroupList.size()){
                        FixtureGroup fixtureGroup = hasFixtureGroupList.get(position);
                        Gson gson = new Gson();
                        DeviceDataMessage.Data deviceData = gson.fromJson(fixtureGroup.getData(), DeviceDataMessage.Data.class);
                        deviceData.setDimDelay(view.getInputTextMyDialog());
                        gson = new GsonBuilder().setPrettyPrinting().create();
                        String data = gson.toJson(deviceData);
                        fixtureGroup.setData(data);
                        view.showControls(hasFixtureGroupList, hasGroupFixtureList, noGroupFixtureList);
                        model.updateFixtureGroup(fixtureGroup);
                    }else {
                        Fixture fixture = noGroupFixtureList.get(position - hasFixtureGroupList.size());
                        Gson gson = new Gson();
                        DeviceDataMessage.Data deviceData = gson.fromJson(fixture.getData(), DeviceDataMessage.Data.class);
                        deviceData.setDimDelay(view.getInputTextMyDialog());
                        gson = new GsonBuilder().setPrettyPrinting().create();
                        String data = gson.toJson(deviceData);
                        fixture.setData(data);
                        view.showControls(hasFixtureGroupList, hasGroupFixtureList, noGroupFixtureList);
                        model.updateFixture(fixture);
                    }
                    view.dismissMyDialog();
                }
            }
        });
    }

    @Override
    public void clickData(int position, String dim) {
        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_Data, "亮度", "请输入一个介于0和100之间的数值", dim, "取消", null, "确定", new MyDialog.PositiveOnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.getInputTextMyDialog().isEmpty() || Integer.parseInt(view.getInputTextMyDialog()) > 100){
                    SnackBarUtil.show(v, "请输入一个介于0和100之间的数值");
                    view.clearInputText();
                }else {
                    if (view.getInputTextMyDialog().equals(dim)){
                        view.dismissMyDialog();
                        return;
                    }
                    if (position < hasFixtureGroupList.size()){
                        FixtureGroup fixtureGroup = hasFixtureGroupList.get(position);
                        Gson gson = new Gson();
                        DeviceDataMessage.Data deviceData = gson.fromJson(fixtureGroup.getData(), DeviceDataMessage.Data.class);
                        deviceData.setDimItem(view.getInputTextMyDialog());
                        gson = new GsonBuilder().setPrettyPrinting().create();
                        String data = gson.toJson(deviceData);
                        fixtureGroup.setData(data);
                        view.showControls(hasFixtureGroupList, hasGroupFixtureList, noGroupFixtureList);
                        model.updateFixtureGroup(fixtureGroup);
                    }else {
                        Fixture fixture = noGroupFixtureList.get(position - hasFixtureGroupList.size());
                        Gson gson = new Gson();
                        DeviceDataMessage.Data deviceData = gson.fromJson(fixture.getData(), DeviceDataMessage.Data.class);
                        deviceData.setDimItem(view.getInputTextMyDialog());
                        gson = new GsonBuilder().setPrettyPrinting().create();
                        String data = gson.toJson(deviceData);
                        fixture.setData(data);
                        view.showControls(hasFixtureGroupList, hasGroupFixtureList, noGroupFixtureList);
                        model.updateFixture(fixture);
                    }
                    view.dismissMyDialog();
                }
            }
        });

    }
}

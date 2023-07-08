package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.bean.Add24GFixture;
import com.example.NanLinkDemo.mvp.adapter.Add24GAdapter;
import com.example.NanLinkDemo.mvp.model.Impl.Add24GFixtureModelImpl;
import com.example.NanLinkDemo.mvp.presenter.Add24GFixturePresenter;
import com.example.NanLinkDemo.mvp.view.Add24GFixtureView;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.util.DateUtil;

import java.util.ArrayList;

public class Add24GFixturePresenterImpl implements Add24GFixturePresenter {
    private final Add24GFixtureView view;
    private final Add24GFixtureModelImpl model;
    private ArrayList<Add24GFixture> add24GFixtures;

    public Add24GFixturePresenterImpl(Add24GFixtureView view) {
        this.view = view;
        this.model = new Add24GFixtureModelImpl(this);
    }

    @Override
    public void getDataFromView() {
        model.getBoxViewData();
        model.getListData();
    }

    @Override
    public void setDataToView(ArrayList<Add24GFixture> add24GFixtures) {
        this.add24GFixtures = add24GFixtures;
        view.setData(add24GFixtures);
    }

    @Override
    public void setDataToBoxView(Integer boxViewId, String title, ArrayList<String> dataList, int checkIndex) {
        view.updateBoxView(boxViewId, title, dataList, checkIndex);
    }



    @Override
    public void onClick() {
        String deviceId = "";
        checkFixtureList(view.getFixtureArrayList());
        switch (view.getCheckIndexType()){
            case 0:
                deviceId = "FFFFFA";
                break;
            case 1:
                switch (view.getCheckIndexCCTRange()){
                    case 0:
                        deviceId = "FFFFFB";
                        break;
                    case 1:
                        deviceId = "FFFFFC";
                        break;
                    case 2:
                        deviceId = "FFFFFD";
                        break;
                    case 3:
                        deviceId = "FFFFFE";
                        break;
                }
            case 2:
                switch (view.getCheckIndexGM()){
                    case 0:
                        deviceId = "FFFFFF";
                        break;
                    case 1:
                        deviceId = "FFFFFG";
                }
        }
        for (Add24GFixture add24GFixture : add24GFixtures){
            Fixture fixture = new Fixture(MyApplication.getOnlineUser().getEmail(), MyApplication.getScene().getName(), add24GFixture.getName(), add24GFixture.getCH(), deviceId, "2.4G", "");
            MyApplication.getFixtures().add(fixture);
            model.addFixture(fixture);

        }
        Scene scene = MyApplication.getScene();
        scene.setFixtureNum(MyApplication.getFixtures().size());
        scene.setModifiedDate(DateUtil.getTime());
        MyApplication.setScene(scene);
        model.updateScene(scene);
        ARouter.getInstance().build(Constant.ACTIVITY_URL_Scene).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();

    }

    // 检查添加是设备是否重复的CH
    private void checkFixtureList(ArrayList<Add24GFixture> fixtureArrayList) {

    }

    @Override
    public void setListener() {
        view.setListViewOnOutRangeListener(new Add24GAdapter.OnOutRangeListener() {
            @Override
            public void onOutRange() {
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "", "请输入介于 1 - 512\n之间的数值", "确定", null);
            }
        });
        view.setListViewOnCheckCompleteListener(new Add24GAdapter.OnCheckCompleteListener() {
            @Override
            public void CheckComplete(boolean complete) {
                view.setFinishBtn(complete);
            }
        });
    }


}

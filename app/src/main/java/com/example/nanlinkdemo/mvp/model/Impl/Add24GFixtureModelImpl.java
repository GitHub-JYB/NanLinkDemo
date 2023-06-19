package com.example.nanlinkdemo.mvp.model.Impl;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.mvp.model.Add24GFixtureModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.Add24GFixturePresenterImpl;
import com.example.nanlinkdemo.ui.BoxView;

import java.util.ArrayList;

public class Add24GFixtureModelImpl implements Add24GFixtureModel {
    private final Add24GFixturePresenterImpl presenter;

    private  ArrayList<String> arrayList = new ArrayList<>();


    public Add24GFixtureModelImpl(Add24GFixturePresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getListData() {
        ArrayList<Add24GFixture> add24GFixtures = new ArrayList<>();
        add24GFixtures.add(new Add24GFixture("",""));
        presenter.setDataToView(add24GFixtures);
    }

    @Override
    public void getBoxViewData(BoxView boxView) {
        switch (boxView.getId()){
            case R.id.type:
                arrayList = new ArrayList<>();
                arrayList.add("单色温");
                arrayList.add("双色温");
                arrayList.add("全彩");
                presenter.setDataToBoxView(boxView, "灯具类型", arrayList, 0);
                break;
            case R.id.cctRange:
                arrayList = new ArrayList<>();
                arrayList.add("3200K\n-\n5600K");
                arrayList.add("2700K\n-\n6500K");
                arrayList.add("3200K\n-\n7500K");
                arrayList.add("2700K\n-\n7500K");
                presenter.setDataToBoxView(boxView,"色温范围", arrayList, 0);
                break;
            case R.id.GM:
                arrayList = new ArrayList<>();
                arrayList.add("可调");
                arrayList.add("不可调");
                presenter.setDataToBoxView(boxView, "红绿平衡", arrayList, 1);
        }
    }
}

package com.example.nanlinkdemo.mvp.model.Impl;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.DataBase.MyDataBase;
import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.mvp.model.Add24GFixtureModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.Add24GFixturePresenterImpl;
import com.example.nanlinkdemo.ui.BoxView;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Add24GFixtureModelImpl implements Add24GFixtureModel {
    private final Add24GFixturePresenterImpl presenter;

    private ArrayList<String> arrayList = new ArrayList<>();


    public Add24GFixtureModelImpl(Add24GFixturePresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getListData() {
        ArrayList<Add24GFixture> add24GFixtures = new ArrayList<>();
        add24GFixtures.add(new Add24GFixture("", ""));
        presenter.setDataToView(add24GFixtures);
    }

    @Override
    public void getBoxViewData() {

                    arrayList = new ArrayList<>();
                    arrayList.add("单色温");
                    arrayList.add("双色温");
                    arrayList.add("全彩");
                    presenter.setDataToBoxView(R.id.type, "灯具类型", arrayList, 0);

                    arrayList = new ArrayList<>();
                    arrayList.add("3200K\n-\n5600K");
                    arrayList.add("2700K\n-\n6500K");
                    arrayList.add("3200K\n-\n7500K");
                    arrayList.add("2700K\n-\n7500K");
                    presenter.setDataToBoxView(R.id.cctRange, "色温范围", arrayList, 0);

                    arrayList = new ArrayList<>();
                    arrayList.add("可调");
                    arrayList.add("不可调");
                    presenter.setDataToBoxView(R.id.GM, "红绿平衡", arrayList, 1);
    }

    @Override
    public void addFixture(Fixture fixture) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .insert(fixture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });
    }
}

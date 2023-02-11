package com.example.nanlinkdemo.mvp.model.Impl;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.DataBase.MyDataBase;
import com.example.nanlinkdemo.DB.bean.Controller;
import com.example.nanlinkdemo.bean.AddFixtureType;
import com.example.nanlinkdemo.mvp.model.AddNewFixtureModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.AddNewFixturePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddNewFixtureModelImpl implements AddNewFixtureModel {
    private final AddNewFixturePresenterImpl presenter;

    public AddNewFixtureModelImpl(AddNewFixturePresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getListData() {
        ArrayList<AddFixtureType> arrayList = new ArrayList<AddFixtureType>();
        arrayList.add(new AddFixtureType("通过蓝牙", "直接连接"));
        arrayList.add(new AddFixtureType("通过2.4G","需要信号控制盒"));
        presenter.receiveListData(arrayList);
    }

    @Override
    public void getControllerList() {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getControllerDao()
                .getAllControllerInfo(MyApplication.getOnlineUser().getEmail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Controller>>() {
                    @Override
                    public void accept(List<Controller> controllers) throws Exception {
                        presenter.receiveControllerList(controllers);
                    }
                });
    }
}

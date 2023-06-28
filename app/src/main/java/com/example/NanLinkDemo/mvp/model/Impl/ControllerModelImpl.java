package com.example.NanLinkDemo.mvp.model.Impl;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.Controller;
import com.example.NanLinkDemo.mvp.model.ControllerModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.ControllerPresenterImpl;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ControllerModelImpl implements ControllerModel {
    private final ControllerPresenterImpl presenter;

    public ControllerModelImpl(ControllerPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getListData() {
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

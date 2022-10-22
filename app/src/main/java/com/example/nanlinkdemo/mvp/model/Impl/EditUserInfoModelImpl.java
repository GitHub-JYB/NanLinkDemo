package com.example.nanlinkdemo.mvp.model.Impl;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.DataBase.MyDataBase;
import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.bean.EditUser;
import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.mvp.model.EditUserInfoModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.EditUserInfoPresenterImpl;
import com.example.nanlinkdemo.util.ApiClient;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class EditUserInfoModelImpl implements EditUserInfoModel {
    private final EditUserInfoPresenterImpl presenter;

    public EditUserInfoModelImpl(EditUserInfoPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void editInfo(String token, EditUser user) {
        ApiClient.getService(ApiClient.BASE_URL)
                .editUserInfo(token, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) throws Exception {
                        presenter.sendMesToView(message);
                    }
                });


    }

    @Override
    public void updateUser(User user) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getUserDao()
                .updateInfo(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }
}

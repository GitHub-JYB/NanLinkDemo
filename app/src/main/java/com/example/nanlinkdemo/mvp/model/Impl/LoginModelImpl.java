package com.example.nanlinkdemo.mvp.model.Impl;


import android.app.Activity;

import com.example.nanlinkdemo.bean.LoginUser;
import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.mvp.model.LoginModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.LoginPresenterImpl;
import com.example.nanlinkdemo.util.ApiClient;
import com.example.nanlinkdemo.util.SnackBarUtil;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class LoginModelImpl implements LoginModel {
    private final LoginPresenterImpl presenter;

    public LoginModelImpl(LoginPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void login(String email, String password) {
        LoginUser loginUser = new LoginUser();
        loginUser.setEmail(email);
        loginUser.setPassword(password);
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJ1aWQiOiIxOCJ9.Za_T1U93e9ap5IdY3r6ZOlFwpZ4WMd6Gm-GDtHirdcE";
        ApiClient.getService(ApiClient.BASE_URL)
                .login(loginUser, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) throws Exception {
                        presenter.sendMesToView(message);
                    }
                });

    }



}

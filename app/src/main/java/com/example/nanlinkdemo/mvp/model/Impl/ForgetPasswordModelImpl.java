package com.example.nanlinkdemo.mvp.model.Impl;


import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.mvp.model.ForgetPasswordModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.ForgetPasswordPresenterImpl;
import com.example.nanlinkdemo.util.ApiClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ForgetPasswordModelImpl implements ForgetPasswordModel {
    private final ForgetPasswordPresenterImpl presenter;

    public ForgetPasswordModelImpl(ForgetPasswordPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getCode(String email, int code_type) {
        ApiClient.getService(ApiClient.BASE_URL)
                .getCode(email, code_type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) throws Exception {
                        presenter.sendMesToView(message, ApiClient.Function_GetCode);
                    }
                });
    }

    @Override
    public void verifyCode(String email, String code) {
        ApiClient.getService(ApiClient.BASE_URL)
                .verifyCode(email, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) throws Exception {
                        presenter.sendMesToView(message, ApiClient.Function_VerifyCode);
                    }
                });
    }
}

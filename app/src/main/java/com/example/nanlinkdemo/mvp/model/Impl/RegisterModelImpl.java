package com.example.nanlinkdemo.mvp.model.Impl;


import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.bean.RegisterUser;
import com.example.nanlinkdemo.mvp.model.RegisterModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.RegisterPresenterImpl;
import com.example.nanlinkdemo.util.ApiClient;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class RegisterModelImpl implements RegisterModel {
    private final RegisterPresenterImpl presenter;

    public RegisterModelImpl(RegisterPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void register(String email, String password, String code, String nickName) {
        RegisterUser registerUser = new RegisterUser();
        registerUser.setEmail(email);
        registerUser.setPassword(password);
        registerUser.setCode(code);
        registerUser.setNickName(nickName);
        ApiClient.getService(ApiClient.BASE_URL)
                .register(registerUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) throws Exception {
                        presenter.sendMesToView(message, ApiClient.Function_Register);
                    }
                });
    }

    @Override
    public void getCode(String email, int code_register) {

        ApiClient.getService(ApiClient.BASE_URL)
                .getCode(email, code_register)
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
    public void startCountDown() {
        Observable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        presenter.sendProgressToView(aLong);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        presenter.endCountDown();
                    }
                })
                .subscribe();
    }


}

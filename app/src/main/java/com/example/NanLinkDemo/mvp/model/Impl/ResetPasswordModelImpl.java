package com.example.NanLinkDemo.mvp.model.Impl;


import com.example.NanLinkDemo.bean.Message;
import com.example.NanLinkDemo.mvp.model.ForgetPasswordModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.ResetPasswordPresenterImpl;
import com.example.NanLinkDemo.util.ApiClient;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ResetPasswordModelImpl implements ForgetPasswordModel {
    private final ResetPasswordPresenterImpl presenter;

    public ResetPasswordModelImpl(ResetPasswordPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getCode(String email, int code_type) {
        Disposable disposable = ApiClient.getService(ApiClient.BASE_URL)
                .getCode(email, code_type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) throws Exception {
                        presenter.sendMesToView(message, ApiClient.Function_GetCode);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        presenter.showWarnToView();
                    }
                });
    }

    @Override
    public void verifyCode(String email, String code) {
        Disposable disposable = ApiClient.getService(ApiClient.BASE_URL)
                .verifyCode(email, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) throws Exception {
                        presenter.sendMesToView(message, ApiClient.Function_VerifyCode);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        presenter.showWarnToView();
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
                        presenter.updateProgressCountToView(aLong);

                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        presenter.endCountToView();
                    }
                })
                .subscribe();
    }
}

package com.example.nanlinkdemo.mvp.model.Impl;

import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.bean.ResetPasswordUser;
import com.example.nanlinkdemo.mvp.presenter.Impl.ForgetPasswordResetPresenterImpl;
import com.example.nanlinkdemo.util.ApiClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ForgetPasswordResetModelImpl {
    private final ForgetPasswordResetPresenterImpl presenter;

    public ForgetPasswordResetModelImpl(ForgetPasswordResetPresenterImpl presenter) {
        this.presenter = presenter;
    }

    public void resetPassword(String email, String password, String code) {
        ResetPasswordUser resetPasswordUser = new ResetPasswordUser();
        resetPasswordUser.setEmail(email);
        resetPasswordUser.setPassword(password);
        resetPasswordUser.setCode(code);
        ApiClient.getService(ApiClient.BASE_URL)
                .resetPassword(resetPasswordUser)
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

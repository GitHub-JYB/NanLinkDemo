package com.example.NanLinkDemo.mvp.model.Impl;

import com.example.NanLinkDemo.bean.Message;
import com.example.NanLinkDemo.bean.ResetPasswordUser;
import com.example.NanLinkDemo.mvp.presenter.Impl.ResetPasswordResetPresenterImpl;
import com.example.NanLinkDemo.util.ApiClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ResetPasswordResetModelImpl {
    private final ResetPasswordResetPresenterImpl presenter;

    public ResetPasswordResetModelImpl(ResetPasswordResetPresenterImpl presenter) {
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

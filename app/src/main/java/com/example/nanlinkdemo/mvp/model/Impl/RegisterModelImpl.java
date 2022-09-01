package com.example.nanlinkdemo.mvp.model.Impl;


import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.bean.RegisterUser;
import com.example.nanlinkdemo.mvp.model.RegisterModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.RegisterPresenterImpl;
import com.example.nanlinkdemo.util.ApiClient;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Message mes) {
                        presenter.sendMesToView(mes);
                    }
                });
    }


}

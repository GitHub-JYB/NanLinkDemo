package com.example.NanLinkDemo.mvp.model.Impl;


import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.User;
import com.example.NanLinkDemo.bean.LoginUser;
import com.example.NanLinkDemo.bean.Message;
import com.example.NanLinkDemo.mvp.model.LoginModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.LoginPresenterImpl;
import com.example.NanLinkDemo.util.ApiClient;


import java.util.List;

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
        ApiClient.getService(ApiClient.BASE_URL)
                .login(loginUser)
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
    public void addUser(User user) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getUserDao()
                .insert(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                    }
                });
    }

    @Override
    public void queryEmail(String email) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getUserDao()
                .getUserFromEmailInfo(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        presenter.receiveUser(users);
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

    @Override
    public void getLastUser() {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getUserDao()
                .getUserFromTypeInfo("lastUser")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        presenter.receiveLastUser(users);
                    }
                });
    }


}

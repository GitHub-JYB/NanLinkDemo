package com.example.NanLinkDemo.mvp.model.Impl;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.Device;
import com.example.NanLinkDemo.DB.bean.User;
import com.example.NanLinkDemo.bean.Message;
import com.example.NanLinkDemo.mvp.model.SplashModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.SplashPresenterImpl;
import com.example.NanLinkDemo.util.ApiClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashModelImpl implements SplashModel {

    private final SplashPresenterImpl presenter;

    public SplashModelImpl(SplashPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void startCountDown() {
        /**
         * 定时1秒结束欢迎界面
         */
        Disposable disposable = Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        presenter.endCountDown();
                    }
                });
    }

    @Override
    public void getOnlineUser() {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getUserDao()
                .getUserFromTypeInfo("online")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        presenter.receiveOnlineUser(users);

                    }
                });
    }

    @Override
    public void getUserInfo(String token) {
        Disposable disposable = ApiClient.getService(ApiClient.BASE_URL)
                .getUserInfo(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) throws Exception {
                        presenter.sendMesToView(message);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        presenter.gotoMainActivity();
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
    public void getDeviceList() {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getDeviceListDao()
                .getDeviceListInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Device>>() {
                    @Override
                    public void accept(List<Device> deviceLists) throws Exception {
                        presenter.receiveDeviceList(deviceLists);
                    }
                });
    }
}

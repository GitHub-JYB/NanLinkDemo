package com.example.NanLinkDemo.mvp.model.Impl;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.User;
import com.example.NanLinkDemo.bean.EditUser;
import com.example.NanLinkDemo.bean.Message;
import com.example.NanLinkDemo.mvp.model.EditUserInfoModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.EditUserInfoPresenterImpl;
import com.example.NanLinkDemo.util.ApiClient;

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
        Disposable disposable = ApiClient.getService(ApiClient.BASE_URL)
                .editUserInfo(token, user)
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
                        presenter.showWarnToView();
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

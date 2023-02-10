package com.example.nanlinkdemo.mvp.model.Impl;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.DataBase.MyDataBase;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.mvp.model.ManageSceneModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.ManageScenePresenterImpl;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ManageSceneModelImpl implements ManageSceneModel {
    private final ManageScenePresenterImpl presenter;

    public ManageSceneModelImpl(ManageScenePresenterImpl presenter) {
        this.presenter = presenter;

    }

    @Override
    public void queryScene() {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .getSceneInfoFromSceneGroup(MyApplication.getOnlineUser().getEmail(), "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Scene>>() {
                    @Override
                    public void accept(List<Scene> scenes) throws Exception {
                        presenter.receiveQueryScene(scenes);
                    }
                });
    }

    @Override
    public void updateScene(Scene scene) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .updateSceneInfo(scene)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                    }
                });
    }

    @Override
    public void updateSceneGroup(SceneGroup sceneGroup) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneGroupDao()
                .updateSceneInfo(sceneGroup)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                    }
                });
    }
}

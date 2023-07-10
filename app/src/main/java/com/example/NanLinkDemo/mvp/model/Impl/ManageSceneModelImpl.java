package com.example.NanLinkDemo.mvp.model.Impl;

import android.util.Log;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;
import com.example.NanLinkDemo.mvp.model.ManageSceneModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.ManageScenePresenterImpl;

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
    public void queryScene(String sceneGroupName) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .getSceneInfoFromSceneGroup(MyApplication.getOnlineUser().getEmail(), sceneGroupName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Scene>>() {
                    @Override
                    public void accept(List<Scene> scenes) throws Exception {
                        presenter.receiveQueryScene(scenes, sceneGroupName);
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

    @Override
    public void getSceneGroup(String sceneGroupName) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneGroupDao()
                .getSceneGroupInfo(MyApplication.getOnlineUser().getEmail(), sceneGroupName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SceneGroup>>() {
                    @Override
                    public void accept(List<SceneGroup> sceneGroups) throws Exception {
                        presenter.receiveSceneGroup(sceneGroups);
                    }
                });
    }

}

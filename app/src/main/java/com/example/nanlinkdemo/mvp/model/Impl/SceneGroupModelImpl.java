package com.example.nanlinkdemo.mvp.model.Impl;

import static com.example.nanlinkdemo.bean.Menu.TYPE_ITEM;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.DataBase.MyDataBase;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.SceneGroupModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.SceneGroupPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SceneGroupModelImpl implements SceneGroupModel {
    private final SceneGroupPresenterImpl presenter;

    public SceneGroupModelImpl(SceneGroupPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getMenu() {
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        menuArrayList.add(new Menu(R.drawable.ic_manage,"管理群组", TYPE_ITEM));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(R.drawable.ic_add,"创建场景", TYPE_ITEM));
        menuArrayList.add(new Menu(R.drawable.ic_sort,"排序", TYPE_ITEM));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(R.drawable.ic_set,"设置", TYPE_ITEM));
        presenter.receiveMenu(menuArrayList);
    }

    @Override
    public void getSceneList(String name) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .getOnlineSceneInfo(MyApplication.getOnlineUser().getEmail(), name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Scene>>() {
                    @Override
                    public void accept(List<Scene> scenes) throws Exception {
                        presenter.receiveSceneList(scenes);
                    }
                });
    }

    @Override
    public void getThreePointMenu(int scenePosition) {
        ArrayList<String> threePointList = new ArrayList<>();
        threePointList.add("设置");
        threePointList.add("重命名");
        threePointList.add("编辑备注");
        threePointList.add("删除");
        threePointList.add("取消");
        presenter.receiveThreePointMenu(threePointList, scenePosition);
    }

    @Override
    public void deleteScene(Scene scene) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .deleteInfo(scene)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        presenter.updateSceneListToView();
                    }
                });
    }

    @Override
    public void queryScene(String sceneName) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .getSceneInfo(MyApplication.getOnlineUser().getEmail(), sceneName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Scene>>() {
                    @Override
                    public void accept(List<Scene> scenes) throws Exception {
                        presenter.switchQuerySceneResult(sceneName, scenes);
                    }
                });
    }

    @Override
    public void addScene(Scene scene) {

        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .insert(scene)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        presenter.updateSceneListToView();
                    }
                });
    }

    @Override
    public void querySceneGroup(String sceneGroupName) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneGroupDao()
                .getSceneGroupInfo(MyApplication.getOnlineUser().getEmail(), sceneGroupName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<SceneGroup>>() {
                    @Override
                    public void accept(List<SceneGroup> sceneGroups) throws Exception {
                        presenter.updateSceneGroup(sceneGroups);

                    }
                });
    }

    @Override
    public void updateSceneGroup(SceneGroup sceneGroup) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneGroupDao()
                .updateSceneInfo(sceneGroup)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }
}

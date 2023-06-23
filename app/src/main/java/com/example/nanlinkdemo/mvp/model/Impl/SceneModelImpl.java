package com.example.nanlinkdemo.mvp.model.Impl;

import static com.example.nanlinkdemo.bean.Menu.TYPE_ITEM_gray_bg;
import static com.example.nanlinkdemo.bean.Menu.TYPE_ITEM_nav_bg;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.DataBase.MyDataBase;
import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.DB.bean.FixtureGroup;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.SceneModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.ScenePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SceneModelImpl implements SceneModel {
    private final ScenePresenterImpl presenter;

    public SceneModelImpl(ScenePresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getMenu() {
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        menuArrayList.add(new Menu(0,"", 0, TYPE_ITEM_nav_bg));
        menuArrayList.add(new Menu(R.drawable.ic_add,"添加设备", 0, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu(R.drawable.ic_add,"添加设备群组", 0, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu(R.drawable.ic_sort,"排序", 0, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(R.drawable.ic_controller,"信号控制器", 0, R.drawable.ic_full_battery, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(R.drawable.ic_console,"控台模式", 0, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu(R.drawable.ic_preset,"预设", 0, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(R.drawable.ic_restart,"重新开始特效", 0, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(R.drawable.ic_set,"设置", 0, TYPE_ITEM_gray_bg));
        presenter.receiveMenu(menuArrayList);
    }

    @Override
    public void getSortList() {
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        menuArrayList.add(new Menu(R.drawable.ic_back,"", 0, TYPE_ITEM_nav_bg));
        menuArrayList.add(new Menu(R.drawable.ic_sort, "排序", 0, TYPE_ITEM_nav_bg));
        menuArrayList.add(new Menu(R.drawable.ic_unselected,"地址码", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu(R.drawable.ic_unselected,"地址码", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu(R.drawable.ic_unselected,"名称", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu(R.drawable.ic_unselected,"名称", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
        presenter.showSortListToView(menuArrayList);
    }

    @Override
    public void getSettingList() {
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        menuArrayList.add(new Menu(R.drawable.ic_back,"", 0, TYPE_ITEM_nav_bg));
        menuArrayList.add(new Menu(R.drawable.ic_set, "设置", 0, TYPE_ITEM_nav_bg));
        menuArrayList.add(new Menu(0,"重命名", 0, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu(0,"编辑备注", 0, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu(0,"删除该场景", 0, TYPE_ITEM_gray_bg));
        presenter.showSettingListToView(menuArrayList);
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
    public void deleteScene(Scene scene) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .deleteInfo(scene)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }

    @Override
    public void deleteFixture(Fixture fixture) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .deleteInfo(fixture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        presenter.updateFixtureList();
                    }
                });
    }

    @Override
    public void deleteFixtureGroup(FixtureGroup fixtureGroup) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureGroupDao()
                .deleteInfo(fixtureGroup)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        presenter.updateFixtureList();
                    }
                });
    }

       @Override
    public void addFixtureGroup(String fixtureGroupName) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureGroupDao()
                .insert(new FixtureGroup(MyApplication.getOnlineUser().getEmail(), MyApplication.getScene().getName(), fixtureGroupName, 0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        presenter.updateFixtureList();
                    }
                });
    }

    @Override
    public void addFixture(Fixture fixture) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .insert(fixture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        presenter.updateFixtureList();
                    }
                });
    }


     @Override
    public void updateFixture(Fixture fixture) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .updateFixtureInfo(fixture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        presenter.updateFixtureList();
                    }
                });
    }

    @Override
    public void updateFixtureGroup(FixtureGroup fixtureGroup) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureGroupDao()
                .updateFixtureGroupInfo(fixtureGroup)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        presenter.updateFixtureList();
                    }
                });
    }

    @Override
    public void getFixtureGroupMenu(int fixtureGroupPosition) {
        ArrayList<String> fixtureGroupMenuList = new ArrayList<>();
        fixtureGroupMenuList.add("设置");
        fixtureGroupMenuList.add("重命名");
        fixtureGroupMenuList.add("管理群组");
        fixtureGroupMenuList.add("删除");
        fixtureGroupMenuList.add("取消");
        presenter.receiveFixtureGroupMenu(fixtureGroupMenuList, fixtureGroupPosition);
    }

    @Override
    public void getFixtureMenu(int fixturePosition) {
        ArrayList<String> fixtureMenuList = new ArrayList<>();
        fixtureMenuList.add("设置");
        fixtureMenuList.add("重命名");
        fixtureMenuList.add("重置名称");
        fixtureMenuList.add("更改地址码");
        fixtureMenuList.add("删除");
        fixtureMenuList.add("取消");
        presenter.receiveFixtureMenu(fixtureMenuList, fixturePosition);
    }

    @Override
    public void queryFixtureGroupList() {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureGroupDao()
                .getAllFixtureGroupInfo(MyApplication.getOnlineUser().getEmail(), MyApplication.getScene().getName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FixtureGroup>>() {
                    @Override
                    public void accept(List<FixtureGroup> fixtureGroups) throws Exception {
                        presenter.receiveFixtureGroupList(fixtureGroups);
                    }
                });
    }

    @Override
    public void queryFixtureList() {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .getAllFixtureInfo(MyApplication.getOnlineUser().getEmail(), MyApplication.getScene().getName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Fixture>>() {
                    @Override
                    public void accept(List<Fixture> fixtures) throws Exception {
                        presenter.receiveFixtureList(fixtures);
                    }
                });
    }

    @Override
    public void queryScene(String sceneName) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .getSceneInfoFromName(MyApplication.getScene().getEmail(), sceneName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Scene>>() {
                    @Override
                    public void accept(List<Scene> scenes) throws Exception {
                        presenter.receiveSceneGroup(scenes);
                    }
                });
    }
}

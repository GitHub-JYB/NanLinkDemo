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
        menuArrayList.add(new Menu(R.drawable.ic_controller,"信号控制器", R.drawable.ic_full_battery, TYPE_ITEM_gray_bg));
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
    public void getSortList(int position) {
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        menuArrayList.add(new Menu(R.drawable.ic_back,"", 0, TYPE_ITEM_nav_bg));
        menuArrayList.add(new Menu(R.drawable.ic_sort, "排序", 0, TYPE_ITEM_nav_bg));
        switch (position){
            case 0:
                menuArrayList.add(new Menu(R.drawable.ic_checked,"地址码", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"地址码", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                break;
            case 1:
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"地址码", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_checked,"地址码", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                break;
            case 2:
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"地址码", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"地址码", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_checked,"名称", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                break;
            case 3:
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"地址码", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"地址码", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_checked,"名称", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                break;
        }

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
    public void queryScene(String sceneName, int type) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .getSceneInfoFromName(MyApplication.getOnlineUser().getEmail(), sceneName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Scene>>() {
                    @Override
                    public void accept(List<Scene> scenes) throws Exception {
                        presenter.receiveSceneList(scenes, type);

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
    public void queryAllFixture(int type) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .getAllFixtureInfo(MyApplication.getOnlineUser().getEmail(), presenter.getScene().getName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Fixture>>() {
                    @Override
                    public void accept(List<Fixture> fixtures) throws Exception {
                        presenter.receiveAllFixture(fixtures, type);
                    }
                });
    }

    @Override
    public void queryAllFixtureGroup(int type) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureGroupDao()
                .getAllFixtureGroupInfo(MyApplication.getOnlineUser().getEmail(), presenter.getScene().getName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FixtureGroup>>() {
                    @Override
                    public void accept(List<FixtureGroup> fixtureGroups) throws Exception {
                        presenter.receiveAllFixtureGroup(fixtureGroups, type);
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
    public void queryFixtureFromFixtureGroupName(String fixtureGroupName, int type) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .getFixtureInfoFromFixtureGroup(MyApplication.getOnlineUser().getEmail(), presenter.getScene().getName(), fixtureGroupName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Fixture>>() {
                    @Override
                    public void accept(List<Fixture> fixtures) throws Exception {
                        presenter.receiveFixtureList(fixtures, type);
                    }
                });
    }

    @Override
    public void addFixtureGroup(String fixtureGroupName) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureGroupDao()
                .insert(new FixtureGroup(MyApplication.getOnlineUser().getEmail(), presenter.getScene().getName(), fixtureGroupName, 0))
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
    public void addFixture(String fixtureCH) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .insert(new Fixture(MyApplication.getOnlineUser().getEmail(), presenter.getScene().getName(), fixtureCH, Integer.parseInt(fixtureCH) ,"000010", "蓝牙", ""))
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
    public void queryFixtureGroup(String fixtureGroupName, int type) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureGroupDao()
                .getFixtureGroupInfoFromName(MyApplication.getOnlineUser().getEmail(), presenter.getScene().getName(), fixtureGroupName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FixtureGroup>>() {
                    @Override
                    public void accept(List<FixtureGroup> fixtureGroups) throws Exception {
                        presenter.receiveQueryFixtureGroup(fixtureGroups, type);
                    }
                });
    }

    @Override
    public void queryFixture(String fixtureCH, int type) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .getFixtureInfoFromName(MyApplication.getOnlineUser().getEmail(), presenter.getScene().getName(), Integer.parseInt(fixtureCH))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Fixture>>() {
                    @Override
                    public void accept(List<Fixture> fixtures) throws Exception {
                        presenter.receiveFixtureList(fixtures, type);
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
}

package com.example.NanLinkDemo.mvp.model.Impl;

import static com.example.NanLinkDemo.bean.Menu.TYPE_ITEM_Control;
import static com.example.NanLinkDemo.bean.Menu.TYPE_ITEM_gray_bg;
import static com.example.NanLinkDemo.bean.Menu.TYPE_ITEM_nav_bg;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.mvp.model.ControlModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.ControlPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ControlModelImpl implements ControlModel {
    private final ControlPresenterImpl presenter;

    public ControlModelImpl(ControlPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getFixture(int id) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .getFixtureInfoFromId(id)
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
    public void getFixtureGroup(int id) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureGroupDao()
                .getFixtureGroupInfoFromId(id)
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
    public void updateFixture(Fixture fixture) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .updateFixtureInfo(fixture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

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

                    }
                });
    }

    @Override
    public void getFixtureMenu() {
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        menuArrayList.add(new Menu(0,"", 0, TYPE_ITEM_nav_bg));
        menuArrayList.add(new Menu(0,"更改地址码", 0, TYPE_ITEM_Control));
        menuArrayList.add(new Menu(0,"风扇控制", 0, TYPE_ITEM_Control));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(0,"删除该设备", 0, TYPE_ITEM_Control));
        presenter.receiveFixtureMenu(menuArrayList);
    }

    @Override
    public void getFixtureGroupMenu() {
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        menuArrayList.add(new Menu(0,"", 0, TYPE_ITEM_nav_bg));
        menuArrayList.add(new Menu(0,"管理群组", 0, TYPE_ITEM_Control));
        menuArrayList.add(new Menu(0,"群组模式", 0, TYPE_ITEM_Control));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(0,"更改地址码", 0, TYPE_ITEM_Control));
        menuArrayList.add(new Menu(0,"风扇控制", 0, TYPE_ITEM_Control));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(0,"重新开始特效", 0, TYPE_ITEM_Control));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(0,"删除该设备群组", 0, TYPE_ITEM_Control));
        presenter.receiveFixtureGroupMenu(menuArrayList);
    }
}

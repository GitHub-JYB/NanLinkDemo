package com.example.nanlinkdemo.mvp.model.Impl;

import static com.example.nanlinkdemo.bean.Menu.TYPE_ITEM_gray_bg;
import static com.example.nanlinkdemo.bean.Menu.TYPE_ITEM_nav_bg;
import static com.example.nanlinkdemo.bean.Menu.TYPE_LOGO;


import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.DataBase.MyDataBase;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.MainModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.MainPresenterImpl;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainModelImpl implements MainModel {

    private final MainPresenterImpl presenter;


    public MainModelImpl(MainPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getMenu(String nickName) {
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        menuArrayList.add(new Menu(0,"", 0, TYPE_ITEM_nav_bg));
        menuArrayList.add(new Menu(0,"", 0, TYPE_LOGO));
        menuArrayList.add(new Menu(R.drawable.ic_user, nickName, 0, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(R.drawable.ic_add,"创建场景", 0, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu(R.drawable.ic_add,"创建场景群组", 0, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu(R.drawable.ic_sort,"排序", 0, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(R.drawable.ic_set,"设置", 0, TYPE_ITEM_gray_bg));
        menuArrayList.add(new Menu(R.drawable.ic_about,"有关", 0, TYPE_ITEM_gray_bg));
        presenter.showMenuToView(menuArrayList);
    }

    @Override
    public void getSceneList() {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .getSceneInfoFromSceneGroup(MyApplication.getOnlineUser().getEmail(), "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Scene>>() {
                    @Override
                    public void accept(List<Scene> scenes) throws Exception {
                        presenter.completeGetScene(scenes);
                    }
                });

    }

    @Override
    public void getSceneGroupList() {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneGroupDao()
                .getAllSceneInfo(MyApplication.getOnlineUser().getEmail())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<SceneGroup>>() {
                    @Override
                    public void accept(List<SceneGroup> sceneGroups) throws Exception {
                        presenter.completeGetSceneGroup(sceneGroups);
                    }
                });

    }


    @Override
    public void getThreePointMenu() {
        ArrayList<String> threePointList = new ArrayList<>();
        threePointList.add("设置");
        threePointList.add("重命名");
        threePointList.add("编辑备注");
        threePointList.add("删除");
        threePointList.add("取消");
        presenter.receiveThreePointMenu(threePointList);
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
                        presenter.updateSceneListToView();
                    }
                });
    }

    @Override
    public void addScene(Scene scene) {

        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .insert(scene)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        presenter.updateSceneListToView();
                    }
                });
    }

    @Override
    public void addSceneGroup(SceneGroup sceneGroup) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneGroupDao()
                .insert(sceneGroup)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        presenter.updateSceneListToView();
                    }
                });
    }

    @Override
    public void deleteSceneGroup(SceneGroup sceneGroup) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneGroupDao()
                .deleteInfo(sceneGroup)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        presenter.updateSceneListToView();
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
                        presenter.switchQuerySceneResult(scenes, type);
                    }
                });
    }

    @Override
    public void querySceneGroup(String sceneGroupName, int type) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneGroupDao()
                .getSceneGroupInfo(MyApplication.getOnlineUser().getEmail(), sceneGroupName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SceneGroup>>() {
                    @Override
                    public void accept(List<SceneGroup> sceneGroups) throws Exception {
                        presenter.switchQuerySceneGroupResult(sceneGroups, type);
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
                        presenter.updateSceneListToView();
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
                        presenter.updateSceneListToView();
                    }
                });
    }

    @Override
    public void getSortList(int position) {
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        menuArrayList.add(new Menu(R.drawable.ic_back,"", 0, TYPE_ITEM_nav_bg));
        menuArrayList.add(new Menu(R.drawable.ic_sort, "排序", 0, TYPE_ITEM_nav_bg));
        switch (position){
            case 0:
                menuArrayList.add(new Menu(R.drawable.ic_checked,"创建时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                break;
            case 1:
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_checked,"创建时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                break;
            case 2:
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_checked,"最后编辑时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                break;
            case 3:
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_checked,"最后编辑时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                break;
            case 4:
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_checked,"设备数量", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                break;
            case 5:
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_checked,"设备数量", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                break;
            case 6:
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_checked,"名称", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                break;
            case 7:
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"创建时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"最后编辑时间", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"设备数量", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_unchecked,"名称", R.drawable.ic_sort_up, TYPE_ITEM_gray_bg));
                menuArrayList.add(new Menu(R.drawable.ic_checked,"名称", R.drawable.ic_sort_down, TYPE_ITEM_gray_bg));
                break;
        }

        presenter.showSortListToView(menuArrayList);
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
    public void querySceneFromGroup(String sceneGroupName, int type) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .getSceneInfoFromSceneGroup(MyApplication.getOnlineUser().getEmail(), sceneGroupName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Scene>>() {
                    @Override
                    public void accept(List<Scene> scenes) throws Exception {
                        presenter.receiveQuerySceneFromSceneGroup(scenes, type);

                    }
                });
    }

}

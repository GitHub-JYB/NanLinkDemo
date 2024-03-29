package com.example.NanLinkDemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.databinding.ActivitySceneGroupBinding;
import com.example.NanLinkDemo.mvp.adapter.MenuAdapter;
import com.example.NanLinkDemo.mvp.adapter.SceneAdapter;
import com.example.NanLinkDemo.mvp.presenter.Impl.SceneGroupPresenterImpl;
import com.example.NanLinkDemo.mvp.view.SceneGroupView;
import com.example.NanLinkDemo.ui.UnlessLastItemDecoration;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.util.SnackBarUtil;

import java.util.ArrayList;
import java.util.List;

@Route(path = Constant.ACTIVITY_URL_SceneGroup)
public class SceneGroupActivity extends BaseActivity<ActivitySceneGroupBinding> implements SceneGroupView, View.OnClickListener {


    private SceneGroupPresenterImpl presenter;
    private SceneAdapter sceneAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initRecycleView();
        initSceneGroup();
    }

    private void initSceneGroup() {
        presenter.getSceneGroupFromModel(MyApplication.getSceneGroup().getName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
        updateRecycleView();
    }

    @Override
    public void updateRecycleView() {
        presenter.getSceneListFromModel(MyApplication.getSceneGroup().getName());
    }


    private void initRecycleView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        // 设置分割线格式并添加
        UnlessLastItemDecoration decoration = new UnlessLastItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getDrawable(R.drawable.decoration_scene));
        binding.recycleView.addItemDecoration(decoration);
        sceneAdapter = new SceneAdapter();
        binding.recycleView.setAdapter(sceneAdapter);
        sceneAdapter.setMenuOnClickListener(new SceneAdapter.MenuOnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.sceneMenuSwitch(position);
            }
        });
        sceneAdapter.setOnClickListener(new SceneAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                if (checkPermission()){
                    if (checkBle()){
                        if (checkLocation()){
                            presenter.sceneListSwitch(position);
                        }else {
                            SnackBarUtil.show(binding.recycleView,"NANLINK需要使用位置信息连接灯具，请打开位置信息");
                        }
                    }else {
                        SnackBarUtil.show(binding.recycleView,"NANLINK需要使用蓝牙连接灯具，请打开蓝牙");
                    }
                }
            }
        });
    }

    private void initToolbar() {
        setTitle(MyApplication.getSceneGroup().getName());
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setRightSecondBtnIcon(R.drawable.ic_search);
        binding.toolbar.setRightBtnIcon(R.drawable.ic_menu);
        binding.toolbar.setLeftBtnOnClickListener(this);
        binding.toolbar.setRightSecondBtnOnClickListener(this);
        binding.toolbar.setRightBtnOnClickListener(this);
    }

    @Override
    public void setTitle(String title){
        binding.toolbar.setTitle(title);
    }

    private void setPresenter() {
        presenter = new SceneGroupPresenterImpl(this);
    }

    @Override
    public void initMenu() {
        presenter.getMenuFromModel();
        binding.navigation.setItemOnClickListener(new MenuAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.menuSwitch(position);
            }
        });
    }


    @Override
    public void showMenu(ArrayList<Menu> menuArrayList) {
        binding.navigation.setData(menuArrayList);
    }

    @Override
    public void openDrawLayout() {
        binding.drawerLayout.openDrawer(GravityCompat.END);
    }

    @Override
    public void closeDrawLayout() {
        binding.drawerLayout.closeDrawers();
    }


    @Override
    public void showSceneList(List<Scene> sceneList) {
        sceneAdapter.setData(sceneList, new ArrayList<SceneGroup>());
    }

    @Override
    public void onClick(View v) {
        presenter.toolbarSwitch(v);
    }

    @Override
    public void showSortList(ArrayList<Menu> sortArrayList) {
        showMenu(sortArrayList);
        binding.navigation.setItemOnClickListener(new MenuAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.sortSwitch(position);
            }
        });
    }

    @Override
    public void showSettingList(ArrayList<Menu> settingArrayList) {
        showMenu(settingArrayList);
        binding.navigation.setItemOnClickListener(new MenuAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.settingSwitch(position);
            }
        });
    }
}
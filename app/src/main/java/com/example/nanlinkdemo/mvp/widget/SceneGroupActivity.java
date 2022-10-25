package com.example.nanlinkdemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.databinding.ActivitySceneGroupBinding;
import com.example.nanlinkdemo.mvp.adapter.MenuAdapter;
import com.example.nanlinkdemo.mvp.adapter.SceneAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.SceneGroupPresenterImpl;
import com.example.nanlinkdemo.mvp.view.SceneGroupView;
import com.example.nanlinkdemo.ui.UnlessLastItemDecoration;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;
import java.util.List;

@Route(path = Constant.ACTIVITY_URL_SceneGroup)
public class SceneGroupActivity extends BaseActivity<ActivitySceneGroupBinding> implements SceneGroupView, View.OnClickListener {


    @Autowired(name = "sceneGroupName")
    String sceneGroupName;

    private SceneGroupPresenterImpl presenter;
    private SceneAdapter sceneAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initMenu();
        initRecycleView();
        initSceneGroup();
    }

    private void initSceneGroup() {
        presenter.getSceneGroupFromModel(sceneGroupName);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateMenu();
        updateRecycleView();
    }

    @Override
    public void updateRecycleView() {
        presenter.getSceneListFromModel(sceneGroupName);
    }


    @Override
    public void updateMenu() {
        presenter.getMenuFromModel();
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
                presenter.sceneListSwitch(position);
            }
        });
    }

    private void initToolbar() {
        binding.toolbar.setTitle(sceneGroupName);
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setRightSecondBtnIcon(R.drawable.ic_search);
        binding.toolbar.setRightBtnIcon(R.drawable.ic_menu);
        binding.toolbar.setLeftBtnOnClickListener(this);
        binding.toolbar.setRightSecondBtnOnClickListener(this);
        binding.toolbar.setRightBtnOnClickListener(this);
    }

    private void setPresenter() {
        presenter = new SceneGroupPresenterImpl(this);
    }

    @Override
    public void initMenu() {
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
    public String getSceneGroupName() {
        return sceneGroupName;
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
}
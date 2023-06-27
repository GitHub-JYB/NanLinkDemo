package com.example.nanlinkdemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.DB.bean.FixtureGroup;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.databinding.ActivitySceneBinding;
import com.example.nanlinkdemo.mvp.adapter.FixtureAdapter;
import com.example.nanlinkdemo.mvp.adapter.MenuAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.ScenePresenterImpl;
import com.example.nanlinkdemo.mvp.view.SceneView;
import com.example.nanlinkdemo.ui.UnlessLastItemDecoration;
import com.example.nanlinkdemo.util.Constant;
import com.example.nanlinkdemo.util.SnackBarUtil;

import java.util.ArrayList;
import java.util.List;

@Route(path = Constant.ACTIVITY_URL_Scene)
public class SceneActivity extends BaseActivity<ActivitySceneBinding> implements SceneView, View.OnClickListener {


    private ScenePresenterImpl presenter;
    private FixtureAdapter fixtureAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initRecycleView();
        initAddNewFixtureLogo();
    }

    private void initAddNewFixtureLogo() {
        binding.addFixture.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateRecycleView();
        setTitle(MyApplication.getScene().getName());
        if (!checkPermission()){
            agreePermission();
        }
    }

    @Override
    public void updateAddNewFixtureLogo(boolean visibility) {
        if (visibility) {
            binding.addFixture.setVisibility(View.VISIBLE);
            binding.recycleView.setVisibility(View.GONE);
        } else {
            binding.addFixture.setVisibility(View.GONE);
            binding.recycleView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateRecycleView() {
        presenter.getFixtureListFromModel();
    }


    private void initRecycleView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        // 设置分割线格式并添加
        UnlessLastItemDecoration decoration = new UnlessLastItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getDrawable(R.drawable.decoration_scene));
        binding.recycleView.addItemDecoration(decoration);
        fixtureAdapter = new FixtureAdapter();
        binding.recycleView.setAdapter(fixtureAdapter);
        fixtureAdapter.setMenuOnClickListener(new FixtureAdapter.MenuOnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.FixtureMenuSwitch(position);

            }
        });
        fixtureAdapter.setOnClickListener(new FixtureAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.FixtureListSwitch(position);

            }
        });
        fixtureAdapter.setRightSecondIconOnClickListener(new FixtureAdapter.RightSecondIconOnClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
        fixtureAdapter.setMenuInGroupOnClickListener(new FixtureAdapter.MenuInGroupOnClickListener() {
            @Override
            public void onClick(Fixture fixture) {
                presenter.FixtureMenuSwitch(fixture);
            }
        });
        fixtureAdapter.setRightSecondIconInGroupOnClickListener(new FixtureAdapter.RightSecondIconInGroupOnClickListener() {
            @Override
            public void onClick(Fixture fixture) {

            }
        });
    }

    private void initToolbar() {
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_exit);
        binding.toolbar.setRightBtnIcon(R.drawable.ic_menu);
        binding.toolbar.setLeftBtnOnClickListener(this);
        binding.toolbar.setRightBtnOnClickListener(this);
    }

    @Override
    public void setTitle(String title) {
        binding.toolbar.setTitle(title);
    }

    private void setPresenter() {
        presenter = new ScenePresenterImpl(this);
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
    public void setData(List<FixtureGroup> fixtureGroupList, List<Fixture> fixtureList) {
        fixtureAdapter.setData(fixtureGroupList, fixtureList);
    }

    @Override
    public void onClick(View v) {
        presenter.switchOnclick(v);
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
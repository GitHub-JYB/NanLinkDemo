package com.example.nanlinkdemo.mvp.widget;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.databinding.ActivityMainBinding;
import com.example.nanlinkdemo.mvp.adapter.MenuAdapter;
import com.example.nanlinkdemo.mvp.adapter.SceneAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.MainPresenterImpl;
import com.example.nanlinkdemo.mvp.view.MainView;
import com.example.nanlinkdemo.ui.UnlessLastItemDecoration;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;
import java.util.List;

@Route(path = Constant.ACTIVITY_URL_Main)
public class MainActivity extends BaseActivity<ActivityMainBinding> implements MainView, View.OnClickListener {


    private MainPresenterImpl presenter;
    private SceneAdapter sceneAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initRecycleView();
        initPermission();

    }



    private void initPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
//            }

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        updateRecycleView();

    }

    @Override
    public void updateRecycleView() {
        presenter.getSceneListFromModel();
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
        binding.toolbar.setTitle("场景列表");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_menu);
        binding.toolbar.setRightBtnIcon(R.drawable.ic_search);
        binding.toolbar.setLeftBtnOnClickListener(this);
        binding.toolbar.setRightBtnOnClickListener(this);
    }

    private void setPresenter() {
        presenter = new MainPresenterImpl(this);
        presenter.getDeviceListFromModel();
    }

    @Override
    public void initMenu() {
        presenter.getMenuFromModel();
        binding.navigation.setVersion("Version " + MyApplication.getVersion());
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
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void closeDrawLayout() {
        binding.drawerLayout.closeDrawers();
    }


    @Override
    public void showSceneList(List<Scene> sceneList, List<SceneGroup> sceneGroupList) {
        sceneAdapter.setData(sceneList, sceneGroupList);
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
    public void onClick(View v) {
        presenter.toolbarSwitch(v);
    }
}
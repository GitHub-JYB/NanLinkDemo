package com.example.nanlinkdemo.mvp.widget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.databinding.ActivityMainBinding;
import com.example.nanlinkdemo.mvp.adapter.MenuAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.MainPresenterImpl;
import com.example.nanlinkdemo.mvp.view.MainView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MainView{


    ActivityMainBinding binding;
    private MainPresenterImpl presenter;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setPresenter();
        initToolbar();
        initRecyclerViewMenu();

    }

    private void setPresenter() {
        presenter = new MainPresenterImpl(this);
    }

    private void initRecyclerViewMenu() {
        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));

        // 设置分割线格式并添加
        DividerItemDecoration decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getDrawable(R.drawable.decoration_menu));
        binding.recyclerViewMenu.addItemDecoration(decoration);

        menuAdapter = new MenuAdapter();
        presenter.getMenuFromModel();
        binding.recyclerViewMenu.setAdapter(menuAdapter);
        menuAdapter.setOnClickListener(new MenuAdapter.OnClickListener() {
            @Override
            public void onClick(String menuText) {
                presenter.menuSwitch(menuText);
            }
        });
    }

    private void initToolbar() {
        binding.toolbar.setTitle("场景列表");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_menu);
        binding.toolbar.setRightBtnIcon(R.drawable.ic_search);
        binding.toolbar.setLeftBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.toolbarSwitch(v);
            }
        });
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    /**
     * 通过设置全屏，设置状态栏透明
     */
    private void fullScreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public void showMenu(ArrayList<Menu> menuArrayList) {
        menuAdapter.setData(menuArrayList);
    }

    @Override
    public void openDrawLayout() {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

}
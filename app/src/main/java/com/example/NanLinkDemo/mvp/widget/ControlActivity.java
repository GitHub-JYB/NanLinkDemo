package com.example.NanLinkDemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.core.view.GravityCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.databinding.ActivityControlBinding;
import com.example.NanLinkDemo.mvp.adapter.MenuAdapter;
import com.example.NanLinkDemo.mvp.presenter.Impl.ControlPresenterImpl;
import com.example.NanLinkDemo.mvp.view.ControlView;
import com.example.NanLinkDemo.util.Constant;

import java.util.ArrayList;

@Route(path = Constant.ACTIVITY_URL_Control)
public class ControlActivity extends BaseActivity<ActivityControlBinding> implements ControlView, View.OnClickListener {


    @Autowired(name = "id")
    int id;

    @Autowired(name = "type")
    int type;

    public static final int TYPE_FIXTURE = 0;
    public static final int TYPE_FIXTURE_GROUP = 1;
    private ControlPresenterImpl presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initControlData();
        initToolbar();
    }

    private void initControlData() {
        presenter.getControlDataFromModel(type, id);
    }


    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
    }


    private void initToolbar() {
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setRightBtnIcon(R.drawable.ic_menu);
        binding.toolbar.setLeftBtnOnClickListener(this);
        binding.toolbar.setRightBtnOnClickListener(this);
    }


    @Override
    public void setTitle(String title) {
        binding.toolbar.setTitle(title);
    }

    private void setPresenter() {
        presenter = new ControlPresenterImpl(this);
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
    public void onClick(View v) {
        presenter.switchOnclick(v);
    }





}
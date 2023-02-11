package com.example.nanlinkdemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.DB.bean.Controller;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivityRecycleviewScanBinding;
import com.example.nanlinkdemo.databinding.ActivityRecycleviewSettingBinding;
import com.example.nanlinkdemo.mvp.adapter.ControllerAdapter;
import com.example.nanlinkdemo.mvp.adapter.SettingAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.ControllerPresenterImpl;
import com.example.nanlinkdemo.mvp.view.ControllerView;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;

@Route(path = Constant.ACTIVITY_URL_Controller)
public class ControllerActivity extends BaseActivity<ActivityRecycleviewScanBinding> implements ControllerView, View.OnClickListener {


    private  ControllerPresenterImpl presenter;
    private ControllerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        binding.toolbar.setTitle("信号控制器");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
        binding.toolbar.setRightBtnIcon(R.drawable.ic_add_controller);
        binding.toolbar.setRightBtnOnClickListener(this);


    }

    @Override
    public void setPresenter() {
        presenter = new ControllerPresenterImpl(this);
    }

    @Override
    public void showController(ArrayList<Controller> controllerList) {
        adapter.setData(controllerList);

    }

    private void initRecyclerView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter = new ControllerAdapter();
        presenter.getListDataFromView();
        binding.recycleView.setAdapter(adapter);
        adapter.setOnClickListener(new ControllerAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.onClickSwitch(position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        presenter.onClick(v);
    }
}

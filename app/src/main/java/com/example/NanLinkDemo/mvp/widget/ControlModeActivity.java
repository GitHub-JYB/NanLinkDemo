package com.example.NanLinkDemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.ActivityRecycleviewSettingBinding;
import com.example.NanLinkDemo.mvp.adapter.ControlModeAdapter;
import com.example.NanLinkDemo.mvp.presenter.Impl.ControlModePresenterImpl;
import com.example.NanLinkDemo.mvp.view.ControlModeView;
import com.example.NanLinkDemo.util.Constant;

import java.util.ArrayList;

@Route(path = Constant.ACTIVITY_URL_ControlMode)
public class ControlModeActivity extends BaseActivity<ActivityRecycleviewSettingBinding> implements ControlModeView, View.OnClickListener {


    private ControlModePresenterImpl presenter;
    private ControlModeAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        binding.toolbar.setTitle("控台模式");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }

    @Override
    public void setPresenter() {
        presenter = new ControlModePresenterImpl(this);
    }


    @Override
    public void showControls(ArrayList<FixtureGroup> hasFixtureGroupList, ArrayList<Fixture> hasGroupFixtureList, ArrayList<Fixture> noGroupFixtureList){
        adapter.setData(hasFixtureGroupList, hasGroupFixtureList, noGroupFixtureList);
    }

    private void initRecyclerView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        // 设置分割线格式并添加
        DividerItemDecoration decoration = new DividerItemDecoration(getBaseContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getBaseContext().getDrawable(R.drawable.decoration_setting));
        binding.recycleView.addItemDecoration(decoration);

        adapter = new ControlModeAdapter();
        presenter.getDataList();
        binding.recycleView.setAdapter(adapter);
        adapter.setOnDelayTimeClickListener(new ControlModeAdapter.OnDelayTimeClickListener() {
            @Override
            public void onDelayTimeClick(int position, String delayTime) {
                presenter.clickDelayTime(position, delayTime);
            }
        });
        adapter.setOnDataClickListener(new ControlModeAdapter.OnDataClickListener() {
            @Override
            public void onDataClick(int position, String dim) {
                presenter.clickData(position, dim);
            }
        });
        adapter.setOnDataUpdateListener(new ControlModeAdapter.OnDataUpdateListener() {
            @Override
            public void onDataUpdate(int position, String dim) {
                presenter.updateDim(position, dim);
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}

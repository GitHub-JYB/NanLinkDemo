package com.example.NanLinkDemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.ActivityRecycleviewScanBinding;
import com.example.NanLinkDemo.mvp.adapter.ManageFixtureAdapter;
import com.example.NanLinkDemo.mvp.presenter.Impl.ManageFixturePresenterImpl;
import com.example.NanLinkDemo.mvp.view.ManageFixtureView;
import com.example.NanLinkDemo.util.Constant;

import java.util.ArrayList;

@Route(path = Constant.ACTIVITY_URL_ManageFixture)
public class ManageFixtureActivity extends BaseActivity<ActivityRecycleviewScanBinding> implements ManageFixtureView, View.OnClickListener {

    @Autowired(name = "fixtureGroupName")
    String fixtureGroupName;
    private ManageFixturePresenterImpl presenter;
    private ManageFixtureAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initBtn();
        initRecyclerView();
   }


    private void initBtn() {
        binding.oneBtn.setVisibility(View.VISIBLE);
        binding.finish.setBackgroundResource(R.drawable.bg_able_btn_selected);
        binding.finish.setOnClickListener(this);
    }

    private void initToolbar() {
        binding.toolbar.setTitle(fixtureGroupName);
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }

    @Override
    public void setPresenter() {
        presenter = new ManageFixturePresenterImpl(this);
    }

    @Override
    public void showFixture(ArrayList<Fixture> fixtureList) {
        adapter.setData(fixtureList);
    }


    private void initRecyclerView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        presenter.getFixtureListFromModel();
        adapter = new ManageFixtureAdapter();
        binding.recycleView.setAdapter(adapter);
        adapter.setOnClickListener(new ManageFixtureAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.switchFixtureList(position);
            }
        });
    }


    @Override
    public void onClick(View view) {
        presenter.switchOnclick(view);
    }

    @Override
    public String getFixtureGroupName(){
        return fixtureGroupName;
    }
}

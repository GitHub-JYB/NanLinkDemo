package com.example.nanlinkdemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivityRecycleviewScanBinding;
import com.example.nanlinkdemo.mvp.adapter.ManageGroupAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.ManageScenePresenterImpl;
import com.example.nanlinkdemo.mvp.view.ManageSceneView;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;

@Route(path = Constant.ACTIVITY_URL_ManageFixture)
public class ManageFixtureActivity extends BaseActivity<ActivityRecycleviewScanBinding> implements ManageSceneView, View.OnClickListener {

    private ManageScenePresenterImpl presenter;
    private ManageGroupAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initBtn();
        initRecyclerView();
   }


    private void initBtn() {
        binding.finish.setVisibility(View.VISIBLE);
        binding.finish.setOnClickListener(this);
    }

    private void initToolbar() {
        binding.toolbar.setTitle(MyApplication.getSceneGroup().getName());
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }

    @Override
    public void setPresenter() {
        presenter = new ManageScenePresenterImpl(this);
    }

    @Override
    public void showScene(ArrayList<Scene> sceneList) {
        adapter.setData(sceneList);

    }

    private void initRecyclerView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        presenter.getSceneListFromModel();
        adapter = new ManageGroupAdapter();
        binding.recycleView.setAdapter(adapter);
        adapter.setOnClickListener(new ManageGroupAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.switchSceneList(position);
            }
        });
    }


    @Override
    public void onClick(View view) {
        presenter.switchOnclick(view);
    }
}

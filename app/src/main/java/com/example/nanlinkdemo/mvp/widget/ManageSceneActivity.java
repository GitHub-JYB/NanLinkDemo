package com.example.nanlinkdemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivityRecycleviewBinding;
import com.example.nanlinkdemo.mvp.adapter.ManageGroupAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.ManageScenePresenterImpl;
import com.example.nanlinkdemo.mvp.view.ManageSceneView;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;

@Route(path = Constant.ACTIVITY_URL_ManageScene)
public class ManageSceneActivity extends BaseActivity<ActivityRecycleviewBinding> implements ManageSceneView, View.OnClickListener {


    @Autowired(name = "sceneGroupName")
    String sceneGroupName;

    private ManageScenePresenterImpl presenter;
    private ManageGroupAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initBtn();
        initRecyclerView();
        initSceneGroup();
    }

    private void initSceneGroup() {
        presenter.getSceneGroupFromModel(sceneGroupName);
    }

    private void initBtn() {
        binding.complete.setVisibility(View.VISIBLE);
        binding.complete.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateRecyclerView();
    }

    @Override
    public void updateRecyclerView() {
        presenter.getSceneListFromModel();
    }

    private void initToolbar() {
        binding.toolbar.setTitle(sceneGroupName);
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

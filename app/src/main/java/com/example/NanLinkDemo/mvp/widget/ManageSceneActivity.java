package com.example.NanLinkDemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.ActivityRecycleviewScanBinding;
import com.example.NanLinkDemo.mvp.adapter.ManageSceneAdapter;
import com.example.NanLinkDemo.mvp.presenter.Impl.ManageScenePresenterImpl;
import com.example.NanLinkDemo.mvp.view.ManageSceneView;
import com.example.NanLinkDemo.util.Constant;

import java.util.ArrayList;

@Route(path = Constant.ACTIVITY_URL_ManageScene)
public class ManageSceneActivity extends BaseActivity<ActivityRecycleviewScanBinding> implements ManageSceneView, View.OnClickListener {

    @Autowired(name = "sceneGroupName")
    String sceneGroupName;

    private ManageScenePresenterImpl presenter;
    private ManageSceneAdapter adapter;

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
        binding.toolbar.setTitle(sceneGroupName);
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }

    @Override
    public void setPresenter() {
        presenter = new ManageScenePresenterImpl(this);
        presenter.getSceneGroupFromModel(sceneGroupName);
    }

    @Override
    public void showScene(ArrayList<Scene> sceneList) {
        adapter.setData(sceneList);

    }

    private void initRecyclerView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        presenter.getSceneListFromModel();
        adapter = new ManageSceneAdapter();
        binding.recycleView.setAdapter(adapter);
        adapter.setOnClickListener(new ManageSceneAdapter.OnClickListener() {
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

    public String getSceneGroupName(){
        return sceneGroupName;
    }
}

package com.example.nanlinkdemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.AddFixtureType;
import com.example.nanlinkdemo.databinding.ActivityRecycleviewScanBinding;
import com.example.nanlinkdemo.mvp.adapter.AddNewFixtureAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.AddNewFixturePresenterImpl;
import com.example.nanlinkdemo.mvp.view.AddNewFixtureView;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;

@Route(path = Constant.ACTIVITY_URL_AddNewFixture)
public class AddNewFixtureActivity extends BaseActivity<ActivityRecycleviewScanBinding> implements AddNewFixtureView, View.OnClickListener {


    private AddNewFixturePresenterImpl presenter;
    private AddNewFixtureAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        binding.toolbar.setTitle("添加新设备");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }

    @Override
    public void setPresenter() {
        presenter = new AddNewFixturePresenterImpl(this);
    }

    @Override
    public void showAddType(ArrayList<AddFixtureType> arrayList) {
        adapter.setData(arrayList);

    }

    private void initRecyclerView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        adapter = new AddNewFixtureAdapter();
        presenter.getListDataFromView();
        binding.recycleView.setAdapter(adapter);
        adapter.setOnClickListener(new AddNewFixtureAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.onClickSwitch(position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}

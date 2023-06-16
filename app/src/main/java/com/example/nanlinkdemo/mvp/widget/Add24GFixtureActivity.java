package com.example.nanlinkdemo.mvp.widget;

import static com.example.nanlinkdemo.util.Constant.ACTIVITY_URL_Add24GFixture;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.databinding.ActivityRecycleviewSettingBinding;
import com.example.nanlinkdemo.mvp.adapter.SettingAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.SettingPresenterImpl;
import com.example.nanlinkdemo.mvp.view.SettingView;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;

@Route(path = ACTIVITY_URL_Add24GFixture)
public class Add24GFixtureActivity extends BaseActivity<ActivityRecycleviewSettingBinding> implements SettingView, View.OnClickListener {


    private SettingPresenterImpl presenter;
    private SettingAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        binding.toolbar.setTitle("设置");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }

    @Override
    public void setPresenter() {
        presenter = new SettingPresenterImpl(this);
    }

    @Override
    public void showStories(ArrayList<Menu> settingList) {
        adapter.setData(settingList, null);

    }

    private void initRecyclerView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        // 设置分割线格式并添加
        DividerItemDecoration decoration = new DividerItemDecoration(getBaseContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getBaseContext().getDrawable(R.drawable.decoration_setting));
        binding.recycleView.addItemDecoration(decoration);

        adapter = new SettingAdapter();
        presenter.getListDataFromView();
        binding.recycleView.setAdapter(adapter);
        adapter.setOnClickListener(new SettingAdapter.OnClickListener() {
            @Override
            public void onClick(String settingText) {
                presenter.onClickSwitch(settingText);
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}

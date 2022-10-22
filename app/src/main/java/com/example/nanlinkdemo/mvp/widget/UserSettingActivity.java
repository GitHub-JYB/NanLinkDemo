package com.example.nanlinkdemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.bean.RegisterUser;
import com.example.nanlinkdemo.databinding.ActivityRecycleviewBinding;
import com.example.nanlinkdemo.mvp.adapter.SettingAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.UserSettingPresenterImpl;
import com.example.nanlinkdemo.mvp.view.UserSettingView;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;

@Route(path = Constant.ACTIVITY_URL_UserSetting)
public class UserSettingActivity extends BaseActivity<ActivityRecycleviewBinding> implements UserSettingView, View.OnClickListener {


    private UserSettingPresenterImpl presenter;
    private SettingAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateRecyclerView();
    }

    @Override
    public void updateRecyclerView() {
        presenter.getSettingListFromModel();
    }

    private void initToolbar() {
        binding.toolbar.setTitle("账号设置");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }

    @Override
    public void setPresenter() {
        presenter = new UserSettingPresenterImpl(this);
    }

    @Override
    public void showStories(ArrayList<Menu> settingList, ArrayList<RegisterUser> userList) {
        adapter.setData(settingList,userList);

    }


    private void initRecyclerView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        // 设置分割线格式并添加
        DividerItemDecoration decoration = new DividerItemDecoration(getBaseContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getBaseContext().getDrawable(R.drawable.decoration_setting));
        binding.recycleView.addItemDecoration(decoration);
        adapter = new SettingAdapter();
        presenter.getSettingListFromModel();
        binding.recycleView.setAdapter(adapter);
        adapter.setOnClickListener(new SettingAdapter.OnClickListener() {
            @Override
            public void onClick(String settingText) {
                presenter.settingSwitch(settingText);
            }
        });
    }


    @Override
    public void onClick(View v) {
        finish();
    }
}

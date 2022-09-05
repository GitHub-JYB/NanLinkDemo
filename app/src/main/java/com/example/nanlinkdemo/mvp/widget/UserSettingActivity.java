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
import com.example.nanlinkdemo.ui.MyDialog;
import com.example.nanlinkdemo.util.Constant;
import com.example.nanlinkdemo.util.SpUtil;

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

    @Override
    public void saveLogin() {
        SpUtil.getIntance(this).setLogin(false);
    }


    private void initRecyclerView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        // 设置分割线格式并添加
        DividerItemDecoration decoration = new DividerItemDecoration(getBaseContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getBaseContext().getDrawable(R.drawable.decoration_setting));
        binding.recycleView.addItemDecoration(decoration);

        adapter = new SettingAdapter();
        presenter.getSettingListFromView();
        binding.recycleView.setAdapter(adapter);
        adapter.setOnClickListener(new SettingAdapter.OnClickListener() {
            @Override
            public void onClick(String settingText) {
                presenter.settingSwitch(settingText);
            }
        });
    }

    @Override
    public void showMenuDialog(String title, String message, int type) {
        MyDialog dialog = new MyDialog();
        dialog.setType(type);
        dialog.setTitle(title);
        if (type == 0) {
            dialog.setMessage(message);
            dialog.setNeutralText("重试");
            dialog.setNeutralOnClickListener(new MyDialog.NeutralOnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else {
            dialog.setNegativeText("取消");
            dialog.setPositiveText("创建");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setNegativeOnClickListener(new MyDialog.NegativeOnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }

        dialog.show(getSupportFragmentManager(), "MyDialog");


    }

    @Override
    public void onClick(View v) {
        finish();
    }
}

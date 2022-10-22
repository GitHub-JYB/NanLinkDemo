package com.example.nanlinkdemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivityEditUserBinding;
import com.example.nanlinkdemo.mvp.presenter.Impl.EditUserInfoPresenterImpl;
import com.example.nanlinkdemo.mvp.view.EditUserInfoView;
import com.example.nanlinkdemo.util.Constant;


@Route(path = Constant.ACTIVITY_URL_EditUserInfo)
public class EditUserInfoActivity extends BaseActivity<ActivityEditUserBinding> implements EditUserInfoView, View.OnClickListener {

    private EditUserInfoPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initBtnOnClick();
        initUserInfo();
    }

    private void initUserInfo() {
        setEmail(MyApplication.getOnlineUser().getEmail());
        setNickName(MyApplication.getOnlineUser().getNickName());
        setVocation(MyApplication.getOnlineUser().getVocation());
    }


    private void initBtnOnClick() {
        binding.btnEditUser.setOnClickListener(this);
    }

    private void initToolbar() {
        binding.toolbar.setTitle("编辑账号信息");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }

    @Override
    public void setPresenter() {
        presenter = new EditUserInfoPresenterImpl(this);
    }

    @Override
    public void setEmail(String email) {
        binding.email.setText(email);
    }

    @Override
    public void setVocation(String vocation) {
        binding.vocation.setText(vocation);
    }

    @Override
    public void setNickName(String nickName) {
        binding.nickName.setText(nickName);
    }

    @Override
    public String getVocation() {
        return binding.vocation.getText().toString().trim();
    }

    @Override
    public String getNickName() {
        return binding.nickName.getText().toString().trim();
    }

    @Override
    public void onClick(View view) {
        presenter.switchOnclick(view);
    }


}

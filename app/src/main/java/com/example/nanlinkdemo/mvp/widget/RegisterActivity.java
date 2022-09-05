package com.example.nanlinkdemo.mvp.widget;

import android.os.Bundle;
import android.view.View;


import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivityRegisterBinding;
import com.example.nanlinkdemo.mvp.presenter.Impl.RegisterPresenterImpl;
import com.example.nanlinkdemo.mvp.view.RegisterView;
import com.example.nanlinkdemo.util.Constant;


@Route(path = Constant.ACTIVITY_URL_Register)
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> implements RegisterView, View.OnClickListener {

    private RegisterPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initBtnOnClick();
    }

    private void initBtnOnClick() {
        binding.btnRegister.setOnClickListener(this);
        binding.btnGetCode.setOnClickListener(this);
        binding.loginCheck.setOnClickListener(this);
    }

    private void initToolbar() {
        binding.toolbar.setTitle("注册");
        binding.toolbar.setTitleColor(R.color.blue);
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }

    @Override
    public void setPresenter() {
        presenter = new RegisterPresenterImpl(this);
    }

    @Override
    public void setCheckImage(int resId) {
        binding.loginCheck.setImageResource(resId);
    }

    @Override
    public String getEmail() {
        return binding.email.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return binding.password.getText().toString().trim();
    }

    @Override
    public String getConfirmPassword() {
        return binding.confirmPassword.getText().toString().trim();
    }

    @Override
    public String getNickName() {
        return binding.nickName.getText().toString().trim();
    }

    @Override
    public String getCode() {
        return binding.etCode.getText().toString().trim();
    }


    @Override
    public void onClick(View view) {
        presenter.switchOnclick(view);
    }
}

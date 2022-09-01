package com.example.nanlinkdemo.mvp.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nanlinkdemo.databinding.ActivityRegisterBinding;
import com.example.nanlinkdemo.mvp.presenter.Impl.RegisterPresenterImpl;
import com.example.nanlinkdemo.mvp.view.RegisterView;



public class RegisterActivity extends AppCompatActivity implements RegisterView, View.OnClickListener {

    private RegisterPresenterImpl presenter;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setPresenter();
        binding.btnRegister.setOnClickListener(this);
        binding.btnGetCode.setOnClickListener(this);
        binding.loginCheck.setOnClickListener(this);

    }

    private void fullScreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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
    public void gotoActivity(Class<?> cls) {
        startActivity(new Intent(RegisterActivity.this, cls));
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

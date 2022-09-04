package com.example.nanlinkdemo.mvp.widget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import androidx.annotation.Nullable;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivityLoginBinding;
import com.example.nanlinkdemo.mvp.presenter.Impl.LoginPresenterImpl;
import com.example.nanlinkdemo.mvp.view.LoginView;
import com.example.nanlinkdemo.ui.LoadingDialog;
import com.example.nanlinkdemo.util.SpUtil;


public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements LoginView, View.OnClickListener {


    private LoginPresenterImpl presenter;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPresenter();
        binding.loginCheck.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
    }



    @Override
    public void setPresenter() {
        presenter = new LoginPresenterImpl(this);
    }


    @Override
    public void setCheckImage(int resId) {
        binding.loginCheck.setImageResource(resId);
    }

    @Override
    public void gotoActivity(Class<?> cls) {
        startActivity(new Intent(LoginActivity.this, cls));
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
    public void saveEmail(String email) {
        SpUtil.getIntance(getBaseContext()).setUsername(email);
    }

    @Override
    public void starLoading() {
        dialog = new LoadingDialog();
        dialog.show(getSupportFragmentManager(),"LoadingDialog");
    }

    @Override
    public void stopLoading() {
        dialog.dismiss();
    }


    @Override
    public void onClick(View view) {
        presenter.switchOnclick(view);
    }
}

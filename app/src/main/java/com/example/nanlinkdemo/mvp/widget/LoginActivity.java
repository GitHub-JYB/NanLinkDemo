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
import com.example.nanlinkdemo.util.SpUtil;


public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements LoginView, View.OnClickListener {


    private LoginPresenterImpl presenter;

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
        binding.loading.ivBgLoading.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_loading);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setRepeatCount(-1);
        binding.loading.ivLoading.startAnimation(animation);
    }

    @Override
    public void stopLoading() {
        binding.loading.ivLoading.clearAnimation();
        binding.loading.ivBgLoading.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View view) {
        presenter.switchOnclick(view);
    }
}

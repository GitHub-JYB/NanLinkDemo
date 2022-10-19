package com.example.nanlinkdemo.mvp.widget;


import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivityForgetPasswordResetBinding;
import com.example.nanlinkdemo.mvp.presenter.Impl.ForgetPasswordResetPresenterImpl;
import com.example.nanlinkdemo.mvp.view.ForgetPasswordResetView;
import com.example.nanlinkdemo.util.Constant;


@Route(path = Constant.ACTIVITY_URL_ForgetPasswordReset)
public class ForgetPasswordResetActivity extends BaseActivity<ActivityForgetPasswordResetBinding> implements ForgetPasswordResetView, View.OnClickListener {

    @Autowired(name = "email")
    String email;

    @Autowired(name = "code")
    String code;

    private ForgetPasswordResetPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initBtnOnClick();
        initTextWatcher();
    }

    private void initTextWatcher() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkPassword();
            }
        };
        binding.password.addTextChangedListener(watcher);
        binding.confirmPassword.addTextChangedListener(watcher);
    }

    private void checkPassword() {
        if (!binding.password.getText().toString().trim().isEmpty() & !binding.confirmPassword.getText().toString().trim().isEmpty()){
            binding.btnResetPassword.setBackgroundResource(R.drawable.bg_able_btn_login);
        }else {
            binding.btnResetPassword.setBackgroundResource(R.drawable.bg_unable_btn_login);
        }
    }

    private void initBtnOnClick() {
        binding.btnResetPassword.setOnClickListener(this);
    }

    private void initToolbar() {
        binding.toolbar.setTitle("重置密码");
        binding.toolbar.setTitleType(Typeface.BOLD);
        binding.toolbar.setTitleColor(R.color.blue);
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }

    public void setPresenter() {
        presenter = new ForgetPasswordResetPresenterImpl(this);
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getCode() {
        return code;
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
    public void onClick(View v) {
        presenter.switchOnclick(v);
    }

}
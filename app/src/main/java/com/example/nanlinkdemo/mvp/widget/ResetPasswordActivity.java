package com.example.nanlinkdemo.mvp.widget;


import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivityResetPasswordBinding;
import com.example.nanlinkdemo.mvp.presenter.Impl.ForgetPasswordPresenterImpl;
import com.example.nanlinkdemo.mvp.view.ForgetPasswordView;
import com.example.nanlinkdemo.util.Constant;


@Route(path = Constant.ACTIVITY_URL_ResetPassword)
public class ResetPasswordActivity extends BaseActivity<ActivityResetPasswordBinding> implements ForgetPasswordView, View.OnClickListener {

    @Autowired(name = "email")
    String email;
    @Autowired(name = "type")
    int type;

    public static final int Type_Forget_Password = 0;
    public static final int Type_Change_Password = 1;

    private ForgetPasswordPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initBtn();
        initEmail();
        initCode();
        initLogo();
    }

    private void initLogo() {
        if (type == Type_Forget_Password){
            binding.loginLogo.setVisibility(View.VISIBLE);
        }else if (type == Type_Change_Password){
            binding.loginLogo.setVisibility(View.GONE);
        }
    }

    private void initCode() {
        binding.etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               presenter.checkCode();
            }
        });
    }



    private void initEmail() {
        if (type == Type_Forget_Password){
            binding.etEmail.setVisibility(View.VISIBLE);
            binding.tvEmail.setVisibility(View.GONE);
            binding.etEmail.setText(email);
        }else if (type == Type_Change_Password){
            binding.etEmail.setVisibility(View.GONE);
            binding.tvEmail.setVisibility(View.VISIBLE);
            binding.tvEmail.setText(email);
        }
    }

    private void initBtn() {
        if (type == Type_Forget_Password){
            binding.btnResetPassword.setText("重置密码");
        }else if (type == Type_Change_Password){
            binding.btnResetPassword.setText("修改密码");
        }
        binding.btnGetCode.setOnClickListener(this);
        binding.btnResetPassword.setOnClickListener(this);
    }

    private void initToolbar() {
        if (type == Type_Forget_Password){
            binding.toolbar.setTitle("重置密码");
            binding.toolbar.setTitleType(Typeface.BOLD);
            binding.toolbar.setTitleColor(R.color.blue);
        }else if (type == Type_Change_Password){
            binding.toolbar.setTitle("修改密码");
        }
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }

    public void setPresenter() {
        presenter = new ForgetPasswordPresenterImpl(this);
    }

    @Override
    public String getEmail() {
        if (type == Type_Forget_Password){
            return binding.etEmail.getText().toString().trim();
        }else {
            return binding.tvEmail.getText().toString().trim();
        }
    }

    @Override
    public String getCode() {
        return binding.etCode.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        presenter.switchOnclick(v);
    }

    @Override
    public void updateGetCodeBtn(boolean able) {
        binding.btnGetCode.setClickable(able);
        if (able){
            binding.btnGetCode.setBackgroundResource(R.drawable.bg_able_btn_login);
        }else {
            binding.btnGetCode.setBackgroundResource(R.drawable.bg_unable_btn_login);
        }
    }

    @Override
    public void updateResetBtn(boolean able) {
        binding.btnResetPassword.setClickable(able);
        if (able){
            binding.btnResetPassword.setBackgroundResource(R.drawable.bg_able_btn_login);
        }else {
            binding.btnResetPassword.setBackgroundResource(R.drawable.bg_unable_btn_login);
        }
    }

    @Override
    public void updateGetCodeBtnText(String text) {
        binding.btnGetCode.setText(text);
    }
}
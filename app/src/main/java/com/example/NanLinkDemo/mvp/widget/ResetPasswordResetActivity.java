package com.example.NanLinkDemo.mvp.widget;


import static com.example.NanLinkDemo.mvp.widget.ResetPasswordActivity.Type_Change_Password;
import static com.example.NanLinkDemo.mvp.widget.ResetPasswordActivity.Type_Forget_Password;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.ActivityResetPasswordResetBinding;
import com.example.NanLinkDemo.mvp.presenter.Impl.ResetPasswordResetPresenterImpl;
import com.example.NanLinkDemo.mvp.view.ResetPasswordResetView;
import com.example.NanLinkDemo.util.Constant;


@Route(path = Constant.ACTIVITY_URL_ResetPasswordReset)
public class ResetPasswordResetActivity extends BaseActivity<ActivityResetPasswordResetBinding> implements ResetPasswordResetView, View.OnClickListener {

    @Autowired(name = "email")
    String email;

    @Autowired(name = "code")
    String code;

    @Autowired(name = "type")
    int type;

    private ResetPasswordResetPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initBtn();
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

    private void initBtn() {
        if (type == Type_Forget_Password){
            binding.btnResetPassword.setText("重置密码");
        }else if (type == Type_Change_Password){
            binding.btnResetPassword.setText("修改密码");
        }
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
        presenter = new ResetPasswordResetPresenterImpl(this);
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
    public int getType() {
        return type;
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
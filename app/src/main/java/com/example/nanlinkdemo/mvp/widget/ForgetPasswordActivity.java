package com.example.nanlinkdemo.mvp.widget;


import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivityForgetPasswordBinding;
import com.example.nanlinkdemo.mvp.presenter.Impl.ForgetPasswordPresenterImpl;
import com.example.nanlinkdemo.mvp.view.ForgetPasswordView;
import com.example.nanlinkdemo.util.Constant;
import com.example.nanlinkdemo.util.SnackBarUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Route(path = Constant.ACTIVITY_URL_ForgetPassword)
public class ForgetPasswordActivity extends BaseActivity<ActivityForgetPasswordBinding> implements ForgetPasswordView, View.OnClickListener {

    @Autowired(name = "email")
    String email;

    private ForgetPasswordPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initBtnOnClick();
        initEmail();
        initCode();
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
        binding.email.setText(email);
    }

    private void initBtnOnClick() {
        binding.btnGetCode.setOnClickListener(this);
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
        presenter = new ForgetPasswordPresenterImpl(this);
    }

    @Override
    public String getEmail() {
        return binding.email.getText().toString().trim();
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
    public void updateGetCodeBtn() {
        Observable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        binding.btnGetCode.setClickable(false);
                        binding.btnGetCode.setText("已发送 " + (60-aLong) + "s");
                        binding.btnGetCode.setBackgroundResource(R.drawable.bg_unable_btn_login);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        binding.btnGetCode.setBackgroundResource(R.drawable.bg_able_btn_login);
                        binding.btnGetCode.setText("获取验证码");
                        binding.btnGetCode.setClickable(true);
                    }
                })
                .subscribe();
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
}
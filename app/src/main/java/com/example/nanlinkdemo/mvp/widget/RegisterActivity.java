package com.example.nanlinkdemo.mvp.widget;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivityRegisterBinding;
import com.example.nanlinkdemo.mvp.presenter.Impl.RegisterPresenterImpl;
import com.example.nanlinkdemo.mvp.view.RegisterView;
import com.example.nanlinkdemo.ui.LoadingDialog;
import com.example.nanlinkdemo.util.Constant;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


@Route(path = Constant.ACTIVITY_URL_Register)
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> implements RegisterView, View.OnClickListener {

    private RegisterPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initBtnOnClick();
        initUserAgreement();
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
                presenter.checkRegisterMessage();
            }
        };
        binding.email.addTextChangedListener(watcher);
        binding.password.addTextChangedListener(watcher);
        binding.confirmPassword.addTextChangedListener(watcher);
        binding.nickName.addTextChangedListener(watcher);
        binding.etCode.addTextChangedListener(watcher);
    }



    private void initUserAgreement() {
        SpannableStringBuilder builder = new SpannableStringBuilder("我已阅读并同意NANLINK用户协议及NANLINK隐私条款");
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ARouter.getInstance().build(Constant.ACTIVITY_URL_WebView).withInt("contentId", Constant.USER_AGREEMENT).navigation();

            }
        }, 7, 18, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ARouter.getInstance().build(Constant.ACTIVITY_URL_WebView).withInt("contentId", Constant.PRIVACY_POLICY).navigation();

            }
        }, 19, 30, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, 30, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        binding.tvUserAgreementPrivacyPolicy.setText(builder);
        binding.tvUserAgreementPrivacyPolicy.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initBtnOnClick() {
        binding.btnRegister.setOnClickListener(this);
        binding.btnGetCode.setOnClickListener(this);
        binding.loginCheck.setOnClickListener(this);
    }

    private void initToolbar() {
        binding.toolbar.setTitle("注册");
        binding.toolbar.setTitleType(Typeface.BOLD);
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
    public void updateGetCodeBtn(boolean able) {
        binding.btnGetCode.setClickable(able);
        if (able){
            binding.btnGetCode.setBackgroundResource(R.drawable.bg_able_btn_login);
        }else {
            binding.btnGetCode.setBackgroundResource(R.drawable.bg_unable_btn_login);
        }


    }

    @Override
    public void updatedRegisterBtnBg(int res) {
        binding.btnRegister.setBackgroundResource(res);
    }

    @Override
    public void updateGetCodeBtnText(String text) {
        binding.btnGetCode.setText(text);
    }


    @Override
    public void onClick(View view) {
        presenter.switchOnclick(view);
    }


}

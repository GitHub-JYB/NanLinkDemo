package com.example.nanlinkdemo.mvp.widget;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivityLoginBinding;
import com.example.nanlinkdemo.mvp.presenter.Impl.LoginPresenterImpl;
import com.example.nanlinkdemo.mvp.view.LoginView;
import com.example.nanlinkdemo.ui.LoadingDialog;
import com.example.nanlinkdemo.ui.MyDialog;
import com.example.nanlinkdemo.util.Constant;
import com.example.nanlinkdemo.util.SpUtil;

@Route(path = Constant.ACTIVITY_URL_Login)
public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements LoginView, View.OnClickListener {


    private LoginPresenterImpl presenter;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPresenter();
        initToolbar();
        initUserAgreement();
        initForgetPassword();
        initUsername();
    }

    private void initForgetPassword() {
        SpannableStringBuilder builder = new SpannableStringBuilder("忘记密码? 或 访客模式");
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ARouter.getInstance().build(Constant.ACTIVITY_URL_ForgetPassword).withString("email", getEmail()).navigation();

            }
        }, 0, 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.login_hintText)), 0, 12, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        binding.tvForgetPassword.setText(builder);
        binding.tvForgetPassword.setMovementMethod(LinkMovementMethod.getInstance());
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

    private void initUsername() {
        binding.email.setText(SpUtil.getIntance(getBaseContext()).getUsername());
    }

    private void initToolbar() {
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
    public void saveLogin() {
        SpUtil.getIntance(getBaseContext()).setLogin(true);
    }


    @Override
    public void onClick(View view) {
        presenter.switchOnclick(view);
    }
}

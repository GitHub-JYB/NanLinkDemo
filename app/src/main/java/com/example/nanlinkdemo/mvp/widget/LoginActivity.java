package com.example.nanlinkdemo.mvp.widget;

import android.os.Bundle;
import android.view.View;


import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
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
    public void starLoading() {
        dialog = new LoadingDialog();
        dialog.show(getSupportFragmentManager(),"LoadingDialog");
    }

    @Override
    public void stopLoading() {
        dialog.dismiss();
    }

    @Override
    public void showMistakeDialog(String title, String message, int type) {
        MyDialog dialog = new MyDialog();
        dialog.setType(type);
        dialog.setTitle(title);
        dialog.setTitleTextColorResId(R.color.warning);
        if (type == 0) {
            dialog.setMessage(message);
            dialog.setNeutralText("重试");
            dialog.setNeutralOnClickListener(new MyDialog.NeutralOnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show(getSupportFragmentManager(), "MyDialog");


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

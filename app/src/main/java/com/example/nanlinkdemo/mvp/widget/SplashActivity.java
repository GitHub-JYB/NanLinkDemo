package com.example.nanlinkdemo.mvp.widget;


import android.os.Bundle;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.DataBase.MyDataBase;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivitySplashBinding;
import com.example.nanlinkdemo.mvp.presenter.Impl.SplashPresenterImpl;
import com.example.nanlinkdemo.mvp.view.SplashView;
import com.example.nanlinkdemo.util.Constant;
import com.example.nanlinkdemo.util.SpUtil;


import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class SplashActivity extends BaseActivity<ActivitySplashBinding> implements SplashView {


    private SplashPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setPresenter();
        startCountDown();
    }

    private void startCountDown() {
        presenter.startCountDownFromModel();
    }

    private void setPresenter() {
        presenter = new SplashPresenterImpl(this);
    }

}

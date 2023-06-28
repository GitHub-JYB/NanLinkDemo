package com.example.NanLinkDemo.mvp.widget;


import android.os.Bundle;



import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.ActivitySplashBinding;
import com.example.NanLinkDemo.mvp.presenter.Impl.SplashPresenterImpl;
import com.example.NanLinkDemo.mvp.view.SplashView;


public class SplashActivity extends BaseActivity<ActivitySplashBinding> implements SplashView {


    private SplashPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setPresenter();
        startCountDown();
        getDeviceList();
    }

    private void getDeviceList() {
        presenter.getDeviceListFromModel();
    }

    private void startCountDown() {
        presenter.startCountDownFromModel();
    }

    private void setPresenter() {
        presenter = new SplashPresenterImpl(this);
    }

}

package com.example.nanlinkdemo.mvp.widget;


import android.content.Intent;
import android.os.Bundle;


import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivitySplashBinding;
import com.example.nanlinkdemo.util.SpUtil;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class SplashActivity extends BaseActivity<ActivitySplashBinding> {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        finishSplash();
    }


    /**
     * 定时1秒结束欢迎界面
     */
    private void finishSplash() {
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (SpUtil.getIntance(getBaseContext()).getUsername() == ""){
                            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                            finish();
                        }else {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

}

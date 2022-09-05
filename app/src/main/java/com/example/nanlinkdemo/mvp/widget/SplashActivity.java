package com.example.nanlinkdemo.mvp.widget;


import android.os.Bundle;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivitySplashBinding;
import com.example.nanlinkdemo.util.Constant;
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
                        if (SpUtil.getIntance(getBaseContext()).isLogin()){
                            ARouter.getInstance().build(Constant.ACTIVITY_URL_Main).navigation();
                            finish();
                        }else {
                            ARouter.getInstance().build(Constant.ACTIVITY_URL_Login).navigation();
                            finish();
                        }
                    }
                });
    }

}

package com.example.NanLinkDemo.mvp.widget;


import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.databinding.ActivityTestBinding;
import com.example.NanLinkDemo.util.Constant;

@Route(path = Constant.ACTIVITY_URL_Test)
public class TestActivity extends BaseActivity<ActivityTestBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
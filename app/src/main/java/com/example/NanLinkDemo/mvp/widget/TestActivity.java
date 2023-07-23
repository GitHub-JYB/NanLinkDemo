package com.example.NanLinkDemo.mvp.widget;


import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.databinding.ActivityTestBinding;
import com.example.NanLinkDemo.ui.HsiView;
import com.example.NanLinkDemo.util.Constant;

@Route(path = Constant.ACTIVITY_URL_Test)
public class TestActivity extends BaseActivity<ActivityTestBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.view.setOnDataChangeListener(new HsiView.OnDataChangeListener() {
            @Override
            public void onProgressChanged(HsiView hsiView, int HSI, int SAT) {
                binding.HSI.setText("HSI: " + HSI);
                binding.SAT.setText("SAT: " + SAT);
            }

            @Override
            public void onStopTrackingTouch(HsiView hsiView) {

            }
        });
    }
}
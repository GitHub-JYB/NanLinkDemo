package com.example.nanlinkdemo.mvp.widget;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;
import static com.example.nanlinkdemo.util.Constant.ACTIVITY_URL_Test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivityTestBinding;

import java.util.ArrayList;

@Route(path = ACTIVITY_URL_Test)
public class TestActivity extends BaseActivity<ActivityTestBinding> implements View.OnClickListener {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        Log.d(TAG, "onCreate: 111111111111111111111111111");
        initBoxView();
    }

    private void initBoxView() {
        binding.box.setTitle("灯具类型");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("单色温");
        arrayList.add("双色温");
        arrayList.add("全彩");
        binding.box.setData(arrayList);


    }

    private void initToolbar() {
        binding.toolbar.setTitle("测试");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }







    @Override
    public void onClick(View v) {
        finish();
    }
}

package com.example.nanlinkdemo.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.databinding.MnavigationBinding;
import com.example.nanlinkdemo.mvp.adapter.MenuAdapter;

import java.util.ArrayList;


public class MyNavigation extends RelativeLayout {




    MnavigationBinding binding;
    private MenuAdapter menuAdapter;

    public MyNavigation(Context context) {
        this(context,null);
    }

    public MyNavigation(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyNavigation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyNavigation(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {

        binding = MnavigationBinding.inflate(LayoutInflater.from(getContext()),this, true);

        initStatusBar();
        initRecycleView();
    }

    private void initStatusBar() {
        // 动态设置statusBar的高度
        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) binding.statusBar.getLayoutParams();
        lp.height= MyApplication.statusHigh;
        binding.statusBar.setLayoutParams(lp);
    }

    // 初始化RecycleView
    public void initRecycleView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 设置分割线格式并添加
        UnlessLastItemDecoration decoration = new UnlessLastItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getContext().getDrawable(R.drawable.decoration_menu));
        binding.recyclerView.addItemDecoration(decoration);

        menuAdapter = new MenuAdapter();
        binding.recyclerView.setAdapter(menuAdapter);
    }

    // 设置选项点击事件
    public void setItemOnClickListener(MenuAdapter.OnClickListener li){
        menuAdapter.setOnClickListener(li);
    }

    // 设置选项点击事件
    public void setData(ArrayList<Menu> menuArrayList){
        menuAdapter.setData(menuArrayList);
    }

    // 设置版本文本
    public void setVersion(String version){
        binding.version.setText(version);
        binding.version.setVisibility(VISIBLE);
    }

}

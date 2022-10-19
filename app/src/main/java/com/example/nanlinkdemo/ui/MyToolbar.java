package com.example.nanlinkdemo.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.databinding.MtoolbarBinding;


public class MyToolbar extends RelativeLayout {




    MtoolbarBinding binding;

    public MyToolbar(Context context) {
        this(context,null);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {

        binding = MtoolbarBinding.inflate(LayoutInflater.from(getContext()),this, true);

        // 动态设置statusBar的高度
        LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) binding.statusBar.getLayoutParams();
        lp.height= MyApplication.statusHight;
        binding.statusBar.setLayoutParams(lp);
    }

    // 设置左侧按键点击事件
    public void setLeftBtnOnClickListener(OnClickListener li){
        binding.toolbarLeftBtn.setOnClickListener(li);
    }

    // 设置右侧按键点击事件
    public void setRightBtnOnClickListener(OnClickListener li){
        binding.toolbarRightBtn.setOnClickListener(li);
    }

    // 设置标题
    public void setTitle(String title){
        binding.toolbarTitle.setText(title);
    }

    // 设置标题颜色
    public void setTitleColor(int color){
        binding.toolbarTitle.setTextColor(getResources().getColor(color));
    }

    // 设置标题字体类型
    public void setTitleType(int type){
        binding.toolbarTitle.setTypeface(null, type);
    }

    // 设置左侧按键图标
    public void setLeftBtnIcon(int resId){
        binding.toolbarLeftBtn.setImageResource(resId);
        binding.toolbarLeftBtn.setVisibility(VISIBLE);
    }

    // 隐藏左侧按键图标
    public void hideLeftBtnIcon(){
        binding.toolbarLeftBtn.setVisibility(GONE);
    }

    // 设置右侧按键图标
    public void setRightBtnIcon(int resId){
        binding.toolbarRightBtn.setImageResource(resId);
        binding.toolbarRightBtn.setVisibility(VISIBLE);
    }

    // 隐藏右侧按键图标
    public void hideRightBtnIcon(){
        binding.toolbarRightBtn.setVisibility(GONE);
    }

}

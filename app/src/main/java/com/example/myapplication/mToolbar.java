package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class mToolbar extends RelativeLayout {

    @BindView(R.id.toolbar_left_btn)
    ImageButton leftBtn;
    @BindView(R.id.toolbar_right_btn)
    ImageButton rightBtn;
    @BindView(R.id.toolbar_title)
    TextView titleTv;


    public mToolbar(Context context) {
        this(context,null);
    }

    public mToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public mToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public mToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.mtoolbar,this);
        ButterKnife.bind(this);



    }

    // 设置左侧按键点击事件
    public void setLeftBtnOnClickListener(OnClickListener li){
        leftBtn.setOnClickListener(li);
    }

    // 设置右侧按键点击事件
    public void setRightBtnOnClickListener(OnClickListener li){
        rightBtn.setOnClickListener(li);
    }

    // 设置标题
    public void setTitle(String title){
        titleTv.setText(title);
    }

    // 设置左侧按键图标
    public void setLeftBtnIcon(int resId){
        leftBtn.setImageResource(resId);
        leftBtn.setVisibility(VISIBLE);
    }

    // 设置右侧按键图标
    public void setRightBtnIcon(int resId){
        rightBtn.setImageResource(resId);
        rightBtn.setVisibility(VISIBLE);
    }



}

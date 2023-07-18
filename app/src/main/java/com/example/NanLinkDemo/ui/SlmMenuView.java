package com.example.NanLinkDemo.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.BoxviewBinding;
import com.example.NanLinkDemo.databinding.SlmmenuviewBinding;

import java.util.ArrayList;

public class SlmMenuView extends RelativeLayout {

    private SlmmenuviewBinding binding;

    private int width = MyApplication.dip2px(84);
    private int height = MyApplication.dip2px(40);
    private int index = 0;

    private float textSize = 12;
    private OnCheckedChangeListener onCheckedChangeListener;

    public SlmMenuView(Context context) {
        this(context, null);
    }

    public SlmMenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlmMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SlmMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        binding = SlmmenuviewBinding.inflate(LayoutInflater.from(getContext()), this, true);


    }

    // 设置是否隐藏
    public void setVisibility(int visibility){
        binding.getRoot().setVisibility(visibility);
    }

    //设置控件标题名称
    public void setTitle(String title) {
        binding.title.setText(title);
    }

    //设置按键高度
    public void setHeight(int height) {
        this.height = height;
    }

    //设置按键宽度
    public void setWidth(int width) {
        this.width = width;
    }

    //设置按键文字大小,单位sp
    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    //设置按键默认选的位置
    public void check(int index) {
        this.index = index;
    }

    //设置控件按键的切换事件
    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener{
        void onCheckedChanged(int index);
    }
}

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

import java.util.ArrayList;

public class BoxView extends RelativeLayout {

    private BoxviewBinding binding;

    private int width = MyApplication.dip2px(84);
    private int height = MyApplication.dip2px(40);
    private int index = 0;

    private float textSize = 12;
    private OnCheckedChangeListener onCheckedChangeListener;

    public BoxView(Context context) {
        this(context, null);
    }

    public BoxView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BoxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        binding = BoxviewBinding.inflate(LayoutInflater.from(getContext()), this, true);


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

    public int getCheck(){
        return index;
    }

    //设置控件按键列表数据,需要最后面调用
    public void setData(ArrayList<String> dataList) {
        binding.group.removeAllViews();
        if (dataList.size() != 0) {
            for (int i = 0; i < dataList.size(); i++) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setLayoutParams(new ViewGroup.LayoutParams(width, height));
                radioButton.setBackgroundResource(R.drawable.bg_selector_btn_boxview);
                radioButton.setGravity(Gravity.CENTER);
                radioButton.setText(dataList.get(i));
                radioButton.setTextColor(getResources().getColor(R.color.white));
                radioButton.setButtonDrawable(new BitmapDrawable((Bitmap) null));
                radioButton.setId(i);
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                binding.group.addView(radioButton);

            }
            binding.group.clearCheck();
            binding.group.check(index < dataList.size() ? index : 0);
            binding.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (onCheckedChangeListener != null){
                        onCheckedChangeListener.onCheckedChanged(checkedId);
                    }
                }
            });
        }
    }

    //设置控件按键的切换事件
    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener{
        void onCheckedChanged(int index);
    }
}

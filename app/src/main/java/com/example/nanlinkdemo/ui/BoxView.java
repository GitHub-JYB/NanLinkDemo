package com.example.nanlinkdemo.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.BoxviewBinding;
import com.example.nanlinkdemo.mvp.adapter.BoxViewAdapter;

import java.util.ArrayList;

public class BoxView extends RelativeLayout {

    private BoxviewBinding binding;
    private ArrayList<String> dataList = new ArrayList<String>();

    public BoxView(Context context) {
        this(context,null);
    }

    public BoxView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BoxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BoxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        binding = BoxviewBinding.inflate(LayoutInflater.from(getContext()),this, true);
        dataList.add("单色温");
        dataList.add("双色温");
        dataList.add("全彩");
        if (dataList.size() != 0){
            for (int i = 0; i < dataList.size(); i++){
                RadioButton radioButton = new RadioButton(getContext());
//                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)radioButton.getLayoutParams();
//                lp.width = MyApplication.dip2px(84);
//                lp.height = MyApplication.dip2px(40);
                radioButton.setLayoutParams(new ViewGroup.LayoutParams(MyApplication.dip2px(84),MyApplication.dip2px(40)));
                radioButton.setBackgroundResource(R.drawable.bg_btn_boxview);
                radioButton.setGravity(Gravity.CENTER);
                radioButton.setText(dataList.get(i));
                radioButton.setTextColor(getResources().getColor(R.color.white));
                radioButton.setButtonDrawable(new BitmapDrawable((Bitmap) null));
                radioButton.setId(i);
                binding.group.addView(radioButton);

            }
            binding.group.check(0);

        }

    }

    //设置控件标题名称
    public void setTitle(String title){
        binding.title.setText(title);
    }

    //设置控件按键列表数据
    public void setData(ArrayList<String> dataList){
        this.dataList = dataList;
        invalidate();
    }

    //设置控件按键的点击事件
    public void setOnClickListener(BoxViewAdapter.OnClickListener listener){
//        adapter.setOnClickListener(listener);
    }
}

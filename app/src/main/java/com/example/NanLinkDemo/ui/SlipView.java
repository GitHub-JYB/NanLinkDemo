package com.example.NanLinkDemo.ui;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.databinding.SlipviewBinding;

import java.util.ArrayList;

public class SlipView extends RelativeLayout {

    private SlipviewBinding binding;


    private OnCheckedChangeListener onCheckedChangeListener;

    public SlipView(Context context) {
        this(context, null);
    }

    public SlipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SlipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        binding = SlipviewBinding.inflate(LayoutInflater.from(getContext()), this, true);
        binding.getRoot().setPadding(MyApplication.dip2percentPx(19), MyApplication.dip2percentPx(8), MyApplication.dip2percentPx(19), MyApplication.dip2percentPx(4));
        ViewGroup.LayoutParams layoutParams = binding.content.getLayoutParams();
        layoutParams.height = MyApplication.dip2percentPx(46);
        binding.content.setLayoutParams(layoutParams);
        layoutParams = binding.delayBtn.getLayoutParams();
        layoutParams.width = MyApplication.dip2percentPx(44);
        layoutParams.height = MyApplication.dip2percentPx(44);
        binding.delayBtn.setLayoutParams(layoutParams);
        layoutParams = binding.delayTime.getLayoutParams();
        layoutParams.width = MyApplication.dip2percentPx(44);
        layoutParams.height = MyApplication.dip2percentPx(44);
        binding.delayTime.setLayoutParams(layoutParams);
        layoutParams = binding.add.getLayoutParams();
        layoutParams.width = MyApplication.dip2percentPx(38);
        layoutParams.height = MyApplication.dip2percentPx(38);
        binding.add.setLayoutParams(layoutParams);
        layoutParams = binding.reduce.getLayoutParams();
        layoutParams.width = MyApplication.dip2percentPx(38);
        layoutParams.height = MyApplication.dip2percentPx(38);
        binding.reduce.setLayoutParams(layoutParams);
        layoutParams = binding.groupLogo.getLayoutParams();
        layoutParams.width = MyApplication.dip2percentPx(15);
        layoutParams.height = MyApplication.dip2percentPx(13);
        binding.groupLogo.setLayoutParams(layoutParams);
        layoutParams = binding.seekbar.getLayoutParams();
        layoutParams.width = MyApplication.dip2percentPx(246);
        binding.seekbar.setLayoutParams(layoutParams);
        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //设置控件标题名称
    public void setTitle(String title) {
        binding.title.setText(title);
    }

    //设置控件标题可见性
    public void setTitleVisibility(int visibility) {
        binding.title.setVisibility(visibility);
    }

    //设置控件CH名称
    public void setCH(String CH) {
        binding.CH.setText(CH);
    }

    //设置控件CH可见性
    public void setCHVisibility(int visibility) {
        binding.CH.setVisibility(visibility);
    }

    //设置控件名称
    public void setName(String title) {
        binding.name.setText(title);
    }

    //设置控件名称可见性
    public void setNameVisibility(int visibility) {
        binding.name.setVisibility(visibility);
    }

    //设置延时开关时间
    public void setDelayTime(String title) {
        binding.delayTime.setText(title);
    }

    //设置控件延时开关时间可见性
    public void setDelayTimeVisibility(int visibility) {
        binding.delayTime.setVisibility(visibility);
    }

    //设置控件开关可见性
    public void setDelayBtnVisibility(int visibility) {
        binding.delayBtn.setVisibility(visibility);
    }

    //设置控件群组图标可见性
    public void setGroupLogoVisibility(int visibility) {
        binding.groupLogo.setVisibility(visibility);
    }

    //设置进度
    public void setProgress(int progress) {
        binding.seekbar.setProgress(progress);
    }

    //设置进度条参数
    public void setSeekBar(int max, int min, int progress) {
        binding.seekbar.setMax(max);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.seekbar.setMin(min);
        }
        binding.seekbar.setProgress(progress);
    }

    //设置控件按键的切换事件
    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener{
        void onCheckedChanged(int index);
    }
}

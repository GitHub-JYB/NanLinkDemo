package com.example.NanLinkDemo.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.SlipviewBinding;

public class SlipView extends RelativeLayout {

    private SlipviewBinding binding;


    private OnDataChangeListener onDataChangeListener;
    private int max, min, step, value;

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
        binding.delayBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                value = -(value - min) + min;
                updateView(value);
                if (onDataChangeListener != null){
                    onDataChangeListener.onDataChanged(value);
                }
            }
        });
        layoutParams = binding.delayTime.getLayoutParams();
        layoutParams.width = MyApplication.dip2percentPx(44);
        layoutParams.height = MyApplication.dip2percentPx(44);
        binding.delayTime.setLayoutParams(layoutParams);
        layoutParams = binding.add.getLayoutParams();
        layoutParams.width = MyApplication.dip2percentPx(38);
        layoutParams.height = MyApplication.dip2percentPx(38);
        binding.add.setLayoutParams(layoutParams);
        binding.add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value < max) {
                    value += step;
                    updateView(value);
                    if (onDataChangeListener != null){
                        onDataChangeListener.onDataChanged(value);
                    }
                }
            }
        });
        layoutParams = binding.reduce.getLayoutParams();
        layoutParams.width = MyApplication.dip2percentPx(38);
        layoutParams.height = MyApplication.dip2percentPx(38);
        binding.reduce.setLayoutParams(layoutParams);
        binding.reduce.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value > min) {
                    value -= step;
                    updateView(value);
                    if (onDataChangeListener != null){
                        onDataChangeListener.onDataChanged(value);
                    }
                }
            }
        });
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
                value = progress * step + min;
                updateView(value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (onDataChangeListener != null){
                    onDataChangeListener.onDataChanged(value);
                }
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

    //设置数据
    public void setData(String data) {
        binding.data.setText(data);
    }

    //获取数据
    public CharSequence getData() {
        return binding.data.getText();
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


    //设置进度条参数
    public void setSeekBar(int max, int min, int step, int value) {
        this.max = max;
        this.min = min;
        this.step = step;
        this.value = value;
        binding.seekbar.setMax((max - min) / step);
        updateView(value);
    }

    private void updateView(int value) {
        if (value >= min) {
            binding.seekbar.setProgress((value - min) / step);
            Rect bounds = binding.seekbar.getProgressDrawable().getBounds();
            binding.seekbar.setProgressDrawable(getResources().getDrawable(R.drawable.bg_on_seekbar_slipview));
            binding.seekbar.getProgressDrawable().setBounds(bounds);
            binding.seekbar.setThumb(getResources().getDrawable(R.drawable.bg_selector_thumb_seekbar_on));
            binding.data.setTextColor(getResources().getColor(R.color.blue));
            binding.data.setText(String.valueOf(value));
            binding.delayTime.setClickable(true);
            binding.delayTime.setTextColor(getResources().getColor(R.color.blue));
            binding.delayBtn.setClickable(true);
            binding.delayBtn.setImageResource(R.drawable.ic_dim_open);
            binding.add.setImageResource(R.drawable.ic_slip_add);
            binding.reduce.setImageResource(R.drawable.ic_slip_reduce);
            if (value == min) {
                binding.delayTime.setClickable(false);
                binding.delayTime.setTextColor(getResources().getColor(R.color.unable));
                binding.delayBtn.setClickable(false);
                binding.delayBtn.setImageResource(R.drawable.ic_dim_close);
            }
        } else {
            binding.seekbar.setProgress((-(value - min) + min) / step);
            Rect bounds = binding.seekbar.getProgressDrawable().getBounds();
            binding.seekbar.setProgressDrawable(getResources().getDrawable(R.drawable.bg_off_seekbar_slipview));
            binding.seekbar.getProgressDrawable().setBounds(bounds);
            binding.seekbar.setThumb(getResources().getDrawable(R.drawable.bg_selector_thumb_seekbar_off));
            binding.data.setTextColor(getResources().getColor(R.color.unable));
            binding.data.setText(String.valueOf(-(value - min) + min));
            binding.delayTime.setClickable(true);
            binding.delayTime.setTextColor(getResources().getColor(R.color.unable));
            binding.delayBtn.setClickable(true);
            binding.delayBtn.setImageResource(R.drawable.ic_dim_close);
            binding.add.setImageResource(R.drawable.ic_slip_add_off);
            binding.reduce.setImageResource(R.drawable.ic_slip_reduce_off);
        }
    }

    //设置控件按键的切换事件
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }

    public interface OnDataChangeListener {
        void onDataChanged(int index);
    }
}

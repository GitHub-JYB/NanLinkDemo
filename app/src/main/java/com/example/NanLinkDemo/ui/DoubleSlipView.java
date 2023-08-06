package com.example.NanLinkDemo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.DoubleslipviewBinding;

public class DoubleSlipView extends RelativeLayout {

    private DoubleslipviewBinding binding;


    private OnDataChangeListener onDataChangeListener;
    private int max, min, step, minValue, maxValue;
    private int delayTime = 2;
    private String remark = "";
    private OnDataClickListener onDataClickListener;
    private OnDelayTimeClickListener onDelayTimeClickListener;
    private int onDataColor = R.color.white;

    public DoubleSlipView(Context context) {
        this(context, null);
    }

    public DoubleSlipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleSlipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DoubleSlipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        binding = DoubleslipviewBinding.inflate(LayoutInflater.from(getContext()), this, true);
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
                maxValue = -(maxValue - min) + min;
                minValue = -(minValue - min) + min;
                updateView(maxValue, minValue);
                if (onDataChangeListener != null) {
                    onDataChangeListener.onDataChanged(maxValue, minValue);
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
                if (binding.seekbar.getIsClickMinItem()) {
                    if (minValue < maxValue) {
                        minValue += step;
                        updateView(maxValue, minValue);
                        if (onDataChangeListener != null) {
                            onDataChangeListener.onDataChanged(maxValue, minValue);
                        }
                    }
                } else {
                    if (maxValue < max) {
                        maxValue += step;
                        updateView(maxValue, minValue);
                        if (onDataChangeListener != null) {
                            onDataChangeListener.onDataChanged(maxValue, minValue);
                        }
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
                if (binding.seekbar.getIsClickMinItem()) {
                    if (minValue > min) {
                        minValue -= step;
                        updateView(maxValue, minValue);
                        if (onDataChangeListener != null) {
                            onDataChangeListener.onDataChanged(maxValue, minValue);
                        }
                    }
                } else {
                    if (maxValue > minValue) {
                        maxValue -= step;
                        updateView(maxValue, minValue);
                        if (onDataChangeListener != null) {
                            onDataChangeListener.onDataChanged(maxValue, minValue);
                        }
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
        layoutParams.height = MyApplication.dip2percentPx(38);
        binding.seekbar.setLayoutParams(layoutParams);
        binding.seekbar.setOnDoubleSeekBarChangeListener(new DoubleSeekBar.OnDoubleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(DoubleSeekBar doubleSlideSeekBar, int maxItem, int minItem) {
                maxValue = maxItem * step + min;
                minValue = minItem * step + min;
                updateView(maxValue, minValue);
                if (onDataChangeListener != null) {
                    onDataChangeListener.onDataChanging(maxValue, minValue);
                }
            }

            @Override
            public void onStopTrackingTouch(DoubleSeekBar doubleSlideSeekBar) {
                if (onDataChangeListener != null) {
                    onDataChangeListener.onDataChanged(maxValue, minValue);
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
        binding.CH.setVisibility(VISIBLE);
    }


    //设置控件CH可见性
    public void setCHVisibility(int visibility) {
        binding.CH.setVisibility(visibility);
    }

    //设置remark, 在设置进度之前调用
    public void setRemark(String remark) {
        this.remark = remark;
    }

    // 设置数值亮时的颜色
    public void setOnDataColor(int onDataColor) {
        this.onDataColor = onDataColor;
    }

    //获取大数值
    public CharSequence getMaxData() {
        return String.valueOf(maxValue);
    }

    //获取小数值
    public CharSequence getMinData() {
        return String.valueOf(minValue);
    }


    //设置控件名称
    public void setName(String title) {
        binding.name.setText(title);
        binding.name.setVisibility(VISIBLE);
    }

    //设置控件名称可见性
    public void setNameVisibility(int visibility) {
        binding.name.setVisibility(visibility);
    }

    //设置延时开关时间
    public void setDelayTime(int delayTime) {
        binding.delayTime.setText(delayTime + "秒");
        binding.delayTime.setVisibility(VISIBLE);
    }

    //获取延时开关时间
    public CharSequence getDelayTime() {
        return String.valueOf(delayTime);
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
    public void setSeekBar(int max, int min, int step, int maxValue, int minValue) {
        this.max = max;
        this.min = min;
        this.step = step;
        this.maxValue = maxValue;
        this.minValue = minValue;
        binding.seekbar.setMax((max - min) / step);
        binding.seekbar.setIsClickMinItem(false);
        updateView(maxValue, minValue);
    }

    private void updateView(int maxValue, int minValue) {
        if (minValue >= min && maxValue >= min) {
            binding.seekbar.setProgress((maxValue - min) / step, (minValue - min) / step);
            binding.seekbar.setIsOff(false);
            binding.data.setTextColor(getResources().getColor(onDataColor));
            binding.data.setText(transformData(minValue) + " - " + transformData(maxValue));
            binding.data.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDataClickListener != null) {
                        onDataClickListener.onDataClick(view);
                    }
                }
            });
            binding.delayTime.setClickable(true);
            binding.delayTime.setTextColor(getResources().getColor(R.color.blue));
            binding.delayTime.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDelayTimeClickListener != null) {
                        onDelayTimeClickListener.onDelayTimeClick(view);
                    }
                }
            });
            binding.delayBtn.setClickable(true);
            binding.delayBtn.setImageResource(R.drawable.ic_dim_open);
            binding.add.setImageResource(R.drawable.ic_slip_add);
            binding.reduce.setImageResource(R.drawable.ic_slip_reduce);
            if (minValue == min && maxValue == min) {
                binding.delayTime.setClickable(false);
                binding.delayTime.setTextColor(getResources().getColor(R.color.unable));
                binding.delayBtn.setClickable(false);
                binding.delayBtn.setImageResource(R.drawable.ic_dim_close);
            }
        } else {
            binding.seekbar.setProgress((-(maxValue - min) + min) / step, (-(minValue - min) + min) / step);
            binding.seekbar.setIsOff(true);
            binding.data.setTextColor(getResources().getColor(R.color.unable));
            binding.data.setText(transformData(-(minValue - min) + min) + " - " + transformData(-(maxValue - min) + min));
            binding.delayTime.setClickable(true);
            binding.delayTime.setTextColor(getResources().getColor(R.color.unable));
            binding.delayBtn.setClickable(true);
            binding.delayBtn.setImageResource(R.drawable.ic_dim_close);
            binding.add.setImageResource(R.drawable.ic_slip_add_off);
            binding.reduce.setImageResource(R.drawable.ic_slip_reduce_off);
        }
    }

    private String transformData(int value) {
        switch (remark) {
            case "亮度":
            case "饱和度":
            case "亮暗比例":
            case "亮度范围":
                return value + "%";
            case "色温":
            case "色温偏移":
            case "色温范围":
                return value + "K";
            case "速度":
                return value + " 秒";
            case "间隔范围":
                return value + "s";
            case "间隔":
            case "时间间隔":
                return value / 1000.0 + " 秒";
        }
        return String.valueOf(value);
    }

    //设置数据更新监听事件
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }

    public interface OnDataChangeListener {
        void onDataChanging(int maxItem, int minItem);
        void onDataChanged(int maxItem, int minItem);
    }

    //设置数据点击监听事件
    public void setOnDataClickListener(OnDataClickListener onDataClickListener) {
        this.onDataClickListener = onDataClickListener;
    }

    public interface OnDataClickListener {
        void onDataClick(View view);
    }

    //设置延时时间点击监听事件
    public void setOnDelayTimeClickListener(OnDelayTimeClickListener onDelayTimeClickListener) {
        this.onDelayTimeClickListener = onDelayTimeClickListener;
    }

    public interface OnDelayTimeClickListener {
        void onDelayTimeClick(View view);
    }
}

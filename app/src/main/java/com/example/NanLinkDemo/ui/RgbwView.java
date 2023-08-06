package com.example.NanLinkDemo.ui;

import android.content.Context;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.HsiviewBinding;
import com.example.NanLinkDemo.databinding.RgbwviewBinding;

public class RgbwView extends RelativeLayout {

    private RgbwviewBinding binding;

    private int r, g, b, w;

    private OnDataChangeListener onDataChangeListener;

    public RgbwView(Context context) {
        this(context, null);
    }

    public RgbwView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RgbwView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RgbwView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        binding = RgbwviewBinding.inflate(LayoutInflater.from(getContext()), this, true);
        setR(r);
        setG(g);
        setB(b);
        setW(w);
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.index_0:
                        binding.viewIndex0.setVisibility(VISIBLE);
                        binding.viewIndex1.setVisibility(GONE);
                        break;
                    case R.id.index_1:
                        binding.viewIndex0.setVisibility(GONE);
                        binding.viewIndex1.setVisibility(VISIBLE);
                        break;
                }
            }
        });

        binding.rgbwColor.setOnDataChangeListener(new RgbwColorView.OnDataChangeListener() {
            @Override
            public void onProgressChanged(RgbwColorView rgbwColorView, int r, int g, int b, int w) {
                setR(r);
                setG(g);
                setB(b);
                setW(w);

            }

            @Override
            public void onStopTrackingTouch(RgbwColorView rgbwColorView) {
                if (onDataChangeListener != null) {
                    onDataChangeListener.onDataChanged(r, g, b, w);
                }
            }
        });
        binding.rSlip.setTitle("红 R");
        binding.rSlip.setDelayTimeVisibility(View.GONE);
        binding.rSlip.setDelayBtnVisibility(View.GONE);
        binding.rSlip.setSeekBar(255, 0, 1, r);
        binding.rSlip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
            @Override
            public void onDataChanging(int index) {

            }

            @Override
            public void onDataChanged(int index) {
                r = index;
                setR(r);
                binding.rgbwColor.clearPointer();
                if (onDataChangeListener != null) {
                    onDataChangeListener.onRDataChanged(r);
                }
            }
        });

        binding.gSlip.setTitle("绿 G");
        binding.gSlip.setDelayTimeVisibility(View.GONE);
        binding.gSlip.setDelayBtnVisibility(View.GONE);
        binding.gSlip.setSeekBar(255, 0, 1, g);
        binding.gSlip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
            @Override
            public void onDataChanging(int index) {

            }

            @Override
            public void onDataChanged(int index) {
                g = index;
                setG(g);
                binding.rgbwColor.clearPointer();
                if (onDataChangeListener != null) {
                    onDataChangeListener.onGDataChanged(g);
                }
            }
        });

        binding.bSlip.setTitle("蓝 B");
        binding.bSlip.setDelayTimeVisibility(View.GONE);
        binding.bSlip.setDelayBtnVisibility(View.GONE);
        binding.bSlip.setSeekBar(255, 0, 1, b);
        binding.bSlip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
            @Override
            public void onDataChanging(int index) {

            }

            @Override
            public void onDataChanged(int index) {
                b = index;
                setB(b);
                binding.rgbwColor.clearPointer();
                if (onDataChangeListener != null) {
                    onDataChangeListener.onBDataChanged(b);
                }
            }
        });

        binding.wSlip.setTitle("白 W");
        binding.wSlip.setDelayTimeVisibility(View.GONE);
        binding.wSlip.setDelayBtnVisibility(View.GONE);
        binding.wSlip.setSeekBar(255, 0, 1, w);
        binding.wSlip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
            @Override
            public void onDataChanging(int index) {

            }

            @Override
            public void onDataChanged(int index) {
                w = index;
                setW(w);
                binding.rgbwColor.clearPointer();
                if (onDataChangeListener != null) {
                    onDataChangeListener.onWDataChanged(w);
                }
            }
        });
    }


    //设置数据
    public void setR(int r) {
        this.r = r;
        binding.rSlip.setSeekBar(255, 0, 1, r);
    }

    public void setG(int g) {
        this.g = g;
        binding.gSlip.setSeekBar(255, 0, 1, g);
    }

    public void setB(int b) {
        this.b = b;
        binding.bSlip.setSeekBar(255, 0, 1, b);
    }

    public void setW(int w) {
        this.w = w;
        binding.wSlip.setSeekBar(255, 0, 1, w);
    }

    //设置按键默认选的位置
    public void check(int index) {
        switch (index) {
            case 0:
                binding.radioGroup.check(R.id.index_0);
                break;
            case 1:
                binding.radioGroup.check(R.id.index_1);
                break;
        }
    }

    //设置控件按键的切换事件
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }


    public interface OnDataChangeListener {
        void onDataChanged(int R, int G, int B, int W);

        void onRDataChanged(int R);

        void onGDataChanged(int G);

        void onBDataChanged(int B);

        void onWDataChanged(int W);

    }
}

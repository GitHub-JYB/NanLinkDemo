package com.example.NanLinkDemo.ui;

import android.content.Context;
import android.graphics.Color;
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
import com.example.NanLinkDemo.databinding.RgbwviewBinding;
import com.example.NanLinkDemo.databinding.XyviewBinding;

import java.util.ArrayList;

public class XYView extends RelativeLayout {

    private XyviewBinding binding;

    private int x = 330200, y = 340800;

    private OnDataChangeListener onDataChangeListener;

    public XYView(Context context) {
        this(context, null);
    }

    public XYView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XYView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public XYView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        binding = XyviewBinding.inflate(LayoutInflater.from(getContext()), this, true);
        setX(x);
        setY(y);
        updateColor();
        binding.curve.setTitle("色温曲线");
        binding.curve.check(1);
        ArrayList<String> curveText = new ArrayList<>();
        curveText.add("显示");
        curveText.add("隐藏");
        binding.curve.setData(curveText);
        binding.curve.setOnCheckedChangeListener(new BoxView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int index) {
                if (index == 0){
                    binding.xyColor.showCurve();
                }else {
                    binding.xyColor.hideCurve();
                }
            }
        });

        binding.xyColor.setOnDataChangeListener(new XYColorView.OnDataChangeListener() {
            @Override
            public void onProgressChanged(XYColorView xyColorView, int x, int y) {

            }

            @Override
            public void onStopTrackingTouch(XYColorView xyColorView) {

            }
        });
        binding.XSlip.setTitle("X");
        binding.XSlip.setRemark("X");
        binding.XSlip.setDelayTimeVisibility(View.GONE);
        binding.XSlip.setDelayBtnVisibility(View.GONE);
        binding.XSlip.setSeekBar(680000, 160000, 100, x);
        binding.XSlip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
            @Override
            public void onDataChanged(int index) {
                x = index;
                setX(x);
                if (onDataChangeListener != null) {
                    onDataChangeListener.onDataChanged(x, y);
                }
            }
        });

        binding.YSlip.setTitle("Y");
        binding.YSlip.setRemark("Y");
        binding.YSlip.setDelayTimeVisibility(View.GONE);
        binding.YSlip.setDelayBtnVisibility(View.GONE);
        binding.YSlip.setSeekBar(690000, 70000, 100, y);
        binding.YSlip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
            @Override
            public void onDataChanged(int index) {
                y = index;
                setY(y);
                if (onDataChangeListener != null) {
                    onDataChangeListener.onDataChanged(x, y);
                }
            }
        });

    }

    private void updateColor() {
        binding.color.setClipToOutline(true);
        binding.color.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), MyApplication.dip2px(6));
            }
        });
        float[] hsl = new float[3];
        hsl[0] = 0;
        hsl[1] = 1;
        hsl[2] = 1;
        binding.color.setBackgroundColor(Color.HSVToColor(hsl));
    }


    //设置数据
    public void setX(int x) {
        this.x = x;
        binding.XSlip.setSeekBar(680000, 160000, 100, x);
    }

    public void setY(int y) {
        this.y = y;
        binding.YSlip.setSeekBar(690000, 70000, 100, y);
    }

    //设置按键默认选的位置
    public void check(int index) {
        binding.curve.check(index);
    }

    //设置控件按键的切换事件
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }


    public interface OnDataChangeListener {
        void onDataChanged(int x, int y);
    }
}

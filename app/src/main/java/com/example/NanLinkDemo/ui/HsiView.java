package com.example.NanLinkDemo.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.HsiviewBinding;
import com.example.NanLinkDemo.databinding.SlmmenuviewBinding;
import com.example.NanLinkDemo.mvp.adapter.FlmMenuListAdapter;
import com.example.NanLinkDemo.util.ColorUtil;

import java.util.ArrayList;

public class HsiView extends RelativeLayout {

    private HsiviewBinding binding;

    private int HSI, SAT;

    private OnDataChangeListener onDataChangeListener;
    private OnCameraListener onCameraListener;

    public HsiView(Context context) {
        this(context, null);
    }

    public HsiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HsiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HsiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        binding = HsiviewBinding.inflate(LayoutInflater.from(getContext()), this, true);
        setHSI(HSI);
        setSAT(SAT);
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
                    case R.id.index_2:
                        if (onCameraListener != null){
                            onCameraListener.gotoCamera();
                        }
                        break;
                }
            }
        });

        binding.hsiColor.setOnDataChangeListener(new HsiColorView.OnDataChangeListener() {
            @Override
            public void onProgressChanged(HsiColorView hsiView, int hsi, int sat) {
                HSI = hsi;
                SAT = sat;
                setHSI(hsi);
                setSAT(sat);
            }

            @Override
            public void onStopTrackingTouch(HsiColorView hsiView) {
                if (onDataChangeListener != null) {
                    onDataChangeListener.onDataChanged(HSI, SAT);
                }
            }
        });
        binding.HSISlip.setRemark("色相");
        binding.HSISlip.setTitle("色相");
        binding.HSISlip.setDelayTimeVisibility(View.GONE);
        binding.HSISlip.setDelayBtnVisibility(View.GONE);
        binding.HSISlip.setSeekBar(360, 0, 1, HSI);
        binding.HSISlip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
            @Override
            public void onDataChanging(int index) {

            }

            @Override
            public void onDataChanged(int index) {
                HSI = index;
                setHSI(HSI);
                if (onDataChangeListener != null) {
                    onDataChangeListener.onDataChanged(HSI, SAT);
                }
            }
        });

        binding.SATSlip.setRemark("饱和度");
        binding.SATSlip.setTitle("饱和度");
        binding.SATSlip.setDelayTimeVisibility(View.GONE);
        binding.SATSlip.setDelayBtnVisibility(View.GONE);
        binding.SATSlip.setSeekBar(100, 0, 1, SAT);
        binding.SATSlip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
            @Override
            public void onDataChanging(int index) {

            }

            @Override
            public void onDataChanged(int index) {
                SAT = index;
                setSAT(SAT);
                if (onDataChangeListener != null) {
                    onDataChangeListener.onDataChanged(HSI, SAT);
                }
            }
        });
    }

    private void updateColor() {
        binding.color.setClipToOutline(true);
        binding.color.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),MyApplication.dip2px(6));
            }
        });
        binding.color.setBackgroundColor(ColorUtil.HsiSatToColor(HSI, SAT));
    }


    //设置数据
    public void setHSI(int HSI) {
        this.HSI = HSI;
        binding.hsiColor.setHSI(HSI);
        binding.HSIText.setText("H: " + HSI);
        binding.HSISlip.setSeekBar(360, 0, 1, HSI);
        updateColor();
    }

    public void setSAT(int SAT) {
        this.SAT = SAT;
        binding.hsiColor.setSAT(SAT);
        binding.SATText.setText("S: " + SAT + "%");
        binding.SATSlip.setSeekBar(100, 0, 1, SAT);
        updateColor();

    }

    public void setCCT(int max, int min, int step, int value) {
        binding.CCTSlip.setRemark("色温");
        binding.CCTSlip.setTitle("色温");
        binding.CCTSlip.setVisibility(VISIBLE);
        binding.CCTSlip.setDelayTimeVisibility(View.GONE);
        binding.CCTSlip.setDelayBtnVisibility(View.GONE);
        binding.CCTSlip.setSeekBar(max, min, step, value);
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
            case 2:
                binding.radioGroup.check(R.id.index_2);
                break;
        }
    }

    //设置控件按键的切换事件
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }


    public interface OnDataChangeListener {
        void onDataChanged(int HSI, int SAT);
    }

    //设置控件按键的切换事件
    public void setOnCameraListener(OnCameraListener onCameraListener) {
        this.onCameraListener = onCameraListener;
    }


    public interface OnCameraListener {
        void gotoCamera();
    }
}

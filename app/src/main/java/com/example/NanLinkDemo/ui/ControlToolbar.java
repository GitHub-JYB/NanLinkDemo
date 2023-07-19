package com.example.NanLinkDemo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.databinding.ControltoolbarBinding;
import com.example.NanLinkDemo.databinding.FlmModeRecycleviewBinding;
import com.example.NanLinkDemo.databinding.VpItemFlmmodeBinding;
import com.example.NanLinkDemo.mvp.adapter.FlmModeListAdapter;

import java.util.ArrayList;
import java.util.List;


public class ControlToolbar extends RelativeLayout {




    ControltoolbarBinding binding;
    private OnModeChangeListener onModeChangeListener;
    private FlmModeListAdapter adapter;
    private ArrayList<DeviceDataMessage.FlmMode> modeList = new ArrayList<>();

    public ControlToolbar(Context context) {
        this(context,null);
    }

    public ControlToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ControlToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ControlToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {

        binding = ControltoolbarBinding.inflate(LayoutInflater.from(getContext()),this, true);
        // 动态设置statusBar的高度
        LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) binding.statusBar.getLayoutParams();
        lp.height= MyApplication.statusHigh;
        binding.statusBar.setLayoutParams(lp);
        binding.mode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = (LinearLayout) inflate(getContext(), R.layout.flm_mode_recycleview, null);
                RecyclerView recyclerView = linearLayout.findViewById(R.id.recycleView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new FlmModeListAdapter();
                adapter.setData(modeList);
                recyclerView.setAdapter(adapter);
                PopupWindow popupWindow = new PopupWindow(linearLayout, LinearLayout.LayoutParams.MATCH_PARENT, MyApplication.heightPixels - binding.getRoot().getHeight(), true);

                popupWindow.setFocusable(true); // 获取焦点

                popupWindow.setOutsideTouchable(true);//获取外部触摸事件

                popupWindow.setTouchable(true);//能够响应触摸事件

                adapter.setOnClickListener(new FlmModeListAdapter.OnClickListener() {
                    @Override
                    public void onClick(int position) {
                        if (onModeChangeListener != null){
                            onModeChangeListener.ModeChange(position);
                        }
                        if (position < modeList.size()){
                            setMode(modeList.get(position).getRemark());
                        }
                        popupWindow.dismiss();
                    }
                });

                linearLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(binding.getRoot());

            }
        });
    }

    // 设置左侧按键点击事件
    public void setLeftBtnOnClickListener(OnClickListener li){
        binding.leftBtn.setOnClickListener(li);
    }

    // 设置右侧按键点击事件
    public void setRightBtnOnClickListener(OnClickListener li){
        binding.rightBtn.setOnClickListener(li);
    }

    //设置右侧按键开始动画
    public void startRightBtnAnimation(Animation animation){
        binding.rightBtn.startAnimation(animation);
    }

    //设置右侧按键结束动画
    public void stopRightBtnAnimation(){
        binding.rightBtn.clearAnimation();
    }

    // 设置右侧第二个按键点击事件
    public void setRightSecondBtnOnClickListener(OnClickListener li){
        binding.rightSecondBtn.setOnClickListener(li);
    }

    // 设置标题
    public void setTitle(String title){
        binding.title.setText(title);
    }

    // 设置副标题
    public void setSecondTitle(String title){
        binding.secondTitle.setText(title);
    }


    // 设置标题颜色
    public void setTitleColor(int color){
        binding.title.setTextColor(getResources().getColor(color));
    }


    // 设置左侧按键图标
    public void setLeftBtnIcon(int resId){
        binding.leftBtn.setImageResource(resId);
        binding.leftBtn.setVisibility(VISIBLE);
    }

    // 设置左侧按键图标可见性
    public void setLeftBtnIconVisibility(int visibility){
        binding.leftBtn.setVisibility(visibility);
    }


    // 设置右侧按键图标
    public void setRightBtnIcon(int resId){
        binding.rightBtn.setImageResource(resId);
        binding.rightBtn.setVisibility(VISIBLE);
    }

    // 设置右侧按键是否可以点击
    public void setRightBtnClickable(boolean able){
        binding.rightBtn.setClickable(able);
    }


    // 设置右侧按键图标可见性
    public void setRightBtnIconVisibility(int visibility){
        binding.rightBtn.setVisibility(visibility);
    }

    // 设置右侧第二个按键图标
    public void setRightSecondBtnIcon(int resId){
        binding.rightSecondBtn.setImageResource(resId);
        binding.rightSecondBtn.setVisibility(VISIBLE);
    }

    // 设置右侧第二个按键图标可见性
    public void setRightSecondBtnIconVisibility(int visibility){
        binding.rightSecondBtn.setVisibility(visibility);
    }

    // 设置撤销按键图标
    public void setUndoBtnIcon(int resId){
        binding.undoBtn.setImageResource(resId);
        binding.undoBtn.setVisibility(VISIBLE);
    }

    // 设置撤销按键是否可以点击
    public void setUndoBtnClickable(boolean able){
        binding.undoBtn.setClickable(able);
    }


    // 设置撤销按键图标可见性
    public void setUndoBtnIconVisibility(int visibility){
        binding.undoBtn.setVisibility(visibility);
    }

    // 设置撤销按键点击事件
    public void setUndoBtnOnClickListener(OnClickListener li){
        binding.undoBtn.setOnClickListener(li);
    }


    // 设置重做按键图标
    public void setRedoBtnIcon(int resId){
        binding.redoBtn.setImageResource(resId);
        binding.redoBtn.setVisibility(VISIBLE);
    }

    // 设置重做按键是否可以点击
    public void setRedoBtnClickable(boolean able){
        binding.redoBtn.setClickable(able);
    }


    // 设置重做按键图标可见性
    public void setRedoBtnIconVisibility(int visibility){
        binding.redoBtn.setVisibility(visibility);
    }

    // 设置重做按键点击事件
    public void setRedoBtnOnClickListener(OnClickListener li){
        binding.redoBtn.setOnClickListener(li);
    }


    // 设置添加预设按键图标
    public void setAddPresetBtnIcon(int resId){
        binding.addPresetBtn.setImageResource(resId);
        binding.addPresetBtn.setVisibility(VISIBLE);
    }

    // 设置添加预设按键是否可以点击
    public void setAddPresetBtnClickable(boolean able){
        binding.addPresetBtn.setClickable(able);
    }


    // 设置添加预设按键图标可见性
    public void setAddPresetBtnIconVisibility(int visibility){
        binding.addPresetBtn.setVisibility(visibility);
    }

    // 设置添加预设按键点击事件
    public void setAddPresetBtnOnClickListener(OnClickListener li){
        binding.addPresetBtn.setOnClickListener(li);
    }

    // 设置模式按键点击事件
    public void setModeBtnOnClickListener(OnClickListener li){
        binding.mode.setOnClickListener(li);


    }

    // 设置模式
    public void setMode(String mode){
        binding.mode.setText(mode);
    }

    // 设置模式
    public void setModeList(ArrayList<DeviceDataMessage.FlmMode> modeList){
       this.modeList = modeList;
    }


    // 设置模式变化监听事件
    public void setOnModeChangeListener(OnModeChangeListener onModeChangeListener){
        this.onModeChangeListener = onModeChangeListener;
    }

    public interface OnModeChangeListener{
        void ModeChange(int position);
    }

}

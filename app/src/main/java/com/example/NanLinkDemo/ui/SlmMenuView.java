package com.example.NanLinkDemo.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.BoxviewBinding;
import com.example.NanLinkDemo.databinding.SlmmenuviewBinding;
import com.example.NanLinkDemo.mvp.adapter.FlmMenuListAdapter;
import com.example.NanLinkDemo.mvp.adapter.FlmModeListAdapter;

import java.util.ArrayList;

public class SlmMenuView extends RelativeLayout {

    private SlmmenuviewBinding binding;

    private int index = 0;

    private OnIndexChangeListener onIndexChangeListener;
    private ArrayList<String> menuData = new ArrayList<>();

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
        binding.title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = (LinearLayout) inflate(getContext(), R.layout.slm_menu, null);
                RecyclerView recyclerView = linearLayout.findViewById(R.id.recycleView);
                LinearLayout background = linearLayout.findViewById(R.id.background);
                TextView title = linearLayout.findViewById(R.id.title);
                title.setText(menuData.get(index));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                FlmMenuListAdapter adapter = new FlmMenuListAdapter();
                adapter.setData(menuData);
                int[] location = new int[2];
                binding.getRoot().getLocationOnScreen(location);
                int y = location[1];
                recyclerView.setAdapter(adapter);

                PopupWindow popupWindow = new PopupWindow(linearLayout, LinearLayout.LayoutParams.MATCH_PARENT,  MyApplication.heightPixels + MyApplication.statusHigh - y, true);

                popupWindow.setFocusable(true); // 获取焦点

                popupWindow.setOutsideTouchable(true);//获取外部触摸事件

                popupWindow.setTouchable(true);//能够响应触摸事件

                adapter.setOnClickListener(new FlmMenuListAdapter.OnClickListener() {
                    @Override
                    public void onClick(int position) {

                        if (position < menuData.size()){
                            setTitle(menuData.get(position));
                            index = position;
                            if (onIndexChangeListener != null){
                                onIndexChangeListener.onIndexChanged(index);
                            }
                        }
                        popupWindow.dismiss();
                    }
                });
                background.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(binding.getRoot());
            }
        });
        if (menuData.size() < 1){
            binding.pre.setClickable(false);
            binding.next.setClickable(false);
        }else {
            binding.pre.setClickable(true);
            binding.next.setClickable(true);
        }
        binding.next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index > menuData.size() - 1){
                    index = 0;
                }
                setTitle(menuData.get(index));
                if (onIndexChangeListener != null){
                    onIndexChangeListener.onIndexChanged(index);
                }
            }
        });
        binding.pre.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                if (index < 0){
                    index = menuData.size() - 1;
                }
                setTitle(menuData.get(index));
                if (onIndexChangeListener != null){
                    onIndexChangeListener.onIndexChanged(index);
                }
            }
        });
    }

    // 设置是否隐藏
    public void setVisibility(int visibility){
        binding.getRoot().setVisibility(visibility);
    }

    //设置控件标题名称
    public void setTitle(String title) {
        binding.title.setText(title);
    }

    //设置数据
    public void setMenuData(ArrayList<String> menuData) {
        this.menuData = menuData;
    }


    //设置按键默认选的位置
    public void check(int index) {
        this.index = index;
    }

    public int getCheck(){
        return index;
    }

    //设置控件按键的切换事件
    public void setOnIndexChangeListener(OnIndexChangeListener onIndexChangeListener) {
        this.onIndexChangeListener = onIndexChangeListener;
    }

    public interface OnIndexChangeListener{
        void onIndexChanged(int index);
    }
}

package com.example.nanlinkdemo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.PopupRecycleviewBinding;
import com.example.nanlinkdemo.databinding.VpItemFixtureGroupBinding;


public class FixtureSceneListView extends LinearLayout {




    VpItemFixtureGroupBinding binding;
    private PopupWindow popupWindow;

    public FixtureSceneListView(Context context) {
        this(context,null);
    }

    public FixtureSceneListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FixtureSceneListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FixtureSceneListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {

        binding = VpItemFixtureGroupBinding.inflate(LayoutInflater.from(getContext()),this, true);
        binding.spreadIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null){
                    showPopupWindow();
                    binding.spreadIcon.setImageResource(R.drawable.ic_spread);
                }else {
                    closePopupWindow();
                    binding.spreadIcon.setImageResource(R.drawable.ic_close);
                }
            }
        });

    }

    private void closePopupWindow() {
        popupWindow.dismiss();
        popupWindow = null;
    }

    private void showPopupWindow() {
        PopupRecycleviewBinding binding1 = PopupRecycleviewBinding.inflate(LayoutInflater.from(getContext()), this, false);
        popupWindow = new PopupWindow(binding1.getRoot(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAsDropDown(this);

        binding1.recycleView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
    }


}

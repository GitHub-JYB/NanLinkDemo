package com.example.nanlinkdemo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.FixtureGroupBinding;
import com.example.nanlinkdemo.mvp.adapter.FixtureGroupAdapter;

import java.util.ArrayList;


public class FixtureGroupView extends LinearLayout {


    private ArrayList<Fixture> fixtureList;
    private boolean fixtureVisibility = false;
    private FixtureGroupAdapter fixtureGroupAdapter;

    public FixtureGroupView(Context context) {
        this(context,null);
    }

    public FixtureGroupView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FixtureGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FixtureGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {

        FixtureGroupBinding binding = FixtureGroupBinding.inflate(LayoutInflater.from(getContext()), this, true);
        binding.spreadIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fixtureVisibility = !fixtureVisibility;
                if (fixtureVisibility){
                    binding.spreadIcon.setImageResource(R.drawable.ic_spread);
                    binding.recyclerView.setVisibility(VISIBLE);
                }else {
                    binding.spreadIcon.setImageResource(R.drawable.ic_close);
                    binding.recyclerView.setVisibility(GONE);
                }
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 设置分割线格式并添加
        UnlessLastItemDecoration decoration = new UnlessLastItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.decoration_fixture));
        binding.recyclerView.addItemDecoration(decoration);

        fixtureGroupAdapter = new FixtureGroupAdapter();
        fixtureGroupAdapter.setData(fixtureList);
        binding.recyclerView.setAdapter(fixtureGroupAdapter);


    }






}

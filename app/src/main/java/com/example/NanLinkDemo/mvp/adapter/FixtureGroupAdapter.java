package com.example.NanLinkDemo.mvp.adapter;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.databinding.VpItemFixtureBinding;
import com.example.NanLinkDemo.util.TransformUtil;

import java.util.List;

public class FixtureGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private List<Fixture> fixtureList;
    private OnClickListener onClickListener;
    private MenuOnClickListener menuOnClickListener;
    private RightSecondIconOnClickListener rightSecondOnClickListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpItemFixtureBinding vpItemFixtureBinding = VpItemFixtureBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderFixtureList(vpItemFixtureBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderFixtureList){
            ((ViewHolderFixtureList) holder).name.setText(fixtureList.get(position).getName());
            ((ViewHolderFixtureList) holder).number.setText("CH: " + TransformUtil.updateCH(fixtureList.get(position).getCH()));
            ((ViewHolderFixtureList) holder).connectType.setText(fixtureList.get(position).getConnectType());
        }
    }

    @Override
    public int getItemCount() {
        return fixtureList != null? fixtureList.size():0;
    }


    public void setData(List<Fixture> fixtureList) {
        this.fixtureList = fixtureList;
        notifyDataSetChanged();
    }

    class ViewHolderFixtureList extends RecyclerView.ViewHolder {


        TextView name, number, connectType;

        public ViewHolderFixtureList(@NonNull VpItemFixtureBinding binding) {
            super(binding.getRoot());
            name = binding.name;
            number = binding.number;
            connectType = binding.connectType;
            ViewGroup.LayoutParams layoutParams = binding.fixture.getLayoutParams();
            layoutParams.width = MyApplication.dip2percentPx(434);
            layoutParams.height = MyApplication.dip2percentPx(50);
            binding.fixture.setLayoutParams(layoutParams);
            binding.fixture.setPadding(0,0,0,0);
            binding.fixture.setBackgroundColor(Color.TRANSPARENT);
            ViewGroup.LayoutParams layoutParams1 = binding.menu.getLayoutParams();
            layoutParams1.width = MyApplication.dip2percentPx(40);
            layoutParams1.height = MyApplication.dip2percentPx(50);
            binding.menu.setLayoutParams(layoutParams1);
            ViewGroup.LayoutParams layoutParams2 = binding.rightSecondIcon.getLayoutParams();
            layoutParams2.width = MyApplication.dip2percentPx(40);
            layoutParams2.height = MyApplication.dip2percentPx(50);
            binding.rightSecondIcon.setLayoutParams(layoutParams2);
            binding.rightSecondIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rightSecondOnClickListener != null){
                        rightSecondOnClickListener.onClick(fixtureList.get(getAdapterPosition()));
                    }
                }
            });
            binding.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuOnClickListener != null){
                        menuOnClickListener.onClick(fixtureList.get(getAdapterPosition()));
                    }
                }
            });

        }
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public interface MenuOnClickListener {
        void onClick(Fixture fixture);
    }

    public void setMenuOnClickListener(MenuOnClickListener menuOnClickListener){
        this.menuOnClickListener = menuOnClickListener;
    }

    public interface RightSecondIconOnClickListener {
        void onClick(Fixture fixture);
    }

    public void setRightSecondIconOnClickListener(RightSecondIconOnClickListener rightSecondOnClickListener){
        this.rightSecondOnClickListener = rightSecondOnClickListener;
    }

}

package com.example.NanLinkDemo.mvp.adapter;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Device;
import com.example.NanLinkDemo.databinding.VpItemFixtureBinding;
import com.example.NanLinkDemo.util.TransformUtil;

import java.util.ArrayList;

public class ScanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private OnClickListener onClickListener;
    private ArrayList<Device> fixtureList;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpItemFixtureBinding vpItemFixtureBinding = VpItemFixtureBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderItemFixture(vpItemFixtureBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderItemFixture){
                ((ViewHolderItemFixture) holder).name.setText(fixtureList.get(position).getNAME());
                if (fixtureList.get(position).getTYPE() == 0){
                    ((ViewHolderItemFixture) holder).number.setText("CH: " + TransformUtil.updateCH(fixtureList.get(position).getCH()));
                    ((ViewHolderItemFixture) holder).number.setTextColor(Color.parseColor("#8E8E8E"));
                    if (fixtureList.get(position).getCH() == 0){
                        ((ViewHolderItemFixture) holder).number.setTextColor(Color.parseColor("#FFFF33"));
                    }
                    for (Fixture fixture : MyApplication.getFixtures()){
                        if (fixtureList.get(position).getCH() == fixture.getCH()){
                            ((ViewHolderItemFixture) holder).number.setTextColor(Color.parseColor("#FFFF33"));
                        }
                    }
                }else {
                    ((ViewHolderItemFixture) holder).number.setText(fixtureList.get(position).getUUID());
                }
                if (fixtureList.get(position).isSelected()){
                    ((ViewHolderItemFixture) holder).menu.setImageResource(R.drawable.ic_selected);
                }else {
                    ((ViewHolderItemFixture) holder).menu.setImageResource(R.drawable.ic_unselected);
                }

        }
    }


    @Override
    public int getItemCount() {
        return fixtureList == null? 0 : fixtureList.size();
    }


    public void setData(ArrayList<Device> arrayList) {
        this.fixtureList = arrayList;
        notifyDataSetChanged();
    }

    class ViewHolderItemFixture extends RecyclerView.ViewHolder {


        TextView name;
        TextView number;

        ImageView menu;

        public ViewHolderItemFixture(@NonNull VpItemFixtureBinding binding) {
            super(binding.getRoot());
            binding.rightSecondIcon.setVisibility(View.GONE);
            binding.connectType.setVisibility(View.GONE);
            menu = binding.menu;
            name = binding.name;
            number = binding.number;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null){
                        onClickListener.onClick(getAdapterPosition());
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

}

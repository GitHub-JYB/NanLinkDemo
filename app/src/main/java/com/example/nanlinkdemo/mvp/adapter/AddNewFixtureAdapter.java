package com.example.nanlinkdemo.mvp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanlinkdemo.bean.AddFixtureType;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.bean.RegisterUser;
import com.example.nanlinkdemo.databinding.VpItemAddTypeBinding;
import com.example.nanlinkdemo.databinding.VpItemSettingBinding;
import com.example.nanlinkdemo.databinding.VpItemUserSettingBinding;

import java.util.ArrayList;
import java.util.List;

public class AddNewFixtureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private OnClickListener onClickListener;
    private ArrayList<AddFixtureType> typeList;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpItemAddTypeBinding vpItemAddTypeBinding = VpItemAddTypeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderItemAddType(vpItemAddTypeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderItemAddType){
            if (typeList.get(position).getType() != null){
                ((ViewHolderItemAddType) holder).type.setText(typeList.get(position).getType());
            }
            if (typeList.get(position).getTypeRemark() != null){
                ((ViewHolderItemAddType) holder).type_remark.setText(typeList.get(position).getTypeRemark());
            }
        }
    }


    @Override
    public int getItemCount() {
        return typeList == null? 0 : typeList.size();
    }


    public void setData(ArrayList<AddFixtureType> arrayList) {
        this.typeList = arrayList;
        notifyDataSetChanged();
    }

    class ViewHolderItemAddType extends RecyclerView.ViewHolder {


        TextView type;
        TextView type_remark;

        public ViewHolderItemAddType(@NonNull VpItemAddTypeBinding binding) {
            super(binding.getRoot());
            type = binding.textType;
            type_remark = binding.textTypeRemark;
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

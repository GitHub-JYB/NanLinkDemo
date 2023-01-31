package com.example.nanlinkdemo.mvp.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.VpSettingSettingdialogBinding;
import com.example.nanlinkdemo.databinding.VpTitleSettingdialogBinding;

import java.util.ArrayList;

public class ThreePointAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_TITLE = 0;
    private static final int TYPE_SETTING = 1;
    private ArrayList<String> settingList = new ArrayList<>();
    private OnClickListener onClickListener;



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TITLE:
                VpTitleSettingdialogBinding vpTitleSettingdialogBinding = VpTitleSettingdialogBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderTitleThreePoint(vpTitleSettingdialogBinding);

            default:
                VpSettingSettingdialogBinding vpSettingSettingdialogBinding= VpSettingSettingdialogBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderSettingThreePoint(vpSettingSettingdialogBinding);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderTitleThreePoint){
            ((ViewHolderTitleThreePoint) holder).title.setText(settingList.get(position));
        } else if (holder instanceof ViewHolderSettingThreePoint){
            ((ViewHolderSettingThreePoint) holder).setting.setText(settingList.get(position));
            if (settingList.get(position).equals("删除")){
                TextView tv = ((ViewHolderSettingThreePoint) holder).setting;
                ((ViewHolderSettingThreePoint) holder).setting.setTextColor(ContextCompat.getColor(tv.getContext(),R.color.warning));
            }
        }
    }

    @Override
    public int getItemCount() {
        return settingList == null? 0 : settingList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
                return TYPE_TITLE;
        }
        return TYPE_SETTING;

    }

    public void setData(ArrayList<String> settingList) {
        this.settingList = settingList;
        notifyDataSetChanged();
    }

    class ViewHolderTitleThreePoint extends RecyclerView.ViewHolder {


        TextView title;

        public ViewHolderTitleThreePoint(@NonNull VpTitleSettingdialogBinding binding) {
            super(binding.getRoot());
            title = binding.title;
        }
    }

    class ViewHolderSettingThreePoint extends RecyclerView.ViewHolder {


        TextView setting;

        public ViewHolderSettingThreePoint(@NonNull VpSettingSettingdialogBinding binding) {
            super(binding.getRoot());
            setting = binding.setting;
            binding.setting.setOnClickListener(new View.OnClickListener() {
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

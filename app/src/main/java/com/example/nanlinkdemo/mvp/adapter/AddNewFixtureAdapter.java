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
import com.example.nanlinkdemo.databinding.VpItemSettingBinding;
import com.example.nanlinkdemo.databinding.VpItemUserSettingBinding;

import java.util.ArrayList;
import java.util.List;

public class AddNewFixtureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private OnClickListener onClickListener;
    private int userListLength;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpItemSettingBinding vpItemSettingBinding = VpItemSettingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderItemSetting(vpItemSettingBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderUserSetting){
            if (userList.get(position).getEmail() != null){
                ((ViewHolderUserSetting) holder).email.setText(userList.get(position).getEmail());
            }
            if (userList.get(position).getNickName() != null){
                ((ViewHolderUserSetting) holder).nickName.setText(userList.get(position).getNickName());
            }
        }else if (holder instanceof ViewHolderItemSetting){
            if (userList == null){
                userListLength = 0;
            }else {
                userListLength = userList.size();
            }
            if (settingList.get(position - userListLength).getStateResId() != 0){
                ((ViewHolderItemSetting) holder).iconSetting.setImageResource(settingList.get(position - userListLength).getStateResId());
            }
            if (settingList.get(position - userListLength).getText() != null){
                ((ViewHolderItemSetting) holder).textSetting.setText(settingList.get(position - userListLength).getText());
            }
        }
    }


    @Override
    public int getItemCount() {
        return userList == null? settingList.size() : userList.size() + settingList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (userList == null || position >= userList.size()){
            return TYPE_SETTING;
        } else {
            return TYPE_USER;

        }


    }

    public void setData(ArrayList<AddFixtureType> arrayList) {
//        this.settingList = arrayList;
//        this.userList = arrayList;
        notifyDataSetChanged();
    }

    class ViewHolderItemSetting extends RecyclerView.ViewHolder {


        ImageView iconSetting;
        TextView textSetting;

        public ViewHolderItemSetting(@NonNull VpItemSettingBinding binding) {
            super(binding.getRoot());
            iconSetting = binding.iconSetting;
            textSetting = binding.textSetting;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null){
                        onClickListener.onClick(settingList.get(getAdapterPosition() - userListLength).getText());
                    }
                }
            });
        }
    }

    public interface OnClickListener {
        void onClick(String settingText);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

}
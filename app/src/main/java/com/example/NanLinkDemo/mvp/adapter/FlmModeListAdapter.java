package com.example.NanLinkDemo.mvp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.databinding.VpItemFlmmodeBinding;

import java.util.ArrayList;
import java.util.List;

public class FlmModeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<DeviceDataMessage.FlmMode> modeList = new ArrayList<>();
    private OnClickListener onClickListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpItemFlmmodeBinding vpItemFlmmodeBinding = VpItemFlmmodeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderItemFlmMode(vpItemFlmmodeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderItemFlmMode) {
            if (position < modeList.size()) {
                ((ViewHolderItemFlmMode) holder).textMode.setText(modeList.get(position).getRemark());
            } else {
                ((ViewHolderItemFlmMode) holder).textMode.setText("预设");
            }

        }
    }


    @Override
    public int getItemCount() {
        return modeList == null ? 0 : modeList.size() + 1;
    }


    public void setData(ArrayList<DeviceDataMessage.FlmMode> modeList) {
        this.modeList = modeList;
        notifyDataSetChanged();
    }

    class ViewHolderItemFlmMode extends RecyclerView.ViewHolder {


        TextView textMode;

        public ViewHolderItemFlmMode(@NonNull VpItemFlmmodeBinding binding) {
            super(binding.getRoot());
            textMode = binding.textMode;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}

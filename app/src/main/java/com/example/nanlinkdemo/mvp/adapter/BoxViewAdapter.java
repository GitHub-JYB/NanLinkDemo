package com.example.nanlinkdemo.mvp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.VpItemAdd24gBinding;
import com.example.nanlinkdemo.databinding.VpItemBoxViewBinding;

import java.util.ArrayList;

public class BoxViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnClickListener onClickListener;
    private ArrayList<String> dataList;

    private int selectedPosition = 0;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpItemBoxViewBinding vpItemBoxViewBinding = VpItemBoxViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderBoxView(vpItemBoxViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderBoxView) holder).btn.setBackgroundResource(R.drawable.bg_type_unselected);
        if (position == selectedPosition) {
            ((ViewHolderBoxView) holder).btn.setBackgroundResource(R.drawable.bg_type_selected);
        }
        ((ViewHolderBoxView) holder).btn.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void setData(ArrayList<String> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    class ViewHolderBoxView extends RecyclerView.ViewHolder {


        Button btn;

        public ViewHolderBoxView(@NonNull VpItemBoxViewBinding binding) {
            super(binding.getRoot());
            btn = binding.btn;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() != selectedPosition) {
                        selectedPosition = getAdapterPosition();
                        notifyDataSetChanged();
                        if (onClickListener != null) {
                            onClickListener.onClick(getAdapterPosition());
                        }
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

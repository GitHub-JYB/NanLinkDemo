package com.example.NanLinkDemo.mvp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NanLinkDemo.databinding.VpItemTwoSlmMenuBinding;

import java.util.ArrayList;
import java.util.List;

public class FlmMenuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<String> menuList = new ArrayList<>();
    private OnClickListener onClickListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpItemTwoSlmMenuBinding vpItemTwoSlmMenuBinding = VpItemTwoSlmMenuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderItemFlmMenu(vpItemTwoSlmMenuBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderItemFlmMenu) holder).leftMenuText.setText(menuList.get(position * 2));
        if (position * 2 + 1 > menuList.size() - 1){
            ((ViewHolderItemFlmMenu) holder).rightMenuText.setVisibility(View.GONE);
            ((ViewHolderItemFlmMenu) holder).rightUnderLine.setVisibility(View.GONE);
        }else {
            ((ViewHolderItemFlmMenu) holder).rightMenuText.setText(menuList.get(position * 2 + 1));
            ((ViewHolderItemFlmMenu) holder).rightMenuText.setVisibility(View.VISIBLE);
            ((ViewHolderItemFlmMenu) holder).rightUnderLine.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return menuList == null ? 0 : menuList.size() % 2 > 0 ? menuList.size() / 2 + 1 : menuList.size() / 2;
    }


    public void setData(ArrayList<String> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }

    class ViewHolderItemFlmMenu extends RecyclerView.ViewHolder {


        TextView leftMenuText, rightMenuText;
        View rightUnderLine;

        public ViewHolderItemFlmMenu(@NonNull VpItemTwoSlmMenuBinding binding) {
            super(binding.getRoot());
            leftMenuText = binding.leftMenu.title;
            rightMenuText = binding.rightMenu.title;
            rightUnderLine = binding.rightMenu.underLine;
            leftMenuText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null){
                        onClickListener.onClick(getAdapterPosition() * 2);
                    }
                }
            });
            rightMenuText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null){
                        onClickListener.onClick(getAdapterPosition() * 2 + 1);
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

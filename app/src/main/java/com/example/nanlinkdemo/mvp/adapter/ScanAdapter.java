package com.example.nanlinkdemo.mvp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.FeasyDevice;
import com.example.nanlinkdemo.databinding.VpItemFixtureBinding;

import java.util.ArrayList;

public class ScanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private OnClickListener onClickListener;
    private ArrayList<FeasyDevice> fixtureList;


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
                    ((ViewHolderItemFixture) holder).number.setText("CH: " + fixtureList.get(position).getCH());
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


    public void setData(ArrayList<FeasyDevice> arrayList) {
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

package com.example.NanLinkDemo.mvp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bleConnect.ExtendedBluetoothDevice;
import com.example.NanLinkDemo.databinding.VpItemFixtureBinding;
import com.example.NanLinkDemo.util.TransformUtil;

import java.util.ArrayList;

public class ScanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private OnClickListener onClickListener;
    private ArrayList<ExtendedBluetoothDevice> fixtureList;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpItemFixtureBinding vpItemFixtureBinding = VpItemFixtureBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderItemFixture(vpItemFixtureBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderItemFixture){
                ((ViewHolderItemFixture) holder).name.setText(fixtureList.get(position).getDEVICE_NAME());
                if (fixtureList.get(position).getTYPE() == 0){
                    TextView number = ((ViewHolderItemFixture) holder).number;
                    number.setText("CH: " + TransformUtil.updateCH(fixtureList.get(position).getCH()));
                    number.setTextColor(ContextCompat.getColor(number.getContext(), R.color.login_hintText));
                    if (fixtureList.get(position).getCH() == 0){
                        number.setTextColor(ContextCompat.getColor(number.getContext(), R.color.warnCH));
                        number.setText("CH: 未设置");
                    }
                    for (Fixture fixture : MyApplication.getFixtures()){
                        if (fixtureList.get(position).getCH() == fixture.getCH()){
                            number.setTextColor(ContextCompat.getColor(number.getContext(), R.color.warnCH));
                        }
                    }
                }else {
                    ((ViewHolderItemFixture) holder).number.setText(fixtureList.get(position).getAddress());
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


    public void setData(ArrayList<ExtendedBluetoothDevice> arrayList) {
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

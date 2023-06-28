package com.example.NanLinkDemo.mvp.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.VpItemFixtureBinding;
import com.example.NanLinkDemo.databinding.VpItemSceneManageBinding;

import java.util.ArrayList;
import java.util.List;

public class ManageFixtureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Fixture> fixtureList = new ArrayList<>();
    private OnClickListener onClickListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpItemFixtureBinding vpItemFixtureBinding = VpItemFixtureBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderManageFixtureList(vpItemFixtureBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderManageFixtureList) holder).name.setText(fixtureList.get(position).getName());
        ((ViewHolderManageFixtureList) holder).number.setText("CH: " + fixtureList.get(position).getCH());
        ((ViewHolderManageFixtureList) holder).connectType.setText(fixtureList.get(position).getConnectType());
        if (fixtureList.get(position).getFixtureGroupName().isEmpty()){
            ((ViewHolderManageFixtureList) holder).menu.setImageResource(R.drawable.ic_unselected);
        }else {
            ((ViewHolderManageFixtureList) holder).menu.setImageResource(R.drawable.ic_selected);
        }
    }

    @Override
    public int getItemCount() {
        return fixtureList.size();
    }


    public void setData(List<Fixture> fixtureList) {
        this.fixtureList = fixtureList;
        notifyDataSetChanged();
    }

    class ViewHolderManageFixtureList extends RecyclerView.ViewHolder {


        TextView name, number, connectType;
        ImageView menu;

        public ViewHolderManageFixtureList(@NonNull VpItemFixtureBinding binding) {
            super(binding.getRoot());
            binding.rightSecondIcon.setVisibility(View.GONE);
            menu = binding.menu;
            name = binding.name;
            number = binding.number;
            connectType = binding.connectType;


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

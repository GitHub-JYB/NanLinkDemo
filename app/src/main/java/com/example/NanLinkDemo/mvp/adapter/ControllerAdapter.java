package com.example.NanLinkDemo.mvp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NanLinkDemo.DB.bean.Controller;
import com.example.NanLinkDemo.databinding.VpItemFixtureBinding;

import java.util.ArrayList;

public class ControllerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_DECORATION_FIXTURE = 1;
    public static final int TYPE_FIXTURE = 2;
    private static final int TYPE_DECORATION_FIXTURE_GROUP = 3;
    public static final int TYPE_FIXTURE_GROUP = 4;
    private OnClickListener onClickListener;
    private MenuOnClickListener menuOnClickListener;
    private RightSecondIconOnClickListener rightSecondOnClickListener;
    private SpreadIconOnClickListener spreadOnClickListener;
    private ArrayList<Controller> controllerList;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpItemFixtureBinding vpItemFixtureBinding = VpItemFixtureBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderControllerList(vpItemFixtureBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderControllerList) holder).name.setText(controllerList.get(position).getControllerName());
        ((ViewHolderControllerList) holder).number.setText(controllerList.get(position).getUuid());
        ((ViewHolderControllerList) holder).connectType.setText(controllerList.get(position).getConnectType());
    }

    @Override
    public int getItemCount() {
        return controllerList == null? 0 : controllerList.size();
    }


    public void setData(ArrayList<Controller> controllerList) {
        this.controllerList = controllerList;
        notifyDataSetChanged();
    }

    class ViewHolderControllerList extends RecyclerView.ViewHolder {


        TextView name, number, connectType;

        public ViewHolderControllerList(@NonNull VpItemFixtureBinding binding) {
            super(binding.getRoot());
            name = binding.name;
            number = binding.number;
            connectType = binding.connectType;
            binding.rightSecondIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rightSecondOnClickListener != null){
                        rightSecondOnClickListener.onClick(getAdapterPosition());
                    }
                }
            });
            binding.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuOnClickListener != null){
                        menuOnClickListener.onClick(getAdapterPosition());
                    }
                }
            });
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

    public interface MenuOnClickListener {
        void onClick(int position);
    }

    public void setMenuOnClickListener(MenuOnClickListener menuOnClickListener){
        this.menuOnClickListener = menuOnClickListener;
    }

    public interface RightSecondIconOnClickListener {
        void onClick(int position);
    }

    public void setRightSecondIconOnClickListener(RightSecondIconOnClickListener rightSecondOnClickListener){
        this.rightSecondOnClickListener = rightSecondOnClickListener;
    }

    public interface SpreadIconOnClickListener {
        void onClick(int position);
    }

    public void setSpreadIconOnClickListener(SpreadIconOnClickListener spreadOnClickListener){
        this.spreadOnClickListener = spreadOnClickListener;
    }
}

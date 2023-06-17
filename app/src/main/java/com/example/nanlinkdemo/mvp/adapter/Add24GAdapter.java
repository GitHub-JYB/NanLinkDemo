package com.example.nanlinkdemo.mvp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanlinkdemo.DB.bean.Controller;
import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.bean.RegisterUser;
import com.example.nanlinkdemo.databinding.VpItemAdd24gBinding;
import com.example.nanlinkdemo.databinding.VpItemFixtureBinding;

import java.util.ArrayList;
import java.util.List;

public class Add24GAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnClickListener onClickListener;
    private ArrayList<Add24GFixture> fixtureArrayList ;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpItemAdd24gBinding vpItemAdd24gBinding = VpItemAdd24gBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderAdd24g(vpItemAdd24gBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderAdd24g) holder).name.setText(fixtureArrayList.get(position).getName());
        ((ViewHolderAdd24g) holder).CH.setText(fixtureArrayList.get(position).getCH());
        ((ViewHolderAdd24g) holder).index.setText("#" + (position + 1));
        if (position == 0){
            ((ViewHolderAdd24g) holder).delete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return fixtureArrayList == null? 0 : fixtureArrayList.size();
    }

    public void setData(ArrayList<Add24GFixture> fixtureArrayList) {
        this.fixtureArrayList = fixtureArrayList;
        notifyDataSetChanged();
    }
    class ViewHolderAdd24g extends RecyclerView.ViewHolder {


        TextView index;
        EditText CH, name;

        ImageView delete;

        public ViewHolderAdd24g(@NonNull VpItemAdd24gBinding binding) {
            super(binding.getRoot());
            name = binding.name;
            CH = binding.CH;
            index = binding.index;
            delete = binding.delete;


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
//        this.menuOnClickListener = menuOnClickListener;
    }

    public interface RightSecondIconOnClickListener {
        void onClick(int position);
    }

    public void setRightSecondIconOnClickListener(RightSecondIconOnClickListener rightSecondOnClickListener){
//        this.rightSecondOnClickListener = rightSecondOnClickListener;
    }

    public interface SpreadIconOnClickListener {
        void onClick(int position);
    }

    public void setSpreadIconOnClickListener(SpreadIconOnClickListener spreadOnClickListener){
//        this.spreadOnClickListener = spreadOnClickListener;
    }
}

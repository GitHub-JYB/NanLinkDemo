package com.example.nanlinkdemo.mvp.adapter;


import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.databinding.VpItemAdd24gBinding;
import com.example.nanlinkdemo.databinding.VpItemAdd24gBtnBinding;
import com.example.nanlinkdemo.ui.MyDialog;
import com.example.nanlinkdemo.util.SnackBarUtil;

import java.util.ArrayList;

public class Add24GAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Add24GFixture> fixtureArrayList;
    private int Type_Item = 0;
    private int Type_Btn = 1;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Type_Item) {
            VpItemAdd24gBinding vpItemAdd24gBinding = VpItemAdd24gBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolderAdd24g(vpItemAdd24gBinding);
        }
        VpItemAdd24gBtnBinding vpItemAdd24gBtnBinding = VpItemAdd24gBtnBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderAddBtn(vpItemAdd24gBtnBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderAdd24g) {
            ((ViewHolderAdd24g) holder).name.setText(fixtureArrayList.get(position).getName());
            ((ViewHolderAdd24g) holder).name.setTag(position);
            ((ViewHolderAdd24g) holder).CH.setText(fixtureArrayList.get(position).getCH());
            ((ViewHolderAdd24g) holder).CH.setTag(position);
            ((ViewHolderAdd24g) holder).index.setText("#" + (position + 1));
            ((ViewHolderAdd24g) holder).index.setFocusable(true);
            ((ViewHolderAdd24g) holder).index.setFocusableInTouchMode(true);
            ((ViewHolderAdd24g) holder).index.requestFocus();
            ((ViewHolderAdd24g) holder).index.findFocus();

            ((ViewHolderAdd24g) holder).delete.setTag(position);
            if (position == 0) {
                ((ViewHolderAdd24g) holder).delete.setVisibility(View.GONE);
            } else {
                ((ViewHolderAdd24g) holder).delete.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return fixtureArrayList != null && position < fixtureArrayList.size() ? Type_Item : Type_Btn;
    }

    @Override
    public int getItemCount() {
        return fixtureArrayList == null ? 1 : fixtureArrayList.size() + 1;
    }

    public void setData(ArrayList<Add24GFixture> fixtureArrayList) {
        this.fixtureArrayList = fixtureArrayList;
        notifyDataSetChanged();
    }

    class ViewHolderAddBtn extends RecyclerView.ViewHolder {


        public ViewHolderAddBtn(@NonNull VpItemAdd24gBtnBinding binding) {
            super(binding.getRoot());

            binding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fixtureArrayList.add(new Add24GFixture("", ""));
                    notifyDataSetChanged();
                }
            });

        }
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


            binding.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fixtureArrayList.remove(fixtureArrayList.get((Integer) binding.delete.getTag()));
                    notifyDataSetChanged();
                }
            });

            binding.CH.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String CH = binding.CH.getText().toString().trim();
                        if (!CH.isEmpty()) {
                            if (Integer.parseInt(CH) <= 0 || Integer.parseInt(CH) > 512) {

                            } else {
                                Add24GFixture fixture = fixtureArrayList.get((Integer) binding.CH.getTag());
                                if (Integer.parseInt(CH) < 10) {
                                    CH = "00" + Integer.parseInt(CH);
                                } else if (Integer.parseInt(CH) < 100) {
                                    CH = "0" + Integer.parseInt(CH);
                                }
                                fixture.setCH(CH);
                                fixtureArrayList.set((Integer) binding.CH.getTag(), fixture);
                            }
                        }
                    }
                }
            });

            binding.name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String name = binding.name.getText().toString().trim();
                        if (!name.isEmpty()) {
                            Add24GFixture fixture = fixtureArrayList.get((Integer) binding.name.getTag());
                            fixture.setName(name);
                            fixtureArrayList.set((Integer) binding.name.getTag(), fixture);
                        }
                    }
                }
            });

        }
    }

}

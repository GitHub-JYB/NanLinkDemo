package com.example.NanLinkDemo.mvp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.databinding.VpItemControlBinding;
import com.example.NanLinkDemo.ui.SlipView;
import com.example.NanLinkDemo.util.TransformUtil;

import java.util.ArrayList;

public class ControlModeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_FIXTURE_GROUP = 0;
    private static final int TYPE_FIXTURE = 1;
    private ArrayList<Fixture> fixtureList = new ArrayList<>();
    private ArrayList<Fixture> noGroupFixtureList = new ArrayList<>();

    private ArrayList<FixtureGroup> fixtureGroupList = new ArrayList<>();


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpItemControlBinding vpItemControlBinding;
        switch (viewType) {
            case TYPE_FIXTURE_GROUP:
                vpItemControlBinding = VpItemControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderFixtureGroupControl(vpItemControlBinding);
            default:
                vpItemControlBinding = VpItemControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderFixtureControl(vpItemControlBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderFixtureControl) {
            if (fixtureGroupList == null){
                ((ViewHolderFixtureControl) holder).control.setName(noGroupFixtureList.get(position).getName());
                ((ViewHolderFixtureControl) holder).control.setCH("CH: " + TransformUtil.updateCH(noGroupFixtureList.get(position).getCH()));

            }else {
                ((ViewHolderFixtureControl) holder).control.setName(noGroupFixtureList.get(position - fixtureGroupList.size()).getName());
                ((ViewHolderFixtureControl) holder).control.setCH("CH: " + TransformUtil.updateCH(noGroupFixtureList.get(position - fixtureGroupList.size()).getCH()));
            }
        } else if (holder instanceof ViewHolderFixtureGroupControl) {
            ((ViewHolderFixtureGroupControl) holder).control.setName(fixtureGroupList.get(position).getName());

        }
    }


    @Override
    public int getItemCount() {
        return fixtureGroupList == null ? noGroupFixtureList.size() : noGroupFixtureList.size() + fixtureGroupList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (fixtureGroupList != null && position < fixtureGroupList.size()) {
            return TYPE_FIXTURE_GROUP;
        } else {
            return TYPE_FIXTURE;

        }


    }

    public void setData(ArrayList<FixtureGroup> fixtureGroupList, ArrayList<Fixture> fixtureList) {
        this.fixtureGroupList = fixtureGroupList;
        this.fixtureList = fixtureList;
        for (Fixture fixture : fixtureList) {
            if (fixture.getFixtureGroupName().equals("")) {
                noGroupFixtureList.add(fixture);
            }
        }
        notifyDataSetChanged();
    }

    class ViewHolderFixtureControl extends RecyclerView.ViewHolder {


        SlipView control;

        public ViewHolderFixtureControl(@NonNull VpItemControlBinding binding) {
            super(binding.getRoot());
            control = binding.control;
            control.setTitleVisibility(View.GONE);
            control.setGroupLogoVisibility(View.GONE);
            control.setDelayTimeVisibility(View.GONE);
        }
    }


    class ViewHolderFixtureGroupControl extends RecyclerView.ViewHolder {

        SlipView control;

        public ViewHolderFixtureGroupControl(@NonNull VpItemControlBinding binding) {
            super(binding.getRoot());
            control = binding.control;
            control.setTitleVisibility(View.GONE);
            control.setDelayTimeVisibility(View.GONE);
        }
    }
}

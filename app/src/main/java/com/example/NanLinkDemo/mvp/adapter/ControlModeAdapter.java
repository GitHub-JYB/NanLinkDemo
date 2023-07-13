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
    private ArrayList<Fixture> noGroupFixtureList = new ArrayList<>();

    private ArrayList<FixtureGroup> hasFixtureGroupList = new ArrayList<>();
    private ArrayList<Fixture> hasGroupFixtureList = new ArrayList<>();
    private ArrayList<Integer> CHList;


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
            ((ViewHolderFixtureControl) holder).control.setName(noGroupFixtureList.get(position - hasFixtureGroupList.size()).getName());
            ((ViewHolderFixtureControl) holder).control.setCH("CH: " + TransformUtil.updateCH(noGroupFixtureList.get(position - hasFixtureGroupList.size()).getCH()));
        } else if (holder instanceof ViewHolderFixtureGroupControl) {
            ((ViewHolderFixtureGroupControl) holder).control.setName(hasFixtureGroupList.get(position).getName());
            CHList = new ArrayList<>();
            for (Fixture fixture : hasGroupFixtureList) {
                if (fixture.getFixtureGroupName().equals(hasFixtureGroupList.get(position).getName())) {
                    CHList.add(fixture.getCH());
                }
            }
            if (CHList.size() == 1) {
                ((ViewHolderFixtureGroupControl) holder).control.setCH("CH: " + TransformUtil.updateCH(CHList.get(0)));
            } else if (CHList.size() == 2) {
                if (CHList.get(0) > CHList.get(1)) {
                    ((ViewHolderFixtureGroupControl) holder).control.setCH("CH: " + TransformUtil.updateCH(CHList.get(1)) + ", " + TransformUtil.updateCH(CHList.get(0)));
                } else {
                    ((ViewHolderFixtureGroupControl) holder).control.setCH("CH: " + TransformUtil.updateCH(CHList.get(0)) + ", " + TransformUtil.updateCH(CHList.get(1)));
                }
            } else {
                int min;
                for (int i = 0; i < CHList.size() - 1; i++) {
                    for (int j = i + 1; j < CHList.size(); j++) {
                        if (CHList.get(j) < CHList.get(i)) {
                            min = CHList.get(j);
                            CHList.set(j, CHList.get(i));
                            CHList.set(i, min);
                        }
                    }
                }
                for (int i = 0; i < CHList.size() - 1; i++) {
                    if (CHList.get(i + 1) - CHList.get(i) > 1) {
                        ((ViewHolderFixtureGroupControl) holder).control.setCH("CH: " + TransformUtil.updateCH(CHList.get(0)) + ", " + TransformUtil.updateCH(CHList.get(1)) + "...");
                        break;
                    }
                    if (i >= CHList.size() - 2){
                        ((ViewHolderFixtureGroupControl) holder).control.setCH("CH: " + TransformUtil.updateCH(CHList.get(0)) + " - " + TransformUtil.updateCH(CHList.get(CHList.size() - 1)));
                    }
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return noGroupFixtureList.size() + hasFixtureGroupList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < hasFixtureGroupList.size()) {
            return TYPE_FIXTURE_GROUP;
        } else {
            return TYPE_FIXTURE;
        }
    }

    public void setData(ArrayList<FixtureGroup> fixtureGroupList, ArrayList<Fixture> fixtureList) {
        for (Fixture fixture : fixtureList) {
            if (fixture.getFixtureGroupName().equals("")) {
                noGroupFixtureList.add(fixture);
            } else {
                hasGroupFixtureList.add(fixture);
            }
        }
        for (FixtureGroup fixtureGroup : fixtureGroupList) {
            if (fixtureGroup.getFixtureNum() != 0) {
                hasFixtureGroupList.add(fixtureGroup);
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

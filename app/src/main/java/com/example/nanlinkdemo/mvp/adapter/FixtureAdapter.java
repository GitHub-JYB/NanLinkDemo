package com.example.nanlinkdemo.mvp.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.DB.bean.FixtureGroup;

import com.example.nanlinkdemo.databinding.VpDecorationFixtureListBinding;
import com.example.nanlinkdemo.databinding.VpItemFixtureBinding;
import com.example.nanlinkdemo.databinding.VpItemFixtureGroupBinding;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FixtureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_DECORATION_FIXTURE = 1;
    public static final int TYPE_FIXTURE = 2;
    private static final int TYPE_DECORATION_FIXTURE_GROUP = 3;
    public static final int TYPE_FIXTURE_GROUP = 4;
    private List<Fixture> fixtureList = new ArrayList<Fixture>();
    private List<Fixture> fixtureListNoGroup;
    private List<Fixture> fixtureListInGroup;
    private List<FixtureGroup> fixtureGroupList = new ArrayList<FixtureGroup>();
    private OnClickListener onClickListener;
    private MenuOnClickListener menuOnClickListener;
    private RightSecondIconOnClickListener rightSecondOnClickListener;
    private SpreadIconOnClickListener spreadOnClickListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_DECORATION_FIXTURE) {

            VpDecorationFixtureListBinding vpDecorationFixtureListBinding = VpDecorationFixtureListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolderDecorationFixture(vpDecorationFixtureListBinding);


        } else if (viewType == TYPE_FIXTURE) {
            VpItemFixtureBinding vpItemFixtureBinding = VpItemFixtureBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolderFixtureList(vpItemFixtureBinding);

        }else if (viewType == TYPE_DECORATION_FIXTURE_GROUP){
            VpDecorationFixtureListBinding vpDecorationFixtureListBinding = VpDecorationFixtureListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolderDecorationFixtureGroup(vpDecorationFixtureListBinding);
        }else {
            VpItemFixtureGroupBinding vpItemFixtureGroupBinding = VpItemFixtureGroupBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolderFixtureGroupList(vpItemFixtureGroupBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderFixtureList){
            if (fixtureGroupList.isEmpty()){
                ((ViewHolderFixtureList) holder).name.setText(fixtureListNoGroup.get(position - 1).getName());
                ((ViewHolderFixtureList) holder).number.setText("CH: " + fixtureListNoGroup.get(position - 1).getCH());
                ((ViewHolderFixtureList) holder).connectType.setText(fixtureListNoGroup.get(position - 1).getConnectType());
            }else {
                ((ViewHolderFixtureList) holder).name.setText(fixtureListNoGroup.get(position - fixtureGroupList.size() - 2).getName());
                ((ViewHolderFixtureList) holder).number.setText("CH: " + fixtureListNoGroup.get(position - fixtureGroupList.size() - 2).getCH());
                ((ViewHolderFixtureList) holder).connectType.setText(fixtureListNoGroup.get(position - fixtureGroupList.size() - 2).getConnectType());
            }
        }else if (holder instanceof ViewHolderFixtureGroupList){
            ((ViewHolderFixtureGroupList) holder).name.setText(fixtureGroupList.get(position - 1).getName());
            ((ViewHolderFixtureGroupList) holder).sceneNum.setText("设备数量: " + fixtureGroupList.get(position  - 1).getFixtureNum());
            fixtureListInGroup = new ArrayList<>();
            for (Fixture fixture : fixtureList){
                if (Objects.equals(fixture.getFixtureGroupName(), fixtureGroupList.get(position - 1).getName())){
                    fixtureListInGroup.add(fixture);
                }
            }

        }else if (holder instanceof ViewHolderDecorationFixture){
            ((ViewHolderDecorationFixture) holder).name.setText("灯光设备");
        }else if (holder instanceof ViewHolderDecorationFixtureGroup){
            ((ViewHolderDecorationFixtureGroup) holder).name.setText("设备群组");
        }
    }

    @Override
    public int getItemCount() {
        if (fixtureListNoGroup.isEmpty() && fixtureGroupList.isEmpty()){
            return 0;
        }else {
            if (fixtureListNoGroup.isEmpty()){
                return fixtureGroupList.size() + 1;
            }else if (fixtureGroupList.isEmpty()){
                return fixtureListNoGroup.size() + 1;
            }else {
                return fixtureListNoGroup.size() + fixtureGroupList.size() + 2;
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (fixtureListNoGroup.isEmpty()) {
            if (position == 0) {
                return TYPE_DECORATION_FIXTURE_GROUP;
            } else {
                return TYPE_FIXTURE_GROUP;
            }
        } else if (fixtureGroupList.isEmpty()) {
            if (position == 0) {
                return TYPE_DECORATION_FIXTURE;
            } else {
                return TYPE_FIXTURE;
            }
        } else {
            if (position == 0) {
                return TYPE_DECORATION_FIXTURE_GROUP;
            } else if (position <= fixtureGroupList.size()) {
                return TYPE_FIXTURE_GROUP;
            } else if (position == fixtureGroupList.size() + 1) {
                return TYPE_DECORATION_FIXTURE;
            } else {
                return TYPE_FIXTURE;
            }
        }
    }

    public void setData(List<FixtureGroup> fixtureGroupList, List<Fixture> fixtureList) {
        this.fixtureList = fixtureList;
        this.fixtureGroupList = fixtureGroupList;
        this.fixtureListNoGroup = new ArrayList<>();
        for (Fixture fixture : fixtureList){
            if(fixture.getFixtureGroupName().equals("")){
                fixtureListNoGroup.add(fixture);
            }
        }
        notifyDataSetChanged();
    }

    class ViewHolderFixtureList extends RecyclerView.ViewHolder {


        TextView name, number, connectType;

        public ViewHolderFixtureList(@NonNull VpItemFixtureBinding binding) {
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

    class ViewHolderFixtureGroupList extends RecyclerView.ViewHolder {


        TextView name, sceneNum;

        public ViewHolderFixtureGroupList(@NonNull VpItemFixtureGroupBinding binding) {
            super(binding.getRoot());
            name = binding.name;
            sceneNum = binding.number;
            binding.spreadIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (spreadOnClickListener != null){
                        spreadOnClickListener.onClick(getAdapterPosition());
                    }
                }
            });
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

    class ViewHolderDecorationFixture extends RecyclerView.ViewHolder {


        TextView name;

        public ViewHolderDecorationFixture(@NonNull VpDecorationFixtureListBinding binding) {
            super(binding.getRoot());
            name = binding.name;
        }

    }

    class ViewHolderDecorationFixtureGroup extends RecyclerView.ViewHolder {


        TextView name;

        public ViewHolderDecorationFixtureGroup(@NonNull VpDecorationFixtureListBinding binding) {
            super(binding.getRoot());
            name = binding.name;
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

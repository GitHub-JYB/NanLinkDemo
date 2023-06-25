package com.example.nanlinkdemo.mvp.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.DB.bean.FixtureGroup;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.FixtureGroupBinding;
import com.example.nanlinkdemo.databinding.VpDecorationFixtureListBinding;
import com.example.nanlinkdemo.databinding.VpItemFixtureBinding;
import com.example.nanlinkdemo.ui.UnlessLastItemDecoration;


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
    private MenuInGroupOnClickListener menuInGroupOnClickListener;
    private RightSecondIconInGroupOnClickListener rightSecondIconInGroupOnClickListener;


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
            FixtureGroupBinding vpItemFixtureGroupBinding = FixtureGroupBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
            fixtureListInGroup = new ArrayList<>();
            for (Fixture fixture : fixtureList){
                if (Objects.equals(fixture.getFixtureGroupName(), fixtureGroupList.get(position - 1).getName())){
                    fixtureListInGroup.add(fixture);
                }
            }

            ((ViewHolderFixtureGroupList) holder).name.setText(fixtureGroupList.get(position - 1).getName());
            ((ViewHolderFixtureGroupList) holder).sceneNum.setText("设备数量: " + fixtureGroupList.get(position  - 1).getFixtureNum());
            if (((ViewHolderFixtureGroupList) holder).showList && !fixtureListInGroup.isEmpty()){
                ((ViewHolderFixtureGroupList) holder).recyclerView.setVisibility(View.VISIBLE);
                ((ViewHolderFixtureGroupList) holder).spreadIcon.setImageResource(R.drawable.ic_spread);
            }else {
                ((ViewHolderFixtureGroupList) holder).recyclerView.setVisibility(View.GONE);
                ((ViewHolderFixtureGroupList) holder).spreadIcon.setImageResource(R.drawable.ic_close);
            }
            ((ViewHolderFixtureGroupList) holder).fixtureGroupAdapter.setData(fixtureListInGroup);




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
            ViewGroup.LayoutParams layoutParams = binding.fixture.getLayoutParams();
            layoutParams.width = MyApplication.dip2percentPx(361);
            layoutParams.height = MyApplication.dip2percentPx(68);
            binding.fixture.setPadding(MyApplication.dip2percentPx(9), MyApplication.dip2percentPx(6), MyApplication.dip2percentPx(9), MyApplication.dip2percentPx(12));
            binding.fixture.setLayoutParams(layoutParams);
            ViewGroup.LayoutParams layoutParams1 = binding.menu.getLayoutParams();
            layoutParams1.width = MyApplication.dip2percentPx(40);
            layoutParams1.height = MyApplication.dip2percentPx(50);
            binding.menu.setLayoutParams(layoutParams1);
            ViewGroup.LayoutParams layoutParams2 = binding.rightSecondIcon.getLayoutParams();
            layoutParams2.width = MyApplication.dip2percentPx(40);
            layoutParams2.height = MyApplication.dip2percentPx(50);
            binding.rightSecondIcon.setLayoutParams(layoutParams2);
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


        FixtureGroupAdapter fixtureGroupAdapter;
        TextView name, sceneNum;

        ImageView spreadIcon;
        RecyclerView recyclerView;

        boolean showList = false;


        public ViewHolderFixtureGroupList(@NonNull FixtureGroupBinding binding) {
            super(binding.getRoot());
            name = binding.name;
            sceneNum = binding.number;
            recyclerView = binding.recyclerView;
            spreadIcon = binding.spreadIcon;
            ViewGroup.LayoutParams layoutParams = binding.fixtureGroup.getLayoutParams();
            layoutParams.width = MyApplication.dip2percentPx(361);
            layoutParams.height = MyApplication.dip2percentPx(68);
            binding.fixtureGroup.setPadding(MyApplication.dip2percentPx(9), MyApplication.dip2percentPx(6), MyApplication.dip2percentPx(9), MyApplication.dip2percentPx(12));
            binding.fixtureGroup.setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) binding.recyclerView.getLayoutParams();
            layoutParams1.width = MyApplication.dip2percentPx(343);
            layoutParams1.topMargin = MyApplication.dip2percentPx(48);
            layoutParams1.leftMargin = MyApplication.dip2percentPx(9);
            layoutParams1.rightMargin = MyApplication.dip2percentPx(9);

            binding.recyclerView.setPadding(0, MyApplication.dip2percentPx(8), 0, 0);
            binding.recyclerView.setLayoutParams(layoutParams1);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

            // 设置分割线格式并添加
            UnlessLastItemDecoration decoration = new UnlessLastItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
            decoration.setDrawable(recyclerView.getContext().getResources().getDrawable(R.drawable.decoration_fixture));
            binding.recyclerView.addItemDecoration(decoration);

            fixtureGroupAdapter = new FixtureGroupAdapter();
            binding.recyclerView.setAdapter(fixtureGroupAdapter);
            fixtureGroupAdapter.setMenuOnClickListener(new FixtureGroupAdapter.MenuOnClickListener() {
                @Override
                public void onClick(Fixture fixture) {
                    if (menuInGroupOnClickListener != null){
                        menuInGroupOnClickListener.onClick(fixture);
                    }
                }
            });
            fixtureGroupAdapter.setRightSecondIconOnClickListener(new FixtureGroupAdapter.RightSecondIconOnClickListener() {
                @Override
                public void onClick(Fixture fixture) {
                    if (rightSecondIconInGroupOnClickListener != null){
                        rightSecondIconInGroupOnClickListener.onClick(fixture);
                    }
                }
            });
            ViewGroup.LayoutParams layoutParams2 = binding.menu.getLayoutParams();
            layoutParams2.width = MyApplication.dip2percentPx(40);
            layoutParams2.height = MyApplication.dip2percentPx(50);
            binding.menu.setLayoutParams(layoutParams2);
            ViewGroup.LayoutParams layoutParams3 = binding.rightSecondIcon.getLayoutParams();
            layoutParams3.width = MyApplication.dip2percentPx(40);
            layoutParams3.height = MyApplication.dip2percentPx(50);
            binding.rightSecondIcon.setLayoutParams(layoutParams3);
            ViewGroup.LayoutParams layoutParams4 = binding.spreadIcon.getLayoutParams();
            layoutParams4.width = MyApplication.dip2percentPx(40);
            layoutParams4.height = MyApplication.dip2percentPx(50);
            binding.spreadIcon.setLayoutParams(layoutParams4);

            binding.spreadIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showList = !showList;
                    notifyDataSetChanged();
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
            ViewGroup.LayoutParams layoutParams = binding.getRoot().getLayoutParams();
            layoutParams.width = MyApplication.dip2percentPx(343);
            layoutParams.height = MyApplication.dip2percentPx(40);
            binding.getRoot().setLayoutParams(layoutParams);
        }

    }

    class ViewHolderDecorationFixtureGroup extends RecyclerView.ViewHolder {


        TextView name;

        public ViewHolderDecorationFixtureGroup(@NonNull VpDecorationFixtureListBinding binding) {
            super(binding.getRoot());
            name = binding.name;
            ViewGroup.LayoutParams layoutParams = binding.getRoot().getLayoutParams();
            layoutParams.width = MyApplication.dip2percentPx(343);
            layoutParams.height = MyApplication.dip2percentPx(40);
            binding.getRoot().setLayoutParams(layoutParams);
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

    public interface MenuInGroupOnClickListener {
        void onClick(Fixture fixture);
    }

    public void setMenuInGroupOnClickListener(MenuInGroupOnClickListener menuInGroupOnClickListener){
        this.menuInGroupOnClickListener = menuInGroupOnClickListener;
    }

    public interface RightSecondIconInGroupOnClickListener {
        void onClick(Fixture fixture);
    }

    public void setRightSecondIconInGroupOnClickListener(RightSecondIconInGroupOnClickListener rightSecondIconInGroupOnClickListener){
        this.rightSecondIconInGroupOnClickListener = rightSecondIconInGroupOnClickListener;
    }

}

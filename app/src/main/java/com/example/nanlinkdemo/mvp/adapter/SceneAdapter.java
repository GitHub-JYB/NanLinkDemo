package com.example.nanlinkdemo.mvp.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.databinding.VpDecorationBinding;
import com.example.nanlinkdemo.databinding.VpSceneScenelistBinding;
import com.example.nanlinkdemo.databinding.VpScenegroupScenelistBinding;
import com.example.nanlinkdemo.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class SceneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int TYPE_SCENE = 1;
    public static final int TYPE_SCENE_GROUP = 2;
    private static final int TYPE_DECORATION = 3;
    private List<Scene> sceneList = new ArrayList<Scene>();
    private List<SceneGroup> sceneGroupList = new ArrayList<SceneGroup>();
    private OnClickListener onClickListener;
    private MenuOnClickListener menuOnClickListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_SCENE_GROUP) {

            VpScenegroupScenelistBinding vpScenegroupScenelistBinding = VpScenegroupScenelistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolderSceneGroupSceneList(vpScenegroupScenelistBinding);


        } else if (viewType == TYPE_DECORATION) {
            VpDecorationBinding vpDecorationBinding = VpDecorationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolderDecorationSceneList(vpDecorationBinding);

        }else {
            VpSceneScenelistBinding vpSceneScenelistBinding = VpSceneScenelistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolderSceneSceneList(vpSceneScenelistBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderSceneSceneList){
            ((ViewHolderSceneSceneList) holder).name.setText(sceneList.get(position).getName());
            ((ViewHolderSceneSceneList) holder).fixtureNum.setText("设备数量: " + sceneList.get(position).getFixtureNum());
            ((ViewHolderSceneSceneList) holder).remark.setText(sceneList.get(position).getRemark());
            ((ViewHolderSceneSceneList) holder).createdData.setText("创建时间:" + DateUtil.getDate(sceneList.get(position).getCreatedDate()));
            ((ViewHolderSceneSceneList) holder).modifiedData.setText("最后编辑时间:" + DateUtil.getDate(sceneList.get(position).getModifiedDate()));
        }else if (holder instanceof ViewHolderSceneGroupSceneList){
            ((ViewHolderSceneGroupSceneList) holder).name.setText(sceneGroupList.get(position - sceneList.size() - 1).getName());
            ((ViewHolderSceneGroupSceneList) holder).sceneNum.setText("场景数量: " + sceneGroupList.get(position - sceneList.size() - 1).getSceneNum());
            ((ViewHolderSceneGroupSceneList) holder).remark.setText(sceneGroupList.get(position - sceneList.size() - 1).getRemark());
            ((ViewHolderSceneGroupSceneList) holder).createdData.setText("创建时间:" + DateUtil.getDate(sceneGroupList.get(position - sceneList.size() - 1).getCreatedDate()));
            ((ViewHolderSceneGroupSceneList) holder).modifiedData.setText("最后编辑时间:" + DateUtil.getDate(sceneGroupList.get(position - sceneList.size() - 1).getModifiedDate()));
        }else if (holder instanceof ViewHolderDecorationSceneList){
            ((ViewHolderDecorationSceneList) holder).name.setText("场景群组");
        }
    }

    @Override
    public int getItemCount() {
        if (sceneList.size() == 0 && sceneGroupList.size() == 0){
            return 0;
        }else if (sceneGroupList.size() == 0){
            return sceneList.size();
        }else {
            return sceneList.size() + sceneGroupList.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {

       if (sceneGroupList.size() == 0){
           return TYPE_SCENE;
       }else if (sceneList.size() == 0){
           if (position == 0) {
               return TYPE_DECORATION;
           }else {
               return TYPE_SCENE_GROUP;
           }
       }else {
           if (position < sceneList.size()) {
               return TYPE_SCENE;
           } else if (position == sceneList.size()) {
               return TYPE_DECORATION;
           } else {
               return TYPE_SCENE_GROUP;
           }
       }
    }

    public void setData(List<Scene> sceneList, List<SceneGroup> sceneGroupList) {
        this.sceneList = sceneList;
        this.sceneGroupList = sceneGroupList;
        notifyDataSetChanged();
    }

    class ViewHolderSceneSceneList extends RecyclerView.ViewHolder {


        TextView name, fixtureNum, remark, createdData, modifiedData;

        public ViewHolderSceneSceneList(@NonNull VpSceneScenelistBinding binding) {
            super(binding.getRoot());
            name = binding.name;
            fixtureNum = binding.fixtureNum;
            remark = binding.remark;
            createdData = binding.createdData;
            modifiedData = binding.modifiedData;
            binding.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuOnClickListener != null){
                        menuOnClickListener.onClick(TYPE_SCENE, getAdapterPosition());
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

    class ViewHolderSceneGroupSceneList extends RecyclerView.ViewHolder {


        TextView name, sceneNum, remark, createdData, modifiedData;

        public ViewHolderSceneGroupSceneList(@NonNull VpScenegroupScenelistBinding binding) {
            super(binding.getRoot());
            name = binding.name;
            sceneNum = binding.sceneNum;
            remark = binding.remark;
            createdData = binding.createdData;
            modifiedData = binding.modifiedData;
            binding.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuOnClickListener != null){
                        menuOnClickListener.onClick(TYPE_SCENE_GROUP, getAdapterPosition());
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

    class ViewHolderDecorationSceneList extends RecyclerView.ViewHolder {


        TextView name, sceneNum, remark, createdData, modifiedData;

        public ViewHolderDecorationSceneList(@NonNull VpDecorationBinding binding) {
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
        void onClick(int type, int position);
    }

    public void setMenuOnClickListener(MenuOnClickListener menuOnClickListener){
        this.menuOnClickListener = menuOnClickListener;
    }

}

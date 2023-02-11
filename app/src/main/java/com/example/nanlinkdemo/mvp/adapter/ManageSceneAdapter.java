package com.example.nanlinkdemo.mvp.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.VpItemSceneManageBinding;

import java.util.ArrayList;
import java.util.List;

public class ManageSceneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int TYPE_MANAGE_SCENE = 1;
    private List<Scene> sceneList = new ArrayList<Scene>();
    private OnClickListener onClickListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpItemSceneManageBinding vpItemSceneManageBinding = VpItemSceneManageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderManageSceneList(vpItemSceneManageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderManageSceneList){
            ((ViewHolderManageSceneList) holder).name.setText(sceneList.get(position).getName());
            ((ViewHolderManageSceneList) holder).number.setText("设备数量: " + sceneList.get(position).getFixtureNum());
            if (sceneList.get(position).getSceneGroup().isEmpty()){
                ((ViewHolderManageSceneList) holder).rightIcon.setImageResource(R.drawable.ic_unselected);
            }else {
                ((ViewHolderManageSceneList) holder).rightIcon.setImageResource(R.drawable.ic_selected);
            }
        }
    }

    @Override
    public int getItemCount() {
        return sceneList.size();
    }


    public void setData(List<Scene> sceneList) {
        this.sceneList = sceneList;
        notifyDataSetChanged();
    }

    class ViewHolderManageSceneList extends RecyclerView.ViewHolder {


        TextView name, number;
        ImageView rightIcon;

        public ViewHolderManageSceneList(@NonNull VpItemSceneManageBinding binding) {
            super(binding.getRoot());
            name = binding.name;
            number = binding.number;
            rightIcon = binding.rightIcon;

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

package com.example.nanlinkdemo.mvp.adapter;

import static com.example.nanlinkdemo.bean.Menu.TYPE_EMPTY;
import static com.example.nanlinkdemo.bean.Menu.TYPE_ITEM;
import static com.example.nanlinkdemo.bean.Menu.TYPE_LOGO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.databinding.VpEmptyMenuBinding;
import com.example.nanlinkdemo.databinding.VpImageMenuBinding;
import com.example.nanlinkdemo.databinding.VpItemMenuBinding;

import java.util.ArrayList;
import java.util.List;

public class SceneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Menu> menuList = new ArrayList<>();
    private OnClickListener onClickListener;



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_LOGO:
                VpImageMenuBinding vpImageMenuBinding = VpImageMenuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderImageMenu(vpImageMenuBinding);
            case TYPE_ITEM:
                VpItemMenuBinding vpItemMenuBinding = VpItemMenuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderItemMenu(vpItemMenuBinding);
            default:
                VpEmptyMenuBinding vpEmptyMenuBinding= VpEmptyMenuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderEmptyMenu(vpEmptyMenuBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderImageMenu){

        }
        if (holder instanceof ViewHolderEmptyMenu){

        }else if (holder instanceof ViewHolderItemMenu){
            if (menuList.get(position).getIconResId() != 0){
                ((ViewHolderItemMenu) holder).iconMenu.setImageResource(menuList.get(position).getIconResId());
            }
            if (menuList.get(position).getType() != 0){
                ((ViewHolderItemMenu) holder).stateMenu.setImageResource(menuList.get(position).getStateResId());
            }
            if (menuList.get(position).getText() != null){
                ((ViewHolderItemMenu) holder).textMenu.setText(menuList.get(position).getText());
            }
        }
    }

    @Override
    public int getItemCount() {
        return menuList == null? 0 : menuList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (menuList.get(position).getType()){
            case 0:
                return TYPE_LOGO;
            case 2:
                return TYPE_ITEM;
            default:
                return TYPE_EMPTY;
        }
    }

    public void setData(List<Menu> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }

    class ViewHolderItemMenu extends RecyclerView.ViewHolder {


        ImageView iconMenu, stateMenu;
        TextView textMenu;

        public ViewHolderItemMenu(@NonNull VpItemMenuBinding binding) {
            super(binding.getRoot());
            iconMenu = binding.iconMenu;
            stateMenu = binding.stateMenu;
            textMenu = binding.textMenu;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null){
                        onClickListener.onClick(menuList.get(getAdapterPosition()).getText());
                    }
                }
            });
        }
    }

    public interface OnClickListener {
        void onClick(String menuText);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    class ViewHolderEmptyMenu extends RecyclerView.ViewHolder {


        public ViewHolderEmptyMenu(@NonNull VpEmptyMenuBinding binding) {
            super(binding.getRoot());
        }
    }

    class ViewHolderImageMenu extends RecyclerView.ViewHolder {


        public ViewHolderImageMenu(@NonNull VpImageMenuBinding binding) {
            super(binding.getRoot());
        }
    }
}

package com.example.nanlinkdemo.mvp.adapter;

import static com.example.nanlinkdemo.bean.Menu.TYPE_EMPTY;
import static com.example.nanlinkdemo.bean.Menu.TYPE_ITEM_nav_bg;
import static com.example.nanlinkdemo.bean.Menu.TYPE_ITEM_gray_bg;
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
import com.example.nanlinkdemo.databinding.VpItemMenuGrayBgBinding;
import com.example.nanlinkdemo.databinding.VpItemMenuNavBgBinding;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Menu> menuList = new ArrayList<>();
    private OnClickListener onClickListener;



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_LOGO:
                VpImageMenuBinding vpImageMenuBinding = VpImageMenuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderImageMenu(vpImageMenuBinding);
            case TYPE_ITEM_gray_bg:
                VpItemMenuGrayBgBinding vpItemMenuGrayBgBinding = VpItemMenuGrayBgBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderItemMenuGrayBg(vpItemMenuGrayBgBinding);
            case TYPE_ITEM_nav_bg:
                VpItemMenuNavBgBinding vpItemMenuNavBgBinding = VpItemMenuNavBgBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderItemMenuNavBg(vpItemMenuNavBgBinding);
            default:
                VpEmptyMenuBinding vpEmptyMenuBinding= VpEmptyMenuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderEmptyMenu(vpEmptyMenuBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderImageMenu){

        }else if (holder instanceof ViewHolderEmptyMenu){

        }else if (holder instanceof ViewHolderItemMenuGrayBg){
            if (menuList.get(position).getIconResId() == 0){
                ((ViewHolderItemMenuGrayBg) holder).iconMenu.setVisibility(View.INVISIBLE);
            }else {
                ((ViewHolderItemMenuGrayBg) holder).iconMenu.setVisibility(View.VISIBLE);
                ((ViewHolderItemMenuGrayBg) holder).iconMenu.setImageResource(menuList.get(position).getIconResId());
            }
            if (menuList.get(position).getStateResId() == 0){
                ((ViewHolderItemMenuGrayBg) holder).stateMenu.setVisibility(View.INVISIBLE);
            }else {
                ((ViewHolderItemMenuGrayBg) holder).stateMenu.setVisibility(View.VISIBLE);
                ((ViewHolderItemMenuGrayBg) holder).stateMenu.setImageResource(menuList.get(position).getStateResId());
            }
            if (menuList.get(position).getEndStateResId() == 0){
                ((ViewHolderItemMenuGrayBg) holder).endStateMenu.setVisibility(View.INVISIBLE);
            }else {
                ((ViewHolderItemMenuGrayBg) holder).endStateMenu.setVisibility(View.VISIBLE);
                ((ViewHolderItemMenuGrayBg) holder).endStateMenu.setImageResource(menuList.get(position).getEndStateResId());
            }
            if (menuList.get(position).getText() != null){
                ((ViewHolderItemMenuGrayBg) holder).textMenu.setText(menuList.get(position).getText());
            }
        }else if (holder instanceof ViewHolderItemMenuNavBg){
            if (menuList.get(position).getIconResId() == 0){
                ((ViewHolderItemMenuNavBg) holder).iconMenu.setVisibility(View.INVISIBLE);
            }else {
                ((ViewHolderItemMenuNavBg) holder).iconMenu.setVisibility(View.VISIBLE);
                ((ViewHolderItemMenuNavBg) holder).iconMenu.setImageResource(menuList.get(position).getIconResId());
            }
            if (menuList.get(position).getStateResId() == 0){
                ((ViewHolderItemMenuNavBg) holder).stateMenu.setVisibility(View.INVISIBLE);
            }else {
                ((ViewHolderItemMenuNavBg) holder).stateMenu.setVisibility(View.VISIBLE);
                ((ViewHolderItemMenuNavBg) holder).stateMenu.setImageResource(menuList.get(position).getStateResId());
            }
            if (menuList.get(position).getText() != null){
                ((ViewHolderItemMenuNavBg) holder).textMenu.setText(menuList.get(position).getText());
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
                return TYPE_ITEM_gray_bg;
            case 3:
                return TYPE_ITEM_nav_bg;
            default:
                return TYPE_EMPTY;
        }
    }

    public void setData(List<Menu> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }

    class ViewHolderItemMenuGrayBg extends RecyclerView.ViewHolder {


        ImageView iconMenu, stateMenu, endStateMenu;
        TextView textMenu;

        public ViewHolderItemMenuGrayBg(@NonNull VpItemMenuGrayBgBinding binding) {
            super(binding.getRoot());
            iconMenu = binding.iconMenu;
            stateMenu = binding.stateMenu;
            textMenu = binding.textMenu;
            endStateMenu = binding.endStateMenu;
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

    class ViewHolderItemMenuNavBg extends RecyclerView.ViewHolder {


        ImageView iconMenu, stateMenu;
        TextView textMenu;

        public ViewHolderItemMenuNavBg(@NonNull VpItemMenuNavBgBinding binding) {
            super(binding.getRoot());
            iconMenu = binding.iconMenu;
            stateMenu = binding.stateMenu;
            textMenu = binding.textMenu;
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

package com.example.NanLinkDemo.mvp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.databinding.VpItemBoxControlBinding;
import com.example.NanLinkDemo.databinding.VpItemDividerControlBinding;
import com.example.NanLinkDemo.databinding.VpItemEmptyControlBinding;
import com.example.NanLinkDemo.databinding.VpItemSlipControlBinding;
import com.example.NanLinkDemo.databinding.VpItemSlmMenuControlBinding;
import com.example.NanLinkDemo.ui.BoxView;
import com.example.NanLinkDemo.ui.SlipView;
import com.example.NanLinkDemo.ui.SlmMenuView;

import java.util.ArrayList;

public class ControlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_UNKNOW = 0;
    private static final int TYPE_SLIP = 1;
    private static final int TYPE_BOX = 2;
    private static final int TYPE_SLM = 3;
    private static final int TYPE_DIVIDER = 4;

    private OnDataUpdateListener onDataUpdateListener;
    private ControlAdapter.OnDataClickListener onDataClickListener;
    private OnDelayTimeClickListener onDelayTimeClickListener;
    private ArrayList<DeviceDataMessage.Control> controls;

    private int dim = 50;

    private int delayTime = 2;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_SLIP:
                VpItemSlipControlBinding vpItemControlBinding = VpItemSlipControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderSlipControl(vpItemControlBinding);
            case TYPE_BOX:
                VpItemBoxControlBinding vpItemBoxControlBinding = VpItemBoxControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderBoxControl(vpItemBoxControlBinding);
            case TYPE_SLM:
                VpItemSlmMenuControlBinding vpItemSlmMenuControlBinding = VpItemSlmMenuControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderSlmControl(vpItemSlmMenuControlBinding);
            case TYPE_DIVIDER:
                VpItemDividerControlBinding vpItemDividerControlBinding = VpItemDividerControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderDividerControl(vpItemDividerControlBinding);
            default:
                VpItemEmptyControlBinding vpItemEmptyControlBinding = VpItemEmptyControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderEmptyControl(vpItemEmptyControlBinding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DeviceDataMessage.Control control = controls.get(position);
        if (holder instanceof ViewHolderBoxControl) {
            int selectIndex = control.getSelectIndex();
            if (control.getControls().size() > 1) {
                ((ViewHolderBoxControl) holder).control.setTitle(control.getRemark());
                ((ViewHolderBoxControl) holder).control.check(selectIndex);
                ArrayList<String> boxTextList = new ArrayList<>();
                for (DeviceDataMessage.Control control1 : control.getControls()) {
                    boxTextList.add(control1.getRemark());
                }
                ((ViewHolderBoxControl) holder).control.setData(boxTextList);
            } else {
                ((ViewHolderBoxControl) holder).control.setVisibility(View.GONE);
            }
            ((ViewHolderBoxControl) holder).controlAdapter.setData(control.getControls().get(selectIndex).getControls());
        } else if (holder instanceof ViewHolderSlipControl) {
            int max = Integer.parseInt(control.getElements().getMax());
            int min = Integer.parseInt(control.getElements().getMin());
            int step = Integer.parseInt(control.getElements().getStep());
            int value = Integer.parseInt(control.getElements().getItem());
            ((ViewHolderSlipControl) holder).control.setRemark(control.getRemark());
            ((ViewHolderSlipControl) holder).control.setTitle(control.getRemark());
            if (control.getElements().getToggle() == null) {
                ((ViewHolderSlipControl) holder).control.setDelayBtnVisibility(View.GONE);
            } else {
                ((ViewHolderSlipControl) holder).control.setDelayBtnVisibility(View.VISIBLE);
            }
            if (control.getElements().getDelay() == null) {
                ((ViewHolderSlipControl) holder).control.setDelayTimeVisibility(View.GONE);
            } else {
                ((ViewHolderSlipControl) holder).control.setDelayTime(delayTime);
            }
            if (control.getRemark().equals("亮度")) {
                ((ViewHolderSlipControl) holder).control.setSeekBar(max, min, step, dim);
            } else {
                ((ViewHolderSlipControl) holder).control.setSeekBar(max, min, step, value);
            }

        } else if (holder instanceof ViewHolderSlmControl) {
            int selectIndex = control.getSelectIndex();
            ArrayList<String> menuTextList = new ArrayList<>();
            for (DeviceDataMessage.Control control1 : control.getControls()) {
                menuTextList.add(control1.getRemark());
            }
            ((ViewHolderSlmControl) holder).control.setTitle(menuTextList.get(selectIndex));
            ((ViewHolderSlmControl) holder).control.check(selectIndex);
            ((ViewHolderSlmControl) holder).control.setMenuData(menuTextList);
            ((ViewHolderSlmControl) holder).controlAdapter.setData(control.getControls().get(selectIndex).getControls());
        }
    }


    @Override
    public int getItemCount() {
        return controls == null ? 0 : controls.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (controls.get(position).getControlType()) {
            case "slip":
                return TYPE_SLIP;
            case "box":
                return TYPE_BOX;
            case "slm":
                return TYPE_SLM;
            case "divider":
                return TYPE_DIVIDER;
            default:
                return TYPE_UNKNOW;
        }
    }

    public void setData(ArrayList<DeviceDataMessage.Control> controls) {
        this.controls = controls;
        notifyDataSetChanged();
    }

    class ViewHolderSlipControl extends RecyclerView.ViewHolder {


        SlipView control;

        public ViewHolderSlipControl(@NonNull VpItemSlipControlBinding binding) {
            super(binding.getRoot());
            control = binding.control;
            control.setOnDelayTimeClickListener(new SlipView.OnDelayTimeClickListener() {
                @Override
                public void onDelayTimeClick(View view) {
                    if (onDelayTimeClickListener != null) {
                        onDelayTimeClickListener.onDelayTimeClick(getAdapterPosition(), (String) control.getDelayTime());
                    }
                }
            });
            control.setOnDataClickListener(new SlipView.OnDataClickListener() {
                @Override
                public void onDataClick(View view) {
                    if (onDataClickListener != null) {
                        onDataClickListener.onDataClick(getAdapterPosition(), (String) control.getData());
                    }
                }
            });
            control.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
                @Override
                public void onDataChanged(int index) {
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), (String) control.getData());
                    }
                }
            });
        }
    }

    class ViewHolderBoxControl extends RecyclerView.ViewHolder {


        BoxView control;

        ControlAdapter controlAdapter;

        public ViewHolderBoxControl(@NonNull VpItemBoxControlBinding binding) {
            super(binding.getRoot());
            control = binding.control;
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.recyclerView.getContext()));
            controlAdapter = new ControlAdapter();
            controlAdapter.setDim(dim);
            controlAdapter.setDelayTime(delayTime);
            binding.recyclerView.setAdapter(controlAdapter);
            binding.control.setOnCheckedChangeListener(new BoxView.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(int index) {
                    controlAdapter.setData(controls.get(getAdapterPosition()).getControls().get(index).getControls());
                }
            });
        }
    }

    class ViewHolderSlmControl extends RecyclerView.ViewHolder {


        SlmMenuView control;

        ControlAdapter controlAdapter;

        public ViewHolderSlmControl(@NonNull VpItemSlmMenuControlBinding binding) {
            super(binding.getRoot());
            control = binding.control;
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.recyclerView.getContext()));
            controlAdapter = new ControlAdapter();
            controlAdapter.setDim(dim);
            controlAdapter.setDelayTime(delayTime);
            binding.recyclerView.setAdapter(controlAdapter);
            binding.control.setOnIndexChangeListener(new SlmMenuView.OnIndexChangeListener() {
                @Override
                public void onIndexChanged(int index) {
                    controlAdapter.setData(controls.get(getAdapterPosition()).getControls().get(index).getControls());
                }
            });
        }
    }

    class ViewHolderDividerControl extends RecyclerView.ViewHolder {

        public ViewHolderDividerControl(@NonNull VpItemDividerControlBinding binding) {
            super(binding.getRoot());
        }
    }

    class ViewHolderEmptyControl extends RecyclerView.ViewHolder {

        public ViewHolderEmptyControl(@NonNull VpItemEmptyControlBinding binding) {
            super(binding.getRoot());
        }
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public void setOnDataUpdateListener(OnDataUpdateListener onDataUpdateListener) {
        this.onDataUpdateListener = onDataUpdateListener;
    }

    public interface OnDataUpdateListener {
        void onDataUpdate(int position, String dim);
    }

    public void setOnDataClickListener(OnDataClickListener onDataClickListener) {
        this.onDataClickListener = onDataClickListener;
    }

    public interface OnDataClickListener {
        void onDataClick(int position, String dim);
    }

    public void setOnDelayTimeClickListener(OnDelayTimeClickListener onDelayTimeClickListener) {
        this.onDelayTimeClickListener = onDelayTimeClickListener;
    }

    public interface OnDelayTimeClickListener {
        void onDelayTimeClick(int position, String delayTime);
    }
}

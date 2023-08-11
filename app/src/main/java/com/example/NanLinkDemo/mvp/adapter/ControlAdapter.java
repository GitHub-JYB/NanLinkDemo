package com.example.NanLinkDemo.mvp.adapter;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.databinding.VpItemBoxControlBinding;
import com.example.NanLinkDemo.databinding.VpItemColorDropDownControlBinding;
import com.example.NanLinkDemo.databinding.VpItemColordefaultControlBinding;
import com.example.NanLinkDemo.databinding.VpItemColormanualControlBinding;
import com.example.NanLinkDemo.databinding.VpItemColorslipControlBinding;
import com.example.NanLinkDemo.databinding.VpItemDividerControlBinding;
import com.example.NanLinkDemo.databinding.VpItemDoubleSlipControlBinding;
import com.example.NanLinkDemo.databinding.VpItemEmptyControlBinding;
import com.example.NanLinkDemo.databinding.VpItemHsiControlBinding;
import com.example.NanLinkDemo.databinding.VpItemRgbwControlBinding;
import com.example.NanLinkDemo.databinding.VpItemSlipControlBinding;
import com.example.NanLinkDemo.databinding.VpItemSlmMenuControlBinding;
import com.example.NanLinkDemo.databinding.VpItemTouchbtnControlBinding;
import com.example.NanLinkDemo.databinding.VpItemXyControlBinding;
import com.example.NanLinkDemo.ui.BoxView;
import com.example.NanLinkDemo.ui.DoubleSlipView;
import com.example.NanLinkDemo.ui.HsiView;
import com.example.NanLinkDemo.ui.RgbwView;
import com.example.NanLinkDemo.ui.SlipView;
import com.example.NanLinkDemo.ui.SlmMenuView;
import com.example.NanLinkDemo.ui.XYView;

import java.util.ArrayList;

public class ControlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_UNKNOW = 0;
    private static final int TYPE_SLIP = 1;
    private static final int TYPE_BOX = 2;
    private static final int TYPE_SLM = 3;
    private static final int TYPE_DIVIDER = 4;
    private static final int TYPE_DOUBLE_SLIP = 5;
    private static final int TYPE_TOUCH_BTN = 6;
    private static final int TYPE_COLOR_SLIP = 7;
    private static final int TYPE_COLOR_DROP_DOWN = 8;
    private static final int TYPE_HSI = 9;
    private static final int TYPE_RGBW = 10;
    private static final int TYPE_XY = 11;
    private static final int TYPE_COLOR_DEFAULT = 12;
    private static final int TYPE_COLOR_MANUAL = 13;


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
            case TYPE_DOUBLE_SLIP:
                VpItemDoubleSlipControlBinding vpItemDoubleSlipControlBinding = VpItemDoubleSlipControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderDoubleSlipControl(vpItemDoubleSlipControlBinding);
            case TYPE_BOX:
                VpItemBoxControlBinding vpItemBoxControlBinding = VpItemBoxControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderBoxControl(vpItemBoxControlBinding);
            case TYPE_SLM:
                VpItemSlmMenuControlBinding vpItemSlmMenuControlBinding = VpItemSlmMenuControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderSlmControl(vpItemSlmMenuControlBinding);
            case TYPE_DIVIDER:
                VpItemDividerControlBinding vpItemDividerControlBinding = VpItemDividerControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderDividerControl(vpItemDividerControlBinding);
            case TYPE_TOUCH_BTN:
                VpItemTouchbtnControlBinding vpItemTouchbtnControlBinding = VpItemTouchbtnControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderTouchBtnControl(vpItemTouchbtnControlBinding);
            case TYPE_COLOR_SLIP:
                VpItemColorslipControlBinding vpItemColorslipControlBinding = VpItemColorslipControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderColorSlipControl(vpItemColorslipControlBinding);
            case TYPE_COLOR_DROP_DOWN:
                VpItemColorDropDownControlBinding vpItemColorDropDownControlBinding = VpItemColorDropDownControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderColorDropDownControl(vpItemColorDropDownControlBinding);
            case TYPE_HSI:
                VpItemHsiControlBinding vpItemHsiControlBinding = VpItemHsiControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderHsiControl(vpItemHsiControlBinding);
            case TYPE_RGBW:
                VpItemRgbwControlBinding vpItemRgbwControlBinding = VpItemRgbwControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderRgbwControl(vpItemRgbwControlBinding);
            case TYPE_XY:
                VpItemXyControlBinding vpItemXyControlBinding = VpItemXyControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderXYControl(vpItemXyControlBinding);
            case TYPE_COLOR_DEFAULT:
                VpItemColordefaultControlBinding vpItemColordefaultControlBinding = VpItemColordefaultControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderColorDefaultControl(vpItemColordefaultControlBinding);
            case TYPE_COLOR_MANUAL:
                VpItemColormanualControlBinding vpItemColormanualControlBinding = VpItemColormanualControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolderColorManualControl(vpItemColormanualControlBinding);
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

        } else if (holder instanceof ViewHolderDoubleSlipControl) {
            int max = Integer.parseInt(control.getElements().getMax());
            int min = Integer.parseInt(control.getElements().getMin());
            int step = Integer.parseInt(control.getElements().getStep());
            int maxItem = Integer.parseInt(control.getElements().getMaxItem());
            int minItem = Integer.parseInt(control.getElements().getMinItem());
            ((ViewHolderDoubleSlipControl) holder).control.setRemark(control.getRemark());
            ((ViewHolderDoubleSlipControl) holder).control.setTitle(control.getRemark());
            if (control.getElements().getToggle() == null) {
                ((ViewHolderDoubleSlipControl) holder).control.setDelayBtnVisibility(View.GONE);
            } else {
                ((ViewHolderDoubleSlipControl) holder).control.setDelayBtnVisibility(View.VISIBLE);
            }
            if (control.getElements().getDelay() == null) {
                ((ViewHolderDoubleSlipControl) holder).control.setDelayTimeVisibility(View.GONE);
            } else {
                ((ViewHolderDoubleSlipControl) holder).control.setDelayTime(delayTime);
            }
            if (control.getRemark().equals("亮度范围")) {
                if (dim < minItem) {
                    if (dim < 1f / 10 * (max - min)) {
                        ((ViewHolderDoubleSlipControl) holder).control.setSeekBar(max, min, step, dim, (int) (1f / 100 * (max - min)));
                    } else {
                        ((ViewHolderDoubleSlipControl) holder).control.setSeekBar(max, min, step, dim, (int) (dim - 1f / 10 * (max - min)));
                    }
                } else {
                    ((ViewHolderDoubleSlipControl) holder).control.setSeekBar(max, min, step, dim, minItem);
                }
            } else {
                ((ViewHolderDoubleSlipControl) holder).control.setSeekBar(max, min, step, maxItem, minItem);
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
        } else if (holder instanceof ViewHolderTouchBtnControl) {
            ((ViewHolderTouchBtnControl) holder).control.setText(control.getRemark());
        } else if (holder instanceof ViewHolderColorSlipControl) {
            int max = Integer.parseInt(control.getElements().getMax());
            int min = Integer.parseInt(control.getElements().getMin());
            int step = Integer.parseInt(control.getElements().getStep());
            int value = Integer.parseInt(control.getElements().getItem());
            ((ViewHolderColorSlipControl) holder).control.setRemark(control.getRemark());
            ((ViewHolderColorSlipControl) holder).control.setTitle(control.getRemark());
            if (control.getElements().getToggle() == null) {
                ((ViewHolderColorSlipControl) holder).control.setDelayBtnVisibility(View.GONE);
            } else {
                ((ViewHolderColorSlipControl) holder).control.setDelayBtnVisibility(View.VISIBLE);
            }
            if (control.getElements().getDelay() == null) {
                ((ViewHolderColorSlipControl) holder).control.setDelayTimeVisibility(View.GONE);
            } else {
                ((ViewHolderColorSlipControl) holder).control.setDelayTime(delayTime);
            }
            ((ViewHolderColorSlipControl) holder).control.setSeekBar(max, min, step, value);
            int num = max - control.getControls().size();
            if (num > 0) {
                for (int i = 0; i < num; i++) {
                    control.getControls().add(control.getControls().get(0));
                }
            }
            ArrayList<DeviceDataMessage.Control> dataList = new ArrayList<>();
            for (int i = 0; i < value; i++) {
                dataList.add(control.getControls().get(i));
            }
            ((ViewHolderColorSlipControl) holder).controlAdapter.setData(dataList);
        } else if (holder instanceof ViewHolderColorDropDownControl) {
            ((ViewHolderColorDropDownControl) holder).num.setText("颜色 " + (position + 1));
            ((ViewHolderColorDropDownControl) holder).controlAdapter.setData(control.getControls());
            float[] hsv = new float[3];
            int selectIndex = control.getControls().get(0).getSelectIndex();
            switch (selectIndex) {
                case 0:
                    int cct = Integer.parseInt(control.getControls().get(0).getControls().get(selectIndex).getControls().get(0).getElements().getItem());
                    int gm = Integer.parseInt(control.getControls().get(0).getControls().get(selectIndex).getControls().get(1).getElements().getItem());
//                            hsv[0] = Integer.;
//                            hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
//                            hsv[2] = 1;
//                            color.setBackgroundColor(Color.HSVToColor(hsv));
                    ((ViewHolderColorDropDownControl) holder).data1.setText("色温: " + cct + "K");
                    ((ViewHolderColorDropDownControl) holder).data2.setText("红绿: " + gm);
                    break;
                case 1:
                    int hsi = Integer.parseInt(control.getControls().get(0).getControls().get(selectIndex).getControls().get(0).getElements().getItem());
                    int sat = Integer.parseInt(control.getControls().get(0).getControls().get(selectIndex).getControls().get(1).getElements().getItem());
                    hsv[0] = hsi;
                    hsv[1] = sat / 100f;
                    hsv[2] = 1;
                    ((ViewHolderColorDropDownControl) holder).color.setBackgroundColor(Color.HSVToColor(hsv));
                    ((ViewHolderColorDropDownControl) holder).data1.setText("色相: " + hsi);
                    ((ViewHolderColorDropDownControl) holder).data2.setText("饱和度: " + sat + "%");
                    break;
                case 2:
                    ((ViewHolderColorDropDownControl) holder).color.setBackgroundColor(ContextCompat.getColor(((ViewHolderColorDropDownControl) holder).color.getContext(), R.color.black));
                    ((ViewHolderColorDropDownControl) holder).data1.setText("黑色");
                    ((ViewHolderColorDropDownControl) holder).data2.setVisibility(View.GONE);
                    break;
            }
        } else if (holder instanceof ViewHolderHsiControl) {
            ((ViewHolderHsiControl) holder).control.setHSI(Integer.parseInt(control.getElements().getHue()));
            ((ViewHolderHsiControl) holder).control.setSAT(Integer.parseInt(control.getElements().getSat()));
            for (DeviceDataMessage.Control control1 : control.getControls()) {
                if (control1.getControlType().equals("slip")) {
                    ((ViewHolderHsiControl) holder).control.setCCT(Integer.parseInt(control1.getElements().getMax()), Integer.parseInt(control1.getElements().getMin()), Integer.parseInt(control1.getElements().getStep()), Integer.parseInt(control1.getElements().getItem()));
                }
            }
        } else if (holder instanceof ViewHolderRgbwControl) {
            ((ViewHolderRgbwControl) holder).control.setR(Integer.parseInt(control.getElements().getR()));
            ((ViewHolderRgbwControl) holder).control.setG(Integer.parseInt(control.getElements().getG()));
            ((ViewHolderRgbwControl) holder).control.setB(Integer.parseInt(control.getElements().getB()));
            ((ViewHolderRgbwControl) holder).control.setW(Integer.parseInt(control.getElements().getW()));
        } else if (holder instanceof ViewHolderXYControl) {
            ((ViewHolderXYControl) holder).control.updateData(Integer.parseInt(control.getElements().getX()), Integer.parseInt(control.getElements().getY()));
        } else if (holder instanceof ViewHolderColorDefaultControl) {
            int sat = Integer.parseInt(control.getElements().getSat());
            ((ViewHolderColorDefaultControl) holder).num.setRemark("颜色数量");
            ((ViewHolderColorDefaultControl) holder).num.setTitle("颜色数量");
            ((ViewHolderColorDefaultControl) holder).num.setDelayBtnVisibility(View.GONE);
            ((ViewHolderColorDefaultControl) holder).num.setDelayTimeVisibility(View.GONE);
            ((ViewHolderColorDefaultControl) holder).num.setSeekBar(Integer.parseInt(control.getElements().getMax()), Integer.parseInt(control.getElements().getMin()), Integer.parseInt(control.getElements().getStep()), Integer.parseInt(control.getElements().getItem()));
            ((ViewHolderColorDefaultControl) holder).sat.setRemark("饱和度");
            ((ViewHolderColorDefaultControl) holder).sat.setTitle("饱和度");
            ((ViewHolderColorDefaultControl) holder).sat.setDelayBtnVisibility(View.GONE);
            ((ViewHolderColorDefaultControl) holder).sat.setDelayTimeVisibility(View.GONE);
            ((ViewHolderColorDefaultControl) holder).sat.setSeekBar(100, 0, 1, sat);
            for (int i = 0; i < ((ViewHolderColorDefaultControl) holder).views.size(); i++) {
                if (i < Integer.parseInt(control.getElements().getItem())) {
                    float[] hsv = new float[3];
                    hsv[0] = ((ViewHolderColorDefaultControl) holder).hsiList.get(i);
                    hsv[1] = sat / 100f;
                    hsv[2] = 1;
                    ((ViewHolderColorDefaultControl) holder).views.get(i).setBackgroundColor(Color.HSVToColor(hsv));
                    ((ViewHolderColorDefaultControl) holder).views.get(i).setVisibility(View.VISIBLE);
                } else {
                    ((ViewHolderColorDefaultControl) holder).views.get(i).setVisibility(View.INVISIBLE);
                }
            }
        } else if (holder instanceof ViewHolderColorManualControl) {
            int sat = Integer.parseInt(control.getElements().getSat());
            ((ViewHolderColorManualControl) holder).num.setRemark("颜色数量");
            ((ViewHolderColorManualControl) holder).num.setTitle("颜色数量");
            ((ViewHolderColorManualControl) holder).num.setDelayBtnVisibility(View.GONE);
            ((ViewHolderColorManualControl) holder).num.setDelayTimeVisibility(View.GONE);
            ((ViewHolderColorManualControl) holder).num.setSeekBar(Integer.parseInt(control.getElements().getMax()), Integer.parseInt(control.getElements().getMin()), Integer.parseInt(control.getElements().getStep()), Integer.parseInt(control.getElements().getItem()));
            ((ViewHolderColorManualControl) holder).sat.setRemark("饱和度");
            ((ViewHolderColorManualControl) holder).sat.setTitle("饱和度");
            ((ViewHolderColorManualControl) holder).sat.setDelayBtnVisibility(View.GONE);
            ((ViewHolderColorManualControl) holder).sat.setDelayTimeVisibility(View.GONE);
            ((ViewHolderColorManualControl) holder).sat.setSeekBar(100, 0, 1, sat);
            if (control.getElements().getHue1() != null) {
                ((ViewHolderColorManualControl) holder).hsiList.set(0, Integer.parseInt(control.getElements().getHue1()));
            }
            if (control.getElements().getHue2() != null) {
                ((ViewHolderColorManualControl) holder).hsiList.set(1, Integer.parseInt(control.getElements().getHue2()));
            }
            if (control.getElements().getHue3() != null) {
                ((ViewHolderColorManualControl) holder).hsiList.set(2, Integer.parseInt(control.getElements().getHue3()));
            }
            if (control.getElements().getHue4() != null) {
                ((ViewHolderColorManualControl) holder).hsiList.set(3, Integer.parseInt(control.getElements().getHue4()));
            }
            if (control.getElements().getHue5() != null) {
                ((ViewHolderColorManualControl) holder).hsiList.set(4, Integer.parseInt(control.getElements().getHue5()));
            }
            if (control.getElements().getHue6() != null) {
                ((ViewHolderColorManualControl) holder).hsiList.set(5, Integer.parseInt(control.getElements().getHue6()));
            }
            for (int i = 0; i < 6; i++) {
                if (i < Integer.parseInt(control.getElements().getItem())) {
                    ((ViewHolderColorManualControl) holder).views.get(i).setVisibility(View.VISIBLE);
                } else {
                    ((ViewHolderColorManualControl) holder).views.get(i).setVisibility(View.GONE);
                }
                ((ViewHolderColorManualControl) holder).views.get(i).setRemark("色相");
                ((ViewHolderColorManualControl) holder).views.get(i).setTitle("色相 " + (i + 1));
                ((ViewHolderColorManualControl) holder).views.get(i).setDelayBtnVisibility(View.GONE);
                ((ViewHolderColorManualControl) holder).views.get(i).setDelayTimeVisibility(View.GONE);
                float[] hsv = new float[3];
                hsv[0] = ((ViewHolderColorManualControl) holder).hsiList.get(i);
                hsv[1] = sat / 100f;
                hsv[2] = 1;
                ((ViewHolderColorManualControl) holder).views.get(i).setColor(Color.HSVToColor(hsv));
                ((ViewHolderColorManualControl) holder).views.get(i).setSeekBar(360, 0, 1, (int) hsv[0]);
            }
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
            case "doubleSlip":
                return TYPE_DOUBLE_SLIP;
            case "touchBtn":
                return TYPE_TOUCH_BTN;
            case "colorSlip":
                return TYPE_COLOR_SLIP;
            case "colorDropDown":
                return TYPE_COLOR_DROP_DOWN;
            case "hsi":
                return TYPE_HSI;
            case "rgbw":
                return TYPE_RGBW;
            case "xy":
                return TYPE_XY;
            case "colorDefault":
                return TYPE_COLOR_DEFAULT;
            case "colorManual":
                return TYPE_COLOR_MANUAL;
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
                public void onDataChanging(int index) {

                }

                @Override
                public void onDataChanged(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    if (controlData.getRemark().equals("亮度")) {
                        setDim(index);
                    }
                    controlData.getElements().setItem(String.valueOf(index));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });
        }
    }

    class ViewHolderDoubleSlipControl extends RecyclerView.ViewHolder {


        DoubleSlipView control;

        public ViewHolderDoubleSlipControl(@NonNull VpItemDoubleSlipControlBinding binding) {
            super(binding.getRoot());
            control = binding.control;
            control.setOnDelayTimeClickListener(new DoubleSlipView.OnDelayTimeClickListener() {
                @Override
                public void onDelayTimeClick(View view) {
                    if (onDelayTimeClickListener != null) {
                        onDelayTimeClickListener.onDelayTimeClick(getAdapterPosition(), (String) control.getDelayTime());
                    }
                }
            });
            control.setOnDataClickListener(new DoubleSlipView.OnDataClickListener() {
                @Override
                public void onDataClick(View view) {
                    if (onDataClickListener != null) {
//                        onDataClickListener.onDataClick(getAdapterPosition(), (String) control.getData());
                    }
                }
            });
            control.setOnDataChangeListener(new DoubleSlipView.OnDataChangeListener() {

                @Override
                public void onDataChanging(int maxItem, int minItem) {

                }

                @Override
                public void onDataChanged(int maxItem, int minItem) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    if (controlData.getRemark().equals("亮度范围")) {
                        setDim(maxItem);
                    }
                    controlData.getElements().setMaxItem(String.valueOf(maxItem));
                    controlData.getElements().setMinItem(String.valueOf(minItem));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });
        }
    }

    class ViewHolderColorSlipControl extends RecyclerView.ViewHolder {


        SlipView control;

        ControlAdapter controlAdapter;

        public ViewHolderColorSlipControl(@NonNull VpItemColorslipControlBinding binding) {
            super(binding.getRoot());
            control = binding.control;
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.recyclerView.getContext()));
            controlAdapter = new ControlAdapter();
            controlAdapter.setDim(dim);
            controlAdapter.setDelayTime(delayTime);
            binding.recyclerView.setAdapter(controlAdapter);
            binding.control.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
                @Override
                public void onDataChanging(int index) {

                }

                @Override
                public void onDataChanged(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setItem(String.valueOf(index));
                    int num = Integer.parseInt(controlData.getElements().getMax()) - controlData.getControls().size();
                    if (num > 0) {
                        for (int i = 0; i < num; i++) {
                            controlData.getControls().add(controls.get(getAdapterPosition()).getControls().get(0));
                        }
                    }
                    ArrayList<DeviceDataMessage.Control> dataList = new ArrayList<>();
                    for (int i = 0; i < index; i++) {
                        dataList.add(controlData.getControls().get(i));
                    }
                    controlAdapter.setData(dataList);
                    controlAdapter.setOnDataUpdateListener(new OnDataUpdateListener() {
                        @Override
                        public void onDataUpdate(int position, DeviceDataMessage.Control control) {
                            controlData.getControls().set(position, control);
                            if (onDataUpdateListener != null) {
                                onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                            }
                        }
                    });
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
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
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    if (index >= 0) {
                        controlData.setSelectIndex(index);
                        controlAdapter.setData(controlData.getControls().get(binding.control.getCheck()).getControls());
                        if (onDataUpdateListener != null) {
                            onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                        }
                    }
                }
            });
            controlAdapter.setOnDataUpdateListener(new OnDataUpdateListener() {
                @Override
                public void onDataUpdate(int position, DeviceDataMessage.Control control) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getControls().get(binding.control.getCheck()).getControls().set(position, control);
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
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
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.setSelectIndex(index);
                    controlAdapter.setData(controlData.getControls().get(binding.control.getCheck()).getControls());
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });
            controlAdapter.setOnDataUpdateListener(new OnDataUpdateListener() {
                @Override
                public void onDataUpdate(int position, DeviceDataMessage.Control control) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getControls().get(binding.control.getCheck()).getControls().set(position, control);
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });
        }
    }

    class ViewHolderTouchBtnControl extends RecyclerView.ViewHolder {


        Button control;

        public ViewHolderTouchBtnControl(@NonNull VpItemTouchbtnControlBinding binding) {
            super(binding.getRoot());
            control = binding.control;
        }
    }

    class ViewHolderHsiControl extends RecyclerView.ViewHolder {


        HsiView control;

        public ViewHolderHsiControl(@NonNull VpItemHsiControlBinding binding) {
            super(binding.getRoot());
            control = binding.control;
            control.setOnDataChangeListener(new HsiView.OnDataChangeListener() {
                @Override
                public void onDataChanged(int HSI, int SAT) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setHue(String.valueOf(HSI));
                    controlData.getElements().setSat(String.valueOf(SAT));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });
        }
    }

    class ViewHolderRgbwControl extends RecyclerView.ViewHolder {


        RgbwView control;

        public ViewHolderRgbwControl(@NonNull VpItemRgbwControlBinding binding) {
            super(binding.getRoot());
            control = binding.control;
            control.setOnDataChangeListener(new RgbwView.OnDataChangeListener() {
                @Override
                public void onDataChanged(int R, int G, int B, int W) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setR(String.valueOf(R));
                    controlData.getElements().setG(String.valueOf(G));
                    controlData.getElements().setB(String.valueOf(B));
                    controlData.getElements().setW(String.valueOf(W));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }

                @Override
                public void onRDataChanged(int R) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setR(String.valueOf(R));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }

                @Override
                public void onGDataChanged(int G) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setG(String.valueOf(G));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }

                @Override
                public void onBDataChanged(int B) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setB(String.valueOf(B));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }

                @Override
                public void onWDataChanged(int W) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setW(String.valueOf(W));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });
        }
    }

    class ViewHolderXYControl extends RecyclerView.ViewHolder {


        XYView control;

        public ViewHolderXYControl(@NonNull VpItemXyControlBinding binding) {
            super(binding.getRoot());
            control = binding.control;
            control.setOnDataChangeListener(new XYView.OnDataChangeListener() {
                @Override
                public void onDataChanged(int x, int y) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setX(String.valueOf(x));
                    controlData.getElements().setY(String.valueOf(y));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
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

    class ViewHolderColorDropDownControl extends RecyclerView.ViewHolder {


        ImageView color, show;
        TextView num, data1, data2;

        ControlAdapter controlAdapter;

        public ViewHolderColorDropDownControl(@NonNull VpItemColorDropDownControlBinding binding) {
            super(binding.getRoot());
            color = binding.color;
            show = binding.show;
            num = binding.num;
            data1 = binding.data1;
            data2 = binding.data2;

            binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.recyclerView.getContext()));
            controlAdapter = new ControlAdapter();
            controlAdapter.setDim(dim);
            controlAdapter.setDelayTime(delayTime);
            binding.recyclerView.setAdapter(controlAdapter);
            controlAdapter.setOnDataUpdateListener(new OnDataUpdateListener() {
                @Override
                public void onDataUpdate(int position, DeviceDataMessage.Control control) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getControls().set(position, control);
                    float[] hsv = new float[3];
                    int selectIndex = controlData.getControls().get(position).getSelectIndex();
                    switch (selectIndex) {
                        case 0:
                            int cct = Integer.parseInt(controlData.getControls().get(position).getControls().get(selectIndex).getControls().get(0).getElements().getItem());
                            int gm = Integer.parseInt(controlData.getControls().get(position).getControls().get(selectIndex).getControls().get(1).getElements().getItem());
//                            hsv[0] = Integer.;
//                            hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
//                            hsv[2] = 1;
//                            color.setBackgroundColor(Color.HSVToColor(hsv));
                            data1.setText("色温: " + cct + "K");
                            data2.setText("红绿: " + gm);
                            break;
                        case 1:
                            int hsi = Integer.parseInt(controlData.getControls().get(position).getControls().get(selectIndex).getControls().get(0).getElements().getItem());
                            int sat = Integer.parseInt(controlData.getControls().get(position).getControls().get(selectIndex).getControls().get(1).getElements().getItem());
                            hsv[0] = hsi;
                            hsv[1] = sat / 100f;
                            hsv[2] = 1;
                            color.setBackgroundColor(Color.HSVToColor(hsv));
                            data1.setText("色相: " + hsi);
                            data2.setText("饱和度: " + sat + "%");
                            break;
                        case 2:
                            color.setBackgroundColor(ContextCompat.getColor(color.getContext(), R.color.black));
                            data1.setText("黑色");
                            break;
                    }
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });
            binding.recyclerView.setVisibility(View.GONE);
            binding.show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int visibility = binding.recyclerView.getVisibility();
                    switch (visibility) {
                        case View.GONE:
                            binding.recyclerView.setVisibility(View.VISIBLE);
                            binding.show.setImageResource(R.drawable.ic_spread);
                            break;
                        case View.VISIBLE:
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.show.setImageResource(R.drawable.ic_close);
                            break;
                    }
                }
            });
        }
    }


    class ViewHolderColorDefaultControl extends RecyclerView.ViewHolder {


        SlipView num, sat;
        ArrayList<View> views;

        ArrayList<Integer> hsiList;

        public ViewHolderColorDefaultControl(@NonNull VpItemColordefaultControlBinding binding) {
            super(binding.getRoot());
            num = binding.number;
            sat = binding.SATSlip;
            num.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
                @Override
                public void onDataChanging(int index) {
                }

                @Override
                public void onDataChanged(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setItem(String.valueOf(index));
                    for (int i = 0; i < views.size(); i++) {
                        if (i < index) {
                            float[] hsv = new float[3];
                            hsv[0] = hsiList.get(i);
                            hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
                            hsv[2] = 1;
                            views.get(i).setBackgroundColor(Color.HSVToColor(hsv));
                            views.get(i).setVisibility(View.VISIBLE);
                        } else {
                            views.get(i).setVisibility(View.INVISIBLE);
                        }
                    }
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });
            sat.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
                @Override
                public void onDataChanging(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setSat(String.valueOf(index));
                    for (int i = 0; i < views.size(); i++) {
                        float[] hsv = new float[3];
                        hsv[0] = hsiList.get(i);
                        hsv[1] = index / 100f;
                        hsv[2] = 1;
                        views.get(i).setBackgroundColor(Color.HSVToColor(hsv));
                    }
                }

                @Override
                public void onDataChanged(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setSat(String.valueOf(index));
                    for (int i = 0; i < views.size(); i++) {
                        float[] hsv = new float[3];
                        hsv[0] = hsiList.get(i);
                        hsv[1] = index / 100f;
                        hsv[2] = 1;
                        views.get(i).setBackgroundColor(Color.HSVToColor(hsv));
                    }
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });
            views = new ArrayList<>();
            views.add(binding.color1);
            views.add(binding.color2);
            views.add(binding.color3);
            views.add(binding.color4);
            views.add(binding.color5);
            views.add(binding.color6);
            views.add(binding.color7);
            views.add(binding.color8);
            views.add(binding.color9);
            views.add(binding.color10);
            views.add(binding.color11);
            views.add(binding.color12);
            views.add(binding.color13);
            views.add(binding.color14);
            views.add(binding.color15);
            views.add(binding.color16);
            views.add(binding.color17);
            views.add(binding.color18);
            views.add(binding.color19);
            views.add(binding.color20);
            views.add(binding.color21);
            views.add(binding.color22);
            views.add(binding.color23);
            views.add(binding.color24);
            hsiList = new ArrayList<>();
            hsiList.add(0);
            hsiList.add(120);
            hsiList.add(240);
            hsiList.add(60);
            hsiList.add(180);
            hsiList.add(300);
            hsiList.add(30);
            hsiList.add(150);
            hsiList.add(270);
            hsiList.add(90);
            hsiList.add(210);
            hsiList.add(330);
            hsiList.add(15);
            hsiList.add(135);
            hsiList.add(255);
            hsiList.add(75);
            hsiList.add(195);
            hsiList.add(315);
            hsiList.add(45);
            hsiList.add(165);
            hsiList.add(285);
            hsiList.add(105);
            hsiList.add(225);
            hsiList.add(350);
        }
    }

    class ViewHolderColorManualControl extends RecyclerView.ViewHolder {


        SlipView num, sat;
        ArrayList<SlipView> views;
        ArrayList<Integer> hsiList;


        public ViewHolderColorManualControl(@NonNull VpItemColormanualControlBinding binding) {
            super(binding.getRoot());
            num = binding.number;
            sat = binding.SATSlip;
            num.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
                @Override
                public void onDataChanging(int index) {

                }

                @Override
                public void onDataChanged(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setItem(String.valueOf(index));
                    for (int i = 0; i < views.size(); i++) {
                        if (i < index) {
                            views.get(i).setVisibility(View.VISIBLE);
                        } else {
                            views.get(i).setVisibility(View.GONE);
                        }
                    }
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });
            sat.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
                @Override
                public void onDataChanging(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setSat(String.valueOf(index));
                    for (int i = 0; i < views.size(); i++) {
                        float[] hsv = new float[3];
                        hsv[0] = hsiList.get(i);
                        hsv[1] = index / 100f;
                        hsv[2] = 1;
                        views.get(i).setColor(Color.HSVToColor(hsv));
                    }
                }

                @Override
                public void onDataChanged(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setSat(String.valueOf(index));
                    for (int i = 0; i < views.size(); i++) {
                        float[] hsv = new float[3];
                        hsv[0] = hsiList.get(i);
                        hsv[1] = index / 100f;
                        hsv[2] = 1;
                        views.get(i).setColor(Color.HSVToColor(hsv));
                    }
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });
            views = new ArrayList<>();
            views.add(binding.HSI1Slip);
            views.add(binding.HSI2Slip);
            views.add(binding.HSI3Slip);
            views.add(binding.HSI4Slip);
            views.add(binding.HSI5Slip);
            views.add(binding.HSI6Slip);
            hsiList = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                hsiList.add(i, 30 * i);
            }


            binding.HSI1Slip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
                @Override
                public void onDataChanging(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setHue1(String.valueOf(index));
                    hsiList.set(0, index);
                    float[] hsv = new float[3];
                    hsv[0] = index;
                    hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
                    hsv[2] = 1;
                    views.get(0).setColor(Color.HSVToColor(hsv));
                }

                @Override
                public void onDataChanged(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setHue1(String.valueOf(index));
                    hsiList.set(0, index);
                    float[] hsv = new float[3];
                    hsv[0] = index;
                    hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
                    hsv[2] = 1;
                    views.get(0).setColor(Color.HSVToColor(hsv));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });


            binding.HSI2Slip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
                @Override
                public void onDataChanging(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setHue2(String.valueOf(index));
                    hsiList.set(1, index);
                    float[] hsv = new float[3];
                    hsv[0] = index;
                    hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
                    hsv[2] = 1;
                    views.get(1).setColor(Color.HSVToColor(hsv));
                }

                @Override
                public void onDataChanged(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setHue2(String.valueOf(index));
                    hsiList.set(1, index);
                    float[] hsv = new float[3];
                    hsv[0] = index;
                    hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
                    hsv[2] = 1;
                    views.get(1).setColor(Color.HSVToColor(hsv));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });

            binding.HSI3Slip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
                @Override
                public void onDataChanging(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setHue3(String.valueOf(index));
                    hsiList.set(2, index);
                    float[] hsv = new float[3];
                    hsv[0] = index;
                    hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
                    hsv[2] = 1;
                    views.get(2).setColor(Color.HSVToColor(hsv));
                }

                @Override
                public void onDataChanged(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setHue3(String.valueOf(index));
                    hsiList.set(2, index);
                    float[] hsv = new float[3];
                    hsv[0] = index;
                    hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
                    hsv[2] = 1;
                    views.get(2).setColor(Color.HSVToColor(hsv));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });

            binding.HSI4Slip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
                @Override
                public void onDataChanging(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setHue4(String.valueOf(index));
                    hsiList.set(3, index);
                    float[] hsv = new float[3];
                    hsv[0] = index;
                    hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
                    hsv[2] = 1;
                    views.get(3).setColor(Color.HSVToColor(hsv));
                }

                @Override
                public void onDataChanged(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setHue4(String.valueOf(index));
                    hsiList.set(3, index);
                    float[] hsv = new float[3];
                    hsv[0] = index;
                    hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
                    hsv[2] = 1;
                    views.get(3).setColor(Color.HSVToColor(hsv));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });

            binding.HSI5Slip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
                @Override
                public void onDataChanging(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setHue5(String.valueOf(index));
                    hsiList.set(4, index);
                    float[] hsv = new float[3];
                    hsv[0] = index;
                    hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
                    hsv[2] = 1;
                    views.get(4).setColor(Color.HSVToColor(hsv));
                }

                @Override
                public void onDataChanged(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setHue5(String.valueOf(index));
                    hsiList.set(4, index);
                    float[] hsv = new float[3];
                    hsv[0] = index;
                    hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
                    hsv[2] = 1;
                    views.get(4).setColor(Color.HSVToColor(hsv));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });

            binding.HSI6Slip.setOnDataChangeListener(new SlipView.OnDataChangeListener() {
                @Override
                public void onDataChanging(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setHue6(String.valueOf(index));
                    hsiList.set(5, index);
                    float[] hsv = new float[3];
                    hsv[0] = index;
                    hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
                    hsv[2] = 1;
                    views.get(5).setColor(Color.HSVToColor(hsv));
                }

                @Override
                public void onDataChanged(int index) {
                    DeviceDataMessage.Control controlData = controls.get(getAdapterPosition());
                    controlData.getElements().setHue6(String.valueOf(index));
                    hsiList.set(5, index);
                    float[] hsv = new float[3];
                    hsv[0] = index;
                    hsv[1] = Integer.parseInt(controlData.getElements().getSat()) / 100f;
                    hsv[2] = 1;
                    views.get(5).setColor(Color.HSVToColor(hsv));
                    if (onDataUpdateListener != null) {
                        onDataUpdateListener.onDataUpdate(getAdapterPosition(), controlData);
                    }
                }
            });
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
        void onDataUpdate(int position, DeviceDataMessage.Control control);
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

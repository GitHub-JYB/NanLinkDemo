package com.example.nanlinkdemo.mvp.adapter;


import android.content.Context;
import android.opengl.Visibility;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.databinding.VpItemAdd24gBinding;
import com.example.nanlinkdemo.databinding.VpItemAdd24gBtnBinding;
import com.example.nanlinkdemo.databinding.VpItemAdd24gTypeBinding;
import com.example.nanlinkdemo.ui.BoxView;

import java.util.ArrayList;

public class Add24GAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String title_type, title_cctRange, title_GM = "";
    private int checkIndex_type, checkIndex_cctRange, checkIndex_GM = 0;
    private ArrayList<String> dataList_type, dataList_cctRange, dataList_GM = new ArrayList<String>();
    private BoxView.OnCheckedChangeListener listener_type, listener_cctRange, listener_GM;
    private int visibility_type = View.VISIBLE;
    private int visibility_cctRange, visibility_GM = View.GONE;


    private ArrayList<Add24GFixture> fixtureArrayList;
    private int Type_type_boxView = 0;

    private int Type_Item = 1;
    private int Type_Btn = 2;

    private OnOutRangeListener outRangeListener;
    private OnCheckCompleteListener checkCompleteListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Type_type_boxView) {
            VpItemAdd24gTypeBinding vpItemAdd24gTypeBinding = VpItemAdd24gTypeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolderAdd24gType(vpItemAdd24gTypeBinding);
        } else if (viewType == Type_Item) {
            VpItemAdd24gBinding vpItemAdd24gBinding = VpItemAdd24gBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolderAdd24g(vpItemAdd24gBinding);
        }
        VpItemAdd24gBtnBinding vpItemAdd24gBtnBinding = VpItemAdd24gBtnBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderAddBtn(vpItemAdd24gBtnBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderAdd24g) {
            ((ViewHolderAdd24g) holder).name.setText(fixtureArrayList.get(position - 1).getName());
            ((ViewHolderAdd24g) holder).CH.setText(fixtureArrayList.get(position - 1).getCH());
            ((ViewHolderAdd24g) holder).index.setText("#" + (position));
            ((ViewHolderAdd24g) holder).index.setFocusable(true);
            ((ViewHolderAdd24g) holder).index.setFocusableInTouchMode(true);
            ((ViewHolderAdd24g) holder).index.requestFocus();
            ((ViewHolderAdd24g) holder).index.findFocus();
            if (position == 1) {
                ((ViewHolderAdd24g) holder).delete.setVisibility(View.GONE);
            } else {
                ((ViewHolderAdd24g) holder).delete.setVisibility(View.VISIBLE);
            }
        } else if (holder instanceof ViewHolderAdd24gType) {
            ((ViewHolderAdd24gType) holder).type.setTitle(title_type);
            ((ViewHolderAdd24gType) holder).type.check(checkIndex_type);
            ((ViewHolderAdd24gType) holder).type.setVisibility(visibility_type);
            if (listener_type != null) {
                ((ViewHolderAdd24gType) holder).type.setOnCheckedChangeListener(listener_type);
            }
            ((ViewHolderAdd24gType) holder).type.setData(dataList_type);
            ((ViewHolderAdd24gType) holder).cctRange.setTitle(title_cctRange);
            ((ViewHolderAdd24gType) holder).cctRange.check(checkIndex_cctRange);
            ((ViewHolderAdd24gType) holder).cctRange.setVisibility(visibility_cctRange);
            if (listener_cctRange != null) {
                ((ViewHolderAdd24gType) holder).cctRange.setOnCheckedChangeListener(listener_cctRange);
            }
            ((ViewHolderAdd24gType) holder).cctRange.setData(dataList_cctRange);
            ((ViewHolderAdd24gType) holder).GM.setTitle(title_GM);
            ((ViewHolderAdd24gType) holder).GM.check(checkIndex_GM);
            ((ViewHolderAdd24gType) holder).GM.setVisibility(visibility_type);
            if (listener_GM != null) {
                ((ViewHolderAdd24gType) holder).GM.setOnCheckedChangeListener(listener_GM);
            }
            ((ViewHolderAdd24gType) holder).GM.setData(dataList_GM);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? Type_type_boxView : fixtureArrayList != null && position < fixtureArrayList.size() + 1 ? Type_Item : Type_Btn;
    }

    @Override
    public int getItemCount() {
        return fixtureArrayList == null ? 2 : fixtureArrayList.size() + 2;
    }

    public void setData(ArrayList<Add24GFixture> fixtureArrayList) {
        this.fixtureArrayList = fixtureArrayList;
        notifyDataSetChanged();
    }


    public void updateBoxView(Integer boxViewId, String title, ArrayList<String> dataList, int checkIndex) {
        switch (boxViewId) {
            case R.id.type:
                title_type = title;
                dataList_type = dataList;
                checkIndex_type = checkIndex;
                break;
            case R.id.cctRange:
                title_cctRange = title;
                dataList_cctRange = dataList;
                checkIndex_cctRange = checkIndex;
                break;
            case R.id.GM:
                title_GM = title;
                dataList_GM = dataList;
                checkIndex_GM = checkIndex;
                break;
        }
    }

    public void setBoxViewOnCheckedChangeListener(Integer boxViewId, BoxView.OnCheckedChangeListener listener) {
        switch (boxViewId) {
            case R.id.type:
                listener_type = listener;
                break;
            case R.id.cctRange:
                listener_cctRange = listener;
                break;
            case R.id.GM:
                listener_GM = listener;
                break;
        }
    }


    public void setBoxViewVisibility(Integer boxViewId, int visibility) {
        switch (boxViewId) {
            case R.id.type:
                visibility_type = visibility;
                break;
            case R.id.cctRange:
                visibility_cctRange = visibility;
                break;
            case R.id.GM:
                visibility_GM = visibility;
                break;
        }
    }

    class ViewHolderAdd24gType extends RecyclerView.ViewHolder {
        BoxView type, cctRange, GM;

        public ViewHolderAdd24gType(@NonNull VpItemAdd24gTypeBinding binding) {
            super(binding.getRoot());
            type = binding.type;
            cctRange = binding.cctRange;
            GM = binding.GM;
            cctRange.setWidth(MyApplication.dip2px(68));
            cctRange.setHeight(MyApplication.dip2px(50));



        }
    }

    class ViewHolderAddBtn extends RecyclerView.ViewHolder {


        public ViewHolderAddBtn(@NonNull VpItemAdd24gBtnBinding binding) {
            super(binding.getRoot());

            binding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeSoftInput(v);
                    fixtureArrayList.add(new Add24GFixture("", ""));
                    notifyDataSetChanged();
                }
            });

        }
    }

    // 关闭键盘
    private void closeSoftInput(View v) {
        InputMethodManager imm = (InputMethodManager) v
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(
                    v.getApplicationWindowToken(), 0);
        }
    }

    class ViewHolderAdd24g extends RecyclerView.ViewHolder {


        TextView index;
        EditText CH, name;

        ImageView delete;

        public ViewHolderAdd24g(@NonNull VpItemAdd24gBinding binding) {
            super(binding.getRoot());
            name = binding.name;
            CH = binding.CH;
            index = binding.index;
            delete = binding.delete;


            binding.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeSoftInput(v);
                    fixtureArrayList.remove(fixtureArrayList.get(getAdapterPosition() - 1));
                    notifyDataSetChanged();
                }
            });


            binding.CH.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String CH = s.toString().trim();

                    if (CH.length() > 0) {
                        if (Integer.parseInt(CH) <= 0 || Integer.parseInt(CH) > 512) {
                            if (outRangeListener != null) {
                                outRangeListener.onOutRange();
                                binding.CH.setText("");
                            }
                        }
                    }

                    Add24GFixture fixture = fixtureArrayList.get(getAdapterPosition() - 1);
                    fixture.setCH(CH);
                    fixtureArrayList.set(getAdapterPosition() - 1 , fixture);
                    CheckComplete();
                }

            });

            binding.name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String name = s.toString().trim();
                    Add24GFixture fixture = fixtureArrayList.get(getAdapterPosition() - 1);
                    fixture.setName(name);
                    fixtureArrayList.set(getAdapterPosition() - 1, fixture);
                    CheckComplete();
                }
            });

        }


    }

    private void CheckComplete() {
        if (checkCompleteListener != null) {
            if (!fixtureArrayList.isEmpty()) {
                for (Add24GFixture fixture : fixtureArrayList) {
                    if (fixture.getCH().isEmpty() || fixture.getName().isEmpty()) {
                        checkCompleteListener.CheckComplete(false);
                        return;
                    }
                }
                checkCompleteListener.CheckComplete(true);
            }
        }
    }

    public interface OnOutRangeListener {
        void onOutRange();
    }

    public void setOnOutRangeListener(OnOutRangeListener outRangeListener) {
        this.outRangeListener = outRangeListener;
    }


    public interface OnCheckCompleteListener {
        void CheckComplete(boolean complete);
    }

    public void setOnCheckCompleteListener(OnCheckCompleteListener checkCompleteListener) {
        this.checkCompleteListener = checkCompleteListener;
    }
}

package com.example.nanlinkdemo.ui;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.DialogSettingBinding;
import com.example.nanlinkdemo.mvp.adapter.ThreePointAdapter;

import java.util.ArrayList;

public class SettingDialog extends DialogFragment {

    DialogSettingBinding binding;
    static Boolean isCanceledOnTouchOutside = false;

    private ThreePointAdapter adapter;
    private ArrayList<String> settingList;
    private ThreePointAdapter.OnClickListener listener;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSettingBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        此方法在视图View已经创建后返回的，但是这个view 还没有添加到父级中。
        我们在这里可以重新设定view的各个数据，但是不能修改对话框最外层的ViewGroup的布局参数。
        因为这里的view还没添加到父级中，我们需要在下面onStart生命周期里修改对话框尺寸参数
         */
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 设置分割线格式并添加
        UnlessLastItemDecoration decoration = new UnlessLastItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.decoration_setting_dialog));
        binding.recycleView.addItemDecoration(decoration);

        adapter = new ThreePointAdapter();
        adapter.setData(settingList);
        adapter.setOnClickListener(listener);
        binding.recycleView.setAdapter(adapter);






    }

    @Override
    public void onStart() {
        /*
            因为View在添加后,对话框最外层的ViewGroup并不知道我们导入的View所需要的的宽度。 所以我们需要在onStart生命周期里修改对话框尺寸参数
         */
//        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
//        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        // 设置背景透明，圆角才能出来
        getDialog().getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));

        //getDialog().setCancelable(false);//这个会屏蔽掉返回键
        // 外部点击不会取消
        if (!isCanceledOnTouchOutside){
            getDialog().setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        }
        super.onStart();
    }

    // 设置标题
    public void setData(ArrayList<String> settingList){
         this.settingList = settingList;
    }

    // 设置标题
    public void setOnClickListener(ThreePointAdapter.OnClickListener listener){
        this.listener = listener;
    }

}

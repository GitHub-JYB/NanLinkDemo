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

import com.example.nanlinkdemo.databinding.ReadDialogBinding;

public class readDialog extends DialogFragment {

    ReadDialogBinding binding;
    static Boolean isCanceledOnTouchOutside = true;
    public static int Type_Read = 0;
    public static int Type_Write = 1;


    public static readDialog newInstance(String title, int type, boolean isCancel){
        readDialog myReadDialog = new readDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("type", type);
        myReadDialog.setArguments(bundle);
        isCanceledOnTouchOutside = isCancel;
        return myReadDialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ReadDialogBinding.inflate(getLayoutInflater());
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
        Bundle arguments = this.getArguments();
        binding.readDialogTitle.setText(arguments.getString("title"));
        if (arguments.getInt("type") == Type_Read){
            binding.readDialogContent.setVisibility(View.VISIBLE);
            binding.readDialogInputText.setVisibility(View.GONE);
            binding.readDialogRetry.setVisibility(View.VISIBLE);
            binding.readDialogConfirm.setVisibility(View.GONE);
            binding.readDialogCancel.setVisibility(View.GONE);
        }else {
            binding.readDialogContent.setVisibility(View.GONE);
            binding.readDialogInputText.setVisibility(View.VISIBLE);
            binding.readDialogRetry.setVisibility(View.GONE);
            binding.readDialogConfirm.setVisibility(View.VISIBLE);
            binding.readDialogCancel.setVisibility(View.VISIBLE);
        }

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


    public void setTitle(CharSequence title){
        binding.readDialogTitle.setText(title);
    }

    public void setContent(CharSequence content){
        binding.readDialogContent.setText(content);
        binding.readDialogContent.setVisibility(View.VISIBLE);
    }


}

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

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.DialogMyBinding;

public class MyDialog extends DialogFragment {

    DialogMyBinding binding;
    static Boolean isCanceledOnTouchOutside = true;

    private CharSequence title, message, neutralText, positiveText, negativeText, hintInputText;
    private int type = 0;
    private int titleTextColorResId = R.color.blue;
    private int neutralTextColorResId = R.color.blue;
    private int positiveTextColorResId = R.color.blue;
    private int negativeTextColorResId = R.color.white;
    private NegativeOnClickListener negativeListener;
    private PositiveOnClickListener positiveListener;
    private NeutralOnClickListener neutralListener;

    public MyDialog(){

    }

    public MyDialog(int type, String title, String message){
        this.type = type;
        this.title = title;
        this.message = message;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogMyBinding.inflate(getLayoutInflater());
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
        switch (type){
            case 1:
                binding.myDialogMessage.setVisibility(View.GONE);
                binding.myDialogNeutralButton.setVisibility(View.GONE);
                binding.myDialogInput.setVisibility(View.VISIBLE);
                binding.myDialogNegativeButton.setVisibility(View.VISIBLE);
                binding.decorationDialogPositiveNegative.setVisibility(View.VISIBLE);
                binding.myDialogPositiveButton.setVisibility(View.VISIBLE);
                setCanceledOnTouchOutside(false);
                break;
            default:
                binding.myDialogMessage.setVisibility(View.VISIBLE);
                binding.myDialogNeutralButton.setVisibility(View.VISIBLE);
                binding.myDialogInput.setVisibility(View.GONE);
                binding.myDialogNegativeButton.setVisibility(View.GONE);
                binding.decorationDialogPositiveNegative.setVisibility(View.GONE);
                binding.myDialogPositiveButton.setVisibility(View.GONE);
                break;

        }


        binding.myDialogTitle.setText(title);
        binding.myDialogTitle.setTextColor(getResources().getColor(titleTextColorResId));

        binding.myDialogMessage.setText(message);

        binding.myDialogInputText.setHint(hintInputText);

        binding.myDialogNeutralButton.setOnClickListener(neutralListener);
        binding.myDialogNeutralButton.setText(neutralText);
        binding.myDialogNeutralButton.setTextColor(getResources().getColor(neutralTextColorResId));


        binding.myDialogNegativeButton.setOnClickListener(negativeListener);
        binding.myDialogNegativeButton.setText(negativeText);
        binding.myDialogNegativeButton.setTextColor(getResources().getColor(negativeTextColorResId));


        binding.myDialogPositiveButton.setOnClickListener(positiveListener);
        binding.myDialogPositiveButton.setText(positiveText);
        binding.myDialogPositiveButton.setTextColor(getResources().getColor(positiveTextColorResId));


        binding.myDialogNeutralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.myDialogNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.myDialogInputTextClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.myDialogInputText.setText("");
            }
        });




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
    public void setTitle(CharSequence title){
         this.title = title;
    }

    // 设置标题
    public void setTitleTextColorResId(int titleTextColorResId){
        this.titleTextColorResId = titleTextColorResId;
    }

    // 设置提示信息
    public void setMessage(CharSequence message){
        this.message = message;
    }


    public void setHintInputText(CharSequence hintInputText){
        this.hintInputText = hintInputText;
    }



    // 消极按键的点击接口
    public interface NegativeOnClickListener extends View.OnClickListener {
        void onClick(View view);
    }

    // 设置消极按键的点击事件
    public void setNegativeOnClickListener(NegativeOnClickListener li){
        this.negativeListener = li;
    }

    // 设置消极按键的文字
    public void setNegativeText(CharSequence negativeText) {
        this.negativeText = negativeText;
    }

    // 设置消极按键的文字颜色
    public void setNegativeTextColorResId(int negativeTextColorResId) {
        this.negativeTextColorResId = negativeTextColorResId;
    }

    // 积极按键的点击接口
    public interface PositiveOnClickListener extends View.OnClickListener {
        void onClick(View view);
    }

    // 设置积极按键的点击事件
    public void setPositiveOnClickListener(PositiveOnClickListener li){
        this.positiveListener = li;
    }

    // 设置积极按键的文字
    public void setPositiveText(CharSequence positiveText) {
        this.positiveText = positiveText;
    }

    // 设置积极按键的文字颜色
    public void setPositiveTextColorResId(int positiveTextColorResId) {
        this.positiveTextColorResId = positiveTextColorResId;
    }


    // 普通按键的点击接口
    public interface NeutralOnClickListener extends View.OnClickListener {
        void onClick(View view);
    }

    // 普通按键的点击事件
    public void setNeutralOnClickListener(NeutralOnClickListener li){
        this.neutralListener = li;
    }

    // 设置普通按键的文字
    public void setNeutralText(CharSequence neutralText) {
        this.neutralText = neutralText;
    }

    // 设置普通按键的文字颜色
    public void setNeutralTextColorResId(int neutralTextColorResId) {
        this.neutralTextColorResId = neutralTextColorResId;
    }

    // 设置点击外边是否可以取消
    public void setCanceledOnTouchOutside(Boolean isCanceledOnTouchOutside) {
        this.isCanceledOnTouchOutside = isCanceledOnTouchOutside;
    }

    // 设置dialog的类型
    public void setType(int type){
        this.type = type;
    }

    // 获取输入框文本
    public String getInputText(){
        return binding.myDialogInputText.getText().toString().trim();
    }
}

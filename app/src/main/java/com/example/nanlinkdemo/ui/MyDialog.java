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

    public static final int Read_OneBtn_NormalTitle_WhiteOneBtn = 1;
    public static final int Read_OneBtn_NormalTitle_BlueOneBtn = 2;
    public static final int Read_OneBtn_WarningTitle_BlueOneBtn = 3;
    public static final int Read_OneBtn_WarningTitle_WarningOneBtn = 4;
    public static final int Read_TwoBtn_NormalTitle_WhiteTwoBtn = 5;
    public static final int Read_TwoBtn_NormalTitle_BlueTwoBtn = 6;
    public static final int Read_TwoBtn_WarningTitle_BlueTwoBtn = 7;
    public static final int Read_TwoBtn_WarningTitle_WarningTwoBtn = 8;
    public static final int Write_OneBtn_NormalTitle_WhiteOneBtn = 9;
    public static final int Write_OneBtn_NormalTitle_BlueOneBtn = 10;
    public static final int Write_OneBtn_WarningTitle_BlueOneBtn = 11;
    public static final int Write_OneBtn_WarningTitle_WarningOneBtn = 12;
    public static final int Write_TwoBtn_NormalTitle_WhiteTwoBtn = 13;
    public static final int Write_TwoBtn_NormalTitle_BlueTwoBtn = 14;
    public static final int Write_TwoBtn_WarningTitle_BlueTwoBtn = 15;
    public static final int Write_TwoBtn_WarningTitle_WarningTwoBtn = 16;

    private CharSequence title, message, neutralText, positiveText, negativeText, hintInputText;
    private int type;
    private NegativeOnClickListener negativeListener = new NegativeOnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };
    private PositiveOnClickListener positiveListener = new PositiveOnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    private NeutralOnClickListener neutralListener = new NeutralOnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };

    public MyDialog(){

    }

    public MyDialog(int type, String title, String message, String neutralText, NeutralOnClickListener neutralListener){
        this.type = type;
        this.title = title;
        this.message = message;
        this.neutralText = neutralText;
        if (neutralListener != null){
            this.neutralListener= neutralListener;
        }
    }

    public MyDialog(int type, String title, String message, String negativeText, NegativeOnClickListener negativeListener, String positiveText, PositiveOnClickListener positiveListener){
        this.type = type;
        this.title = title;
        this.message = message;
        this.negativeText = negativeText;
        if (negativeListener != null){
            this.negativeListener = negativeListener;
        }
        this.positiveText = positiveText;
        if (positiveListener != null){
            this.positiveListener = positiveListener;
        }
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
            case Read_OneBtn_NormalTitle_WhiteOneBtn:
                setRead();
                setOneBtn();
                setNormalTitle();
                setWhiteOneBtn();
                break;
            case Read_OneBtn_NormalTitle_BlueOneBtn:
                setRead();
                setOneBtn();
                setNormalTitle();
                setBlueOneBtn();
                break;
            case Read_OneBtn_WarningTitle_BlueOneBtn:
                setRead();
                setOneBtn();
                setWarningTitle();
                setBlueOneBtn();
                break;
            case Read_OneBtn_WarningTitle_WarningOneBtn:
                setRead();
                setOneBtn();
                setWarningTitle();
                setWarningOneBtn();
                break;
            case Read_TwoBtn_NormalTitle_WhiteTwoBtn:
                setRead();
                setTwoBtn();
                setNormalTitle();
                setWhiteTwoBtn();
                break;
            case Read_TwoBtn_NormalTitle_BlueTwoBtn:
                setRead();
                setTwoBtn();
                setNormalTitle();
                setBlueTwoBtn();
                break;
            case Read_TwoBtn_WarningTitle_BlueTwoBtn:
                setRead();
                setTwoBtn();
                setWarningTitle();
                setBlueTwoBtn();
                break;
            case Read_TwoBtn_WarningTitle_WarningTwoBtn:
                setRead();
                setTwoBtn();
                setWarningTitle();
                setWarningTwoBtn();
                break;
            case Write_OneBtn_NormalTitle_WhiteOneBtn:
                setWrite();
                setOneBtn();
                setNormalTitle();
                setWhiteOneBtn();
                break;
            case Write_OneBtn_NormalTitle_BlueOneBtn:
                setWrite();
                setOneBtn();
                setNormalTitle();
                setBlueOneBtn();
                break;
            case Write_OneBtn_WarningTitle_BlueOneBtn:
                setWrite();
                setOneBtn();
                setWarningTitle();
                setBlueOneBtn();
                break;
            case Write_OneBtn_WarningTitle_WarningOneBtn:
                setWrite();
                setOneBtn();
                setWarningTitle();
                setWarningOneBtn();
                break;
            case Write_TwoBtn_NormalTitle_WhiteTwoBtn:
                setWrite();
                setTwoBtn();
                setNormalTitle();
                setWhiteTwoBtn();
                break;
            case Write_TwoBtn_NormalTitle_BlueTwoBtn:
                setWrite();
                setTwoBtn();
                setNormalTitle();
                setBlueTwoBtn();
                break;
            case Write_TwoBtn_WarningTitle_BlueTwoBtn:
                setWrite();
                setTwoBtn();
                setWarningTitle();
                setBlueTwoBtn();
                break;
            case Write_TwoBtn_WarningTitle_WarningTwoBtn:
                setWrite();
                setTwoBtn();
                setWarningTitle();
                setWarningTwoBtn();
                break;
        }


        binding.myDialogTitle.setText(title);

        binding.myDialogMessage.setText(message);

        binding.myDialogInputText.setHint(hintInputText);

        binding.myDialogNeutralButton.setOnClickListener(neutralListener);
        binding.myDialogNeutralButton.setText(neutralText);


        binding.myDialogNegativeButton.setOnClickListener(negativeListener);
        binding.myDialogNegativeButton.setText(negativeText);


        binding.myDialogPositiveButton.setOnClickListener(positiveListener);
        binding.myDialogPositiveButton.setText(positiveText);




        binding.myDialogInputTextClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.myDialogInputText.setText("");
            }
        });




    }

    private void setWhiteOneBtn() {
        binding.myDialogNeutralButton.setTextColor(getResources().getColor(R.color.white));
    }

    private void setBlueOneBtn() {
        binding.myDialogNeutralButton.setTextColor(getResources().getColor(R.color.blue));
    }

    private void setWarningOneBtn() {
        binding.myDialogNeutralButton.setTextColor(getResources().getColor(R.color.warning));
    }

    private void setWhiteTwoBtn() {
        binding.myDialogPositiveButton.setTextColor(getResources().getColor(R.color.white));
    }

    private void setBlueTwoBtn() {
        binding.myDialogPositiveButton.setTextColor(getResources().getColor(R.color.blue));
    }

    private void setWarningTwoBtn() {
        binding.myDialogPositiveButton.setTextColor(getResources().getColor(R.color.warning));
    }

    private void setNormalTitle() {
        binding.myDialogTitle.setTextColor(getResources().getColor(R.color.blue));
    }

    private void setWarningTitle() {
        binding.myDialogTitle.setTextColor(getResources().getColor(R.color.warning));
    }

    private void setRead() {
        binding.myDialogInput.setVisibility(View.GONE);
        binding.myDialogMessage.setVisibility(View.VISIBLE);
        this.hintInputText = this.message;
    }

    private void setWrite() {
        binding.myDialogInput.setVisibility(View.VISIBLE);
        binding.myDialogMessage.setVisibility(View.GONE);
    }

    private void setOneBtn() {
        binding.myDialogNeutralButton.setVisibility(View.VISIBLE);
        binding.myDialogNegativeButton.setVisibility(View.GONE);
        binding.myDialogPositiveButton.setVisibility(View.GONE);
        binding.decorationDialogPositiveNegative.setVisibility(View.GONE);

    }

    private void setTwoBtn() {
        binding.myDialogNeutralButton.setVisibility(View.GONE);
        binding.myDialogNegativeButton.setVisibility(View.VISIBLE);
        binding.myDialogPositiveButton.setVisibility(View.VISIBLE);
        binding.decorationDialogPositiveNegative.setVisibility(View.VISIBLE);
        setCanceledOnTouchOutside(false);
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

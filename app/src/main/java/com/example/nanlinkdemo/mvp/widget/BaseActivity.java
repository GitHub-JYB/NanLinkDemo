package com.example.nanlinkdemo.mvp.widget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.ui.LoadingDialog;
import com.example.nanlinkdemo.ui.MyDialog;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    protected T binding;
    private LoadingDialog loadingDialog;
    private MyDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ARouter.getInstance().inject(this);

        fullScreen(this);

        try {
            Type type = getClass().getGenericSuperclass();
            Class clazz = (Class<T>) ((java.lang.reflect.ParameterizedType) type).getActualTypeArguments()[0];
            Method method1 = clazz.getMethod("inflate", LayoutInflater.class);
            binding = (T) method1.invoke(null, getLayoutInflater());


        } catch (Exception e) {
            e.printStackTrace();
        }


        setContentView(binding.getRoot());
    }



    /**
     * 通过设置全屏，设置状态栏透明
     */
    private void fullScreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void startLoading() {
        loadingDialog = new LoadingDialog();
        loadingDialog.show(getSupportFragmentManager(),"LoadingDialog");
    }

    public void stopLoading() {
        loadingDialog.dismiss();
    }

    public void showMistakeDialog(String title, String message, int type) {
        myDialog = new MyDialog();
        myDialog.setType(type);
        myDialog.setTitle(title);
        myDialog.setTitleTextColorResId(R.color.warning);
        if (type == 0) {
            myDialog.setMessage(message);
            myDialog.setNeutralText("重试");
        }
        myDialog.show(getSupportFragmentManager(), "MyDialog");
    }

    public void dismissDialog(){
        myDialog.dismiss();
    }

    public void showSuccessDialog(String title, String message, int type, MyDialog.NeutralOnClickListener listener) {
        myDialog = new MyDialog();
        myDialog.setType(type);
        myDialog.setTitle(title);
        if (type == 0) {
            myDialog.setMessage(message);
            myDialog.setNeutralText("完成");
            myDialog.setNeutralOnClickListener(listener);
        }
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show(getSupportFragmentManager(), "MyDialog");
    }

}
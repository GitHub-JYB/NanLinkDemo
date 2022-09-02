package com.example.nanlinkdemo.mvp.widget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    public T binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
package com.example.NanLinkDemo.util;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;


import com.google.android.material.snackbar.Snackbar;

public class SnackBarUtil {




    public static void show(View view, CharSequence message){

        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        snackBarView.setLayoutParams(layoutParams);
        snackbar.show();
    }
}

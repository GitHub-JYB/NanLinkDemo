package com.example.nanlinkdemo.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarUtil {




    public static void show(Activity activity,CharSequence message){

        View decorView = activity.getWindow().getDecorView();
        View coordinatorLayout  = decorView.findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        snackbarView.setLayoutParams(layoutParams);
        snackbar.show();
    }
}

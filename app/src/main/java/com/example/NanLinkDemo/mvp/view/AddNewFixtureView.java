package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.bean.AddFixtureType;
import com.example.NanLinkDemo.ui.MyDialog;

import java.util.ArrayList;

public interface AddNewFixtureView {

    void setPresenter();

    boolean checkBle();

    boolean checkLocation();



//    boolean checkPermission();

//    void agreePermission();

    void finish();

    void showAddType(ArrayList<AddFixtureType> arrayList);

    void showMyDialog(int type, String title, String message, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

    void dismissMyDialog();
}

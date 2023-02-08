package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.bean.AddFixtureType;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.ui.MyDialog;

import java.util.ArrayList;

public interface AddNewFixtureView {

    void setPresenter();


    void finish();

    void showAddType(ArrayList<AddFixtureType> arrayList);

    void showMyDialog(int type, String title, String message, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

}

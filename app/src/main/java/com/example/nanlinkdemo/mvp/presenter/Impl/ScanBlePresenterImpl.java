package com.example.nanlinkdemo.mvp.presenter.Impl;


import android.view.View;


import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.mvp.model.Impl.ScanBleModelImpl;
import com.example.nanlinkdemo.mvp.presenter.ScanBlePresenter;
import com.example.nanlinkdemo.mvp.view.ScanBleView;


public class ScanBlePresenterImpl implements ScanBlePresenter {
    private final ScanBleView view;
    private final ScanBleModelImpl model;

    public ScanBlePresenterImpl(ScanBleView view) {
        this.view = view;
        this.model = new ScanBleModelImpl(this);
    }

    @Override
    public void onClickSwitch(int position) {

    }

    @Override
    public void getListDataFromView() {


        model.getListData();
    }

    @Override
    public void onClickSwitch(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_btn:
                view.finish();
                break;
        }
    }
}

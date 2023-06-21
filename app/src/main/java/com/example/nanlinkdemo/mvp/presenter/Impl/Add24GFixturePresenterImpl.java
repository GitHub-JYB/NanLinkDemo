package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.view.View;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.mvp.adapter.Add24GAdapter;
import com.example.nanlinkdemo.mvp.model.Impl.Add24GFixtureModelImpl;
import com.example.nanlinkdemo.mvp.presenter.Add24GFixturePresenter;
import com.example.nanlinkdemo.mvp.view.Add24GFixtureView;
import com.example.nanlinkdemo.ui.BoxView;
import com.example.nanlinkdemo.ui.MyDialog;

import java.util.ArrayList;

public class Add24GFixturePresenterImpl implements Add24GFixturePresenter {
    private final Add24GFixtureView view;
    private final Add24GFixtureModelImpl model;
    private ArrayList<Add24GFixture> add24GFixtures;

    public Add24GFixturePresenterImpl(Add24GFixtureView view) {
        this.view = view;
        this.model = new Add24GFixtureModelImpl(this);
    }

    @Override
    public void getDataFromView() {
        model.getBoxViewData();
        model.getListData();
    }

    @Override
    public void setDataToView(ArrayList<Add24GFixture> add24GFixtures) {
        this.add24GFixtures = add24GFixtures;
        view.setData(add24GFixtures);
    }

    @Override
    public void setDataToBoxView(Integer boxViewId, String title, ArrayList<String> dataList, int checkIndex) {
        view.updateBoxView(boxViewId, title, dataList, checkIndex);
    }



    @Override
    public void onClick() {
        view.finish();
    }

    @Override
    public void setListener() {
        view.setListViewOnOutRangeListener(new Add24GAdapter.OnOutRangeListener() {
            @Override
            public void onOutRange() {
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "", "请输入介于 1 - 512\n之间的数值", "确定", null);
            }
        });
        view.setListViewOnCheckCompleteListener(new Add24GAdapter.OnCheckCompleteListener() {
            @Override
            public void CheckComplete(boolean complete) {
                view.setFinishBtn(complete);
            }
        });
    }


}

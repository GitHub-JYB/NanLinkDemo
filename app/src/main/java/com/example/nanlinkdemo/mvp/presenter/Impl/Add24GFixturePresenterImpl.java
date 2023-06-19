package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.view.View;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.mvp.model.Impl.Add24GFixtureModelImpl;
import com.example.nanlinkdemo.mvp.presenter.Add24GFixturePresenter;
import com.example.nanlinkdemo.mvp.view.Add24GFixtureView;
import com.example.nanlinkdemo.ui.BoxView;

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
    public void getListDataFromView() {
        model.getListData();
    }

    @Override
    public void setDataToView(ArrayList<Add24GFixture> add24GFixtures) {
        this.add24GFixtures = add24GFixtures;
        view.setData(add24GFixtures);
    }

    @Override
    public void setDataToBoxView(BoxView boxView, String title, ArrayList<String> dataList, int checkIndex) {
        switch (boxView.getId()) {
            case R.id.type:
                view.setBoxViewOnCheckedChangeListener(boxView, new BoxView.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(int index) {
                        switch (index) {
                            case 0:
                                view.setBoxViewVisibility(R.id.cctRange, View.GONE);
                                view.setBoxViewVisibility(R.id.GM, View.GONE);
                                break;
                            case 1:
                                view.setBoxViewVisibility(R.id.cctRange, View.VISIBLE);
                                view.setBoxViewVisibility(R.id.GM, View.GONE);
                                break;
                            case 2:
                                view.setBoxViewVisibility(R.id.cctRange, View.VISIBLE);
                                view.setBoxViewVisibility(R.id.GM, View.VISIBLE);
                                break;
                        }
                    }
                });
                break;
            case R.id.cctRange:
                view.setBoxViewOnCheckedChangeListener(boxView, new BoxView.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(int index) {
                        switch (index) {
                            case 0:
                        }
                    }
                });
                break;
            case R.id.GM:
                view.setBoxViewOnCheckedChangeListener(boxView, new BoxView.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(int index) {
                        switch (index) {

                        }
                    }
                });
                break;
        }
        view.updateBoxView(boxView, title, dataList, checkIndex);
    }

    @Override
    public void getBoxViewDataFromModel(BoxView boxView) {
        model.getBoxViewData(boxView);
    }

    @Override
    public void onClick() {
        view.finish();
    }


}

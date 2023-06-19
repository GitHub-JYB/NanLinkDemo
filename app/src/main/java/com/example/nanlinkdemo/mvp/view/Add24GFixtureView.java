package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.ui.BoxView;

import java.util.ArrayList;

public interface Add24GFixtureView {
    void setPresenter();

    void setData(ArrayList<Add24GFixture> add24GFixtures);

    void updateBoxView(BoxView boxView, String title, ArrayList<String> dataList, int checkIndex);

    void setBoxViewOnCheckedChangeListener(BoxView boxView, BoxView.OnCheckedChangeListener listener);

    void setBoxViewVisibility(int boxViewId, int visibility);

    void finish();
}

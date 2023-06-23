package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.mvp.adapter.Add24GAdapter;
import com.example.nanlinkdemo.ui.BoxView;
import com.example.nanlinkdemo.ui.MyDialog;

import java.util.ArrayList;

public interface Add24GFixtureView {
    void setFinishBtn(boolean complete);

    void setPresenter();

    void setData(ArrayList<Add24GFixture> add24GFixtures);

    void updateBoxView(Integer boxViewId, String title, ArrayList<String> dataList, int checkIndex);

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void finish();

    ArrayList<Add24GFixture> getFixtureArrayList();

    int getCheckIndexType();

    int getCheckIndexCCTRange();

    int getCheckIndexGM();

    void setListViewOnOutRangeListener(Add24GAdapter.OnOutRangeListener onOutRangeListener);

    void setListViewOnCheckCompleteListener(Add24GAdapter.OnCheckCompleteListener onCheckCompleteListener);

}

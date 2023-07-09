package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.bean.Add24GFixture;
import com.example.NanLinkDemo.mvp.adapter.Add24GAdapter;
import com.example.NanLinkDemo.ui.MyDialog;

import java.util.ArrayList;

public interface Add24GFixtureView {
    void setFinishBtn(boolean complete);

    void setPresenter();

    void setData(ArrayList<Add24GFixture> add24GFixtures);

    void updateBoxView(Integer boxViewId, String title, ArrayList<String> dataList, int checkIndex);

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void showMyDialog(int type, String title, String message, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

    void showMyDialog(int type, String title, String bigSizeMessage, String smallSizeMessage, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

    void dismissMyDialog();

    String getInputTextMyDialog();

    void finish();

    ArrayList<Add24GFixture> getFixtureArrayList();

    int getCheckIndexType();

    int getCheckIndexCCTRange();

    int getCheckIndexGM();

    void setListViewOnOutRangeListener(Add24GAdapter.OnOutRangeListener onOutRangeListener);

    void setListViewOnCheckCompleteListener(Add24GAdapter.OnCheckCompleteListener onCheckCompleteListener);

}

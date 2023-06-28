package com.example.NanLinkDemo.mvp.presenter;

import android.view.View;

import com.example.NanLinkDemo.DB.bean.Scene;

import java.util.List;

public interface ManageScenePresenter {
    void getSceneListFromModel();

    void switchOnclick(View view);

    void switchSceneList(int position);

    void receiveQueryScene(List<Scene> scenes);


}

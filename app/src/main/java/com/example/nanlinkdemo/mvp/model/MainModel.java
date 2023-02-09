package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.Device;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.bean.DeviceMessage;

import java.util.HashMap;

public interface MainModel {
    void getMenu(String nickName);

    void getSceneList();

    void getSceneGroupList();

    void getThreePointMenu();

    void deleteScene(Scene scene);

    void addScene(Scene scene);

    void addSceneGroup(SceneGroup sceneGroup);

    void deleteSceneGroup(SceneGroup sceneGroup);

    void queryScene(String sceneName, int type);

    void querySceneGroup(String sceneGroupName, int type);

    void updateScene(Scene scene);

    void updateSceneGroup(SceneGroup sceneGroup);

    void getSortList(int position);

    void updateUser(User user);

    void querySceneFromGroup(String sceneGroupName, int type);

    void getDeviceList();

    void updateDevice(Device device);
}

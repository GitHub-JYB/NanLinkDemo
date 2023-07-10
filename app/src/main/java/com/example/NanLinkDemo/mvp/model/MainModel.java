package com.example.NanLinkDemo.mvp.model;

import com.example.NanLinkDemo.DB.bean.Device;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;
import com.example.NanLinkDemo.DB.bean.User;

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

    void getSortList();

    void updateUser(User user);

    void querySceneFromGroup(String sceneGroupName, int type);

    void getDeviceList();

    void updateDevice(Device device);

    void addDevice(Device device);

    void getOnlineUser();
}

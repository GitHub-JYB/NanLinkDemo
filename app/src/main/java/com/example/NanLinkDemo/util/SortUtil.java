package com.example.NanLinkDemo.util;

import android.util.Log;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;

import java.util.ArrayList;


public class SortUtil {


    private static ArrayList<Fixture> upCHFixtureList(ArrayList<Fixture> fixtures){
        if (fixtures.size() >= 1){
            Fixture minCH = fixtures.get(0);
            for (int i = 0; i < fixtures.size() - 1; i++){
                for (int j = i + 1; j < fixtures.size(); j++){
                    if (fixtures.get(i).getCH() > fixtures.get(j).getCH()){
                        minCH = fixtures.get(j);
                        fixtures.set(j, fixtures.get(i));
                        fixtures.set(i, minCH);
                    }
                }
            }
        }
        return fixtures;
    }

    private static ArrayList<Fixture> downCHFixtureList(ArrayList<Fixture> fixtures){
        if (fixtures.size() >= 1){
            Fixture maxCH = fixtures.get(0);
            for (int i = 0; i < fixtures.size() - 1; i++){
                for (int j = i + 1; j < fixtures.size(); j++){
                    if (fixtures.get(i).getCH() < fixtures.get(j).getCH()){
                        maxCH = fixtures.get(j);
                        fixtures.set(j, fixtures.get(i));
                        fixtures.set(i, maxCH);
                    }
                }
            }
        }
        return fixtures;
    }

    private static ArrayList<Fixture> upNameFixtureList(ArrayList<Fixture> fixtures){
        if (fixtures.size() >= 1){
            Fixture minName = fixtures.get(0);
            for (int i = 0; i < fixtures.size() - 1; i++){
                for (int j = i + 1; j < fixtures.size(); j++){
                    if (fixtures.get(i).getName().compareTo(fixtures.get(j).getName()) > 0){
                        minName = fixtures.get(j);
                        fixtures.set(j, fixtures.get(i));
                        fixtures.set(i, minName);
                    }
                }
            }
        }
        return fixtures;
    }

    private static ArrayList<Fixture> downNameFixtureList(ArrayList<Fixture> fixtures){
        if (fixtures.size() >= 1){
            Fixture maxName = fixtures.get(0);
            for (int i = 0; i < fixtures.size() - 1; i++){
                for (int j = i + 1; j < fixtures.size(); j++){
                    if (fixtures.get(i).getName().compareTo(fixtures.get(j).getName()) <= 0){
                        maxName = fixtures.get(j);
                        fixtures.set(j, fixtures.get(i));
                        fixtures.set(i, maxName);
                    }
                }
            }
        }
        return fixtures;
    }

    public static ArrayList<Fixture> sortFixtureList(ArrayList<Fixture> fixtures, int type){
        if (fixtures == null){
            return new ArrayList<>();
        }
        switch (type){
            case 1:
                return downCHFixtureList(fixtures);
            case 2:
                return upNameFixtureList(fixtures);
            case 3:
                return downNameFixtureList(fixtures);
            default:
                return upCHFixtureList(fixtures);
        }
    }

    private static ArrayList<SceneGroup> upCreatedDataSceneGroupList(ArrayList<SceneGroup> sceneGroups){
        if (sceneGroups.size() >= 1){
            SceneGroup minCreatedData = sceneGroups.get(0);
            for (int i = 0; i < sceneGroups.size() - 1; i++){
                for (int j = i + 1; j < sceneGroups.size(); j++){
                    if (sceneGroups.get(i).getCreatedDate() > sceneGroups.get(j).getCreatedDate()){
                        minCreatedData = sceneGroups.get(j);
                        sceneGroups.set(j, sceneGroups.get(i));
                        sceneGroups.set(i, minCreatedData);
                    }
                }
            }
        }
        return sceneGroups;
    }

    private static ArrayList<SceneGroup> downCreatedDataSceneGroupList(ArrayList<SceneGroup> sceneGroups){
        if (sceneGroups.size() >= 1){
            SceneGroup maxCreatedData = sceneGroups.get(0);
            for (int i = 0; i < sceneGroups.size() - 1; i++){
                for (int j = i + 1; j < sceneGroups.size(); j++){
                    if (sceneGroups.get(i).getCreatedDate() < sceneGroups.get(j).getCreatedDate()){
                        maxCreatedData = sceneGroups.get(j);
                        sceneGroups.set(j, sceneGroups.get(i));
                        sceneGroups.set(i, maxCreatedData);
                    }
                }
            }
        }
        return sceneGroups;
    }

    private static ArrayList<SceneGroup> upModifiedDataSceneGroupList(ArrayList<SceneGroup> sceneGroups){
        if (sceneGroups.size() >= 1){
            SceneGroup minModifiedData = sceneGroups.get(0);
            for (int i = 0; i < sceneGroups.size() - 1; i++){
                for (int j = i + 1; j < sceneGroups.size(); j++){
                    if (sceneGroups.get(i).getModifiedDate() > sceneGroups.get(j).getModifiedDate()){
                        minModifiedData = sceneGroups.get(j);
                        sceneGroups.set(j, sceneGroups.get(i));
                        sceneGroups.set(i, minModifiedData);
                    }
                }
            }
        }
        return sceneGroups;
    }

    private static ArrayList<SceneGroup> downModifiedDataSceneGroupList(ArrayList<SceneGroup> sceneGroups){
        if (sceneGroups.size() >= 1){
            SceneGroup maxModifiedData = sceneGroups.get(0);
            for (int i = 0; i < sceneGroups.size() - 1; i++){
                for (int j = i + 1; j < sceneGroups.size(); j++){
                    if (sceneGroups.get(i).getModifiedDate() < sceneGroups.get(j).getModifiedDate()){
                        maxModifiedData = sceneGroups.get(j);
                        sceneGroups.set(j, sceneGroups.get(i));
                        sceneGroups.set(i, maxModifiedData);
                    }
                }
            }
        }
        return sceneGroups;
    }


    private static ArrayList<SceneGroup> upFixtureNumSceneGroupList(ArrayList<SceneGroup> sceneGroups){
        if (sceneGroups.size() >= 1){
            SceneGroup minFixtureNum = sceneGroups.get(0);
            for (int i = 0; i < sceneGroups.size() - 1; i++){
                for (int j = i + 1; j < sceneGroups.size(); j++){
                    if (sceneGroups.get(i).getSceneNum() > sceneGroups.get(j).getSceneNum()){
                        minFixtureNum = sceneGroups.get(j);
                        sceneGroups.set(j, sceneGroups.get(i));
                        sceneGroups.set(i, minFixtureNum);
                    }
                }
            }
        }
        return sceneGroups;
    }

    private static ArrayList<SceneGroup> downFixtureNumSceneGroupList(ArrayList<SceneGroup> sceneGroups){
        if (sceneGroups.size() >= 1){
            SceneGroup maxFixtureNum = sceneGroups.get(0);
            for (int i = 0; i < sceneGroups.size() - 1; i++){
                for (int j = i + 1; j < sceneGroups.size(); j++){
                    if (sceneGroups.get(i).getSceneNum() <= sceneGroups.get(j).getSceneNum()){
                        maxFixtureNum = sceneGroups.get(j);
                        sceneGroups.set(j, sceneGroups.get(i));
                        sceneGroups.set(i, maxFixtureNum);
                    }
                }
            }
        }
        return sceneGroups;
    }

    private static ArrayList<SceneGroup> upNameSceneGroupList(ArrayList<SceneGroup> sceneGroups){
        if (sceneGroups.size() >= 1){
            SceneGroup minName = sceneGroups.get(0);
            for (int i = 0; i < sceneGroups.size() - 1; i++){
                for (int j = i + 1; j < sceneGroups.size(); j++){
                    if (sceneGroups.get(i).getName().compareTo(sceneGroups.get(j).getName()) > 0){
                        minName = sceneGroups.get(j);
                        sceneGroups.set(j, sceneGroups.get(i));
                        sceneGroups.set(i, minName);
                    }
                }
            }
        }
        return sceneGroups;
    }

    private static ArrayList<SceneGroup> downNameSceneGroupList(ArrayList<SceneGroup> sceneGroups){
        if (sceneGroups.size() >= 1){
            SceneGroup maxName = sceneGroups.get(0);
            for (int i = 0; i < sceneGroups.size() - 1; i++){
                for (int j = i + 1; j < sceneGroups.size(); j++){
                    if (sceneGroups.get(i).getName().compareTo(sceneGroups.get(j).getName()) <= 0){
                        maxName = sceneGroups.get(j);
                        sceneGroups.set(j, sceneGroups.get(i));
                        sceneGroups.set(i, maxName);
                    }
                }
            }
        }
        return sceneGroups;
    }

    public static ArrayList<SceneGroup> sortSceneGroupList(ArrayList<SceneGroup> sceneGroups, int type){
        if (sceneGroups == null){
            return new ArrayList<>();
        }
        switch (type){
            case 1:
                return downCreatedDataSceneGroupList(sceneGroups);
            case 2:
                return upModifiedDataSceneGroupList(sceneGroups);
            case 3:
                return downModifiedDataSceneGroupList(sceneGroups);
            case 4:
                return upFixtureNumSceneGroupList(sceneGroups);
            case 5:
                return downFixtureNumSceneGroupList(sceneGroups);
            case 6:
                return upNameSceneGroupList(sceneGroups);
            case 7:
                return downNameSceneGroupList(sceneGroups);
            default:
                return upCreatedDataSceneGroupList(sceneGroups);
        }
    }
    private static ArrayList<Scene> upCreatedDataSceneList(ArrayList<Scene> scenes){
        if (scenes.size() >= 1){
            Scene minCreatedData = scenes.get(0);
            for (int i = 0; i < scenes.size() - 1; i++){
                for (int j = i + 1; j < scenes.size(); j++){
                    if (scenes.get(i).getCreatedDate() > scenes.get(j).getCreatedDate()){
                        minCreatedData = scenes.get(j);
                        scenes.set(j, scenes.get(i));
                        scenes.set(i, minCreatedData);
                    }
                }
            }
        }
        return scenes;
    }

    private static ArrayList<Scene> downCreatedDataSceneList(ArrayList<Scene> scenes){
        if (scenes.size() >= 1){
            Scene maxCreatedData = scenes.get(0);
            for (int i = 0; i < scenes.size() - 1; i++){
                for (int j = i + 1; j < scenes.size(); j++){
                    if (scenes.get(i).getCreatedDate() < scenes.get(j).getCreatedDate()){
                        maxCreatedData = scenes.get(j);
                        scenes.set(j, scenes.get(i));
                        scenes.set(i, maxCreatedData);
                    }
                }
            }
        }
        return scenes;
    }

    private static ArrayList<Scene> upModifiedDataSceneList(ArrayList<Scene> scenes){
        if (scenes.size() >= 1){
            Scene minModifiedData = scenes.get(0);
            for (int i = 0; i < scenes.size() - 1; i++){
                for (int j = i + 1; j < scenes.size(); j++){
                    if (scenes.get(i).getModifiedDate() > scenes.get(j).getModifiedDate()){
                        minModifiedData = scenes.get(j);
                        scenes.set(j, scenes.get(i));
                        scenes.set(i, minModifiedData);
                    }
                }
            }
        }
        return scenes;
    }

    private static ArrayList<Scene> downModifiedDataSceneList(ArrayList<Scene> scenes){
        if (scenes.size() >= 1){
            Scene maxModifiedData = scenes.get(0);
            for (int i = 0; i < scenes.size() - 1; i++){
                for (int j = i + 1; j < scenes.size(); j++){
                    if (scenes.get(i).getModifiedDate() < scenes.get(j).getModifiedDate()){
                        maxModifiedData = scenes.get(j);
                        scenes.set(j, scenes.get(i));
                        scenes.set(i, maxModifiedData);
                    }
                }
            }
        }
        return scenes;
    }


    private static ArrayList<Scene> upFixtureNumSceneList(ArrayList<Scene> scenes){
        if (scenes.size() >= 1){
            Scene minFixtureNum = scenes.get(0);
            for (int i = 0; i < scenes.size() - 1; i++){
                for (int j = i + 1; j < scenes.size(); j++){
                    if (scenes.get(i).getFixtureNum() > scenes.get(j).getFixtureNum()){
                        minFixtureNum = scenes.get(j);
                        scenes.set(j, scenes.get(i));
                        scenes.set(i, minFixtureNum);
                    }
                }
            }
        }
        return scenes;
    }

    private static ArrayList<Scene> downFixtureNumSceneList(ArrayList<Scene> scenes){
        if (scenes.size() >= 1){
            Scene maxFixtureNum = scenes.get(0);
            for (int i = 0; i < scenes.size() - 1; i++){
                for (int j = i + 1; j < scenes.size(); j++){
                    if (scenes.get(i).getFixtureNum() <= scenes.get(j).getFixtureNum()){
                        maxFixtureNum = scenes.get(j);
                        scenes.set(j, scenes.get(i));
                        scenes.set(i, maxFixtureNum);
                    }
                }
            }
        }
        return scenes;
    }

    private static ArrayList<Scene> upNameSceneList(ArrayList<Scene> scenes){
        if (scenes.size() >= 1){
            Scene minName = scenes.get(0);
            for (int i = 0; i < scenes.size() - 1; i++){
                for (int j = i + 1; j < scenes.size(); j++){
                    if (scenes.get(i).getName().compareTo(scenes.get(j).getName()) > 0){
                        minName = scenes.get(j);
                        scenes.set(j, scenes.get(i));
                        scenes.set(i, minName);
                    }
                }
            }
        }
        return scenes;
    }

    private static ArrayList<Scene> downNameSceneList(ArrayList<Scene> scenes){
        if (scenes.size() >= 1){
            Scene maxName = scenes.get(0);
            for (int i = 0; i < scenes.size() - 1; i++){
                for (int j = i + 1; j < scenes.size(); j++){
                    if (scenes.get(i).getName().compareTo(scenes.get(j).getName()) <= 0){
                        maxName = scenes.get(j);
                        scenes.set(j, scenes.get(i));
                        scenes.set(i, maxName);
                    }
                }
            }
        }
        return scenes;
    }

    public static ArrayList<Scene> sortSceneList(ArrayList<Scene> scenes, int type){
        if (scenes == null){
            return new ArrayList<>();
        }
        switch (type){
            case 1:
                return downCreatedDataSceneList(scenes);
            case 2:
                return upModifiedDataSceneList(scenes);
            case 3:
                return downModifiedDataSceneList(scenes);
            case 4:
                return upFixtureNumSceneList(scenes);
            case 5:
                return downFixtureNumSceneList(scenes);
            case 6:
                return upNameSceneList(scenes);
            case 7:
                return downNameSceneList(scenes);
            default:
                return upCreatedDataSceneList(scenes);
        }
    }
}

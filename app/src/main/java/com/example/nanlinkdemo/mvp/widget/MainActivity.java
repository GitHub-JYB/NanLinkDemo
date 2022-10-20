package com.example.nanlinkdemo.mvp.widget;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.databinding.ActivityMainBinding;
import com.example.nanlinkdemo.mvp.adapter.MenuAdapter;
import com.example.nanlinkdemo.mvp.adapter.SceneAdapter;
import com.example.nanlinkdemo.mvp.adapter.ThreePointAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.MainPresenterImpl;
import com.example.nanlinkdemo.mvp.view.MainView;
import com.example.nanlinkdemo.ui.MyDialog;
import com.example.nanlinkdemo.ui.SettingDialog;
import com.example.nanlinkdemo.ui.UnlessLastItemDecoration;
import com.example.nanlinkdemo.util.Constant;
import com.example.nanlinkdemo.util.DateUtil;
import com.example.nanlinkdemo.util.SnackBarUtil;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

@Route(path = Constant.ACTIVITY_URL_Main)
public class MainActivity extends BaseActivity<ActivityMainBinding> implements MainView, View.OnClickListener {


    private MainPresenterImpl presenter;
    private MenuAdapter menuAdapter;
    private SceneAdapter sceneAdapter;
    private SettingDialog settingDialog;
    private List<Scene> sceneList;
    private List<SceneGroup> sceneGroupList;
    private MyDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initMenu();
        initRecycleView();
    }

    private void initRecycleView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        // 设置分割线格式并添加
        UnlessLastItemDecoration decoration = new UnlessLastItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getDrawable(R.drawable.decoration_scene));
        binding.recycleView.addItemDecoration(decoration);
        sceneAdapter = new SceneAdapter();
        updateSceneList();
        binding.recycleView.setAdapter(sceneAdapter);
        sceneAdapter.setMenuOnClickListener(new SceneAdapter.MenuOnClickListener() {
            @Override
            public void onClick(int type, int position) {
                showThreePointDialog(type, position);
            }
        });
        sceneAdapter.setOnClickListener(new SceneAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.sceneListSwitch(position);
            }
        });
    }

    private void showThreePointDialog(int type, int position) {
        presenter.getThreePointMenuFromModel(type, position);
    }

    private void initToolbar() {
        binding.toolbar.setTitle("设备列表");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_menu);
        binding.toolbar.setRightBtnIcon(R.drawable.ic_search);
        binding.toolbar.setLeftBtnOnClickListener(this);
        binding.toolbar.setRightBtnOnClickListener(this);
    }

    private void setPresenter() {
        presenter = new MainPresenterImpl(this);
    }

    private void initMenu() {
        binding.version.setText("Version " + MyApplication.getVersion());
        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));

        // 设置分割线格式并添加
        UnlessLastItemDecoration decoration = new UnlessLastItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getDrawable(R.drawable.decoration_menu));
        binding.recyclerViewMenu.addItemDecoration(decoration);

        menuAdapter = new MenuAdapter();
        presenter.getMenuFromModel();
        binding.recyclerViewMenu.setAdapter(menuAdapter);
        menuAdapter.setOnClickListener(new MenuAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.menuSwitch(position);
            }
        });
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    @Override
    public void showMenu(ArrayList<Menu> menuArrayList) {
        menuAdapter.setData(menuArrayList);
    }

    @Override
    public void openDrawLayout() {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void closeDrawLayout() {
        binding.drawerLayout.closeDrawers();
    }



    @Override
    public void showMenuDialog(String title, String message, int type) {
        dialog = new MyDialog(type, title, message);
        if(type == 0){
            dialog.setMessage(message);
            dialog.setNeutralText("重试");
            dialog.setNeutralOnClickListener(new MyDialog.NeutralOnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }else {
            dialog.setPositiveText("创建");
            dialog.setNegativeText("取消");
            dialog.setPositiveOnClickListener(new MyDialog.PositiveOnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.switchDialog(dialog.getInputText(), title);
//                    dialog.dismiss();
                }
            });
        }

        dialog.show(getSupportFragmentManager(), "MyDialog");
    }

    @Override
    public void showSceneList(List<Scene> sceneList, List<SceneGroup> sceneGroupList) {
        this.sceneList = sceneList;
        this.sceneGroupList = sceneGroupList;
        sceneAdapter.setData(sceneList, sceneGroupList);
    }

    @Override
    public void updateSceneList() {
        presenter.getSceneListFromModel();
    }

    @Override
    public void showThreePointMenu(ArrayList<String> threePointList, int type, int furtherPosition) {
        settingDialog = new SettingDialog();
        settingDialog.setData(threePointList);
        settingDialog.show(getSupportFragmentManager(), "SettingDialog");
        settingDialog.setOnClickListener(new ThreePointAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.switchThreePointMenu(position, type, furtherPosition);
            }
        });
    }

    @Override
    public void dismissSettingDialog() {
        settingDialog.dismiss();
    }

    @Override
    public void dismissMyDialog() {
        dialog.dismiss();
    }

    @Override
    public void deleteScene(int furtherPosition) {
        presenter.deleteSceneFromModel(sceneList.get(furtherPosition));
    }

    @Override
    public void deleteSceneGroup(int furtherPosition) {
        presenter.deleteSceneGroupFromModel(sceneGroupList.get(furtherPosition));

    }

    @Override
    public void showSnack(CharSequence message) {
        SnackBarUtil.show(dialog.getView(), message);
    }


    @Override
    public void onClick(View v) {
        presenter.toolbarSwitch(v);
    }
}
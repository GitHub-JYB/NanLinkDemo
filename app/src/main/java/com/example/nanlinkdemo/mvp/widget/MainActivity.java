package com.example.nanlinkdemo.mvp.widget;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.databinding.ActivityMainBinding;
import com.example.nanlinkdemo.mvp.adapter.MenuAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.MainPresenterImpl;
import com.example.nanlinkdemo.mvp.view.MainView;
import com.example.nanlinkdemo.ui.MyDialog;

import java.util.ArrayList;


public class MainActivity extends BaseActivity<ActivityMainBinding> implements MainView {


    private MainPresenterImpl presenter;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initRecyclerViewMenu();
        replaceFragment(null, SceneListFragment.getInstance());
    }

    private void setPresenter() {
        presenter = new MainPresenterImpl(this);
    }

    private void initRecyclerViewMenu() {
        binding.version.setText("Version " + MyApplication.getVersion());
        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));

        // 设置分割线格式并添加
        DividerItemDecoration decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getDrawable(R.drawable.decoration_menu));
        binding.recyclerViewMenu.addItemDecoration(decoration);

        menuAdapter = new MenuAdapter();
        presenter.getMenuFromModel();
        binding.recyclerViewMenu.setAdapter(menuAdapter);
        menuAdapter.setOnClickListener(new MenuAdapter.OnClickListener() {
            @Override
            public void onClick(String menuText) {
                presenter.menuSwitch(menuText);
            }
        });
    }

    @Override
    public void initToolbar() {
        setToolbar(R.drawable.ic_menu, "场景列表", R.drawable.ic_search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.toolbarSwitch(v);
            }
        },null);
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
        MyDialog dialog = new MyDialog();
        dialog.setType(type);
        dialog.setTitle(title);
        if (type == 0) {
            dialog.setMessage(message);
            dialog.setNeutralText("重试");
            dialog.setNeutralOnClickListener(new MyDialog.NeutralOnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else {
            dialog.setNegativeText("取消");
            dialog.setPositiveText("创建");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setNegativeOnClickListener(new MyDialog.NegativeOnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }

        dialog.show(getSupportFragmentManager(), "MyDialog");


    }

    @Override
    public void replaceFragment(Fragment oldFragment, Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!newFragment.isAdded()) {
            if (oldFragment != null) {
                transaction.hide(oldFragment).add(R.id.content, newFragment).commit();
            } else {
                transaction.add(R.id.content, newFragment).commit();
            }
        } else {
            if (oldFragment != null) {
                transaction.hide(oldFragment).show(newFragment).commit();
            } else {
                transaction.show(newFragment).commit();
            }
        }
    }

    @Override
    public void setToolbar(int leftBtnResId, String title, int rightBtnResId, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        binding.toolbar.setTitle(title);
        if (leftBtnResId != 0){
            binding.toolbar.setLeftBtnIcon(leftBtnResId);
        }else {
            binding.toolbar.hideLeftBtnIcon();
        }
        if (rightBtnResId != 0){
            binding.toolbar.setRightBtnIcon(rightBtnResId);
        }else {
            binding.toolbar.hideRightBtnIcon();
        }
        if (leftListener != null){
            binding.toolbar.setLeftBtnOnClickListener(leftListener);
        }
        if (rightListener != null){
            binding.toolbar.setRightBtnOnClickListener(rightListener);
        }
    }

}
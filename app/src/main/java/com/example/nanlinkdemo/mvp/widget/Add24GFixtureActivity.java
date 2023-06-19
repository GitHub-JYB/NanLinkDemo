package com.example.nanlinkdemo.mvp.widget;

import static com.example.nanlinkdemo.util.Constant.ACTIVITY_URL_Add24GFixture;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.databinding.ActivityAdd24gFixtureBinding;
import com.example.nanlinkdemo.mvp.adapter.Add24GAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.Add24GFixturePresenterImpl;
import com.example.nanlinkdemo.mvp.view.Add24GFixtureView;
import com.example.nanlinkdemo.ui.BoxView;

import java.util.ArrayList;

@Route(path = ACTIVITY_URL_Add24GFixture)
public class Add24GFixtureActivity extends BaseActivity<ActivityAdd24gFixtureBinding> implements Add24GFixtureView, View.OnClickListener {


    private Add24GFixturePresenterImpl presenter;
    private Add24GAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initBoxView();
        initRecyclerView();
        initFinishBtn();

    }

    private void initFinishBtn() {
        binding.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClick();
            }
        });
    }

    private void initBoxView() {
        presenter.getBoxViewDataFromModel(binding.type);
        binding.cctRange.setWidth(MyApplication.dip2px(68));
        binding.cctRange.setHeight(MyApplication.dip2px(50));
        binding.cctRange.setVisibility(View.GONE);
        presenter.getBoxViewDataFromModel(binding.cctRange);
        binding.GM.setVisibility(View.GONE);
        presenter.getBoxViewDataFromModel(binding.GM);
    }

    private void initToolbar() {
        binding.toolbar.setTitle("通过2.4G");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }

    private void initRecyclerView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        // 设置分割线格式并添加
        DividerItemDecoration decoration = new DividerItemDecoration(getBaseContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getBaseContext().getDrawable(R.drawable.decoration_add_24g));
        binding.recycleView.addItemDecoration(decoration);

        adapter = new Add24GAdapter();
        presenter.getListDataFromView();
        binding.recycleView.setAdapter(adapter);
//        adapter.setOnClickListener(new Add24GAdapter.OnClickListener() {
//            @Override
//            public void onClick(String settingText) {
//                presenter.onClickSwitch(settingText);
//            }
//        });
    }

    @Override
    public void setPresenter() {
        presenter = new Add24GFixturePresenterImpl(this);
    }

    @Override
    public void setData(ArrayList<Add24GFixture> add24GFixtures) {
        adapter.setData(add24GFixtures);
    }

    @Override
    public void updateBoxView(BoxView boxView, String title, ArrayList<String> dataList, int checkIndex){
        boxView.setTitle(title);
        boxView.check(checkIndex);
        boxView.setData(dataList);
    }

    @Override
    public void setBoxViewOnCheckedChangeListener(BoxView boxView, BoxView.OnCheckedChangeListener listener){
        boxView.setOnCheckedChangeListener(listener);
    }

    @Override
    public void setBoxViewVisibility(int boxViewId, int visibility) {
        findViewById(boxViewId).setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}

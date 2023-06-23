package com.example.nanlinkdemo.mvp.widget;

import static com.example.nanlinkdemo.util.Constant.ACTIVITY_URL_Add24GFixture;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.databinding.ActivityAdd24gFixtureBinding;
import com.example.nanlinkdemo.mvp.adapter.Add24GAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.Add24GFixturePresenterImpl;
import com.example.nanlinkdemo.mvp.view.Add24GFixtureView;

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
        presenter.getDataFromView();
        binding.recycleView.setAdapter(adapter);
        presenter.setListener();
    }

    @Override
    public void setFinishBtn(boolean complete) {
        binding.btnFinish.setClickable(complete);
        if (complete) {
            binding.btnFinish.setBackgroundResource(R.drawable.bg_able_btn_selected);
        } else {
            binding.btnFinish.setBackgroundResource(R.drawable.bg_unable_btn_selected);
        }
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
    public void updateBoxView(Integer boxViewId, String title, ArrayList<String> dataList, int checkIndex){
        adapter.updateBoxView(boxViewId, title, dataList, checkIndex);
    }

    @Override
    public ArrayList<Add24GFixture> getFixtureArrayList(){
        return adapter.getFixtureArrayList();
    }

    @Override
    public int getCheckIndexType(){
        return adapter.getCheckIndex_type();
    }

    @Override
    public int getCheckIndexCCTRange(){
        return adapter.getCheckIndex_cctRange();
    }

    @Override
    public int getCheckIndexGM(){
        return adapter.getCheckIndex_GM();
    }


    @Override
    public void setListViewOnOutRangeListener(Add24GAdapter.OnOutRangeListener onOutRangeListener) {
        adapter.setOnOutRangeListener(onOutRangeListener);
    }

    @Override
    public void setListViewOnCheckCompleteListener(Add24GAdapter.OnCheckCompleteListener onCheckCompleteListener) {
        adapter.setOnCheckCompleteListener(onCheckCompleteListener);
    }


    @Override
    public void onClick(View v) {
        finish();
    }
}

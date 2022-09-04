package com.example.nanlinkdemo.mvp.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.bean.RegisterUser;
import com.example.nanlinkdemo.databinding.FragmentBaseBinding;
import com.example.nanlinkdemo.mvp.adapter.SettingAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.SettingPresenterImpl;
import com.example.nanlinkdemo.mvp.presenter.Impl.UserSettingPresenterImpl;
import com.example.nanlinkdemo.mvp.view.SettingView;
import com.example.nanlinkdemo.mvp.view.UserSettingView;

import java.util.ArrayList;


public class SettingFragment extends Fragment implements SettingView {


    private SettingPresenterImpl presenter;
    private SettingAdapter adapter;
    private Context context;
    private FragmentBaseBinding binding;

    public SettingFragment() {
    }

    private static SettingFragment instance = new SettingFragment();

    public static SettingFragment getInstance() {
        if (instance == null){
            synchronized (SettingFragment.class){
                if (instance == null){
                    instance = new SettingFragment();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
    }

    @Override
    public void setPresenter() {
        presenter = new SettingPresenterImpl(this);
    }

    @Override
    public void showStories(ArrayList<Menu> settingList) {
        adapter.setData(settingList,null);

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        binding = FragmentBaseBinding.inflate(getLayoutInflater());
        initRecyclerView();
        return binding.getRoot();
    }

    private void initRecyclerView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 设置分割线格式并添加
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getContext().getDrawable(R.drawable.decoration_setting));
        binding.recycleView.addItemDecoration(decoration);

        adapter = new SettingAdapter();
        presenter.getSettingListFromView();
        binding.recycleView.setAdapter(adapter);
        adapter.setOnClickListener(new SettingAdapter.OnClickListener() {
            @Override
            public void onClick(String settingText) {

            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}

package com.example.nanlinkdemo.mvp.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.databinding.FragmentBaseBinding;
import com.example.nanlinkdemo.mvp.adapter.MenuAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.SceneListPresenterImpl;
import com.example.nanlinkdemo.mvp.view.SceneListView;

import java.util.ArrayList;


public class SceneListFragment extends Fragment implements SceneListView {


    private SceneListPresenterImpl presenter;
    private MenuAdapter menuAdapter;
    private Context context;
    private FragmentBaseBinding binding;
    private ArrayList<Menu> menuList;

    public SceneListFragment() {
    }

    private static SceneListFragment instance = new SceneListFragment();

    public static SceneListFragment getInstance() {
        if (instance == null){
            synchronized (SceneListFragment.class){
                if (instance == null){
                    instance = new SceneListFragment();
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
        presenter = new SceneListPresenterImpl(this);
    }

    @Override
    public void showStories(ArrayList<Menu> menuList) {
        menuAdapter.setData(menuList);

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
        menuAdapter = new MenuAdapter();
        presenter.getMenuFromView();
        binding.recycleView.setAdapter(menuAdapter);
        menuAdapter.setOnClickListener(new MenuAdapter.OnClickListener() {
            @Override
            public void onClick(String menuText) {
//                Intent intent = new Intent(getContext(), DetailActivity.class);
//                intent.putExtra("storyId",storyId);
//                context.startActivity(intent);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}

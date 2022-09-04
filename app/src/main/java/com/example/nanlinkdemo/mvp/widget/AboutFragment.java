package com.example.nanlinkdemo.mvp.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.FragmentWebviewBinding;
import com.example.nanlinkdemo.mvp.presenter.Impl.AboutPresenterImpl;
import com.example.nanlinkdemo.mvp.view.AboutView;



public class AboutFragment extends Fragment implements AboutView {


    private AboutPresenterImpl presenter;
    private Context context;
    private FragmentWebviewBinding binding;

    public AboutFragment() {
    }

    private static AboutFragment instance = new AboutFragment();

    public static AboutFragment getInstance() {
        if (instance == null) {
            synchronized (AboutFragment.class) {
                if (instance == null) {
                    instance = new AboutFragment();
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
        presenter = new AboutPresenterImpl(this);
    }



    @Override
    public void replaceFragment(Fragment oldFragment, Fragment newFragment) {
        ((MainActivity) instance.getActivity()).replaceFragment(oldFragment, newFragment);
    }

    @Override
    public void setToolbar(int leftBtnResId, String title, int rightBtnResId, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        ((MainActivity) instance.getActivity()).setToolbar(leftBtnResId, title, rightBtnResId,leftListener, rightListener);
    }

    @Override
    public void initToolbar() {
        ((MainActivity) instance.getActivity()).initToolbar();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        binding = FragmentWebviewBinding.inflate(getLayoutInflater());
        initWebView();
        return binding.getRoot();
    }

    @SuppressLint("ResourceAsColor")
    private void initWebView() {
        binding.webView.loadUrl("https://cdn-test.nanlink.com/nanlink_about_zh.html");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}

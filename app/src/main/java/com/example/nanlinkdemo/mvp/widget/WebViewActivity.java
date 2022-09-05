package com.example.nanlinkdemo.mvp.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.databinding.ActivityWebviewBinding;
import com.example.nanlinkdemo.mvp.presenter.Impl.WebViewPresenterImpl;
import com.example.nanlinkdemo.mvp.view.WebViewView;
import com.example.nanlinkdemo.util.Constant;

@Route(path = Constant.ACTIVITY_URL_WebView)
public class WebViewActivity extends BaseActivity<ActivityWebviewBinding> implements WebViewView {

    @Autowired(name = "contentId")
    int contentId;

    private WebViewPresenterImpl presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        presenter.SwitchContentId(contentId);
    }

    private void setPresenter() {
        presenter = new WebViewPresenterImpl(this);
    }


    @Override
    public void showMessage(String title, String url) {
        binding.toolbar.setTitle(title);
        binding.webView.loadUrl(url);
        binding.webView.setBackgroundColor(getResources().getColor(R.color.theme));
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}

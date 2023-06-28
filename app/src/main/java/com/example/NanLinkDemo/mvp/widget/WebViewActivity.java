package com.example.NanLinkDemo.mvp.widget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.ActivityWebviewBinding;
import com.example.NanLinkDemo.mvp.presenter.Impl.WebViewPresenterImpl;
import com.example.NanLinkDemo.mvp.view.WebViewView;
import com.example.NanLinkDemo.util.Constant;

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

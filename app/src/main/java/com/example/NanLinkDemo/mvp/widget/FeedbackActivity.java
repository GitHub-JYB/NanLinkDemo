package com.example.NanLinkDemo.mvp.widget;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.ActivityFeedbackBinding;
import com.example.NanLinkDemo.mvp.presenter.Impl.FeedbackPresenterImpl;
import com.example.NanLinkDemo.mvp.view.FeedbackView;
import com.example.NanLinkDemo.util.Constant;


@Route(path = Constant.ACTIVITY_URL_Feedback)
public class FeedbackActivity extends BaseActivity<ActivityFeedbackBinding> implements FeedbackView, View.OnClickListener {


    private FeedbackPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initBtnSubmit();
        initTextWatcher();
    }

    private void initTextWatcher() {
        binding.feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               presenter.checkText();
            }
        });
    }



    private void initBtnSubmit() {
        binding.btnSubmit.setOnClickListener(this);
    }

    private void initToolbar() {
        binding.toolbar.setTitle("意见反馈");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }

    public void setPresenter() {
        presenter = new FeedbackPresenterImpl(this);
    }


    @Override
    public void onClick(View v) {
        presenter.switchOnclick(v);
    }

    @Override
    public void updateBtnSubmit(boolean able) {
        binding.btnSubmit.setClickable(able);
        if (able){
            binding.btnSubmit.setBackgroundResource(R.drawable.bg_able_btn_login);
        }else {
            binding.btnSubmit.setBackgroundResource(R.drawable.bg_unable_btn_login);
        }
    }


    @Override
    public String getFeedback() {
        return binding.feedback.getText().toString().trim();
    }
}
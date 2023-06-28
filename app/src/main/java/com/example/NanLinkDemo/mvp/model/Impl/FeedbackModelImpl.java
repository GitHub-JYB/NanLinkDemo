package com.example.NanLinkDemo.mvp.model.Impl;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.bean.Feedback;
import com.example.NanLinkDemo.bean.Message;
import com.example.NanLinkDemo.mvp.model.FeedbackModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.FeedbackPresenterImpl;
import com.example.NanLinkDemo.util.ApiClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FeedbackModelImpl implements FeedbackModel {
    private final FeedbackPresenterImpl presenter;

    public FeedbackModelImpl(FeedbackPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void submitFeedback(String feedback) {
        Feedback fb = new Feedback();
        fb.setContent(feedback);
        ApiClient.getService(ApiClient.BASE_URL)
                .submitFeedback(MyApplication.getOnlineUser().getToken(), fb)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) throws Exception {
                        presenter.sendMesToView(message);
                    }
                });

    }
}

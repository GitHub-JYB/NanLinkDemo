package com.example.nanlinkdemo.mvp.presenter.Impl;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.mvp.model.Impl.SplashModelImpl;
import com.example.nanlinkdemo.mvp.presenter.SplashPresenter;
import com.example.nanlinkdemo.mvp.view.SplashView;
import com.example.nanlinkdemo.util.Constant;

import java.util.List;

public class SplashPresenterImpl implements SplashPresenter {
    private final SplashView view;
    private final SplashModelImpl model;
    private User onlineUser;

    public SplashPresenterImpl(SplashView view) {
        this.view = view;
        model = new SplashModelImpl(this);
    }

    @Override
    public void startCountDownFromModel() {
        model.startCountDown();
    }

    @Override
    public void endCountDown() {
        model.getOnlineUser();
    }

    @Override
    public void receiveOnlineUser(List<User> users) {
        if (!users.isEmpty()){
            onlineUser = users.get(0);
            updateUser(onlineUser);
        }else {
            ARouter.getInstance().build(Constant.ACTIVITY_URL_Login).navigation();
        }
        view.finish();
    }

    @Override
    public void sendMesToView(Message message) {
        switch (message.getCode()) {
            case 200:
                onlineUser.setNickName(message.getData().getNickName());
                onlineUser.setVocation(message.getData().getVocation());
                model.updateUser(onlineUser);
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Main).navigation();
                break;
            case 1001:
            case 1002:
            case 1003:
            case 1004:
            case 1005:
            case 1006:
            case 1007:
            case 1008:
            case 1009:
            case 1010:
            case 1011:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Main).navigation();
                break;
        }
    }

    private void updateUser(User user) {
        model.getUserInfo(user.getToken());
    }
}

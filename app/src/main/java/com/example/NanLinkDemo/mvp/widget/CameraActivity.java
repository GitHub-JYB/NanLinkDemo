package com.example.NanLinkDemo.mvp.widget;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.ActivityCameraBinding;
import com.example.NanLinkDemo.databinding.ActivityLoginBinding;
import com.example.NanLinkDemo.mvp.presenter.Impl.CameraPresenterImpl;
import com.example.NanLinkDemo.mvp.view.CameraView;
import com.example.NanLinkDemo.util.Constant;

@Route(path = Constant.ACTIVITY_URL_Camera)
public class CameraActivity extends BaseActivity<ActivityCameraBinding> implements CameraView, View.OnClickListener {

    @Autowired(name = "type")
    int type;

    @Autowired(name = "id")
    int id;

    private CameraPresenterImpl presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPresenter();
        initToolbar();
        initPreView();
        initControlData();
    }

    private void initControlData() {
        presenter.getControlDataFromModel(type, id);
    }


    private void initPreView() {
        ViewGroup.LayoutParams layoutParams = binding.preview.getLayoutParams();
        layoutParams.height = layoutParams.width;
        binding.preview.setLayoutParams(layoutParams);
        binding.preview.setBackgroundColor(getResources().getColor(R.color.blue));
    }


    private void initToolbar() {
        binding.toolbar.setTitle("颜色匹配");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
    }


    @Override
    public void setPresenter() {
        presenter = new CameraPresenterImpl(this);
    }




    @Override
    public void onClick(View view) {
        presenter.switchOnclick(view);
    }
}

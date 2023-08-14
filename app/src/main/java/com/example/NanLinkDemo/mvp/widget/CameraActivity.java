package com.example.NanLinkDemo.mvp.widget;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.ActivityCameraBinding;
import com.example.NanLinkDemo.databinding.ActivityLoginBinding;
import com.example.NanLinkDemo.mvp.presenter.Impl.CameraPresenterImpl;
import com.example.NanLinkDemo.mvp.view.CameraView;
import com.example.NanLinkDemo.util.Constant;

import java.io.IOException;

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
        initCamera();
        initControlData();
    }

    private void initCamera() {
        try {

            CameraManager cameraManager = (CameraManager) getBaseContext().getSystemService(Context.CAMERA_SERVICE);
            cameraManager.getCameraCharacteristics("0");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initControlData() {
        presenter.getControlDataFromModel(type, id);
    }


    private void initPreView() {
        ViewGroup.LayoutParams layoutParams = binding.preview.getLayoutParams();
        layoutParams.height = layoutParams.width = MyApplication.widthPixels;
        binding.preview.setLayoutParams(layoutParams);
        SurfaceHolder holder = binding.preview.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                try {
                    
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });
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


    @Override
    protected void onStop() {
        super.onStop();

    }
}

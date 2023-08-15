package com.example.NanLinkDemo.mvp.widget;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

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
import java.util.List;

@Route(path = Constant.ACTIVITY_URL_Camera)
public class CameraActivity extends BaseActivity<ActivityCameraBinding> implements CameraView, View.OnClickListener {

    @Autowired(name = "type")
    int type;

    @Autowired(name = "id")
    int id;

    private CameraPresenterImpl presenter;
    private Camera.CameraInfo cameraInfo;
    private Camera mCamera;
    private SurfaceHolder holder;

    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (data != null){
                Camera.Parameters parameters = camera.getParameters();
                Camera.Size size = parameters.getPreviewSize();
            }
            camera.addCallbackBuffer(data);
        }
    };


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
        holder = binding.preview.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                openCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                releaseCamera();
            }
        });
    }

    private void releaseCamera() {
        if (mCamera != null){
            mCamera.stopPreview();
            mCamera.stopFaceDetection();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private void openCamera(int cameraId) {
        try {
            releaseCamera();
            if (cameraInfo == null){
                cameraInfo = new Camera.CameraInfo();
            }
            int numberOfCameras = Camera.getNumberOfCameras();
            for (int i = 0; i < numberOfCameras; i++){
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing == cameraId){
                    mCamera = Camera.open(i);
                    break;
                }
            }

            startPreview();
        }catch (Exception e){
            e.printStackTrace();
            mCamera = null;
        }
    }

    private void startPreview() {
        try {
            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            int width = display.getWidth();
            int height = display.getHeight();

            initPreviewParams(width, height);

            adjustCameraOrientation();

            mCamera.setPreviewDisplay(holder);

            mCamera.addCallbackBuffer(new byte[width * height * 3 / 2]);

            mCamera.setPreviewCallbackWithBuffer(previewCallback);

            mCamera.startPreview();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void adjustCameraOrientation() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();

        int degress = 0;

        switch (rotation){
            case Surface.ROTATION_0:
                degress = 0;
                break;
            case Surface.ROTATION_90:
                degress = 90;
                break;
            case Surface.ROTATION_180:
                degress = 180;
                break;
            case Surface.ROTATION_270:
                degress = 270;
                break;
        }

        int result = 0;

        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
            result = (cameraInfo.orientation - degress + 360 ) % 360;
        }else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
            result = (cameraInfo.orientation + degress) % 360;
            result = (360 - result) % 360;
        }

        mCamera.setDisplayOrientation(result);
    }

    private void initPreviewParams(int width, int height) {
        if (mCamera != null){
            Camera.Parameters parameters = mCamera.getParameters();
            List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
            Camera.Size bestSize = getBestSize(width, height, sizes);
            parameters.setPreviewSize(bestSize.width, bestSize.height);
            parameters.setPictureSize(bestSize.width, bestSize.height);
            parameters.setPreviewFormat(ImageFormat.NV21);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            mCamera.setParameters(parameters);
        }
    }

    private Camera.Size getBestSize(int width, int height, List<Camera.Size> sizes) {
        Camera.Size bestSize = null;
        float uiRadio = (float) height / width;
        float minRadio = uiRadio;
        for (Camera.Size previewSize : sizes){
            float cameraRadio = (float) previewSize.width / previewSize.height;

            float offset = Math.abs(cameraRadio - minRadio);
            if (offset < minRadio){
                minRadio = offset;
                bestSize = previewSize;
            }

            if (uiRadio == cameraRadio){
                bestSize = previewSize;
                break;
            }
        }
        return bestSize;

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

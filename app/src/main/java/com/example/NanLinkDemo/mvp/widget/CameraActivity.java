package com.example.NanLinkDemo.mvp.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.databinding.ActivityCameraBinding;
import com.example.NanLinkDemo.mvp.presenter.Impl.CameraPresenterImpl;
import com.example.NanLinkDemo.mvp.view.CameraView;
import com.example.NanLinkDemo.util.ColorUtil;
import com.example.NanLinkDemo.util.Constant;

import java.io.ByteArrayOutputStream;
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
    private boolean isZoomIn;
    private int HSI, SAT;


    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (data != null) {
                Camera.Parameters parameters = camera.getParameters();
                Camera.Size size = parameters.getPreviewSize();
                YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                image.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, outputStream);
                byte[] bytes = outputStream.toByteArray();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                int color = bitmap.getPixel(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                float[] hsv = new float[3];
                Color.colorToHSV(color, hsv);
                updateData((int) hsv[0], (int) (hsv[1] * 100));
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
        initBtn();
    }

    private void initBtn() {
        binding.retry.setOnClickListener(this);
        binding.complete.setOnClickListener(this);
        binding.getPhoto.setOnClickListener(this);
        binding.changeView.setOnClickListener(this);
        binding.color.setClipToOutline(true);
        binding.color.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), MyApplication.dip2px(6));
            }
        });
    }

    @Override
    public void updateBtn(boolean isPreviewing) {
        if (isPreviewing) {
            binding.retry.setVisibility(View.GONE);
            binding.complete.setVisibility(View.GONE);
            binding.getPhoto.setClickable(true);
            binding.getPhoto.setImageDrawable(getResources().getDrawable(R.drawable.bg_able_camera));
            binding.changeView.setClickable(true);
            binding.changeView.setBackgroundResource(R.drawable.bg_able_btn_camera_zoom_able);
        } else {
            binding.retry.setVisibility(View.VISIBLE);
            binding.complete.setVisibility(View.VISIBLE);
            binding.getPhoto.setClickable(false);
            binding.getPhoto.setImageDrawable(getResources().getDrawable(R.drawable.bg_unable_camera));
            binding.changeView.setClickable(false);
            binding.changeView.setBackgroundResource(R.drawable.bg_able_btn_camera_zoom_unbale);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        initControlData();
    }

    private void initControlData() {
        presenter.getControlDataFromModel(type, id);
    }


    private void initPreView() {
        holder = binding.preview.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                openCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                try {
                    mCamera.setPreviewCallback(previewCallback);
                    mCamera.setPreviewDisplay(surfaceHolder);
                    mCamera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                releaseCamera();
            }
        });
    }

    private void updateData(int HSI, int SAT) {
        this.HSI = HSI;
        this.SAT = SAT;

        binding.color.setBackgroundColor(ColorUtil.HsiSatToColor(HSI, SAT));
        binding.HSI.setText(String.valueOf(HSI));
        binding.SAT.setText(SAT + "%");
    }

    private void releaseCamera() {
        if (mCamera != null) {
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
            if (cameraInfo == null) {
                cameraInfo = new Camera.CameraInfo();
            }
            int numberOfCameras = Camera.getNumberOfCameras();
            for (int i = 0; i < numberOfCameras; i++) {
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing == cameraId) {
                    mCamera = Camera.open(i);
                    break;
                }
            }

            startPreview();
        } catch (Exception e) {
            e.printStackTrace();
            mCamera = null;
        }
    }

    public void startPreview() {
        try {
//            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//            Display display = manager.getDefaultDisplay();
//            int width = display.getWidth();
//            int height = display.getHeight();

            int width = MyApplication.widthPixels;
            int height = MyApplication.heightPixels;

            initPreviewParams(width, height);

            adjustCameraOrientation();

            mCamera.setPreviewDisplay(holder);

            mCamera.addCallbackBuffer(new byte[width * height * 3 / 2]);

            mCamera.setPreviewCallbackWithBuffer(previewCallback);

            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toggleZoom() {
        isZoomIn = !isZoomIn;
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            if (parameters.isZoomSupported()) {
                int maxZoom = parameters.getMaxZoom();
                List<Integer> zoomRatios = parameters.getZoomRatios();
                if (isZoomIn) {
                    parameters.setZoom(maxZoom >= 10 ? 10 : maxZoom);
                    binding.changeView.setText("缩小");
                } else {
                    parameters.setZoom(0);
                    binding.changeView.setText("放大");
                }
                List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
                Camera.Size bestSize = getBestSize(MyApplication.widthPixels, MyApplication.heightPixels, sizes);
                parameters.setPreviewSize(bestSize.width, bestSize.height);
                parameters.setPreviewFormat(ImageFormat.NV21);
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                mCamera.setParameters(parameters);

            }
        }

    }

    @Override
    public int getHSI() {
        return HSI;
    }

    @Override
    public int getSAT() {
        return SAT;
    }

    private void adjustCameraOrientation() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();

        int degress = 0;

        switch (rotation) {
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

        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            result = (cameraInfo.orientation - degress + 360) % 360;
        } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (cameraInfo.orientation + degress) % 360;
            result = (360 - result) % 360;
        }

        mCamera.setDisplayOrientation(result);
    }

    private void initPreviewParams(int width, int height) {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
            Camera.Size bestSize = getBestSize(width, height, sizes);
            parameters.setPreviewSize(bestSize.width, bestSize.height);
            parameters.setPreviewFormat(ImageFormat.NV21);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            mCamera.setParameters(parameters);
        }
    }

    private Camera.Size getBestSize(int width, int height, List<Camera.Size> sizes) {
        Camera.Size bestSize = null;
        float uiRadio = (float) height / width;
        float minRadio = uiRadio;
        for (Camera.Size previewSize : sizes) {
            float cameraRadio = (float) previewSize.width / previewSize.height;

            float offset = Math.abs(cameraRadio - minRadio);
            if (offset < minRadio) {
                minRadio = offset;
                bestSize = previewSize;
            }

            if (uiRadio == cameraRadio) {
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
    public void stopPreview() {
        mCamera.stopPreview();
    }


    @Override
    public void onClick(View view) {
        presenter.switchOnclick(view);
    }


    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
    }
}

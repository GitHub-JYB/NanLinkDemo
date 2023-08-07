package com.example.NanLinkDemo.ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;

import java.util.ArrayList;


public class XYColorView extends View {

    private int X = 330200;
    private int Y = 340800;
    private OnDataChangeListener listener;
    private Bitmap colorBitmap, pointerBitmap;
    private float RedX, RedY, BlueX, BlueY, GreenX, GreenY;
    public static final float krb = (300000 - 70000) / (float) (680000 - 160000);
    public static final float kgb = (690000 - 70000) / (float) (190000 - 160000);
    public static final float kgr = (690000 - 300000) / (float) (190000 - 680000);
    private float unit;
    private boolean isShowCurve;
    private boolean isUpdate;

    public XYColorView(Context context) {
        this(context, null);
    }

    public XYColorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XYColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public XYColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(widthMeasureSpec);
        measuredHeight = measuredHeight > measuredWidth ? widthMeasureSpec : measuredHeight;
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint colorPaint = new Paint();
        if (colorBitmap == null) {
            colorBitmap = getColorBitmap();
        }
        canvas.drawBitmap(colorBitmap, (getWidth() - colorBitmap.getWidth()) / 2f + getPaddingLeft(), (getHeight() - colorBitmap.getHeight()) / 2f + getPaddingTop(), colorPaint);

        unit = (330 - 93 - 56) / 330f * colorBitmap.getWidth() / (610000 - 70000);
        RedX = getPaddingLeft() + colorBitmap.getWidth() * (330 - 93) / 330f + 70000 * unit;
        BlueY = getPaddingTop() + colorBitmap.getHeight() * (330 - 56) / 330f;

        RedY = BlueY - (300000 - 70000) * unit;
        BlueX = RedX - (680000 - 160000) * unit;
        GreenX = RedX - (680000 - 190000) * unit;
        GreenY = BlueY - (690000 - 70000) * unit;

        float pointX = (X - 160000) * unit + BlueX;
        float pointY = BlueY - (Y - 70000) * unit;

        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(getResources().getColor(R.color.black));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(MyApplication.dip2px(1));

//        canvas.drawLine(RedX, 0, RedX, colorBitmap.getHeight(), linePaint);
//        canvas.drawLine(BlueX, 0, BlueX, colorBitmap.getHeight(), linePaint);
//        canvas.drawLine(GreenX, 0, GreenX, colorBitmap.getHeight(), linePaint);
//        canvas.drawLine(0, RedY, colorBitmap.getHeight(), RedY, linePaint);
//        canvas.drawLine(0, BlueY, colorBitmap.getHeight(), BlueY, linePaint);
//        canvas.drawLine(0, GreenY, colorBitmap.getHeight(), GreenY, linePaint);


        canvas.drawLine(RedX, RedY, BlueX, BlueY, linePaint);
        canvas.drawLine(BlueX, BlueY, GreenX, GreenY, linePaint);
        canvas.drawLine(GreenX, GreenY, RedX, RedY, linePaint);


        Paint pointerPaint = new Paint();
        if (pointerBitmap == null) {
            pointerBitmap = getPointerBitmap();
        }

        canvas.drawBitmap(pointerBitmap, (pointX - (pointerBitmap.getWidth() / 2f)), (pointY - (pointerBitmap.getHeight() / 2f)), pointerPaint);

        if (isShowCurve) {
            Paint curvePaint = new Paint();
            curvePaint.setAntiAlias(true);
            curvePaint.setColor(getResources().getColor(R.color.black));
            curvePaint.setStyle(Paint.Style.STROKE);
            curvePaint.setStrokeWidth(MyApplication.dip2px(1));

            ArrayList<Double> xList = new ArrayList<>();
//            xList.add(0.585713587);
            xList.add(0.526677753);
            xList.add(0.476997683);
            xList.add(0.436936884);
            xList.add(0.405312009);
            xList.add(0.380450477);
            xList.add(0.360800679);
            xList.add(0.345116254);
            xList.add(0.3324511);
            xList.add(0.322101371);
            xList.add(0.313545233);
            xList.add(0.306394013);
            xList.add(0.300355781);
            xList.add(0.295209048);
            xList.add(0.290783965);
            xList.add(0.286948876);
            xList.add(0.283600665);
            xList.add(0.280657749);

            ArrayList<Double> yList = new ArrayList<>();
//            yList.add(0.393126202);
            yList.add(0.413299964);
            yList.add(0.413680112);
            yList.add(0.404082516);
            yList.add(0.390729182);
            yList.add(0.376765825);
            yList.add(0.36356902);
            yList.add(0.351637993);
            yList.add(0.341070191);
            yList.add(0.331793054);
            yList.add(0.323672244);
            yList.add(0.316560384);
            yList.add(0.310317725);
            yList.add(0.30481975);
            yList.add(0.299958856);
            yList.add(0.295643503);
            yList.add(0.291796401);
            yList.add(0.288352501);

            Path path = new Path();
            path.moveTo((float) (xList.get(0) * 1000000 - 160000) * unit + BlueX, BlueY - (float) (yList.get(0) * 1000000 - 70000) * unit);
            for (int i = 1; i < xList.size(); i++) {
                path.quadTo((float) (xList.get(i - 1) * 1000000 - 160000) * unit + BlueX, BlueY - (float) (yList.get(i - 1) * 1000000 - 70000) * unit, (float) (xList.get(i) * 1000000 - 160000) * unit + BlueX, BlueY - (float) (yList.get(i) * 1000000 - 70000) * unit);
            }
            canvas.drawPath(path, curvePaint);
        }
    }

    private Bitmap getPointerBitmap() {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_pointer);
        int w = MyApplication.dip2px(40);
        int h = MyApplication.dip2px(40);
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }


    private Bitmap getColorBitmap() {
        Drawable drawable = getResources().getDrawable(R.drawable.bg_color_xy);
//        int w = drawable.getIntrinsicWidth() - pointerDrawable.getIntrinsicWidth();
//        int h = drawable.getIntrinsicHeight() - pointerDrawable.getIntrinsicHeight();
        int w = getWidth() - getPaddingLeft() - getPaddingRight();
        int h = getHeight() - getPaddingTop() - getPaddingBottom();
        int r = Math.min(w, h);

        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(r, r, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, r, r);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (inRange(x, y)) {
                    updateData(x, y);
                }
                invalidate();
                if (x > RedX) {
                    isUpdate = false;
                } else {
                    isUpdate = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isUpdate) {
                    if (inRange(x, y)) {
                        updateData(x, y);
                    } else {
                        updateInRange(x, y);
                    }
                    invalidate();
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (listener != null) {
                    listener.onStopTrackingTouch(this);
                }
                break;
        }
        return true;
    }

    private void updateData(float x, float y) {
        X = (int) ((x - BlueX) / unit + 0.5f) + 160000;
        Y = 690000 - (int) ((y - GreenY) / unit + 0.5f) / 100 * 100;
        if (listener != null) {
            listener.onProgressChanged(this, X, Y);
        }
    }

    private void updateInRange(float x, float y) {
        float pointX, pointY;
        if (x < BlueX) {
            pointY = Math.min(BlueY, Math.max(y, GreenY));
            pointX = -(pointY - BlueY) / kgb + BlueX;
        } else {
            float kb = -(y - BlueY) / (x - BlueX);
            if (kb < krb) {
                pointX = Math.min(RedX, Math.max(x, BlueX));
                pointY = BlueY - krb * (pointX - BlueX);
            } else if (kb > kgb) {
                pointY = Math.min(BlueY, Math.max(y, GreenY));
                pointX = -(pointY - BlueY) / kgb + BlueX;
            } else {
                pointX = Math.min(RedX, Math.max(x, BlueX));
                pointY = RedY - kgr * (pointX - RedX);
            }
        }
        X = (int) ((pointX - BlueX) / unit + 0.5f) + 160000;
        Y = 690000 - (int) ((pointY - GreenY) / unit + 0.5f) / 100 * 100;
        if (listener != null) {
            listener.onProgressChanged(this, X, Y);
        }
    }

    private boolean inRange(float x, float y) {
        if (x < BlueX || x > RedX || y > BlueY || y < GreenY) {
            return false;
        } else {
            if (x <= GreenX) {
                return y <= BlueY - krb * (x - BlueX) && y >= BlueY - kgb * (x - BlueX);
            } else {
                return y <= BlueY - krb * (x - BlueX) && y >= RedY - kgr * (x - RedX);
            }
        }
    }


    public void setOnDataChangeListener(OnDataChangeListener listener) {
        this.listener = listener;
    }

    public void showCurve() {
        isShowCurve = true;
        invalidate();
    }

    public void hideCurve() {
        isShowCurve = false;
        invalidate();
    }

    public void setData(int x, int y) {
        X = x;
        Y = y;
        invalidate();
    }


    public interface OnDataChangeListener {
        void onProgressChanged(XYColorView xyColorView, int x, int y);

        void onStopTrackingTouch(XYColorView xyColorView);
    }

}

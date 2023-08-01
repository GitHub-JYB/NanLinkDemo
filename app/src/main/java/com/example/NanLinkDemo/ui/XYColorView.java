package com.example.NanLinkDemo.ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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


public class XYColorView extends View {

    private float pointX, pointY;
    private int X = 3302;
    private int Y = 3408;
    private OnDataChangeListener listener;
    private Bitmap colorBitmap, pointerBitmap;
    private float RedX, RedY, BlueX, BlueY, GreenX, GreenY;
    private float k1, k2, k3;
    private float kb, kr;
    private float unit;

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

        unit = (330 - 93 - 56) / 330f * colorBitmap.getWidth() / (6800 - 700);
        RedX = getPaddingLeft() + colorBitmap.getWidth() * (330 - 93) / 330f;
        BlueY = getPaddingTop() + colorBitmap.getHeight() * (330 - 56) / 330f;
        RedY = BlueY - (3000 - 700) * unit;
        BlueX = RedX - (6800 - 1600) * unit;
        GreenX = RedX - (6800 - 1900) * unit;
        GreenY = BlueY - (6900 - 700) * unit;
        k1 = -(RedY - BlueY) / (RedX - BlueX);
        k2 = -(GreenY - BlueY) / (GreenX - BlueX);
        k3 = -(GreenY - RedY) / (GreenX - RedX);
        pointX = (X - 1600) * unit + BlueX;
        pointY = (Y - 700) * unit + GreenY;

        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(getResources().getColor(R.color.black));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(MyApplication.dip2px(1));

        canvas.drawLine(RedX, RedY, BlueX, BlueY, linePaint);
        canvas.drawLine(BlueX, BlueY, GreenX, GreenY, linePaint);
        canvas.drawLine(GreenX, GreenY, RedX, RedY, linePaint);


        Paint pointerPaint = new Paint();
        if (pointerBitmap == null) {
            pointerBitmap = getPointerBitmap();
        }

        canvas.drawBitmap(pointerBitmap, (pointX - (pointerBitmap.getWidth() / 2f)), (pointY - (pointerBitmap.getHeight() / 2f)), pointerPaint);
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

        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        float x = event.getX();
        float y = event.getY();
        kb = -(event.getY() - BlueY) / (event.getX() - BlueX);
        kr = -(event.getY() - RedY) / (event.getX() - RedX);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (y > BlueY) {
                    pointX = Math.min(RedX, Math.max(x, BlueX));
                    pointY = BlueY - k1 * (pointX - BlueX);
                } else if (y < GreenY) {
                    if (x <= GreenX) {
                        pointX = Math.max(x, BlueX);
                        pointY = BlueY - k2 * (pointX - BlueX);
                    } else {
                        pointX = Math.min(x, RedX);
                        pointY = RedY - k3 * (pointX - RedX);
                    }
                } else if (x < BlueX) {
                    pointY = Math.min(BlueY, Math.max(y, GreenY));
                    pointX = (BlueY - pointY) / k2 + BlueX;
                } else if (x > RedX) {
                    if (y < RedY) {
                        pointY = Math.max(y, GreenY);
                        pointX = (RedY - pointY) / k3 + RedX;
                    } else {
                        pointY = Math.min(y, BlueY);
                        pointX = (RedY - pointY) / k1 + RedX;
                    }
                } else {
                    if (kb < k1) {
                        pointX = x;
                        pointY = BlueY - k1 * (pointX - BlueX);
                    } else if (kb > k2) {
                        pointY = y;
                        pointX = (BlueY - pointY) / k2 + BlueX;
                    } else {
                        if (kr < k3) {
                            pointY = y;
                            pointX = (RedY - pointY) / k3 + RedX;
                        } else {
                            pointX = x;
                            pointY = y;
                        }
                    }
                }
                X = (int) ((pointX - BlueX) / unit + 0.5f) + 1600;
                Y = (int)((pointY - GreenY) / unit + 0.5f) + 700;
                if (listener != null) {
                    listener.onProgressChanged(this, X, Y);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (listener != null) {
                    listener.onStopTrackingTouch(this);
                }
                break;
        }
        return true;
    }


    public void setOnDataChangeListener(OnDataChangeListener listener) {
        this.listener = listener;
    }

    public void showCurve() {

    }

    public void hideCurve() {

    }

    public interface OnDataChangeListener {
        void onProgressChanged(XYColorView xyColorView, int x, int y);

        void onStopTrackingTouch(XYColorView xyColorView);
    }

}

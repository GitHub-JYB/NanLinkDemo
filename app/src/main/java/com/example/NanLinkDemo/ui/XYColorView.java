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
    private int X = 330200;
    private int Y = 340800;
    private OnDataChangeListener listener;
    private Bitmap colorBitmap, pointerBitmap;
    private float RedX, RedY, BlueX, BlueY, GreenX, GreenY;
    public static final int krb = (300000 - 70000) / (680000 - 160000);
    public static final int kgb = (690000 - 70000) / (190000 - 160000);
    public static final int kgr = (690000 - 300000) / (190000 - 680000);
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

        unit = (330 - 93 - 56) / 330f * colorBitmap.getWidth() / (680000 - 70000);
        RedX = getPaddingLeft() + colorBitmap.getWidth() * (330 - 93) / 330f;
        BlueY = getPaddingTop() + colorBitmap.getHeight() * (330 - 56) / 330f;
        RedY = BlueY - (300000 - 70000) * unit;
        BlueX = RedX - (680000 - 160000) * unit;
        GreenX = RedX - (680000 - 190000) * unit;
        GreenY = BlueY - (690000 - 70000) * unit;

        pointX = (X - 160000) * unit + BlueX;
        pointY = (Y - 70000) * unit + GreenY;

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
                    pointY = BlueY - krb * (pointX - BlueX);
                } else if (y < GreenY) {
                    if (x <= GreenX) {
                        pointX = Math.max(x, BlueX);
                        pointY = BlueY - kgb * (pointX - BlueX);
                    } else {
                        pointX = Math.min(x, RedX);
                        pointY = RedY - kgr * (pointX - RedX);
                    }
                } else if (x < BlueX) {
                    pointY = Math.min(BlueY, Math.max(y, GreenY));
                    pointX = (BlueY - pointY) / kgb + BlueX;
                } else if (x > RedX) {
                    return false;
//                    if (y < RedY) {
//                        pointY = Math.max(y, GreenY);
//                        pointX = (RedY - pointY) / kgr + RedX;
//                    } else {
//                        pointY = Math.min(y, BlueY);
//                        pointX = (RedY - pointY) / krb + RedX;
//                    }
                } else {
                    if (kb < krb) {
                        pointX = x;
                        pointY = BlueY - krb * (pointX - BlueX);
                    } else if (kb > kgb) {
                        pointY = y;
                        pointX = (BlueY - pointY) / kgb + BlueX;
                    } else {
                        if (kr < kgr) {
                            pointY = y;
                            pointX = (RedY - pointY) / kgr + RedX;
                        } else {
                            pointX = x;
                            pointY = y;
                        }
                    }
                }
                X = (int) ((pointX - BlueX) / unit + 0.5f) + 160000;
                Y = (int)((pointY - GreenY) / unit + 0.5f) + 70000;
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

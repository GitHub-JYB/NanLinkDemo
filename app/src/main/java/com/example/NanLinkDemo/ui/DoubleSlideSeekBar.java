package com.example.NanLinkDemo.ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;


public class DoubleSlideSeekBar extends View {

    private int line_height = 2;
    private int max = 10;
    private int min = 0;
    private int minItem = 0;
    private int maxItem = 0;
    private boolean isClickMinItem;
    private int length;

    public DoubleSlideSeekBar(Context context) {
        this(context, null);
    }

    public DoubleSlideSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleSlideSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DoubleSlideSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        length = getWidth() - getPaddingRight() - getPaddingLeft();
        float strokeWidth = MyApplication.dip2px(4);
        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true); // 抗锯齿
        linePaint.setStyle(Paint.Style.STROKE); // 设置实心线
        linePaint.setStrokeCap(Paint.Cap.ROUND); // 圆角线
        linePaint.setStrokeWidth(strokeWidth); // 设置线宽
        linePaint.setColor(getResources().getColor(R.color.login_hintText));
        canvas.drawLine(0f + getPaddingLeft(), 0f + getHeight() / 2f, length + getPaddingLeft(), getHeight() / 2f, linePaint);

        Paint progressPaint = new Paint();
        progressPaint.setAntiAlias(true); // 抗锯齿
        progressPaint.setStyle(Paint.Style.STROKE); // 设置实心线
        progressPaint.setStrokeCap(Paint.Cap.ROUND); // 圆角线
        progressPaint.setStrokeWidth(strokeWidth); // 设置线宽
        progressPaint.setColor(getResources().getColor(R.color.able));
        canvas.drawLine((float) minItem / (max - min) * length + getPaddingLeft(), getHeight() / 2f, (float)maxItem / (max - min) * length + getPaddingLeft(), getHeight() / 2f, progressPaint);


        Paint minThumbPaint = new Paint();
        Paint maxThumbPaint = new Paint();

        Drawable normalDrawable = getResources().getDrawable(R.drawable.bg_selector_thumb_seekbar_on);
        Drawable clickDrawable = getResources().getDrawable(R.drawable.bg_selector_thumb_double_seekbar_on_click);


        Drawable minDrawable;
        Drawable maxDrawable;
        if (isClickMinItem){
            minDrawable = clickDrawable;
            maxDrawable = normalDrawable;
        }else {
            minDrawable = normalDrawable;
            maxDrawable = clickDrawable;
        }
        Bitmap minBitmap = drawableToBitmap(minDrawable);
        canvas.drawBitmap(minBitmap, (float) minItem / (max - min) * length + getPaddingLeft() - minBitmap.getWidth() / 2.0f, (getHeight() - minBitmap.getHeight()) / 2f, minThumbPaint);

        Bitmap maxBitmap = drawableToBitmap(maxDrawable);
        canvas.drawBitmap(maxBitmap, (float)maxItem / (max - min) * length + getPaddingLeft() - maxBitmap.getWidth() / 2.0f, (getHeight() - maxBitmap.getHeight()) / 2f, maxThumbPaint);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(x >= getPaddingLeft() && x <= getWidth() - getPaddingRight()){
                    if (Math.abs(x - (float) minItem / (max - min) * length + getPaddingLeft()) < Math.abs(x - (float) maxItem / (max - min) * length + getPaddingLeft())){
                        isClickMinItem = true;
                        minItem = (int) ((x - getPaddingLeft()) / length * (max - min));
                    }else {
                        isClickMinItem = false;
                        maxItem = (int) ((x - getPaddingLeft()) / length * (max - min));
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isClickMinItem) {
                    if(x >= getPaddingLeft() && x <= (float) maxItem / (max - min) * length + getPaddingLeft()){
                        minItem = (int) ((x - getPaddingLeft()) / length * (max - min));
                    }else if (x < getPaddingLeft()){
                        minItem = min;
                    }else if (x > (float) maxItem / (max - min) * length + getPaddingLeft()){
                        minItem = maxItem;
                    }
                }else {
                    if(x <= length + getPaddingLeft() && x >= (float) minItem / (max - min) * length + getPaddingLeft()){
                        maxItem = (int) ((x - getPaddingLeft()) / length * (max - min));
                    }else if (x > length + getPaddingLeft()){
                        maxItem = max;
                    }else if (x < (float) minItem / (max - min) * length + getPaddingLeft()){
                        maxItem = minItem;
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    public void setMax(int max){
        this.max = max;
        invalidate();

    }

    public void setProgress(int maxItem, int minItem){
        this.maxItem = Math.min(maxItem, max);
        this.minItem = Math.min(minItem, max);
        invalidate();
    }

}

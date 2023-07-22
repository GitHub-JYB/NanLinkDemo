package com.example.NanLinkDemo.ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;


public class DoubleSeekBar extends View {

    private int line_height = 2;
    private int max = 10;
    private int min = 0;
    private int minItem = 0;
    private int maxItem = 0;
    private boolean isClickMinItem;
    private boolean isOff;

    private int length;
    private Drawable clickDrawable;
    private OnDoubleSeekBarChangeListener listener;

    public DoubleSeekBar(Context context) {
        this(context, null);
    }

    public DoubleSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DoubleSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        if (isOff) {
            linePaint.setColor(getResources().getColor(R.color.dialog_input_text));
        } else {
            linePaint.setColor(getResources().getColor(R.color.login_hintText));
        }
        canvas.drawLine(0f + getPaddingLeft(), 0f + getHeight() / 2f, length + getPaddingLeft(), getHeight() / 2f, linePaint);

        Paint progressPaint = new Paint();
        progressPaint.setAntiAlias(true); // 抗锯齿
        progressPaint.setStyle(Paint.Style.STROKE); // 设置实心线
        progressPaint.setStrokeCap(Paint.Cap.ROUND); // 圆角线
        progressPaint.setStrokeWidth(strokeWidth); // 设置线宽
        if (isOff) {
            progressPaint.setColor(getResources().getColor(R.color.unable));
        } else {
            progressPaint.setColor(getResources().getColor(R.color.able));
        }
        canvas.drawLine((float) minItem / (max - min) * length + getPaddingLeft(), getHeight() / 2f, (float) maxItem / (max - min) * length + getPaddingLeft(), getHeight() / 2f, progressPaint);


        Paint minThumbPaint = new Paint();
        Paint maxThumbPaint = new Paint();

        Drawable offDrawable = getResources().getDrawable(R.drawable.bg_selector_thumb_seekbar_off);
        Drawable normalDrawable = getResources().getDrawable(R.drawable.bg_selector_thumb_seekbar_on);
        clickDrawable = getResources().getDrawable(R.drawable.bg_selector_thumb_double_seekbar_on_click);


        Drawable minDrawable;
        Drawable maxDrawable;
        if (isOff) {
            minDrawable = offDrawable;
            maxDrawable = offDrawable;
        } else {
            if (isClickMinItem) {
                minDrawable = clickDrawable;
                maxDrawable = normalDrawable;
            } else {
                minDrawable = normalDrawable;
                maxDrawable = clickDrawable;
            }
        }

        Bitmap minBitmap = drawableToBitmap(minDrawable);
        canvas.drawBitmap(minBitmap, (float) minItem / (max - min) * length + getPaddingLeft() - minBitmap.getWidth() / 2.0f, (getHeight() - minBitmap.getHeight()) / 2f, minThumbPaint);

        Bitmap maxBitmap = drawableToBitmap(maxDrawable);
        canvas.drawBitmap(maxBitmap, (float) maxItem / (max - min) * length + getPaddingLeft() - maxBitmap.getWidth() / 2.0f, (getHeight() - maxBitmap.getHeight()) / 2f, maxThumbPaint);
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
        getParent().requestDisallowInterceptTouchEvent(true);
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x >= getPaddingLeft() - clickDrawable.getIntrinsicWidth() / 2f && x <= getWidth() - getPaddingRight() + clickDrawable.getIntrinsicWidth() / 2f) {
                    if (Math.abs(x - ((float) minItem / (max - min) * length + getPaddingLeft())) < Math.abs(x - ((float) maxItem / (max - min) * length + getPaddingLeft()))) {
                        isClickMinItem = true;
                        if (Math.abs(x - ((float) minItem / (max - min) * length + getPaddingLeft())) > clickDrawable.getIntrinsicWidth()) {
                            minItem = (int) ((x - getPaddingLeft()) / length * (max - min) + 0.5f);
                            if (listener != null) {
                                listener.onProgressChanged(this, maxItem, minItem);
                            }
                        }
                    } else {
                        isClickMinItem = false;
                        if (Math.abs(x - ((float) maxItem / (max - min) * length + getPaddingLeft())) > clickDrawable.getIntrinsicWidth()) {
                            maxItem = (int) ((x - getPaddingLeft()) / length * (max - min) + 0.5f);
                            if (listener != null) {
                                listener.onProgressChanged(this, maxItem, minItem);
                            }
                        }
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isClickMinItem) {
                    if (x >= getPaddingLeft() && x <= (float) maxItem / (max - min) * length + getPaddingLeft()) {
                        minItem = (int) ((x - getPaddingLeft()) / length * (max - min) + 0.5f);
                        if (listener != null) {
                            listener.onProgressChanged(this, maxItem, minItem);
                        }
                    } else if (x < getPaddingLeft()) {
                        minItem = min;
                        if (listener != null){
                            listener.onProgressChanged(this, maxItem, minItem);
                        }
                    } else if (x > (float) maxItem / (max - min) * length + getPaddingLeft()) {
                        minItem = maxItem;
                        if (listener != null){
                            listener.onProgressChanged(this, maxItem, minItem);
                        }
                    }
                } else {
                    if (x <= length + getPaddingLeft() && x >= (float) minItem / (max - min) * length + getPaddingLeft()) {
                        maxItem = (int) ((x - getPaddingLeft()) / length * (max - min) + 0.5f);
                        if (listener != null) {
                            listener.onProgressChanged(this, maxItem, minItem);
                        }
                    } else if (x > length + getPaddingLeft()) {
                        maxItem = max;
                        if (listener != null){
                            listener.onProgressChanged(this, maxItem, minItem);
                        }
                    } else if (x < (float) minItem / (max - min) * length + getPaddingLeft()) {
                        maxItem = minItem;
                        if (listener != null){
                            listener.onProgressChanged(this, maxItem, minItem);
                        }
                    }
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

    public void setMax(int max) {
        this.max = max;
        invalidate();

    }

    public void setProgress(int maxItem, int minItem) {
        this.maxItem = Math.min(maxItem, max);
        this.minItem = Math.min(minItem, max);
        invalidate();
    }

    public void setOnDoubleSeekBarChangeListener(OnDoubleSeekBarChangeListener listener) {
        this.listener = listener;
    }

    public void setIsOff(boolean isOff) {
        this.isOff = isOff;
        invalidate();
    }

    public boolean getIsClickMinItem() {
        return isClickMinItem;
    }

    public void setIsClickMinItem(boolean isClickMinItem) {
        this.isClickMinItem = isClickMinItem;
    }

    public interface OnDoubleSeekBarChangeListener {
        void onProgressChanged(DoubleSeekBar doubleSlideSeekBar, int maxItem, int minItem);

        void onStopTrackingTouch(DoubleSeekBar doubleSlideSeekBar);
    }

}

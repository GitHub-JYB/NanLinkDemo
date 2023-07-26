package com.example.NanLinkDemo.ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;


public class XYColorView extends View {

    private float pointX = -1, pointY = -1;
    private OnDataChangeListener listener;
    private Bitmap colorBitmap, pointerBitmap;
    private float RedX, RedY, BlueX, BlueY, GreenX, GreenY;

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

        float unit = (330 - 93 - 56) / 330f * colorBitmap.getWidth() / (68 - 7);
        RedX = getPaddingLeft() + colorBitmap.getWidth() * (330 - 93) / 330f;
        BlueY = getPaddingTop() + colorBitmap.getHeight() * (330 - 56) / 330f;
        RedY = BlueY - (30 - 7) * unit;
        BlueX = RedX - (68 - 16) * unit;
        GreenX = RedX - (68 - 19) * unit;;
        GreenY = BlueY - (69 - 7) * unit;;
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

        if (pointX != -1 && pointY != -1) {
            canvas.drawBitmap(pointerBitmap, pointX - (pointerBitmap.getWidth() / 2f), pointY - (pointerBitmap.getHeight() / 2f), pointerPaint);
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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                updateData(event.getX(), event.getY());
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
        if (x >= BlueX && x <= RedX) {
            pointX = x;
        }else if (x < BlueX){
            pointX = BlueX;
        }else {
            pointX = RedX;
        }
        if (y <= BlueY && y >= GreenY) {
            pointY = y;
        }else if (y < GreenY){
            pointY = GreenY;
        }else {
            pointY = BlueY;
        }
        float[] hsl = new float[3];
        hsl[0] = (float) ((pointX - getPaddingLeft() - pointerBitmap.getWidth() * 0.5) / colorBitmap.getWidth() * 360);
        hsl[1] = (float) ((pointY - getPaddingTop() - pointerBitmap.getHeight() * 0.5) / colorBitmap.getHeight());
        hsl[2] = 0.5f + 0.5f - 0.5f * hsl[1];
        int color = ColorUtils.HSLToColor(hsl);
        int w = (int) ((1 - hsl[1]) * 255);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        if (listener != null){
            listener.onProgressChanged(this, r, g, b, w);
        }
        invalidate();
    }

    public void setOnDataChangeListener(OnDataChangeListener listener) {
        this.listener = listener;
    }

    public void clearPointer(){
        pointX = pointY = -1;
        invalidate();
    }

    public interface OnDataChangeListener {
        void onProgressChanged(XYColorView rgbwColorView, int r, int g, int b, int w);

        void onStopTrackingTouch(XYColorView rgbwColorView);
    }

}

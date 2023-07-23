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


public class HsiView extends View {

    private int HSI = 0;
    private int SAT = 100;

    private OnDataChangeListener listener;
    private int r;

    public HsiView(Context context) {
        this(context, null);
    }

    public HsiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HsiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HsiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint circlePaint = new Paint();
        Drawable colorDrawable = getResources().getDrawable(R.drawable.bg_color_hsi);
        Bitmap colorBitmap = Transparency(drawableToBitmap(colorDrawable));
        canvas.drawBitmap(colorBitmap, (getWidth() - colorBitmap.getWidth()) / 2, (getHeight() - colorBitmap.getHeight()) / 2, circlePaint);
        Paint pointerPaint = new Paint();
        Drawable pointerDrawable = getResources().getDrawable(R.drawable.ic_pointer);
        Bitmap pointerBitmap = drawableToBitmap(pointerDrawable);
        r = colorBitmap.getWidth() / 2;
        float pointX = (float) (r + getPaddingLeft() + (SAT / 100 * r * Math.cos((360 - HSI) * Math.PI * 2 / 360)));
        float pointY = (float) (r + getPaddingTop() + (SAT / 100 * r * Math.sin((360 - HSI) * Math.PI * 2 / 360)));


        canvas.drawBitmap(pointerBitmap, pointX - (pointerBitmap.getWidth() / 2), pointY - (pointerBitmap.getHeight() / 2), pointerPaint);
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


    private Bitmap Transparency(Bitmap bitmap) {
        int minX = bitmap.getWidth();
        int minY = bitmap.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                int alpha = (bitmap.getPixel(x, y) >> 24) & 255;
                if (alpha > 0) {
                    if (x < minX) {
                        minX = x;
                    }
                    if (x > maxX) {
                        maxX = x;
                    }
                    if (y < minY) {
                        minY = y;
                    }
                    if (y > maxY) {
                        maxY = y;
                    }
                }
            }
        }
        if ((maxX < minX) || (maxY < minY)) {
            return null;
        }
        return Bitmap.createBitmap(bitmap, minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                updateData(event.getX(), event.getY());
                break;
        }
        return true;
    }

    private void updateData(float x, float y) {
        double length = Math.sqrt(Math.pow(x - r - getPaddingLeft(), 2) + Math.pow(y - r - getPaddingTop(), 2));
        if (length > r) {
            return;
        }
        SAT = (int) (100 * length / r);
        if (x - r - getPaddingLeft() > 0){
            HSI = -(int) (Math.atan((y - r - getPaddingTop()) / (x - r - getPaddingLeft())) / Math.PI * 180);
            if (HSI < 0){
                HSI += 360;
            }
        }else {
            HSI = 180 - (int) (Math.atan((y - r - getPaddingTop()) / (x - r - getPaddingLeft())) / Math.PI * 180);
        }
        if (listener != null){
            listener.onProgressChanged(this, HSI, SAT);
        }
        invalidate();
    }

    public void setOnDataChangeListener(OnDataChangeListener listener) {
        this.listener = listener;
    }


    public interface OnDataChangeListener {
        void onProgressChanged(HsiView hsiView, int HSI, int SAT);

        void onStopTrackingTouch(HsiView hsiView);
    }

}

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


public class HsiColorView extends View {

    private int HSI = 0;
    private int SAT = 100;

    private OnDataChangeListener listener;
    private int r;
    private Bitmap colorBitmap, pointerBitmap;

    public HsiColorView(Context context) {
        this(context, null);
    }

    public HsiColorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HsiColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HsiColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        Paint circlePaint = new Paint();
        if (colorBitmap == null) {
            colorBitmap = getColorBitmap();
        }
        canvas.drawBitmap(colorBitmap, (getWidth() - colorBitmap.getWidth()) / 2f + getPaddingLeft(), (getHeight() - colorBitmap.getHeight()) / 2f + getPaddingTop(), circlePaint);


        Paint pointerPaint = new Paint();
        if (pointerBitmap == null) {
            pointerBitmap = getPointerBitmap();
        }
        r = colorBitmap.getWidth() / 2;
        float pointX = (float) (getWidth() / 2f + getPaddingLeft() + (SAT / 100.0 * r * Math.cos((360 - HSI) * Math.PI * 2 / 360)));
        float pointY = (float) (getHeight() / 2f + getPaddingTop() + (SAT / 100.0 * r * Math.sin((360 - HSI) * Math.PI * 2 / 360)));


        canvas.drawBitmap(pointerBitmap, pointX - (pointerBitmap.getWidth() / 2f), pointY - (pointerBitmap.getHeight() / 2f), pointerPaint);

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
        Drawable drawable = getResources().getDrawable(R.drawable.bg_color_hsi);
//        int w = drawable.getIntrinsicWidth() - pointerDrawable.getIntrinsicWidth();
//        int h = drawable.getIntrinsicHeight() - pointerDrawable.getIntrinsicHeight();
        int w = getWidth() - getPaddingLeft() - getPaddingRight() - MyApplication.dip2px(40);
        int h = getHeight() - getPaddingTop() - getPaddingBottom() - MyApplication.dip2px(40);

        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
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
        int r = Math.min((maxX - minX + 1), (maxY - minY) + 1) / 2;
        return Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2 - r, bitmap.getHeight() / 2 - r, 2 * r, 2 * r);
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
        double length = Math.sqrt(Math.pow(x - getWidth() / 2f - getPaddingLeft(), 2) + Math.pow(y - getHeight() / 2f - getPaddingTop(), 2));
        if (length > r) {
            SAT = 100;
        } else {
            SAT = (int) (100 * length / r);
        }
        if (x - getWidth() / 2f - getPaddingLeft() > 0) {
            HSI = -(int) (Math.atan((y - getHeight() / 2f - getPaddingTop()) / (x - getWidth() / 2f - getPaddingLeft())) / Math.PI * 180);
            if (HSI < 0) {
                HSI += 360;
            }
        } else {
            HSI = 180 - (int) (Math.atan((y - getHeight() / 2f - getPaddingTop()) / (x - getWidth() / 2f - getPaddingLeft())) / Math.PI * 180);
        }
        if (listener != null) {
            listener.onProgressChanged(this, HSI, SAT);
        }
        invalidate();
    }

    public void setOnDataChangeListener(OnDataChangeListener listener) {
        this.listener = listener;
    }

    public void setHSI(int HSI) {
        this.HSI = HSI;
        invalidate();
    }

    public void setSAT(int SAT) {
        this.SAT = SAT;
        invalidate();
    }



    public interface OnDataChangeListener {
        void onProgressChanged(HsiColorView hsiView, int HSI, int SAT);

        void onStopTrackingTouch(HsiColorView hsiView);
    }

}

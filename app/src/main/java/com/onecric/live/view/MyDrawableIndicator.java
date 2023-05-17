package com.onecric.live.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;

import com.onecric.live.R;
import com.youth.banner.indicator.BaseIndicator;

public class MyDrawableIndicator extends BaseIndicator{
    private Bitmap normalBitmap;
    private Bitmap selectedBitmap;
    public MyDrawableIndicator(Context context, @DrawableRes int normalResId, @DrawableRes int selectedResId) {
        super(context);
        normalBitmap = BitmapFactory.decodeResource(getResources(), normalResId);
        selectedBitmap = BitmapFactory.decodeResource(getResources(), selectedResId);
    }

    public MyDrawableIndicator(Context context) {
        this(context, null);
    }

    public MyDrawableIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDrawableIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawableIndicator);
//        if (a != null) {
//            BitmapDrawable normal = (BitmapDrawable) a.getDrawable(R.mipmap.img_indicator_1);
//            normalBitmap = normal.getBitmap();
//        }
        normalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_indicator_2);
        selectedBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_indicator_1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = config.getIndicatorSize();
        if (count <= 1) {
            return;
        }
        setMeasuredDimension(normalBitmap.getWidth() * (count - 1) +
                        selectedBitmap.getWidth() + config.getIndicatorSpace() * (count - 1),

                Math.max(normalBitmap.getHeight(), selectedBitmap.getHeight()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = config.getIndicatorSize();
        if (count <= 1 || normalBitmap == null || selectedBitmap == null) {
            return;
        }

        float left = 0;
        for (int i = 0; i < count; i++) {
            canvas.drawBitmap(config.getCurrentPosition() == i ? selectedBitmap : normalBitmap, left, 0, mPaint);
            left += (config.getCurrentPosition() == i ? selectedBitmap.getWidth() : normalBitmap.getWidth()) + config.getIndicatorSpace();
        }
    }
}

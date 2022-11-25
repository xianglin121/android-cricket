package com.longya.live.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.longya.live.R;
import com.longya.live.util.DpUtil;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/7
 */
public class ScoreProgressBar extends View {
    private Paint mBgPaint;
    private Paint mLeftPaint;
    private Paint mRightPaint;
    private RectF mBgRect;
    private Rect mLeftRect;
    private Rect mRightRect;
    private int mWidth;

    public ScoreProgressBar(Context context) {
        this(context, null);
    }

    public ScoreProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScoreProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setDither(true);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setColor(getContext().getResources().getColor(R.color.c_f5f5f5));

        mLeftPaint = new Paint();
        mLeftPaint.setAntiAlias(true);
        mLeftPaint.setDither(true);
        mLeftPaint.setStyle(Paint.Style.FILL);
        mLeftPaint.setColor(getContext().getResources().getColor(R.color.c_F84A4B));

        mRightPaint = new Paint();
        mRightPaint.setAntiAlias(true);
        mRightPaint.setDither(true);
        mRightPaint.setStyle(Paint.Style.FILL);
        mRightPaint.setColor(getContext().getResources().getColor(R.color.c_687AE0));

        mBgRect = new RectF();
        mLeftRect = new Rect();
        mRightRect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mBgRect.left = 0;
        mBgRect.right = w;
        mBgRect.top = 0;
        mBgRect.bottom = h;
        mLeftRect.left = w/2;
        mLeftRect.right = w/2;
        mLeftRect.top = 0;
        mLeftRect.bottom = h;
        mRightRect.top = 0;
        mRightRect.left = w/2;
        mRightRect.right = w/2;
        mRightRect.bottom = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mBgRect, DpUtil.dp2px(5), DpUtil.dp2px(5), mBgPaint);
        canvas.drawRect(mLeftRect, mLeftPaint);
        canvas.drawRect(mRightRect, mRightPaint);
    }

    public void setProgress(int leftColor, int rightColor, float leftRate, float rightRate) {
        mLeftPaint.setColor(leftColor);
        mRightPaint.setColor(rightColor);
        int leftBound = (int) (mWidth/2 * leftRate);
        int rightBound = (int) (mWidth/2 * rightRate);
        mLeftRect.left = mWidth/2 - leftBound;
        mRightRect.right = mWidth/2 + rightBound;
        if (leftRate > rightRate) {
            mRightPaint.setColor(getContext().getResources().getColor(R.color.c_4D687AE0));
        }else if (leftRate < rightRate) {
            mLeftPaint.setColor(getContext().getResources().getColor(R.color.c_4DF84A4B));
        }
        invalidate();
    }
}

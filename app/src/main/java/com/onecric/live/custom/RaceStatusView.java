package com.onecric.live.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.onecric.live.R;
import com.onecric.live.model.FootballTypeBean;
import com.onecric.live.model.FootballTypeInnerBean;
import com.onecric.live.util.DpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/16
 */
public class RaceStatusView extends View {
    private Paint mPaint = new Paint();
    private int mTextHeight = 0;//文字高度
    private String[] strs = {"0'", "15'", "30'", "HT", "60'", "75'", "90'"};

    private Paint mRedPaint = new Paint();
    private Paint mBluePaint = new Paint();
    private int mMaxRectHeight = 0;//红蓝方每个矩形最大高度
    private float mMaxValues = 100;//红蓝方可达最大值
//    private int[] reds = {3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8, 3, 2, 5, 7, 8};
//    private int[] blues = {2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5, 2, 6, 5, 2, 5};
    private List<FootballTypeBean> mList;

    public RaceStatusView(Context context) {
        this(context, null);
    }

    public RaceStatusView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RaceStatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mList = new ArrayList<>();
        mTextHeight = DpUtil.dp2px(10);
        mMaxRectHeight = DpUtil.dp2px(20);

        mPaint.setAntiAlias(true);
        mPaint.setTextSize(DpUtil.dp2px(10));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(getContext().getResources().getColor(R.color.c_999999));

        mRedPaint.setAntiAlias(true);
        mRedPaint.setColor(getContext().getResources().getColor(R.color.c_F84A4B));

        mBluePaint.setAntiAlias(true);
        mBluePaint.setColor(getContext().getResources().getColor(R.color.c_687AE0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        float leftTextWidth = 0;
        float rightTextWidth = 0;
        //绘制文字
//        leftTextWidth = mPaint.measureText(strs[0]);
//        rightTextWidth = mPaint.measureText(strs[strs.length-1]);
        leftTextWidth = DpUtil.dp2px(10);
        rightTextWidth = DpUtil.dp2px(10);
        //每个区间宽度
        float interval = (width-(leftTextWidth/2)-(rightTextWidth/2))/6;
        for (int i = 0; i < strs.length; i++) {
            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(DpUtil.dp2px(10));
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setColor(getContext().getResources().getColor(R.color.c_999999));
            float textWidth = mPaint.measureText(strs[i]);
            if (i > 0) {
                if (i == strs.length-1) {
                    canvas.drawText(strs[i], i*interval-(rightTextWidth/2), mTextHeight, mPaint);
                }else {
                    canvas.drawText(strs[i], i*interval, mTextHeight, mPaint);
                }
            }else {
                canvas.drawText(strs[i], i*interval+(leftTextWidth/2), mTextHeight, mPaint);
            }
            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(DpUtil.dp2px(1));
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(getContext().getResources().getColor(R.color.c_D9D9D9));
            if (i > 0) {
                if (i == strs.length-1) {
                    canvas.drawLine(i*interval-(textWidth/2),
                            mTextHeight*2+DpUtil.dp2px(8),
                            i*interval-(textWidth/2),
                            mTextHeight*2+DpUtil.dp2px(8) + DpUtil.dp2px(57), mPaint);
                }else {
                    canvas.drawLine(i*interval,
                            mTextHeight*2+DpUtil.dp2px(8),
                            i*interval,
                            mTextHeight*2+DpUtil.dp2px(8) + DpUtil.dp2px(57), mPaint);
                }
            }else {
                canvas.drawLine(i*interval+(textWidth/2),
                        mTextHeight*2+DpUtil.dp2px(8),
                        i*interval+(textWidth/2),
                        mTextHeight*2+DpUtil.dp2px(8) + DpUtil.dp2px(57), mPaint);
            }
        }

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getContext().getResources().getColor(R.color.c_FAFAFA));
        canvas.drawRect(leftTextWidth/2, mTextHeight*2+DpUtil.dp2px(25), (strs.length-1)*interval-(rightTextWidth/2), mTextHeight*2+DpUtil.dp2px(65), mPaint);

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getContext().getResources().getColor(R.color.c_EDEDED));
        canvas.drawRect(leftTextWidth/2, mTextHeight*2+DpUtil.dp2px(35), (strs.length-1)*interval-(rightTextWidth/2), mTextHeight*2+DpUtil.dp2px(55), mPaint);

        //绘制红方矩形
//        float rectWidth = (width-(leftTextWidth/2)-(rightTextWidth/2))/93-(DpUtil.dp2px(0.2f));//每个矩形宽度
//        for (int i = 0; i < reds.length; i++) {
//            float currHeight = reds[i]/mMaxValues*mMaxRectHeight;
//            if (i == 0) {
//                canvas.drawRect(leftTextWidth/2, mTextHeight*2+DpUtil.dp2px(25)+(mMaxRectHeight-currHeight), (leftTextWidth/2)+rectWidth, mTextHeight*2+DpUtil.dp2px(45), mRedPaint);
//            }else {
//                canvas.drawRect((leftTextWidth/2)+(i*(rectWidth+(DpUtil.dp2px(0.2f)))), mTextHeight*2+DpUtil.dp2px(25)+(mMaxRectHeight-currHeight), (leftTextWidth/2)+(i*(rectWidth+(DpUtil.dp2px(0.2f))))+rectWidth, mTextHeight*2+DpUtil.dp2px(45), mRedPaint);
//            }
//        }
//        //绘制蓝方矩形
//        for (int i = 0; i < blues.length; i++) {
//            float currHeight = blues[i]/mMaxValues*mMaxRectHeight;
//            if (i == 0) {
//                canvas.drawRect(leftTextWidth/2, mTextHeight*2+DpUtil.dp2px(45), (leftTextWidth/2)+rectWidth, mTextHeight*2+DpUtil.dp2px(45)+currHeight, mBluePaint);
//            }else {
//                canvas.drawRect((leftTextWidth/2)+(i*(rectWidth+(DpUtil.dp2px(0.2f)))), mTextHeight*2+DpUtil.dp2px(45), (leftTextWidth/2)+(i*(rectWidth+(DpUtil.dp2px(0.2f))))+rectWidth, mTextHeight*2+DpUtil.dp2px(45)+currHeight, mBluePaint);
//            }
//        }
        //绘制矩形
        float rectWidth = (width-(leftTextWidth/2)-(rightTextWidth/2)-DpUtil.dp2px(9))/90-DpUtil.dp2px(0.2f);//每个矩形宽度
        for (int i = 0; i < mList.size(); i++) {
            if (i >= 90) {
                break;
            }
            float currHeight = Math.abs(mList.get(i).getNum()) / mMaxValues*mMaxRectHeight;
            //柱形图
            if (mList.get(i).getNum() > 0) {
                if (i == 0) {
                    canvas.drawRect(leftTextWidth / 2, mTextHeight * 2 + DpUtil.dp2px(25) + (mMaxRectHeight - currHeight), (leftTextWidth / 2) + rectWidth, mTextHeight * 2 + DpUtil.dp2px(45), mRedPaint);
                } else {
                    canvas.drawRect((leftTextWidth / 2) + (i * (rectWidth + (DpUtil.dp2px(0.2f)))), mTextHeight * 2 + DpUtil.dp2px(25) + (mMaxRectHeight - currHeight), (leftTextWidth / 2) + (i * (rectWidth + (DpUtil.dp2px(0.2f)))) + rectWidth, mTextHeight * 2 + DpUtil.dp2px(45), mRedPaint);
                }
            }else {
                if (i == 0) {
                    canvas.drawRect(leftTextWidth/2, mTextHeight*2+DpUtil.dp2px(45), (leftTextWidth/2)+rectWidth, mTextHeight*2+DpUtil.dp2px(45)+currHeight, mBluePaint);
                }else {
                    canvas.drawRect((leftTextWidth/2)+(i*(rectWidth+(DpUtil.dp2px(0.2f)))), mTextHeight*2+DpUtil.dp2px(45), (leftTextWidth/2)+(i*(rectWidth+(DpUtil.dp2px(0.2f))))+rectWidth, mTextHeight*2+DpUtil.dp2px(45)+currHeight, mBluePaint);
                }
            }
            //事件
            List<FootballTypeInnerBean> event = mList.get(i).getEvent();
            if (event != null && event.size() > 0) {
                for (int j = 0; j < event.size(); j++) {
                    Bitmap bitmap = getIcon(event.get(j).getType());
                    if (bitmap != null) {
                        if (i == 0) {
                            if (event.get(j).getPosition() == 1) {//主队的事件
                                float left = leftTextWidth / 2 + (j * DpUtil.dp2px(3));
                                canvas.drawBitmap(bitmap, left, mTextHeight*2+DpUtil.dp2px(8)-(j*DpUtil.dp2px(3)), mRedPaint);
                            }else {//客队的事件
                                float left = leftTextWidth / 2 + (j * DpUtil.dp2px(3));
                                canvas.drawBitmap(bitmap, left, mTextHeight*2+DpUtil.dp2px(68)+(j*DpUtil.dp2px(3)), mBluePaint);
                            }
                        }else {
                            if (event.get(j).getPosition() == 1) {//主队的事件
                                float left = (leftTextWidth / 2) + (i * (rectWidth + (DpUtil.dp2px(0.2f)))) + (j * DpUtil.dp2px(3));
                                canvas.drawBitmap(bitmap, left, mTextHeight*2+DpUtil.dp2px(8)-(j*DpUtil.dp2px(3)), mRedPaint);
                            }else {
                                float left = (leftTextWidth / 2) + (i * (rectWidth + (DpUtil.dp2px(0.2f)))) + (j * DpUtil.dp2px(3));
                                canvas.drawBitmap(bitmap, left, mTextHeight*2+DpUtil.dp2px(68)+(j*DpUtil.dp2px(3)), mBluePaint);
                            }
                        }
                    }
                }
            }
        }
    }

    public void setData(List<FootballTypeBean> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        mList = list;
        invalidate();
    }

    private Bitmap getIcon(int type) {
        Bitmap bitmap = null;
        switch (type) {
            case 1://进球
                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon_goal);
                break;
            case 2://角球
                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon_corner_kick);
                break;
            case 3://黄牌
                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon_yellow_card_2);
                break;
            case 4://红牌
                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon_red_card_2);
                break;
            case 8://点球
                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon_penalty_kick);
                break;
            case 9://换人
                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon_substitution);
                break;
            case 15://两黄变红
                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon_two_yellows_sent_off);
                break;
            case 16://点球未进
                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon_missed_penalty);
                break;
            case 17://乌龙球
                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon_own_goals);
                break;
        }
        return bitmap;
    }
}

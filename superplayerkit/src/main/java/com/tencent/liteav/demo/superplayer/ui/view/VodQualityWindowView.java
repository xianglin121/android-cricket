package com.tencent.liteav.demo.superplayer.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tencent.liteav.demo.superplayer.R;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/23
 */
public class VodQualityWindowView extends RelativeLayout implements View.OnClickListener {
    private Context mContext;
    private TextView tv_quality_one;
    private TextView tv_quality_two;
    private TextView tv_quality_three;
    private TextView tv_quality_four;

    public VodQualityWindowView(Context context) {
        super(context);
        init(context);
    }

    public VodQualityWindowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VodQualityWindowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.superplayer_vod_quality_window, this);
        tv_quality_one = findViewById(R.id.tv_quality_one);
        tv_quality_two = findViewById(R.id.tv_quality_two);
        tv_quality_three = findViewById(R.id.tv_quality_three);
        tv_quality_four = findViewById(R.id.tv_quality_four);

        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.tv_quality_one).setOnClickListener(this);
        findViewById(R.id.tv_quality_two).setOnClickListener(this);
        findViewById(R.id.tv_quality_three).setOnClickListener(this);
        findViewById(R.id.tv_quality_four).setOnClickListener(this);

        tv_quality_one.setSelected(true);
    }

    public void updateQuality(int type) {
        if (type == 0) {
            tv_quality_one.setSelected(true);
            tv_quality_one.setTextColor(Color.parseColor("#DC3C23"));
            tv_quality_two.setSelected(false);
            tv_quality_two.setTextColor(Color.parseColor("#FFFFFF"));
            tv_quality_three.setSelected(false);
            tv_quality_three.setTextColor(Color.parseColor("#FFFFFF"));
            tv_quality_four.setSelected(false);
            tv_quality_four.setTextColor(Color.parseColor("#FFFFFF"));
        }else if (type == 1) {
            tv_quality_one.setSelected(false);
            tv_quality_one.setTextColor(Color.parseColor("#FFFFFF"));
            tv_quality_two.setSelected(true);
            tv_quality_two.setTextColor(Color.parseColor("#DC3C23"));
            tv_quality_three.setSelected(false);
            tv_quality_three.setTextColor(Color.parseColor("#FFFFFF"));
            tv_quality_four.setSelected(false);
            tv_quality_four.setTextColor(Color.parseColor("#FFFFFF"));
        }else if (type == 2) {
            tv_quality_one.setSelected(false);
            tv_quality_one.setTextColor(Color.parseColor("#FFFFFF"));
            tv_quality_two.setSelected(false);
            tv_quality_two.setTextColor(Color.parseColor("#FFFFFF"));
            tv_quality_three.setSelected(true);
            tv_quality_three.setTextColor(Color.parseColor("#DC3C23"));
            tv_quality_four.setSelected(false);
            tv_quality_four.setTextColor(Color.parseColor("#FFFFFF"));
        }else if (type == 3) {
            tv_quality_one.setSelected(false);
            tv_quality_one.setTextColor(Color.parseColor("#FFFFFF"));
            tv_quality_two.setSelected(false);
            tv_quality_two.setTextColor(Color.parseColor("#FFFFFF"));
            tv_quality_three.setSelected(false);
            tv_quality_three.setTextColor(Color.parseColor("#FFFFFF"));
            tv_quality_four.setSelected(true);
            tv_quality_four.setTextColor(Color.parseColor("#DC3C23"));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_close) {
            if (mCallBack != null) {
                mCallBack.onClose();
            }
        }else if (v.getId() == R.id.tv_quality_one) {
            if (mCallBack != null) {
                mCallBack.onQualityChange(0);
            }
        }else if (v.getId() == R.id.tv_quality_two) {
            if (mCallBack != null) {
                mCallBack.onQualityChange(1);
            }
        }else if (v.getId() == R.id.tv_quality_three) {
            if (mCallBack != null) {
                mCallBack.onQualityChange(2);
            }
        }else if (v.getId() == R.id.tv_quality_four) {
            if (mCallBack != null) {
                mCallBack.onQualityChange(3);
            }
        }
    }

    private CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public interface CallBack {
        void onClose();

        void onQualityChange(int type);
    }
}

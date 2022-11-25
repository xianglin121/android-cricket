package com.tencent.liteav.demo.superplayer.ui.view;

import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tencent.liteav.demo.superplayer.R;

import java.math.BigDecimal;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/23
 */
public class WindowDanmuSettingView extends RelativeLayout implements View.OnClickListener {
    private Context mContext;
    private ImageView iv_small;
    private ImageView iv_medium;
    private ImageView iv_big;
    private SeekBar mSeekBarAlpha;
    private TextView tv_alpha;

    public WindowDanmuSettingView(Context context) {
        super(context);
        init(context);
    }

    public WindowDanmuSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WindowDanmuSettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.superplayer_danmu_setting_window, this);
        iv_small = findViewById(R.id.iv_small);
        iv_medium = findViewById(R.id.iv_medium);
        iv_big = findViewById(R.id.iv_big);
        mSeekBarAlpha = findViewById(R.id.superplayer_sb_alpha);
        tv_alpha = findViewById(R.id.tv_alpha);

        findViewById(R.id.ll_small).setOnClickListener(this);
        findViewById(R.id.ll_medium).setOnClickListener(this);
        findViewById(R.id.ll_big).setOnClickListener(this);
        mSeekBarAlpha.setOnSeekBarChangeListener(mAlphaChangeListener);

        iv_medium.setSelected(true);
        mSeekBarAlpha.setProgress(100);
    }

    private SeekBar.OnSeekBarChangeListener mAlphaChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                updateAlphaProgress(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void updateAlphaProgress(int progress) {
        float percentage = (float) progress / mSeekBarAlpha.getMax();

        if (percentage < 0 || percentage > 1)
            return;

        tv_alpha.setText(progress + "%");

        if (mCallBack != null) {
            mCallBack.OnAlphaChange(percentage);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_small) {
            if (iv_small.isSelected()) {
                return;
            }
            iv_small.setSelected(true);
            iv_medium.setSelected(false);
            iv_big.setSelected(false);
            if (mCallBack != null) {
                mCallBack.OnChooseTextSize(10f);
            }
        }else if (v.getId() == R.id.ll_medium) {
            if (iv_medium.isSelected()) {
                return;
            }
            iv_small.setSelected(false);
            iv_medium.setSelected(true);
            iv_big.setSelected(false);
            if (mCallBack != null) {
                mCallBack.OnChooseTextSize(12f);
            }
        }else if (v.getId() == R.id.ll_big) {
            if (iv_big.isSelected()) {
                return;
            }
            iv_small.setSelected(false);
            iv_medium.setSelected(false);
            iv_big.setSelected(true);
            if (mCallBack != null) {
                mCallBack.OnChooseTextSize(14f);
            }
        }
    }

    private CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public interface CallBack {
        void OnChooseTextSize(float size);

        void OnAlphaChange(float alpha);
    }
}

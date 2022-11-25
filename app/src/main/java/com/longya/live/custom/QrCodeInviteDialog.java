package com.longya.live.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.longya.live.R;
import com.longya.live.adapter.FootballGoalAdapter;
import com.longya.live.model.MatchSocketBean;
import com.longya.live.util.DialogUtil;
import com.longya.live.util.DownloadUtil;
import com.longya.live.util.GlideUtil;
import com.longya.live.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/3
 */
public class QrCodeInviteDialog extends Dialog {
    private Context mContext;
    private ImageView iv_qrcode;
    private String mImg;

    public QrCodeInviteDialog(@NonNull Context context, int themeResId, String img, CallBack callBack) {
        super(context, themeResId);
        this.mContext = context;
        mImg = img;
        mCallBack = callBack;
        initDialog();
    }

    private void initDialog() {
        setContentView(R.layout.dialog_qrcode_invite);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        iv_qrcode = findViewById(R.id.iv_qrcode);

        GlideUtil.loadImageDefault(getContext(), mImg, iv_qrcode);
        findViewById(R.id.tv_send).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mCallBack != null) {
                    mCallBack.onSend();
                }
                return false;
            }
        });
        findViewById(R.id.tv_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    mCallBack.onCopy();
                }
            }
        });
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    private CallBack mCallBack;

    public interface CallBack {
        void onSend();

        void onCopy();
    }
}

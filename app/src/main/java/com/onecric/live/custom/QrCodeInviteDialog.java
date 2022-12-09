package com.onecric.live.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.onecric.live.R;
import com.onecric.live.util.GlideUtil;

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

package com.longya.live.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.CustomMsgBean;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class MatchChatAdapter extends BaseQuickAdapter<MessageInfo, BaseViewHolder> {
    public MatchChatAdapter(int layoutResId, @Nullable List<MessageInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageInfo item) {
        TextView tv_content = helper.getView(R.id.tv_content);
        String str = "";
        String nickName = "";
        if (!TextUtils.isEmpty(item.getNickName())) {
            nickName = item.getNickName() + "：";
            str = nickName;
        }
        String content = "";
        if (item.getExtra() != null) {
            if (item.getExtra().toString().startsWith("{") && item.getExtra().toString().endsWith("}")) {
                CustomMsgBean customMsgBean = JSONObject.parseObject(item.getExtra().toString(), CustomMsgBean.class);
                if (customMsgBean.getType() == MessageInfo.MSG_TYPE_BG_DANMU){
                    if (customMsgBean.getNormal() != null) {
                        if (!TextUtils.isEmpty(String.valueOf(customMsgBean.getNormal().getText()))) {
                            content = String.valueOf(customMsgBean.getNormal().getText());
                        }
                    }
                }else {
                    if (!TextUtils.isEmpty(String.valueOf(item.getExtra()))) {
                        content = String.valueOf(item.getExtra());
                    }
                }
            }else {
                if (!TextUtils.isEmpty(String.valueOf(item.getExtra()))) {
                    content = String.valueOf(item.getExtra());
                }
            }
        }
        str = str + content;
        SpannableStringBuilder msg = FaceManager.handlerEmojiText(str);
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#2C9CED"));
        msg.setSpan(span, 0, nickName.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_content.setText(msg);
    }
}

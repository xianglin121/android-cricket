package com.longya.live.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.CustomMsgBean;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class LiveChatAdapter extends BaseQuickAdapter<MessageInfo, BaseViewHolder> {
    public LiveChatAdapter(int layoutResId, @Nullable List<MessageInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageInfo item) {
        TextView tv_content = helper.getView(R.id.tv_content);
        if (!TextUtils.isEmpty(item.getSystemNotice())) {
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(item.getSystemNotice());
            stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FC3838")), 0, item.getSystemNotice().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            tv_content.setText(stringBuilder);
        }else {
            String str = "";
            boolean isAnchor = false;//是否是主播
            Bitmap nobleBitmap = null;//贵族图标
            Bitmap expBitmap = null;//等级图标
            String nickName = "";
            if (!TextUtils.isEmpty(item.getNickName())) {
                nickName = item.getNickName() + "：";
                str = nickName;
            }
            String content = "";
            int contentColor =  0;
            if (item.getExtra() != null) {
                expBitmap = item.getExpIcon();
                nobleBitmap = item.getNobleIcon();
                if (item.getExtra().toString().startsWith("{") && item.getExtra().toString().endsWith("}")) {
                    CustomMsgBean customMsgBean = JSONObject.parseObject(item.getExtra().toString(), CustomMsgBean.class);
                    if (customMsgBean.getType() == MessageInfo.MSG_TYPE_COLOR_DANMU) {
                        isAnchor = customMsgBean.getColor().getIs_room() == 1?true:false;
                        if (!TextUtils.isEmpty(customMsgBean.getColor().getText())) {
                            content = customMsgBean.getColor().getText();
                            contentColor = Color.parseColor(customMsgBean.getColor().getColor());
                        }else {
                            content = "";
                        }
                    }else if (customMsgBean.getType() == MessageInfo.MSG_TYPE_NOBEL_ENTER) {
                        isAnchor = customMsgBean.getNobel().getIs_room() == 1?true:false;
                        content = mContext.getString(R.string.enter_the_chat_room);
                        contentColor = Color.parseColor("#F15C43");
                    }else if (customMsgBean.getType() == MessageInfo.MSG_TYPE_GIFT) {
                        isAnchor = customMsgBean.getGift().getIs_room() == 1?true:false;
                        if (customMsgBean.getGift() != null) {
                            if (!TextUtils.isEmpty(customMsgBean.getGift().getGiftname())) {
                                content = "送给主播 " + customMsgBean.getGift().getGiftname();
                                contentColor = Color.parseColor("#F15C43");
                            }
                        }
                    }else if (customMsgBean.getType() == MessageInfo.MSG_TYPE_BG_DANMU){
                        isAnchor = customMsgBean.getNormal().getIs_room() == 1?true:false;
                        if (!TextUtils.isEmpty(String.valueOf(customMsgBean.getNormal().getText()))) {
                            content = String.valueOf(customMsgBean.getNormal().getText());
                        }
                    }else if (customMsgBean.getType() == MessageInfo.MSG_TYPE_RED_ENVELOPE){
                        isAnchor = customMsgBean.getNormal().getIs_room() == 1?true:false;
                        content = mContext.getString(R.string.text_send_red_envelope);
                        contentColor = Color.parseColor("#F15C43");
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
            String nobleLength = "";
            String expLength = "";
            if (nobleBitmap != null) {
                nobleLength = "1";
            }
            if (expBitmap != null) {
                expLength = "2";
            }
            if (isAnchor) {
                str = "  " + str + content;
                SpannableStringBuilder msg = FaceManager.handlerEmojiText(str);
                ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#2C9CED"));
                msg.setSpan(span, 1, 1+nickName.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                if (contentColor != 0) {
                    ForegroundColorSpan contentSpan = new ForegroundColorSpan(contentColor);
                    msg.setSpan(contentSpan, 1+nickName.length(), str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                ImageSpan imageSpan = new ImageSpan(mContext, R.mipmap.icon_anchor_label, DynamicDrawableSpan.ALIGN_CENTER);
                msg.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_content.setText(msg);
            }else {
                str = nobleLength + expLength + str + content;
                SpannableStringBuilder msg = FaceManager.handlerEmojiText(str);
                ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#2C9CED"));
                msg.setSpan(span, nobleLength.length()+expLength.length(), nobleLength.length()+expLength.length()+nickName.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                if (contentColor != 0) {
                    ForegroundColorSpan contentSpan = new ForegroundColorSpan(contentColor);
                    msg.setSpan(contentSpan, nobleLength.length()+expLength.length()+nickName.length(), str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                tv_content.setText(msg);
//                if (nobleBitmap != null) {
//                    ImageSpan imageSpan = new ImageSpan(mContext, nobleBitmap);
//                    msg.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    if (expBitmap != null) {
//                        ImageSpan imageSpanTwo = new ImageSpan(mContext, expBitmap);
//                        msg.setSpan(imageSpanTwo, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        tv_content.setText(msg);
//                    }
//                }else {
//                    if (expBitmap != null) {
//                        ImageSpan imageSpan = new ImageSpan(mContext, expBitmap);
//                        msg.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        tv_content.setText(msg);
//                    }
//                }
            }
        }
    }
}

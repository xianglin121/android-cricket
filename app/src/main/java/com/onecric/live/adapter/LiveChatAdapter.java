package com.onecric.live.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.fragment.LiveChatFragment;
import com.onecric.live.model.CustomMsgBean;
import com.onecric.live.util.GlideUtil;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class LiveChatAdapter extends BaseQuickAdapter<MessageInfo, BaseViewHolder> {
    private LiveChatFragment mFragment;
    public LiveChatAdapter(int layoutResId, @Nullable List<MessageInfo> data, LiveChatFragment fragment) {
        super(layoutResId, data);
        mFragment = fragment;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageInfo item) {
        TextView tv_content = helper.getView(R.id.tv_content);
        TextView tv_user_content = helper.getView(R.id.tv_user_content);
        TextView tv_system_notice = helper.getView(R.id.tv_system_notice);
        TextView tv_office_notice = helper.getView(R.id.tv_office_notice);
        helper.setGone(R.id.rl_default,false);
        tv_system_notice.setVisibility(View.GONE);
        tv_content.setVisibility(View.GONE);
        tv_office_notice.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(item.getSystemNotice())) {
            tv_system_notice.setVisibility(View.VISIBLE);
            tv_system_notice.setText(item.getSystemNotice());
        }else if(!TextUtils.isEmpty(item.getOfficeNotice())){
            tv_office_notice.setAutoLinkMask(Linkify.WEB_URLS);
            tv_office_notice.setLinkTextColor(Color.BLUE);
            tv_office_notice.setText(item.getOfficeNotice());

            //文案+链接
/*            String notice = item.getOfficeNotice();
            int aUrlStart = notice.indexOf("http");
            if(aUrlStart == -1){
                tv_office_notice.setText(notice);
            }else{
                CharSequence charSequence;
                int aUrlEnd = (notice.indexOf(" ",aUrlStart) == -1)?notice.length():notice.indexOf(" ",aUrlStart);
                String url = notice.substring(aUrlStart,aUrlEnd);
                //拼接
                SpannableString spanStr = new SpannableString(notice);
                spanStr.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        //跳转链接
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        mContext.startActivity(intent);
                    }
                }, aUrlStart, aUrlEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                spanStr.setSpan(new ForegroundColorSpan(Color.BLUE), aUrlStart, aUrlEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                tv_office_notice.setMovementMethod(LinkMovementMethod.getInstance());//设置可点击状态
                tv_office_notice.setText(spanStr);
            }*/

            //官方发言 走马灯
//            tv_office_notice.setText(item.getOfficeNotice());
            tv_office_notice.setVisibility(View.VISIBLE);
//            tv_office_notice.requestFocus();
            tv_office_notice.setFocusable(true);
            tv_office_notice.setFocusableInTouchMode(true);
            tv_office_notice.setSelected(true);
            tv_office_notice.requestFocusFromTouch();
        }else {
            String str = "";
            boolean isAnchor = false;//是否是主播
            Bitmap nobleBitmap = null;//贵族图标
            Bitmap expBitmap = null;//等级图标
            String nickName = "";

            String content = "";
            int contentColor =  0;
            boolean isEnterInfo = false;
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
                        if(customMsgBean.getNobel()==null){
                            isAnchor = false;
                        }else{
                            isAnchor = customMsgBean.getNobel().getIs_room() == 1?true:false;
                        }
                        isEnterInfo = true;
                        if (!TextUtils.isEmpty(item.getNickName())) {
                            nickName = item.getNickName() + "：";
                            str = nickName;
                        }else if(!TextUtils.isEmpty(item.getFromUser())){
                            nickName = item.getFromUser() + "：";
                            str = nickName;
                        }
                        //进入房间的消息不需要发言人
                        content = (TextUtils.isEmpty(item.getNickName()) ? item.getFromUser() : item.getNickName()) + " " + mContext.getString(R.string.enter_the_chat_room);
                        contentColor = Color.parseColor("#EEA831");
                    }else if (customMsgBean.getType() == MessageInfo.MSG_TYPE_GIFT) {
                        isAnchor = customMsgBean.getGift().getIs_room() == 1?true:false;
                        if (customMsgBean.getGift() != null) {
                            if (!TextUtils.isEmpty(customMsgBean.getGift().getGiftname())) {
                                content = "give to network anchor " + customMsgBean.getGift().getGiftname();
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
                //进入房间的消息不需要发言人
                str = "  " + (isEnterInfo?"":str) + content;
                SpannableStringBuilder msg = FaceManager.handlerEmojiText(str);
                ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(isEnterInfo?"#EEA831":"#2C9CED"));
                msg.setSpan(span, 1, 1+nickName.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                if (contentColor != 0) {
                    ForegroundColorSpan contentSpan = new ForegroundColorSpan(contentColor);
                    msg.setSpan(contentSpan, 1+nickName.length(), str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                ImageSpan imageSpan = new ImageSpan(mContext, R.mipmap.icon_anchor_label, DynamicDrawableSpan.ALIGN_CENTER);
                msg.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                tv_user_content.setText(msg);

            }else {
                str = nobleLength + expLength +  (isEnterInfo?"":str) + content;
                SpannableStringBuilder msg = FaceManager.handlerEmojiText(str);
                ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(isEnterInfo?"#EEA831":"#2C9CED"));
                int len = (nobleLength.length()+expLength.length()+nickName.length())>msg.length()?msg.length():(nobleLength.length()+expLength.length()+nickName.length());
                msg.setSpan(span, nobleLength.length()+expLength.length(), len, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                if (contentColor != 0) {
                    ForegroundColorSpan contentSpan = new ForegroundColorSpan(contentColor);
                    msg.setSpan(contentSpan, len, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }

                if(isEnterInfo){
                    tv_content.setVisibility(View.VISIBLE);
                    tv_content.setText(msg);
                }else{
                    helper.setGone(R.id.rl_default,true);
                    helper.setText(R.id.tv_user_name,(!TextUtils.isEmpty(item.getNickName()))?item.getNickName():item.getFromUser());
                    GlideUtil.loadUserImageDefault(mContext, item.getFaceUrl(),helper.getView(R.id.iv_avatar));

                    tv_user_content.setAutoLinkMask(Linkify.WEB_URLS);
                    tv_user_content.setLinkTextColor(Color.BLUE);
                    tv_user_content.setMovementMethod(LinkMovementMethod.getInstance());

                    tv_user_content.setText(msg);
                }



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

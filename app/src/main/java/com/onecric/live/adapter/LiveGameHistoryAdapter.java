package com.onecric.live.adapter;

import static com.onecric.live.util.TimeUtil.toToday;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.GameHistoryBean;
import com.onecric.live.util.GlideUtil;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class LiveGameHistoryAdapter extends BaseQuickAdapter<GameHistoryBean, BaseViewHolder> {
    public LiveGameHistoryAdapter(int layoutResId, @Nullable List<GameHistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GameHistoryBean item) {
        helper.addOnClickListener(R.id.ll_heart);
        helper.addOnClickListener(R.id.iv_follow);
        helper.addOnClickListener(R.id.ll_share);
        helper.addOnClickListener(R.id.iv_avatar);
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        ImageView iv_cover = helper.getView(R.id.iv_cover);

        android.view.ViewGroup.LayoutParams ppiv_cover = iv_cover.getLayoutParams();
        int width = UIUtil.getScreenWidth(mContext);
//        ppiv_cover.height = (int)(width * 0.5625 * 0.9);
        ppiv_cover.height = (int)(width /1.778);
        iv_cover.setLayoutParams(ppiv_cover);
        GlideUtil.loadUserImageDefault(mContext, item.avatar, iv_avatar);
        Glide.with(mContext).load(item.thumb).placeholder(R.mipmap.img_updates_default).error(R.mipmap.img_updates_default).into(iv_cover);

        int eyeNum = item.viewers;
        int likeNum = item.like;
        int commentNum = item.comment;
        helper.setText(R.id.tv_tool_heart, likeNum > 1000 ? String.format("%.1f", (float) likeNum / 1000) + "K" : likeNum + "");
        helper.setText(R.id.tv_tool_reply, commentNum > 1000 ? String.format("%.1f", (float) likeNum / 1000) + "K" : commentNum + "");
        helper.setText(R.id.tv_eyes_num,eyeNum > 1000 ? String.format("%.1f", (float) eyeNum / 1000) + "K" : eyeNum + "");

        helper.setGone(R.id.iv_follow,(item.isAttention == 1)?false:true);
        if (!TextUtils.isEmpty(item.userNickname)) {
            helper.setText(R.id.tv_name, item.userNickname);
        }else {
            helper.setText(R.id.tv_name, "");
        }
        ImageView iv_like = helper.getView(R.id.iv_tool_heart);
        if (item.isLikes == 0) {
            iv_like.setSelected(false);
        }else {
            iv_like.setSelected(true);
        }
        if (!TextUtils.isEmpty(item.title)) {
            SpannableStringBuilder msg = FaceManager.handlerEmojiText(item.title);
            helper.setText(R.id.tv_content, msg);
        }else {
            helper.setText(R.id.tv_content, "");
        }
        helper.setText(R.id.tv_time, TextUtils.isEmpty(item.createdAt)?"":toToday(item.createdAt));
    }

    @Override
    protected void convertPayloads(@NonNull BaseViewHolder helper, GameHistoryBean item, @NonNull List<Object> payloads) {
        if (payloads != null && payloads.size() > 0) {
            ImageView iv_like = helper.getView(R.id.iv_tool_heart);
            int likeNum = item.like;
            helper.setText(R.id.tv_tool_heart, likeNum > 1000 ? String.format("%.1f", (float) likeNum / 1000) + "K" : likeNum + "");
            helper.setGone(R.id.iv_follow,(item.isAttention == 0)?true:false);
            if(iv_like!=null){
                iv_like.setSelected((item.isLikes == 0)?false:true);
            }
        }
    }

    public void forFollowedStatus(int aId,int isFollow){
        for(GameHistoryBean bean:this.getData()){
            if(bean.uid == aId){
                bean.isAttention= isFollow;
                notifyDataSetChanged();
//                notifyItemChanged(position, Constant.PAYLOAD);
            }
        }
    }
}

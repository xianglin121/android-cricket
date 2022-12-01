package com.longya.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.MatchListBean;
import com.longya.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/11
 */
public class LiveClassifyAdapter extends BaseMultiItemQuickAdapter<MatchListBean, BaseViewHolder> {

    public LiveClassifyAdapter(List<MatchListBean> data) {
        super(data);
        addItemType(MatchListBean.HEAD, R.layout.item_live_classify_head);
        addItemType(MatchListBean.CONTENT, R.layout.item_live_classify_content);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MatchListBean item) {
        switch (helper.getItemViewType()) {
            case MatchListBean.HEAD:
                break;
            case MatchListBean.CONTENT:
                if(!TextUtils.isEmpty(item.getMatch_time())) {
                    helper.setText(R.id.tv_time, item.getMatch_time());
                }else {
                    helper.setText(R.id.tv_time, "");
                }
                if(!TextUtils.isEmpty(item.getCompetition())) {
                    helper.setText(R.id.tv_title, item.getCompetition());
                }else {
                    helper.setText(R.id.tv_title, "");
                }
                ImageView iv_team_logo_one = helper.getView(R.id.iv_team_logo_one);
                GlideUtil.loadTeamImageDefault(mContext, item.getHome_team_logo(), iv_team_logo_one);
                if(!TextUtils.isEmpty(item.getHome_team_name())) {
                    helper.setText(R.id.tv_team_name_one, item.getHome_team_name());
                }else {
                    helper.setText(R.id.tv_team_name_one, "");
                }
                helper.setText(R.id.tv_team_one_score, String.valueOf(item.getHome_scores()));

                ImageView iv_team_logo_two = helper.getView(R.id.iv_team_logo_two);
                GlideUtil.loadTeamImageDefault(mContext, item.getAway_team_logo(), iv_team_logo_two);
                if(!TextUtils.isEmpty(item.getAway_team_name())) {
                    helper.setText(R.id.tv_team_name_two, item.getAway_team_name());
                }else {
                    helper.setText(R.id.tv_team_name_two, "");
                }
                helper.setText(R.id.tv_team_two_score, String.valueOf(item.getAway_scores()));
                TextView tv_state = helper.getView(R.id.tv_state);
                helper.addOnClickListener(R.id.tv_state);
                if (item.getStatus_type() == 0) {
                    if (item.getReserve() == 1) {
                        tv_state.setText(mContext.getString(R.string.booked));
                        tv_state.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                        tv_state.setBackgroundResource(R.drawable.shape_f1f1f1_10dp_rec);
                    }else {
                        tv_state.setText(mContext.getString(R.string.book));
                        tv_state.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
                        tv_state.setBackgroundResource(R.drawable.bg_reserve);
                    }
                }else if (item.getStatus_type() == 1) {
                    tv_state.setText(mContext.getString(R.string.living_two));
                    tv_state.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
                    tv_state.setBackgroundResource(R.drawable.bg_reserve);
                }else {
                    tv_state.setText(mContext.getString(R.string.finished));
                    tv_state.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                    tv_state.setBackgroundResource(R.drawable.shape_f1f1f1_10dp_rec);
                }
                break;
        }
    }
}

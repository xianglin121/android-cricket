package com.longya.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.LiveUserBean;
import com.longya.live.model.MatchListBean;
import com.longya.live.model.ReserveMatchBean;
import com.longya.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class MyReserveAdapter extends BaseMultiItemQuickAdapter<ReserveMatchBean, BaseViewHolder> {

    public MyReserveAdapter(List<ReserveMatchBean> data) {
        super(data);
        addItemType(ReserveMatchBean.HEAD, R.layout.item_match_date_head);
        addItemType(ReserveMatchBean.CONTENT, R.layout.item_my_reserve);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ReserveMatchBean item) {
        switch (helper.getItemViewType()) {
            case ReserveMatchBean.HEAD:
                if (!TextUtils.isEmpty(item.getStart2())) {
                    helper.setText(R.id.tv_date, item.getStart2());
                }else {
                    helper.setText(R.id.tv_date, "");
                }
                break;
            case ReserveMatchBean.CONTENT:
                ImageView iv_team_logo_one = helper.getView(R.id.iv_team_logo_one);
                TextView tv_team_name_one = helper.getView(R.id.tv_team_name_one);
                ImageView iv_team_logo_two = helper.getView(R.id.iv_team_logo_two);
                TextView tv_team_name_two = helper.getView(R.id.tv_team_name_two);
                TextView tv_league = helper.getView(R.id.tv_league);
                TextView tv_time = helper.getView(R.id.tv_time);
                TextView tv_count = helper.getView(R.id.tv_count);
                ImageView iv_avatar_one = helper.getView(R.id.iv_avatar_one);
                ImageView iv_avatar_two = helper.getView(R.id.iv_avatar_two);
                ImageView iv_avatar_three = helper.getView(R.id.iv_avatar_three);
                ImageView iv_avatar_four = helper.getView(R.id.iv_avatar_four);
                ImageView iv_avatar_five = helper.getView(R.id.iv_avatar_five);
                helper.addOnClickListener(R.id.tv_reserve);

                GlideUtil.loadTeamImageDefault(mContext, item.getHome_logo(), iv_team_logo_one);
                if (!TextUtils.isEmpty(item.getHome_name())) {
                    tv_team_name_one.setText(item.getHome_name());
                }else {
                    tv_team_name_one.setText("");
                }
                GlideUtil.loadTeamImageDefault(mContext, item.getAway_logo(), iv_team_logo_two);
                if (!TextUtils.isEmpty(item.getAway_name())) {
                    tv_team_name_two.setText(item.getAway_name());
                }else {
                    tv_team_name_two.setText("");
                }
                if (!TextUtils.isEmpty(item.getName())) {
                    tv_league.setText(item.getName());
                }else {
                    tv_league.setText("");
                }
                if (!TextUtils.isEmpty(item.getStart())) {
                    tv_time.setText(item.getStart());
                }else {
                    tv_time.setText("");
                }
                List<LiveUserBean> anchorList = item.getAnchorList();
                if (anchorList != null) {
                    tv_count.setText(anchorList.size() + "位主播");
                    if (anchorList.size() > 0) {
                        iv_avatar_one.setVisibility(View.VISIBLE);
                        GlideUtil.loadUserImageDefault(mContext, anchorList.get(0).getAvatar(), iv_avatar_one);
                    }else {
                        iv_avatar_one.setVisibility(View.GONE);
                    }
                    if (anchorList.size() > 1) {
                        iv_avatar_two.setVisibility(View.VISIBLE);
                        GlideUtil.loadUserImageDefault(mContext, anchorList.get(1).getAvatar(), iv_avatar_two);
                    }else {
                        iv_avatar_two.setVisibility(View.GONE);
                    }
                    if (anchorList.size() > 2) {
                        iv_avatar_three.setVisibility(View.VISIBLE);
                        GlideUtil.loadUserImageDefault(mContext, anchorList.get(2).getAvatar(), iv_avatar_three);
                    }else {
                        iv_avatar_three.setVisibility(View.GONE);
                    }
                    if (anchorList.size() > 3) {
                        iv_avatar_four.setVisibility(View.VISIBLE);
                        GlideUtil.loadUserImageDefault(mContext, anchorList.get(3).getAvatar(), iv_avatar_four);
                    }else {
                        iv_avatar_four.setVisibility(View.GONE);
                    }
                    if (anchorList.size() > 4) {
                        iv_avatar_five.setVisibility(View.VISIBLE);
                        GlideUtil.loadUserImageDefault(mContext, anchorList.get(4).getAvatar(), iv_avatar_five);
                    }else {
                        iv_avatar_five.setVisibility(View.GONE);
                    }
                }else {
                    tv_count.setText("0位主播");
                    iv_avatar_one.setVisibility(View.GONE);
                    iv_avatar_two.setVisibility(View.GONE);
                    iv_avatar_three.setVisibility(View.GONE);
                    iv_avatar_four.setVisibility(View.GONE);
                    iv_avatar_five.setVisibility(View.GONE);
                }
                break;
        }
    }
}

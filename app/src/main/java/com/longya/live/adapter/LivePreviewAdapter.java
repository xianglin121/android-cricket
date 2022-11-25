package com.longya.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.ReserveLiveBean;
import com.longya.live.model.ReserveMatchBean;
import com.longya.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class LivePreviewAdapter extends BaseMultiItemQuickAdapter<ReserveLiveBean, BaseViewHolder> {
    public LivePreviewAdapter(List<ReserveLiveBean> data) {
        super(data);
        addItemType(ReserveLiveBean.HEAD, R.layout.item_match_date_head);
        addItemType(ReserveLiveBean.CONTENT, R.layout.item_live_preview);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ReserveLiveBean item) {
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
                TextView tv_reserve = helper.getView(R.id.tv_reserve);
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
                if (item.getIs_reserve() == 0) {
                    tv_reserve.setText(mContext.getString(R.string.book));
                    tv_reserve.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
                    tv_reserve.setBackgroundResource(R.drawable.bg_live_classify);
                }else {
                    tv_reserve.setText(mContext.getString(R.string.booked));
                    tv_reserve.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                    tv_reserve.setBackgroundResource(R.drawable.bg_reserve_selected);
                }
                break;
        }
    }
}

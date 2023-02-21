package com.onecric.live.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.model.FootballMatchBean;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.SpUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/11
 */
public class FootballMatchInnerAdapter extends BaseMultiItemQuickAdapter<FootballMatchBean, BaseViewHolder> {

    public FootballMatchInnerAdapter(List<FootballMatchBean> data) {
        super(data);
        addItemType(FootballMatchBean.HEAD, R.layout.item_football_match_inner_head);
        addItemType(FootballMatchBean.CONTENT, R.layout.item_football_match_inner_content);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FootballMatchBean item) {
        switch (helper.getItemViewType()) {
            case MatchListBean.HEAD:
                break;
            case MatchListBean.CONTENT:
//                ImageView iv_label = helper.getView(R.id.iv_label);
//                iv_label.setBackgroundResource(R.mipmap.bg_league_label);
                helper.addOnClickListener(R.id.iv_collect);
                ImageView iv_collect = helper.getView(R.id.iv_collect);
                if (item.getIs_collect() == 1) {
                    iv_collect.setSelected(true);
                }else {
                    iv_collect.setSelected(false);
                }
                helper.getView(R.id.tv_title).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(item.getPrimary_color())));
                if ("1".equals(SpUtil.getInstance().getStringValue(SpUtil.FOOTBALL_LANGUAGE))) {//繁体中文
                    if (!TextUtils.isEmpty(item.getCompetition_name_zht())) {
                        helper.setText(R.id.tv_title, item.getCompetition_name_zht());
                    }else {
                        helper.setText(R.id.tv_title, "");
                    }
                    if (!TextUtils.isEmpty(item.getHome_team_name_zht())) {
                        helper.setText(R.id.tv_team_name_one, item.getHome_team_name_zht());
                    }else {
                        helper.setText(R.id.tv_team_name_one, "");
                    }
                    if (!TextUtils.isEmpty(item.getAway_team_name_zht())) {
                        helper.setText(R.id.tv_team_name_two, item.getAway_team_name_zht());
                    }else {
                        helper.setText(R.id.tv_team_name_two, "");
                    }
                }else if ("2".equals(SpUtil.getInstance().getStringValue(SpUtil.FOOTBALL_LANGUAGE))) {//英文
                    if (!TextUtils.isEmpty(item.getCompetition_name_en())) {
                        helper.setText(R.id.tv_title, item.getCompetition_name_en());
                    }else {
                        helper.setText(R.id.tv_title, "");
                    }
                    if (!TextUtils.isEmpty(item.getHome_team_name_en())) {
                        helper.setText(R.id.tv_team_name_one, item.getHome_team_name_en());
                    }else {
                        helper.setText(R.id.tv_team_name_one, "");
                    }
                    if (!TextUtils.isEmpty(item.getAway_team_name_en())) {
                        helper.setText(R.id.tv_team_name_two, item.getAway_team_name_en());
                    }else {
                        helper.setText(R.id.tv_team_name_two, "");
                    }
                }else {
                    if (!TextUtils.isEmpty(item.getCompetition_name_zh())) {
                        helper.setText(R.id.tv_title, item.getCompetition_name_zh());
                    }else {
                        helper.setText(R.id.tv_title, "");
                    }
                    if (!TextUtils.isEmpty(item.getHome_team_name_zh())) {
                        helper.setText(R.id.tv_team_name_one, item.getHome_team_name_zh());
                    }else {
                        helper.setText(R.id.tv_team_name_one, "");
                    }
                    if (!TextUtils.isEmpty(item.getAway_team_name_zh())) {
                        helper.setText(R.id.tv_team_name_two, item.getAway_team_name_zh());
                    }else {
                        helper.setText(R.id.tv_team_name_two, "");
                    }
                }
                if (!TextUtils.isEmpty(item.getTime())) {
                    long time = Long.parseLong(item.getTime())*1000;
                    helper.setText(R.id.tv_time, timeStamp2Date(String.valueOf(time)));
                }else {
                    helper.setText(R.id.tv_time, "");
                }
                String desc = "";
                if (!TextUtils.isEmpty(item.getHalf_score())) {
                    desc = desc + "半 " + item.getHalf_score();
                }
                if (!TextUtils.isEmpty(item.getCorner_kick())) {
                    desc = desc + "  角 " + item.getCorner_kick();
                }
                helper.setText(R.id.tv_desc, desc);
                TextView tv_team_one_score = helper.getView(R.id.tv_team_one_score);
                TextView tv_team_two_score = helper.getView(R.id.tv_team_two_score);
                if (!TextUtils.isEmpty(item.getHome_score())) {
                    tv_team_one_score.setText(item.getHome_score());
                }else {
                    tv_team_one_score.setText("-");
                }
                if (!TextUtils.isEmpty(item.getAway_score())) {
                    tv_team_two_score.setText(item.getAway_score());
                }else {
                    tv_team_two_score.setText("-");
                }
                SuperTextView tv_state = helper.getView(R.id.tv_state);
                if (item.getStatus() == 1) {
                    tv_state.setText("未");
                    tv_state.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                    tv_state.setSolid(mContext.getResources().getColor(R.color.c_f5f5f5));
                    tv_team_one_score.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                    tv_team_two_score.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                } else if (item.getStatus() == 3) {
                    tv_state.setText("中");
                    tv_state.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
                    tv_state.setSolid(mContext.getResources().getColor(R.color.c_F7E6D2));
                    tv_team_one_score.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
                    tv_team_two_score.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
                } else if (item.getStatus() == 8) {
                    tv_state.setText("完");
                    tv_state.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                    tv_state.setSolid(mContext.getResources().getColor(R.color.c_f5f5f5));
                    tv_team_one_score.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
                    tv_team_two_score.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
                }else {
//                    if (item.getMatch_time() != null) {
//                        long time = System.currentTimeMillis()/1000-item.getMatch_time();
//                        tv_state.setText(time/60 + "'");
//                    }else {
//                        tv_state.setText("0'");
//                    }
                    if (!TextUtils.isEmpty(item.getMatch_str())) {
                        tv_state.setText(item.getMatch_str() + "'");
                    }else {
                        tv_state.setText("0'");
                    }
                    tv_state.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
                    tv_state.setSolid(mContext.getResources().getColor(R.color.c_F7E6D2));
                    tv_team_one_score.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
                    tv_team_two_score.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
                }
                TextView tv_rcard_one = helper.getView(R.id.tv_rcard_one);
                TextView tv_ycard_one = helper.getView(R.id.tv_ycard_one);
                TextView tv_rcard_two = helper.getView(R.id.tv_rcard_two);
                TextView tv_ycard_two = helper.getView(R.id.tv_ycard_two);
                if (SpUtil.getInstance().getBooleanValue(SpUtil.RED_AND_YELLOW_CARD)) {
                    if (item.getHome_red_card() > 0) {
                        tv_rcard_one.setVisibility(View.VISIBLE);
                        tv_rcard_one.setText(String.valueOf(item.getHome_red_card()));
                    }else {
                        tv_rcard_one.setVisibility(View.GONE);
                    }
                    if (item.getHome_yellow_card() > 0) {
                        tv_ycard_one.setVisibility(View.VISIBLE);
                        tv_ycard_one.setText(String.valueOf(item.getHome_yellow_card()));
                    }else {
                        tv_ycard_one.setVisibility(View.GONE);
                    }
                    if (item.getAway_red_card() > 0) {
                        tv_rcard_two.setVisibility(View.VISIBLE);
                        tv_rcard_two.setText(String.valueOf(item.getAway_red_card()));
                    }else {
                        tv_rcard_two.setVisibility(View.GONE);
                    }
                    if (item.getAway_yellow_card() > 0) {
                        tv_ycard_two.setVisibility(View.VISIBLE);
                        tv_ycard_two.setText(String.valueOf(item.getAway_yellow_card()));
                    }else {
                        tv_ycard_two.setVisibility(View.GONE);
                    }
                }else {
                    tv_rcard_one.setVisibility(View.GONE);
                    tv_ycard_one.setVisibility(View.GONE);
                    tv_rcard_two.setVisibility(View.GONE);
                    tv_ycard_two.setVisibility(View.GONE);
                }
                ImageView iv_logo = helper.getView(R.id.iv_logo);
                ImageView iv_anchor = helper.getView(R.id.iv_anchor);
                if (item.getAnchor() != null && item.getAnchor().getId() > 0) {
                    iv_logo.setVisibility(View.GONE);
                    iv_anchor.setVisibility(View.VISIBLE);
                    GlideUtil.loadUserImageDefault(mContext, item.getAnchor().getAvatar(), iv_anchor);
                    iv_anchor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME)){
//                                LoginActivity.forward(mContext);
                            }else{
                                LiveDetailActivity.forward(mContext, item.getAnchor().getId(), 1, item.getAnchor().getMatch_id(),item.getAnchor().getId());
                            }
                        }
                    });
                }else {
                    iv_anchor.setVisibility(View.GONE);
                    iv_anchor.setOnClickListener(null);
                    if (item.getMlive() == 0) {
                        iv_logo.setVisibility(View.GONE);
                    }else {
                        iv_logo.setVisibility(View.VISIBLE);
                        iv_logo.setImageResource(R.mipmap.icon_match_animation);
                    }
                }
                break;
        }
    }

    private String timeStamp2Date(String time) {
        Long timeLong = Long.parseLong(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");//要转换的时间格式
        Date date;
        try {
            date = sdf.parse(sdf.format(timeLong));
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

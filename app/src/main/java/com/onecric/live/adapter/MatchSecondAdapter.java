package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.SpUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/11
 */
public class MatchSecondAdapter extends BaseMultiItemQuickAdapter<MatchListBean, BaseViewHolder> {

    public MatchSecondAdapter(List<MatchListBean> data) {
        super(data);
        addItemType(MatchListBean.HEAD, R.layout.item_match_date_head);
        addItemType(MatchListBean.CONTENT, R.layout.item_match_second_content);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MatchListBean item) {
        switch (helper.getItemViewType()) {
            case MatchListBean.HEAD:
                if (!TextUtils.isEmpty(item.getMatch_date())) {
                    helper.setText(R.id.tv_date, item.getMatch_date());
                }else {
                    helper.setText(R.id.tv_date, "");
                }
                break;
            case MatchListBean.CONTENT:
                if ("1".equals(SpUtil.getInstance().getStringValue(SpUtil.FOOTBALL_LANGUAGE))) {//繁体中文
                    if (!TextUtils.isEmpty(item.getCompetition_zht())) {
                        helper.setText(R.id.tv_title, item.getCompetition_zht());
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
                    if (!TextUtils.isEmpty(item.getCompetition_en())) {
                        helper.setText(R.id.tv_title, item.getCompetition_en());
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
                    if (!TextUtils.isEmpty(item.getCompetition())) {
                        helper.setText(R.id.tv_title, item.getCompetition());
                    }else {
                        helper.setText(R.id.tv_title, "");
                    }
                    if (!TextUtils.isEmpty(item.getHome_team_name())) {
                        helper.setText(R.id.tv_team_name_one, item.getHome_team_name());
                    }else {
                        helper.setText(R.id.tv_team_name_one, "");
                    }
                    if (!TextUtils.isEmpty(item.getAway_team_name())) {
                        helper.setText(R.id.tv_team_name_two, item.getAway_team_name());
                    }else {
                        helper.setText(R.id.tv_team_name_two, "");
                    }
                }
                if (!TextUtils.isEmpty(item.getMatch_str())) {
                    helper.setText(R.id.tv_time, item.getMatch_str());
                }else {
                    helper.setText(R.id.tv_time, "");
                }

                helper.setText(R.id.tv_state, formatStatus(item.getType(), item.getStatus_id()));

                ImageView iv_collect = helper.getView(R.id.iv_collect);
                if (item.getCollect() == 1) {
                    iv_collect.setSelected(true);
                }else {
                    iv_collect.setSelected(false);
                }
                ImageView iv_team_logo_one = helper.getView(R.id.iv_team_logo_one);
                ImageView iv_team_logo_two = helper.getView(R.id.iv_team_logo_two);
                GlideUtil.loadTeamImageDefault(mContext, item.getHome_team_logo(), iv_team_logo_one);
                GlideUtil.loadTeamImageDefault(mContext, item.getAway_team_logo(), iv_team_logo_two);
                TextView tv_team_one_score = helper.getView(R.id.tv_team_one_score);
                TextView tv_team_two_score = helper.getView(R.id.tv_team_two_score);
                tv_team_one_score.setText(String.valueOf(item.getHome_scores()));
                tv_team_two_score.setText(String.valueOf(item.getAway_scores()));

                ImageView iv_logo = helper.getView(R.id.iv_logo);
                ImageView iv_anchor = helper.getView(R.id.iv_anchor);
                if (item.getAnchor() != null && item.getAnchor().getId() > 0) {
                    iv_logo.setVisibility(View.GONE);
                    iv_anchor.setVisibility(View.VISIBLE);
                    GlideUtil.loadUserImageDefault(mContext, item.getAnchor().getAvatar(), iv_anchor);
                    iv_anchor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LiveDetailActivity.forward(mContext, item.getAnchor().getId(), item.getType(), item.getAnchor().getMatch_id());
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

    private String formatStatus(int type, int status_id) {
        String name = "";
        if (type == 0) {
            if (status_id == 1) {
                name = "未开赛";
            }else if (status_id == 2) {
                name = "上半场";
            }else if (status_id == 3) {
                name = "中场";
            }else if (status_id == 4) {
                name = "下半场";
            }else if (status_id == 5) {
                name = "加时赛";
            }else if (status_id == 7) {
                name = "点球决战";
            }else if (status_id == 8) {
                name = "完场";
            }else if (status_id == 9) {
                name = "推迟";
            }else if (status_id == 10) {
                name = "中断";
            }else if (status_id == 11) {
                name = "腰斩";
            }else if (status_id == 12) {
                name = "取消";
            }else {
                name = "待定";
            }
        }else {
            if (status_id == 1) {
                name = "未开赛";
            }else if (status_id == 2) {
                name = "第一节";
            }else if (status_id == 3) {
                name = "第一节完 ";
            }else if (status_id == 4) {
                name = "第二节 ";
            }else if (status_id == 5) {
                name = "第二节完 ";
            }else if (status_id == 6) {
                name = "第三节 ";
            }else if (status_id == 7) {
                name = "第三节完 ";
            }else if (status_id == 8) {
                name = "第四节  ";
            }else if (status_id == 9) {
                name = "加时";
            }else if (status_id == 10) {
                name = "完场";
            }else if (status_id == 11) {
                name = "中断";
            }else if (status_id == 12) {
                name = "取消";
            }else if (status_id == 13) {
                name = "延期";
            }else if (status_id == 14) {
                name = "腰斩";
            }else if (status_id == 15) {
                name = "待定";
            }else {
                name = "异常";
            }
        }
        return name;
    }
}

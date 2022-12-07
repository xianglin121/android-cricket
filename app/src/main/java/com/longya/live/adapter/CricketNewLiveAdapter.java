package com.longya.live.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.CricketLiveBean;


import java.util.List;

public class CricketNewLiveAdapter extends BaseMultiItemQuickAdapter<CricketLiveBean, BaseViewHolder> {


    public CricketNewLiveAdapter(List<CricketLiveBean> data) {
        super(data);
        addItemType(CricketLiveBean.LIVE_ITEM_TYPE_HEAD, R.layout.item_live_head);
        addItemType(CricketLiveBean.LIVE_ITEM_TYPE_NARRATE, R.layout.item_live_narrate);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketLiveBean item) {
        switch (helper.getItemViewType()) {
            case CricketLiveBean.LIVE_ITEM_TYPE_HEAD:
                helper.setText(R.id.cricket_live_round, item.serial_number + "");
                helper.setText(R.id.cricket_live_country, item.head.country_code + "");
                helper.setText(R.id.cricket_live_sessions, item.head.sessions_number + "");
                helper.setText(R.id.cricket_live_score, item.head.display_score + "");
                helper.setText(R.id.cricket_live_runs, item.head.sum + " Runs");

                if (item.head.striker.name.contains(", ")) {
                    String[] name = item.head.striker.name.split(", ");
                    helper.setText(R.id.cricket_live_striker, name[1] + " " + name[0]);
                } else {
                    helper.setText(R.id.cricket_live_striker, item.head.striker.name + "");
                }
                helper.setText(R.id.cricket_live_striker_score, item.head.striker.runs_scored_so_far + "");
                helper.setText(R.id.cricket_live_striker_score_pt, "(" + item.head.striker.balls_faced_so_far + ")");

                if (item.head.non_striker.name.contains(", ")) {
                    String[] name = item.head.non_striker.name.split(", ");
                    helper.setText(R.id.cricket_live_non_striker, name[1] + " " + name[0]);
                } else {
                    helper.setText(R.id.cricket_live_non_striker, item.head.non_striker.name + "");
                }

                helper.setText(R.id.cricket_live_non_striker_score, item.head.non_striker.runs_scored_so_far + "");
                helper.setText(R.id.cricket_live_non_striker_score_pt, "(" + item.head.non_striker.balls_faced_so_far + ")");

                TextView overTxt = helper.getView(R.id.cricket_live_over);
                TextView roundTxt = helper.getView(R.id.cricket_live_round);
//                overTxt.setTypeface(AndroidUtilities.getPingFangTypefaceMedium());
//                roundTxt.setTypeface(AndroidUtilities.getPingFangTypefaceBold());

                break;

            case CricketLiveBean.LIVE_ITEM_TYPE_NARRATE:

                helper.setText(R.id.cricket_live_serial, item.display_overs + "");
// TODO  暂时保留

//                String commentaries = item.commentaries;
//                String title = "";
//                String content = "";
//                if (commentaries.contains(",")) {
//                    String[] split = commentaries.split(",");
//                    title = split[0];
//                    for (int i = 1; i < split.length; i++) {
//                        if (TextUtils.isEmpty(content)) {
//                            content = split[i];
//                        } else {
//                            content = content + "," + split[i];
//                        }
//                    }
//                }
                helper.setText(R.id.cricket_live_serial_content, item.commentaries);
//                helper.setText(R.id.cricket_live_serial_content, title + "\n" + content);
                TextView serialScore = helper.getView(R.id.cricket_live_serial_score);
                String runsScored = item.runs_scored + "";
                serialScore.setText(runsScored + "");
                if (runsScored.equals("4") || runsScored.equals("6") || runsScored.equals("8")) {
                    serialScore.setBackground(mContext.getResources().getDrawable(R.drawable.shape_live_score_oval_green));
                    serialScore.setTextColor(mContext.getResources().getColor(R.color.white));
                } else if (runsScored.equals("W")) {
                    serialScore.setBackground(mContext.getResources().getDrawable(R.drawable.shape_live_score_oval_red));
                    serialScore.setTextColor(mContext.getResources().getColor(R.color.white));
                } else {
                    serialScore.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                    serialScore.setBackground(mContext.getResources().getDrawable(R.drawable.shape_live_score_oval_gray));
                }

                break;
        }

    }

}

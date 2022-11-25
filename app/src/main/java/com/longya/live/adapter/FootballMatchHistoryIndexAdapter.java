package com.longya.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.activity.HistoryIndexDetailActivity;
import com.longya.live.fragment.FootballMatchIndexInnerFragment;
import com.longya.live.model.FootballHistoryIndexInnerBean;
import com.longya.live.model.IndexBean;
import com.longya.live.model.MatchListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/11
 */
public class FootballMatchHistoryIndexAdapter extends BaseMultiItemQuickAdapter<FootballHistoryIndexInnerBean, BaseViewHolder> {

    private OptionsPickerView pvCustomOptions;

    public FootballMatchHistoryIndexAdapter(List<FootballHistoryIndexInnerBean> data, int type) {
        super(data);
        if (type == FootballMatchIndexInnerFragment.TYPE_DAXIAOQIU) {
            addItemType(MatchListBean.HEAD, R.layout.item_football_history_daxiaoqiu_index_head);
        }else {
            addItemType(MatchListBean.HEAD, R.layout.item_football_history_index_head);
        }
        addItemType(MatchListBean.CONTENT, R.layout.item_football_history_index_content);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FootballHistoryIndexInnerBean item) {
        switch (helper.getItemViewType()) {
            case MatchListBean.HEAD:
                helper.addOnClickListener(R.id.cl_company);
                if (!TextUtils.isEmpty(item.getWon())) {
                    helper.setText(R.id.tv_win_rate, item.getWon());
                }else {
                    helper.setText(R.id.tv_win_rate, "");
                }
                if (!TextUtils.isEmpty(item.getLoss())) {
                    helper.setText(R.id.tv_lose_rate, item.getLoss());
                }else {
                    helper.setText(R.id.tv_lose_rate, "");
                }
                if (!TextUtils.isEmpty(item.getDraw())) {
                    helper.setText(R.id.tv_draw_rate, item.getDraw());
                }else {
                    helper.setText(R.id.tv_draw_rate, "");
                }
                if (!TextUtils.isEmpty(item.getCompany_name())) {
                    helper.setText(R.id.tv_company, item.getCompany_name());
                }else {
                    helper.setText(R.id.tv_company, "");
                }
                break;
            case MatchListBean.CONTENT:
                helper.addOnClickListener(R.id.cl_company_content);
                LinearLayout rootView = helper.getView(R.id.rootView);
                int position = helper.getLayoutPosition()-1;
                if (position%2 == 0) {
                    rootView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }else {
                    rootView.setBackgroundColor(mContext.getResources().getColor(R.color.c_FFFBF6));
                }
                TextView tv_name = helper.getView(R.id.tv_name);
                if (helper.getLayoutPosition() == 1) {
                    tv_name.setText(mContext.getString(R.string.all));
                }else {
                    tv_name.setText(mContext.getString(R.string.same_league));
                }
                TextView tv_home_rate = helper.getView(R.id.tv_home_rate);
                TextView tv_flat_rate = helper.getView(R.id.tv_flat_rate);
                TextView tv_away_rate = helper.getView(R.id.tv_away_rate);
                tv_home_rate.setText(String.valueOf(item.getWon()));
                tv_flat_rate.setText(String.valueOf(item.getDraw()));
                tv_away_rate.setText(String.valueOf(item.getLoss()));
                TextView tv_home_percent = helper.getView(R.id.tv_home_percent);
                TextView tv_flat_percent = helper.getView(R.id.tv_flat_percent);
                TextView tv_away_percent = helper.getView(R.id.tv_away_percent);
                if (!TextUtils.isEmpty(item.getWin_rate())) {
                    tv_home_percent.setText(item.getWin_rate() + "%");
                }else {
                    tv_home_percent.setText("");
                }
                if (!TextUtils.isEmpty(item.getDraw_rate())) {
                    tv_flat_percent.setText(item.getDraw_rate() + "%");
                }else {
                    tv_flat_percent.setText("");
                }
                if (!TextUtils.isEmpty(item.getLoss_rate())) {
                    tv_away_percent.setText(item.getLoss_rate() + "%");
                }else {
                    tv_away_percent.setText("");
                }
                break;
        }
    }
}

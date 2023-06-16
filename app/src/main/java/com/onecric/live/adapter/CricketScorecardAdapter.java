package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.model.ScorecardBatterBean;
import com.onecric.live.model.ScorecardBowlerBean;
import com.onecric.live.model.ScorecardWicketBean;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.retrofit.ApiClient;
import com.onecric.live.retrofit.ApiStores;
import com.onecric.live.util.GlideUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CricketScorecardAdapter extends BaseQuickAdapter<CricketMatchBean.CompetitionBean, BaseViewHolder> {
    public int match_id;
    public CricketScorecardAdapter(int layoutResId, @Nullable List<CricketMatchBean.CompetitionBean> data) {
        super(layoutResId, data);
        this.match_id = match_id;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketMatchBean.CompetitionBean item) {
        ViewGroup ll_home = helper.getView(R.id.ll_home);
        ImageView iv_arrow_one = helper.getView(R.id.iv_arrow_one);
        NestedScrollView sv_home = helper.getView(R.id.sv_home);
        RecyclerView rv_home_batter = helper.getView(R.id.rv_home_batter);
        RecyclerView rv_home_bowler = helper.getView(R.id.rv_home_bowler);
        RecyclerView rv_home_wicket = helper.getView(R.id.rv_home_wicket);

        if (!TextUtils.isEmpty(item.name)) {
            helper.setText(R.id.tv_home_name,item.name);
        }
        if (!TextUtils.isEmpty(item.score)) {
            helper.setText(R.id.tv_home_score,item.score);
        }
        if (!TextUtils.isEmpty(item.order)) {
            helper.setText(R.id.tv_home_round,item.order);
        }

        helper.setOnClickListener(R.id.ll_home, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_home.isSelected()) {
                    ll_home.setSelected(false);
                    sv_home.setVisibility(View.GONE);
                    iv_arrow_one.setBackgroundResource(R.mipmap.icon_arrow_down_four);
                } else {
                    ll_home.setSelected(true);
                    sv_home.setVisibility(View.VISIBLE);
                    iv_arrow_one.setBackgroundResource(R.mipmap.icon_arrow_up_two);
                }
            }
        });

        if(item.bean == null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", match_id);
            jsonObject.put("team_id", item.id);
            ApiClient.retrofit().create(ApiStores.class)
                    .getHeadlineReplyList(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback() {
                        @Override
                        public void onSuccess(String data, String msg) {
                            List<ScorecardBatterBean> battingList = JSONObject.parseArray(JSONObject.parseObject(data).getString("batting_info"), ScorecardBatterBean.class);
                            List<ScorecardBowlerBean> bowlList = JSONObject.parseArray(JSONObject.parseObject(data).getString("bowling_info"), ScorecardBowlerBean.class);
                            List<ScorecardWicketBean> wicketList = JSONObject.parseArray(JSONObject.parseObject(data).getString("partnerships"), ScorecardWicketBean.class);
                            item.bean = new CricketMatchBean.ListDataBean(battingList, bowlList, wicketList, JSONObject.parseObject(data).getString("extras"), JSONObject.parseObject(data).getString("no_batting_name"));

                            ScorecardBatterAdapter mHomeBatterAdapter;
                            ScorecardBowlerAdapter mHomeBowlerAdapter;
                            ScorecardWicketAdapter mHomeWicketAdapter;
                            if (battingList != null && battingList.size() > 0) {
                                mHomeBatterAdapter = new ScorecardBatterAdapter(R.layout.item_cricket_scorecard_batter, battingList);
                                rv_home_batter.setLayoutManager(new LinearLayoutManager(mContext));
                                rv_home_batter.setAdapter(mHomeBatterAdapter);
                            }
                            if (!TextUtils.isEmpty(item.bean.getExtras())) {
                                helper.setText(R.id.tv_home_extras,item.bean.getExtras());
                            }
                            if (!TextUtils.isEmpty(item.bean.getNoBattingName())) {
                                helper.setText(R.id.tv_home_no_bat,item.bean.getNoBattingName());
                            }
                            if (bowlList != null && bowlList.size() > 0) {
                                mHomeBowlerAdapter = new ScorecardBowlerAdapter(R.layout.item_cricket_scorecard_bowler, bowlList);
                                rv_home_bowler.setLayoutManager(new LinearLayoutManager(mContext));
                                rv_home_bowler.setAdapter(mHomeBowlerAdapter);
                            }
                            if (wicketList != null && wicketList.size() > 0) {
                                mHomeWicketAdapter = new ScorecardWicketAdapter(R.layout.item_cricket_scorecard_wicket, wicketList);
                                rv_home_wicket.setLayoutManager(new LinearLayoutManager(mContext));
                                rv_home_wicket.setAdapter(mHomeWicketAdapter);
                            }

                        }

                        @Override
                        public void onFailure(String msg) {

                        }

                        @Override
                        public void onError(String msg) {

                        }

                        @Override
                        public void onFinish() {

                        }
                    });
        }else{
             ImageView iv_home_logo = helper.getView(R.id.iv_home_logo);
            GlideUtil.loadUserImageDefault(mContext, "", iv_home_logo);

            ScorecardBatterAdapter mHomeBatterAdapter;
            ScorecardBowlerAdapter mHomeBowlerAdapter;
            ScorecardWicketAdapter mHomeWicketAdapter;
            if (item.bean.getBatterList() != null && item.bean.getBatterList().size() > 0) {
                mHomeBatterAdapter = new ScorecardBatterAdapter(R.layout.item_cricket_scorecard_batter, item.bean.getBatterList());
                rv_home_batter.setLayoutManager(new LinearLayoutManager(mContext));
                rv_home_batter.setAdapter(mHomeBatterAdapter);
            }
            if (!TextUtils.isEmpty(item.bean.getExtras())) {
                helper.setText(R.id.tv_home_extras,item.bean.getExtras());
            }
            if (!TextUtils.isEmpty(item.bean.getNoBattingName())) {
                helper.setText(R.id.tv_home_no_bat,item.bean.getNoBattingName());
            }
            if (item.bean.getBowlerList() != null && item.bean.getBowlerList().size() > 0) {
                mHomeBowlerAdapter = new ScorecardBowlerAdapter(R.layout.item_cricket_scorecard_bowler, item.bean.getBowlerList());
                rv_home_bowler.setLayoutManager(new LinearLayoutManager(mContext));
                rv_home_bowler.setAdapter(mHomeBowlerAdapter);
            }
            if (item.bean.getWicketList() != null && item.bean.getWicketList().size() > 0) {
                mHomeWicketAdapter = new ScorecardWicketAdapter(R.layout.item_cricket_scorecard_wicket, item.bean.getWicketList());
                rv_home_wicket.setLayoutManager(new LinearLayoutManager(mContext));
                rv_home_wicket.setAdapter(mHomeWicketAdapter);
            }
        }
    }

    public RequestBody getRequestBody(JSONObject jsonObject) {
        MediaType CONTENT_TYPE = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(CONTENT_TYPE, jsonObject.toString());
        return requestBody;
    }
}

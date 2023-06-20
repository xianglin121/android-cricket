package com.tencent.liteav.demo.superplayer.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.liteav.demo.superplayer.R;
import com.tencent.liteav.demo.superplayer.adapter.ScorecardBatterAdapter;
import com.tencent.liteav.demo.superplayer.adapter.ScorecardBowlerAdapter;
import com.tencent.liteav.demo.superplayer.adapter.ScorecardWicketAdapter;
import com.tencent.liteav.demo.superplayer.model.ScorecardBatterBean;
import com.tencent.liteav.demo.superplayer.model.ScorecardBowlerBean;
import com.tencent.liteav.demo.superplayer.model.ScorecardWicketBean;

import java.util.ArrayList;
import java.util.List;

public class VodMatchFullScreenView extends RelativeLayout implements View.OnClickListener {
    private Context mContext;
    private TextView tv_tab_scorecard;
    private TextView tv_tab_animation;
    private TextView tv_tab_squad;
    private NestedScrollView ll_scorecard_main;
    private WebView wv_animation;
    private RecyclerView rv_home_batter,rv_home_bowler,rv_home_wicket;
    private TextView tv_home_extras,tv_home_no_bat,tv_scorecard_home,tv_scorecard_away;
    private android.widget.LinearLayout ll_team_tab;
    private ScorecardBatterAdapter mHomeBatterAdapter;
    private ScorecardBowlerAdapter mHomeBowlerAdapter;
    private ScorecardWicketAdapter mHomeWicketAdapter;

    public VodMatchFullScreenView(Context context) {
        super(context);
        init(context);
    }

    public VodMatchFullScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VodMatchFullScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.superplayer_vod_match_full_screen, this);
        tv_tab_scorecard = findViewById(R.id.tv_tab_scorecard);
        tv_tab_animation = findViewById(R.id.tv_tab_animation);
        tv_tab_squad = findViewById(R.id.tv_tab_squad);
        ll_scorecard_main = findViewById(R.id.ll_scorecard_main);
        ll_team_tab = findViewById(R.id.ll_team_tab);
        wv_animation = findViewById(R.id.wv_animation);
        rv_home_batter = findViewById(R.id.rv_home_batter);
        rv_home_batter = findViewById(R.id.rv_home_bowler);
        rv_home_wicket = findViewById(R.id.rv_home_wicket);
        tv_home_extras = findViewById(R.id.tv_home_extras);
        tv_home_no_bat = findViewById(R.id.tv_home_no_bat);
        tv_scorecard_home = findViewById(R.id.tv_scorecard_home);
        tv_scorecard_away = findViewById(R.id.tv_scorecard_away);

        findViewById(R.id.tv_tab_scorecard).setOnClickListener(this);
        findViewById(R.id.tv_tab_animation).setOnClickListener(this);
        findViewById(R.id.tv_tab_squad).setOnClickListener(this);
        findViewById(R.id.tv_scorecard_home).setOnClickListener(this);
        findViewById(R.id.tv_scorecard_away).setOnClickListener(this);

        tv_tab_scorecard.setSelected(true);
        tv_scorecard_home.setSelected(true);
        mHomeBatterAdapter = new ScorecardBatterAdapter(R.layout.item_cricket_scorecard_batter, new ArrayList<ScorecardBatterBean>());
        rv_home_batter.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_home_batter.setAdapter(mHomeBatterAdapter);
        mHomeBowlerAdapter = new ScorecardBowlerAdapter(R.layout.item_cricket_scorecard_bowler, new ArrayList<ScorecardBowlerBean>());
        rv_home_bowler.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_home_bowler.setAdapter(mHomeBowlerAdapter);
        mHomeWicketAdapter = new ScorecardWicketAdapter(R.layout.item_cricket_scorecard_wicket, new ArrayList<ScorecardWicketBean>());
        rv_home_wicket.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_home_wicket.setAdapter(mHomeWicketAdapter);
    }

    public void updateQuality(int type) {
        if (type == 0) {
            tv_tab_scorecard.setSelected(true);
            tv_tab_animation.setSelected(false);
            tv_tab_squad.setSelected(false);
            ll_scorecard_main.setVisibility(VISIBLE);
            ll_team_tab.setVisibility(VISIBLE);
            wv_animation.setVisibility(GONE);
        }else if (type == 1) {
            tv_tab_scorecard.setSelected(false);
            tv_tab_scorecard.setTextColor(Color.parseColor("#FFFFFF"));
            tv_tab_animation.setSelected(true);
            tv_tab_animation.setTextColor(Color.parseColor("#DC3C23"));
            tv_tab_squad.setSelected(false);
            tv_tab_squad.setTextColor(Color.parseColor("#FFFFFF"));
        }else if (type == 2) {
            tv_tab_scorecard.setSelected(false);
            tv_tab_scorecard.setTextColor(Color.parseColor("#FFFFFF"));
            tv_tab_animation.setSelected(false);
            tv_tab_animation.setTextColor(Color.parseColor("#FFFFFF"));
            tv_tab_squad.setSelected(true);
            tv_tab_squad.setTextColor(Color.parseColor("#DC3C23"));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_tab_scorecard) {
            if(tv_tab_scorecard.isSelected()){
                return;
            }
            updateQuality(0);
            if (mCallBack != null) {
                mCallBack.onQualityChange(0);
            }
        }else if (v.getId() == R.id.tv_tab_animation) {
            if (mCallBack != null) {
                mCallBack.onQualityChange(1);
            }
        }else if (v.getId() == R.id.tv_tab_squad) {
            if (mCallBack != null) {
                mCallBack.onQualityChange(2);
            }
        }else if(v.getId() == R.id.tv_scorecard_home){
            if(tv_scorecard_home.isSelected()){
                return;
            }
            tv_scorecard_home.setSelected(true);
            tv_scorecard_away.setSelected(false);
            tv_scorecard_home.setTextColor(Color.BLACK);
            tv_scorecard_away.setTextColor(Color.WHITE);
            //change data
        }else if(v.getId() == R.id.tv_scorecard_away){
            if(tv_scorecard_away.isSelected()){
                return;
            }
            tv_scorecard_home.setSelected(false);
            tv_scorecard_away.setSelected(true);
            tv_scorecard_away.setTextColor(Color.BLACK);
            tv_scorecard_home.setTextColor(Color.WHITE);
            //change data
        }
    }

    private void changeScorecardData(int id,int teamId){
        List<ScorecardBatterBean> batterList = null;
        List<ScorecardBowlerBean> bowlerList = null;
        List<ScorecardWicketBean> wicketList = null;
        String noBattingName = null;
        String extras = null;
        if (batterList != null && batterList.size() > 0) {
            mHomeBatterAdapter.setNewData(batterList);
        }

        if (bowlerList != null && bowlerList.size() > 0) {
            mHomeBowlerAdapter.setNewData(bowlerList);
        }

        if (wicketList != null && wicketList.size() > 0) {
            mHomeWicketAdapter.setNewData(wicketList);
        }

        if (!TextUtils.isEmpty(extras)) {
            tv_home_extras.setText(extras);
        }

        if (!TextUtils.isEmpty(noBattingName)) {
            tv_home_no_bat.setText(noBattingName);
        }
    }

    private CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public interface CallBack {
        void onQualityChange(int type);
    }
}

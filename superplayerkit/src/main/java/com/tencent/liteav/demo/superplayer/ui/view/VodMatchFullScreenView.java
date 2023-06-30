package com.tencent.liteav.demo.superplayer.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.liteav.demo.superplayer.R;
import com.tencent.liteav.demo.superplayer.adapter.CricketScorecardAdapter;
import com.tencent.liteav.demo.superplayer.adapter.ScorecardBatterAdapter;
import com.tencent.liteav.demo.superplayer.adapter.ScorecardBowlerAdapter;
import com.tencent.liteav.demo.superplayer.adapter.ScorecardWicketAdapter;
import com.tencent.liteav.demo.superplayer.adapter.SquadAdapter;
import com.tencent.liteav.demo.superplayer.model.CompetitionBean;
import com.tencent.liteav.demo.superplayer.model.ScorecardBatterBean;
import com.tencent.liteav.demo.superplayer.model.ScorecardBowlerBean;
import com.tencent.liteav.demo.superplayer.model.ScorecardWicketBean;
import com.tencent.liteav.demo.superplayer.model.SquadBean;
import com.tencent.liteav.demo.superplayer.model.SquadDataBean;

import java.util.ArrayList;
import java.util.List;

public class VodMatchFullScreenView extends RelativeLayout implements View.OnClickListener {
    private Context mContext;
    private TextView tv_tab_scorecard;
    private TextView tv_tab_animation;
    private TextView tv_tab_squad;
    private NestedScrollView ll_scorecard_main;
    private WebView mWvAnimation;
    private RecyclerView rv_home_batter,rv_home_bowler,rv_home_wicket;
    private TextView tv_home_extras,tv_home_no_bat;
    private ScorecardBatterAdapter mHomeBatterAdapter;
    private ScorecardBowlerAdapter mHomeBowlerAdapter;
    private ScorecardWicketAdapter mHomeWicketAdapter;
    private CricketScorecardAdapter scorecardAdapter;
    private RecyclerView rv_team;
    private String animationUrl;
    private LinearLayout ll_empty,ll_squad;
    private TextView tv_home_win_rate,tv_away_win_rate,tv_home_name,tv_away_name,tv_tab_home,tv_tab_away;
    private ProgressBar progress_bar;
    private ImageView iv_home_logo,iv_away_logo;
    private RecyclerView rv_squad;
    private SquadAdapter squadAdapter;
    private List<SquadDataBean> squadList;
    private ScrollView scrollView_squad;
    private FrameLayout fl_animation;

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
        mWvAnimation = findViewById(R.id.wv_animation);
        rv_home_batter = findViewById(R.id.rv_home_batter);
        rv_home_bowler = findViewById(R.id.rv_home_bowler);
        rv_home_wicket = findViewById(R.id.rv_home_wicket);
        tv_home_extras = findViewById(R.id.tv_home_extras);
        tv_home_no_bat = findViewById(R.id.tv_home_no_bat);
        rv_team = findViewById(R.id.rv_team);
        ll_empty = findViewById(R.id.ll_empty);
        rv_squad = findViewById(R.id.rv_squad);
        tv_home_win_rate = findViewById(R.id.tv_home_win_rate);
        tv_away_win_rate = findViewById(R.id.tv_away_win_rate);
        tv_home_name = findViewById(R.id.tv_home_name);
        tv_away_name = findViewById(R.id.tv_away_name);
        progress_bar = findViewById(R.id.progress_bar);
        iv_home_logo = findViewById(R.id.iv_home_logo);
        iv_away_logo = findViewById(R.id.iv_away_logo);
        ll_squad = findViewById(R.id.ll_squad);
        tv_tab_home = findViewById(R.id.tv_tab_home);
        tv_tab_away = findViewById(R.id.tv_tab_away);
        scrollView_squad = findViewById(R.id.scrollView_squad);
        fl_animation = findViewById(R.id.fl_animation);

        findViewById(R.id.tv_tab_scorecard).setOnClickListener(this);
        findViewById(R.id.tv_tab_animation).setOnClickListener(this);
        findViewById(R.id.tv_tab_squad).setOnClickListener(this);

        tv_tab_scorecard.setSelected(true);
        tv_tab_home.setSelected(true);
        scorecardAdapter = new CricketScorecardAdapter(R.layout.item_match_team_score_title,new ArrayList<CompetitionBean>());
        scorecardAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(((CompetitionBean)adapter.getData().get(position)).isSelected){
                    return;
                }
                if(mCallBack!=null){
                    getScorecardData(position,((CompetitionBean)adapter.getData().get(position)).id);
                }
            }
        });
        rv_team.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_team.setAdapter(scorecardAdapter);
        mHomeBatterAdapter = new ScorecardBatterAdapter(R.layout.item_scorecard_batter, new ArrayList<ScorecardBatterBean>());
        rv_home_batter.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_home_batter.setAdapter(mHomeBatterAdapter);
        mHomeBowlerAdapter = new ScorecardBowlerAdapter(R.layout.item_scorecard_bowler, new ArrayList<ScorecardBowlerBean>());
        rv_home_bowler.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_home_bowler.setAdapter(mHomeBowlerAdapter);
        mHomeWicketAdapter = new ScorecardWicketAdapter(R.layout.item_scorecard_wicket, new ArrayList<ScorecardWicketBean>());
        rv_home_wicket.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_home_wicket.setAdapter(mHomeWicketAdapter);

        squadAdapter = new SquadAdapter(this,R.layout.item_match_squad, new ArrayList<SquadBean>());
        rv_squad.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_squad.setAdapter(squadAdapter);
        initWebViewOne();

        tv_tab_home.setOnClickListener(this);
        tv_tab_away.setOnClickListener(this);
    }

    public void updateQuality(int type) {
        if (type == 0) {
            tv_tab_scorecard.setSelected(true);
            tv_tab_animation.setSelected(false);
            tv_tab_squad.setSelected(false);
            fl_animation.setVisibility(GONE);
            ll_empty.setVisibility(View.GONE);
            scrollView_squad.setVisibility(GONE);
            ll_squad.setVisibility(GONE);
            if(scorecardAdapter.getData() == null || scorecardAdapter.getData().size()<=0){
                scorecardAdapter.setNewData(new ArrayList<CompetitionBean>());
                rv_team.setVisibility(GONE);
                ll_scorecard_main.setVisibility(GONE);
                ll_empty.setVisibility(View.VISIBLE);
                return;
            }
            rv_team.setVisibility(VISIBLE);
            ll_scorecard_main.setVisibility(VISIBLE);
        }else if (type == 1) {
            tv_tab_scorecard.setSelected(false);
            tv_tab_animation.setSelected(true);
            tv_tab_squad.setSelected(false);
            ll_scorecard_main.setVisibility(GONE);
            rv_team.setVisibility(GONE);
            fl_animation.setVisibility(GONE);
            ll_empty.setVisibility(GONE);
            scrollView_squad.setVisibility(GONE);
            ll_squad.setVisibility(GONE);
            if (TextUtils.isEmpty(animationUrl)) {
                ll_empty.setVisibility(VISIBLE);
                return;
            }
            fl_animation.setVisibility(VISIBLE);
        }else if (type == 2) {
            tv_tab_scorecard.setSelected(false);
            tv_tab_animation.setSelected(false);
            tv_tab_squad.setSelected(true);
            ll_scorecard_main.setVisibility(GONE);
            rv_team.setVisibility(GONE);
            fl_animation.setVisibility(GONE);
            if(squadAdapter.getData() == null || squadAdapter.getData().size()<=0){
                squadAdapter.setNewData(new ArrayList<SquadBean>());
                scrollView_squad.setVisibility(GONE);
                ll_squad.setVisibility(GONE);
                ll_empty.setVisibility(View.VISIBLE);
                return;
            }else{
                scrollView_squad.setVisibility(VISIBLE);
                ll_squad.setVisibility(VISIBLE);
                ll_empty.setVisibility(GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_tab_scorecard && !tv_tab_scorecard.isSelected()) {
            updateQuality(0);
        }else if (v.getId() == R.id.tv_tab_animation && !tv_tab_animation.isSelected()) {
            updateQuality(1);
        }else if (v.getId() == R.id.tv_tab_squad && !tv_tab_squad.isSelected()) {
            updateQuality(2);
        }else if(v.getId() == R.id.tv_tab_home && !tv_tab_home.isSelected()){
            tv_tab_home.setSelected(true);
            tv_tab_away.setSelected(false);
            changeSquadData(0);
        }else if(v.getId() == R.id.tv_tab_away && !tv_tab_away.isSelected()){
            tv_tab_home.setSelected(false);
            tv_tab_away.setSelected(true);
            changeSquadData(1);
        }
    }

    public void setTeamData(int matchId,List<CompetitionBean> list,String url){
        if(matchId == 0){
            return;
        }
        scorecardAdapter.match_id = matchId;
        scorecardAdapter.setNewData(list);

        if(tv_tab_scorecard.isSelected()){
            ll_empty.setVisibility(View.GONE);
            if(list != null && list.size()>0 && mCallBack != null){
                getScorecardData(0,list.get(0).id);
            }else{
                rv_team.setVisibility(GONE);
                ll_scorecard_main.setVisibility(GONE);
                ll_empty.setVisibility(View.VISIBLE);
            }
            this.animationUrl = url;
            if (!TextUtils.isEmpty(animationUrl)) {
                mWvAnimation.loadUrl(animationUrl);
            }
        }



    }

    private CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public interface CallBack {
        void onQualityChange(int type);
        void onGetScorecardData(int index, int teamId);
        void onForwardPlayerProfile(int id);
    }

    private void getScorecardData(int index, int teamId){
        CompetitionBean item = scorecardAdapter.getData().get(index);
        if(item.bean == null && mCallBack != null){
             mCallBack.onGetScorecardData(index,teamId);
        }else{
            setScorecardData(index,item.bean);
        }
    }

    public void setScorecardData(int index, CompetitionBean.ListDataBean dataBean){
        if(scorecardAdapter.getData().get(index).bean == null){
            scorecardAdapter.getData().get(index).bean = dataBean;
        }
        scorecardAdapter.refreshChecked(index);

        if (dataBean.batterList != null && dataBean.batterList.size() > 0) {
            mHomeBatterAdapter.setNewData(dataBean.batterList);
        }

        if (dataBean.bowlerList != null && dataBean.bowlerList.size() > 0) {
            mHomeBowlerAdapter.setNewData(dataBean.bowlerList);
        }

        if (dataBean.wicketList != null && dataBean.wicketList.size() > 0) {
            mHomeWicketAdapter.setNewData(dataBean.wicketList);
        }

        if (!TextUtils.isEmpty(dataBean.extras)) {
            tv_home_extras.setText(dataBean.extras);
        }

        if (!TextUtils.isEmpty(dataBean.noBattingName)) {
            tv_home_no_bat.setText(dataBean.noBattingName);
        }
    }

    public void setSquadData(List<SquadDataBean> bean){
        if(bean == null || bean.size()<=0){
            return;
        }
        squadList = bean;
        squadAdapter.setNewData(bean.get(0).list);
        tv_home_win_rate.setText(bean.get(0).win.homeWinProbabilities + "%");
        tv_away_win_rate.setText(bean.get(0).win.awayWinProbabilities + "%");
        tv_home_name.setText(bean.get(0).win.homeName);
        tv_away_name.setText(bean.get(0).win.awayName);
        progress_bar.setProgress((int) bean.get(0).win.homeWinProbabilities);
        Glide.with(mContext.getApplicationContext()).load(bean.get(0).win.awayLogo).placeholder(R.mipmap.img_team_logo_default).error(R.mipmap.img_team_logo_default).circleCrop()
                .into(iv_home_logo);
        Glide.with(mContext.getApplicationContext()).load(bean.get(0).win.awayLogo).placeholder(R.mipmap.img_team_logo_default).error(R.mipmap.img_team_logo_default).circleCrop()
                .into(iv_away_logo);
        tv_tab_home.setText(bean.get(0).name+bean.get(0).score);
        tv_tab_away.setText(bean.get(1).name+bean.get(1).score);
    }

    public void changeSquadData(int index){
        if(squadList.size()< index){
            return;
        }
        squadAdapter.setNewData(squadList.get(index).list);
    }

    private void initWebViewOne() {
        /* 设置支持Js */
        mWvAnimation.getSettings().setJavaScriptEnabled(true);
        /* 设置为true表示支持使用js打开新的窗口 */
        mWvAnimation.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        /* 设置缓存模式 */
        mWvAnimation.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWvAnimation.getSettings().setDomStorageEnabled(true);

        /* 设置为使用webview推荐的窗口 */
//        mWebView.getSettings().setUseWideViewPort(true);
        /* 设置为使用屏幕自适配 */
        mWvAnimation.getSettings().setLoadWithOverviewMode(true);
        /* 设置是否允许webview使用缩放的功能,我这里设为false,不允许 */
        mWvAnimation.getSettings().setBuiltInZoomControls(false);
        /* 提高网页渲染的优先级 */
        mWvAnimation.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        /* HTML5的地理位置服务,设置为true,启用地理定位 */
        mWvAnimation.getSettings().setGeolocationEnabled(true);
        /* 设置可以访问文件 */
        mWvAnimation.getSettings().setAllowFileAccess(true);

        // 设置UserAgent标识
//        mWebView.getSettings().setUserAgentString(mWebView.getSettings().getUserAgentString() + " app-shikuimapp");
    }

    public void forwardPlayerProfile(int id){
        if(mCallBack != null){
            mCallBack.onForwardPlayerProfile(id);
        }
    }

}

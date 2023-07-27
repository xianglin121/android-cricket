package com.onecric.live.fragment;

import static com.onecric.live.util.TimeUtil.stampToTime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.AppManager;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.CricketDetailActivity;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.LiveMoreActivity;
import com.onecric.live.activity.LiveNotStartDetailActivity;
import com.onecric.live.activity.OneLogInActivity;
import com.onecric.live.adapter.BannerRoundLiveImageAdapter;
import com.onecric.live.adapter.LiveAuthorAdapter;
import com.onecric.live.adapter.LiveHistoryAdapter;
import com.onecric.live.adapter.LiveUpcomingAdapter;
import com.onecric.live.adapter.LiveViewNowAdapter;
import com.onecric.live.event.ToggleTabEvent;
import com.onecric.live.model.BannerBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveAuthorBean;
import com.onecric.live.model.LiveMatchBean;
import com.onecric.live.model.LiveMatchListBean;
import com.onecric.live.model.OneHistoryLiveBean;
import com.onecric.live.model.PlayCardsBean;
import com.onecric.live.presenter.live.OneLivePresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.OneLiveView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.qcloud.tuicore.util.DateTimeUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OneLiveFragment extends MvpFragment<OneLivePresenter> implements OneLiveView, View.OnClickListener{
    private Banner mBanner;
    private TextView tv_see_all;
    private RecyclerView rv_match,rv_author_live,rv_upcoming,rv_history;
    private SmartRefreshLayout smart_rl;
    private RelativeLayout rl_open_live;
    private ImageView iv_live_thumb,iv_home_logo,iv_away_logo,iv_author_live_now,iv_match_live_now,iv_match_error,iv_state_live;
    private TextView tv_views_number,tv_live_title,tv_home_name,tv_away_name,tv_view_all_upcoming,tv_view_all_history,tv_view_all_live,
            tv_author_title,tv_match_title,tv_match_error,tv_upcoming_error,tv_author_error,tv_upcoming_title,tv_home_score,tv_away_score,
            tv_home_score2,tv_away_score2,tv_state_info,tv_state_time;
    private CardView cv_match;

    private LiveAuthorBean selectLiveBean;
    private BannerRoundLiveImageAdapter bannerRoundLiveImageAdapter;
//    private LiveVideoAdapter mRecommendLiveAdapter;
    private LiveViewNowAdapter mMatchAdapter;
    private LiveAuthorAdapter mAnchorAdapter;
    private LiveUpcomingAdapter mUpcomingAdapter;
    private LiveHistoryAdapter mHistoryAdapter;
    private Timer mTimer;
    private List<PlayCardsBean> todayList;
    private boolean isOpenMatch = false;
    private Drawable drawableArrUp,drawableArrDown,drawableArrRed,drawableArrTransparent;
    private CountDownTimer timer;
    private ImageView iv_advert;
    private String advertUrl;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_view_all_live:
                if(todayList == null || todayList.size()<=2){
                    tv_view_all_live.setVisibility(View.GONE);
                    return;
                }
                tv_view_all_live.setVisibility(View.VISIBLE);
                if(isOpenMatch){//收缩
                    tv_view_all_live.setCompoundDrawables(null, null, drawableArrDown, null);
                    tv_view_all_live.setText(getString(R.string.show_all_live_games));
                    mMatchAdapter.setNewData(todayList.subList(0,2));
                }else{
                    tv_view_all_live.setCompoundDrawables(null, null, drawableArrUp, null);
                    tv_view_all_live.setText(getString(R.string.take_back));
                    mMatchAdapter.setNewData(todayList);
                }
                isOpenMatch = !isOpenMatch;
//                EventBus.getDefault().post(new ToggleTabEvent(21));
                break;
            case R.id.rl_open_live:
                if(selectLiveBean!=null){
                    if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                        OneLogInActivity.forward(getContext());
                    } else if (selectLiveBean.islive == 0) {
                        LiveNotStartDetailActivity.forward(getContext(), selectLiveBean.uid,selectLiveBean.matchId,selectLiveBean.id);
                    } else{
                        LiveDetailActivity.forward(getContext(), selectLiveBean.uid,selectLiveBean.matchId,selectLiveBean.id);
                    }
                }
                break;
            case R.id.tv_view_all_upcoming:
                EventBus.getDefault().post(new ToggleTabEvent(2));
                break;
            case R.id.tv_view_all_history:
                //历史播放列表
                LiveMoreActivity.forward(getContext(), 2);
                break;
        }
    }

    @Override
    protected void initUI() {
        mBanner = findViewById(R.id.banner_live);
        tv_see_all = findViewById(R.id.tv_see_all);
        rv_match = findViewById(R.id.rv_match);
        rv_upcoming = findViewById(R.id.rv_upcoming);
        rv_history = findViewById(R.id.rv_history);
        rv_author_live = findViewById(R.id.rv_author_live);
        smart_rl = findViewById(R.id.smart_rl);
        cv_match = findViewById(R.id.cv_match);

        rl_open_live = findViewById(R.id.rl_open_live);
        iv_live_thumb = findViewById(R.id.iv_live_thumb);
        iv_home_logo = findViewById(R.id.iv_home_logo);
        iv_away_logo = findViewById(R.id.iv_away_logo);
        tv_views_number = findViewById(R.id.tv_views_number);
        tv_live_title = findViewById(R.id.tv_live_title);
        tv_home_name = findViewById(R.id.tv_home_name);
        tv_away_name = findViewById(R.id.tv_away_name);
        tv_view_all_upcoming = findViewById(R.id.tv_view_all_upcoming);
        tv_view_all_history = findViewById(R.id.tv_view_all_history);
        tv_view_all_live = findViewById(R.id.tv_view_all_live);
        tv_author_title = findViewById(R.id.tv_author_title);
        tv_match_title = findViewById(R.id.tv_match_title);
        tv_match_error = findViewById(R.id.tv_match_error);
        tv_upcoming_error = findViewById(R.id.tv_upcoming_error);
        tv_author_error = findViewById(R.id.tv_author_error);
        iv_author_live_now = findViewById(R.id.iv_author_live_now);
        iv_match_live_now = findViewById(R.id.iv_match_live_now);
        iv_match_error = findViewById(R.id.iv_match_error);
        tv_upcoming_title = findViewById(R.id.tv_upcoming_title);
        tv_home_score = findViewById(R.id.tv_home_score);
        tv_away_score = findViewById(R.id.tv_away_score);
        tv_home_score2 = findViewById(R.id.tv_home_score2);
        tv_away_score2 = findViewById(R.id.tv_away_score2);
        tv_state_info = findViewById(R.id.tv_state_info);
        tv_state_time = findViewById(R.id.tv_state_time);
        iv_state_live = findViewById(R.id.iv_state_live);

        iv_advert = findViewById(R.id.iv_advert);
        android.view.ViewGroup.LayoutParams pp4 = iv_advert.getLayoutParams();
        pp4.height = (int) (UIUtil.getScreenWidth(getContext())/8);//8:1
        iv_advert.setLayoutParams(pp4);
        iv_advert.setOnClickListener(v -> {
            if(!TextUtils.isEmpty(advertUrl)){
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(advertUrl);
                intent.setData(content_url);
                startActivity(intent);
            }
        });

        rl_open_live.setOnClickListener(this);
        tv_view_all_upcoming.setOnClickListener(this);
        tv_view_all_history.setOnClickListener(this);
        tv_view_all_live.setOnClickListener(this);

        int width = UIUtil.getScreenWidth(getContext());
        android.view.ViewGroup.LayoutParams pp = mBanner.getLayoutParams();
        pp.height = (int)((width*0.9-20) * 0.48);//0.78
        mBanner.setLayoutParams(pp);

        drawableArrUp = getResources().getDrawable(R.mipmap.ic_arr_top_red);
        drawableArrUp.setBounds(0, 0, drawableArrUp.getMinimumWidth(), drawableArrUp.getMinimumHeight());
        drawableArrDown = getResources().getDrawable(R.mipmap.ic_arr_down_red);
        drawableArrDown.setBounds(0, 0, drawableArrDown.getMinimumWidth(), drawableArrDown.getMinimumHeight());
        drawableArrRed = AppManager.mContext.getDrawable(R.mipmap.icon_arrow_left_two);
        drawableArrRed.setBounds(0,0,drawableArrRed.getMinimumWidth(),drawableArrRed.getMinimumHeight());
        drawableArrTransparent = AppManager.mContext.getDrawable(R.mipmap.img_transparent1624);
        drawableArrTransparent.setBounds(0,0,drawableArrTransparent.getMinimumWidth(),drawableArrTransparent.getMinimumHeight());

        //rv
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setEnableLoadMore(false);
        smart_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getBannerList(1);
                mvpPresenter.getBannerList(2);
                mvpPresenter.getUpComingList();
                mvpPresenter.getAllData();
                mvpPresenter.getPlayingCards();
                mvpPresenter.getHistoryList(1);
            }
        });

        initMatchList();
        smart_rl.autoRefresh();
    }

    private void initMatchList(){
        mMatchAdapter = new LiveViewNowAdapter(getContext(),new ArrayList<>());
        mMatchAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                OneLogInActivity.forward(getContext());
            }else if (mMatchAdapter.getItem(position).liveStatus == 0) {
                LiveNotStartDetailActivity.forward(getContext(),mMatchAdapter.getItem(position).liveUid,
                        mMatchAdapter.getItem(position).id,mMatchAdapter.getItem(position).liveId);
            } else{
                LiveDetailActivity.forward(getContext(),mMatchAdapter.getItem(position).liveUid,
                        mMatchAdapter.getItem(position).id,mMatchAdapter.getItem(position).liveId);
            }
        });
        rv_match.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_match.setAdapter(mMatchAdapter);

        mAnchorAdapter = new LiveAuthorAdapter(R.layout.item_live_anchor_2, new ArrayList<>());
        mAnchorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(!mAnchorAdapter.getItem(position).isSelected){
                    selectLiveBean = mAnchorAdapter.getItem(position);
                    selectLiveBean.isSelected = true;
                    mAnchorAdapter.refreshStatus(selectLiveBean.id);
                    refreshLive(false);
                }
            }
        });
        rv_author_live.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rv_author_live.setAdapter(mAnchorAdapter);

        mUpcomingAdapter = new LiveUpcomingAdapter(R.layout.item_upcoming,new ArrayList<>());
        View inflate2 = LayoutInflater.from(getContext()).inflate(R.layout.item_empty_upcoming, null, false);
        mUpcomingAdapter.setEmptyView(inflate2);
        mUpcomingAdapter.setOnItemClickListener((adapter, view, position) -> {
            if(mUpcomingAdapter.getItem(position).getMatch_id()!=0){
                CricketDetailActivity.forward(getContext(), mUpcomingAdapter.getItem(position).getMatch_id());
            }
        });
        rv_upcoming.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        rv_upcoming.setAdapter(mUpcomingAdapter);

        mHistoryAdapter = new LiveHistoryAdapter(R.layout.item_history,new ArrayList<>());
        View inflate3 = LayoutInflater.from(getContext()).inflate(R.layout.item_empty_history, null, false);
        mHistoryAdapter.setEmptyView(inflate3);
        mHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            String url = mHistoryAdapter.getItem(position).mediaUrl;
            if (TextUtils.isEmpty(url)) {
                return;
            }
            if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                OneLogInActivity.forward(getContext());
            }else{
                LiveDetailActivity.forward(getContext(),mHistoryAdapter.getItem(position).liveUid,mHistoryAdapter.getItem(position).matchId,
                        mHistoryAdapter.getItem(position).mediaUrl,mHistoryAdapter.getItem(position).liveId);
            }
        });
        rv_history.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        rv_history.setAdapter(mHistoryAdapter);

    }

    @Override
    protected void initData() {
        refreshTodayData();
    }

    private void refreshLive(boolean isSame){
        if(selectLiveBean == null){
            return;
        }
//       GlideUtil.loadUpdatesImageDefault(getContext(), selectLiveBean.thumb, iv_live_thumb);
        if(!isSame){
            GlideUtil.loadUpdatesImageDefault10(getContext(),selectLiveBean.thumb,iv_live_thumb);
            tv_live_title.setText(selectLiveBean.tournament);
            GlideUtil.loadTeamImageDefault(getContext(), selectLiveBean.homeLogo, iv_home_logo);
            tv_home_name.setText(TextUtils.isEmpty(selectLiveBean.homeName)?"":selectLiveBean.homeName);
            GlideUtil.loadTeamImageDefault(getContext(), selectLiveBean.awayLogo, iv_away_logo);
            tv_away_name.setText(TextUtils.isEmpty(selectLiveBean.awayName)?"":selectLiveBean.awayName);
            tv_home_score.setTextColor(getResources().getColor(R.color.c_111111));
            tv_away_score.setTextColor(getResources().getColor(R.color.c_111111));
            tv_home_score.setCompoundDrawables(null,null,drawableArrTransparent,null);
            tv_away_score.setCompoundDrawables(null,null,drawableArrTransparent,null);
            tv_state_time.setVisibility(View.GONE);
            iv_state_live.setVisibility(View.GONE);
            tv_state_info.setVisibility(View.GONE);
        }

        tv_views_number.setText(selectLiveBean.heat>1000?String.format("%.1f",(float)selectLiveBean.heat/1000)+"K":selectLiveBean.heat+"");

        if (selectLiveBean.status == 2) {//已结束
            tv_state_info.setVisibility(View.VISIBLE);
            tv_state_info.setText(getString(R.string.completed));
            if (selectLiveBean.homeId == selectLiveBean.winnerId) {//主赢 右边
                tv_home_score.setCompoundDrawables(null,null,drawableArrRed,null);
                tv_away_score.setTextColor(getResources().getColor(R.color.c_999999));
                tv_away_score2.setTextColor(getResources().getColor(R.color.c_999999));
            } else if(selectLiveBean.awayId == selectLiveBean.winnerId){//客赢
                tv_away_score.setCompoundDrawables(null,null,drawableArrRed,null);
                tv_home_score.setTextColor(getResources().getColor(R.color.c_999999));
                tv_home_score2.setTextColor(getResources().getColor(R.color.c_999999));
            }
        }else if (selectLiveBean.status == 0) {//未开始
            if(TextUtils.isEmpty(selectLiveBean.scheduled)){
                return;
            }
            tv_state_time.setVisibility(View.VISIBLE);
            tv_state_info.setVisibility(View.VISIBLE);

            try {
                long time = DateTimeUtil.getStringToDate(selectLiveBean.scheduled, "yyyy-MM-dd HH:mm:ss");
                String st = stampToTime(time,"hh:mm a");
                tv_state_time.setText(Html.fromHtml("<strong>"+st.substring(0,5)+"</strong> <small>"+st.substring(5)+"</small>"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

/*            try{
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(selectLiveBean.scheduled.substring(11,13)));
                cal.set(Calendar.SECOND, Integer.parseInt(selectLiveBean.scheduled.substring(14,16)));
                cal.set(Calendar.MINUTE, Integer.parseInt(selectLiveBean.scheduled.substring(17)));
                tv_state_info.setText(getString(R.string.watch_live_at));
                if(!isSame){
                    timer.cancel();
                    timer = null;
                }
                if((cal.getTimeInMillis() - new Date().getTime())/1000<3600){
                    if(timer == null){
                        timer = new CountDownTimer((cal.getTimeInMillis() - new Date().getTime()), 1000) {
                            public void onTick(long millisUntilFinished) {
                                tv_state_time.setText(Html.fromHtml("<strong>" + TimeUtil.timeConversion(millisUntilFinished/1000) + "</strong>"));
                            }

                            public void onFinish() {
                                selectLiveBean.status = 1;
                                tv_state_time.setVisibility(View.GONE);
                                tv_state_info.setVisibility(View.GONE);
                                iv_state_live.setVisibility(View.VISIBLE);
                            }
                        }.start();
                    }
                }else{
                    tv_state_time.setText(Html.fromHtml("<strong>" + TimeUtil.timeConversion((cal.getTimeInMillis() - new Date().getTime())/1000) + "</strong>"));
                }
            }catch (Exception e){
                try {
                    long time = DateTimeUtil.getStringToDate(selectLiveBean.scheduled, "yyyy-MM-dd HH:mm:ss");
                    String st = stampToTime(time,"hh:mm a");
                    tv_state_time.setText(Html.fromHtml("<strong>"+st.substring(0,5)+"</strong> <small>"+st.substring(5)+"</small>"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }*/
            //转时间戳 得到倒计时毫秒数
/*            long time = DateTimeUtil.getStringToDate(selectLiveBean.scheduled, "yyyy-MM-dd HH:mm:ss");
            long countTime = time - new Date().getTime();
            if (countTime > 0) {
                tv_state_info.setText(getString(R.string.watch_live_at));
                tv_state_time.setText(Html.fromHtml("<strong>" + TimeUtil.timeConversion(countTime/1000) + "</strong>"));
            }else{
                tv_state_info.setText(getString(R.string.watch_live_at));
                try{
                    String st = stampToTime(time,"hh:mm a");
                    tv_state_time.setText(Html.fromHtml("<strong>"+st.substring(0,5)+"</strong> <small>"+st.substring(5)+"</small>"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }*/
        } else {//已开始
            tv_state_time.setVisibility(View.GONE);
            tv_state_info.setVisibility(View.GONE);
            iv_state_live.setVisibility(View.VISIBLE);
        }

        tv_home_score2.setText("");
        if (!TextUtils.isEmpty(selectLiveBean.homeDisplayOvers)) {
            if(selectLiveBean.homeDisplayOvers.contains("0/0")){
                tv_home_score.setText("");
                tv_home_score2.setText(getString(R.string.yet_to_bat));
            }else if(selectLiveBean.homeDisplayOvers.equals("0")){
                tv_home_score.setText("");
            }else{
                String scoreStr = selectLiveBean.homeDisplayOvers;
                if (scoreStr.contains(" ")){
                    String[] split = scoreStr.split(" ");
                    tv_home_score2.setText(split[1]);
                    scoreStr = split[0];
                }
                if(scoreStr.contains("&")){
                    SpannableStringBuilder builder = new SpannableStringBuilder(scoreStr);
                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#99111111")), 0, scoreStr.indexOf("&"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    tv_home_score.setText(builder);
                }else{
                    tv_home_score.setText(scoreStr);
                }
            }
        } else {
            tv_home_score.setText("");
        }

        tv_away_score2.setText("");
        if (!TextUtils.isEmpty(selectLiveBean.awayDisplayOvers)) {
            if(selectLiveBean.awayDisplayOvers.contains("0/0")){
                tv_away_score.setText("");
                tv_away_score2.setText(getString(R.string.yet_to_bat));
            }else if(selectLiveBean.awayDisplayOvers.equals("0")){
                tv_away_score.setText("");
            }else{
                String scoreStr = selectLiveBean.awayDisplayOvers;
                if (scoreStr.contains(" ")){
                    String[] split = scoreStr.split(" ");
                    tv_away_score2.setText(split[1]);
                    scoreStr = split[0];
                }
                if(scoreStr.contains("&")){
                    SpannableStringBuilder builder = new SpannableStringBuilder(scoreStr);
                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#99111111")), 0, scoreStr.indexOf("&"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    tv_away_score.setText(builder);
                }else{
                    tv_away_score.setText(scoreStr);
                }
            }
        } else {
            tv_away_score.setText("");
        }

    }

    @Override
    public void getAllDataSuccess(List<LiveAuthorBean> aList) {
        smart_rl.finishRefresh();
        if (aList != null && aList.size()>0) {
            mAnchorAdapter.setNewData(aList);
            if(iv_author_live_now.getVisibility() == View.GONE){
                iv_author_live_now.setVisibility(View.VISIBLE);
                tv_author_title.setVisibility(View.VISIBLE);
                tv_author_error.setVisibility(View.GONE);
            }
            if(selectLiveBean!=null){
                for(LiveAuthorBean bean :aList){
                    if(selectLiveBean.id == bean.id){
                        selectLiveBean = bean;
                        selectLiveBean.isSelected = true;
                        refreshLive(true);
                        return;
                    }
                }
            }

            selectLiveBean = aList.get(0);
            selectLiveBean.isSelected = true;
            mAnchorAdapter.refreshStatus(selectLiveBean.id);
            if(rl_open_live.getVisibility() == View.GONE){
                rl_open_live.setVisibility(View.VISIBLE);
            }
            refreshLive(false);
        }else{
            tv_author_error.setVisibility(View.GONE);
            iv_author_live_now.setVisibility(View.GONE);
            tv_author_title.setVisibility(View.GONE);
            rl_open_live.setVisibility(View.GONE);
            mAnchorAdapter.setNewData(new ArrayList<>());
            selectLiveBean = null;
        }
    }

    @Override
    public void getDataSuccess(List<LiveMatchBean> list) {

    }

    @Override
    public void getBannerSuccess(List<BannerBean> list) {
        if (list != null && list.size() > 0) {
            mBanner.setVisibility(View.VISIBLE);
            //添加画廊效果
            mBanner.setBannerGalleryMZ(20, 0);
            bannerRoundLiveImageAdapter = new BannerRoundLiveImageAdapter(list) {
                @Override
                public void onBindView(Object holder, Object data, int position, int size) {
                    BannerBean bannerBean = (BannerBean) data;
                    Glide.with(getContext()).load(bannerBean.getImg()).priority(Priority.HIGH).into(((BannerRoundLiveImageHolder) holder).imageView);

                    /*if(TextUtils.isEmpty(bannerBean.match_status)){
                        ((BannerRoundLiveImageHolder)holder).tv_banner_title.setVisibility(View.GONE);
                        ((BannerRoundLiveImageHolder)holder).tv_banner_btn.setVisibility(View.GONE);
                        ((BannerRoundLiveImageHolder)holder).tv_banner_into.setVisibility(View.GONE);
                        return;
                    }

                    if("ended".equals(bannerBean.match_status)){
                        ((BannerRoundLiveImageHolder)holder).tv_banner_btn.setVisibility(View.INVISIBLE);
                    }else if("live".equals(bannerBean.match_status)){
                        ((BannerRoundLiveImageHolder)holder).tv_banner_btn.setText(R.string.open_live);
                        ((BannerRoundLiveImageHolder)holder).tv_banner_btn.setVisibility(View.VISIBLE);
                    }else if("not_started".equals(bannerBean.match_status)){
                        ((BannerRoundLiveImageHolder)holder).tv_banner_btn.setText(R.string.open_watch);
                        ((BannerRoundLiveImageHolder)holder).tv_banner_btn.setVisibility(View.VISIBLE);
                    }

                    if(!TextUtils.isEmpty(bannerBean.tournament)){
                        ((BannerRoundLiveImageHolder)holder).tv_banner_into.setText(bannerBean.tournament);
                    }

                    if(!TextUtils.isEmpty(bannerBean.battle) ){
                        ((BannerRoundLiveImageHolder)holder).tv_banner_title.setText(bannerBean.battle);

                    }*/
                }
            };

            bannerRoundLiveImageAdapter.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(Object data, int position) {
                    BannerBean bannerBean = (BannerBean) data;
                    if("live".equals(bannerBean.match_status) && bannerBean.getAnchor_id() != 0 && !TextUtils.isEmpty(bannerBean.match) && bannerBean.getLive_id() != 0){
                        if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                            OneLogInActivity.forward(getContext());
                        }else{
                            LiveDetailActivity.forward(getContext(), bannerBean.getAnchor_id(), Integer.parseInt(bannerBean.match), bannerBean.getLive_id());
                        }
                    }else if(!TextUtils.isEmpty(bannerBean.match) && Integer.parseInt(bannerBean.match)!=0){
                        CricketDetailActivity.forward(getActivity(), Integer.parseInt(bannerBean.match));
                    }else if(!TextUtils.isEmpty(bannerBean.getUrl())){
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(bannerBean.getUrl());
                        intent.setData(content_url);
                        startActivity(intent);
                    }

                }
            });

            if (mBanner.getAdapter() == null) {
                mBanner.setAdapter(bannerRoundLiveImageAdapter);
                mBanner.addBannerLifecycleObserver(this);
            } else {
                mBanner.getAdapter().notifyDataSetChanged();
            }
        }else{
            mBanner.setVisibility(View.GONE);
        }
    }

    @Override
    public void getMatchSuccess(List<PlayCardsBean> today) {
        if (today != null && today.size()>0) {
            today.get(0).type = 1;
            todayList = today;
            if(todayList.size()<=2){
                tv_view_all_live.setVisibility(View.GONE);
                mMatchAdapter.setNewData(today);
            }else if(isOpenMatch){
                tv_view_all_live.setCompoundDrawables(null, null, drawableArrUp, null);
                tv_view_all_live.setText(getString(R.string.take_back));
                tv_view_all_live.setVisibility(View.VISIBLE);
                mMatchAdapter.setNewData(todayList);
            }else{
                tv_view_all_live.setCompoundDrawables(null, null, drawableArrDown, null);
                tv_view_all_live.setText(getString(R.string.show_all_live_games));
                tv_view_all_live.setVisibility(View.VISIBLE);
                mMatchAdapter.setNewData(todayList.subList(0,2));
            }

            if(iv_match_live_now.getVisibility() == View.GONE){
                iv_match_live_now.setVisibility(View.VISIBLE);
                tv_match_title.setVisibility(View.VISIBLE);
                tv_match_error.setVisibility(View.GONE);
                iv_match_error.setVisibility(View.GONE);
                cv_match.setVisibility(View.VISIBLE);
            }
        }else{
            todayList = null;
            mMatchAdapter.setNewData(new ArrayList<>());
            if(iv_match_live_now.getVisibility() == View.VISIBLE){
                iv_match_live_now.setVisibility(View.GONE);
                tv_match_title.setVisibility(View.GONE);
                tv_match_error.setVisibility(View.VISIBLE);
                iv_match_error.setVisibility(View.VISIBLE);
                cv_match.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void getUpcomingSuccess(List<LiveMatchListBean.MatchItemBean> list) {
        if(list != null && list.size()>0){
            mUpcomingAdapter.setNewData(list);
            if(tv_upcoming_error.getVisibility() == View.VISIBLE){
                tv_upcoming_title.setVisibility(View.VISIBLE);
                tv_upcoming_error.setVisibility(View.GONE);
            }
        }else{
            mUpcomingAdapter.setNewData(new ArrayList<>());
        }
    }

    @Override
    public void getDataHistorySuccess(List<OneHistoryLiveBean> list) {
        if(list != null && list.size()>0){
            mHistoryAdapter.setNewData(list);
        }else{
            mHistoryAdapter.setNewData(new ArrayList<>());
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {
        smart_rl.finishRefresh();
        ToastUtil.show(msg);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one_live2;
    }

    @Override
    protected OneLivePresenter createPresenter() {
        return new OneLivePresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null) {
            mBanner.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBanner != null) {
            mBanner.destroy();
        }
        if(mTimer != null){
            mTimer.cancel();
        }
    }

    /**
     * 每10s刷新
     */
    private void refreshTodayData(){
/*        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(getActivity()!=null && ((PowerManager)((getActivity().getSystemService(Context.POWER_SERVICE)))).isScreenOn() &&
                        getActivity().getWindow().getDecorView().getVisibility() == View.VISIBLE ){
                   mvpPresenter.getPlayingCards();
                   mvpPresenter.getAllData();
                }
            }
        }, 10000, 5000);*/

        final Handler handler = new Handler();
        mTimer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if(getActivity()!=null && ((PowerManager)((getActivity().getSystemService(Context.POWER_SERVICE)))).isScreenOn() &&
                                getActivity().getWindow().getDecorView().getVisibility() == View.VISIBLE ){
                            mvpPresenter.getPlayingCards();
                            mvpPresenter.getAllData();
                        }
                    }
                });
            }
        };
        mTimer.schedule(doAsynchronousTask, 500, 10000);
    }

    public void getAdvertSuccess(String img,String url){
        if(!TextUtils.isEmpty(img)){
            iv_advert.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(img).dontAnimate().into(iv_advert);
        }
        if(!TextUtils.isEmpty(url)){
            advertUrl = url;
        }
    }
}

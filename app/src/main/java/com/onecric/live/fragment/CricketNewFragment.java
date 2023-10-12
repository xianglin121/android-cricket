package com.onecric.live.fragment;


import static com.onecric.live.util.AnimUtils.transAnim;
import static com.onecric.live.util.TimeUtil.getDayInfo;
import static com.onecric.live.util.TimeUtil.toTodayIntDay;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.github.gzuliyujiang.calendarpicker.CalendarPicker;
import com.github.gzuliyujiang.calendarpicker.core.ColorScheme;
import com.onecric.live.AppManager;
import com.onecric.live.R;
import com.onecric.live.activity.SearchMatchActivity;
import com.onecric.live.adapter.CricketDayAdapter;
import com.onecric.live.adapter.CricketFiltrateAdapter;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.fragment.dialog.TournamentDialog;
import com.onecric.live.model.CricketAllBean;
import com.onecric.live.model.CricketFiltrateBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.cricket.CricketNewPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.CricketNewView;
import com.onecric.live.view.MvpFragment;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public class CricketNewFragment extends MvpFragment<CricketNewPresenter> implements CricketNewView,View.OnClickListener {
    private RecyclerView rv_filtrate;
    private TextView tv_day;
    private TextView tv_date;
    private TextView tv_month;
    private TextView tv_live_now;
    private TextView tv_tours_num;
    private ImageView iv_all;
    private ImageView iv_all_author;
    private ImageView iv_all_match;
    private LinearLayout ll_streaming;
    private ImageView iv_streaming;
    private TextView tv_streaming;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerView;
    private LinearLayout ll_tours;
    private TextView tv_to_today;
    private TextView tv_fresh;
    private TextView tv_calendar;

    private CricketDayAdapter mAdapter;
    public CricketFiltrateAdapter mFiltrateAdapter;
    private LoginDialog loginDialog;

    private boolean isLiveNow = false;
    private int streamType = 0;
    private int selectToursNum = 0;
    private Dialog mStreamDialog;
    public List<CricketFiltrateBean> filtrateCheckedList;
    public String tag = "";
    private boolean isNotNetWork;

    private long singleTimeInMillis;
    private CalendarPicker picker;
    private RecyclerViewSkeletonScreen filtrateSkeletonScreen;//会导致回到每次rv数量变化会跳回第一页

    private String lastDay,lastDay2;
    private String endDay,endDay2;
    public int todayPosition = 0;
    private Drawable drawableTop,drawableDown;
    private LinearLayout skeletonLoadLayout;
    private Timer mTimer;
    private TournamentDialog tournamentDialog;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private LinearLayoutManager linearLayoutManager;
    private int todayPostion;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricket2;
    }

    @Override
    protected CricketNewPresenter createPresenter() {
        return new CricketNewPresenter(this);
    }

    public void setLoginDialog(LoginDialog dialog) {
        this.loginDialog = dialog;
    }

    @Override
    protected void initUI() {
        rv_filtrate = findViewById(R.id.rv_filtrate);
        tv_day = findViewById(R.id.tv_day);
        tv_date = findViewById(R.id.tv_date);
        tv_month = findViewById(R.id.tv_month);
        tv_live_now = findViewById(R.id.tv_live_now);
        tv_tours_num = findViewById(R.id.tv_tours_num);
        smart_rl = findViewById(R.id.smart_rl);
        recyclerView = findViewById(R.id.recyclerView);
        ll_tours = findViewById(R.id.ll_tours);
        tv_to_today = findViewById(R.id.tv_to_today);
        tv_live_now.setOnClickListener(this);
        tv_fresh = findViewById(R.id.tv_fresh);
        skeletonLoadLayout = findViewById(R.id.ll_skeleton);
        tv_fresh.setOnClickListener(this);
        findViewById(R.id.tv_search).setOnClickListener(this);
        tv_calendar = findViewById(R.id.tv_calendar);
        tv_calendar.setOnClickListener(this);
        findViewById(R.id.tv_to_today).setOnClickListener(this);
        ll_tours.setOnClickListener(this);
        initStreamDialog();
        initCalendarPicker();
        drawableTop = getContext().getDrawable(R.mipmap.ic_go_today_up);
        drawableTop.setBounds(0,0,drawableTop.getMinimumWidth(),drawableTop.getMinimumHeight());
        drawableDown = getContext().getDrawable(R.mipmap.ic_go_today_down);
        drawableDown.setBounds(0,0,drawableDown.getMinimumWidth(),drawableDown.getMinimumHeight());
        tournamentDialog = new TournamentDialog(getContext(),this,new TournamentDialog.OnSelectTourListener() {
            @Override
            public void selectedWord(String tourIds, int checkNum) {
                tag = tourIds;
                selectToursNum = checkNum;
                tv_tours_num.setText(selectToursNum+"");
                tv_tours_num.setVisibility(selectToursNum > 0 ? View.VISIBLE : View.GONE);
                mFiltrateAdapter.notifyDataSetChanged();
                requestList(1);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        //------------TOP
        filtrateCheckedList = new ArrayList<>();
        mFiltrateAdapter = new CricketFiltrateAdapter(R.layout.item_cricket_filtrate,new ArrayList<>());
        mFiltrateAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if(view.getId() == R.id.tv_name){
                mFiltrateAdapter.getItem(position).setCheck(!mFiltrateAdapter.getItem(position).isCheck());
                int id = mFiltrateAdapter.getData().get(position).getId();
                //联动top
                List<CricketFiltrateBean> tList = new ArrayList<>();
                if(tournamentDialog.mAdapter!=null && tournamentDialog.mAdapter.getItemCount()>0){
                    tList.addAll(tournamentDialog.mAdapter.getData());
                    for(int i=0; i<tList.size();i++){
                        if(id == tournamentDialog.mAdapter.getData().get(i).getId()){
                            //刚刚选中的前挪
                            tList.get(i).setCheck(mFiltrateAdapter.getItem(position).isCheck());
                            tournamentDialog.mAdapter.addData(0,tList.get(i));
                            tournamentDialog.mAdapter.remove(i+1);
                            tournamentDialog.mAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }


                StringBuilder tagsId = new StringBuilder(tag);
                if(mFiltrateAdapter.getData().get(position).isCheck()){
                    ++selectToursNum;
                    //已选的里 没有这条就添加
                    if(!tag.contains(","+id) && !tag.contains(id+",")){
                        tagsId.append(id+",");
                    }
                    filtrateCheckedList.add(mFiltrateAdapter.getData().get(position));
                    mFiltrateAdapter.addData(0,mFiltrateAdapter.getData().get(position));
                    mFiltrateAdapter.remove(position+1);
                }else{
                    --selectToursNum;
                    //已选的里 有这条就移除
                    if(tag.contains(id+",")){
                        tagsId.delete(tagsId.indexOf(id+","),
                                tagsId.indexOf(id+",")+(id+",").length());
                    }
                    filtrateCheckedList.remove(mFiltrateAdapter.getData().get(position));
                    mFiltrateAdapter.addData(mFiltrateAdapter.getData().get(position));
                    mFiltrateAdapter.remove(position);
                }
                if(TextUtils.isEmpty(tagsId.toString())){
                    tag = "";
                }else{
                    tag = tagsId.toString();
                }

                rv_filtrate.smoothScrollToPosition(0);
                tv_tours_num.setText(selectToursNum + "");
                tv_tours_num.setVisibility(selectToursNum > 0 ? View.VISIBLE : View.GONE);
/*                if(filtrateCheckedList.size()>0){
                    StringBuilder tagsId = new StringBuilder();
                    for(CricketFiltrateBean bean : filtrateCheckedList){
                        tagsId.append(","+bean.getId());
                    }
                    tag = tagsId.substring(1);
                }else{
                    tag = "";
                }*/
                requestList(1);
            }
        });

        View headerStreamingView = LayoutInflater.from(getContext()).inflate(R.layout.item_cricket_filtrate_header, null);
        ll_streaming = headerStreamingView.findViewById(R.id.ll_streaming);
        iv_streaming = headerStreamingView.findViewById(R.id.iv_streaming);
        tv_streaming = headerStreamingView.findViewById(R.id.tv_streaming);
        ll_streaming.setOnClickListener(v -> {
            if (mStreamDialog != null) {
                mStreamDialog.show();
            }
        });
        mFiltrateAdapter.addHeaderView(headerStreamingView,-1, LinearLayout.HORIZONTAL);

        rv_filtrate.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        rv_filtrate.setAdapter(mFiltrateAdapter);

        //----------LIST
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
//        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableLoadMore(false);
/*        smart_rl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //后一天
                requestList(2);
            }
        });*/

        smart_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //前一天
                requestList(0);
            }
        });

//        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 20);
//        recyclerView.setItemViewCacheSize(20);
//        mAdapter = new CricketDayAdapter(this,R.layout.item_cricket_day, new ArrayList<>());
        mAdapter = new CricketDayAdapter(this, getActivity(), new ArrayList<>());
        linearLayoutManager = new LinearLayoutManager(getContext());
        //不回收子view
        linearLayoutManager.setItemPrefetchEnabled(false);
        linearLayoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        //避免新holder的生成，从而避免item闪烁
        recyclerView.setItemAnimator(null);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1) && !isLiveNow) {
                        requestList(2);
                    }
                }else if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    if(recyclerView.getChildAt(0) != null){
                        int currentPosition = ((RecyclerView.LayoutParams) recyclerView.getChildAt(0).getLayoutParams()).getViewAdapterPosition();
                        if(currentPosition != -1 && !TextUtils.isEmpty(mAdapter.getData().get(currentPosition).getDay())){
                            setDayInfo(getDayInfo(mAdapter.getData().get(currentPosition).getDay(),getContext()));
                            try{
                                picker.setSelectedDate(sdf.parse(mAdapter.getData().get(currentPosition).getDay()).getTime());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }

                // 遍历可见的item
/*                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    View view = recyclerView.getChildAt(i);
                    CricketDayAdapter.ViewHolder viewHolder = (CricketDayAdapter.ViewHolder) recyclerView.getChildViewHolder(view);
                    // 检查是否有内部recyclerView
                    if (viewHolder.rv_cricket.getVisibility() == View.VISIBLE && viewHolder.isToday) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) viewHolder.rv_cricket.getTag();
                        // 滑动到指定位置
                        layoutManager.scrollToPosition(mAdapter.todayBeginIndex);
                        // 或者使用平滑滚动
                        // layoutManager.smoothScrollToPosition(recyclerView, null, yourDesiredPosition);
                    }
                }*/

            }
        });


        filtrateSkeletonScreen = Skeleton.bind(rv_filtrate)
                .adapter(mFiltrateAdapter)
                .shimmer(false)
                .count(6)
                .load(R.layout.item_cricket_filtrate_skeleton)
                .show();

        mvpPresenter.getFiltrateList();
        requestList(1);


    }

    private void initStreamDialog() {
        mStreamDialog = new Dialog(getContext(), R.style.dialog);
        mStreamDialog.setContentView(R.layout.dialog_streaming);
        mStreamDialog.setCancelable(true);
        mStreamDialog.setCanceledOnTouchOutside(true);

        mStreamDialog.getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = mStreamDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        mStreamDialog.getWindow().setAttributes(params);
        iv_all = mStreamDialog.findViewById(R.id.iv_all);
        iv_all_author = mStreamDialog.findViewById(R.id.iv_all_author);
        iv_all_match = mStreamDialog.findViewById(R.id.iv_all_match);
        iv_all.setSelected(true);
        mStreamDialog.findViewById(R.id.ll_all_live).setOnClickListener(this);
        mStreamDialog.findViewById(R.id.ll_author_live).setOnClickListener(this);
        mStreamDialog.findViewById(R.id.ll_match_live).setOnClickListener(this);
    }

    public void filtrateData(int type){
        if(type == 1 && mStreamDialog != null){
            if(streamType!=2){
                streamType = 2;
                if(iv_all != null){
                    iv_all_match.setSelected(false);
                    iv_all.setSelected(false);
                    iv_all_author.setSelected(true);
                }
                ll_streaming.setSelected(true);
                iv_streaming.setSelected(true);
                tv_streaming.setSelected(true);
                requestList(1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
                //跳转搜索页面
                SearchMatchActivity.forward(getContext());
                break;
            case R.id.tv_live_now:
                tv_live_now.setSelected(!tv_live_now.isSelected());
                isLiveNow = tv_live_now.isSelected();
                if(isLiveNow){
                    //清空Streaming
                    if(streamType!=0){
                        streamType = 0;
                        if(iv_all != null){
                            iv_all.setSelected(true);
                            iv_all_match.setSelected(false);
                            iv_all_author.setSelected(false);
                        }
                        ll_streaming.setSelected(false);
                        iv_streaming.setSelected(false);
                        tv_streaming.setSelected(false);
                    }

                    smart_rl.setEnableRefresh(false);
                }else{
                    smart_rl.setEnableRefresh(true);
                }
                requestList(1);
                break;
            case R.id.tv_calendar:
                //选择日期 时间选择器
                if(picker == null){
                    initCalendarPicker();
                }
                picker.show();
                break;
            case R.id.ll_tours:
                //展开筛选联赛弹窗
                if(tournamentDialog != null){
                    tournamentDialog.show();
                }
                break;
            case R.id.ll_all_live:
                if(streamType!=0){
                    streamType = 0;
                    if(iv_all != null){
                        iv_all.setSelected(true);
                        iv_all_match.setSelected(false);
                        iv_all_author.setSelected(false);
                    }
                    ll_streaming.setSelected(false);
                    iv_streaming.setSelected(false);
                    tv_streaming.setSelected(false);
                    requestList(1);
                }
                if(mStreamDialog != null){
                    mStreamDialog.dismiss();
                }
                break;
            case R.id.ll_match_live:
                //清空LiveNow
                if(isLiveNow){
                    tv_live_now.setSelected(false);
                    isLiveNow = false;
                    smart_rl.setEnableRefresh(true);
                }

                if(streamType!=1){
                    streamType = 1;
                    if(iv_all != null){
                        iv_all.setSelected(false);
                        iv_all_match.setSelected(true);
                        iv_all_author.setSelected(false);
                    }
                    ll_streaming.setSelected(true);
                    iv_streaming.setSelected(true);
                    tv_streaming.setSelected(true);
                    requestList(1);
                }
                if(mStreamDialog != null){
                    mStreamDialog.dismiss();
                }
                break;
            case R.id.ll_author_live:
                if(isLiveNow){
                    tv_live_now.setSelected(false);
                    isLiveNow = false;
                    smart_rl.setEnableRefresh(true);
                }

                if(streamType!=2){
                    streamType = 2;
                    if(iv_all != null){
                        iv_all_match.setSelected(false);
                        iv_all.setSelected(false);
                        iv_all_author.setSelected(true);
                    }
                    ll_streaming.setSelected(true);
                    iv_streaming.setSelected(true);
                    tv_streaming.setSelected(true);
                    requestList(1);
                }
                if(mStreamDialog != null){
                    mStreamDialog.dismiss();
                }
                break;
            case R.id.tv_to_today:
                //有筛选条件 -> 回到今日 筛选失效
                if(tv_calendar.isSelected() || streamType !=0 || isLiveNow || !TextUtils.isEmpty(tag)){
                    initFreshCondition();
                    requestList(1);
                }else if(todayPosition<mAdapter.getData().size()){
                    //Fixme 滚动到今天最早正在比赛的赛事>距离现在最近的未开赛
//                    recyclerView.smoothScrollToPosition(todayPosition);
                    linearLayoutManager.scrollToPosition(todayPosition);
                    /*recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            try{
                                //1.没有被回收的显示在屏幕上的
//                                ((RecyclerView)recyclerView.getChildAt(todayPosition).findViewById(R.id.rv_cricket)).scrollToPosition(mAdapter.todayBeginIndex);


                                //2.getChildAt 在不可见时可能为null
//                                View view = recyclerView.getChildAt(todayPosition);
//                                CricketDayAdapter.ViewHolder viewHolder = (CricketDayAdapter.ViewHolder) recyclerView.getChildViewHolder(view);
//                                LinearLayoutManager layoutManager = (LinearLayoutManager) viewHolder.rv_cricket.getTag();
                                // 滑动到指定位置
//                                layoutManager.scrollToPosition(mAdapter.todayBeginIndex);
                                // 或者使用平滑滚动
//                                 layoutManager.smoothScrollToPosition(recyclerView, null, mAdapter.todayBeginIndex);

                                //3.
                                CricketDayAdapter.ViewHolder viewHolder = (CricketDayAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(todayPosition);
                                LinearLayoutManager layoutManager = (LinearLayoutManager) viewHolder.rv_cricket.getTag();
                                layoutManager.scrollToPosition(mAdapter.todayBeginIndex);

                            }catch (Exception e){

                            }

                            //算出高度 算不出，只有可见才能算出高度
//                            recyclerView.scrollBy(0, (int) (recyclerView.getY() + UIUtil.dip2px(getActivity(),todayPostion)));
                        }
                    });*/

                    setDayInfo(getDayInfo(mAdapter.getData().get(todayPosition).getDay(),getContext()));
                }
                break;
            case R.id.tv_fresh:
                hideEmptyView();
                recyclerView.setVisibility(View.VISIBLE);
                tv_to_today.setVisibility(View.VISIBLE);
                initFreshCondition();
                mvpPresenter.getFiltrateList();
                requestList(1);
                break;
            default:break;
        }
    }

    private void initFreshCondition(){
        tv_calendar.setSelected(false);
        if(isLiveNow){
            tv_live_now.setSelected(false);
            isLiveNow = false;
            smart_rl.setEnableRefresh(true);
        }

        if(streamType!=0){
            streamType = 0;
            if(iv_all != null){
                iv_all.setSelected(true);
                iv_all_match.setSelected(false);
                iv_all_author.setSelected(false);
            }
            ll_streaming.setSelected(false);
            iv_streaming.setSelected(false);
            tv_streaming.setSelected(false);
        }

        //清空已选的标签状态
        tag = "";
        mvpPresenter.getFiltrateList();
    }

    private void showTodayBtnAnim(int type){
        if(!TextUtils.isEmpty(tag)){
            return;
        }
        switch (type){
            case 0:
                if(tv_to_today.isSelected()){
                    transAnim(tv_to_today,tv_to_today.getMeasuredHeight() + UIUtil.dip2px(getContext(),20),0);
                    tv_to_today.setSelected(false);
                }
                break;
            case 1:
                if(!tv_to_today.isSelected()){
                    transAnim(tv_to_today,0,tv_to_today.getMeasuredHeight() + UIUtil.dip2px(getContext(),20));
                    tv_to_today.setSelected(true);
                }
                break;
        }
    }

    public void requestList(int type){
        if(type == 4 || (tv_calendar.isSelected() && type == 2) || (tv_calendar.isSelected() && type == 0)){

        }else{
            //非日期筛选
            tv_calendar.setSelected(false);
            singleTimeInMillis = System.currentTimeMillis();
            picker.setSelectedDate(singleTimeInMillis);
        }

        switch (type){
            case 1:
            case 4:
                lastDay = "";
                endDay = "";
                skeletonLoadLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                mvpPresenter.getCricketMatchList(type,sdf.format(singleTimeInMillis),TextUtils.isEmpty(tag)?"":tag,streamType,isLiveNow);//选中日
                break;
            case 2:
                if(!TextUtils.isEmpty(tag)){
                    return;
                }
                //不超过15天
                if(mAdapter.getItemCount() <= 0 && !TextUtils.isEmpty(endDay2)){
                    try{
                        if(toTodayIntDay(sdf.parse(endDay2))<15){
                            mvpPresenter.getCricketMatchList(type,endDay2,TextUtils.isEmpty(tag)?"":tag,streamType,isLiveNow);//后一天
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else if(!TextUtils.isEmpty(endDay)){
                    try{
                        if(toTodayIntDay(sdf.parse(endDay))<15){
                            mvpPresenter.getCricketMatchList(type,endDay,TextUtils.isEmpty(tag)?"":tag,streamType,isLiveNow);//后一天
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case 0:
                if(mFiltrateAdapter.getItemCount() <=1){
                    mvpPresenter.getFiltrateList();
                }

                if(mAdapter.getItemCount() <= 0 && isNotNetWork){
                    requestList(1);
                }else if(mAdapter.getItemCount() <= 0 && !TextUtils.isEmpty(lastDay2)){
                    //不超过15天
                    try{
                        if(toTodayIntDay(sdf.parse(lastDay2))<=15){
                            mvpPresenter.getCricketMatchList(type,lastDay2,TextUtils.isEmpty(tag)?"":tag,streamType,isLiveNow);//前一天
                        }else{
                            smart_rl.finishRefresh();
                        }
                    }catch (Exception e){
                        smart_rl.finishRefresh();
                        e.printStackTrace();
                    }
                }else if(!TextUtils.isEmpty(lastDay)){
                    try{
                        if(toTodayIntDay(sdf.parse(lastDay))<=15){
                            mvpPresenter.getCricketMatchList(type,lastDay,TextUtils.isEmpty(tag)?"":tag,streamType,isLiveNow);//前一天
                        }else{
                            smart_rl.finishRefresh();
                        }
                    }catch (Exception e){
                        smart_rl.finishRefresh();
                        e.printStackTrace();
                    }
                }else{
                    smart_rl.finishRefresh();
                }
                break;
            default:
                break;
        }




    }

    @Override
    public void getDataSuccess(List<CricketFiltrateBean> list) {
        filtrateCheckedList.clear();
        selectToursNum = 0;
        tv_tours_num.setVisibility(View.GONE);
        if(list!=null){
            mFiltrateAdapter.setNewData(list);
            ll_tours.setVisibility(View.VISIBLE);
            rv_filtrate.setBackgroundColor(Color.TRANSPARENT);
            filtrateSkeletonScreen.hide();
        }
    }

    @Override
    public void getDataSuccess(int type, CricketAllBean bean) {
        if(type == 0){
            smart_rl.finishRefresh();
        }
        skeletonLoadLayout.setVisibility(View.GONE);

        if(type == 1 || type == 4){
            //没数据也显示日期
            setDayInfo(getDayInfo(singleTimeInMillis,getContext()));
        }

        if (bean != null && bean.getItem() != null && bean.getItem().size() > 0) {
            hideEmptyView();
            if(type == 0 || type == 1 || type == 4){
                this.lastDay = bean.getFrontDay();
            }
            if(type == 2 || type == 1 || type == 4){
                this.endDay = bean.getEndDay();
            }
            recyclerView.setVisibility(View.VISIBLE);
            tv_to_today.setVisibility(View.VISIBLE);
            if(type == 0){
                todayPosition = todayPosition + bean.getItem().size();
                setDayInfo(getDayInfo(bean.getItem().get(0).getDay(),getContext()));
                mAdapter.addData(0,bean.getItem());
                //今天之前的 滑动定位到最后一条
                if(mAdapter.getData().size()>1){
                    recyclerView.scrollToPosition(1);
                }
            }else if(type == 1){
                todayPosition = 0;
                setDayInfo(getDayInfo(bean.getItem().get(0).getDay(),getContext()));
//                mAdapter.setNewData(bean.getItem());
                mAdapter.setData(bean.getItem());
                recyclerView.scrollBy(0, (int) (recyclerView.getY() + UIUtil.dip2px(getActivity(),60)));
                //当前时间
                Date now = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
                //获取今天的日期
                String nowDay = sf.format(now);
                //对比的时间
                String day = sf.format(singleTimeInMillis);
                if(mTimer == null && day.equals(nowDay)){
                    refreshTodayData();
                }
            }else if(type == 4){
                todayPosition = 0;
                setDayInfo(getDayInfo(bean.getItem().get(0).getDay(),getContext()));
//                mAdapter.setNewData(bean.getItem());
                mAdapter.setData(bean.getItem());
                recyclerView.scrollBy(0, (int) (recyclerView.getY() + UIUtil.dip2px(getActivity(),60)));
            }else{
                mAdapter.addData(bean.getItem());
            }
        } else {
//        } else if(mAdapter.getItemCount() == 0 || type == 1 || type == 4){
            if(bean != null){
                if(type == 0 || type == 1){
                    this.lastDay2 = bean.getFrontDay();
                }
                if(type == 2 || type == 1){
                    this.endDay2 = bean.getFrontDay();
                }

                if(type == 0){
                    if(mAdapter.getItemCount() <= 0 && !TextUtils.isEmpty(lastDay2)){
                        setDayInfo(getDayInfo(lastDay2,getContext()));
                    }else if(!TextUtils.isEmpty(lastDay)){
                        setDayInfo(getDayInfo(lastDay,getContext()));
                    }
                }else if(type == 2){
                    if(mAdapter.getItemCount() <= 0 && !TextUtils.isEmpty(endDay2)){
                        setDayInfo(getDayInfo(endDay2,getContext()));
                    }else if(!TextUtils.isEmpty(endDay)){
                        setDayInfo(getDayInfo(endDay,getContext()));
                    }
                }
            }
            recyclerView.setVisibility(View.GONE);
            tv_to_today.setVisibility(View.GONE);
            mAdapter.setData(new ArrayList<>());
            showEmptyView();
        }
    }

    @Override
    public void getDataFail(int type, String msg) {
        smart_rl.finishRefresh();
        skeletonLoadLayout.setVisibility(View.GONE);
        if (mAdapter.getItemCount() <= 0) {
            recyclerView.setVisibility(View.GONE);
            tv_to_today.setVisibility(View.GONE);
            showEmptyView();
        } else {
            ToastUtil.show(msg);
        }

        if(AppManager.mContext.getString(R.string.no_internet_connection).equals(msg)){
            isNotNetWork = true;
        }else{
            isNotNetWork = false;
        }
    }


    public void setDayInfo(String[] info){
        tv_date.setText(info[0]);
        tv_month.setText(info[1]);
        tv_day.setText(info[2]);

        if(Integer.parseInt(info[3]) < 0){
            tv_to_today.setCompoundDrawables(null,null,drawableDown,null);
            showTodayBtnAnim(0);
        }else if(Integer.parseInt(info[3]) > 0){
            tv_to_today.setCompoundDrawables(null,null,drawableTop,null);
            showTodayBtnAnim(0);
        }else{
            showTodayBtnAnim(1);
        }
    }

    private void initCalendarPicker(){
        picker = new CalendarPicker(getActivity());
        picker.setColorScheme(new ColorScheme()
                .weekTextColor(0xFFBDBFC8)
                .daySelectBackgroundColor(0xFFDC3C23)
                .daySelectTextColor(0xFFFFFFFF)
                .dayStressTextColor(0xFF000000)
                .dayNormalTextColor(0xFF000000)
                .dayInvalidTextColor(0xFFC8C8C8));
        //范围
        Calendar c = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c.setTime(new Date());
        c2.setTime(new Date());
        c.add(Calendar.DATE, -15);
        c2.add(Calendar.DATE, 15);
        picker.setRangeDate(c.getTime(),c2.getTime());
        picker.setTitle("Select Date");
        picker.getCancelView().setVisibility(View.GONE);
        picker.getTitleView().setTypeface(Typeface.DEFAULT_BOLD);
        picker.getTitleView().setTextColor(0xFF000000);
        picker.getOkView().setText("confirm");
        if (singleTimeInMillis == 0) {
            singleTimeInMillis = System.currentTimeMillis();
        }
        picker.setBackgroundDrawable(getActivity().getDrawable(R.drawable.shape_white_25dp_half_rec));
        picker.setSelectedDate(singleTimeInMillis);
        picker.setOnSingleDatePickListener(date -> {
            tv_calendar.setSelected(true);
            if(singleTimeInMillis != date.getTime()){
                singleTimeInMillis = date.getTime();
                requestList(4);
            }
        });

    }

    @Override
    public void showLoading() {
        //pass
    }

    @Override
    public void hideLoading() {
        //pass
    }

    @Override
    public void getDataSuccess(JsonBean model) {
        //pass
    }

    @Override
    public void getDataFail(String msg) {
        //pass
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTimer != null){
            mTimer.cancel();
        }
    }

    /**
     * 每10s刷新今天的数据
     */
    private void refreshTodayData(){
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //每10s判断
                //如果当前滚动到的Today，刷新最新数据(本fragment、未息屏、在前台
                if(getActivity()!=null && tv_day.getText().toString().equals("Today") &&
                        getUserVisibleHint() &&
                        ((PowerManager)getActivity().getSystemService(Context.POWER_SERVICE)).isScreenOn() &&
                        getActivity().getWindow().getDecorView().getVisibility() == View.VISIBLE ){
                    mvpPresenter.getRefreshTodayData(tag,streamType,isLiveNow);
                }
            }
        }, 10000, 10000);
    }

    @Override
    public void getRefreshSuccess(CricketAllBean bean) {
        try{
            if(mAdapter.getItemCount()-1 >= todayPosition){
                mAdapter.setPositionData(todayPosition,bean.getItem().get(0));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

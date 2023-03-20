package com.onecric.live.fragment;


import static com.onecric.live.util.AnimUtils.transAnim;
import static com.onecric.live.util.TimeUtil.getDayInfo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
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
import com.onecric.live.model.CricketAllBean;
import com.onecric.live.model.CricketFiltrateBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.cricket.CricketNewPresenter;
import com.onecric.live.util.TimeUtil;
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
    private TextView tv_search;
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

    private CricketDayAdapter mAdapter;
    private CricketFiltrateAdapter mFiltrateAdapter;
    private LoginDialog loginDialog;

    private boolean isLiveNow = false;
    private int streamType = 0;
    private int selectToursNum = 0;
    private Dialog mStreamDialog;
    private List<CricketFiltrateBean> filtrateCheckedList;
    private String tag="";
    private boolean isNotNetWork;

    private long singleTimeInMillis;
    private CalendarPicker picker;
    private RecyclerViewSkeletonScreen filtrateSkeletonScreen;//会导致回到每次rv数量变化会跳回第一页

    private String lastDay;
    private String endDay;
    public int todayPosition = 0;
    private Drawable drawableTop,drawableDown;
    private LinearLayout skeletonLoadLayout;
    private Timer mTimer;

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
        tv_search = findViewById(R.id.tv_search);
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
        tv_tours_num.setOnClickListener(this);
        tv_fresh = findViewById(R.id.tv_fresh);
        skeletonLoadLayout = findViewById(R.id.ll_skeleton);
        tv_fresh.setOnClickListener(this);
        findViewById(R.id.tv_search).setOnClickListener(this);
        findViewById(R.id.tv_calendar).setOnClickListener(this);
        findViewById(R.id.tv_to_today).setOnClickListener(this);
        ll_tours.setOnClickListener(this);
        initStreamDialog();
        initCalendarPicker();
        drawableTop = getContext().getDrawable(R.mipmap.ic_go_today_up);
        drawableTop.setBounds(0,0,drawableTop.getMinimumWidth(),drawableTop.getMinimumHeight());
        drawableDown = getContext().getDrawable(R.mipmap.ic_go_today_down);
        drawableDown.setBounds(0,0,drawableDown.getMinimumWidth(),drawableDown.getMinimumHeight());
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
                if(mFiltrateAdapter.getItem(position).isCheck()){
                    ++selectToursNum;
                    filtrateCheckedList.add(mFiltrateAdapter.getItem(position));
                    mFiltrateAdapter.addData(0,mFiltrateAdapter.getItem(position));
                    mFiltrateAdapter.remove(position+1);
                }else{
                    --selectToursNum;
                    filtrateCheckedList.remove(mFiltrateAdapter.getItem(position));
                    mFiltrateAdapter.addData(mFiltrateAdapter.getItem(position));
                    mFiltrateAdapter.remove(position);
                }
                rv_filtrate.smoothScrollToPosition(0);
                tv_tours_num.setText(selectToursNum + "");
                tv_tours_num.setVisibility(selectToursNum > 0 ? View.VISIBLE : View.GONE);
                if(filtrateCheckedList.size()>0){
                    StringBuilder tagsId = new StringBuilder();
                    for(CricketFiltrateBean bean : filtrateCheckedList){
                        tagsId.append(","+bean.getId());
                    }
                    tag = tagsId.substring(1);
                }else{
                    tag = "";
                }
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
        mAdapter = new CricketDayAdapter(this,getActivity(), new ArrayList<>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
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
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        requestList(2);
                    }
                }else if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    if(recyclerView.getChildAt(0) != null){
                        int currentPosition = ((RecyclerView.LayoutParams) recyclerView.getChildAt(0).getLayoutParams()).getViewAdapterPosition();
                        if(currentPosition != -1 && !TextUtils.isEmpty(mAdapter.getData().get(currentPosition).getDay())){
                            setDayInfo(getDayInfo(mAdapter.getData().get(currentPosition).getDay()));
                        }
                    }
                }

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

                break;
            case R.id.tv_tours_num:
                //数量
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
            case R.id.tv_to_today:
                recyclerView.smoothScrollToPosition(todayPosition);
                setDayInfo(getDayInfo(mAdapter.getData().get(todayPosition).getDay()));
                break;
            case R.id.tv_fresh:
                hideEmptyView();
                recyclerView.setVisibility(View.VISIBLE);
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

                tag = "";

                mvpPresenter.getFiltrateList();
                requestList(1);
                break;
            default:break;
        }
    }

    private void showTodayBtnAnim(int type){
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
        if(type == 0){
            if(mFiltrateAdapter.getItemCount() <=1){
                mvpPresenter.getFiltrateList();
            }
            if(mAdapter.getItemCount() <= 0 && isNotNetWork){
                requestList(1);
            }else if(!TextUtils.isEmpty(lastDay)){
                mvpPresenter.getCricketMatchList(type,lastDay,tag,streamType,isLiveNow);//前一天
            }else{
                smart_rl.finishRefresh();
            }
        }else if(type == 1){
            lastDay = "";
            endDay = "";
            skeletonLoadLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            mvpPresenter.getCricketMatchList(type,new SimpleDateFormat("yyyy-MM-dd").format(singleTimeInMillis),tag,streamType,isLiveNow);//选中日
        }else if(type == 2){
            if(!TextUtils.isEmpty(endDay)){
                mvpPresenter.getCricketMatchList(type,endDay,tag,streamType,isLiveNow);//后一天
            }
        }
    }

    @Override
    public void getDataSuccess(List<CricketFiltrateBean> list) {
        filtrateCheckedList.clear();
        selectToursNum = 0;
        tv_tours_num.setVisibility(View.GONE);
        if(list!=null){
            mFiltrateAdapter.setNewData(list);
//            ll_tours.setVisibility(View.VISIBLE);
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
        if (bean != null && bean.getItem() != null && bean.getItem().size() > 0) {
            if(type == 0 || type == 1){
                this.lastDay = bean.getFrontDay();
            }
            if(type == 2 || type == 1){
                this.endDay = bean.getEndDay();
            }
            hideEmptyView();
            recyclerView.setVisibility(View.VISIBLE);
            if(type == 0){
                todayPosition = todayPosition+bean.getItem().size();
                mAdapter.addData(0,bean.getItem());
            }else if(type == 1){
                todayPosition = 0;
                setDayInfo(getDayInfo(bean.getItem().get(0).getDay()));
//                mAdapter.setNewData(bean.getItem());
                mAdapter.setData(bean.getItem());
                recyclerView.scrollBy(0, (int) (recyclerView.getY() + UIUtil.dip2px(getActivity(),60)));
                if(mTimer == null){
                    refreshTodayData();
                }
            }else{
                mAdapter.addData(bean.getItem());
            }
        } else if(mAdapter.getItemCount() == 0){
            recyclerView.setVisibility(View.GONE);
            showEmptyView();
        } else if(type == 1 && (!TextUtils.isEmpty(tag) || isLiveNow || streamType != 0)){
//            mAdapter.setNewData(new ArrayList<>());
            mAdapter.setData(new ArrayList<>());
            recyclerView.setVisibility(View.GONE);
            showEmptyView();
        }
    }

    @Override
    public void getDataFail(int type, String msg) {
        smart_rl.finishRefresh();
        skeletonLoadLayout.setVisibility(View.GONE);
        if (mAdapter.getItemCount() <= 0) {
            recyclerView.setVisibility(View.GONE);
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
        singleTimeInMillis = new Date().getTime();
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
            if(singleTimeInMillis != date.getTime()){
                singleTimeInMillis = date.getTime();
                requestList(1);
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
                //如果当前滚动到的Tpday，刷新最新数据
                if(tv_day.getText().toString().equals("Today")){
                    mvpPresenter.getRefreshTodayData(tag,streamType,isLiveNow);
                }
            }
        }, 10000, 10000);
    }

    @Override
    public void getRefreshSuccess(CricketAllBean bean) {
        mAdapter.setPositionData(todayPosition,bean.getItem().get(0));
    }
}

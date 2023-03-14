package com.onecric.live.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.onecric.live.model.CricketDayBean;
import com.onecric.live.model.CricketFiltrateBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.cricket.CricketNewPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.CricketNewView;
import com.onecric.live.view.MvpFragment;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    private CricketDayAdapter mAdapter;
    private CricketFiltrateAdapter mFiltrateAdapter;
    private LoginDialog loginDialog;

    private boolean isLiveNow = false;
    private int streamType = 0;
    private int selectToursNum = 0;
    private Dialog mStreamDialog;
    private List<CricketFiltrateBean> filtrateCheckedList;
    private List<CricketFiltrateBean> filtrateList;
    private String tag="";
    private boolean isNotNetWork;
    //    private int beforePage = 0;
    //    private int afterPage = 0;

    private long singleTimeInMillis;
    private CalendarPicker picker;
    private RecyclerViewSkeletonScreen skeletonScreen,filtrateSkeletonScreen;

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
        tv_live_now.setOnClickListener(this);
        tv_tours_num.setOnClickListener(this);
        findViewById(R.id.tv_search).setOnClickListener(this);
        findViewById(R.id.tv_calendar).setOnClickListener(this);
        ll_tours.setOnClickListener(this);
        initStreamDialog();
        initCalendarPicker();
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
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableLoadMore(true);
        smart_rl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //后一天
//                afterPage++;
                requestList(2);
            }
        });

        smart_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //前一天
//                beforePage++;
                requestList(0);
            }
        });

        mAdapter = new CricketDayAdapter(this,R.layout.item_cricket_day, new ArrayList<>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);

        filtrateSkeletonScreen = Skeleton.bind(rv_filtrate)
                .adapter(mFiltrateAdapter)
                .shimmer(false)
                .count(6)
                .load(R.layout.item_cricket_filtrate_skeleton)
                .show();

        skeletonScreen = Skeleton.bind(recyclerView)
                .adapter(mAdapter)
                .shimmer(false)
                .count(2)
                .load(R.layout.item_cricket_skeleton)
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
                    smart_rl.setEnableLoadMore(false);
                    smart_rl.setEnableRefresh(false);
                }else{
                    smart_rl.setEnableLoadMore(true);
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
            default:break;
        }

    }

    /**
     *
     * @param type 0前 1今 2后
     */
    private void requestList(int type){
        if(type == 0){
            if(mFiltrateAdapter.getItemCount() <=1){
                mvpPresenter.getFiltrateList();
            }
            if(mAdapter.getItemCount() <= 0 && isNotNetWork){
                requestList(1);
            }else if(!TextUtils.isEmpty("lastDay")){
                mvpPresenter.getCricketMatchList(type,"lastDay",tag,streamType,isLiveNow);//前一天
            }else{
                smart_rl.finishRefresh();
            }
        }else if(type == 1){
//            lastDay = "";
//            endDay = "";
            mvpPresenter.getCricketMatchList(type,new SimpleDateFormat("yyyy-MM-dd").format(singleTimeInMillis),tag,streamType,isLiveNow);//选中日
        }else if(type == 2){
            if(!TextUtils.isEmpty("endDay")){
                mvpPresenter.getCricketMatchList(type,"endDay",tag,streamType,isLiveNow);//后一天
            }else{
                smart_rl.finishLoadMore();
            }
        }
    }

    @Override
    public void getDataSuccess(List<CricketFiltrateBean> list) {
        if(list!=null){
            mFiltrateAdapter.setNewData(list);
            ll_tours.setVisibility(View.VISIBLE);
            rv_filtrate.setBackgroundColor(Color.TRANSPARENT);
            filtrateSkeletonScreen.hide();
        }
    }

    @Override
    public void getDataSuccess(int type, List<CricketDayBean> list, String lastDay, String endDay) {
        if(type == 0 || type == 1){
//            this.lastDay = lastDay;
        }

        if(type == 2 || type == 1){
//            this.endDay = endDay;
        }

        smart_rl.finishLoadMore();
        smart_rl.finishRefresh();
        skeletonScreen.hide();
        if (list != null && list.size() > 0) {
            hideEmptyView();
            if(type == 0){
                mAdapter.addData(0,list);
            }else if(type == 1){
                mAdapter.setNewData(list);
                recyclerView.scrollBy(0, (int) (recyclerView.getY() + UIUtil.dip2px(getActivity(),60)));
            }else{
                mAdapter.addData(list);
            }
        } else if(mAdapter.getItemCount() == 0){
            showEmptyView();
        } else if(type == 1 && (!TextUtils.isEmpty(tag) || isLiveNow || streamType != 0)){
            mAdapter.setNewData(new ArrayList<>());
            showEmptyView();
            //没数据再请求之前/后的数据
/*            if(type == 0){
                beforePage++;
            }else{
                afterPage++;
            }
            requestList(type);*/
        }
    }

    @Override
    public void getDataFail(int type, String msg) {
        smart_rl.finishRefresh();
        smart_rl.finishLoadMore();
        skeletonScreen.hide();
/*        if(type == 0){
            beforePage--;
        }else if(type == 2){
            afterPage--;
        }*/
        if (mAdapter.getData().size() <= 0) {
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

    public void setDayInfo(String[] info,long data){
        singleTimeInMillis = data;
        tv_date.setText(info[0]);
        tv_month.setText(info[1]);
        tv_day.setText(info[2]);
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
}

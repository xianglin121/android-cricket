package com.onecric.live.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
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

import com.onecric.live.AppManager;
import com.onecric.live.R;
import com.onecric.live.activity.CricketInnerActivity;
import com.onecric.live.adapter.CricketFiltrateAdapter;
import com.onecric.live.adapter.CricketNewAdapter;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.model.CricketFiltrateBean;
import com.onecric.live.model.CricketNewBean;
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

    private CricketNewAdapter mAdapter;
    private CricketFiltrateAdapter mFiltrateAdapter;
    private LoginDialog loginDialog;

    private boolean isLiveNow = false;
    private int streamType = 0;
    private int selectToursNum = 0;
//    private int beforePage = 0;
//    private int afterPage = 0;
    private Dialog mStreamDialog;
    private List<CricketFiltrateBean> filtrateCheckedList;
    private List<CricketFiltrateBean> filtrateList;
    private String tag="";
    private String lastDay="";
    private String endDay="";
    public boolean isMore;
    private boolean isNotNetWork;

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
        tv_live_now.setOnClickListener(this);
        tv_tours_num.setOnClickListener(this);
        findViewById(R.id.tv_search).setOnClickListener(this);
        findViewById(R.id.tv_calendar).setOnClickListener(this);
        findViewById(R.id.ll_tours).setOnClickListener(this);
        initStreamDialog();
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
//                rv_filtrate.scrollToPosition(0);
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
                isMore = true;
                requestList(2);
            }
        });

        smart_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //前一天
//                beforePage++;
                isMore = false;
                requestList(0);
            }
        });

        mAdapter = new CricketNewAdapter(this,R.layout.item_cricket_new, new ArrayList<>());
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_empty, null, false);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ll_title) {
                CricketInnerActivity.forward(getContext(), mAdapter.getItem(position).getName(), mAdapter.getItem(position).getType(), mAdapter.getItem(position).getTournamentId());
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);

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

                break;
            case R.id.tv_live_now:
                tv_live_now.setSelected(!tv_live_now.isSelected());
                isLiveNow = tv_live_now.isSelected();
                requestList(1);
                break;
            case R.id.tv_calendar:
                //选择日期 时间选择器

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
            }else if(!TextUtils.isEmpty(lastDay)){
                mvpPresenter.getCricketMatchList(type,lastDay,tag,streamType,isLiveNow);//前一天
            }else{
                smart_rl.finishRefresh();
            }
        }else if(type == 1){
//            beforePage = 0;
//            afterPage = 0;
            lastDay = "";
            endDay = "";
            mvpPresenter.getCricketMatchList(type,new SimpleDateFormat("yyyy-MM-dd").format(new Date()),tag,streamType,isLiveNow);//当日
        }else if(type == 2){
            if(!TextUtils.isEmpty(endDay)){
                mvpPresenter.getCricketMatchList(type,endDay,tag,streamType,isLiveNow);//后一天
            }else{
                smart_rl.finishLoadMore();
            }
        }
    }

    @Override
    public void getDataSuccess(List<CricketFiltrateBean> list) {
        if(list!=null){
            mFiltrateAdapter.setNewData(list);
        }
    }

    @Override
    public void getDataSuccess(int type, List<CricketNewBean> list, String lastDay, String endDay) {
        if(type == 0 || type == 1){
            this.lastDay = lastDay;
        }

        if(type == 2 || type == 1){
            this.endDay = endDay;
        }

        smart_rl.finishLoadMore();
        smart_rl.finishRefresh();
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

    public void setDayInfo(String[] info){
        tv_date.setText(info[0]);
        tv_month.setText(info[1]);
        tv_day.setText(info[2]);
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

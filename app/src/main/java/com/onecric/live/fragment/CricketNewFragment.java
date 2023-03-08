package com.onecric.live.fragment;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.MainActivity;
import com.onecric.live.adapter.CricketFiltrateAdapter;
import com.onecric.live.event.ToggleTabEvent;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.model.CricketFiltrateBean;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.view.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public class CricketNewFragment extends BaseFragment implements View.OnClickListener {
    private TextView tv_search;
    private RecyclerView rv_filtrate;
    private TextView tv_day;
    private TextView tv_date;
    private TextView tv_month;
    private TextView tv_live_now;
    private TextView tv_tours_num;
    private FrameLayout fl_main;

    private CricketFiltrateAdapter mFiltrateAdapter;
    private LoginDialog loginDialog;

    private boolean isLiveNow;
    private int selectToursNum = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricket2;
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
        fl_main = findViewById(R.id.fl_main);
        tv_live_now = findViewById(R.id.tv_live_now);
        tv_tours_num = findViewById(R.id.tv_tours_num);
        tv_live_now.setOnClickListener(this);
        tv_tours_num.setOnClickListener(this);
        findViewById(R.id.tv_search).setOnClickListener(this);
        findViewById(R.id.tv_calendar).setOnClickListener(this);
        findViewById(R.id.ll_tours).setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        //------------TOP
        //筛选数据
        List<CricketFiltrateBean> filtrateList = new ArrayList<>();
        filtrateList.add(new CricketFiltrateBean("Women's IPL 2023"));
        filtrateList.add(new CricketFiltrateBean("PSL 2023"));
        filtrateList.add(new CricketFiltrateBean("IND vs AUS"));
        filtrateList.add(new CricketFiltrateBean("BAN vs ENG"));
        filtrateList.add(new CricketFiltrateBean("SA vs WI"));
        mFiltrateAdapter = new CricketFiltrateAdapter(R.layout.item_cricket_filtrate,filtrateList);
        mFiltrateAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if(view.getId() == R.id.tv_name){
                filtrateList.get(position).isCheck = !filtrateList.get(position).isCheck;
                if(filtrateList.get(position).isCheck){
                    ++selectToursNum;
                    filtrateList.add(0,filtrateList.remove(position));
                }else{
                    --selectToursNum;
                    filtrateList.add(filtrateList.remove(position));
                }
                tv_tours_num.setText(selectToursNum + "");
                tv_tours_num.setVisibility(selectToursNum > 0 ? View.VISIBLE : View.GONE);
                mFiltrateAdapter.notifyDataSetChanged();
                rv_filtrate.scrollToPosition(0);
                //筛选 更新列表

            }
        });

        View headerStreamingView = LayoutInflater.from(getContext()).inflate(R.layout.item_cricket_filtrate_header, null);
        headerStreamingView.findViewById(R.id.ll_streaming).setOnClickListener(v -> {
            //原逻辑跳Live
            EventBus.getDefault().post(new ToggleTabEvent(2));
        });
        mFiltrateAdapter.addHeaderView(headerStreamingView);

        rv_filtrate.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        rv_filtrate.setAdapter(mFiltrateAdapter);

        //----------LIST
        getFragmentManager().beginTransaction().add(R.id.fl_main,CricketListFragment.newInstance("today")).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
                //跳转搜索页面

                break;
            case R.id.tv_live_now:
                tv_live_now.setSelected(!tv_live_now.isSelected());
                //筛选今天直播列表

                break;
            case R.id.tv_calendar:
                //选择日期

                break;
            case R.id.ll_tours:
                //展开筛选联赛

                break;
            case R.id.tv_tours_num:
                //数量
                break;
            default:break;
        }

    }

}

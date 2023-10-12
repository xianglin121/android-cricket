package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.adapter.CricketStatsAdapter;
import com.onecric.live.custom.CenterShowHorizontalScrollView;
import com.onecric.live.model.CricketStatsListBean;
import com.onecric.live.presenter.cricket.CricketStatsPresenter;
import com.onecric.live.util.DpUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.cricket.CricketStatsView;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketStatsActivity extends MvpActivity<CricketStatsPresenter> implements CricketStatsView, BaseQuickAdapter.OnItemClickListener {
    public static void forward(Context context, int position, int id, String type) {
        Intent intent = new Intent(context, CricketStatsActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    private CenterShowHorizontalScrollView scroll_view;
    private RecyclerView rv_stats;

    private LinearLayout ll_one;
    private CricketStatsAdapter mOneAdapter;
    private LinearLayout ll_two;
    private CricketStatsAdapter mTwoAdapter;
    private LinearLayout ll_three;
    private CricketStatsAdapter mThreeAdapter;
    private LinearLayout ll_four;
    private CricketStatsAdapter mFourAdapter;
    private LinearLayout ll_five;
    private CricketStatsAdapter mFiveAdapter;
    private LinearLayout ll_six;
    private CricketStatsAdapter mSixAdapter;
    private LinearLayout ll_seven;
    private CricketStatsAdapter mSevenAdapter;
    private LinearLayout ll_eight;
    private CricketStatsAdapter mEightAdapter;
    private LinearLayout ll_night;
    private CricketStatsAdapter mNightAdapter;

    private int mId;
    private String mType;
    public int mPosition;

    @Override
    protected CricketStatsPresenter createPresenter() {
        return new CricketStatsPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cricket_stats;
    }

    @Override
    protected void initView() {
        mId = getIntent().getIntExtra("id", 0);
        mType = getIntent().getStringExtra("type");
        mPosition = getIntent().getIntExtra("position", 0);
        setTitleText("Tour Stats:" + mType.toUpperCase());

        scroll_view = findViewById(R.id.scroll_view);
        rv_stats = findViewById(R.id.rv_stats);
        ll_one = findViewById(R.id.ll_one);
        ll_two = findViewById(R.id.ll_two);
        ll_three = findViewById(R.id.ll_three);
        ll_four = findViewById(R.id.ll_four);
        ll_five = findViewById(R.id.ll_five);
        ll_six = findViewById(R.id.ll_six);
        ll_seven = findViewById(R.id.ll_seven);
        ll_eight = findViewById(R.id.ll_eight);
        ll_night = findViewById(R.id.ll_night);
    }

    @Override
    protected void initData() {
        initScrollBar();

        mOneAdapter = new CricketStatsAdapter(R.layout.item_cricket_stats_one, new ArrayList<>());
        mOneAdapter.setOnItemClickListener(this);
        mOneAdapter.addFooterView(View.inflate(this, R.layout.item_cricket_stats_footer_one, null));

        mTwoAdapter = new CricketStatsAdapter(R.layout.item_cricket_stats_one, new ArrayList<>());
        mTwoAdapter.setOnItemClickListener(this);
        mTwoAdapter.addFooterView(View.inflate(this, R.layout.item_cricket_stats_footer_two, null));

        mThreeAdapter = new CricketStatsAdapter(R.layout.item_cricket_stats_one, new ArrayList<>());
        mThreeAdapter.setOnItemClickListener(this);
        mThreeAdapter.addFooterView(View.inflate(this, R.layout.item_cricket_stats_footer_three, null));

        mFourAdapter = new CricketStatsAdapter(R.layout.item_cricket_stats_one, new ArrayList<>());
        mFourAdapter.setOnItemClickListener(this);
        mFourAdapter.addFooterView(View.inflate(this, R.layout.item_cricket_stats_footer_four, null));

        mFiveAdapter = new CricketStatsAdapter(R.layout.item_cricket_stats_one, new ArrayList<>());
        mFiveAdapter.setOnItemClickListener(this);
        mFiveAdapter.addFooterView(View.inflate(this, R.layout.item_cricket_stats_footer_five, null));

        mSixAdapter = new CricketStatsAdapter(R.layout.item_cricket_stats_one, new ArrayList<>());
        mSixAdapter.setOnItemClickListener(this);
        mSixAdapter.addFooterView(View.inflate(this, R.layout.item_cricket_stats_footer_six, null));

        mSevenAdapter = new CricketStatsAdapter(R.layout.item_cricket_stats_one, new ArrayList<>());
        mSevenAdapter.setOnItemClickListener(this);
        mSevenAdapter.addFooterView(View.inflate(this, R.layout.item_cricket_stats_footer_six, null));

        mEightAdapter = new CricketStatsAdapter(R.layout.item_cricket_stats_one, new ArrayList<>());
        mEightAdapter.setOnItemClickListener(this);
        mEightAdapter.addFooterView(View.inflate(this, R.layout.item_cricket_stats_footer_seven, null));

        mNightAdapter = new CricketStatsAdapter(R.layout.item_cricket_stats_two, new ArrayList<>());
        mNightAdapter.setOnItemClickListener(this);
        mNightAdapter.addFooterView(View.inflate(this, R.layout.item_cricket_stats_footer_eight, null));



        rv_stats.setLayoutManager(new LinearLayoutManager(this));

        mvpPresenter.getList(mId, mPosition + 1);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PlayerProfileActivity.forward(mActivity, ((CricketStatsAdapter)adapter).getItem(position).getPlayer_id());
    }

    private void initScrollBar() {
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.highest_individual_score));
        list.add(getString(R.string.most_runs));
        list.add(getString(R.string.best_batting_average));
        list.add(getString(R.string.highest_strike_rate));
        list.add(getString(R.string.maximum_sixes));
        list.add(getString(R.string.most_wickets));
        list.add(getString(R.string.best_bowling_average));
        list.add(getString(R.string.best_bowling_economy));
/*        list.add(getString(R.string.most_catches));
        list.add(getString(R.string.most_run_outs));
        list.add(getString(R.string.most_stumpings));*/

        for (int i = 0; i < list.size(); i++) {
            TextView textView = (TextView) View.inflate(this, R.layout. item_cricket_stats_head, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                layoutParams.leftMargin = DpUtil.dp2px(14);
            }
            layoutParams.rightMargin = DpUtil.dp2px(12);
            textView.setLayoutParams(layoutParams);
            textView.setText(list.get(i));
            scroll_view.addItemView(textView, i);
            if (i == mPosition) {
                textView.setSelected(true);
            }else {
                textView.setSelected(false);
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scroll_view.onClicked(v);
                    int position = (Integer) v.getTag();
                    if (position == mPosition) {
                        return;
                    }
                    scroll_view.getView(mPosition).setSelected(false);
                    v.setSelected(true);
                    mPosition = position;
                    mvpPresenter.getList(mId, mPosition + 1);
                }
            });
        }
        scroll_view.postDelayed(new Runnable() {
            @Override
            public void run() {
                scroll_view.onClicked(mPosition);
            }
        }, 500);
    }

    @Override
    public void getDataSuccess(List<CricketStatsListBean> list) {
        if (list == null) {
            list = new ArrayList<>();
        }

        if (mPosition == 0) {
            ll_one.setVisibility(View.VISIBLE);
            mOneAdapter.setNewData(list);
            rv_stats.setAdapter(mOneAdapter);
        }else {
            ll_one.setVisibility(View.GONE);
        }
        if (mPosition == 1) {
            ll_two.setVisibility(View.VISIBLE);
            mTwoAdapter.setNewData(list);
            rv_stats.setAdapter(mTwoAdapter);
        }else {
            ll_two.setVisibility(View.GONE);
        }
        if (mPosition == 2) {
            ll_three.setVisibility(View.VISIBLE);
            mThreeAdapter.setNewData(list);
            rv_stats.setAdapter(mThreeAdapter);
        }else {
            ll_three.setVisibility(View.GONE);
        }
        if (mPosition == 3) {
            ll_four.setVisibility(View.VISIBLE);
            mFourAdapter.setNewData(list);
            rv_stats.setAdapter(mFourAdapter);
        }else {
            ll_four.setVisibility(View.GONE);
        }
        if (mPosition == 4) {
            ll_five.setVisibility(View.VISIBLE);
            mFiveAdapter.setNewData(list);
            rv_stats.setAdapter(mFiveAdapter);
        }else {
            ll_five.setVisibility(View.GONE);
        }
        if (mPosition == 5) {
            ll_six.setVisibility(View.VISIBLE);
            mSixAdapter.setNewData(list);
            rv_stats.setAdapter(mSixAdapter);
        }else {
            ll_six.setVisibility(View.GONE);
        }
        if (mPosition == 6) {
            ll_seven.setVisibility(View.VISIBLE);
            mSevenAdapter.setNewData(list);
            rv_stats.setAdapter(mSevenAdapter);
        }else {
            ll_seven.setVisibility(View.GONE);
        }
        if (mPosition == 7) {
            ll_eight.setVisibility(View.VISIBLE);
            mEightAdapter.setNewData(list);
            rv_stats.setAdapter(mEightAdapter);
        }else {
            ll_eight.setVisibility(View.GONE);
        }
        if (mPosition == 8 || mPosition == 9 || mPosition == 10) {
            ll_night.setVisibility(View.VISIBLE);
            mNightAdapter.setNewData(list);
            rv_stats.setAdapter(mNightAdapter);
        }else {
            ll_night.setVisibility(View.GONE);
        }

        if (list == null || list.size()<=0) {
            showEmptyView();
            switch (mPosition){
                case 0:
                    mOneAdapter.removeAllFooterView();
                    ll_one.setVisibility(View.GONE);
                    break;
                case 1:
                    mTwoAdapter.removeAllFooterView();
                    ll_two.setVisibility(View.GONE);
                    break;
                case 2:
                    mThreeAdapter.removeAllFooterView();
                    ll_three.setVisibility(View.GONE);
                    break;
                case 3:
                    mFourAdapter.removeAllFooterView();
                    ll_four.setVisibility(View.GONE);
                    break;
                case 4:
                    mFiveAdapter.removeAllFooterView();
                    ll_five.setVisibility(View.GONE);
                    break;
                case 5:
                    mSixAdapter.removeAllFooterView();
                    ll_six.setVisibility(View.GONE);
                    break;
                case 6:
                    mSevenAdapter.removeAllFooterView();
                    ll_seven.setVisibility(View.GONE);
                    break;
                case 7:
                    mEightAdapter.removeAllFooterView();
                    ll_eight.setVisibility(View.GONE);
                    break;
                case 8:
                case 9:
                case 10:
                    mNightAdapter.removeAllFooterView();
                    ll_night.setVisibility(View.GONE);
                    break;
                default:break;
            }
        }else{
            hideEmptyView();
        }

    }

    @Override
    public void getDataFail(String msg) {

    }
}

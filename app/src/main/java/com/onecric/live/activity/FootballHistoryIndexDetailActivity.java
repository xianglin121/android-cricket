package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.onecric.live.R;
import com.onecric.live.adapter.FootballHistoryIndexDetailAdapter;
import com.onecric.live.model.FootballHistoryIndexBean;
import com.onecric.live.model.FootballHistoryIndexDetailBean;
import com.onecric.live.presenter.match.FootballHistoryIndexDetailPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.match.FootballHistoryIndexDetailView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FootballHistoryIndexDetailActivity extends MvpActivity<FootballHistoryIndexDetailPresenter> implements FootballHistoryIndexDetailView, View.OnClickListener {

    public static void forward(Context context, int id, int companyId, List<FootballHistoryIndexBean> historyList, boolean isAll) {
        Intent intent = new Intent(context, FootballHistoryIndexDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("companyId", companyId);
        intent.putExtra("list", (Serializable) historyList);
        intent.putExtra("isAll", isAll);
        context.startActivity(intent);
    }

    private int mId = 0;
    private int mCompanyId;
    private List<FootballHistoryIndexBean> mHistoryList;
    private boolean mIsAll;

    private ImageView iv_toggle;
    private TextView tv_company;
    private TextView tv_win_rate;
    private TextView tv_draw_rate;
    private TextView tv_lose_rate;
    private TextView tv_home_percent, tv_flat_percent, tv_away_percent;
    private TextView tv_home_rate, tv_flat_rate, tv_away_rate;
    private OptionsPickerView pvCustomOptions;
    private RecyclerView rv_history;
    private FootballHistoryIndexDetailAdapter mAdapter;

    private FootballHistoryIndexDetailBean mBean;

    @Override
    protected FootballHistoryIndexDetailPresenter createPresenter() {
        return new FootballHistoryIndexDetailPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_history_index_detail;
    }

    @Override
    protected void initView() {
        mId = getIntent().getIntExtra("id", 0);
        mCompanyId = getIntent().getIntExtra("companyId", 0);
        mHistoryList = (List<FootballHistoryIndexBean>) getIntent().getSerializableExtra("list");
        mIsAll = getIntent().getBooleanExtra("isAll", false);
        setTitleText(WordUtil.getString(this, R.string.history_index_detail));

        iv_toggle = findViewById(R.id.iv_toggle);
        tv_company = findViewById(R.id.tv_company);
        tv_win_rate = findViewById(R.id.tv_win_rate);
        tv_draw_rate = findViewById(R.id.tv_draw_rate);
        tv_lose_rate = findViewById(R.id.tv_lose_rate);
        tv_home_percent = findViewById(R.id.tv_home_percent);
        tv_flat_percent = findViewById(R.id.tv_flat_percent);
        tv_away_percent = findViewById(R.id.tv_away_percent);
        tv_home_rate = findViewById(R.id.tv_home_rate);
        tv_flat_rate = findViewById(R.id.tv_flat_rate);
        tv_away_rate = findViewById(R.id.tv_away_rate);
        tv_company = findViewById(R.id.tv_company);
        rv_history = findViewById(R.id.rv_history);

        findViewById(R.id.ll_toggle).setOnClickListener(this);
        findViewById(R.id.ll_company).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        iv_toggle.setSelected(!mIsAll);
//        List<IndexBean> list = new ArrayList<>();
//        IndexBean indexOne = new IndexBean();
//        indexOne.setItemType(IndexBean.HEAD);
//        list.add(indexOne);
//        IndexBean indexTwo = new IndexBean();
//        indexTwo.setItemType(IndexBean.CONTENT);
//        list.add(indexTwo);
//        IndexBean indexThree = new IndexBean();
//        indexThree.setItemType(IndexBean.CONTENT);
//        list.add(indexThree);
        mAdapter = new FootballHistoryIndexDetailAdapter(new ArrayList<>());
        rv_history.setLayoutManager(new LinearLayoutManager(this));
        rv_history.setAdapter(mAdapter);

        mvpPresenter.getDetail(mId, mCompanyId);
    }

    @Override
    public void getDataSuccess(FootballHistoryIndexDetailBean bean) {
        if (bean != null) {
            mBean = bean;
            if (!TextUtils.isEmpty(bean.getName())) {
                tv_company.setText(bean.getName());
            }else {
                tv_company.setText("");
            }
            if (!TextUtils.isEmpty(bean.getInitial_won())) {
                tv_win_rate.setText(bean.getInitial_won());
            }
            if (!TextUtils.isEmpty(bean.getInitial_draw())) {
                tv_draw_rate.setText(bean.getInitial_draw());
            }
            if (!TextUtils.isEmpty(bean.getInitial_loss())) {
                tv_lose_rate.setText(bean.getInitial_loss());
            }
            toggleData();
        }
    }

    @Override
    public void getDataFail(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_toggle:
                mIsAll = !mIsAll;
                iv_toggle.setSelected(!mIsAll);
                toggleData();
                break;
            case R.id.ll_company:
                showPickerView();
                break;
        }
    }

    private void toggleData() {
        if (mBean != null) {
            if (mIsAll) {
                if (!TextUtils.isEmpty(mBean.getAll().getWin_rate())) {
                    tv_home_percent.setText(mBean.getAll().getWin_rate() + "%");
                }
                if (!TextUtils.isEmpty(mBean.getAll().getDraw_rate())) {
                    tv_flat_percent.setText(mBean.getAll().getDraw_rate() + "%");
                }
                if (!TextUtils.isEmpty(mBean.getAll().getLoss_rate())) {
                    tv_away_percent.setText(mBean.getAll().getLoss_rate() + "%");
                }
                tv_home_rate.setText(mBean.getAll().getWon() + "赢");
                tv_flat_rate.setText(mBean.getAll().getDraw() + "走");
                tv_away_rate.setText(mBean.getAll().getLoss() + "输");
                if (mBean.getList() != null) {
                    mAdapter.setNewData(mBean.getList());
                }
            }else {
                if (!TextUtils.isEmpty(mBean.getTonglian().getWin_rate())) {
                    tv_home_percent.setText(mBean.getTonglian().getWin_rate() + "%");
                }
                if (!TextUtils.isEmpty(mBean.getTonglian().getDraw_rate())) {
                    tv_flat_percent.setText(mBean.getTonglian().getDraw_rate() + "%");
                }
                if (!TextUtils.isEmpty(mBean.getTonglian().getLoss_rate())) {
                    tv_away_percent.setText(mBean.getTonglian().getLoss_rate() + "%");
                }
                tv_home_rate.setText(mBean.getTonglian().getWon() + "赢");
                tv_flat_rate.setText(mBean.getTonglian().getDraw() + "走");
                tv_away_rate.setText(mBean.getTonglian().getLoss() + "输");
                if (mBean.getTonglian_list() != null) {
                    mAdapter.setNewData(mBean.getTonglian_list());
                }
            }
        }
    }

    private void showPickerView() {
        if (mHistoryList != null) {
            List<String> optionList = new ArrayList<>();
            for (int i = 0; i < mHistoryList.size(); i++) {
                optionList.add(mHistoryList.get(i).getName());
            }
            pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    int companyId = mHistoryList.get(options1).getId();
                    mvpPresenter.getDetail(mId, companyId);
                }
            })
                    .setLayoutRes(R.layout.pickerview_index_company, new CustomListener() {
                        @Override
                        public void customLayout(View v) {
                            TextView tv_cancel = v.findViewById(R.id.tv_cancel);
                            TextView tv_confirm = v.findViewById(R.id.tv_confirm);
                            tv_confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pvCustomOptions.returnData();
                                    pvCustomOptions.dismiss();
                                }
                            });

                            tv_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pvCustomOptions.dismiss();
                                }
                            });
                        }
                    })
                    .isDialog(false)
                    .setOutSideCancelable(true)
                    .build();

            pvCustomOptions.setPicker(optionList);//添加数据
            pvCustomOptions.show();
        }
    }
}

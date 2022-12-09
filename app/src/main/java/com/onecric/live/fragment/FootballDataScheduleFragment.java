package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.onecric.live.R;
import com.onecric.live.adapter.FootballDataScheduleAdapter;
import com.onecric.live.model.DataScheduleBean;
import com.onecric.live.model.DataStageBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.match.FootballDataSchedulePresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.match.FootballDataScheduleView;

import java.util.ArrayList;
import java.util.List;

public class FootballDataScheduleFragment extends MvpFragment<FootballDataSchedulePresenter> implements FootballDataScheduleView, View.OnClickListener {

    public static FootballDataScheduleFragment newInstance() {
        FootballDataScheduleFragment fragment = new FootballDataScheduleFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mId;
    private int mSeasonId;
    private TextView tv_filter;
    private RecyclerView recyclerView;
    private FootballDataScheduleAdapter mAdapter;

    private OptionsPickerView pvCustomOptions;
    private List<DataStageBean> mStageList;
    private int mStageId;
    private int mSonId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_football_data_schedule;
    }

    @Override
    protected FootballDataSchedulePresenter createPresenter() {
        return new FootballDataSchedulePresenter(this);
    }

    @Override
    protected void initUI() {
        tv_filter = findViewById(R.id.tv_filter);
        recyclerView = findViewById(R.id.recyclerView);

        tv_filter.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mAdapter = new FootballDataScheduleAdapter(R.layout.item_football_data_schedule, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    public void setData(int id, int seasonId) {
        mId = id;
        mSeasonId = seasonId;
        mvpPresenter.getStageList(mSeasonId);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getStageDataSuccess(List<DataStageBean> list) {
        mStageList = list;
        if (mStageList != null && mStageList.size() > 0) {
            if (mStageList.get(0).getLower() != null && mStageList.get(0).getLower().size() > 0) {
                tv_filter.setText(mStageList.get(0).getLower().get(0).getName());
                mStageId = mStageList.get(0).getId();
                mSonId = mStageList.get(0).getLower().get(0).getId();
            }else {
                tv_filter.setText(mStageList.get(0).getName());
                mStageId = mStageList.get(0).getId();
                mSonId = 0;
            }
            mvpPresenter.getList(mId, mSeasonId, mStageId, mSonId);
        }
    }

    @Override
    public void getDataSuccess(List<DataScheduleBean> list) {
        mAdapter.setNewData(list);
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_filter:
                if (mStageList != null && mStageList.size() > 0) {
                    List<String> list = new ArrayList<>();
                    List<List<String>> innerList = new ArrayList<>();
                    for (int i = 0; i < mStageList.size(); i++) {
                        list.add(mStageList.get(i).getName());
                        List<String> tempList = new ArrayList<>();
                        List<DataStageBean> lower = mStageList.get(i).getLower();
                        for (int j = 0; j < lower.size(); j++) {
                            tempList.add(lower.get(j).getName());
                        }
                        innerList.add(tempList);
                    }
                    pvCustomOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                            //返回的分别是三个级别的选中位置
                            if (innerList.get(options1).size() > 0 && !TextUtils.isEmpty(innerList.get(options1).get(option2))) {
                                tv_filter.setText(innerList.get(options1).get(option2));
                            }else {
                                tv_filter.setText(list.get(options1));
                            }
                            mStageId = mStageList.get(options1).getId();
                            if (mStageList.get(options1).getLower() != null && mStageList.get(options1).getLower().size() > 0) {
                                mSonId = mStageList.get(options1).getLower().get(option2).getId();
                            }else {
                                mSonId = 0;
                            }
                            mvpPresenter.getList(mId, mSeasonId, mStageId, mSonId);
                        }
                    })
                            .setLayoutRes(R.layout.pickerview_choose_language, new CustomListener() {
                                @Override
                                public void customLayout(View v) {
                                    TextView tv_title = v.findViewById(R.id.tv_title);
                                    TextView tv_cancel = v.findViewById(R.id.tv_cancel);
                                    TextView tv_confirm = v.findViewById(R.id.tv_confirm);
                                    tv_title.setText("");
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

                    pvCustomOptions.setPicker(list, innerList);//添加数据
                    pvCustomOptions.show();
                }
                break;
        }
    }
}

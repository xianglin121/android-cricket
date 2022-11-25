package com.longya.live.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.CommonCode;
import com.longya.live.R;
import com.longya.live.adapter.MatchFilterAdapter;
import com.longya.live.adapter.MatchFilterIndexAdapter;
import com.longya.live.model.FilterBean;
import com.longya.live.model.FilterInnerBean;
import com.longya.live.model.IndexBarBean;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.match.MatchFilterTwoPresenter;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.match.MatchFilterTwoView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchFilterTwoFragment extends MvpFragment<MatchFilterTwoPresenter> implements MatchFilterTwoView, View.OnClickListener {
    public static MatchFilterTwoFragment newInstance(int type) {
        MatchFilterTwoFragment fragment = new MatchFilterTwoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mType;
    private RecyclerView rv_country;
    private MatchFilterAdapter matchAdapter;
    private LinearLayoutManager matchManager;
    private RecyclerView rv_index;
    private MatchFilterIndexAdapter indexAdapter;
    private int currentPosition;
    private TextView tv_count;
    private int mSelectCountryCount;
    private int mCountryCount;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match_filter_two;
    }

    @Override
    protected MatchFilterTwoPresenter createPresenter() {
        return new MatchFilterTwoPresenter(this);
    }

    @Override
    protected void initUI() {
        mType = getArguments().getInt("type");
        rv_country = findViewById(R.id.rv_country);
        rv_index = findViewById(R.id.rv_index);
        tv_count = findViewById(R.id.tv_count);

        findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = "";
                List<FilterBean> list = matchAdapter.getData();
                for (int i = 0; i < list.size(); i++) {
                    List<FilterInnerBean> innerList = list.get(i).getList();
                    for (int j = 0; j < innerList.size(); j++) {
                        if (innerList.get(j).isCheck()) {
                            if (i == list.size()-1 && j == innerList.size()-1) {
                                id = id + innerList.get(j).getId();
                            }else {
                                id = id + innerList.get(j).getId() + ",";
                            }
                        }
                    }
                }
                if (TextUtils.isEmpty(id)) {
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("id", id);
                getActivity().setResult(CommonCode.RESULT_CODE_MATCH_FILTER, intent);
                getActivity().finish();
            }
        });
    }

    @Override
    protected void initData() {
        matchAdapter = new MatchFilterAdapter(R.layout.item_match_filter_one, new ArrayList<>());
        matchAdapter.setOnToggleListener(new MatchFilterAdapter.OnToggleListener() {
            @Override
            public void onToggle(boolean isSelect) {
                if (isSelect) {
                    if (mSelectCountryCount > 0) {
                        mSelectCountryCount--;
                    }
                }else {
                    mSelectCountryCount++;
                }
                tv_count.setText("(已选" + mSelectCountryCount + "/" + mCountryCount + "场)");
            }
        });
        matchManager = new LinearLayoutManager(getContext());
        rv_country.setLayoutManager(matchManager);
        rv_country.setAdapter(matchAdapter);
        rv_country.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                //获取第一个可见view的位置
                int firstItemPosition = matchManager.findFirstVisibleItemPosition();
                if (firstItemPosition != -1) {
                    if (firstItemPosition != currentPosition) {
                        indexAdapter.getItem(currentPosition).setSelect(false);
                        indexAdapter.getItem(firstItemPosition).setSelect(true);
                        indexAdapter.notifyDataSetChanged();
                        currentPosition = firstItemPosition;
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            }
        });

        indexAdapter = new MatchFilterIndexAdapter(R.layout.item_match_filter_one_index, new ArrayList<>());
        indexAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position != -1) {
                    indexAdapter.getItem(position).setSelect(true);
                    indexAdapter.getItem(currentPosition).setSelect(false);
                    indexAdapter.notifyDataSetChanged();
                    matchManager.scrollToPositionWithOffset(position, 0);
                    currentPosition = position;
                }
            }
        });
        rv_index.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_index.setAdapter(indexAdapter);

        mvpPresenter.getList(0, mType);
    }

    @Override
    public void getDataSuccess(List<FilterBean> competitionList, List<FilterBean> countryList, int countryCount, int selectCountryCount) {
        if (countryList != null) {
            matchAdapter.setNewData(countryList);
            List<IndexBarBean> indexList = new ArrayList<>();
            for (int i = 0; i < countryList.size(); i++) {
                FilterBean filterBean = countryList.get(i);
                IndexBarBean indexBarBean = new IndexBarBean();
                indexBarBean.setName(filterBean.getName());
                if (i == 0) {
                    indexBarBean.setSelect(true);
                }else {
                    indexBarBean.setSelect(false);
                }
                indexList.add(indexBarBean);
            }
            indexAdapter.setNewData(indexList);
        }
        mSelectCountryCount = selectCountryCount;
        mCountryCount = countryCount;
        tv_count.setText("(已选" + selectCountryCount + "/" + countryCount + "场)");
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}

package com.longya.live.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;
import com.longya.live.R;
import com.longya.live.adapter.MatchSecondAdapter;
import com.longya.live.adapter.SearchKeywordAdapter;
import com.longya.live.model.Channel;
import com.longya.live.model.JsonBean;
import com.longya.live.model.MatchListBean;
import com.longya.live.presenter.match.SearchMatchPresenter;
import com.longya.live.presenter.user.SettingPresenter;
import com.longya.live.util.DialogUtil;
import com.longya.live.util.DpUtil;
import com.longya.live.util.SpUtil;
import com.longya.live.util.ToastUtil;
import com.longya.live.util.WordUtil;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.match.SearchMatchView;
import com.longya.live.view.user.SettingView;

import java.util.ArrayList;
import java.util.List;

public class SearchMatchActivity extends MvpActivity<SearchMatchPresenter> implements SearchMatchView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, SearchMatchActivity.class);
        context.startActivity(intent);
    }

    private EditText et_input;
    private ImageView iv_close;
    private RecyclerView recyclerView;
    private MatchSecondAdapter mAdapter;
    private RecyclerView rv_history;
    private SearchKeywordAdapter mHistoryAdapter;
    private RecyclerView rv_hot;
    private SearchKeywordAdapter mHotAdapter;

    @Override
    protected SearchMatchPresenter createPresenter() {
        return new SearchMatchPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_match;
    }

    @Override
    protected void initView() {
        et_input = findViewById(R.id.et_input);
        iv_close = findViewById(R.id.iv_close);
        recyclerView = findViewById(R.id.recyclerView);
        rv_history = findViewById(R.id.rv_history);
        rv_hot = findViewById(R.id.rv_hot);

        iv_close.setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_clear).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“发送”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchContent(et_input.getText().toString());
                    return true;
                }
                return false;
            }
        });

        //搜索出来的内容
        mAdapter = new MatchSecondAdapter(new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mAdapter.getItem(position).getType() == 1) {
                    BasketballMatchDetailActivity.forward(mActivity, mAdapter.getItem(position).getSourceid());
                }else {
                    FootballMatchDetailActivity.forward(mActivity, mAdapter.getItem(position).getSourceid());
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        //历史记录
        String value = SpUtil.getInstance().getStringValue(SpUtil.MATCH_SEARCH_WORD);
        List<String> keywordList = new ArrayList<>();
        if (!TextUtils.isEmpty(value)) {
            keywordList = JSONObject.parseArray(value, String.class);
        }
        mHistoryAdapter = new SearchKeywordAdapter(R.layout.item_search_keyword, keywordList);
        mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                et_input.setText(mHistoryAdapter.getItem(position));
                et_input.setSelection(mHistoryAdapter.getItem(position).length());
                searchContent(mHistoryAdapter.getItem(position));
            }
        });
        rv_history.addItemDecoration(new SpaceItemDecoration(DpUtil.dp2px(5)));
        rv_history.setLayoutManager(new FlowLayoutManager());
        rv_history.setAdapter(mHistoryAdapter);
        //热门搜索
        mHotAdapter = new SearchKeywordAdapter(R.layout.item_search_keyword, new ArrayList<>());
        mHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                et_input.setText(mHotAdapter.getItem(position));
                et_input.setSelection(mHotAdapter.getItem(position).length());
                searchContent(mHotAdapter.getItem(position));
            }
        });
        rv_hot.addItemDecoration(new SpaceItemDecoration(DpUtil.dp2px(5)));
        rv_hot.setLayoutManager(new FlowLayoutManager());
        rv_hot.setAdapter(mHotAdapter);
        mvpPresenter.getList();
    }

    private void searchContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            iv_close.setVisibility(View.VISIBLE);
            findViewById(R.id.cl_history).setVisibility(View.GONE);
            findViewById(R.id.cl_hot).setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mvpPresenter.searchMatch(content);

            String stringValue = SpUtil.getInstance().getStringValue(SpUtil.MATCH_SEARCH_WORD);
            List<String> tempList = JSON.parseArray(stringValue, String.class);
            if (tempList == null) {
                tempList = new ArrayList<>();
            }
            if (!tempList.contains(content)) {
                tempList.add(content);
                mHistoryAdapter.setNewData(tempList);
                SpUtil.getInstance().setStringValue(SpUtil.MATCH_SEARCH_WORD, JSON.toJSONString(tempList));
            }
        }
    }

    @Override
    public void getDataSuccess(List<MatchListBean> list) {
        if (list != null) {
            mAdapter.setNewData(list);
        }
    }

    @Override
    public void getHotMatchClassifySuccess(List<Channel> list) {
        if (list != null) {
            List<String> tempList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (!TextUtils.isEmpty(list.get(i).short_name_zh)) {
                    tempList.add(list.get(i).short_name_zh);
                }
            }
            mHotAdapter.setNewData(tempList);
        }
    }

    @Override
    public void getDataFail(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                et_input.setText("");
                iv_close.setVisibility(View.GONE);
                findViewById(R.id.cl_history).setVisibility(View.VISIBLE);
                findViewById(R.id.cl_hot).setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_clear:
                SpUtil.getInstance().setStringValue(SpUtil.MATCH_SEARCH_WORD, "");
                mHistoryAdapter.setNewData(new ArrayList<>());
                break;
        }
    }
}

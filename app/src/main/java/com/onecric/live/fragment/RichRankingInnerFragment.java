package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.adapter.RankingAdapter;
import com.onecric.live.model.RankingUserBean;
import com.onecric.live.presenter.live.RichRankingPresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.RichRankingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class RichRankingInnerFragment extends MvpFragment<RichRankingPresenter> implements RichRankingView, View.OnClickListener {
    public static RichRankingInnerFragment newInstance(int type) {
        RichRankingInnerFragment fragment = new RichRankingInnerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mType;
    //第二名
    private ImageView iv_avatar_two;
    private TextView tv_name_two;
    private TextView tv_popular_two;
    private ImageView iv_state_two;
    //第一名
    private ImageView iv_avatar_one;
    private TextView tv_name_one;
    private TextView tv_popular_one;
    private ImageView iv_state_one;
    //第三名
    private ImageView iv_avatar_three;
    private TextView tv_name_three;
    private TextView tv_popular_three;
    private ImageView iv_state_three;

    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_ranking;
    private RankingAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rich_ranking_inner;
    }

    @Override
    protected RichRankingPresenter createPresenter() {
        return new RichRankingPresenter(this);
    }

    @Override
    protected void initUI() {
        mType = getArguments().getInt("type");

        iv_avatar_two = findViewById(R.id.iv_avatar_two);
        tv_name_two = findViewById(R.id.tv_name_two);
        tv_popular_two = findViewById(R.id.tv_popular_two);
        iv_state_two = findViewById(R.id.iv_state_two);
        iv_avatar_one = findViewById(R.id.iv_avatar_one);
        tv_name_one = findViewById(R.id.tv_name_one);
        tv_popular_one = findViewById(R.id.tv_popular_one);
        iv_state_one = findViewById(R.id.iv_state_one);
        iv_avatar_three = findViewById(R.id.iv_avatar_three);
        tv_name_three = findViewById(R.id.tv_name_three);
        tv_popular_three = findViewById(R.id.tv_popular_three);
        iv_state_three = findViewById(R.id.iv_state_three);
        smart_rl = findViewById(R.id.smart_rl);
        rv_ranking = findViewById(R.id.rv_ranking);
    }

    @Override
    protected void initData() {
        smart_rl.setRefreshHeader(new ClassicsHeader(getContext()));
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableLoadMore(false);
        smart_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(mType);
            }
        });

        mAdapter = new RankingAdapter(R.layout.item_rich_ranking, new ArrayList<>());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                RankingUserBean item = mAdapter.getItem(position);
                if (item.getIs_live() == 0 && item.getIs_watchlist() == 0) {
                    item.setIs_watchlist(1);
                    iv_state_two.setSelected(true);
                    mvpPresenter.doFollow(item.getUid());
                }
            }
        });
        rv_ranking.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_ranking.setAdapter(mAdapter);

        mvpPresenter.getList(mType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<RankingUserBean> list) {
        smart_rl.finishRefresh();
        if (list != null) {
            if (list.size() > 0) {
                RankingUserBean userBean = list.get(0);
                GlideUtil.loadUserImageDefault(getContext(), userBean.getAvatar(), iv_avatar_one);
                if (!TextUtils.isEmpty(userBean.getUser_nickname())) {
                    tv_name_one.setText(userBean.getUser_nickname());
                }else {
                    tv_name_one.setText("");
                }
                if (!TextUtils.isEmpty(userBean.getAmount())) {
                    tv_popular_one.setText("壕气" + userBean.getAmount());
                }else {
                    tv_popular_one.setText("");
                }
                if (userBean.getIs_live() == 1) {
                    iv_state_one.setBackgroundResource(R.mipmap.icon_state_living);
                }else {
                    iv_state_one.setBackgroundResource(R.drawable.selector_headline_user_follow);
                    if (userBean.getIs_watchlist() == 1) {
                        iv_state_one.setSelected(true);
                    }else {
                        iv_state_one.setSelected(false);
                    }
                    iv_state_one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (userBean.getIs_live() == 0 && userBean.getIs_watchlist() == 0) {
                                userBean.setIs_watchlist(1);
                                iv_state_one.setSelected(true);
                                mvpPresenter.doFollow(userBean.getUid());
                            }
                        }
                    });
                }
            }
            if (list.size() > 1) {
                RankingUserBean userBean = list.get(1);
                GlideUtil.loadUserImageDefault(getContext(), userBean.getAvatar(), iv_avatar_two);
                if (!TextUtils.isEmpty(userBean.getUser_nickname())) {
                    tv_name_two.setText(userBean.getUser_nickname());
                }else {
                    tv_name_two.setText("");
                }
                if (!TextUtils.isEmpty(userBean.getAmount())) {
                    tv_popular_two.setText("壕气" + userBean.getAmount());
                }else {
                    tv_popular_two.setText("");
                }
                if (userBean.getIs_live() == 1) {
                    iv_state_two.setBackgroundResource(R.mipmap.icon_state_living);
                }else {
                    iv_state_two.setBackgroundResource(R.drawable.selector_headline_user_follow);
                    if (userBean.getIs_watchlist() == 1) {
                        iv_state_two.setSelected(true);
                    }else {
                        iv_state_two.setSelected(false);
                    }
                    iv_state_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (userBean.getIs_live() == 0 && userBean.getIs_watchlist() == 0) {
                                userBean.setIs_watchlist(1);
                                iv_state_two.setSelected(true);
                                mvpPresenter.doFollow(userBean.getUid());
                            }
                        }
                    });
                }
            }
            if (list.size() > 2) {
                RankingUserBean userBean = list.get(2);
                GlideUtil.loadUserImageDefault(getContext(), userBean.getAvatar(), iv_avatar_three);
                if (!TextUtils.isEmpty(userBean.getUser_nickname())) {
                    tv_name_three.setText(userBean.getUser_nickname());
                }else {
                    tv_name_three.setText("");
                }
                if (!TextUtils.isEmpty(userBean.getAmount())) {
                    tv_popular_three.setText("壕气" + userBean.getAmount());
                }else {
                    tv_popular_three.setText("");
                }
                if (userBean.getIs_live() == 1) {
                    iv_state_three.setBackgroundResource(R.mipmap.icon_state_living);
                }else {
                    iv_state_three.setBackgroundResource(R.drawable.selector_headline_user_follow);
                    if (userBean.getIs_watchlist() == 1) {
                        iv_state_three.setSelected(true);
                    }else {
                        iv_state_three.setSelected(false);
                    }
                    iv_state_three.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (userBean.getIs_live() == 0 && userBean.getIs_watchlist() == 0) {
                                userBean.setIs_watchlist(1);
                                iv_state_three.setSelected(true);
                                mvpPresenter.doFollow(userBean.getUid());
                            }
                        }
                    });
                }
            }
            if (list.size() > 3) {
                for (int i = 0; i < 3; i++) {
                    list.remove(0);
                }
                mAdapter.setNewData(list);
            }
        }
    }

    @Override
    public void getDataFail(String msg) {

    }
}

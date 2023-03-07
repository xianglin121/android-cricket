package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coorchice.library.SuperTextView;
import com.google.android.material.appbar.AppBarLayout;
import com.onecric.live.Constant;
import com.onecric.live.R;
import com.onecric.live.adapter.ThemeCommunityAdapter;
import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.ThemeClassifyBean;
import com.onecric.live.presenter.theme.CommunityDetailPresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.theme.CommunityDetailView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class CommunityDetailActivity extends MvpActivity<CommunityDetailPresenter> implements CommunityDetailView, View.OnClickListener {

    public static void forward(Context context, int id) {
        Intent intent = new Intent(context, CommunityDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private int mId;
    private AppBarLayout appBarLayout;
    private ConstraintLayout cl_title_one, cl_title_two;
    private ImageView iv_bg;
    private TextView tv_community_name_one;
    private TextView tv_community_desc;
    private TextView tv_community_name_two;
    private SuperTextView tv_all;
    private SuperTextView tv_best;
    private TextView tv_sort;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private ThemeCommunityAdapter mAdapter;

    private int mPage = 1;
    private int mIsAll = 0;
    private int mSortType= 1;

    @Override
    protected CommunityDetailPresenter createPresenter() {
        return new CommunityDetailPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_community_detail;
    }

    @Override
    protected void initView() {
        mId = getIntent().getIntExtra("id", 0);
        appBarLayout = findViewById(R.id.appBarLayout);
        cl_title_one = findViewById(R.id.cl_title_one);
        cl_title_two = findViewById(R.id.cl_title_two);
        iv_bg = findViewById(R.id.iv_bg);
        tv_community_name_one = findViewById(R.id.tv_community_name_one);
        tv_community_desc = findViewById(R.id.tv_community_desc);
        tv_community_name_two = findViewById(R.id.tv_community_name_two);
        tv_all = findViewById(R.id.tv_all);
        tv_best = findViewById(R.id.tv_best);
        tv_sort = findViewById(R.id.tv_sort);
        smart_rl = findViewById(R.id.smart_rl);
        recyclerview = findViewById(R.id.recyclerview);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_back_two).setOnClickListener(this);
        tv_all.setOnClickListener(this);
        tv_best.setOnClickListener(this);
        tv_sort.setOnClickListener(this);

        cl_title_two.setAlpha(0f);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = (Math.abs(verticalOffset * 1.0f)/appBarLayout.getTotalScrollRange());
                cl_title_one.setAlpha(1f - percent);
                cl_title_two.setAlpha(percent);
            }
        });
    }

    @Override
    protected void initData() {
        MaterialHeader materialHeader = new MaterialHeader(this);
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(this));
        smart_rl.setEnableRefresh(false);
        smart_rl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mPage, mId, mIsAll, mSortType);
            }
        });

        mAdapter = new ThemeCommunityAdapter(R.layout.item_theme_community, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommunityCommentActivity.forward(mActivity, mAdapter.getItem(position).getUid(), mAdapter.getItem(position).getId());
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_like) {
                    CommunityBean item = mAdapter.getItem(position);
                    int like = item.getLikes();
                    if (item.getIs_likes() == 1) {
                        like--;
                        item.setIs_likes(0);
                    }else {
                        like++;
                        item.setIs_likes(1);
                    }
                    item.setLikes(like);
                    mAdapter.notifyItemChanged(position, Constant.PAYLOAD);
                    mvpPresenter.doCommunityLike(item.getId());
                }
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(mAdapter);

        mvpPresenter.getList(true, 1, mId, mIsAll, 1);
    }

    @Override
    public void getDataSuccess(ThemeClassifyBean model) {
        if (mPage == 1) {
            if (model != null) {
                GlideUtil.loadImageDefault(this, model.getImg(), iv_bg);
                if (!TextUtils.isEmpty(model.getName())) {
                    tv_community_name_one.setText(model.getName());
                    tv_community_name_two.setText(model.getName());
                }
                if (!TextUtils.isEmpty(model.getContent())) {
                    tv_community_desc.setText(model.getContent());
                }
            }
        }
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<CommunityBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                if (list.size() > 0) {
                    hideEmptyView();
                }else {
                    showEmptyView();
                }
                mAdapter.setNewData(list);
            }else {
                mAdapter.setNewData(new ArrayList<>());
                showEmptyView();
            }
        }else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mAdapter.addData(list);
            }else {
                smart_rl.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        smart_rl.finishRefresh();
        smart_rl.finishLoadMore();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.iv_back_two:
                finish();
                break;
            case R.id.tv_all:
                if (mIsAll == 0) {
                    return;
                }
                mIsAll = 0;
                tv_all.setTextColor(getResources().getColor(R.color.c_E3AC72));
                tv_all.setSolid(getResources().getColor(R.color.c_FFF1E0));
                tv_best.setTextColor(getResources().getColor(R.color.c_666666));
                tv_best.setSolid(getResources().getColor(R.color.white));
                mvpPresenter.getList(true, 1, mId, mIsAll, mSortType);
                break;
            case R.id.tv_best:
                if (mIsAll == 1) {
                    return;
                }
                mIsAll = 1;
                tv_all.setTextColor(getResources().getColor(R.color.c_666666));
                tv_all.setSolid(getResources().getColor(R.color.white));
                tv_best.setTextColor(getResources().getColor(R.color.c_E3AC72));
                tv_best.setSolid(getResources().getColor(R.color.c_FFF1E0));
                mvpPresenter.getList(true, 1, mId, mIsAll, mSortType);
                break;
            case R.id.tv_sort:
                if (mSortType == 1) {
                    mSortType = 0;
                    tv_sort.setText(getString(R.string.best_new_reply));
                }else {
                    mSortType = 1;
                    tv_sort.setText(getString(R.string.best_new_post));
                }
                mvpPresenter.getList(true, 1, mId, mIsAll, mSortType);
                break;
        }
    }
}

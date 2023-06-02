package com.onecric.live.activity;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.adapter.VideoAllAdapter;
import com.onecric.live.event.UpdateLoginTokenEvent;
import com.onecric.live.model.HistoryLiveBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.LiveVideoBean;
import com.onecric.live.model.ViewMoreBean;
import com.onecric.live.presenter.live.LiveMorePresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.live.LiveMoreView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class VideoMoreActivity extends MvpActivity<LiveMorePresenter> implements LiveMoreView {

    public static void forward(Context context,String matchTitle) {
        Intent intent = new Intent(context, VideoMoreActivity.class);
        intent.putExtra("matchTitle", matchTitle);
        context.startActivity(intent);
    }

    private String matchTitle;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private LinearLayout ll_title;
    private TextView tv_title;
    private ImageView iv_avatar;

    private VideoAllAdapter mAllAdapter;

    private RecyclerViewSkeletonScreen skeletonScreen;
    private int mPage = 1;

    @Override
    protected LiveMorePresenter createPresenter() {
        return new LiveMorePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_more;
    }

    @Override
    protected void initView() {
        matchTitle = getIntent().getStringExtra("matchTitle");

        smart_rl = findViewById(R.id.smart_rl);
        recyclerview = findViewById(R.id.recyclerview);
        ll_title = findViewById(R.id.ll_title);
        tv_title = findViewById(R.id.tv_title);
        iv_avatar = findViewById(R.id.iv_avatar);

        ll_title.setVisibility(View.VISIBLE);

        if(!TextUtils.isEmpty(matchTitle)){
            tv_title.setText(matchTitle+"");
        }

        if (CommonAppConfig.getInstance().getUserBean() != null) {
            GlideUtil.loadUserImageDefault(this, CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar);
        } else {
            iv_avatar.setImageResource(R.mipmap.bg_avatar_default);
        }

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.iv_avatar).setOnClickListener(v -> {
            if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                OneLogInActivity.forward(mActivity);
            } else {
                if (!isFastDoubleClick())
                    PersonalHomepageActivity.forward(mActivity, CommonAppConfig.getInstance().getUid());
            }
        });

        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        MaterialHeader materialHeader = new MaterialHeader(this);
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(this));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(!TextUtils.isEmpty(matchTitle) ){
                    mvpPresenter.getMoreVideoList(false,matchTitle,mPage);
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if(mAllAdapter.getItemCount()<=0){
                    skeletonScreen.show();
                }
                if(!TextUtils.isEmpty(matchTitle) ){
                    mvpPresenter.getMoreVideoList(true,matchTitle,1);
                }
            }
        });

        View inflate2 = LayoutInflater.from(this).inflate(R.layout.layout_common_empty, null, false);
        inflate2.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);

        if(!TextUtils.isEmpty(matchTitle)){
            recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
            mAllAdapter = new VideoAllAdapter(R.layout.item_video_all, new ArrayList<>());
            mAllAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                        OneLogInActivity.forward(mActivity);
                    }else{
                        OneVideoDetailActivity.forward(mActivity,"",mAllAdapter.getItem(position).id);
                    }
                }
            });
            mAllAdapter.setEmptyView(inflate2);
            recyclerview.setAdapter(mAllAdapter);
            skeletonScreen = Skeleton.bind(recyclerview)
                    .adapter(mAllAdapter)
                    .shimmer(false)
                    .count(10)
                    .load(R.layout.item_live_video_skeleton)
                    .show();
        }
        smart_rl.autoRefresh();
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<LiveBean> list) {

    }

    @Override
    public void getDataHistorySuccess(boolean isRefresh, List<HistoryLiveBean> list) {

    }

    @Override
    public void getMatchVideoSuccess(List<HistoryLiveBean> list) {

    }

    @Override
    public void getTournamentSuccess(List<LiveVideoBean.LBean> list) {

    }

    @Override
    public void getVideoSuccess(boolean isRefresh,List<ViewMoreBean> list) {
        skeletonScreen.hide();
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                mAllAdapter.setNewData(list);
            }else {
                mAllAdapter.setNewData(new ArrayList<>());
            }
        }else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mAllAdapter.addData(list);
            }else {
                smart_rl.finishLoadMoreWithNoMoreData();
            }
        }
    }


    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {
        smart_rl.finishRefresh();
        smart_rl.finishLoadMore();
    }


    //登录成功，更新信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateLoginTokenEvent(UpdateLoginTokenEvent event) {
        if (event != null) {
            smart_rl.autoRefresh();
            if (CommonAppConfig.getInstance().getUserBean() != null) {
                GlideUtil.loadUserImageDefault(this, CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar);
            } else {
                iv_avatar.setImageResource(R.mipmap.bg_avatar_default);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}

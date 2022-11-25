package com.longya.live.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.CommonAppConfig;
import com.longya.live.R;
import com.longya.live.activity.LoginActivity;
import com.longya.live.activity.VideoPagerActivity;
import com.longya.live.activity.VideoPublishActivity;
import com.longya.live.adapter.VideoAdapter;
import com.longya.live.adapter.decoration.StaggeredDividerItemDecoration;
import com.longya.live.event.UpdateVideoLikeEvent;
import com.longya.live.model.JsonBean;
import com.longya.live.model.ShortVideoBean;
import com.longya.live.presenter.theme.ThemePresenter;
import com.longya.live.presenter.video.VideoPresenter;
import com.longya.live.util.ToastUtil;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.theme.ThemeView;
import com.longya.live.view.video.VideoView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoFragment extends MvpFragment<VideoPresenter> implements VideoView {

    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_video;
    private VideoAdapter mAdapter;

    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected VideoPresenter createPresenter() {
        return new VideoPresenter(this);
    }

    @Override
    protected void initUI() {
        smart_rl = findViewById(R.id.smart_rl);
        rv_video = findViewById(R.id.rv_video);

        findViewById(R.id.iv_publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonAppConfig.getInstance().getUserBean() != null) {
                    if (CommonAppConfig.getInstance().getUserBean().getIs_writer() == 1) {
                        VideoPublishActivity.forward(getContext());
                    }else {
                        ToastUtil.show(getActivity().getString(R.string.please_join_writer));
                    }
                }else {
                    LoginActivity.forward(getContext());
                }
            }
        });
    }

    @Override
    protected void initData() {
        smart_rl.setRefreshHeader(new ClassicsHeader(getContext()));
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, 1);
            }
        });

        mAdapter = new VideoAdapter(R.layout.item_video, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoPagerActivity.forward(getContext(), mAdapter.getData(), position, mPage);
            }
        });
        //解决瀑布流从底部到顶部出现画面重新排版动画还有间距出现问题
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rv_video.setItemAnimator(null);
        rv_video.setLayoutManager(staggeredGridLayoutManager);
        rv_video.addItemDecoration(new StaggeredDividerItemDecoration(getContext(), 10, 2));
        rv_video.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<ShortVideoBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                if (list.size() > 0) {
                    hideEmptyView();
                }else {
                    showEmptyView();
                }
                mAdapter.getData().clear();
                mAdapter.getData().addAll(list);
                mAdapter.notifyItemInserted(list.size());
            }else {
                mAdapter.setNewData(new ArrayList<>());
                hideEmptyView();
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
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateVideoLikeEvent(UpdateVideoLikeEvent event) {
        if (event != null) {
            for (int i = 0; i < mAdapter.getData().size(); i++) {
                ShortVideoBean shortVideoBean = mAdapter.getData().get(i);
                if (shortVideoBean.getId() == event.id) {
                    int likeCount =  shortVideoBean.getLikes();
                    if (shortVideoBean.getIs_likes() == 1) {
                        shortVideoBean.setIs_likes(0);
                        likeCount--;
                    }else {
                        shortVideoBean.setIs_likes(1);
                        likeCount++;
                    }
                    shortVideoBean.setLikes(likeCount);
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

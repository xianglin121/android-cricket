package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.activity.VideoPagerActivity;
import com.onecric.live.activity.VideoPublishActivity;
import com.onecric.live.adapter.VideoAdapter;
import com.onecric.live.adapter.decoration.StaggeredDividerItemDecoration;
import com.onecric.live.event.UpdateVideoLikeEvent;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.presenter.user.PersonalVideosPresenter;
import com.onecric.live.presenter.video.VideoPresenter;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.video.VideoView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class PersonalVideoFragment extends MvpFragment<PersonalVideosPresenter> implements VideoView {

    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_video;
    private VideoAdapter mAdapter;

    private int mPage = 1;
    private int type = 0;
    private LinearLayout ll_select;
    private PopupWindow popupWindow;
    private String id;
    //    private int selectPosition = 0;


    public static PersonalVideoFragment newInstance(String id) {
        PersonalVideoFragment fragment = new PersonalVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_video;
    }

    @Override
    protected PersonalVideosPresenter createPresenter() {
        return new PersonalVideosPresenter(this);
    }

    @Override
    protected void initUI() {
        smart_rl = findViewById(R.id.smart_rl);
        rv_video = findViewById(R.id.rv_video);
        ll_select = findViewById(R.id.ll_select);
        ll_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_view, null);
                TextView all = view.findViewById(R.id.all);
                TextView published = view.findViewById(R.id.published);
                TextView under_review = view.findViewById(R.id.under_review);
                TextView audit_failure = view.findViewById(R.id.audit_failure);
                all.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                published.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                under_review.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                audit_failure.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                switch (type) {
                    case 0:
                        all.setTextColor(getActivity().getResources().getColor(R.color.c_DC3C23));
                        break;
                    case 1:
                        published.setTextColor(getActivity().getResources().getColor(R.color.c_DC3C23));
                        break;
                    case 2:
                        under_review.setTextColor(getActivity().getResources().getColor(R.color.c_DC3C23));
                        break;
                    case 3:
                        audit_failure.setTextColor(getActivity().getResources().getColor(R.color.c_DC3C23));
                        break;
                }
                all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        type = 0;
                        all.setTextColor(getActivity().getResources().getColor(R.color.c_DC3C23));
                        published.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                        under_review.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                        audit_failure.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                        if (popupWindow != null) {
                            if (popupWindow.isShowing())
                                popupWindow.dismiss();
                        }
                        mvpPresenter.getList(true, 1, type, Integer.parseInt(id));
                    }
                });
                published.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        type = 1;
                        all.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                        published.setTextColor(getActivity().getResources().getColor(R.color.c_DC3C23));
                        under_review.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                        audit_failure.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                        if (popupWindow != null) {
                            if (popupWindow.isShowing())
                                popupWindow.dismiss();
                        }
                        mvpPresenter.getList(true, 1, type, Integer.parseInt(id));
                    }
                });
                under_review.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        type = 2;
                        all.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                        published.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                        under_review.setTextColor(getActivity().getResources().getColor(R.color.c_DC3C23));
                        audit_failure.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                        if (popupWindow != null) {
                            if (popupWindow.isShowing())
                                popupWindow.dismiss();
                        }
                        mvpPresenter.getList(true, 1, type, Integer.parseInt(id));
                    }
                });
                audit_failure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        type = 3;
                        all.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                        published.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                        under_review.setTextColor(getActivity().getResources().getColor(R.color.c_666666));
                        audit_failure.setTextColor(getActivity().getResources().getColor(R.color.c_DC3C23));
                        if (popupWindow != null) {
                            if (popupWindow.isShowing())
                                popupWindow.dismiss();
                        }
                        mvpPresenter.getList(true, 1, type, Integer.parseInt(id));
                    }
                });
                showPop(view);
            }
        });

        findViewById(R.id.iv_publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonAppConfig.getInstance().getUserBean() != null) {
                    if (CommonAppConfig.getInstance().getUserBean().getIs_writer() == 1) {
                        VideoPublishActivity.forward(getContext());
                    } else {
                        ToastUtil.show(getActivity().getString(R.string.please_join_writer));
                    }
                } else {
                    LoginActivity.forward(getContext());
                }
            }
        });
    }

    @Override
    protected void initData() {
        id = getArguments().getString("id");
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mPage, type, Integer.parseInt(id));
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, 1, type, Integer.parseInt(id));
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
                    ll_select.setVisibility(View.VISIBLE);
                } else {
                    showEmptyView();
                    ll_select.setVisibility(View.GONE);
                }
                mAdapter.getData().clear();
                mAdapter.getData().addAll(list);
                mAdapter.notifyItemInserted(list.size());
            } else {
                mAdapter.setNewData(new ArrayList<>());
                hideEmptyView();
            }
        } else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mAdapter.addData(list);
            } else {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateVideoLikeEvent(UpdateVideoLikeEvent event) {
        if (event != null) {
            for (int i = 0; i < mAdapter.getData().size(); i++) {
                ShortVideoBean shortVideoBean = mAdapter.getData().get(i);
                if (shortVideoBean.getId() == event.id) {
                    int likeCount = shortVideoBean.getLikes();
                    if (shortVideoBean.getIs_likes() == 1) {
                        shortVideoBean.setIs_likes(0);
                        likeCount--;
                    } else {
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


    public void showPop(View view) {
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);      //点击弹窗外部是否取消弹窗
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.bottomToTopAnim);  //设置自定义好的动画
        //弹窗出现外部为阴影
        WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
        attributes.alpha = 0.5f;
        getActivity().getWindow().setAttributes(attributes);
        //弹窗取消监听 取消之后恢复阴影
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
                attributes.alpha = 1;
                getActivity().getWindow().setAttributes(attributes);
            }
        });
        // 获取控件位置
        int[] lv2 = {0, 0};
        ll_select.getLocationInWindow(lv2);
        // 设置弹窗位置
        popupWindow.showAtLocation(ll_select, Gravity.NO_GRAVITY, lv2[0] - DpUtil.dp2px(10), lv2[1] + DpUtil.dp2px(10));
    }
}

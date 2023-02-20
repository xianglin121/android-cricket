package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.Constant;
import com.onecric.live.R;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.LiveMovingCommentActivity;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.adapter.LiveAnchorMovingAdapter;
import com.onecric.live.adapter.LivePreviewAdapter;
import com.onecric.live.adapter.LiveReplyAdapter;
import com.onecric.live.custom.ButtonFollowView;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.model.LiveUserBean;
import com.onecric.live.model.MovingBean;
import com.onecric.live.model.ReserveLiveBean;
import com.onecric.live.presenter.live.LiveAnchorPresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.LiveAnchorView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LiveAnchorFragment extends MvpFragment<LiveAnchorPresenter> implements LiveAnchorView, View.OnClickListener {
    public static LiveAnchorFragment newInstance(int anchorId) {
        LiveAnchorFragment fragment = new LiveAnchorFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("anchorId", anchorId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mAnchorId;
    private TextView tv_notice;
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_desc;
    private ButtonFollowView btn_follow;
    private TextView tv_anchor_moving, tv_live_preview, tv_live_replay;
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_moving;//主播动态
    private LiveAnchorMovingAdapter mMovingAdapter;
    private int mMovingPage = 1;
    private RecyclerView rv_preview;//直播预告
    private LivePreviewAdapter mPreviewAdapter;
    private int mPreviewPage = 1;
    private Map<String, Integer> mValueMap = new ArrayMap<>();
    private List<String> mAddDateList = new ArrayList<>();
    private RecyclerView rv_reply;//直播回放
    private LiveReplyAdapter mReplyAdapter;

    private LiveUserBean mUserBean;

    private LoginDialog loginDialog;
    public void setLoginDialog(LoginDialog dialog){
        loginDialog = dialog;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_anchor;
    }

    @Override
    protected LiveAnchorPresenter createPresenter() {
        return new LiveAnchorPresenter(this);
    }

    @Override
    protected void initUI() {
        mAnchorId = getArguments().getInt("anchorId");

        tv_notice = findViewById(R.id.tv_notice);
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_desc = findViewById(R.id.tv_desc);
        btn_follow = findViewById(R.id.btn_follow);
        tv_anchor_moving = findViewById(R.id.tv_anchor_moving);
        tv_live_preview = findViewById(R.id.tv_live_preview);
        tv_live_replay = findViewById(R.id.tv_live_replay);
        smart_rl = findViewById(R.id.smart_rl);
        rv_moving = findViewById(R.id.rv_moving);
        rv_preview = findViewById(R.id.rv_preview);
        rv_reply = findViewById(R.id.rv_reply);

        btn_follow.setOnClickListener(this);
        tv_anchor_moving.setOnClickListener(this);
        tv_live_preview.setOnClickListener(this);
        tv_live_replay.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tv_notice.setSelected(true);
        tv_anchor_moving.setSelected(true);

        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableRefresh(false);
        smart_rl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (tv_anchor_moving.isSelected()) {
                    mvpPresenter.getMovingList(mMovingPage, mAnchorId);
                }else if (tv_live_preview.isSelected()) {
                    mvpPresenter.getReserveLiveList(mPreviewPage, mAnchorId);
                }
            }
        });

        mMovingAdapter = new LiveAnchorMovingAdapter(R.layout.item_live_anchor_moving, new ArrayList<>());
        mMovingAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_comment) {
                    LiveMovingCommentActivity.forward(getContext(), mAnchorId, mMovingAdapter.getItem(position).getId());
                }else if (view.getId() == R.id.ll_like) {
                    MovingBean item = mMovingAdapter.getItem(position);
                    int like = item.getLike();
                    if (item.getIs_likes() == 0) {
                        item.setIs_likes(1);
                        like++;
                    }else {
                        item.setIs_likes(0);
                        like--;
                    }
                    item.setLike(like);
                    mMovingAdapter.notifyItemChanged(position, Constant.PAYLOAD);
                    mvpPresenter.doLike(item.getId());
                }
            }
        });
        rv_moving.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_moving.setAdapter(mMovingAdapter);

        mPreviewAdapter = new LivePreviewAdapter(new ArrayList<>());
        mPreviewAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_reserve) {
                    if (mPreviewAdapter.getItem(position).getIs_reserve() == 0) {
                        mvpPresenter.doReserve(position, mPreviewAdapter.getItem(position).getId());
                    }
                }
            }
        });
        rv_preview.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_preview.setAdapter(mPreviewAdapter);

        mReplyAdapter = new LiveReplyAdapter(R.layout.item_live_reply, new ArrayList<>());
        rv_reply.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_reply.setAdapter(mReplyAdapter);

        //加载主播动态列表
        mvpPresenter.getMovingList(1, mAnchorId);

        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
            if (CommonAppConfig.getInstance().getUid().equals(String.valueOf(mAnchorId))) {
                btn_follow.setVisibility(View.GONE);
            }else {
                btn_follow.setVisibility(View.VISIBLE);
            }
        }else {
            btn_follow.setVisibility(View.VISIBLE);
        }
    }

    public void updateFollowData() {
        if (((LiveDetailActivity)getActivity()).mLiveRoomBean != null) {
            mUserBean = ((LiveDetailActivity)getActivity()).mLiveRoomBean.getUserData();
        }
        if (mUserBean != null) {
            GlideUtil.loadUserImageDefault(getContext(), mUserBean.getAvatar(), iv_avatar);
            if (!TextUtils.isEmpty(mUserBean.getUser_nickname())) {
                tv_name.setText(mUserBean.getUser_nickname());
            }
            if (!TextUtils.isEmpty(mUserBean.getSignature())) {
                tv_desc.setText(mUserBean.getSignature());
            }
            if (mUserBean.getIs_attention() == 1) {
                btn_follow.setFollow(true);
            }else {
                btn_follow.setFollow(false);
            }
            if (!TextUtils.isEmpty(mUserBean.getAnnouncement())) {
                tv_notice.setText(mUserBean.getAnnouncement());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_follow:
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    if (mUserBean!=null && mUserBean.getIs_attention() == 0) {
                        ((LiveDetailActivity)getActivity()).doFollow();
                    }
                }else {
                    ToastUtil.show(getString(R.string.please_login));
                }
                break;
            case R.id.tv_anchor_moving:
                if (tv_anchor_moving.isSelected()) {
                    return;
                }
                tv_anchor_moving.setSelected(true);
                tv_live_preview.setSelected(false);
                tv_live_replay.setSelected(false);
                rv_preview.setVisibility(View.GONE);
                rv_reply.setVisibility(View.GONE);
                if (mMovingAdapter.getData().size() == 0) {
                    rv_moving.setVisibility(View.GONE);
                    findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
                }else {
                    rv_moving.setVisibility(View.VISIBLE);
                    findViewById(R.id.ll_empty).setVisibility(View.GONE);
                }
                break;
            case R.id.tv_live_preview:
                if (tv_live_preview.isSelected()) {
                    return;
                }
                tv_anchor_moving.setSelected(false);
                tv_live_preview.setSelected(true);
                tv_live_replay.setSelected(false);
                rv_moving.setVisibility(View.GONE);
                rv_preview.setVisibility(View.VISIBLE);
                rv_reply.setVisibility(View.GONE);
                //加载直播预告列表
                mvpPresenter.getReserveLiveList(1, mAnchorId);
                break;
            case R.id.tv_live_replay:
                if (tv_live_replay.isSelected()) {
                    return;
                }
                tv_anchor_moving.setSelected(false);
                tv_live_preview.setSelected(false);
                tv_live_replay.setSelected(true);
                rv_moving.setVisibility(View.GONE);
                rv_preview.setVisibility(View.GONE);
                if (mReplyAdapter.getData().size() == 0) {
                    rv_reply.setVisibility(View.GONE);
                    findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
                }else {
                    rv_reply.setVisibility(View.VISIBLE);
                    findViewById(R.id.ll_empty).setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<MovingBean> list) {
        mMovingPage++;
        if (list != null && list.size() > 0) {
            smart_rl.finishLoadMore();
            mMovingAdapter.addData(list);
        }else {
            showEmptyView();
            smart_rl.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getReserveListSuccess(List<ReserveLiveBean> list) {
        mPreviewPage++;
        if (list != null && list.size() > 0) {
            smart_rl.finishLoadMore();
            for (int i = 0; i < list.size(); i++) {
                ReserveLiveBean reserveLiveBean = list.get(i);
                String match_time = reserveLiveBean.getMatch_time();
                if (match_time.contains(" ")) {
                    String[] str = match_time.split(" ");
                    if (!mValueMap.containsKey(str[0])) {
                        mValueMap.put(str[0], i);
                    }
                }
            }
            int headCount = 0;
            for (String date : mValueMap.keySet()) {
                boolean has = false;
                for (int i = 0; i < mAddDateList.size(); i++) {
                    if (date.equals(mAddDateList.get(i))) {
                        has = true;
                        break;
                    }
                }
                if (!has) {
                    Integer position = mValueMap.get(date);
                    ReserveLiveBean head = new ReserveLiveBean();
                    head.setItemType(ReserveLiveBean.HEAD);
                    head.setStart2(date);
                    list.add(position+headCount, head);
                    headCount++;
                }
            }
            mPreviewAdapter.addData(list);
        }else {
            smart_rl.finishLoadMoreWithNoMoreData();
        }
        if (mPreviewAdapter.getData().size() == 0) {
            rv_preview.setVisibility(View.GONE);
            findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        }else {
            rv_preview.setVisibility(View.VISIBLE);
            findViewById(R.id.ll_empty).setVisibility(View.GONE);
        }
    }

    @Override
    public void doReserveSuccess(int position) {
        mPreviewAdapter.getItem(position).setIs_reserve(1);
        mPreviewAdapter.notifyItemChanged(position);
    }
}

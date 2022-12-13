package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.adapter.MatchChatAdapter;
import com.onecric.live.model.CustomMsgBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.match.MatchChatPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.match.MatchChatView;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;

import java.util.ArrayList;

public class MatchChatFragment extends MvpFragment<MatchChatPresenter> implements MatchChatView, View.OnClickListener {

    public static MatchChatFragment newInstance(int type, int id) {
        MatchChatFragment fragment = new MatchChatFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mType;
    private int mMatchId;
    private String mRoomId;

    private TextView tv_notice;
    private RecyclerView rv_chat;
    private MatchChatAdapter mChatAdapter;
    private LinearLayoutManager mChatLayoutManager;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match_chat;
    }

    @Override
    protected MatchChatPresenter createPresenter() {
        return new MatchChatPresenter(this);
    }

    @Override
    protected void initUI() {
        mType = getArguments().getInt("type");
        mMatchId = getArguments().getInt("id");

        tv_notice = findViewById(R.id.tv_notice);
        rv_chat = findViewById(R.id.rv_chat);
    }

    @Override
    protected void initData() {
        tv_notice.setSelected(true);
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getAnnouncement())) {
            tv_notice.setText(CommonAppConfig.getInstance().getConfig().getAnnouncement());
        }
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            if (mType == 0) {
                mRoomId = "f" + mMatchId;
            }else if (mType == 1) {
                mRoomId = "b" + mMatchId;
            }
            V2TIMManager.getInstance().joinGroup(mRoomId, "", new V2TIMCallback() {
                @Override
                public void onSuccess() {
                    mvpPresenter.initListener();
                }

                @Override
                public void onError(int i, String s) {
                }
            });
        }
        mChatAdapter = new MatchChatAdapter(R.layout.item_match_chat, new ArrayList<>());
        mChatLayoutManager = new LinearLayoutManager(getContext());
        rv_chat.setLayoutManager(mChatLayoutManager);
        rv_chat.setAdapter(mChatAdapter);
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
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onGroupForceExit(String groupId) {

    }

    @Override
    public void exitGroupChat(String chatId) {

    }

    @Override
    public void onApplied(int unHandledSize) {

    }

    @Override
    public void handleRevoke(String msgId) {

    }

    @Override
    public void onRecvNewMessage(MessageInfo messageInfo) {
        if (TextUtils.isEmpty(mRoomId) || TextUtils.isEmpty(messageInfo.getGroupId())) {
            return;
        }
        if (messageInfo != null) {
            //筛选当前房间的消息，其他房间的消息不显示
            if (messageInfo.getGroupId().equals(mRoomId)) {
                CustomMsgBean customMsgBean = JSONObject.parseObject(messageInfo.getExtra().toString(), CustomMsgBean.class);
                if (messageInfo.getMsgType() == MessageInfo.MSG_TYPE_BG_DANMU) {
                    updateMsg(messageInfo);
                }
            }
        }
    }

    @Override
    public void onGroupNameChanged(String groupId, String newName) {

    }

    public void updateMsg(MessageInfo messageInfo) {
        mChatAdapter.addData(messageInfo);
        if (mChatLayoutManager.findLastVisibleItemPosition() != (mChatAdapter.getData().size()-1)) {//如果最后一项item不可见，那添加消息不会滑动到底部
            rv_chat.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁聊天信息接收监听
        mvpPresenter.destroyListener();
    }
}

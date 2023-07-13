package com.onecric.live.fragment;

import static com.tencent.qcloud.tuikit.tuichat.util.ChatMessageInfoUtil.buildRequestMessage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.Constant;
import com.onecric.live.HttpConstant;
import com.onecric.live.R;
import com.onecric.live.activity.ChargeActivity;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.OpenNobleActivity;
import com.onecric.live.activity.WebViewActivity;
import com.onecric.live.adapter.GiftViewPagerRecyclerAdapter;
import com.onecric.live.adapter.LiveChatAdapter;
import com.onecric.live.adapter.RedEnvelopeAdapter;
import com.onecric.live.adapter.layoutmanager.OnViewPagerListener;
import com.onecric.live.adapter.layoutmanager.ViewPagerLayoutManager;
import com.onecric.live.custom.TreasureChestDialog;
import com.onecric.live.fragment.dialog.InputChatMsgDialogFragment;
import com.onecric.live.model.BannerBean;
import com.onecric.live.model.BlockFunctionBean;
import com.onecric.live.model.BoxBean;
import com.onecric.live.model.CustomMsgBean;
import com.onecric.live.model.GiftBean;
import com.onecric.live.model.HistoryMsgBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.NobelBean;
import com.onecric.live.model.NobelMsgBean;
import com.onecric.live.model.RedEnvelopeBean;
import com.onecric.live.presenter.live.LiveChatPresenter;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.StringUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.LiveChatView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMMessageListGetOption;
import com.tencent.imsdk.v2.V2TIMSendCallback;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.qcloud.tuicore.component.interfaces.IUIKitCallback;
import com.tencent.qcloud.tuikit.tuichat.TUIChatConstants;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;
import com.tencent.qcloud.tuikit.tuichat.util.ChatMessageInfoUtil;
import com.tencent.qcloud.tuikit.tuichat.util.TUIChatLog;
import com.tencent.qcloud.tuikit.tuichat.util.TUIChatUtils;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LiveChatFragment extends MvpFragment<LiveChatPresenter> implements LiveChatView, View.OnClickListener {
    public static LiveChatFragment newInstance(String groupId, int anchorId) {
        LiveChatFragment fragment = new LiveChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString("groupId", groupId);
        bundle.putInt("anchorId", anchorId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mGroupId;
    private int mAnchorId;
    private TextView tv_notice;
    private TextView tv_input;
    private RecyclerView rv_chat;
    private LiveChatAdapter mChatAdapter;
    private LinearLayoutManager mChatLayoutManager;
    private boolean mIsNeedScrollBottom = true;

    private InputChatMsgDialogFragment inputChatMsgDialog;
    private Dialog mGiftDialog;
    private List<GiftBean> mGiftList;
    private List<GiftBean> mBackpackList;
    private List<GiftBean> mDanmuList;
    private NobelBean mNobelBean;

    private ImageView iv_block;
    private EasyPopup popup;
    private ImageView iv_envelope;
    private TextView tv_envelope;
    private ImageView iv_noble;
    private TextView tv_noble;
    private ImageView iv_gift;
    private TextView tv_gift;
    private ImageView iv_enter;
    private TextView tv_enter;

    private TreasureChestDialog mTreasureChestDialog;
    //定时器修改时间
    private Handler mHandler;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String mDate = "";
    public int mPosition = -1;
    private long timeValue = 5 * 60 * 1000;
    public long mTime = timeValue;

    private Dialog mLoadingDialog;

    private MessageInfo firstMessageInfo;
    private SmartRefreshLayout smart_rl;
    protected List<MessageInfo> loadedMessageInfoList = new ArrayList<>();

    private android.widget.ProgressBar progressBar;

    public LiveDetailMainFragment mainFragment;

    public LottieAnimationView lottieView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_chat;
    }

    @Override
    protected LiveChatPresenter createPresenter() {
        return new LiveChatPresenter(this);
    }

    @Override
    protected void initUI() {
        mLoadingDialog = DialogUtil.loadingDialog(getContext());
        mTreasureChestDialog = new TreasureChestDialog(getActivity(), R.style.dialog2, this);
        mGiftList = new ArrayList<>();
        mBackpackList = new ArrayList<>();

        mGroupId = getArguments().getString("groupId");
//        mGroupId = 1008+"";
        mAnchorId = getArguments().getInt("anchorId");
        tv_notice = findViewById(R.id.tv_notice);
        tv_input = findViewById(R.id.tv_input);
        rv_chat = findViewById(R.id.rv_chat);
        iv_block = findViewById(R.id.iv_block);
        smart_rl = findViewById(R.id.smart_rl);
        progressBar = findViewById(R.id.ProgressBar);
        lottieView = findViewById(R.id.lottieView);

        findViewById(R.id.tv_input).setOnClickListener(this);
        findViewById(R.id.iv_emoji).setOnClickListener(this);
        findViewById(R.id.iv_gift).setOnClickListener(this);
//        findViewById(R.id.iv_noble).setOnClickListener(this);
        findViewById(R.id.iv_block).setOnClickListener(this);
        findViewById(R.id.iv_box).setOnClickListener(this);
        findViewById(R.id.iv_lottery).setOnClickListener(this);
        findViewById(R.id.iv_box_close).setOnClickListener(this);
//        findViewById(R.id.iv_red_envelope).setOnClickListener(this);

        findViewById(R.id.iv_likeing).setOnClickListener(this);
        findViewById(R.id.iv_hearting).setOnClickListener(this);
    }

    @Override
    protected void initData() {
/*        if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            findViewById(R.id.fl_board).setVisibility(View.VISIBLE);
            findViewById(R.id.fl_board).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(loginDialog!=null){
                        loginDialog.isCanClose = true;
                        loginDialog.show();
                    }
                }
            });
            SpannableStringBuilder spannable = new SpannableStringBuilder(getString(R.string.tip_send_bullet_screen));
            spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.c_E3AC72)), 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tv_input.setText(spannable);
        }*/
        //游客可发言
        tv_input.setText(R.string.live_talk_some_hint);
        findViewById(R.id.fl_board).setVisibility(View.GONE);

        tv_notice.setSelected(true);
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getAnnouncement())) {
            tv_notice.setText(CommonAppConfig.getInstance().getConfig().getAnnouncement());
        }

        List<MessageInfo> list = new ArrayList<>();
        if (CommonAppConfig.getInstance().getConfig() != null && !TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getLive_notice())) {
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setSystemNotice(CommonAppConfig.getInstance().getConfig().getLive_notice());
            list.add(messageInfo);
        }

        rv_chat.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0) {
                    mIsNeedScrollBottom = false;
                }else {
                    if (mChatLayoutManager.findLastVisibleItemPosition() == (mChatAdapter.getData().size() - 1)) {
                        mIsNeedScrollBottom = true;
                    }
                }
            }
        });

        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableLoadMore(false);
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadHistoryMsg(false);
            }
        });

        loadedMessageInfoList.addAll(list);
        mChatAdapter = new LiveChatAdapter(R.layout.item_live_chat, loadedMessageInfoList,this);
        mChatLayoutManager = new LinearLayoutManager(getContext());
        rv_chat.setLayoutManager(mChatLayoutManager);
        rv_chat.setAdapter(mChatAdapter);

        //获取贵族信息
//        mvpPresenter.getNobelData();
        smart_rl.autoRefresh();
        //初始化屏蔽特效弹窗
        initPopup();
        popup.findViewById(R.id.ll_envelope).setOnClickListener(this);
        popup.findViewById(R.id.ll_noble).setOnClickListener(this);
        popup.findViewById(R.id.ll_gift).setOnClickListener(this);
        popup.findViewById(R.id.ll_enter).setOnClickListener(this);

//        mvpPresenter.getHistoryMessage(Integer.parseInt(mGroupId));

        //获取礼物列表
        mvpPresenter.getGiftList();
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            //获取背包礼物列表
            mvpPresenter.getBackpackGiftList();
            //获取炫彩气泡列表
            mvpPresenter.getBackgroundDanmuList();
        }
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            mDate = sdf.format(new Date());
            String str = SpUtil.getInstance().getStringValue(SpUtil.BOX_TIME);
            if (!TextUtils.isEmpty(str)) {
                mTime = Long.valueOf(str);
            }
            mHandler = new Handler(Looper.myLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    //宝箱逻辑
                    if (msg.what == 0) {
                        //如果判断到日期不一致，马上将position和time重置回默认状态，调接口刷新宝箱列表
                        String format = sdf.format(new Date());
                        if (mDate.equals(format)) {
                            //判断调完获取宝箱列表接口以后才开始倒计时
                            if (mPosition >= 0) {
                                if (mTime > 0) {
                                    mTime = mTime - 1000;
                                    if (mTime <= 0) {
                                        mTime = timeValue;
                                        SpUtil.getInstance().setStringValue(SpUtil.BOX_TIME, "");
                                        //调接口改宝箱状态为未开启,然后调接口刷新列表
                                        mvpPresenter.doBoxTimeOver(mTreasureChestDialog.getAdapter().getItem(mPosition).getId());
                                    } else {
                                        mTreasureChestDialog.getAdapter().notifyItemChanged(mPosition, Constant.PAYLOAD);
                                    }
                                }
                            }
                        } else {
                            mDate = format;
                            mPosition = -1;
                            mTime = timeValue;
                            mvpPresenter.getBoxList();
                        }
                        mHandler.sendEmptyMessageDelayed(0, 1000);
                    }
                    //红包逻辑
                    if (msg.what == 100) {
                        Integer removePosition = null;
                        for (int i = 0; i < mAdapter.getData().size(); i++) {
                            RedEnvelopeBean bean = mAdapter.getData().get(i);
                            int countdown = bean.getCountdown_time();
                            if (countdown > 0) {
                                countdown--;
                                bean.setCountdown_time(countdown);
                                mAdapter.notifyItemChanged(i, Constant.PAYLOAD);
                            } else {
                                int receiveTime = bean.getReceive_time();
                                if (receiveTime > 0) {
                                    receiveTime--;
                                    bean.setReceive_time(receiveTime);
                                } else {
                                    removePosition = i;
                                }
                            }
                        }
                        if (removePosition != null) {
                            mAdapter.remove(removePosition);
                            if (mAdapter.getData().size() <= 0) {
                                ll_no_red_envelope.setVisibility(View.VISIBLE);
                                ll_has_red_envelope.setVisibility(View.GONE);
                                ll_countdown.setVisibility(View.VISIBLE);
                                iv_grab.setVisibility(View.GONE);
                                if (getContext() instanceof LiveDetailActivity) {
                                    if (((LiveDetailActivity) getContext()).playerView != null) {
                                        ((LiveDetailActivity) getContext()).playerView.setCountdownVisible(View.GONE);
                                    }
                                }
                            }
                        }
                        if (mAdapter.getData().size() > 0) {
                            if (mAdapter.getData().get(0).getCountdown_time() > 0) {
                                if (getContext() instanceof LiveDetailActivity) {
                                    if (((LiveDetailActivity) getContext()).playerView != null) {
                                        ((LiveDetailActivity) getContext()).playerView.setCountdownText(StringUtil.getDurationText(mAdapter.getData().get(0).getCountdown_time() * 1000) + getContext().getString(R.string.second));
                                    }
                                }
                                tv_countdown.setText(StringUtil.getDurationText(mAdapter.getData().get(0).getCountdown_time() * 1000) + getContext().getString(R.string.second));
                            } else {
                                if (getContext() instanceof LiveDetailActivity) {
                                    if (((LiveDetailActivity) getContext()).playerView != null) {
                                        ((LiveDetailActivity) getContext()).playerView.setCountdownText(getContext().getString(R.string.text_open_red_envelope));
                                    }
                                }
                                ll_countdown.setVisibility(View.GONE);
                                iv_grab.setVisibility(View.VISIBLE);
                            }
                        } else {
                            ll_no_red_envelope.setVisibility(View.VISIBLE);
                            ll_has_red_envelope.setVisibility(View.GONE);
                            if (getContext() instanceof LiveDetailActivity) {
                                if (((LiveDetailActivity) getContext()).playerView != null) {
                                    ((LiveDetailActivity) getContext()).playerView.setCountdownVisible(View.GONE);
                                }
                            }
                        }
                        mHandler.sendEmptyMessageDelayed(100, 1000);
                    }
                }
            };
            mvpPresenter.getBoxList();
            mTreasureChestDialog.getAdapter().setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.tv_text) {
                        mvpPresenter.openBox(mTreasureChestDialog.getAdapter().getItem(position).getId());
                    }
                }
            });
            //宝箱定时器
            mHandler.sendEmptyMessageDelayed(0, 1000);
            //初始化红包弹窗
            initRedEnvelopeDialog();
            //获取红包列表
            mvpPresenter.getRedEnvelopeList(mAnchorId);
            //红包定时器
            mHandler.sendEmptyMessageDelayed(100, 0);
        }
    }

    //登录成功更新状态
    public void updateLoginData() {
/*        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            tv_input.setText(R.string.live_talk_some_hint);
            findViewById(R.id.fl_board).setVisibility(View.GONE);
        }*/
    }

    //更新数据
    public void updateData() {
        mvpPresenter.getBackgroundDanmuList();
    }

    private void initPopup() {
        popup = EasyPopup.create().setContentView(getContext(), R.layout.view_live_block_function, DpUtil.dp2px(187), DpUtil.dp2px(38))
                .setFocusAndOutsideEnable(true).apply();
        iv_envelope = popup.findViewById(R.id.iv_envelope);
        tv_envelope = popup.findViewById(R.id.tv_envelope);
        iv_noble = popup.findViewById(R.id.iv_noble);
        tv_noble = popup.findViewById(R.id.tv_noble);
        iv_gift = popup.findViewById(R.id.iv_gift);
        tv_gift = popup.findViewById(R.id.tv_gift);
        iv_enter = popup.findViewById(R.id.iv_enter);
        tv_enter = popup.findViewById(R.id.tv_enter);

        BlockFunctionBean blockFunctionInfo = CommonAppConfig.getInstance().getBlockFunctionInfo();
        iv_envelope.setSelected(blockFunctionInfo.isBlockEnvelope());
        iv_noble.setSelected(blockFunctionInfo.isBlockNoble());
        iv_gift.setSelected(blockFunctionInfo.isBlockGift());
        iv_enter.setSelected(blockFunctionInfo.isBlockEnter());
        if (blockFunctionInfo.isBlockEnvelope()) {
            tv_envelope.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
        } else {
            tv_envelope.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
        }
        if (blockFunctionInfo.isBlockNoble()) {
            tv_noble.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
        } else {
            tv_noble.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
        }
        if (blockFunctionInfo.isBlockGift()) {
            tv_gift.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
        } else {
            tv_gift.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
        }
        if (blockFunctionInfo.isBlockEnter()) {
            tv_enter.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
        } else {
            tv_enter.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
        }
    }

    @Override
    public void onClick(View v) {
        BlockFunctionBean blockFunctionInfo = CommonAppConfig.getInstance().getBlockFunctionInfo();
        switch (v.getId()) {
            case R.id.tv_input:
                if (mNobelBean != null) {
                    showInputTextMsgDialog(InputChatMsgDialogFragment.STATE_SOFT_INPUT);
                }
                break;
            case R.id.iv_emoji:
                if (mNobelBean != null) {
                    showInputTextMsgDialog(InputChatMsgDialogFragment.STATE_FACE_INPUT);
                }
                break;
            case R.id.iv_gift:
                showGiftDialog();
                break;
            case R.id.iv_noble:
                if (mNobelBean != null) {
//                    if (((LiveDetailActivity) getActivity()).mLiveRoomBean != null) {
                    if (mainFragment!=null && mainFragment.mUserBean != null) {
                        OpenNobleActivity.forward(getActivity(), mainFragment.mUserBean, mNobelBean);
                    }
                }
                break;
            case R.id.iv_block:
                popup.showAtAnchorView(iv_block, YGravity.ALIGN_TOP, XGravity.CENTER, 0, 0);
                break;
            case R.id.ll_envelope:
                blockFunctionInfo.setBlockEnvelope(!blockFunctionInfo.isBlockEnvelope());
                CommonAppConfig.getInstance().saveBlockFunctionInfo(blockFunctionInfo);
                iv_envelope.setSelected(blockFunctionInfo.isBlockEnvelope());
                if (blockFunctionInfo.isBlockEnvelope()) {
                    tv_envelope.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
                } else {
                    tv_envelope.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
                }
                break;
            case R.id.ll_noble:
                blockFunctionInfo.setBlockNoble(!blockFunctionInfo.isBlockNoble());
                CommonAppConfig.getInstance().saveBlockFunctionInfo(blockFunctionInfo);
                iv_noble.setSelected(blockFunctionInfo.isBlockNoble());
                if (blockFunctionInfo.isBlockNoble()) {
                    tv_noble.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
                } else {
                    tv_noble.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
                }
                break;
            case R.id.ll_gift:
                blockFunctionInfo.setBlockGift(!blockFunctionInfo.isBlockGift());
                CommonAppConfig.getInstance().saveBlockFunctionInfo(blockFunctionInfo);
                iv_gift.setSelected(blockFunctionInfo.isBlockGift());
                if (blockFunctionInfo.isBlockGift()) {
                    tv_gift.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
                } else {
                    tv_gift.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
                }
                break;
            case R.id.ll_enter:
                blockFunctionInfo.setBlockEnter(!blockFunctionInfo.isBlockEnter());
                CommonAppConfig.getInstance().saveBlockFunctionInfo(blockFunctionInfo);
                iv_enter.setSelected(blockFunctionInfo.isBlockEnter());
                if (blockFunctionInfo.isBlockEnter()) {
                    tv_enter.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
                } else {
                    tv_enter.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
                }
                break;
            case R.id.iv_box:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getContext().getString(R.string.please_login));
                    return;
                }
                if (mTreasureChestDialog != null) {
                    mTreasureChestDialog.show();
                }
                break;
            case R.id.iv_lottery:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getH5_url())) {
                    ToastUtil.show(getContext().getString(R.string.please_login));
                    return;
                }
                WebViewActivity.forward(getContext(), CommonAppConfig.getInstance().getConfig().getH5_url() + HttpConstant.LOTTERY_CAROUSEL_URL + CommonAppConfig.getInstance().getToken());
                break;
            case R.id.iv_box_close:
                findViewById(R.id.rl_box).setVisibility(View.GONE);
                break;
            case R.id.iv_red_envelope:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getContext().getString(R.string.please_login));
                    return;
                }
                if (mRedEnvelopeDialog != null) {
                    mRedEnvelopeDialog.show();
                }
                break;
            case R.id.iv_likeing:
                lottieView.setAnimation("like-emoji.json");
                lottieView.playAnimation();
                break;
            case R.id.iv_hearting:
                lottieView.setAnimation("heart-emoji.json");
                lottieView.playAnimation();
                break;
        }
    }

    //发送进入房间消息
    public void sendEnterMessage() {
        CustomMsgBean customMsgBean = new CustomMsgBean();
        customMsgBean.setType(MessageInfo.MSG_TYPE_NOBEL_ENTER);
        NobelMsgBean nobelMsgBean = new NobelMsgBean();
        nobelMsgBean.setLevel(0);
        if (CommonAppConfig.getInstance().getUserBean() != null && CommonAppConfig.getInstance().getUserBean().getGuard() != null) {
            nobelMsgBean.setGuard_name(CommonAppConfig.getInstance().getUserBean().getGuard().getSwf_name());
            nobelMsgBean.setGuard_swf(CommonAppConfig.getInstance().getUserBean().getGuard().getSwf());
        }
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
            if (mAnchorId == Integer.valueOf(CommonAppConfig.getInstance().getUid())) {
                nobelMsgBean.setIs_room(1);
            } else {
                nobelMsgBean.setIs_room(0);
            }
        }

        MessageInfo messageInfo;
        if (CommonAppConfig.getInstance().getUserBean() != null) {
            nobelMsgBean.setIs_guard(CommonAppConfig.getInstance().getUserBean().getIs_guard());
            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getExp_icon())) {
                nobelMsgBean.setExp_icon(CommonAppConfig.getInstance().getUserBean().getExp_icon());
            }
/*            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getGuard().getIcon())) {
                nobelMsgBean.setGuard_icon(CommonAppConfig.getInstance().getUserBean().getGuard().getIcon());
            }*/
            customMsgBean.setNobel(nobelMsgBean);
            messageInfo = ChatMessageInfoUtil.buildCustomMessage(JSONObject.toJSONString(customMsgBean), "", null);
            messageInfo.setNickName(CommonAppConfig.getInstance().getUserBean().getUser_nickname());
        }else{
            messageInfo = ChatMessageInfoUtil.buildCustomMessage(JSONObject.toJSONString(customMsgBean), "", null);
            messageInfo.setNickName(CommonAppConfig.getInstance().getVisitorUserId());
        }

        V2TIMManager.getMessageManager().sendMessage(messageInfo.getTimMessage(), null, mGroupId, V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null,
                new V2TIMSendCallback<V2TIMMessage>() {
                    @Override
                    public void onProgress(int i) {

                    }

                    @Override
                    public void onSuccess(V2TIMMessage v2TIMMessage) {
                        if (CommonAppConfig.getInstance().getBlockFunctionInfo() != null) {
                            //fixme 屏蔽入场信息为true？
//                            if (!CommonAppConfig.getInstance().getBlockFunctionInfo().isBlockEnter()) {
//                                updateAdapter(nobelMsgBean.getGuard_icon(), nobelMsgBean.getExp_icon(), messageInfo);
                                updateAdapter(messageInfo);
//                            }
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
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
        if(mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void getNobelDataSuccess(NobelBean nobelBean) {
        if (nobelBean != null) {
            mNobelBean = nobelBean;
            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) || !TextUtils.isEmpty(CommonAppConfig.getInstance().getVisitorUserSign())) {
                V2TIMManager.getInstance().joinGroup(mGroupId, "", new V2TIMCallback() {
                    @Override
                    public void onSuccess() {
                        mvpPresenter.initListener();
//                        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {}
//                        ((LiveDetailActivity) getActivity()).setPeopleCount();
//                        loadHistoryMsg(true);
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.e("Chat","code:"+i+" info:"+s);
                        mvpPresenter.initListener();
//                        loadHistoryMsg(true);
                    }
                });
            }
        }
    }

    @Override
    public void getGiftListSuccess(List<GiftBean> list) {
        if (list != null && list.size() > 0) {
            mGiftList = list;
        }
    }

    @Override
    public void getBackpackGiftListSuccess(List<GiftBean> list) {
        if (list != null && list.size() > 0) {
            mBackpackList = list;
        }
    }

    @Override
    public void getBackgroundDanmuListSuccess(List<GiftBean> list) {
        if (list != null && list.size() > 0) {
            mDanmuList = list;
        }
    }

    @Override
    public void getBoxListSuccess(List<BoxBean> list) {
        if (list != null && list.size() > 0) {
            if (mTreasureChestDialog != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getStatus() == 2) {
                        mPosition = i;
                        break;
                    }
                }
                mTreasureChestDialog.setNewData(list);
            }
        }
    }

    @Override
    public void doBoxTimeOverSuccess() {
        mvpPresenter.getBoxList();
    }

    @Override
    public void openBoxSuccess(BoxBean boxBean) {
        if (boxBean != null) {
            List<BoxBean> list = mTreasureChestDialog.getAdapter().getData();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == boxBean.getId()) {
                    if (!TextUtils.isEmpty(boxBean.getGift_name())) {
                        list.get(i).setGift_name(boxBean.getGift_name());
                    }
                    if (!TextUtils.isEmpty(boxBean.getGift_img())) {
                        list.get(i).setGift_img(boxBean.getGift_img());
                    }
                    list.get(i).setNum(boxBean.getNum());
                    list.get(i).setStatus(1);
                    mTreasureChestDialog.getAdapter().notifyItemChanged(i, Constant.PAYLOAD);
                    break;
                }
            }
        }
    }

    @Override
    public void sendGiftSuccess(GiftBean giftBean, String msg) {
//        if (giftBean != null) {
//            ((LiveDetailActivity) getActivity()).startGif(giftBean, CommonAppConfig.getInstance().getUserBean().getUser_nickname(), CommonAppConfig.getInstance().getUserBean().getAvatar());
//        }
        if (getContext() instanceof LiveDetailActivity) {
            ((LiveDetailActivity) getActivity()).sendGiftMessage(giftBean);
        }
    }

    private void showInputTextMsgDialog(int state) {
        inputChatMsgDialog = new InputChatMsgDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        if (mNobelBean != null) {
            bundle.putSerializable("bean", mNobelBean);
        }
//        if (((LiveDetailActivity) getActivity()).mLiveRoomBean != null) {
        if (mainFragment!=null && mainFragment.mUserBean != null) {
            bundle.putSerializable("user", mainFragment.mUserBean);
        }
        bundle.putSerializable("danmu", (Serializable) mDanmuList);
        inputChatMsgDialog.setArguments(bundle);
        inputChatMsgDialog.show(getChildFragmentManager(), "InputChatMsgDialogFragment");
    }

    private void dismissInputDialog() {
        if (inputChatMsgDialog != null) {
            inputChatMsgDialog.dismiss();
        }
    }

    private void showGiftDialog() {
        mGiftDialog = new Dialog(getContext(), R.style.dialog2);
        mGiftDialog.setContentView(R.layout.dialog_gift_layout);
        setGiftContentView(mGiftDialog);
        mGiftDialog.setCancelable(true);
        mGiftDialog.setCanceledOnTouchOutside(true);
        mGiftDialog.getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = mGiftDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        mGiftDialog.getWindow().setAttributes(params);
        mGiftDialog.show();
    }

    private void setGiftContentView(Dialog dialog) {
        ImageView iv_indicator_gift = dialog.findViewById(R.id.iv_indicator_gift);
        ImageView iv_indicator_backpack = dialog.findViewById(R.id.iv_indicator_backpack);
        RecyclerView rv_gift = dialog.findViewById(R.id.rv_gift);
        RecyclerView rv_backpack = dialog.findViewById(R.id.rv_backpack);
        LinearLayout ll_indicator = dialog.findViewById(R.id.ll_indicator);
        LinearLayout ll_backpack_indicator = dialog.findViewById(R.id.ll_backpack_indicator);

        //礼物
        List<List<GiftBean>> giftListBeanList = new ArrayList<>();
        if (mGiftList != null && mGiftList.size() > 0) {
            int count = mGiftList.size() / 8;
            int left = mGiftList.size() % 8;
            if (count > 0) {//如果大于等于8个
                for (int i = 1; i <= count; i++) {
                    int start = (i - 1) * 8;
                    int end = i * 8;
                    List<GiftBean> subList = mGiftList.subList(start, end);
                    giftListBeanList.add(i - 1, subList);
                }
                if (left != 0) {//如果还剩余的话,那剩余的加进入
                    List<GiftBean> leftBeans = mGiftList.subList(count * 8, mGiftList.size());
                    giftListBeanList.add(count, leftBeans);
                }
            } else {
                giftListBeanList.add(0, mGiftList);
            }
        }
        GiftViewPagerRecyclerAdapter giftAdapter = new GiftViewPagerRecyclerAdapter(R.layout.item_gift_view_pager_recycler_layout, giftListBeanList);
        ViewPagerLayoutManager mLayoutManager = new ViewPagerLayoutManager(getContext(), OrientationHelper.HORIZONTAL);
        rv_gift.setLayoutManager(mLayoutManager);
        rv_gift.setAdapter(giftAdapter);
        //指示器
        List<ImageView> imageViews = new ArrayList<>();
        for (int i = 0; i < giftListBeanList.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            int width = DpUtil.dp2px(6);
            int height = DpUtil.dp2px(6);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.leftMargin = DpUtil.dp2px(13);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setImageResource(R.drawable.shape_gift_indicator_yellow_back);
            } else {
                imageView.setImageResource(R.drawable.shape_gift_indicator_grey_back);
            }
            imageViews.add(imageView);
            ll_indicator.addView(imageView);
        }
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {

            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                if (imageViews.size() > 0) {
                    for (int i = 0; i < imageViews.size(); i++) {
                        if (i == position) {
                            imageViews.get(i).setImageResource(R.drawable.shape_gift_indicator_yellow_back);
                        } else {
                            imageViews.get(i).setImageResource(R.drawable.shape_gift_indicator_grey_back);
                        }
                    }
                }
            }
        });
        //背包礼物
        List<List<GiftBean>> backpackListBeanList = new ArrayList<>();
        if (mBackpackList != null && mBackpackList.size() > 0) {
            int count = mBackpackList.size() / 8;
            int left = mBackpackList.size() % 8;
            if (count > 0) {//如果大于等于8个
                for (int i = 1; i <= count; i++) {
                    int start = (i - 1) * 8;
                    int end = i * 8;
                    List<GiftBean> subList = mBackpackList.subList(start, end);
                    backpackListBeanList.add(i - 1, subList);
                }
                if (left != 0) {//如果还剩余的话,那剩余的加进入
                    List<GiftBean> leftBeans = mBackpackList.subList(count * 8, mBackpackList.size());
                    backpackListBeanList.add(count, leftBeans);
                }
            } else {
                backpackListBeanList.add(0, mBackpackList);
            }
        }
        GiftViewPagerRecyclerAdapter backpackAdapter = new GiftViewPagerRecyclerAdapter(R.layout.item_gift_view_pager_recycler_layout, backpackListBeanList);
        ViewPagerLayoutManager mLayoutManagerTwo = new ViewPagerLayoutManager(getContext(), OrientationHelper.HORIZONTAL);
        rv_backpack.setLayoutManager(mLayoutManagerTwo);
        rv_backpack.setAdapter(backpackAdapter);
        //指示器
        List<ImageView> imageViewsTwo = new ArrayList<>();
        for (int i = 0; i < backpackListBeanList.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            int width = DpUtil.dp2px(6);
            int height = DpUtil.dp2px(6);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.leftMargin = DpUtil.dp2px(13);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setImageResource(R.drawable.shape_gift_indicator_yellow_back);
            } else {
                imageView.setImageResource(R.drawable.shape_gift_indicator_grey_back);
            }
            imageViewsTwo.add(imageView);
            ll_backpack_indicator.addView(imageView);
        }
        mLayoutManagerTwo.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {

            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                if (imageViewsTwo.size() > 0) {
                    for (int i = 0; i < imageViewsTwo.size(); i++) {
                        if (i == position) {
                            imageViewsTwo.get(i).setImageResource(R.drawable.shape_gift_indicator_yellow_back);
                        } else {
                            imageViewsTwo.get(i).setImageResource(R.drawable.shape_gift_indicator_grey_back);
                        }
                    }
                }
            }
        });

        dialog.findViewById(R.id.cl_gift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_indicator_gift.setVisibility(View.VISIBLE);
                iv_indicator_backpack.setVisibility(View.GONE);
                rv_gift.setVisibility(View.VISIBLE);
                rv_backpack.setVisibility(View.GONE);
                ll_indicator.setVisibility(View.VISIBLE);
                ll_backpack_indicator.setVisibility(View.GONE);
            }
        });
        dialog.findViewById(R.id.cl_backpack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_indicator_gift.setVisibility(View.GONE);
                iv_indicator_backpack.setVisibility(View.VISIBLE);
                rv_gift.setVisibility(View.GONE);
                rv_backpack.setVisibility(View.VISIBLE);
                ll_indicator.setVisibility(View.GONE);
                ll_backpack_indicator.setVisibility(View.VISIBLE);
            }
        });
        dialog.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iv_indicator_gift.getVisibility() == View.VISIBLE) {
                    if (giftAdapter.getSelectBean() == null) {
                        ToastUtil.show(getString(R.string.please_choose_a_gift));
                        return;
                    }
                    mvpPresenter.sendGift(giftAdapter.getSelectBean(), mAnchorId, 0);
                } else {
                    if (backpackAdapter.getSelectBean() == null) {
                        ToastUtil.show(getString(R.string.please_choose_a_gift));
                        return;
                    }
                    mvpPresenter.sendGift(backpackAdapter.getSelectBean(), mAnchorId, 1);
                }
            }
        });
    }

    /***********************红包相关***********************/
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_SEND = 1;
    public static final int TYPE_PAY = 2;
    public static final int TYPE_LIST = 3;
    public static final int TYPE_RESULT = 4;

    private int mType;
    private Dialog mRedEnvelopeDialog;
    //最开始的红包界面
    private RelativeLayout rl_normal;
    private LinearLayout ll_no_red_envelope;
    private LinearLayout ll_has_red_envelope;
    private LinearLayout ll_countdown;
    private ImageView iv_grab;
    private TextView tv_countdown;
    //发红包界面
    private RelativeLayout rl_send;
    private LinearLayout ll_three;
    private TextView tv_type;
    private TextView tv_red_envelope_type;
    private EditText et_amount;
    private EditText et_number;
    private int isLucky;
    private int redEnvelopeType;
    //付款和余额不足界面
    private RelativeLayout rl_pay;
    private TextView tv_pay_amount;
    private TextView tv_balance;
    private TextView tv_confirm_pay;
    //红包列表界面
    private RelativeLayout rl_list;
    private RecyclerView rv_red_envelope;
    private RedEnvelopeAdapter mAdapter;
    //打开红包界面
    private RelativeLayout rl_result;
    private TextView tv_result;
    private TextView tv_result_count;

    private EasyPopup redEnvelopePopup;
    private ImageView iv_live;
    private TextView tv_live;
    private ImageView iv_fans;
    private TextView tv_fans;

    private void initRedEnvelopeDialog() {
        mRedEnvelopeDialog = new Dialog(getContext(), R.style.dialog2);
        mRedEnvelopeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mType != TYPE_NORMAL) {
                    toggleType(TYPE_NORMAL);
                }
            }
        });
        mRedEnvelopeDialog.setContentView(R.layout.dialog_red_envelope);
        mRedEnvelopeDialog.setCancelable(true);
        mRedEnvelopeDialog.setCanceledOnTouchOutside(true);
        initDialogView(mRedEnvelopeDialog);
    }

    public void showRedEnvelopeDialog() {
        if (mRedEnvelopeDialog != null) {
            mRedEnvelopeDialog.show();
        }
    }

    public void showOfficeNotice(String msg) {
        if(!TextUtils.isEmpty(msg)){
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setOfficeNotice(msg);
            mChatAdapter.addData(messageInfo);
        }
    }

    private void initDialogView(Dialog dialog) {
        rl_normal = dialog.findViewById(R.id.rl_normal);
        ll_no_red_envelope = dialog.findViewById(R.id.ll_no_red_envelope);
        ll_has_red_envelope = dialog.findViewById(R.id.ll_has_red_envelope);
        ll_countdown = dialog.findViewById(R.id.ll_countdown);
        iv_grab = dialog.findViewById(R.id.iv_grab);
        tv_countdown = dialog.findViewById(R.id.tv_countdown);
        rl_send = dialog.findViewById(R.id.rl_send);
        ll_three = dialog.findViewById(R.id.ll_three);
        tv_type = dialog.findViewById(R.id.tv_type);
        tv_red_envelope_type = dialog.findViewById(R.id.tv_red_envelope_type);
        et_amount = dialog.findViewById(R.id.et_amount);
        et_number = dialog.findViewById(R.id.et_number);
        rl_pay = dialog.findViewById(R.id.rl_pay);
        tv_pay_amount = dialog.findViewById(R.id.tv_pay_amount);
        tv_balance = dialog.findViewById(R.id.tv_balance);
        tv_confirm_pay = dialog.findViewById(R.id.tv_confirm_pay);
        rl_list = dialog.findViewById(R.id.rl_list);
        rv_red_envelope = dialog.findViewById(R.id.rv_red_envelope);
        rl_result = dialog.findViewById(R.id.rl_result);
        tv_result = dialog.findViewById(R.id.tv_result);
        tv_result_count = dialog.findViewById(R.id.tv_result_count);

        dialog.findViewById(R.id.iv_close).setOnClickListener(onClickListener);
        dialog.findViewById(R.id.tv_send).setOnClickListener(onClickListener);
        dialog.findViewById(R.id.tv_list).setOnClickListener(onClickListener);
        dialog.findViewById(R.id.tv_list_back).setOnClickListener(onClickListener);
        dialog.findViewById(R.id.tv_result_back).setOnClickListener(onClickListener);
        dialog.findViewById(R.id.tv_pay_back).setOnClickListener(onClickListener);
        dialog.findViewById(R.id.iv_close_two).setOnClickListener(onClickListener);
        dialog.findViewById(R.id.tv_shield).setOnClickListener(onClickListener);
        dialog.findViewById(R.id.tv_confirm).setOnClickListener(onClickListener);
        iv_grab.setOnClickListener(onClickListener);
        ll_three.setOnClickListener(onClickListener);
        tv_type.setOnClickListener(onClickListener);
        tv_confirm_pay.setOnClickListener(onClickListener);

        initPopupView();

        mAdapter = new RedEnvelopeAdapter(R.layout.item_red_envelope, new ArrayList<>());
        rv_red_envelope.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_red_envelope.setAdapter(mAdapter);

        String text = String.format(getString(R.string.text_red_envelope_type), getString(R.string.luck_red_envelope), getString(R.string.normal_red_envelope));
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFDFAB")), text.length() - 4, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_type.setText(builder);
    }

    private void initPopupView() {
        redEnvelopePopup = EasyPopup.create().setContentView(getContext(), R.layout.view_red_envelope_limit, DpUtil.dp2px(233), DpUtil.dp2px(167)).setFocusAndOutsideEnable(true).apply();
        iv_live = redEnvelopePopup.findViewById(R.id.iv_live);
        tv_live = redEnvelopePopup.findViewById(R.id.tv_live);
        iv_fans = redEnvelopePopup.findViewById(R.id.iv_fans);
        tv_fans = redEnvelopePopup.findViewById(R.id.tv_fans);
        redEnvelopePopup.findViewById(R.id.ll_live).setOnClickListener(onClickListener);
        redEnvelopePopup.findViewById(R.id.ll_fans).setOnClickListener(onClickListener);
        iv_live.setSelected(true);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_close:
                case R.id.iv_close_two:
                    if (mRedEnvelopeDialog != null) {
                        mRedEnvelopeDialog.dismiss();
                    }
                    break;
                case R.id.tv_list:
                    toggleType(TYPE_LIST);
                    break;
                case R.id.tv_list_back:
                case R.id.tv_result_back:
                    toggleType(TYPE_NORMAL);
                    break;
                case R.id.tv_shield:
                    DialogUtil.showShieldRedEnvelopeDialog(getContext());
                    break;
                case R.id.tv_pay_back:
                case R.id.tv_send:
                    toggleType(TYPE_SEND);
                    break;
                case R.id.ll_three:
                    redEnvelopePopup.showAtAnchorView(ll_three, YGravity.BELOW, XGravity.CENTER, 0, 0);
                    break;
                case R.id.tv_type:
                    toggleLuckyRedEnvelope();
                    break;
                case R.id.iv_grab:
                    if (mAdapter.getData().size() > 0) {
                        mvpPresenter.receiveRedEnvelope(mAdapter.getData().get(0).getId());
                    }
                    break;
                case R.id.ll_live:
                    redEnvelopeType = 0;
                    tv_red_envelope_type.setText(getString(R.string.live_red_envelope));
                    iv_live.setSelected(true);
                    tv_live.setTextColor(getContext().getResources().getColor(R.color.c_E05B32));
                    iv_fans.setSelected(false);
                    tv_fans.setTextColor(getContext().getResources().getColor(R.color.c_999999));
                    break;
                case R.id.ll_fans:
                    redEnvelopeType = 1;
                    tv_red_envelope_type.setText(getString(R.string.fans_red_envelope));
                    iv_live.setSelected(false);
                    tv_live.setTextColor(getContext().getResources().getColor(R.color.c_999999));
                    iv_fans.setSelected(true);
                    tv_fans.setTextColor(getContext().getResources().getColor(R.color.c_E05B32));
                    break;
                case R.id.tv_confirm:
                    if (TextUtils.isEmpty(et_amount.getText().toString())) {
                        return;
                    }
                    if (TextUtils.isEmpty(et_number.getText().toString())) {
                        return;
                    }
                    if (!StringUtil.isInt(et_amount.getText().toString()) && Integer.parseInt(et_amount.getText().toString()) <= 0) {
                        return;
                    }
                    if (!StringUtil.isInt(et_number.getText().toString()) && Integer.parseInt(et_number.getText().toString()) <= 0) {
                        return;
                    }
                    if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getBalances())) {
                        toggleType(TYPE_PAY);
                        if (new BigDecimal(CommonAppConfig.getInstance().getUserBean().getBalances()).intValue() > new BigDecimal(et_amount.getText().toString()).intValue()) {
                            tv_pay_amount.setText(Integer.parseInt(et_amount.getText().toString()) + getString(R.string.diamond));
                            tv_balance.setText(getString(R.string.red_envelope_balance_prefix) + CommonAppConfig.getInstance().getUserBean().getBalances() + getString(R.string.diamond));
                            tv_balance.setTextColor(getResources().getColor(R.color.c_999999));
                            tv_confirm_pay.setText(getString(R.string.confirm_pay));
                        }else {
                            tv_pay_amount.setText(Integer.parseInt(et_amount.getText().toString()) + getString(R.string.diamond));
                            tv_balance.setText(String.format(getString(R.string.red_envelope_balance_not_enough), CommonAppConfig.getInstance().getUserBean().getBalances() + getString(R.string.diamond)));
                            tv_balance.setTextColor(getResources().getColor(R.color.c_D4122D));
                            tv_confirm_pay.setText(getString(R.string.to_recharge));
                        }
                    }
                    break;
                case R.id.tv_confirm_pay:
                    if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getBalances())) {
                        toggleType(TYPE_PAY);
                        if (new BigDecimal(CommonAppConfig.getInstance().getUserBean().getBalances()).intValue() > new BigDecimal(et_amount.getText().toString()).intValue()) {
                            mLoadingDialog.show();
                            mvpPresenter.addRedEnvelope(mAnchorId, Integer.parseInt(et_amount.getText().toString()), Integer.parseInt(et_number.getText().toString()), isLucky, redEnvelopeType);
                        }else {
                            ChargeActivity.forward(getContext());
                        }
                    }
                    break;
            }
        }
    };

    private void toggleType(int type) {
        mType = type;
        if (type == TYPE_NORMAL) {
            rl_normal.setVisibility(View.VISIBLE);
        } else {
            rl_normal.setVisibility(View.GONE);
        }
        if (type == TYPE_SEND) {
            rl_send.setVisibility(View.VISIBLE);
        } else {
            rl_send.setVisibility(View.GONE);
        }
        if (type == TYPE_PAY) {
            rl_pay.setVisibility(View.VISIBLE);
        } else {
            rl_pay.setVisibility(View.GONE);
        }
        if (type == TYPE_LIST) {
            rl_list.setVisibility(View.VISIBLE);
        } else {
            rl_list.setVisibility(View.GONE);
        }
        if (type == TYPE_RESULT) {
            rl_result.setVisibility(View.VISIBLE);
        } else {
            rl_result.setVisibility(View.GONE);
        }
    }

    private void toggleLuckyRedEnvelope() {
        if (isLucky == 0) {
            isLucky = 1;
            String text = String.format(getString(R.string.text_red_envelope_type), getString(R.string.normal_red_envelope), getString(R.string.luck_red_envelope));
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            builder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFDFAB")), text.length() - 4, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tv_type.setText(builder);
        } else {
            isLucky = 0;
            String text = String.format(getString(R.string.text_red_envelope_type), getString(R.string.luck_red_envelope), getString(R.string.normal_red_envelope));
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            builder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFDFAB")), text.length() - 4, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tv_type.setText(builder);
        }
    }

    @Override
    public void getRedEnvelopeListSuccess(List<RedEnvelopeBean> list) {
        if (list != null && list.size() > 0) {
            ll_no_red_envelope.setVisibility(View.GONE);
            ll_has_red_envelope.setVisibility(View.VISIBLE);
            if (getContext() instanceof LiveDetailActivity) {
                ((LiveDetailActivity) getContext()).playerView.setCountdownVisible(View.VISIBLE);
            }
            mAdapter.setNewData(list);
        } else {
            ll_no_red_envelope.setVisibility(View.VISIBLE);
            ll_has_red_envelope.setVisibility(View.GONE);
            if (getContext() instanceof LiveDetailActivity) {
                ((LiveDetailActivity) getContext()).playerView.setCountdownVisible(View.GONE);
            }
        }
    }

    @Override
    public void receiveRedEnvelope(String amount) {
        tv_result.setText(String.format(getContext().getString(R.string.red_envelope_text_three), CommonAppConfig.getInstance().getUserBean().getUser_nickname()));
        tv_result_count.setText(amount);
        toggleType(TYPE_RESULT);
    }

    @Override
    public void addRedEnvelopeSuccess() {
        mLoadingDialog.dismiss();
        if (getActivity() instanceof LiveDetailActivity) {
            ((LiveDetailActivity)getActivity()).refreshUserInfo();
        }
        toggleType(TYPE_NORMAL);
        mvpPresenter.getRedEnvelopeList(mAnchorId);
    }

    @Override
    public void getHistoryMsgListSuccess(List<HistoryMsgBean.RspMsgListDTO> list) {
        smart_rl.finishRefresh();
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.GONE);
        }

        if(list != null && list.size()>0){
            List<MessageInfo> msgInfo = new ArrayList<>();
            MessageInfo bean;
            HistoryMsgBean.DataDTO DataDTO;//MsgBody -> MsgContent -> Data -> 转取 text -> text不为空
            CustomMsgBean customMsgBean = new CustomMsgBean();
            for(HistoryMsgBean.RspMsgListDTO item:list){
                if(item.getFromAccount() != null && item.getMsgBody() != null && item.getMsgBody().get(0) != null && item.getMsgBody().get(0).getMsgContent() != null && item.getMsgBody().get(0).getMsgContent().getData() != null && !TextUtils.isEmpty(item.getMsgBody().get(0).getMsgContent().getData())){
                    DataDTO = JSONObject.parseObject(item.getMsgBody().get(0).getMsgContent().getData(), HistoryMsgBean.DataDTO.class);
                    if(DataDTO.getType() == 102){//入场消息 && DataDTO.getNobel() != null
                        customMsgBean.setType(MessageInfo.MSG_TYPE_NOBEL_ENTER);
                        bean = ChatMessageInfoUtil.buildCustomMessage(JSONObject.toJSONString(customMsgBean), "", null);
                        bean.setNickName(item.getFromAccount());
                        msgInfo.add(bean);
                    }else if(DataDTO.getNormal() != null && DataDTO.getNormal().getText() != null && !TextUtils.isEmpty(DataDTO.getNormal().getText())){//普通消息
                        msgInfo.add(buildRequestMessage(DataDTO.getNormal().getText(),item.getFromAccount()));
                    }
                }
            }
            mChatAdapter.addData(msgInfo);
            mChatAdapter.notifyDataSetChanged();
            rv_chat.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);
            sendEnterMessage();
        }
        //获取贵族信息
        mvpPresenter.getNobelData();
    }

    /***********************红包相关***********************/

    @Override
    public void onDestroy() {
        if (mHandler != null) {
            mHandler.removeMessages(0);
            mHandler.removeMessages(100);
            mHandler = null;
        }
        //退出界面时保存宝箱倒计时
        SpUtil.getInstance().setStringValue(SpUtil.BOX_TIME, String.valueOf(mTime));
        //销毁聊天信息接收监听
        mvpPresenter.destroyListener();
        super.onDestroy();
    }

    /***********************聊天相关***********************/
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
    public void onRecvNewMessage(MessageInfo messageInfo) {//收到一条消息
        if (TextUtils.isEmpty(mGroupId) || TextUtils.isEmpty(messageInfo.getGroupId())) {
            return;
        }
        if (messageInfo != null) {
            //筛选当前房间的消息，其他房间的消息不显示
            if (messageInfo.getGroupId().equals(mGroupId)) {
                CustomMsgBean customMsgBean = JSONObject.parseObject(messageInfo.getExtra().toString(), CustomMsgBean.class);
                if (messageInfo.getMsgType() == MessageInfo.MSG_TYPE_GIFT) {
//                    if (customMsgBean.getGift() != null) {
//                        ((LiveDetailActivity) getActivity()).startGif(customMsgBean.getGift(), messageInfo.getNickName(), messageInfo.getFaceUrl());
//                    }
//                    updateAdapter(customMsgBean.getGift().getGuard_icon(), customMsgBean.getGift().getExp_icon(), messageInfo);
                    updateAdapter(messageInfo);
                } else if (messageInfo.getMsgType() == MessageInfo.MSG_TYPE_COLOR_DANMU) {
//                    updateAdapter(customMsgBean.getColor().getGuard_icon(), customMsgBean.getColor().getExp_icon(), messageInfo);
                    updateAdapter(messageInfo);
                    if (getActivity() instanceof LiveDetailActivity) {
                        ((LiveDetailActivity) getActivity()).addDanmu(messageInfo);
                    }
                } else if (messageInfo.getMsgType() == MessageInfo.MSG_TYPE_NOBEL_ENTER) {
                    if (customMsgBean.getNobel() != null && customMsgBean.getNobel().getIs_guard() == 1) {
                        if (getActivity() instanceof LiveDetailActivity) {
                            ((LiveDetailActivity) getActivity()).showNobleAnim(messageInfo.getNickName(), customMsgBean.getNobel().getGuard_name(), customMsgBean.getNobel().getGuard_swf());
                        }
                    }
                    if (CommonAppConfig.getInstance().getBlockFunctionInfo() != null) {
                        if (!CommonAppConfig.getInstance().getBlockFunctionInfo().isBlockEnter()) {
//                            updateAdapter(customMsgBean.getNobel().getGuard_icon(), customMsgBean.getNobel().getExp_icon(), messageInfo);
                            updateAdapter(messageInfo);
                        }
                    }
                    if (getActivity() instanceof LiveDetailActivity) {
                        ((LiveDetailActivity) getActivity()).setPeopleCount();
                    }

                } else if (messageInfo.getMsgType() == MessageInfo.MSG_TYPE_BG_DANMU) {
//                    updateAdapter(customMsgBean.getNormal().getGuard_icon(), customMsgBean.getNormal().getExp_icon(), messageInfo);
                    updateAdapter(messageInfo);
                    if (getActivity() instanceof LiveDetailActivity) {
                        ((LiveDetailActivity) getActivity()).addDanmu(messageInfo);
                    }
                } else if (messageInfo.getMsgType() == MessageInfo.MSG_TYPE_RED_ENVELOPE) {
//                    updateAdapter(customMsgBean.getNormal().getGuard_icon(), customMsgBean.getNormal().getExp_icon(), messageInfo);
                    updateAdapter(messageInfo);
                    if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                        mvpPresenter.getRedEnvelopeList(mAnchorId);
                    }
                } else if (messageInfo.getMsgType() == MessageInfo.MSG_TYPE_BROADCAST) {
                    if (getActivity() instanceof LiveDetailActivity) {
                        ((LiveDetailActivity) getActivity()).receiveBroadcast(customMsgBean.getInfo());
                    }
                }
            }
        }
    }

    @Override
    public void onGroupNameChanged(String groupId, String newName) {

    }

    public void updateAdapter(MessageInfo messageInfo) {
        if(mIsNeedScrollBottom) {
            mChatAdapter.addData(messageInfo);
            rv_chat.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);
        }else {
            if (mChatLayoutManager.findLastVisibleItemPosition() != (mChatAdapter.getData().size() - 1)) {//如果最后一项item不可见，那添加消息不会滑动到底部
                mChatAdapter.addData(messageInfo);
            } else {
                mChatAdapter.addData(messageInfo);
                rv_chat.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);
            }
        }
    }

    public void updateAdapter(String nobleIcon, String expIcon, MessageInfo messageInfo) {
        if (mChatLayoutManager != null) {
            if (!TextUtils.isEmpty(nobleIcon)) {
                if(getContext() != null) {
                    Glide.with(getContext()).asBitmap().load(nobleIcon).override(DpUtil.dp2px(18), DpUtil.dp2px(18)).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            messageInfo.setNobleIcon(resource);
                            if (!TextUtils.isEmpty(expIcon)) {
                                Glide.with(getContext()).asBitmap().load(expIcon).override(DpUtil.dp2px(25), DpUtil.dp2px(18)).into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        messageInfo.setExpIcon(resource);
                                        if(mIsNeedScrollBottom) {
                                            mChatAdapter.addData(messageInfo);
                                            rv_chat.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);
                                        }else {
                                            if (mChatLayoutManager.findLastVisibleItemPosition() != (mChatAdapter.getData().size() - 1)) {//如果最后一项item不可见，那添加消息不会滑动到底部
                                                mChatAdapter.addData(messageInfo);
                                            } else {
                                                mChatAdapter.addData(messageInfo);
                                                rv_chat.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            } else {
                if (getContext() != null) {
                    Glide.with(getContext()).asBitmap().load(expIcon).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            messageInfo.setExpIcon(resource);
                            if(mIsNeedScrollBottom) {
                                mChatAdapter.addData(messageInfo);
                                rv_chat.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);
                            }else {
                                if (mChatLayoutManager.findLastVisibleItemPosition() != (mChatAdapter.getData().size() - 1)) {//如果最后一项item不可见，那添加消息不会滑动到底部
                                    mChatAdapter.addData(messageInfo);
                                } else {
                                    mChatAdapter.addData(messageInfo);
                                    rv_chat.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * 拉取历史记录
     */
    public void loadHistoryMessageList(int loadCount, MessageInfo locateMessageInfo, int getType, IUIKitCallback<List<MessageInfo>> callBack) {
        V2TIMMessageListGetOption optionBackward = new V2TIMMessageListGetOption();
        optionBackward.setCount(loadCount);
        if (getType == TUIChatConstants.GET_MESSAGE_FORWARD) {
            optionBackward.setGetType(V2TIMMessageListGetOption.V2TIM_GET_CLOUD_OLDER_MSG);
        } else if (getType == TUIChatConstants.GET_MESSAGE_BACKWARD) {
            optionBackward.setGetType(V2TIMMessageListGetOption.V2TIM_GET_CLOUD_NEWER_MSG);
        }
        if (locateMessageInfo != null) {
            optionBackward.setLastMsg(locateMessageInfo.getTimMessage());
        }
        optionBackward.setGroupID(mGroupId);//optionBackward.setUserID(mGroupId);

        V2TIMManager.getMessageManager().getHistoryMessageList(optionBackward, new V2TIMValueCallback<List<V2TIMMessage>>() {
            @Override
            public void onError(int code, String desc) {
                TUIChatUtils.callbackOnError(callBack, "live", code, desc);
                TUIChatLog.e("live", "loadChatMessages getHistoryMessageList optionBackward failed, code = " + code + ", desc = " + desc);
            }

            @Override
            public void onSuccess(List<V2TIMMessage> v2TIMMessages) {
                List<MessageInfo> messageInfoList = ChatMessageInfoUtil.convertTIMMessages2MessageInfos(v2TIMMessages);
                TUIChatUtils.callbackOnSuccess(callBack, messageInfoList);
                TUIChatLog.e("live", "loadChatMessages getHistoryMessageList success, v2TIMMessages = " + v2TIMMessages.size() );
            }
        });
    }

    protected boolean checkExist(MessageInfo msg) {
        if (msg != null) {
            String msgId = msg.getId();
            for (int i = loadedMessageInfoList.size() - 1; i >= 0; i--) {
                if (loadedMessageInfoList.get(i).getId().equals(msgId)
                        && loadedMessageInfoList.get(i).getUniqueId() == msg.getUniqueId()
                        && TextUtils.equals(loadedMessageInfoList.get(i+1).getExtra().toString(), msg.getExtra().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void loadHistoryMsg(boolean isFirst){
        mvpPresenter.getHistoryMessage(Integer.parseInt(mGroupId));
        /*loadHistoryMessageList(20,firstMessageInfo,TUIChatConstants.GET_MESSAGE_FORWARD,new IUIKitCallback<List<MessageInfo>>(){
            @Override
            public void onSuccess(List<MessageInfo> data) {
                smart_rl.finishRefresh();
                if(data!=null && data.size()>0){
                    Collections.reverse(data);
                    firstMessageInfo = data.get(0);
                    List<MessageInfo> list = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        MessageInfo info = data.get(i);
                        if (checkExist(info)) {
                            continue;
                        }

                        try {
                            if(JSONObject.parseObject(info.getExtra().toString()).getIntValue("type") == 102){//入场消息 && DataDTO.getNobel() != null
                                info.setMsgType(MessageInfo.MSG_TYPE_NOBEL_ENTER);
                                info.setNickName(TextUtils.isEmpty(info.getNickName())?info.getFromUser():info.getNickName());
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            continue;
                        }

                        list.add(info);
                    }
                    if (mChatAdapter == null) {
                        return;
                    }
                    loadedMessageInfoList.addAll(0,list);
                    mChatAdapter.notifyDataSetChanged();
                }
                if(progressBar.getVisibility() == View.VISIBLE){
                    progressBar.setVisibility(View.GONE);
                }
                if(isFirst){
                    rv_chat.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);
                    sendEnterMessage();
                }
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                if(progressBar.getVisibility() == View.VISIBLE){
                    progressBar.setVisibility(View.GONE);
                }
                smart_rl.finishRefresh();
                Log.e("Chat","getHistoryMessageList Error: module-"+module+" errCode-"+errCode+" errMsg"+errMsg);
            }
        });*/
    }

    public void setChatAdvertList(List<BannerBean> list){
        //fixme 设置广告banner

    }

}

package com.longya.live.presenter.live;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.BoxBean;
import com.longya.live.model.GiftBean;
import com.longya.live.model.NobelBean;
import com.longya.live.model.RedEnvelopeBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.util.ToastUtil;
import com.longya.live.view.live.LiveChatView;
import com.longya.live.view.live.LiveClassifyView;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.imsdk.v2.V2TIMUserInfo;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;
import com.tencent.qcloud.tuikit.tuichat.interfaces.GroupChatEventListener;

import java.util.List;


public class LiveChatPresenter extends BasePresenter<LiveChatView> {
    private String mGroupId;
    private GroupChatEventListener groupChatEventListener;

    public LiveChatPresenter(LiveChatView view) {
        attachView(view);
    }

    public void setGroupId(String groupId) {
        mGroupId = groupId;
    }

    public void getGiftList() {
        addSubscription(apiStores.getGiftList(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<GiftBean> giftBeans = JSONObject.parseArray(data, GiftBean.class);
                        mvpView.getGiftListSuccess(giftBeans);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void getNobelData() {
        addSubscription(apiStores.getNobelList(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        NobelBean nobelBean = JSONObject.parseObject(data, NobelBean.class);
                        mvpView.getNobelDataSuccess(nobelBean);
                    }

                    @Override
                    public void onFailure(String msg) {

                    }

                    @Override
                    public void onError(String msg) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void getBackgroundDanmuList() {
        addSubscription(apiStores.getBackgroundDanmuList(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<GiftBean> list = JSONObject.parseArray(data, GiftBean.class);
                        mvpView.getBackgroundDanmuListSuccess(list);
                    }

                    @Override
                    public void onFailure(String msg) {

                    }

                    @Override
                    public void onError(String msg) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void getBackpackGiftList() {
        addSubscription(apiStores.getBackpackGiftList(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<GiftBean> giftBeans = JSONObject.parseArray(data, GiftBean.class);
                        mvpView.getBackpackGiftListSuccess(giftBeans);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void sendGift(GiftBean giftBean, int anchorId, int type) {
        addSubscription(apiStores.sendGift(CommonAppConfig.getInstance().getToken(), giftBean.getId(), anchorId, type),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.sendGiftSuccess(giftBean, msg);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void getBoxList() {
        addSubscription(apiStores.getBoxList(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getBoxListSuccess(JSONObject.parseArray(data, BoxBean.class));
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void doBoxTimeOver(int id) {
        addSubscription(apiStores.doBoxTimeOver(CommonAppConfig.getInstance().getToken(), id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.doBoxTimeOverSuccess();
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void openBox(int id) {
        addSubscription(apiStores.openBox(CommonAppConfig.getInstance().getToken(), id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        BoxBean boxBean = JSONObject.parseObject(data, BoxBean.class);
                        boxBean.setId(id);
                        mvpView.openBoxSuccess(boxBean);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void getRedEnvelopeList(int anchorId) {
        addSubscription(apiStores.getRedEnvelopeList(CommonAppConfig.getInstance().getToken(), anchorId),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<RedEnvelopeBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), RedEnvelopeBean.class);
                        mvpView.getRedEnvelopeListSuccess(list);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void receiveRedEnvelope(int id) {
        addSubscription(apiStores.receiveRedEnvelope(CommonAppConfig.getInstance().getToken(), id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.receiveRedEnvelope(msg);
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void addRedEnvelope(int id, int amount, int number, int is_luck, int type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("anchor_id", id);
        jsonObject.put("amount", amount);
        jsonObject.put("number", number);
        jsonObject.put("is_luck", is_luck);
        jsonObject.put("type", type);
        addSubscription(apiStores.addRedEnvelope(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.addRedEnvelopeSuccess();
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void initListener() {
        groupChatEventListener = new GroupChatEventListener() {

            @Override
            public void onGroupForceExit(String groupId) {
                mvpView.onGroupForceExit(groupId);
            }

            @Override
            public void exitGroupChat(String chatId) {
                mvpView.exitGroupChat(chatId);
            }

            @Override
            public void onApplied(int unHandledSize) {
                mvpView.onApplied(unHandledSize);
            }

            @Override
            public void handleRevoke(String msgId) {
                mvpView.handleRevoke(msgId);
            }

            @Override
            public void onRecvNewMessage(MessageInfo messageInfo) {
                if (mvpView != null) {
                    mvpView.onRecvNewMessage(messageInfo);
                }
            }

            @Override
            public void onGroupNameChanged(String groupId, String newName) {
                if (mvpView != null) {
                    mvpView.onGroupNameChanged(groupId, newName);
                }
            }
        };
        TUIChatService.getInstance().setGroupChatEventListener(groupChatEventListener);
    }

    public void destroyListener() {
        TUIChatService.getInstance().setGroupChatEventListener(null);
    }
}

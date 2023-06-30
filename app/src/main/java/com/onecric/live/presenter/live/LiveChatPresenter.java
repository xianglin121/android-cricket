package com.onecric.live.presenter.live;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.BoxBean;
import com.onecric.live.model.GiftBean;
import com.onecric.live.model.HistoryMsgBean;
import com.onecric.live.model.NobelBean;
import com.onecric.live.model.RedEnvelopeBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.live.LiveChatView;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;
import com.tencent.qcloud.tuikit.tuichat.interfaces.GroupChatEventListener;

import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.observers.DisposableObserver;


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

    public void getHistoryMessage(int id) {
        addSubscription(apiStores.getHistoryMessage(TimeZone.getDefault().getID(),id),
                new DisposableObserver() {
                    @Override
                    public void onNext(Object o) {
                        int code = JSONObject.parseObject(o.toString()).getIntValue("errorCode");
                        if(code == 0){
                            //成功
                            try{
                                List<HistoryMsgBean.RspMsgListDTO> list = JSONObject.parseArray(JSONObject.parseObject(o.toString()).getString("RspMsgList"), HistoryMsgBean.RspMsgListDTO.class);
                                Collections.reverse(list);
                                mvpView.getHistoryMsgListSuccess(list);
                            }catch (Exception e){
                                e.printStackTrace();
                                mvpView.getHistoryMsgListSuccess(null);
                            }
                        }else{
                            //失败
                            mvpView.getHistoryMsgListSuccess(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mvpView.getHistoryMsgListSuccess(null);
                    }

                    @Override
                    public void onComplete() {
                        mvpView.getHistoryMsgListSuccess(null);
                    }
                });
    }
}

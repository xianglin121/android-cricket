package com.longya.live.view.live;

import com.longya.live.model.BoxBean;
import com.longya.live.model.GiftBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.NobelBean;
import com.longya.live.model.RedEnvelopeBean;
import com.longya.live.view.BaseView;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;

import java.util.List;

public interface LiveChatView extends BaseView<JsonBean> {
    void getNobelDataSuccess(NobelBean nobelBean);

    void getGiftListSuccess(List<GiftBean> list);

    void getBackpackGiftListSuccess(List<GiftBean> list);

    void sendGiftSuccess(GiftBean giftBean, String msg);

    void onGroupForceExit(String groupId);

    void exitGroupChat(String chatId);

    void onApplied(int unHandledSize);

    void handleRevoke(String msgId);

    void onRecvNewMessage(MessageInfo messageInfo);

    void onGroupNameChanged(String groupId, String newName);

    void getBackgroundDanmuListSuccess(List<GiftBean> list);

    void getBoxListSuccess(List<BoxBean> list);

    void doBoxTimeOverSuccess();

    void openBoxSuccess(BoxBean boxBean);

    void getRedEnvelopeListSuccess(List<RedEnvelopeBean> list);

    void receiveRedEnvelope(String amount);

    void addRedEnvelopeSuccess();
}

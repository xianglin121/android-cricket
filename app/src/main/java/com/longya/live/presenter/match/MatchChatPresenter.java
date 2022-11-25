package com.longya.live.presenter.match;

import com.longya.live.presenter.BasePresenter;
import com.longya.live.view.match.MatchChatView;
import com.longya.live.view.match.MatchView;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;
import com.tencent.qcloud.tuikit.tuichat.interfaces.GroupChatEventListener;

public class MatchChatPresenter extends BasePresenter<MatchChatView> {
    private GroupChatEventListener groupChatEventListener;

    public MatchChatPresenter(MatchChatView view) {
        attachView(view);
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

package com.onecric.live.custom.listener;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public interface OnChannelListener {
    void onItemMove(int starPos, int endPos);
    void onMoveToMyChannel(int type, int starPos, int endPos);
    void onMoveToOtherChannel(int type, int starPos, int endPos);
}

package com.onecric.live.model;

import java.io.Serializable;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/1
 */
public class BlockFunctionBean implements Serializable {
    private static final long serialVersionUID = 141315161718191140L;

    private boolean isBlockEnvelope;//是否屏蔽红包弹窗
    private boolean isBlockNoble;//是否屏蔽贵族特效
    private boolean isBlockGift;//是否屏蔽礼物特效
    private boolean isBlockEnter;//是否屏蔽入场消息

    public boolean isBlockEnvelope() {
        return isBlockEnvelope;
    }

    public void setBlockEnvelope(boolean blockEnvelope) {
        isBlockEnvelope = blockEnvelope;
    }

    public boolean isBlockNoble() {
        return isBlockNoble;
    }

    public void setBlockNoble(boolean blockNoble) {
        isBlockNoble = blockNoble;
    }

    public boolean isBlockGift() {
        return isBlockGift;
    }

    public void setBlockGift(boolean blockGift) {
        isBlockGift = blockGift;
    }

    public boolean isBlockEnter() {
        return isBlockEnter;
    }

    public void setBlockEnter(boolean blockEnter) {
        isBlockEnter = blockEnter;
    }
}

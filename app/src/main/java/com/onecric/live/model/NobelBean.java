package com.onecric.live.model;

import java.io.Serializable;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/27
 */
public class NobelBean implements Serializable {
    private List<NobelListBean> goard;

    public List<NobelListBean> getGoard() {
        return goard;
    }

    public void setGoard(List<NobelListBean> goard) {
        this.goard = goard;
    }
}

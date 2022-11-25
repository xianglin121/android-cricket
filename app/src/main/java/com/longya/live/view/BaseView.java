package com.longya.live.view;

/**
 * Created by WuXiaolong on 2016/10/19.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */

public interface BaseView<M> {
    void showLoading();

    void hideLoading();

    void getDataSuccess(M model);

    void getDataFail(String msg);
}

package com.onecric.live.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.onecric.live.AppManager;
import com.onecric.live.R;
import com.onecric.live.presenter.BasePresenter;

import pro.piwik.sdk.Tracker;
import pro.piwik.sdk.extra.TrackHelper;


/**
 * Created by WuXiaolong on 2016/3/30.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public abstract class MvpFragment<P extends BasePresenter> extends Fragment {
    protected View rootView;
    protected P mvpPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onActivityCreated(savedInstanceState);
        initUI();
        Tracker tracker = ((AppManager) getActivity().getApplication()).getTracker();
        TrackHelper.track().screen(getActivity()).with(tracker);
        initData();
    }


    protected abstract int getLayoutId();

    protected abstract P createPresenter();

    protected abstract void initUI();

    protected abstract void initData();

    protected <T extends View> T findViewById(int layoutId) {
        return rootView.findViewById(layoutId);
    }

    public void showEmptyView() {
        if (rootView.findViewById(R.id.ll_empty) != null) {
            rootView.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        }
    }

    public void hideEmptyView() {
        if (rootView.findViewById(R.id.ll_empty) != null) {
            rootView.findViewById(R.id.ll_empty).setVisibility(View.INVISIBLE);
        }
    }

    public void setEmptyText(String text) {
        TextView tv_empty = rootView.findViewById(R.id.tv_empty);
        if (tv_empty != null) {
            if (!TextUtils.isEmpty(text)) {
                tv_empty.setText(text);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}

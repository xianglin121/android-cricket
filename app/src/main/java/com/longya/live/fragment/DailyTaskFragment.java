package com.longya.live.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.R;
import com.longya.live.activity.LiveDetailActivity;
import com.longya.live.activity.MyTaskActivity;
import com.longya.live.adapter.NoviceTaskAdapter;
import com.longya.live.event.ToggleTabEvent;
import com.longya.live.model.JsonBean;
import com.longya.live.model.TaskBean;
import com.longya.live.presenter.live.DailyTaskPresenter;
import com.longya.live.presenter.live.NoviceTaskPresenter;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.live.DailyTaskView;
import com.longya.live.view.live.NoviceTaskView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/11
 */
public class DailyTaskFragment extends MvpFragment<DailyTaskPresenter> implements DailyTaskView {
    public static DailyTaskFragment newInstance() {
        DailyTaskFragment fragment = new DailyTaskFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView rv_novice;
    private NoviceTaskAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_novice_task;
    }

    @Override
    protected DailyTaskPresenter createPresenter() {
        return new DailyTaskPresenter(this);
    }

    @Override
    protected void initUI() {
        rv_novice = findViewById(R.id.rv_novice);
    }

    @Override
    protected void initData() {
        mAdapter = new NoviceTaskAdapter(R.layout.item_novice_task, new ArrayList<>());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_finish) {
                    if ("邀请好友注册".equals(mAdapter.getItem(position).getTask())) {
                        ((MyTaskActivity)getActivity()).showDialog();
                    }else if ("评论奖励".equals(mAdapter.getItem(position).getTask())
                            || "回复奖励".equals(mAdapter.getItem(position).getTask())
                            || "点赞奖励".equals(mAdapter.getItem(position).getTask())) {
                        EventBus.getDefault().post(new ToggleTabEvent(1));
                        getActivity().finish();
                    }else if ("关注主播".equals(mAdapter.getItem(position).getTask())) {
                        if (mAdapter.getItem(position).getAnchor().getUid() > 0) {
                            LiveDetailActivity.forward(getContext(), mAdapter.getItem(position).getAnchor().getUid(), mAdapter.getItem(position).getAnchor().getType(), mAdapter.getItem(position).getAnchor().getMatch_id());
                        }
                    }
                }
            }
        });
        rv_novice.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_novice.setAdapter(mAdapter);

        mvpPresenter.getList();
    }

    @Override
    public void getDataSuccess(List<TaskBean> list) {
        if (list != null) {
            mAdapter.setNewData(list);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}

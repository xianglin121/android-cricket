package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.activity.MyTaskActivity;
import com.onecric.live.adapter.NoviceTaskAdapter;
import com.onecric.live.event.ToggleTabEvent;
import com.onecric.live.model.TaskBean;
import com.onecric.live.presenter.live.DailyTaskPresenter;
import com.onecric.live.util.SpUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.DailyTaskView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
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
                            if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME)){
//                                LoginActivity.forward(getContext());
                            }else{
                                LiveDetailActivity.forward(getContext(), mAdapter.getItem(position).getAnchor().getUid(), mAdapter.getItem(position).getAnchor().getType(), mAdapter.getItem(position).getAnchor().getMatch_id(),mAdapter.getItem(position).getAnchor().getId());
                            }
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

package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.CommunityPublishActivity;
import com.onecric.live.adapter.CreatorTaskAdapter;
import com.onecric.live.model.TaskBean;
import com.onecric.live.presenter.live.CreatorTaskPresenter;
import com.onecric.live.util.CommonUtil;
import com.onecric.live.util.DpUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.CreatorTaskView;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/11
 */
public class CreatorTaskFragment extends MvpFragment<CreatorTaskPresenter> implements CreatorTaskView {
    public static CreatorTaskFragment newInstance() {
        CreatorTaskFragment fragment = new CreatorTaskFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView rv_creator;
    private CreatorTaskAdapter mAdapter;
    private TextView tv_state;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_creator_task;
    }

    @Override
    protected CreatorTaskPresenter createPresenter() {
        return new CreatorTaskPresenter(this);
    }

    @Override
    protected void initUI() {
        rv_creator = findViewById(R.id.rv_creator);
        tv_state = findViewById(R.id.tv_state);
    }

    @Override
    protected void initData() {
        mAdapter = new CreatorTaskAdapter(R.layout.item_creator_task, new ArrayList<>());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_finish) {
                    if (CommonAppConfig.getInstance().getUserBean() != null) {
                        if (CommonAppConfig.getInstance().getUserBean().getIs_writer() == 1) {
                            CommunityPublishActivity.forward(getContext());
                        }else {
                            CommonUtil.forwardCustomer(getContext());
                        }
                    }
                }
            }
        });
        rv_creator.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_creator.setAdapter(mAdapter);

        if (CommonAppConfig.getInstance().getUserBean() != null) {
            if (CommonAppConfig.getInstance().getUserBean().getIs_writer() == 1) {
                tv_state.setText(getActivity().getString(R.string.hint_apply_creator_success));
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv_state.getLayoutParams();
                layoutParams.setMargins(DpUtil.dp2px(20), DpUtil.dp2px(14), DpUtil.dp2px(20), DpUtil.dp2px(20));
            }else {
                tv_state.setText(getActivity().getString(R.string.hint_apply_creator_fail));
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv_state.getLayoutParams();
                layoutParams.setMargins(DpUtil.dp2px(20), DpUtil.dp2px(14), DpUtil.dp2px(20), DpUtil.dp2px(80));
            }
        }
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

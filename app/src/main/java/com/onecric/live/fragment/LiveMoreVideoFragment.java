package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.LiveNotStartDetailActivity;
import com.onecric.live.activity.OneLogInActivity;
import com.onecric.live.adapter.LiveMoreVideoAdapter;
import com.onecric.live.adapter.decoration.GridDividerItemDecoration;
import com.onecric.live.model.LiveBean;
import com.onecric.live.presenter.live.LiveMoreVideoPresenter;
import com.onecric.live.util.SpUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.LiveMoreVideoView;

import java.util.ArrayList;
import java.util.List;

public class LiveMoreVideoFragment extends MvpFragment<LiveMoreVideoPresenter> implements LiveMoreVideoView, View.OnClickListener {
    public static LiveMoreVideoFragment newInstance() {
        LiveMoreVideoFragment fragment = new LiveMoreVideoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView rv_live;
    private LiveMoreVideoAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_more_video;
    }

    @Override
    protected LiveMoreVideoPresenter createPresenter() {
        return new LiveMoreVideoPresenter(this);
    }

    @Override
    protected void initUI() {
        rv_live = findViewById(R.id.rv_live);
    }

    @Override
    protected void initData() {
        mAdapter = new LiveMoreVideoAdapter(R.layout.item_live_more_video, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(mAdapter.getItem(position).getIslive() == 0){
                    LiveNotStartDetailActivity.forward(getContext(),mAdapter.getItem(position).getUid(),
                            mAdapter.getItem(position).getMatch_id(),mAdapter.getItem(position).getLive_id());
                }else if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                    /*if(loginDialog!=null){
                        loginDialog.show();
                    }else{
                        ToastUtil.show(getString(R.string.please_login));
                    }*/
                    OneLogInActivity.forward(getContext());
                }else{
                    LiveDetailActivity.forward(getContext(), mAdapter.getItem(position).getUid(),
                            mAdapter.getItem(position).getMatch_id(),mAdapter.getItem(position).getLive_id());
                    getActivity().finish();
                }
            }
        });
        rv_live.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_live.addItemDecoration(new GridDividerItemDecoration(getContext(), 10, 2));
        rv_live.setAdapter(mAdapter);

        mvpPresenter.getList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<LiveBean> list) {
        if (list != null) {
            mAdapter.setNewData(list);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }
}

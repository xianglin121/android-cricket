package com.longya.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.R;
import com.longya.live.activity.CricketTeamsActivity;
import com.longya.live.activity.PlayerProfileActivity;
import com.longya.live.adapter.CricketTeamsAdapter;
import com.longya.live.adapter.PlayerBioAdapter;
import com.longya.live.custom.ItemDecoration;
import com.longya.live.model.RecentMatchesBean;
import com.longya.live.view.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public class PlayerBioFragment extends BaseFragment {
    public static PlayerBioFragment newInstance() {
        PlayerBioFragment fragment = new PlayerBioFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private TextView tv_teams;
    private RecyclerView rv_bio;
    private PlayerBioAdapter mAdapter;
    private TextView tv_profile;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_player_bio;
    }

    @Override
    protected void initUI() {
        tv_teams = findViewById(R.id.tv_teams);
        rv_bio = findViewById(R.id.rv_bio);
        tv_profile = findViewById(R.id.tv_profile);
    }

    @Override
    protected void initData() {
        mAdapter = new PlayerBioAdapter(R.layout.item_player_bio, new ArrayList<>());
        rv_bio.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_bio.addItemDecoration(new ItemDecoration(getContext(), getResources().getColor(R.color.c_CCCCCC), 0, 0.5f));
        rv_bio.setAdapter(mAdapter);
    }

    public void setData(String teams, List<RecentMatchesBean> list, String profile) {
        if (!TextUtils.isEmpty(teams)) {
            tv_teams.setText(teams);
        }
        if (!TextUtils.isEmpty(profile)) {
            tv_profile.setText(profile);
        }
    }
}

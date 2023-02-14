package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.onecric.live.HttpConstant;
import com.onecric.live.R;
import com.onecric.live.fragment.PlayerBioFragment;
import com.onecric.live.fragment.PlayerCareerFragment;
import com.onecric.live.model.BattingBean;
import com.onecric.live.model.BowlingBean;
import com.onecric.live.model.FieldingBean;
import com.onecric.live.model.PlayerProfileBean;
import com.onecric.live.model.RecentMatchesBean;
import com.onecric.live.presenter.cricket.PlayerProfilePresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.ShareUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.cricket.PlayerProfileView;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/9/2
 * 球员详情页
 */
public class PlayerProfileActivity extends MvpActivity<PlayerProfilePresenter> implements PlayerProfileView {
    public static void forward(Context context, int playerId) {
        Intent intent = new Intent(context, PlayerProfileActivity.class);
        intent.putExtra("player_id", playerId);
        context.startActivity(intent);
    }

    private int mPlayerId;
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_position;
    private TextView tv_birth;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;

    @Override
    protected PlayerProfilePresenter createPresenter() {
        return new PlayerProfilePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_player_profile;
    }

    @Override
    protected void initView() {
        //scheme
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                String id = uri.getQueryParameter("id");
                mPlayerId = Integer.parseInt(id);
            }
        }else{
            mPlayerId = getIntent().getIntExtra("player_id", 0);
        }

        setTitleText(getString(R.string.player_profile));

        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_position = findViewById(R.id.tv_position);
        tv_birth = findViewById(R.id.tv_birth);
        tabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewpager);

        ((ImageView)findViewById(R.id.iv_right)).setBackgroundResource(R.mipmap.icon_share2);
        ((ImageView)findViewById(R.id.iv_right)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.shareText(mActivity, "", HttpConstant.PLAYER_PROFILE_URL + mPlayerId);
            }
        });
    }

    @Override
    protected void initData() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.bio)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.career)));
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.fantasy)));

        mViewList = new ArrayList<>();
        mViewList.add(PlayerBioFragment.newInstance());
        mViewList.add(PlayerCareerFragment.newInstance());
//        mViewList.add(PlayerFantasyFragment.newInstance());

        initViewPager();
        mvpPresenter.getData(mPlayerId);
        mvpPresenter.getBioData(mPlayerId);
        mvpPresenter.getCareerData(mPlayerId, PlayerCareerFragment.TYPE_ODI);
    }

    private void initViewPager() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //初始化viewpager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setOffscreenPageLimit(mViewList.size());
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mViewList.get(i);
            }

            @Override
            public int getCount() {
                return mViewList.size();
            }
        });
    }

    @Override
    public void getDataSuccess(PlayerProfileBean model) {
        if (model != null) {
            GlideUtil.loadUserImageDefault(this, model.getImage_path(), iv_avatar);
            if (!TextUtils.isEmpty(model.getFull_name())) {
                tv_name.setText(model.getFull_name());
            }
            if (!TextUtils.isEmpty(model.getType())) {
                tv_position.setText(model.getType());
            }
            if (!TextUtils.isEmpty(model.getBirth())) {
                tv_birth.setText(model.getBirth());
            }
        }
    }

    @Override
    public void getDataSuccess(String teams, List<RecentMatchesBean> list, String profile) {
        ((PlayerBioFragment)mViewList.get(0)).setData(teams, list, profile);
    }

    public void getCareerData(String type) {
        mvpPresenter.getCareerData(mPlayerId, type);
    }

    @Override
    public void getDataSuccess(BattingBean batting, BowlingBean bowling, FieldingBean fielding, List<RecentMatchesBean> list) {
        ((PlayerCareerFragment)mViewList.get(1)).setData(batting, bowling, fielding, list);
    }

    @Override
    public void getDataFail(String msg) {

    }
}

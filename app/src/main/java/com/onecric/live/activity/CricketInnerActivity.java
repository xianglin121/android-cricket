package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.onecric.live.HttpConstant;
import com.onecric.live.R;
import com.onecric.live.fragment.CricketMatchesFragment;
import com.onecric.live.fragment.CricketPointsFragment;
import com.onecric.live.fragment.CricketStatsFragment;
import com.onecric.live.fragment.CricketTeamsFragment;
import com.onecric.live.fragment.CricketUpdatesFragment;
import com.onecric.live.model.CricketPointsBean;
import com.onecric.live.model.CricketStatsBean;
import com.onecric.live.model.CricketTeamBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.UpdatesBean;
import com.onecric.live.presenter.cricket.CricketInnerPresenter;
import com.onecric.live.util.ShareUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.cricket.CricketInnerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketInnerActivity extends MvpActivity<CricketInnerPresenter> implements CricketInnerView {
    public static void forward(Context context, String name, String type, int id) {
        Intent intent = new Intent(context, CricketInnerActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private int mId;
    public String mType = "";
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;

    @Override
    protected CricketInnerPresenter createPresenter() {
        return new CricketInnerPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cricket_inner;
    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("name"))) {
            setTitleText(getIntent().getStringExtra("name"));
        }else {
            setTitleText("");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
            mType = getIntent().getStringExtra("type");
        }
        mId = getIntent().getIntExtra("id", 0);

        tabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

        ((ImageView)findViewById(R.id.iv_right)).setBackgroundResource(R.mipmap.icon_share2);
        ((ImageView)findViewById(R.id.iv_right)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.shareText(mActivity, "", HttpConstant.CRICKET_LEAGUE_URL + mId);
            }
        });
    }

    @Override
    protected void initData() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.matches)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.stats)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.teams)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.points_table)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.updates)));

        mViewList = new ArrayList<>();
        mViewList.add(CricketMatchesFragment.newInstance(mId));
        mViewList.add(CricketStatsFragment.newInstance());
        mViewList.add(CricketTeamsFragment.newInstance());
        mViewList.add(CricketPointsFragment.newInstance());
        mViewList.add(CricketUpdatesFragment.newInstance());

        initViewPager();
        mvpPresenter.getTeamList(mId);
        mvpPresenter.getPointsList(mId);
        mvpPresenter.getUpdatesDetail(mId);
        mvpPresenter.getStats(mId);
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
    public void getTeamDataSuccess(List<CricketTeamBean> list) {
        ((CricketTeamsFragment)mViewList.get(2)).setList(list);
    }

    @Override
    public void getPointsDataSuccess(List<CricketPointsBean> list) {
        ((CricketPointsFragment)mViewList.get(3)).setList(list);
    }

    @Override
    public void getUpdatesDataSuccess(List<UpdatesBean> list) {
        ((CricketUpdatesFragment)mViewList.get(4)).setData(list);
    }

    @Override
    public void getStatsDataSuccess(CricketStatsBean cricketStatsBean) {
        ((CricketStatsFragment)mViewList.get(1)).setData(mId, cricketStatsBean);
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }
}

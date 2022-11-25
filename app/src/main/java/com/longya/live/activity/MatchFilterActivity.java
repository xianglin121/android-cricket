package com.longya.live.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.longya.live.CommonCode;
import com.longya.live.R;
import com.longya.live.fragment.MatchFilterOneFragment;
import com.longya.live.fragment.MatchFilterTwoFragment;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.match.MatchFilterPresenter;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.match.MatchFilterView;

import java.util.ArrayList;
import java.util.List;

public class MatchFilterActivity extends MvpActivity<MatchFilterPresenter> implements MatchFilterView, View.OnClickListener {

    public static void forwardForFootball(Activity activity) {
        Intent intent = new Intent(activity, MatchFilterActivity.class);
        intent.putExtra("type", 0);
        activity.startActivityForResult(intent, CommonCode.REQUEST_CODE_FOOTBALL_MATCH_FILTER);
    }

    public static void forwardForBasketball(Activity activity) {
        Intent intent = new Intent(activity, MatchFilterActivity.class);
        intent.putExtra("type", 1);
        activity.startActivityForResult(intent, CommonCode.REQUEST_CODE_BASKETBALL_MATCH_FILTER);
    }

    private TextView tv_match, tv_area;
    private ViewPager vp_filter;
    private List<Fragment> mViewList;

    @Override
    protected MatchFilterPresenter createPresenter() {
        return new MatchFilterPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_match_filter;
    }

    @Override
    protected void initView() {
        mViewList = new ArrayList<>();
        tv_match = findViewById(R.id.tv_match);
        tv_area = findViewById(R.id.tv_area);
        vp_filter = findViewById(R.id.vp_filter);

        tv_match.setOnClickListener(this);
        tv_area.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tv_match.setSelected(true);

        mViewList.add(MatchFilterOneFragment.newInstance(getIntent().getIntExtra("type", 0)));
        mViewList.add(MatchFilterTwoFragment.newInstance(getIntent().getIntExtra("type", 0)));
        initViewPager();
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_match:
                if (tv_match.isSelected()) {
                    return;
                }
                tv_match.setSelected(true);
                tv_area.setSelected(false);
                vp_filter.setCurrentItem(0, false);
                break;
            case R.id.tv_area:
                if (tv_area.isSelected()) {
                    return;
                }
                tv_match.setSelected(false);
                tv_area.setSelected(true);
                vp_filter.setCurrentItem(1, false);
                break;
        }
    }

    private void initViewPager() {
        //初始化viewpager
        vp_filter.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        vp_filter.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
}

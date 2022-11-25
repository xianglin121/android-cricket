package com.longya.live.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.longya.live.R;
import com.longya.live.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/10
 */
public class MySpaceCommunityFragment extends BaseFragment implements View.OnClickListener {
    public static MySpaceCommunityFragment newInstance(int uid) {
        MySpaceCommunityFragment fragment = new MySpaceCommunityFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("uid", uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mUid;
    private TextView tv_one;
    private TextView tv_two;
    private TextView tv_three;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_space_community;
    }

    @Override
    protected void initUI() {
        mUid = getArguments().getInt("uid");
        mViewList = new ArrayList<>();
        tv_one = findViewById(R.id.tv_one);
        tv_two = findViewById(R.id.tv_two);
        tv_three = findViewById(R.id.tv_three);
        mViewPager = findViewById(R.id.vp_headline);

        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);
        tv_three.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_one:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tv_two:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tv_three:
                mViewPager.setCurrentItem(2);
                break;
        }
    }

    @Override
    protected void initData() {
        //初始化viewpager
        mViewList.add(MySpaceCommunityOneFragment.newInstance(mUid));
        mViewList.add(MySpaceCommunityTwoFragment.newInstance(mUid));
        mViewList.add(MySpaceCommunityThreeFragment.newInstance(mUid));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    tv_one.setTextColor(getResources().getColor(R.color.c_E3AC72));
                    tv_two.setTextColor(getResources().getColor(R.color.c_666666));
                    tv_three.setTextColor(getResources().getColor(R.color.c_666666));
                }else if (i == 1) {
                    tv_one.setTextColor(getResources().getColor(R.color.c_666666));
                    tv_two.setTextColor(getResources().getColor(R.color.c_E3AC72));
                    tv_three.setTextColor(getResources().getColor(R.color.c_666666));
                }else if (i == 2) {
                    tv_one.setTextColor(getResources().getColor(R.color.c_666666));
                    tv_two.setTextColor(getResources().getColor(R.color.c_666666));
                    tv_three.setTextColor(getResources().getColor(R.color.c_E3AC72));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mViewList.get(i);
            }

            @Override
            public int getCount() {
                return mViewList.size();
            }
        });
        mViewPager.setOffscreenPageLimit(mViewList.size());
    }
}

package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.coorchice.library.SuperTextView;
import com.onecric.live.R;
import com.onecric.live.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MatchFilterOneFragment extends BaseFragment implements  View.OnClickListener {
    public static MatchFilterOneFragment newInstance(int type) {
        MatchFilterOneFragment fragment = new MatchFilterOneFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mType;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;
    private SuperTextView tv_all, tv_hot;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match_filter_one;
    }

    @Override
    protected void initUI() {
        mType = getArguments().getInt("type");
        mViewPager = rootView.findViewById(R.id.vp_match);
        tv_all = rootView.findViewById(R.id.tv_all);
        tv_hot = rootView.findViewById(R.id.tv_hot);

        tv_all.setOnClickListener(this);
        tv_hot.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mViewList = new ArrayList<>();
        tv_all.setSelected(true);

        mViewList.add(MatchFilterOneInnerFragment.newInstance(0, mType));
        if (mType == 1) {
            tv_hot.setVisibility(View.GONE);
        }else {
            tv_hot.setVisibility(View.VISIBLE);
            mViewList.add(MatchFilterOneInnerFragment.newInstance(1, mType));
        }
        initViewPager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_all:
                if (tv_all.isSelected()) {
                    return;
                }
                toggleButton(tv_all, tv_hot);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tv_hot:
                if (tv_hot.isSelected()) {
                    return;
                }
                toggleButton(tv_hot, tv_all);
                mViewPager.setCurrentItem(1);
                break;
        }
    }

    private void initViewPager() {
        //初始化viewpager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        if (tv_all.isSelected()) {
                            return;
                        }
                        toggleButton(tv_all, tv_hot);
                        break;
                    case 1:
                        if (tv_hot.isSelected()) {
                            return;
                        }
                        toggleButton(tv_hot, tv_all);
                        break;
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
    }

    private void toggleButton(SuperTextView selectedView, SuperTextView unselectView1) {
        selectedView.setSelected(true);
        selectedView.setTextColor(getResources().getColor(R.color.c_87390E));
        selectedView.setSolid(getResources().getColor(R.color.c_FFDFAB));
        unselectView1.setSelected(false);
        unselectView1.setTextColor(getResources().getColor(R.color.c_666666));
        unselectView1.setSolid(getResources().getColor(R.color.c_EAEAEA));
    }
}

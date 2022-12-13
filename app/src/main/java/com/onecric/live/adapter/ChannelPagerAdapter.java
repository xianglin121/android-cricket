package com.onecric.live.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/11
 */
public class ChannelPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mViewList;

    public ChannelPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mViewList = fragmentList != null ? fragmentList : new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        return mViewList.get(i);
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}

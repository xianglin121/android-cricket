package com.longya.live.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.longya.live.fragment.PreViewContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2020/11/24
 */

public class PreViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> imageList = new ArrayList<>();

    public PreViewPagerAdapter(FragmentManager fm, List<String> image) {
        super(fm);
        this.imageList = image;
    }

    @Override
    public Fragment getItem(int i) {
        return PreViewContentFragment.newInstant(imageList.get(i));
    }

    @Override
    public int getCount() {
        return imageList.size();
    }
}

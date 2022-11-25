package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.longya.live.R;
import com.longya.live.fragment.UsagePreViewContentFragment;
import com.longya.live.util.SpUtil;
import com.longya.live.util.WordUtil;
import com.longya.live.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/6/17
 */
public class UsageViewActivity extends BaseActivity {
    public static void forward(Context context) {
        Intent intent = new Intent(context, UsageViewActivity.class);
        context.startActivity(intent);
    }

    private ViewPager mPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_usage_view;
    }

    @Override
    protected void initView() {
        mPager = findViewById(R.id.view_pager);
    }

    @Override
    protected void initData() {
        List<UsagePreViewContentFragment> list = new ArrayList<>();
        list.add(UsagePreViewContentFragment.newInstant(1));
        list.add(UsagePreViewContentFragment.newInstant(2));
        list.add(UsagePreViewContentFragment.newInstant(3));
        list.add(UsagePreViewContentFragment.newInstant(4));

        //初始化viewpager
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        SpUtil.getInstance().setBooleanValue(SpUtil.RED_AND_YELLOW_CARD, true);
        SpUtil.getInstance().setBooleanValue(SpUtil.GOAL_SOUND, true);
        SpUtil.getInstance().setBooleanValue(SpUtil.GOAL_VIBRATOR, true);
        SpUtil.getInstance().setBooleanValue(SpUtil.RED_CARD_SOUND, true);
        SpUtil.getInstance().setBooleanValue(SpUtil.RED_CARD_VIBRATOR, true);
    }
}

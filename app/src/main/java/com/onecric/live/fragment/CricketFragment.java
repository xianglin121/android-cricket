package com.onecric.live.fragment;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.MainActivity;
import com.onecric.live.event.ToggleTabEvent;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.UiUtils;
import com.onecric.live.view.BaseFragment;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public class CricketFragment extends BaseFragment {
    private ImageView iv_avatar;
    private TextView tv_name;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricket;
    }

    @Override
    protected void initUI() {
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

        findViewById(R.id.iv_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openDrawer();
            }
        });
    }

    @Override
    protected void initData() {
        updateUserInfo();

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.results)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.today)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.upcoming)));

        mViewList = new ArrayList<>();
        mViewList.add(CricketResultFragment.newInstance("results"));
        mViewList.add(CricketResultFragment.newInstance("today"));
        mViewList.add(CricketResultFragment.newInstance("upcoming"));

        initViewPager();
    }

    public void updateUserInfo() {
        if (CommonAppConfig.getInstance().getUserBean() != null) {
            GlideUtil.loadUserImageDefault(getContext(), CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar);
            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getUser_nickname())) {
                tv_name.setText(CommonAppConfig.getInstance().getUserBean().getUser_nickname());
            }
        }
    }

    private void initViewPager() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                TextView textView = new TextView(getActivity());
                float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 16, getResources().getDisplayMetrics());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
                textView.setTextColor(getResources().getColor(R.color.c_DC3C23));
                textView.setGravity(Gravity.CENTER);
                textView.setText(tab.getText());
                tab.setCustomView(textView);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
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
        mViewPager.setCurrentItem(1);
    }

    public void toTabPosition(int index){
        mViewPager.setCurrentItem(index);
    }

}

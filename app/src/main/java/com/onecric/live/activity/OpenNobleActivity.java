package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.onecric.live.R;
import com.onecric.live.custom.CustomPagerTitleView;
import com.onecric.live.fragment.OpenNobleFragment;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveUserBean;
import com.onecric.live.model.NobelBean;
import com.onecric.live.model.NobelListBean;
import com.onecric.live.presenter.live.OpenNoblePresenter;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.live.OpenNobleView;
import com.tencent.liteav.demo.superplayer.model.event.OpenNobleSuccessEvent;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class OpenNobleActivity extends MvpActivity<OpenNoblePresenter> implements OpenNobleView, View.OnClickListener {

    public static void forward(Context context, LiveUserBean liveUserBean, NobelBean nobelBean) {
        Intent intent = new Intent(context, OpenNobleActivity.class);
        intent.putExtra("user", liveUserBean);
        intent.putExtra("bean", nobelBean);
        context.startActivity(intent);
    }

    private LiveUserBean mLiveUserBean;
    private NobelBean mNobelBean;
    private MagicIndicator magicIndicator;
    private List<String> mTitles;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;

    @Override
    protected OpenNoblePresenter createPresenter() {
        return new OpenNoblePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_open_noble;
    }

    @Override
    protected void initView() {
        if (getIntent().getSerializableExtra("user") != null) {
            mLiveUserBean = (LiveUserBean) getIntent().getSerializableExtra("user");
        }
        mNobelBean = (NobelBean) getIntent().getSerializableExtra("bean");
        mTitles = new ArrayList<>();
        mViewList = new ArrayList<>();
        setTitleText(getString(R.string.title_open_nobel));

        magicIndicator = findViewById(R.id.magicIndicator);
        mViewPager = findViewById(R.id.viewpager);
    }

    @Override
    protected void initData() {
        if (mNobelBean != null) {
            if (mNobelBean.getGoard() != null) {
                for (int i = 0; i < mNobelBean.getGoard().size(); i++) {
                    NobelListBean nobelListBean = mNobelBean.getGoard().get(i);
                    if (!TextUtils.isEmpty(nobelListBean.getName())) {
                        mTitles.add(nobelListBean.getName());
                    }else {
                        mTitles.add("");
                    }
                    mViewList.add(OpenNobleFragment.newInstance(i, mLiveUserBean, nobelListBean));
                }
            }
            initViewPager();
        }
    }

    public void openNoble(int id) {
        mvpPresenter.buyNoble(id, mLiveUserBean.getUid());
    }

    @Override
    public void getDataSuccess(JsonBean model) {
        ToastUtil.show(getString(R.string.tip_open_noble_success));
        EventBus.getDefault().post(new OpenNobleSuccessEvent());
        finish();
    }

    @Override
    public void getDataFail(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void initViewPager() {
        //初始化指示器
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CustomPagerTitleView titleView = new CustomPagerTitleView(context);
                titleView.setNormalColor(getResources().getColor(R.color.c_B3B4B7));
                titleView.setSelectedColor(getResources().getColor(R.color.c_FFDFAB));
                titleView.setText(mTitles.get(index));
                titleView.setTextSize(16);
//                titleView.getPaint().setFakeBoldText(true);
                titleView.setOnPagerTitleChangeListener(new CustomPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mViewPager != null) {
                            mViewPager.setCurrentItem(index);
                        }
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(DpUtil.dp2px(25));
                linePagerIndicator.setLineHeight(DpUtil.dp2px(3));
                linePagerIndicator.setXOffset(DpUtil.dp2px(0));
                linePagerIndicator.setYOffset(DpUtil.dp2px(1));
                linePagerIndicator.setRoundRadius(DpUtil.dp2px(2));
                linePagerIndicator.setColors(getResources().getColor(R.color.c_FFDFAB));
                return linePagerIndicator;
            }
        });
        commonNavigator.setAdjustMode(true);
        //初始化viewpager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }
}

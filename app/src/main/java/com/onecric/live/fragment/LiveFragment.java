package com.onecric.live.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LiveMoreFunctionActivity;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.activity.MyTaskActivity;
import com.onecric.live.activity.RankingActivity;
import com.onecric.live.activity.SearchLiveActivity;
import com.onecric.live.custom.CustomPagerTitleView;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.presenter.live.LivePresenter;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.LiveView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class LiveFragment extends MvpFragment<LivePresenter> implements LiveView, View.OnClickListener {

    private MagicIndicator magicIndicator;
    private List<String> mTitles;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;
    private TextView tvSingleTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    protected LivePresenter createPresenter() {
        return new LivePresenter(this);
    }

    @Override
    protected void initUI() {
        magicIndicator = rootView.findViewById(R.id.magicIndicator);
        mViewPager = rootView.findViewById(R.id.viewpager);
        tvSingleTitle = rootView.findViewById(R.id.tv_single_title);

        findViewById(R.id.iv_more).setOnClickListener(this);
        findViewById(R.id.cl_search).setOnClickListener(this);
        findViewById(R.id.iv_ranking).setOnClickListener(this);
        findViewById(R.id.iv_more2).setOnClickListener(this);
//        findViewById(R.id.iv_red_envelope).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //判断other是否为空来布局
        mvpPresenter.getOtherList(2,1);
/*//        mTitles.add(WordUtil.getString(getActivity(), R.string.live_classify));
        mTitles.add(WordUtil.getString(getActivity(), R.string.live_recommend));
//        mTitles.add(WordUtil.getString(getActivity(), R.string.live_football));
//        mTitles.add(WordUtil.getString(getActivity(), R.string.live_basketball));
        mTitles.add(WordUtil.getString(getActivity(), R.string.live_other));
//        mViewList.add(new LiveClassifyFragment());
        mViewList.add(new LiveRecommendFragment());
//        mViewList.add(LiveMatchFragment.newInstance(0));
//        mViewList.add(LiveMatchFragment.newInstance(1));
        mViewList.add(LiveMatchFragment.newInstance(2));
        initViewPager();*/
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_more:
            case R.id.iv_more2:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    LoginActivity.forward(getContext());
                    return;
                }
                LiveMoreFunctionActivity.forward(getContext());
                break;
            case R.id.cl_search:
                SearchLiveActivity.forward(getContext());
                break;
            case R.id.iv_ranking:
                RankingActivity.forward(getContext());
                break;
            case R.id.iv_red_envelope:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getString(R.string.please_login));
                    return;
                }
                MyTaskActivity.forward(getContext());
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    private void initViewPager() {
        //初始化指示器
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CustomPagerTitleView titleView = new CustomPagerTitleView(context);
                titleView.setNormalColor(getResources().getColor(R.color.white));
                titleView.setSelectedColor(getResources().getColor(R.color.c_DC3C23));
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
                linePagerIndicator.setLineWidth(DpUtil.dp2px(50));
                linePagerIndicator.setLineHeight(DpUtil.dp2px(3));
                linePagerIndicator.setXOffset(DpUtil.dp2px(0));
                linePagerIndicator.setYOffset(DpUtil.dp2px(1));
                linePagerIndicator.setRoundRadius(DpUtil.dp2px(2));
                linePagerIndicator.setColors(getResources().getColor(R.color.c_DC3C23));
                return linePagerIndicator;
            }
        });
//        commonNavigator.setAdjustMode(true);
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
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public void getOtherDataSuccess(List<LiveBean> list) {
        mTitles = new ArrayList<>();
        mViewList = new ArrayList<>();
        mTitles.add(WordUtil.getString(getActivity(), R.string.live_recommend));
        mViewList.add(new LiveRecommendFragment());

        if (list != null && list.size() > 0) {
            mTitles.add(WordUtil.getString(getActivity(), R.string.live_other));
            mViewList.add(LiveMatchFragment.newInstance(2));
            magicIndicator.setVisibility(View.VISIBLE);
            tvSingleTitle.setVisibility(View.GONE);
        }else{
            magicIndicator.setVisibility(View.GONE);
            tvSingleTitle.setVisibility(View.VISIBLE);
        }
        initViewPager();
    }
}

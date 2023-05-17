package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.OneLogInActivity;
import com.onecric.live.activity.VideoPublishActivity;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.OneVideoBean;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.model.VideoCategoryBean;
import com.onecric.live.model.VideoShowBean;
import com.onecric.live.presenter.video.VideoPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.video.VideoView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class OneVideoFragment extends MvpFragment<VideoPresenter> implements VideoView {

    public static OneVideoFragment newInstance() {
        OneVideoFragment fragment = new OneVideoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private ViewPager mViewPager;
    private List<Fragment> mViewList;
    private SmartRefreshLayout smart_no_network;
    private TextView tv_empty;
    private TabLayout tabLayout;

    private LoginDialog loginDialog;
    public void setLoginDialog(LoginDialog dialog){
        this.loginDialog = dialog;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_one;
    }

    @Override
    protected VideoPresenter createPresenter() {
        return new VideoPresenter(this);
    }

    @Override
    protected void initUI() {
        mViewList = new ArrayList<>();
        mViewPager = rootView.findViewById(R.id.view_pager);
        tabLayout = rootView.findViewById(R.id.tab_layout);
        tv_empty = rootView.findViewById(R.id.tv_empty);
        smart_no_network = rootView.findViewById(R.id.smart_no_network);
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_no_network.setRefreshHeader(materialHeader);
        smart_no_network.setOnRefreshListener(refreshLayout -> {
            mvpPresenter.getCategory();
        });
        findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        tv_empty.setText(R.string.pull_refresh);

        findViewById(R.id.iv_publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonAppConfig.getInstance().getUserBean() != null) {
                    if (CommonAppConfig.getInstance().getUserBean().getIs_writer() == 1) {
                        VideoPublishActivity.forward(getContext());
                    } else {
                        ToastUtil.show(getActivity().getString(R.string.please_join_writer));
                    }
                } else {
                    OneLogInActivity.forward(getContext());
                }
            }
        });

        smart_no_network.autoRefresh();
    }

    @Override
    protected void initData() {

    }


    private void initViewPager() {
        tabLayout.setVisibility(View.VISIBLE);
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
        //没网时空图
        smart_no_network.finishRefresh();
        smart_no_network.setVisibility(View.VISIBLE);
        if (msg.equals(getString(R.string.no_internet_connection)) && tabLayout.getTabCount()<=0) {
            smart_no_network.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);
        }
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<ShortVideoBean> list) {
    }

    @Override
    public void getCategorySuccess(List<VideoCategoryBean> list) {
        smart_no_network.finishRefresh();
        smart_no_network.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
        if (list != null && list.size() > 0) {
            tabLayout.removeAllTabs();
            mViewList.clear();
            for (int i = 0; i < list.size(); i++) {
                VideoCategoryBean bean = list.get(i);
                if (!TextUtils.isEmpty(bean.name)) {
                    tabLayout.addTab(tabLayout.newTab().setText(bean.name));
                    mViewList.add(OneVideoInnerFragment.newInstance(bean.id));
                }
            }
            initViewPager();
        }
    }

    @Override
    public void getInnerDataSuccess(List<OneVideoBean.FirstCategoryBean> tList, List<OneVideoBean.SecondCategoryBean> othersList, List<VideoShowBean> showsList,OneVideoBean.BannerBean bean) {

    }
}

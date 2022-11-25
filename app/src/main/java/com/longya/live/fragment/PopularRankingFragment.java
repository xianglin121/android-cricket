package com.longya.live.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.longya.live.R;
import com.longya.live.activity.RankingActivity;
import com.longya.live.adapter.LiveRankingAdapter;
import com.longya.live.model.JsonBean;
import com.longya.live.model.RankingBean;
import com.longya.live.presenter.live.LiveRankingPresenter;
import com.longya.live.presenter.live.PopularRankingPresenter;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.live.LiveRankingView;
import com.longya.live.view.live.PopularRankingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class PopularRankingFragment extends Fragment implements View.OnClickListener {
    public static PopularRankingFragment newInstance() {
        PopularRankingFragment fragment = new PopularRankingFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    protected View rootView;
    private TextView tv_week_list, tv_month_list, tv_total_list;
    private ViewPager vp_popular;
    private List<Fragment> mViewList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_popular_ranking, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
        initData();
    }

    protected void initUI() {
        tv_week_list = rootView.findViewById(R.id.tv_week_list);
        tv_month_list = rootView.findViewById(R.id.tv_month_list);
        tv_total_list = rootView.findViewById(R.id.tv_total_list);
        vp_popular = rootView.findViewById(R.id.vp_popular);

        tv_week_list.setOnClickListener(this);
        tv_month_list.setOnClickListener(this);
        tv_total_list.setOnClickListener(this);
    }

    protected void initData() {
        tv_week_list.setSelected(true);

        mViewList = new ArrayList<>();
        mViewList.add(PopularRankingInnerFragment.newInstance(RankingActivity.TYPE_WEEK));
        mViewList.add(PopularRankingInnerFragment.newInstance(RankingActivity.TYPE_MONTH));
        mViewList.add(PopularRankingInnerFragment.newInstance(RankingActivity.TYPE_TOTAL));
        initViewPager();
    }

    private void initViewPager() {
        //初始化viewpager
        vp_popular.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        vp_popular.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_week_list:
                if (tv_week_list.isSelected()) {
                    return;
                }
                tv_week_list.setSelected(true);
                tv_month_list.setSelected(false);
                tv_total_list.setSelected(false);
                vp_popular.setCurrentItem(0);
                break;
            case R.id.tv_month_list:
                if (tv_month_list.isSelected()) {
                    return;
                }
                tv_week_list.setSelected(false);
                tv_month_list.setSelected(true);
                tv_total_list.setSelected(false);
                vp_popular.setCurrentItem(1);
                break;
            case R.id.tv_total_list:
                if (tv_total_list.isSelected()) {
                    return;
                }
                tv_week_list.setSelected(false);
                tv_month_list.setSelected(false);
                tv_total_list.setSelected(true);
                vp_popular.setCurrentItem(2);
                break;
        }
    }
}

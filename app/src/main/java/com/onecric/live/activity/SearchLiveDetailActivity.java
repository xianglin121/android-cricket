package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.onecric.live.R;
import com.onecric.live.custom.CustomPagerTitleView;
import com.onecric.live.fragment.SearchAnchorFragment;
import com.onecric.live.fragment.SearchCommunityFragment;
import com.onecric.live.fragment.SearchComplexFragment;
import com.onecric.live.fragment.SearchHeadlineFragment;
import com.onecric.live.fragment.SearchLiveFragment;
import com.onecric.live.fragment.SearchLiveMatchFragment;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.presenter.live.SearchLiveDetailPresenter;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.live.SearchLiveDetailView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class SearchLiveDetailActivity extends MvpActivity<SearchLiveDetailPresenter> implements SearchLiveDetailView, View.OnClickListener {

    public static void forward(Context context, String content) {
        Intent intent = new Intent(context, SearchLiveDetailActivity.class);
        intent.putExtra("content", content);
        context.startActivity(intent);
    }

    private String mContent;
    private EditText et_input;
    private ImageView iv_close;
    private MagicIndicator magicIndicator;
    private List<String> mTitles;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;

    @Override
    protected SearchLiveDetailPresenter createPresenter() {
        return new SearchLiveDetailPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_live_detail;
    }

    @Override
    protected void initView() {
        mContent = getIntent().getStringExtra("content");
        et_input = findViewById(R.id.et_input);
        iv_close = findViewById(R.id.iv_close);
        magicIndicator = findViewById(R.id.magicIndicator);
        mViewPager = findViewById(R.id.viewpager);

        iv_close.setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*??????????????????????????????*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchContent(et_input.getText().toString());
                    return true;
                }
                return false;
            }
        });
        et_input.setText(mContent);

        mTitles = new ArrayList<>();
        mViewList = new ArrayList<>();
        mTitles.add(getString(R.string.complex));
        mTitles.add(getString(R.string.anchor));
        mTitles.add(getString(R.string.match_live));
        mTitles.add(getString(R.string.match_schedule));
        mTitles.add(getString(R.string.theme_headline));
        mTitles.add(getString(R.string.theme_community));
        mViewList.add(SearchComplexFragment.newInstance(mContent));
        mViewList.add(SearchAnchorFragment.newInstance(mContent));
        mViewList.add(SearchLiveFragment.newInstance(mContent));
        mViewList.add(SearchLiveMatchFragment.newInstance(mContent));
        mViewList.add(SearchHeadlineFragment.newInstance(mContent));
        mViewList.add(SearchCommunityFragment.newInstance(mContent));
        initViewPager();
    }

    private void searchContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            iv_close.setVisibility(View.VISIBLE);
            mContent = content;

            String stringValue = SpUtil.getInstance().getStringValue(SpUtil.LIVE_SEARCH_WORD);
            List<String> tempList = JSON.parseArray(stringValue, String.class);
            if (tempList == null) {
                tempList = new ArrayList<>();
            }
            if (!tempList.contains(content)) {
                tempList.add(content);
                SpUtil.getInstance().setStringValue(SpUtil.LIVE_SEARCH_WORD, JSON.toJSONString(tempList));
            }
            if (mViewList != null) {
                for (int i = 0; i < mViewList.size(); i++) {
                    if (mViewList.get(i) instanceof  SearchComplexFragment) {
                        ((SearchComplexFragment)mViewList.get(i)).updateData(content);
                    }
                    if (mViewList.get(i) instanceof  SearchAnchorFragment) {
                        ((SearchAnchorFragment)mViewList.get(i)).updateData(content);
                    }
                    if (mViewList.get(i) instanceof  SearchLiveFragment) {
                        ((SearchLiveFragment)mViewList.get(i)).updateData(content);
                    }
                    if (mViewList.get(i) instanceof  SearchLiveMatchFragment) {
                        ((SearchLiveMatchFragment)mViewList.get(i)).updateData(content);
                    }
                    if (mViewList.get(i) instanceof  SearchHeadlineFragment) {
                        ((SearchHeadlineFragment)mViewList.get(i)).updateData(content);
                    }
                    if (mViewList.get(i) instanceof  SearchCommunityFragment) {
                        ((SearchCommunityFragment)mViewList.get(i)).updateData(content);
                    }
                }
            }
        }
    }

    @Override
    public void getDataSuccess(List<MatchListBean> list) {
    }

    @Override
    public void getDataFail(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                et_input.setText("");
                iv_close.setVisibility(View.GONE);
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    private void initViewPager() {
        //??????????????????
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CustomPagerTitleView titleView = new CustomPagerTitleView(context);
                titleView.setNormalColor(getResources().getColor(R.color.c_666666));
                titleView.setSelectedColor(getResources().getColor(R.color.c_333333));
                titleView.setText(mTitles.get(index));
                titleView.setTextSize(14);
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
                linePagerIndicator.setLineWidth(DpUtil.dp2px(20));
                linePagerIndicator.setLineHeight(DpUtil.dp2px(3));
                linePagerIndicator.setXOffset(DpUtil.dp2px(0));
                linePagerIndicator.setYOffset(DpUtil.dp2px(1));
                linePagerIndicator.setRoundRadius(DpUtil.dp2px(2));
                linePagerIndicator.setColors(getResources().getColor(R.color.c_DC3C23));
                return linePagerIndicator;
            }
        });
//        commonNavigator.setAdjustMode(true);
        //?????????viewpager
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
        mViewPager.setOffscreenPageLimit(mViewList.size());
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

    public void switchPage(int position) {
        if (mViewPager != null && mViewList.size() > position) {
            mViewPager.setCurrentItem(position);
        }
    }
}

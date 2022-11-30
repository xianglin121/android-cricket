package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coorchice.library.SuperTextView;
import com.google.android.material.appbar.AppBarLayout;
import com.longya.live.CommonAppConfig;
import com.longya.live.Constant;
import com.longya.live.R;
import com.longya.live.adapter.ChargeAdapter;
import com.longya.live.custom.CustomPagerTitleView;
import com.longya.live.event.ChangeFootballLanguageEvent;
import com.longya.live.event.UpdateMatchDataFollowEvent;
import com.longya.live.fragment.BasketballDataBestFragment;
import com.longya.live.fragment.BasketballDataRankingFragment;
import com.longya.live.fragment.BasketballDataScheduleFragment;
import com.longya.live.fragment.BasketballDataStatusFragment;
import com.longya.live.fragment.FootballMatchIndexFragment;
import com.longya.live.fragment.FootballMatchLineupFragment;
import com.longya.live.fragment.FootballMatchLiveFragment;
import com.longya.live.fragment.FootballMatchStatusFragment;
import com.longya.live.fragment.MatchChatFragment;
import com.longya.live.model.Channel;
import com.longya.live.model.CoinBean;
import com.longya.live.model.DataInfoBean;
import com.longya.live.model.DataMatchTypeBean;
import com.longya.live.model.DataSeasonBean;
import com.longya.live.model.DataStatusBean;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.match.BasketballMatchDataPresenter;
import com.longya.live.presenter.user.ChargePresenter;
import com.longya.live.util.DpUtil;
import com.longya.live.util.GlideUtil;
import com.longya.live.util.SpUtil;
import com.longya.live.util.ToastUtil;
import com.longya.live.util.WordUtil;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.match.BasketballMatchDataView;
import com.longya.live.view.match.BasketballMatchDetailView;
import com.longya.live.view.user.ChargeView;

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

public class BasketballMatchDataActivity extends MvpActivity<BasketballMatchDataPresenter> implements BasketballMatchDataView, View.OnClickListener {

    public static void forward(Context context, int id) {
        Intent intent = new Intent(context, BasketballMatchDataActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private int mId;
    private AppBarLayout appBarLayout;
    private ConstraintLayout cl_title_one, cl_title_two;
    private TextView tv_year;
    private ImageView iv_logo;
    private TextView tv_match_name_one;
    private TextView tv_match_en_name;
    private TextView tv_match_name_two;
    private SuperTextView tv_follow;
    private MagicIndicator magicIndicator;
    private List<String> mTitles;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;

    private List<DataSeasonBean> mSeasonList;
    private OptionsPickerView pvCustomOptions;
    private int mSeasonId;

    @Override
    protected BasketballMatchDataPresenter createPresenter() {
        return new BasketballMatchDataPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_basketball_match_data;
    }

    @Override
    protected void initView() {
        mId = getIntent().getIntExtra("id", 0);
        appBarLayout = findViewById(R.id.appBarLayout);
        cl_title_one = findViewById(R.id.cl_title_one);
        cl_title_two = findViewById(R.id.cl_title_two);
        tv_year = findViewById(R.id.tv_year);
        iv_logo = findViewById(R.id.iv_logo);
        tv_match_name_one = findViewById(R.id.tv_match_name_one);
        tv_match_en_name = findViewById(R.id.tv_match_en_name);
        tv_match_name_two = findViewById(R.id.tv_match_name_two);
        tv_follow = findViewById(R.id.tv_follow);
        magicIndicator = findViewById(R.id.magicIndicator);
        mViewPager = findViewById(R.id.viewpager);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_back_two).setOnClickListener(this);
        findViewById(R.id.tv_year).setOnClickListener(this);
        findViewById(R.id.tv_follow).setOnClickListener(this);

        cl_title_two.setAlpha(0f);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = (Math.abs(verticalOffset * 1.0f)/appBarLayout.getTotalScrollRange());
                cl_title_one.setAlpha(1f - percent);
                cl_title_two.setAlpha(percent);
            }
        });
    }

    @Override
    protected void initData() {
        String selectedJson = SpUtil.getInstance().getStringValue(Constant.MATCH_DATA_SELECTED);
        if (!TextUtils.isEmpty(selectedJson)) {
            List<Channel> channels = JSONObject.parseArray(selectedJson, Channel.class);
            for (int i = 0; i < channels.size(); i++) {
                if (channels.get(i).sourceid == mId) {
                    tv_follow.setText(getString(R.string.followed));
                    tv_follow.setSelected(true);
                    break;
                }
            }
        }

        mTitles = new ArrayList<>();
        mViewList = new ArrayList<>();
        mTitles.add("概况");
        mTitles.add("赛程");
        mTitles.add("排名");
        mTitles.add("最佳");
        mViewList.add(BasketballDataStatusFragment.newInstance());
        mViewList.add(BasketballDataScheduleFragment.newInstance());
        mViewList.add(BasketballDataRankingFragment.newInstance());
        mViewList.add(BasketballDataBestFragment.newInstance());
        initViewPager();

        mvpPresenter.getDetail(mId, 1, 0, 0);
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getDetailSuccess(List<DataSeasonBean> seasonList, DataInfoBean info, DataStatusBean statusBean, List<DataMatchTypeBean> matchTypeList) {
        if (seasonList != null && seasonList.size() > 0) {
            if (mSeasonList == null) {
                mSeasonId = seasonList.get(0).getId();
                tv_year.setText(seasonList.get(0).getYear());
            }
            mSeasonList = seasonList;
        }
        if (info != null) {
            GlideUtil.loadTeamImageDefault(mActivity, info.getLogo(), iv_logo);
            if (!TextUtils.isEmpty(info.getName())) {
                tv_match_name_one.setText(info.getName());
            }
            if (!TextUtils.isEmpty(info.getName_en())) {
                tv_match_en_name.setText(info.getName_en());
            }
            if (!TextUtils.isEmpty(info.getName())) {
                tv_match_name_two.setText(info.getName());
            }
        }
        ((BasketballDataStatusFragment)mViewList.get(0)).setData(statusBean);
        ((BasketballDataScheduleFragment)mViewList.get(1)).setData(mId, mSeasonId, matchTypeList);
        ((BasketballDataRankingFragment)mViewList.get(2)).getList(mSeasonId);
        ((BasketballDataBestFragment)mViewList.get(3)).getList(mSeasonId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.iv_back_two:
                finish();
                break;
            case R.id.tv_follow:
                tv_follow.setSelected(!tv_follow.isSelected());
                if (tv_follow.isSelected()) {
                    tv_follow.setText(getString(R.string.followed));
                }else {
                    tv_follow.setText(getString(R.string.follow));
                }
                EventBus.getDefault().post(new UpdateMatchDataFollowEvent(mId, 1));
                break;
            case R.id.tv_year:
                if (mSeasonList != null && mSeasonList.size() > 0) {
                    List<String> list = new ArrayList<>();
                    List<Integer> idList = new ArrayList<>();
                    for (int i = 0; i < mSeasonList.size(); i++) {
                        list.add(mSeasonList.get(i).getYear());
                        idList.add(mSeasonList.get(i).getId());
                    }
                    pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                            //返回的分别是三个级别的选中位置
                            tv_year.setText(list.get(options1));
                            mSeasonId = idList.get(options1);
                            mvpPresenter.getDetail(mId, 1, idList.get(options1), 0);
                        }
                    })
                            .setLayoutRes(R.layout.pickerview_choose_language, new CustomListener() {
                                @Override
                                public void customLayout(View v) {
                                    TextView tv_title = v.findViewById(R.id.tv_title);
                                    TextView tv_cancel = v.findViewById(R.id.tv_cancel);
                                    TextView tv_confirm = v.findViewById(R.id.tv_confirm);
                                    tv_title.setText("选择年份");
                                    tv_confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            pvCustomOptions.returnData();
                                            pvCustomOptions.dismiss();
                                        }
                                    });

                                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            pvCustomOptions.dismiss();
                                        }
                                    });
                                }
                            })
                            .isDialog(false)
                            .setOutSideCancelable(true)
                            .build();

                    pvCustomOptions.setPicker(list);//添加数据
                    pvCustomOptions.show();
                }
                break;
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
                titleView.setNormalColor(getResources().getColor(R.color.c_80FFFFFF));
                titleView.setSelectedColor(getResources().getColor(R.color.white));
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
                linePagerIndicator.setLineWidth(DpUtil.dp2px(25));
                linePagerIndicator.setLineHeight(DpUtil.dp2px(3));
                linePagerIndicator.setXOffset(DpUtil.dp2px(0));
                linePagerIndicator.setYOffset(DpUtil.dp2px(5));
                linePagerIndicator.setRoundRadius(DpUtil.dp2px(2));
                linePagerIndicator.setColors(getResources().getColor(R.color.white));
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
        mViewPager.setOffscreenPageLimit(4);
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

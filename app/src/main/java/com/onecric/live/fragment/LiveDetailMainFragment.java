package com.onecric.live.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.custom.CustomPagerTitleView;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.model.LiveUserBean;
import com.onecric.live.util.DpUtil;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/25
 */
public class LiveDetailMainFragment extends Fragment {
    public static LiveDetailMainFragment newInstance(String groupId, int anchorId,int matchId) {
        LiveDetailMainFragment fragment = new LiveDetailMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("groupId", groupId);
        bundle.putInt("anchorId", anchorId);
        bundle.putInt("matchId", anchorId);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected View rootView;
//    private RecyclerView rv_indicator;
//    private LiveIndicatorAdapter mIndicatorAdapter;
//    private int mCurrPosition;//当前指示器位置
    private MagicIndicator magicIndicator;
    private List<String> mTitles;
    private ViewPager vp_live;
    private List<Fragment> mViewList;
    private ConstraintLayout cl_follow;
    private ImageView iv_icon;
    private TextView tv_follow;
    private TextView tv_attention_count;

    private LiveUserBean mUserBean;

    private LoginDialog loginDialog;
    public void setLoginDialog(LoginDialog dialog){
        loginDialog = dialog;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_live_detail_main, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
        initData();
    }

    private void initUI() {
        mTitles = new ArrayList<>();
        mViewList = new ArrayList<>();
//        rv_indicator = rootView.findViewById(R.id.rv_indicator);
        magicIndicator = rootView.findViewById(R.id.magicIndicator);
        vp_live = rootView.findViewById(R.id.vp_live);
        cl_follow = rootView.findViewById(R.id.cl_follow);
        iv_icon = rootView.findViewById(R.id.iv_icon);
        tv_follow = rootView.findViewById(R.id.tv_follow);
        tv_attention_count = rootView.findViewById(R.id.tv_attention_count);

        cl_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    if (mUserBean != null && mUserBean.getIs_attention() == 0) {
                        ((LiveDetailActivity)getActivity()).doFollow();
                    }
                }else {
                    if(loginDialog!=null){
                        loginDialog.show();
                    }else{
                        LoginActivity.forward(getContext());
                    }
                }
            }
        });
    }

    private void initData() {
        updateFollowData();
//        List<IndicatorBean> list = new ArrayList<>();
//        list.add(new IndicatorBean(getActivity().getString(R.string.live_chat), true));
//        list.add(new IndicatorBean(getActivity().getString(R.string.live_anchor), false));
//        list.add(new IndicatorBean(getActivity().getString(R.string.live_ranking), false));
//        list.add(new IndicatorBean(getActivity().getString(R.string.live_about_video), false));
//        mIndicatorAdapter = new LiveIndicatorAdapter(R.layout.item_live_indicator, list);
//        mIndicatorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if (mCurrPosition != position) {
//                    mIndicatorAdapter.getItem(mCurrPosition).setSelected(false);
//                    mIndicatorAdapter.getItem(position).setSelected(true);
//                    mCurrPosition = position;
//                    mIndicatorAdapter.notifyDataSetChanged();
//                    vp_live.setCurrentItem(mCurrPosition);
//                }
//            }
//        });
//        rv_indicator.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        rv_indicator.setAdapter(mIndicatorAdapter);

        initViewPager();

        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
            if (CommonAppConfig.getInstance().getUid().equals(String.valueOf(getArguments().getInt("anchorId")))) {
                cl_follow.setVisibility(View.GONE);
            }else {
                cl_follow.setVisibility(View.VISIBLE);
            }
        }else {
            cl_follow.setVisibility(View.VISIBLE);
        }
    }

    public void updateFollowData() {
        if (((LiveDetailActivity)getActivity()).mLiveRoomBean != null) {
            mUserBean = ((LiveDetailActivity)getActivity()).mLiveRoomBean.getUserData();
        }
        if (mUserBean != null) {
            if (mUserBean.getIs_attention() == 1) {
                cl_follow.setBackgroundColor(getContext().getResources().getColor(R.color.c_D5D5D5));
                tv_follow.setText(getActivity().getString(R.string.followed));
                iv_icon.setVisibility(View.GONE);
            }else {
                cl_follow.setBackgroundResource(R.mipmap.bg_live_follow);
                tv_follow.setText(getActivity().getString(R.string.follow));
                iv_icon.setVisibility(View.VISIBLE);
            }
            tv_attention_count.setText(String.valueOf(mUserBean.getAttention()));
            ((LiveAnchorFragment)mViewList.get(1)).updateFollowData();
            ((LiveChatFragment)mViewList.get(0)).updateLoginData();
        }
    }

    public void updateData() {
        ((LiveChatFragment)mViewList.get(0)).updateData();
    }

    private void initViewPager() {
        int mMatchId = getArguments().getInt("matchId");

        mTitles.add(getActivity().getString(R.string.live_chat));
        mTitles.add(getActivity().getString(R.string.live_anchor));
//        mTitles.add(getActivity().getString(R.string.live_ranking));

        /*
         mTitles.add(getString(R.string.fantasy));
        if(mMatchId!=0){
            mTitles.add(getString(R.string.info));
            mTitles.add(getString(R.string.live));
        }
        mTitles.add(getString(R.string.scorecard));
        mTitles.add(getString(R.string.updates));
        mTitles.add(getString(R.string.squad));
         */

        mTitles.add(getActivity().getString(R.string.live_about_video));
        LiveChatFragment chatFragment = LiveChatFragment.newInstance(getArguments().getString("groupId"), getArguments().getInt("anchorId"));
        chatFragment.setLoginDialog(loginDialog);

        LiveAnchorFragment anchorFragment = LiveAnchorFragment.newInstance(getArguments().getInt("anchorId"));
        anchorFragment.setLoginDialog(loginDialog);
        mViewList.add(chatFragment);
        mViewList.add(anchorFragment);
//        mViewList.add(LiveRankingFragment.newInstance(getArguments().getInt("anchorId")));

        /*
        mViewList.add(CricketFantasyFragment.newInstance());
        if(mMatchId!=0){
            mViewList.add(CricketInfoFragment.newInstance(mMatchId));
            mViewList.add(CricketLiveFragment.newInstance(mMatchId));
        }
        mViewList.add(CricketScorecardFragment.newInstance());
//        mViewList.add(CricketFantasyFragment.newInstance());
        mViewList.add(CricketUpdatesFragment.newInstance());
        mViewList.add(CricketSquadFragment.newInstance());
        */

        mViewList.add(LiveMoreVideoFragment.newInstance());
        //初始化viewpager
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
                titleView.setNormalColor(getResources().getColor(R.color.c_333333));
                titleView.setSelectedColor(getResources().getColor(R.color.c_DC3C23));
                titleView.setText(mTitles.get(index));
                titleView.setTextSize(13);
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
                        if (vp_live != null) {
                            vp_live.setCurrentItem(index);
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
                linePagerIndicator.setColors(getResources().getColor(R.color.c_DC3C23));
                return linePagerIndicator;
            }
        });
//        commonNavigator.setAdjustMode(true);
        vp_live.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (mCurrPosition != position) {
//                    mIndicatorAdapter.getItem(mCurrPosition).setSelected(false);
//                    mIndicatorAdapter.getItem(position).setSelected(true);
//                    mCurrPosition = position;
//                    mIndicatorAdapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        vp_live.setOffscreenPageLimit(4);
        vp_live.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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
        ViewPagerHelper.bind(magicIndicator, vp_live);
    }

    public void sendMessage(String nobleIcon, String expIcon, MessageInfo messageInfo) {
//        ((LiveChatFragment)mViewList.get(0)).updateAdapter(nobleIcon, expIcon, messageInfo);
        ((LiveChatFragment)mViewList.get(0)).updateAdapter(messageInfo);
    }

    public void showRedEnvelopeDialog() {
        ((LiveChatFragment)mViewList.get(0)).showRedEnvelopeDialog();
    }
}

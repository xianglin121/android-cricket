package com.onecric.live.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.Constant;
import com.onecric.live.R;
import com.onecric.live.adapter.ChannelNavigatorAdapter;
import com.onecric.live.adapter.ChannelPagerAdapter;
import com.onecric.live.custom.listener.OnChannelListener;
import com.onecric.live.fragment.dialog.ChannelDialogFragment;
import com.onecric.live.model.Channel;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.model.MatchSocketBean;
import com.onecric.live.presenter.match.HotMatchPresenter;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.match.HotMatchView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

public class HotMatchFragment extends MvpFragment<HotMatchPresenter> implements HotMatchView, OnChannelListener {
    public static HotMatchFragment newInstance() {
        HotMatchFragment fragment = new HotMatchFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private MagicIndicator mIndicator;
    private ChannelNavigatorAdapter mNavigatorAdapter;
    private List<Channel> mSelectedChannels = new ArrayList<>();
    private List<Channel> mUnSelectedFootballChannels = new ArrayList<>();
    private List<Channel> mUnSelectedBasketballChannels = new ArrayList<>();
    private ViewPager mViewPager;
    private ChannelPagerAdapter mPagerAdapter;
    private List<Fragment> mViewList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot_match;
    }

    @Override
    protected HotMatchPresenter createPresenter() {
        return new HotMatchPresenter(this);
    }

    @Override
    protected void initUI() {
        mIndicator = rootView.findViewById(R.id.indicator);
        mViewPager = rootView.findViewById(R.id.view_pager);
        rootView.findViewById(R.id.iv_manage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChannelDialogFragment dialogFragment = ChannelDialogFragment.newInstance(mSelectedChannels, mUnSelectedFootballChannels, mUnSelectedBasketballChannels);
                dialogFragment.setOnChannelListener(HotMatchFragment.this);
                dialogFragment.show(getChildFragmentManager(), "CHANNEL");
                dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (mPagerAdapter != null && mNavigatorAdapter != null) {
                            mPagerAdapter.notifyDataSetChanged();
                            mNavigatorAdapter.notifyDataSetChanged();
                            SpUtil.getInstance().setStringValue(Constant.SELECTED_CHANNEL_JSON, JSONObject.toJSONString(mSelectedChannels));
                            SpUtil.getInstance().setStringValue(Constant.UNSELECTED_FOOTBALL_CHANNEL_JSON, JSONObject.toJSONString(mUnSelectedFootballChannels));
                            SpUtil.getInstance().setStringValue(Constant.UNSELECTED_BASKETBALL_CHANNEL_JSON, JSONObject.toJSONString(mUnSelectedBasketballChannels));
//                        mViewPager.setOffscreenPageLimit(mSelectedChannels.size());
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        String selectedChannelJson = SpUtil.getInstance().getStringValue(Constant.SELECTED_CHANNEL_JSON);
        String unselectFootballChannel = SpUtil.getInstance().getStringValue(Constant.UNSELECTED_FOOTBALL_CHANNEL_JSON);
        String unselectBasketballChannel = SpUtil.getInstance().getStringValue(Constant.UNSELECTED_BASKETBALL_CHANNEL_JSON);
        if (TextUtils.isEmpty(selectedChannelJson) && TextUtils.isEmpty(unselectFootballChannel) && TextUtils.isEmpty(unselectBasketballChannel)) {
            mvpPresenter.getList();
        }else {
            List<Channel> selectedChannels = JSONObject.parseArray(selectedChannelJson, Channel.class);
            List<Channel> unSelectedFootballChannels = JSONObject.parseArray(unselectFootballChannel, Channel.class);
            List<Channel> unSelectedBasketballChannels = JSONObject.parseArray(unselectBasketballChannel, Channel.class);
            if (selectedChannels != null) {
                mSelectedChannels.addAll(selectedChannels);
            }
            if (unSelectedFootballChannels != null) {
                mUnSelectedFootballChannels.addAll(unSelectedFootballChannels);
            }
            if (unSelectedBasketballChannels != null) {
                mUnSelectedBasketballChannels.addAll(unSelectedBasketballChannels);
            }
            for (int i = 0; i < selectedChannels.size(); i++) {
                mViewList.add(HotMatchInnerFragment.newInstance(selectedChannels.get(i)));
            }
            initViewPager();
        }
    }

    @Override
    public void getDataSuccess(List<Channel> hotMatch, List<Channel> football, List<Channel> basketball) {
        if (hotMatch != null && hotMatch.size() > 0) {
            Channel one = new Channel();
            one.short_name_zh = WordUtil.getString(getActivity(), R.string.match_all);
            one.itemType = Channel.TYPE_MY_CHANNEL;
            one.selected = 1;
            hotMatch.add(0, one);
            mSelectedChannels.addAll(hotMatch);
            for (int i = 0; i < mSelectedChannels.size(); i++) {
                Channel channel = mSelectedChannels.get(i);
                channel.itemType = Channel.TYPE_MY_CHANNEL;
            }
            SpUtil.getInstance().setStringValue(Constant.SELECTED_CHANNEL_JSON, JSONObject.toJSONString(mSelectedChannels));
            if (football != null && football.size() > 0) {
                mUnSelectedFootballChannels.addAll(football);
                for (int i = 0; i < mUnSelectedFootballChannels.size(); i++) {
                    Channel channel = mUnSelectedFootballChannels.get(i);
                    channel.itemType = Channel.TYPE_MY_CHANNEL;
                }
                SpUtil.getInstance().setStringValue(Constant.UNSELECTED_FOOTBALL_CHANNEL_JSON, JSONObject.toJSONString(mUnSelectedFootballChannels));
            }
            if (basketball != null && basketball.size() > 0) {
                mUnSelectedBasketballChannels.addAll(basketball);
                for (int i = 0; i < mUnSelectedBasketballChannels.size(); i++) {
                    Channel channel = mUnSelectedBasketballChannels.get(i);
                    channel.itemType = Channel.TYPE_MY_CHANNEL;
                }
                SpUtil.getInstance().setStringValue(Constant.UNSELECTED_BASKETBALL_CHANNEL_JSON, JSONObject.toJSONString(mUnSelectedBasketballChannels));
            }
            for (int i = 0; i < hotMatch.size(); i++) {
                mViewList.add(HotMatchInnerFragment.newInstance(hotMatch.get(i)));
            }
            initViewPager();
//            Channel one = new Channel();
//            one.short_name_zh = WordUtil.getString(getActivity(), R.string.match_all);
//            one.itemType = Channel.TYPE_MY_CHANNEL;
//            mSelectedChannels.add(one);
//            Channel two = new Channel();
//            two.short_name_zh = "西甲";
//            two.setType(0);
//            two.itemType = Channel.TYPE_MY_CHANNEL;
//            mSelectedChannels.add(two);
//
//            Channel three = new Channel();
//            three.short_name_zh = "意甲";
//            three.setType(0);
//            three.itemType = Channel.TYPE_MY_CHANNEL;
//            mSelectedChannels.add(three);
//
//            Channel four = new Channel();
//            four.short_name_zh = "英超";
//            four.setType(0);
//            four.itemType = Channel.TYPE_MY_CHANNEL;
//            mSelectedChannels.add(four);
//
//            Channel five = new Channel();
//            five.short_name_zh = "德甲";
//            five.setType(0);
//            five.itemType = Channel.TYPE_MY_CHANNEL;
//            mSelectedChannels.add(five);
//
//            Channel six = new Channel();
//            six.short_name_zh = "欧冠";
//            six.setType(0);
//            six.itemType = Channel.TYPE_MY_CHANNEL;
//            mSelectedChannels.add(six);
//
//            Channel seven = new Channel();
//            seven.short_name_zh = "法甲";
//            seven.setType(0);
//            seven.itemType = Channel.TYPE_MY_CHANNEL;
//            mSelectedChannels.add(seven);
//
//            Channel eight = new Channel();
//            eight.short_name_zh = "NBA";
//            eight.setType(0);
//            eight.itemType = Channel.TYPE_MY_CHANNEL;
//            mSelectedChannels.add(eight);
//
//            Channel night = new Channel();
//            night.short_name_zh = "CBA";
//            night.setType(0);
//            night.itemType = Channel.TYPE_MY_CHANNEL;
//            mSelectedChannels.add(night);
//            mViewList = new ArrayList<>();
//            mViewList.add(HotMatchInnerFragment.newInstance(one));
//            mViewList.add(HotMatchInnerFragment.newInstance(two));
//            mViewList.add(HotMatchInnerFragment.newInstance(three));
//            mViewList.add(HotMatchInnerFragment.newInstance(four));
//            mViewList.add(HotMatchInnerFragment.newInstance(five));
//            mViewList.add(HotMatchInnerFragment.newInstance(six));
//            mViewList.add(HotMatchInnerFragment.newInstance(seven));
//            mViewList.add(HotMatchInnerFragment.newInstance(eight));
//            mViewList.add(HotMatchInnerFragment.newInstance(night));
//            initViewPager();
        }
    }

    private void initViewPager() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        mNavigatorAdapter = new ChannelNavigatorAdapter(mSelectedChannels);
        mNavigatorAdapter.setOnChannelChangeListener(new ChannelNavigatorAdapter.OnChannelChangeListener() {
            @Override
            public void onChange(int index) {
                mViewPager.setCurrentItem(index, false);
            }
        });
        commonNavigator.setAdapter(mNavigatorAdapter);
        mIndicator.setNavigator(commonNavigator);
        mPagerAdapter = new ChannelPagerAdapter(getChildFragmentManager(), mViewList);
        mViewPager.setOffscreenPageLimit(mViewList.size()-1);
        mViewPager.setAdapter(mPagerAdapter);
        ViewPagerHelper.bind(mIndicator, mViewPager);
    }

    public void updateData(MatchSocketBean dataBean) {
        if(mViewList != null) {
            for (int i = 0; i < mViewList.size(); i++) {
                ((HotMatchInnerFragment)mViewList.get(i)).updateData(dataBean);
            }
        }
    }

    public List<MatchListBean> getData() {
        List<MatchListBean> list = new ArrayList();
        if(mViewList != null) {
            for (int i = 0; i < mViewList.size(); i++) {
                if (i < mSelectedChannels.size()) {
                    if (mSelectedChannels.get(i).getType() == 0) {
                        list.addAll(((HotMatchInnerFragment)mViewList.get(i)).getData());
                    }
                }
            }
        }
        return list;
    }

//    public void updateTime() {
//        if (mViewList != null) {
//            for (int i = 0; i < mViewList.size(); i++) {
//                ((HotMatchInnerFragment)mViewList.get(i)).updateTime();
//            }
//        }
//    }

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

    @Override
    public void onItemMove(int starPos, int endPos) {
        listMove(mSelectedChannels, starPos, endPos);
        listMove(mViewList, starPos, endPos);
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMoveToMyChannel(int type, int starPos, int endPos) {
        //移动到我的频道
//        if (!TextUtils.isEmpty(type)) {
            Channel channel = null;
            if (type == 0) {
                channel = mUnSelectedFootballChannels.remove(starPos);
            } else if (type == 1) {
                channel = mUnSelectedBasketballChannels.remove(starPos);
            }
            mSelectedChannels.add(channel);

            mViewList.add(HotMatchInnerFragment.newInstance(channel));
            mPagerAdapter.notifyDataSetChanged();
//            mViewPager.setOffscreenPageLimit(mViewList.size()-1);
//        }
    }

    @Override
    public void onMoveToOtherChannel(int type, int starPos, int endPos) {
        //移动到推荐频道
//        mUnSelectedChannels.add(endPos, mSelectedChannels.remove(starPos));
//        if (!TextUtils.isEmpty(type)) {
            Channel startChannel = mSelectedChannels.remove(starPos);
            if (type == 0) {
                mUnSelectedFootballChannels.add(startChannel);
            }else if (type == 1) {
                mUnSelectedBasketballChannels.add(startChannel);
            }
            mViewList.remove(starPos);
            mPagerAdapter.notifyDataSetChanged();
//            mViewPager.setOffscreenPageLimit(mViewList.size());
//        }
    }

    private void listMove(List datas, int starPos, int endPos) {
        Object o = datas.get(starPos);
        //先删除之前的位置
        datas.remove(starPos);
        //添加到现在的位置
        datas.add(endPos, o);
    }
}

package com.longya.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.Constant;
import com.longya.live.R;
import com.longya.live.activity.BasketballMatchDataActivity;
import com.longya.live.activity.FootballMatchDataActivity;
import com.longya.live.adapter.ChannelAdapter;
import com.longya.live.adapter.MatchDataClassifyAdapter;
import com.longya.live.adapter.MatchDataFirstAdapter;
import com.longya.live.adapter.MatchDataFollowAdapter;
import com.longya.live.adapter.MatchDataSecondAdapter;
import com.longya.live.custom.listener.ItemDragHelperCallBack;
import com.longya.live.custom.listener.OnChannelDragListener;
import com.longya.live.event.UpdateMatchDataFollowEvent;
import com.longya.live.model.Channel;
import com.longya.live.model.JsonBean;
import com.longya.live.model.MatchDataClassifyBean;
import com.longya.live.model.MatchDataFirstBean;
import com.longya.live.model.MatchDataFollowBean;
import com.longya.live.model.MatchDataSecondBean;
import com.longya.live.presenter.match.MatchDataPresenter;
import com.longya.live.util.SpUtil;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.match.MatchDataView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/2/24
 */
public class MatchDataFragment extends MvpFragment<MatchDataPresenter> implements MatchDataView, OnChannelDragListener, View.OnClickListener {
    public static MatchDataFragment newInstance() {
        MatchDataFragment fragment = new MatchDataFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private List<Channel> mDatas = new ArrayList<>();
    private MatchDataFollowAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ItemTouchHelper mHelper;
    private TextView tv_football;
    private View line_football;
    private TextView tv_basketball;
    private View line_basketball;
    private RecyclerView rv_classify;
    private MatchDataClassifyAdapter mClassifyAdapter;
    private RecyclerView rv_match;
    private MatchDataFirstAdapter mFirstAdapter;
    private MatchDataSecondAdapter mSecondAdapter;

    private int type = 0;
    private int mClassifyId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match_data;
    }

    @Override
    protected MatchDataPresenter createPresenter() {
        return new MatchDataPresenter(this);
    }

    @Override
    protected void initUI() {
        mRecyclerView = findViewById(R.id.recyclerview);
        rv_classify = findViewById(R.id.rv_classify);
        rv_match = findViewById(R.id.rv_match);
        tv_football = findViewById(R.id.tv_football);
        line_football = findViewById(R.id.line_football);
        tv_basketball = findViewById(R.id.tv_basketball);
        line_basketball = findViewById(R.id.line_basketball);

        findViewById(R.id.ll_football).setOnClickListener(this);
        findViewById(R.id.ll_basketball).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mAdapter = new MatchDataFollowAdapter(mDatas);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mAdapter.mIsEdit) {
                    if (mAdapter.getItem(position).getType() == 0) {
                        FootballMatchDataActivity.forward(getContext(), mAdapter.getItem(position).sourceid);
                    }else {
                        BasketballMatchDataActivity.forward(getContext(), mAdapter.getItem(position).sourceid);
                    }
                }
            }
        });
        mAdapter.setOnEditModeListener(new MatchDataFollowAdapter.OnEditModeListener() {
            @Override
            public void switchEdit(boolean isEdit) {
                if (mSecondAdapter != null) {
                    mSecondAdapter.mIsEdit = isEdit;
                    mSecondAdapter.notifyDataSetChanged();
                }
                if (mFirstAdapter != null) {
                    mFirstAdapter.mIsEdit = isEdit;
                    mFirstAdapter.notifyDataSetChanged();
                }
            }
        });
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = mAdapter.getItemViewType(position);
                return itemViewType == Channel.TYPE_MY_CHANNEL || itemViewType == Channel.TYPE_OTHER_CHANNEL ? 1 : 4;
            }
        });
        ItemDragHelperCallBack callBack = new ItemDragHelperCallBack(this);
        mHelper = new ItemTouchHelper(callBack);
        mAdapter.setOnChannelDragListener(this);
        //attachRecyclerView
        mHelper.attachToRecyclerView(mRecyclerView);

        //获取关注列表
        String selectedJson = SpUtil.getInstance().getStringValue(Constant.MATCH_DATA_SELECTED);
        if (!TextUtils.isEmpty(selectedJson)) {
            mDatas = JSONObject.parseArray(selectedJson, Channel.class);
            mAdapter.setNewData(mDatas);
        }else {
            mvpPresenter.getFollowList();
        }
        //获取分类列表
        mvpPresenter.getClassifyList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_football:
                if (type == 0) {
                    return;
                }
                type = 0;
                tv_football.setTextColor(getResources().getColor(R.color.c_E3AC72));
                line_football.setVisibility(View.VISIBLE);
                tv_basketball.setTextColor(getResources().getColor(R.color.c_666666));
                line_basketball.setVisibility(View.GONE);
                refreshData();
                break;
            case R.id.ll_basketball:
                if (type == 1) {
                    return;
                }
                type = 1;
                tv_football.setTextColor(getResources().getColor(R.color.c_666666));
                line_football.setVisibility(View.GONE);
                tv_basketball.setTextColor(getResources().getColor(R.color.c_E3AC72));
                line_basketball.setVisibility(View.VISIBLE);
                refreshData();
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
    public void getFollowListSuccess(List<MatchDataFollowBean> list) {
        Channel one = new Channel();
        one.short_name_zh = "关注赛事";
        one.itemType = Channel.TYPE_MY;
        mDatas.add(one);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Channel channel = new Channel();
                channel.sourceid = list.get(i).getId();
                channel.short_name_zh = list.get(i).getName();
                channel.setType(list.get(i).getType());
                channel.setLogoUrl(list.get(i).getLogo());
                channel.itemType = Channel.TYPE_MY_CHANNEL;
                mDatas.add(channel);
            }
        }
        mAdapter.setNewData(mDatas);
        SpUtil.getInstance().setStringValue(Constant.MATCH_DATA_SELECTED, JSONObject.toJSONString(mDatas));
    }

    @Override
    public void getClassifyListSuccess(List<MatchDataClassifyBean> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (list.size() > 0) {
            list.get(0).setSelect(true);
            mClassifyId = list.get(0).getId();
            refreshData();
        }
        mClassifyAdapter = new MatchDataClassifyAdapter(R.layout.item_match_data_classify, list);
        mClassifyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<MatchDataClassifyBean> data = mClassifyAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (i == position) {
                        data.get(i).setSelect(true);
                    }else {
                        data.get(i).setSelect(false);
                    }
                }
                mClassifyId = data.get(position).getId();
                mClassifyAdapter.notifyDataSetChanged();
                refreshData();
            }
        });
        rv_classify.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rv_classify.setAdapter(mClassifyAdapter);
    }

    public void refreshData() {
        if (mClassifyId == 1) {
            if (type == 0) {
                mvpPresenter.getFootBallList(0, 0, mClassifyId);
            }else {
                mvpPresenter.getBasketBallList(0, 0, mClassifyId);
            }
        }else {
            mvpPresenter.getCountryList(mClassifyId);
        }
    }

    @Override
    public void getCountryListSuccess(List<MatchDataFirstBean> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        mFirstAdapter = new MatchDataFirstAdapter(R.layout.item_match_data_first, list);
        mFirstAdapter.setOnChildClickListener(new MatchDataFirstAdapter.OnChildClickListener() {
            @Override
            public void onClick(int parentPosition, int childPosition) {
                MatchDataSecondBean matchDataSecondBean = mFirstAdapter.getItem(parentPosition).getList().get(childPosition);
                if (mFirstAdapter.mIsEdit) {
                    Integer temp = null;
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).short_name_zh.equals(matchDataSecondBean.getName())) {
                            temp = i;
                        }
                    }
                    if (temp != null) {
                        matchDataSecondBean.setFollow(false);
                        mDatas.remove(temp.intValue());
                    }else {
                        matchDataSecondBean.setFollow(true);
                        Channel channel = new Channel();
                        channel.sourceid = matchDataSecondBean.getId();
                        channel.short_name_zh = matchDataSecondBean.getName();
                        channel.setType(type);
                        channel.setLogoUrl(matchDataSecondBean.getLogo());
                        channel.itemType = Channel.TYPE_MY_CHANNEL;
                        mDatas.add(channel);
                    }
                    mFirstAdapter.notifyItemChanged(parentPosition);
                    mAdapter.notifyDataSetChanged();
                    SpUtil.getInstance().setStringValue(Constant.MATCH_DATA_SELECTED, JSONObject.toJSONString(mDatas));
                }else {
                    if (type == 0) {
                        FootballMatchDataActivity.forward(getContext(), matchDataSecondBean.getId());
                    }else {
                        BasketballMatchDataActivity.forward(getContext(), matchDataSecondBean.getId());
                    }
                }
            }
        });
        mFirstAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MatchDataFirstBean item = mFirstAdapter.getItem(position);
                item.setExpand(!item.isExpand());
                if (item.isExpand()) {
                    if (item.getList() == null) {
                        if (type == 0) {
                            mvpPresenter.getFootBallList(position, item.getId(), mClassifyId);
                        }else {
                            mvpPresenter.getBasketBallList(position, item.getId(), mClassifyId);
                        }
                    }else {
                        if (mFirstAdapter.getItem(position).getList().size() == 0) {
                            if (type == 0) {
                                mvpPresenter.getFootBallList(position, item.getId(), mClassifyId);
                            }else {
                                mvpPresenter.getBasketBallList(position, item.getId(), mClassifyId);
                            }
                        }
                    }
                }
                mFirstAdapter.notifyItemChanged(position);
            }
        });
        rv_match.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_match.setAdapter(mFirstAdapter);
    }

    @Override
    public void getListSuccess(int position, List<MatchDataSecondBean> list) {
        for (int i = 0; i < list.size(); i++) {
            MatchDataSecondBean bean = list.get(i);
            for (int j = 0; j < mDatas.size(); j++) {
                if (mDatas.get(j).short_name_zh.equals(bean.getName_zh())) {
                    bean.setFollow(true);
                    break;
                }
            }
        }
        if (mClassifyId == 1) {
            mSecondAdapter = new MatchDataSecondAdapter(R.layout.item_match_data_second, list);
            mSecondAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (mSecondAdapter.mIsEdit) {
                        Integer temp = null;
                        for (int i = 0; i < mDatas.size(); i++) {
                            if (mDatas.get(i).short_name_zh.equals(mSecondAdapter.getItem(position).getName_zh())) {
                                temp = i;
                            }
                        }
                        if (temp != null) {
                            mSecondAdapter.getItem(position).setFollow(false);
                            mDatas.remove(temp.intValue());
                        }else {
                            mSecondAdapter.getItem(position).setFollow(true);
                            Channel channel = new Channel();
                            channel.sourceid = mSecondAdapter.getItem(position).getId();
                            channel.short_name_zh = mSecondAdapter.getItem(position).getName();
                            channel.setType(type);
                            channel.setLogoUrl(mSecondAdapter.getItem(position).getLogo());
                            channel.itemType = Channel.TYPE_MY_CHANNEL;
                            mDatas.add(channel);
                        }
                        mSecondAdapter.notifyItemChanged(position);
                        mAdapter.notifyDataSetChanged();
                        SpUtil.getInstance().setStringValue(Constant.MATCH_DATA_SELECTED, JSONObject.toJSONString(mDatas));
                    }else {
                        if (type == 0) {
                            FootballMatchDataActivity.forward(getContext(), mSecondAdapter.getItem(position).getId());
                        }else {
                            BasketballMatchDataActivity.forward(getContext(), mSecondAdapter.getItem(position).getId());
                        }
                    }
                }
            });
            rv_match.setLayoutManager(new LinearLayoutManager(getContext()));
            rv_match.setAdapter(mSecondAdapter);
        }else {
            if (list != null) {
                if (mFirstAdapter != null) {
                    mFirstAdapter.getItem(position).setList(list);
                    mFirstAdapter.notifyItemChanged(position);
                }
            }
        }
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onStarDrag(BaseViewHolder baseViewHolder) {
        mHelper.startDrag(baseViewHolder);
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
        onMove(starPos, endPos);
        SpUtil.getInstance().setStringValue(Constant.MATCH_DATA_SELECTED, JSONObject.toJSONString(mDatas));
    }

    private void onMove(int starPos, int endPos) {
        Channel startChannel = mDatas.get(starPos);
        //先删除之前的位置
        mDatas.remove(starPos);
        //添加到现在的位置
        mDatas.add(endPos, startChannel);
        mAdapter.notifyItemMoved(starPos, endPos);
    }

    @Override
    public void onMoveToMyChannel(int type, int starPos, int endPos) {

    }

    @Override
    public void onMoveToOtherChannel(int type, int starPos, int endPos) {
        mDatas.remove(starPos);
        mAdapter.notifyItemRemoved(starPos);
        SpUtil.getInstance().setStringValue(Constant.MATCH_DATA_SELECTED, JSONObject.toJSONString(mDatas));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMatchFollowEvent(UpdateMatchDataFollowEvent event) {
        if (event != null) {
            if (mClassifyId == 1) {
                if (event.type == type) {
                    MatchDataSecondBean bean = null;
                    int position = 0;
                    for (int i = 0; i < mSecondAdapter.getData().size(); i++) {
                        if (event.id == mSecondAdapter.getItem(i).getId()) {
                            bean = mSecondAdapter.getItem(i);
                            position = i;
                            break;
                        }
                    }
                    Integer temp = null;
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).short_name_zh.equals(bean.getName_zh())) {
                            temp = i;
                        }
                    }
                    if (temp != null) {
                        bean.setFollow(false);
                        mDatas.remove(temp.intValue());
                    }else {
                        bean.setFollow(true);
                        Channel channel = new Channel();
                        channel.sourceid = bean.getId();
                        channel.short_name_zh = bean.getName();
                        channel.setType(type);
                        channel.setLogoUrl(bean.getLogo());
                        channel.itemType = Channel.TYPE_MY_CHANNEL;
                        mDatas.add(channel);
                    }
                    mSecondAdapter.notifyItemChanged(position);
                }else {
                    Integer temp = null;
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).sourceid == event.id && mDatas.get(i).getType() == event.type) {
                            temp = i;
                        }
                    }
                    mDatas.remove(temp.intValue());
                }
            }else {
                if (event.type == type) {
                    MatchDataSecondBean bean = null;
                    int position = 0;
                    for (int i = 0; i < mFirstAdapter.getData().size(); i++) {
                        if (mFirstAdapter.getItem(i).getList() != null) {
                            for (int j = 0; j < mFirstAdapter.getItem(i).getList().size(); j++) {
                                if (event.id == mFirstAdapter.getItem(i).getList().get(j).getId()) {
                                    bean = mFirstAdapter.getItem(i).getList().get(j);
                                    position = i;
                                    break;
                                }
                            }
                        }
                    }
                    Integer temp = null;
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).short_name_zh.equals(bean.getName_zh())) {
                            temp = i;
                        }
                    }
                    if (temp != null) {
                        bean.setFollow(false);
                        mDatas.remove(temp.intValue());
                    }else {
                        bean.setFollow(true);
                        Channel channel = new Channel();
                        channel.sourceid = bean.getId();
                        channel.short_name_zh = bean.getName();
                        channel.setType(type);
                        channel.setLogoUrl(bean.getLogo());
                        channel.itemType = Channel.TYPE_MY_CHANNEL;
                        mDatas.add(channel);
                    }
                    mFirstAdapter.notifyItemChanged(position);
                }else {
                    Integer temp = null;
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).sourceid == event.id && mDatas.get(i).getType() == event.type) {
                            temp = i;
                        }
                    }
                    mDatas.remove(temp.intValue());
                }
            }
            mAdapter.notifyDataSetChanged();
            SpUtil.getInstance().setStringValue(Constant.MATCH_DATA_SELECTED, JSONObject.toJSONString(mDatas));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.Constant;
import com.onecric.live.R;
import com.onecric.live.activity.BasketballMatchDetailActivity;
import com.onecric.live.activity.CommunityCommentActivity;
import com.onecric.live.activity.FootballMatchDetailActivity;
import com.onecric.live.activity.HeadlineDetailActivity;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.SearchLiveDetailActivity;
import com.onecric.live.adapter.LiveClassifyAdapter;
import com.onecric.live.adapter.LiveRecommendAdapter;
import com.onecric.live.adapter.MyFollowAdapter;
import com.onecric.live.adapter.SearchHeadlineAdapter;
import com.onecric.live.adapter.ThemeCommunityAdapter;
import com.onecric.live.adapter.decoration.GridDividerItemDecoration;
import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.HeadlineBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.live.SearchComplexPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.SearchComplexView;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/25
 */
public class SearchComplexFragment extends MvpFragment<SearchComplexPresenter> implements SearchComplexView, View.OnClickListener {

    public static SearchComplexFragment newInstance(String content) {
        SearchComplexFragment fragment = new SearchComplexFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mContent;
    private RecyclerView rv_anchor;
    private MyFollowAdapter mAnchorAdapter;
    private RecyclerView rv_live;
    private LiveRecommendAdapter mLiveAdapter;
    private RecyclerView rv_match;
    private LiveClassifyAdapter mMatchAdapter;
    private RecyclerView rv_headline;
    private SearchHeadlineAdapter mHeadlineAdapter;
    private RecyclerView rv_community;
    private ThemeCommunityAdapter mCommunityAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_complex;
    }

    @Override
    protected SearchComplexPresenter createPresenter() {
        return new SearchComplexPresenter(this);
    }

    @Override
    public void initUI() {
        mContent = getArguments().getString("content");
        rv_anchor = findViewById(R.id.rv_anchor);
        rv_live = findViewById(R.id.rv_live);
        rv_match = findViewById(R.id.rv_match);
        rv_headline = findViewById(R.id.rv_headline);
        rv_community = findViewById(R.id.rv_community);

        findViewById(R.id.tv_more_anchor).setOnClickListener(this);
        findViewById(R.id.tv_more_live).setOnClickListener(this);
        findViewById(R.id.tv_more_match).setOnClickListener(this);
        findViewById(R.id.tv_more_headline).setOnClickListener(this);
        findViewById(R.id.tv_more_community).setOnClickListener(this);
    }

    @Override
    public void initData() {
        //主播
        mAnchorAdapter = new MyFollowAdapter(R.layout.item_my_follow_inner, new ArrayList<>());
        mAnchorAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_follow) {
                    UserBean item = mAnchorAdapter.getItem(position);
                    if (item.getAttention() == 0) {
                        item.setAttention(1);
                        mAnchorAdapter.notifyItemChanged(position);
                        mvpPresenter.doFollow(item.getId());
                    }
                }
            }
        });
        rv_anchor.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_anchor.setAdapter(mAnchorAdapter);

        //直播
        mLiveAdapter = new LiveRecommendAdapter(R.layout.item_live_recommend, new ArrayList<>());
        mLiveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(mLiveAdapter.getItem(position).getIslive() == 0){
                    ToastUtil.show("The broadcast has not started");
                }else{
                    LiveDetailActivity.forward(getContext(), mLiveAdapter.getItem(position).getUid(), mLiveAdapter.getItem(position).getType(), mLiveAdapter.getItem(position).getMatch_id());
                }
            }
        });
        rv_live.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_live.addItemDecoration(new GridDividerItemDecoration(getContext(), 10, 2));
        rv_live.setAdapter(mLiveAdapter);
        //赛程
        mMatchAdapter = new LiveClassifyAdapter(new ArrayList<>());
        mMatchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mMatchAdapter.getItem(position).getType() == 0) {
                    FootballMatchDetailActivity.forward(getContext(), mMatchAdapter.getItem(position).getSourceid());
                }else if (mMatchAdapter.getItem(position).getType() == 1) {
                    BasketballMatchDetailActivity.forward(getContext(), mMatchAdapter.getItem(position).getSourceid());
                }
            }
        });
        mMatchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_state) {
                    if (mMatchAdapter.getItem(position).getStatus_type() == 0 && mMatchAdapter.getItem(position).getReserve() == 0) {
                        mvpPresenter.doReserve(position, mMatchAdapter.getItem(position).getSourceid(), mMatchAdapter.getItem(position).getType());
                    }
                }
            }
        });
        rv_match.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_match.setAdapter(mMatchAdapter);
        //头条
        mHeadlineAdapter = new SearchHeadlineAdapter(R.layout.item_search_headline, new ArrayList<>());
        mHeadlineAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HeadlineDetailActivity.forward(getContext(), mHeadlineAdapter.getItem(position).getId());
            }
        });
        rv_headline.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_headline.setAdapter(mHeadlineAdapter);
        //社区
        mCommunityAdapter = new ThemeCommunityAdapter(R.layout.item_theme_community, new ArrayList<>());
        mCommunityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommunityCommentActivity.forward(getContext(), mCommunityAdapter.getItem(position).getUid(), mCommunityAdapter.getItem(position).getId());
            }
        });
        mCommunityAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_like) {
                    CommunityBean item = mCommunityAdapter.getItem(position);
                    int like = item.getLikes();
                    if (item.getIs_likes() == 1) {
                        like--;
                        item.setIs_likes(0);
                    }else {
                        like++;
                        item.setIs_likes(1);
                    }
                    item.setLikes(like);
                    mCommunityAdapter.notifyItemChanged(position, Constant.PAYLOAD);
                    mvpPresenter.doCommunityLike(item.getId());
                }
            }
        });
        rv_community.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_community.setAdapter(mCommunityAdapter);

        mvpPresenter.getList(true, mContent);
    }

    public void updateData(String content) {
        if (!TextUtils.isEmpty(content)) {
            mContent = content;
            if (mvpPresenter != null) {
                mvpPresenter.getList(true, mContent);
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<UserBean> anchorList, List<LiveBean> liveList, List<MatchListBean> matchList, List<HeadlineBean> headlineList, List<CommunityBean> communityList) {
        if (anchorList != null) {
            mAnchorAdapter.setNewData(anchorList);
        }
        if (liveList != null) {
            mLiveAdapter.setNewData(liveList);
        }
        if (matchList != null) {
            mMatchAdapter.setNewData(matchList);
        }
        if (headlineList != null) {
            mHeadlineAdapter.setNewData(headlineList);
        }
        if (communityList != null) {
            mCommunityAdapter.setNewData(communityList);
        }
    }

    @Override
    public void doFollowSuccess(int id) {
    }

    @Override
    public void doReserveSuccess(int position) {
        MatchListBean item = mMatchAdapter.getItem(position);
        if (item.getReserve() == 0) {
            item.setReserve(1);
        }else {
            item.setReserve(0);
        }
        mMatchAdapter.notifyItemChanged(position);
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_more_anchor:
                ((SearchLiveDetailActivity)getActivity()).switchPage(1);
                break;
            case R.id.tv_more_live:
                ((SearchLiveDetailActivity)getActivity()).switchPage(2);
                break;
            case R.id.tv_more_match:
                ((SearchLiveDetailActivity)getActivity()).switchPage(3);
                break;
            case R.id.tv_more_headline:
                ((SearchLiveDetailActivity)getActivity()).switchPage(4);
                break;
            case R.id.tv_more_community:
                ((SearchLiveDetailActivity)getActivity()).switchPage(5);
                break;
        }
    }
}

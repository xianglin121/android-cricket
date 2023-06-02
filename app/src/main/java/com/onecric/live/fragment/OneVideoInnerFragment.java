package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.OneLogInActivity;
import com.onecric.live.activity.OneVideoDetailActivity;
import com.onecric.live.adapter.ShowVideoAdapter;
import com.onecric.live.adapter.VideoCategoryAdapter;
import com.onecric.live.adapter.VideoInnerAdapter;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.OneVideoBean;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.model.VideoCategoryBean;
import com.onecric.live.model.VideoShowBean;
import com.onecric.live.presenter.video.VideoPresenter;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.video.VideoView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;

public class OneVideoInnerFragment extends MvpFragment<VideoPresenter> implements VideoView {
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_recommend_video,rv_show,rv_other;
    private ConstraintLayout cl_top;
    private ImageView iv_bg;
    private TextView tv_top_into,tv_top_title,tv_show_info,tv_other_info;

    private VideoCategoryAdapter mVideoAdapter;
    private ShowVideoAdapter mAdapter;
    private VideoInnerAdapter mOtherAdapter;
    private int tId;

    public static OneVideoInnerFragment newInstance(int id) {
        OneVideoInnerFragment fragment = new OneVideoInnerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tId", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_inner;
    }

    @Override
    protected VideoPresenter createPresenter() {
        return new VideoPresenter(this);
    }

    @Override
    protected void initUI() {
        tId = getArguments().getInt("tId",0);
        smart_rl = findViewById(R.id.smart_rl);
        rv_recommend_video = findViewById(R.id.rv_recommend_video);
        rv_show = findViewById(R.id.rv_show);
        rv_other = findViewById(R.id.rv_other);
        cl_top = findViewById(R.id.cl_top);
        iv_bg = findViewById(R.id.iv_bg);
//        tv_top_into = findViewById(R.id.tv_top_into);
        tv_top_title = findViewById(R.id.tv_top_title);
        tv_show_info = findViewById(R.id.tv_show_info);
        tv_other_info = findViewById(R.id.tv_other_info);

        int width = UIUtil.getScreenWidth(getContext());
        android.view.ViewGroup.LayoutParams pp = iv_bg.getLayoutParams();
        pp.height = (int)(width * 0.78);
        iv_bg.setLayoutParams(pp);


    }

    @Override
    protected void initData() {
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setEnableLoadMore(false);
        smart_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if(mVideoAdapter.getItemCount()<=0){
                    findViewById(R.id.view_loading).setVisibility(View.VISIBLE);
                }
                mvpPresenter.getOneList(tId);
            }
        });

        mAdapter = new ShowVideoAdapter(R.layout.item_show_img, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                    OneLogInActivity.forward(getContext());
                }else{
                    OneVideoDetailActivity.forward(getContext(),"",mAdapter.getItem(position).id);
                }
            }
        });
        rv_show.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_show.setAdapter(mAdapter);

        mVideoAdapter = new VideoCategoryAdapter(R.layout.item_live_video, new ArrayList<>());
        rv_recommend_video.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_recommend_video.setAdapter(mVideoAdapter);

        mOtherAdapter = new VideoInnerAdapter(R.layout.item_video_inner,"", new ArrayList<>());
        mOtherAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mOtherAdapter.getItem(position).id == 0) {
                    ToastUtil.show(getString(R.string.stay_tuned_for_broadcasts));
                    return;
                }
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0) {
                    OneLogInActivity.forward(getContext());
                } else {
                    OneVideoDetailActivity.forward(getContext(),"",mOtherAdapter.getItem(position).id);
                }
            }
        });
        rv_other.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_other.setAdapter(mOtherAdapter);

        smart_rl.autoRefresh();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<ShortVideoBean> list) {

    }

    @Override
    public void getCategorySuccess(List<VideoCategoryBean> list) {

    }

    @Override
    public void getInnerDataSuccess(List<OneVideoBean.FirstCategoryBean> tList, List<OneVideoBean.SecondCategoryBean> othersList, List<VideoShowBean> showsList,OneVideoBean.BannerBean banner) {
        smart_rl.finishRefresh();
        findViewById(R.id.view_loading).setVisibility(View.GONE);
        //有无置顶banner
        if(banner != null && banner.flie.size()>0){
            Glide.with(getContext()).load(banner.flie.get(0).img).priority(Priority.HIGH).into(iv_bg);
            cl_top.setVisibility(View.VISIBLE);
            cl_top.setOnClickListener(v -> {
                OneVideoDetailActivity.forward(getContext(),"",banner.id);
            });
            tv_top_title.setText(banner.title);
        }else{
            cl_top.setVisibility(View.GONE);
        }

        //分类列表
        if (tList != null && tList.size() > 0) {
            mVideoAdapter.setNewData(tList);
            rv_recommend_video.setVisibility(View.VISIBLE);
        }else{
            rv_recommend_video.setVisibility(View.GONE);
        }

        //Others
        if (othersList != null && othersList.size() > 0) {
            mOtherAdapter.getData().clear();
            mOtherAdapter.getData().addAll(othersList);
//            mOtherAdapter.notifyItemInserted(othersList.size());
            tv_other_info.setVisibility(View.VISIBLE);
            rv_other.setVisibility(View.VISIBLE);
        } else {
            mOtherAdapter.setNewData(new ArrayList<>());
            rv_other.setVisibility(View.GONE);
            tv_other_info.setVisibility(View.GONE);
        }

        //有无配置视频
        if (showsList != null && showsList.size() > 0) {
            mAdapter.getData().clear();
            mAdapter.getData().addAll(showsList);
            tv_show_info.setVisibility(View.VISIBLE);
            rv_show.setVisibility(View.VISIBLE);
        } else {
            mAdapter.setNewData(new ArrayList<>());
            rv_show.setVisibility(View.GONE);
            tv_show_info.setVisibility(View.GONE);
        }
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {
        smart_rl.finishRefresh();
        findViewById(R.id.view_loading).setVisibility(View.GONE);
        ToastUtil.show(msg);
    }

}

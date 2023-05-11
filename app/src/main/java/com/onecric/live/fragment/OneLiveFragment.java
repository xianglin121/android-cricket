package com.onecric.live.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.onecric.live.R;
import com.onecric.live.activity.HeadlineDetailActivity;
import com.onecric.live.adapter.BannerRoundLiveImageAdapter;
import com.onecric.live.model.HeadlineBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveFiltrateBean;
import com.onecric.live.presenter.live.OneLivePresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.OneLiveView;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.listener.OnBannerListener;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class OneLiveFragment extends MvpFragment<OneLivePresenter> implements OneLiveView, View.OnClickListener{
    private LinearLayout view_loading;
    private Banner mBanner;
    private TextView tv_view_all,tv_recommend_name,tv_see_all;
    private RecyclerView rv_match,rv_author_live,rv_recommend_match;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_view_all:
                //match 正在直播

                break;
            case R.id.tv_see_all:
                //match 推荐联赛直播

                break;
        }
    }

    private void initBanner(List<HeadlineBean> banners) {
        if (banners.size() == 0) {
            mBanner.setVisibility(View.GONE);
        }
        mBanner.setIndicator(new RectangleIndicator(getContext()));
        mBanner.setAdapter(new BannerRoundLiveImageAdapter(banners) {
            @Override
            public void onBindView(Object holder, Object data, int position, int size) {
                Glide.with(getContext()).load(((HeadlineBean)data).getImg()).into(((BannerRoundLiveImageHolder)holder).imageView);
                if (!TextUtils.isEmpty(((HeadlineBean)data).getTitle())) {
                    ((BannerRoundLiveImageHolder)holder).textView.setText(((HeadlineBean)data).getTitle());
                }else {
                    ((BannerRoundLiveImageHolder)holder).textView.setText("");
                }
            }
        });
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                HeadlineDetailActivity.forward(getContext(), ((HeadlineBean)data).getId());
            }
        });
        mBanner.addBannerLifecycleObserver(this);
    }

    @Override
    protected void initUI() {
        view_loading = findViewById(R.id.view_loading);
        mBanner = findViewById(R.id.banner_live);
        tv_view_all = findViewById(R.id.tv_view_all);
        tv_recommend_name = findViewById(R.id.tv_recommend_name);
        tv_see_all = findViewById(R.id.tv_see_all);
        rv_match = findViewById(R.id.rv_match);
        rv_author_live = findViewById(R.id.rv_author_live);
        rv_recommend_match = findViewById(R.id.rv_recommend_match);

        //预加载 view_loading

        //banner_live
        int width = UIUtil.getScreenWidth(getContext());
        android.view.ViewGroup.LayoutParams pp = mBanner.getLayoutParams();
        pp.height = (int)(width * 0.78);
        mBanner.setLayoutParams(pp);

        //rv


    }

    @Override
    protected void initData() {
        //数据

    }

    public void updateUserInfo() {

    }

    @Override
    public void getOtherDataSuccess(List<LiveFiltrateBean> list) {

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one_live;
    }

    @Override
    protected OneLivePresenter createPresenter() {
        return new OneLivePresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null) {
            mBanner.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBanner != null) {
            mBanner.destroy();
        }
    }
}

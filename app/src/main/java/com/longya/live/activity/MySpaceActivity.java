package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.longya.live.CommonAppConfig;
import com.longya.live.R;
import com.longya.live.fragment.MySpaceCommunityFragment;
import com.longya.live.fragment.MySpaceHeadlineFragment;
import com.longya.live.fragment.MySpaceVideoFragment;
import com.longya.live.model.JsonBean;
import com.longya.live.model.UserBean;
import com.longya.live.presenter.user.MySpacePresenter;
import com.longya.live.util.GlideUtil;
import com.longya.live.view.BaseActivity;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.user.MySpaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/10
 */
public class MySpaceActivity extends MvpActivity<MySpacePresenter> implements MySpaceView, View.OnClickListener {
    public static void forward(Context context, int uid) {
        Intent intent = new Intent(context, MySpaceActivity.class);
        intent.putExtra("uid", uid);
        context.startActivity(intent);
    }

    private int mUid;
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_count;
    private TextView tv_content;
    private ImageView indicator_headline;
    private ImageView indicator_community;
    private ImageView indicator_video;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;

    @Override
    protected MySpacePresenter createPresenter() {
        return new MySpacePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_space;
    }

    @Override
    protected void initView() {
        mUid = getIntent().getIntExtra("uid", 0);
        mViewList = new ArrayList<>();
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_count = findViewById(R.id.tv_count);
        tv_content = findViewById(R.id.tv_content);
        indicator_headline = findViewById(R.id.indicator_headline);
        indicator_community = findViewById(R.id.indicator_community);
        indicator_video = findViewById(R.id.indicator_video);
        mViewPager = findViewById(R.id.viewpager);

        findViewById(R.id.ll_headline).setOnClickListener(this);
        findViewById(R.id.ll_community).setOnClickListener(this);
        findViewById(R.id.ll_video).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //初始化viewpager
        mViewList.add(MySpaceHeadlineFragment.newInstance(mUid));
        mViewList.add(MySpaceCommunityFragment.newInstance(mUid));
        mViewList.add(MySpaceVideoFragment.newInstance(mUid));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    indicator_headline.setVisibility(View.VISIBLE);
                    indicator_community.setVisibility(View.GONE);
                    indicator_video.setVisibility(View.GONE);
                }else if (i == 1) {
                    indicator_headline.setVisibility(View.GONE);
                    indicator_community.setVisibility(View.VISIBLE);
                    indicator_video.setVisibility(View.GONE);
                }else if (i == 2) {
                    indicator_headline.setVisibility(View.GONE);
                    indicator_community.setVisibility(View.GONE);
                    indicator_video.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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
        mViewPager.setOffscreenPageLimit(mViewList.size());

        mvpPresenter.getUserInfo(mUid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_headline:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.ll_community:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.ll_video:
                mViewPager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void getDataSuccess(UserBean model) {
        if (model != null) {
            GlideUtil.loadUserImageDefault(this, model.getAvatar(), iv_avatar);
            if (!TextUtils.isEmpty(model.getUser_nickname())) {
                tv_name.setText(model.getUser_nickname());
            }
            tv_count.setText(getString(R.string.follows) + model.getAttention_num() + " | " +getString(R.string.fans)+ model.getAttention());
            if (!TextUtils.isEmpty(model.getSignature())) {
                tv_content.setText(model.getSignature());
            }
        }
    }

    @Override
    public void getDataFail(String msg) {

    }
}

package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.tabs.TabLayout;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.fragment.PersonalVideoFragment;
import com.onecric.live.fragment.PersonalPostFragment;
import com.onecric.live.fragment.ThemeFragment;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.user.PersonalHomepagePresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.user.PersonalHomepageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalHomepageActivity extends MvpActivity<PersonalHomepagePresenter> implements PersonalHomepageView, View.OnClickListener {

    private CircleImageView head_pic;
    private TextView anchor_num;
    private TextView author_num;
    private TextView fans_num;
    private TextView user_name;
    private TextView user_profile;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;
    private String id;
    private View ll_follow;
    private ImageView iv_icon;
    private TextView tv_follow;


    public static void forward(Context context, String id) {
        Intent intent = new Intent(context, PersonalHomepageActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_homepage;
    }

    @Override
    public boolean getStatusBarTextColor() {
        return false;
    }


    @Override
    protected void initView() {
        id = getIntent().getStringExtra("id");
        ll_follow = findViewById(R.id.ll_follow);
        if (id.equals(CommonAppConfig.getInstance().getUid())) {
            ll_follow.setVisibility(View.GONE);
        }
        iv_icon = findViewById(R.id.iv_icon);
        tv_follow = findViewById(R.id.tv_follow);
        head_pic = findViewById(R.id.person_head_pic);
        user_name = findViewById(R.id.tv_user_name);
        user_profile = findViewById(R.id.tv_user_profile);
        findViewById(R.id.ll_follow).setOnClickListener(this);
        head_pic.setOnClickListener(this);
        anchor_num = findViewById(R.id.follow_the_anchor);
        author_num = findViewById(R.id.follow_the_author);
        fans_num = findViewById(R.id.fans);
        tabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.post)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.videos)));

        mViewList = new ArrayList<>();
//        mViewList.add(PersonalPostFragment.newInstance(id));
        mViewList.add(PersonalVideoFragment.newInstance(id));

        initViewPager();
    }


    private void initViewPager() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //初始化viewpager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.getTabAt(i).select();
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
    }


    @Override
    protected void initData() {
        mvpPresenter.getUserInfo(id);
    }

    @Override
    protected PersonalHomepagePresenter createPresenter() {
        return new PersonalHomepagePresenter(this);
    }

    @Override
    public void getDataSuccess(UserBean userBean) {
        if (userBean != null) {
            if (userBean.isIs_attention() == 1) {
                ll_follow.setBackgroundColor(getResources().getColor(R.color.c_D5D5D5));
                tv_follow.setText(getString(R.string.followed));
                iv_icon.setVisibility(View.GONE);
            } else {
                ll_follow.setBackgroundResource(R.mipmap.bg_live_follow);
                tv_follow.setText(getString(R.string.follow));
                iv_icon.setVisibility(View.VISIBLE);
            }
            GlideUtil.loadUserImageDefault(this, userBean.getAvatar(), head_pic);
            if (!TextUtils.isEmpty(userBean.getUser_nickname())) {
                user_name.setText(userBean.getUser_nickname());
            } else {
                user_name.setText("");
            }
            if (!TextUtils.isEmpty(userBean.getSignature())) {
                user_profile.setText(userBean.getSignature());
            } else {
                user_profile.setText("");
            }
            anchor_num.setText(userBean.getFollow_the_anchor() + "");
            author_num.setText(userBean.getFollow_the_author() + "");
            fans_num.setText(userBean.getFans() + "");
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_follow:
                mvpPresenter.doFollow(Integer.parseInt(id));
                break;
            case R.id.person_head_pic:
                if (id.equals(CommonAppConfig.getInstance().getUid())) {
                    UserInfoActivity.forward(mActivity);
                }
                break;
        }
    }

    @Override
    public void doFollowSuccess(int id) {
        ll_follow.setBackgroundColor(getResources().getColor(R.color.c_D5D5D5));
        tv_follow.setText(getString(R.string.followed));
        iv_icon.setVisibility(View.GONE);
    }
}

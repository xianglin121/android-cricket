package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.R;
import com.longya.live.adapter.IndexDetailAdapter;
import com.longya.live.adapter.IndexDetailCompanyAdapter;
import com.longya.live.fragment.RangqiuIndexDetailFragment;
import com.longya.live.model.BasketballIndexListBean;
import com.longya.live.model.IndexBean;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.match.RangqiuIndexDetailPresenter;
import com.longya.live.util.ToastUtil;
import com.longya.live.util.WordUtil;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.match.RangqiuIndexDetailView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RangqiuIndexDetailActivity extends MvpActivity<RangqiuIndexDetailPresenter> implements RangqiuIndexDetailView, View.OnClickListener {
    public static final int TYPE_FOOTBALL = 0;
    public static final int TYPE_BASKETBALL = 1;

    public static void forward(Context context, int enterType, int companyId, int id, int type) {
        Intent intent = new Intent(context, RangqiuIndexDetailActivity.class);
        intent.putExtra("enterType", enterType);
        intent.putExtra("id", id);
        intent.putExtra("companyId", companyId);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    private int mEnterType;
    private int mCompanyId;
    private int mId = 0;
    private int mType = 0;
    private RecyclerView rv_index;
    private IndexDetailCompanyAdapter mAdapter;
    private RecyclerView rv_detail;
    private IndexDetailAdapter mDetailAdapter;
    private int mSelectPosition;
//    private ViewPager vp_index;
//    private List<Fragment> mViewList;

    @Override
    protected RangqiuIndexDetailPresenter createPresenter() {
        return new RangqiuIndexDetailPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_rangqiu_index_detail;
    }

    @Override
    protected void initView() {
        mEnterType = getIntent().getIntExtra("enterType", TYPE_FOOTBALL);
        mCompanyId = getIntent().getIntExtra("companyId", 0);
        mId = getIntent().getIntExtra("id", 0);
        mType = getIntent().getIntExtra("type", 0);
        setTitleText(WordUtil.getString(this, R.string.rangqiu_index_detail));
//        mViewList = new ArrayList<>();

        rv_index = findViewById(R.id.rv_index);
        rv_detail = findViewById(R.id.rv_detail);
//        vp_index = findViewById(R.id.vp_index);
    }

    @Override
    protected void initData() {
        mSelectPosition = 0;
        mAdapter = new IndexDetailCompanyAdapter(R.layout.item_index_detail_company, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.getItem(position).setSelected(true);
                mAdapter.getItem(mSelectPosition).setSelected(false);
                mSelectPosition = position;
                mAdapter.notifyDataSetChanged();
                mDetailAdapter.setNewData(mAdapter.getItem(position).getList());
//                vp_index.setCurrentItem(position, false);
            }
        });
        rv_index.setLayoutManager(new LinearLayoutManager(this));
        rv_index.setAdapter(mAdapter);

        mDetailAdapter = new IndexDetailAdapter(R.layout.item_index_detail, new ArrayList<>());
        rv_detail.setLayoutManager(new LinearLayoutManager(this));
        rv_detail.setAdapter(mDetailAdapter);

        mvpPresenter.getList(mEnterType, mId, mType);
//        mViewList.add(RangqiuIndexDetailFragment.newInstance());
//        mViewList.add(RangqiuIndexDetailFragment.newInstance());
//        mViewList.add(RangqiuIndexDetailFragment.newInstance());
//        initViewPager();
    }

//    private void initViewPager() {
//        //初始化viewpager
//        vp_index.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
//        vp_index.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int i) {
//                return mViewList.get(i);
//            }
//
//            @Override
//            public int getCount() {
//                return mViewList.size();
//            }
//        });
//    }

    @Override
    public void getDataSuccess(List<BasketballIndexListBean> list) {
        if (list != null) {
            mAdapter.setNewData(list);
            if (list.size() > 0 && list.get(0).getList() != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (mCompanyId == list.get(i).getCompany_id()) {
                        mSelectPosition = i;
                        list.get(i).setSelected(true);
                        break;
                    }
                }
                mDetailAdapter.setNewData(list.get(0).getList());
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}

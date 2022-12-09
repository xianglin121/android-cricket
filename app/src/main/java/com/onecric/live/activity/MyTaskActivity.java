package com.onecric.live.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.custom.QrCodeInviteDialog;
import com.onecric.live.fragment.CreatorTaskFragment;
import com.onecric.live.fragment.DailyTaskFragment;
import com.onecric.live.fragment.NoviceTaskFragment;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.live.MyTaskPresenter;
import com.onecric.live.util.CommonUtil;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.DownloadUtil;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.live.MyTaskView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class MyTaskActivity extends MvpActivity<MyTaskPresenter> implements MyTaskView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, MyTaskActivity.class);
        context.startActivity(intent);
    }

    private MagicIndicator magicIndicator;
    private List<String> mTitles;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;

    private QrCodeInviteDialog mDialog;

    @Override
    protected MyTaskPresenter createPresenter() {
        return new MyTaskPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_task;
    }

    @Override
    protected void initView() {
        magicIndicator = findViewById(R.id.magicIndicator);
        mViewPager = findViewById(R.id.viewpager);

        findViewById(R.id.fl_creator).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mTitles = new ArrayList<>();
        mViewList = new ArrayList<>();
        mTitles.add(getString(R.string.novice_task));
        mTitles.add(getString(R.string.daily_task));
        mTitles.add(getString(R.string.creator_task));
        mViewList.add(NoviceTaskFragment.newInstance());
        mViewList.add(DailyTaskFragment.newInstance());
        mViewList.add(CreatorTaskFragment.newInstance());
        initViewPager();

        mvpPresenter.getQrCode();
    }

    private void initViewPager() {
        magicIndicator.setBackgroundResource(R.drawable.shape_e05b32_6dp_rec);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);

                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(mTitles.get(index));
                clipPagerTitleView.setTextColor(Color.WHITE);
                clipPagerTitleView.setTextSize(DpUtil.dp2px(14));
                clipPagerTitleView.setClipColor(Color.parseColor("#87390E"));
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(clipPagerTitleView);

                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                float navigatorHeight = DpUtil.dp2px(30);
                float borderWidth = UIUtil.dip2px(context, 1);
                float lineHeight = navigatorHeight - 2 * borderWidth;
                indicator.setLineHeight(lineHeight);
                indicator.setRoundRadius(DpUtil.dp2px(6));
                indicator.setYOffset(borderWidth);
//                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
//                indicator.setLineWidth(DpUtil.dp2px(80));
                indicator.setColors(Color.parseColor("#FFF3CB"), Color.parseColor("#FFF7BA"));
                return indicator;
            }
        });
        commonNavigator.setAdjustMode(true);
        magicIndicator.setNavigator(commonNavigator);
        //初始化viewpager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 2) {
                    if (CommonAppConfig.getInstance().getUserBean() != null) {
                        if (CommonAppConfig.getInstance().getUserBean().getIs_writer() == 1) {
                            findViewById(R.id.fl_creator).setVisibility(View.GONE);
                        }else {
                            findViewById(R.id.fl_creator).setVisibility(View.VISIBLE);
                        }
                    }
                }else {
                    findViewById(R.id.fl_creator).setVisibility(View.GONE);
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
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    public void showDialog() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    @Override
    public void getDataSuccess(String url, String img) {
        if (!TextUtils.isEmpty(img) && !TextUtils.isEmpty(url)) {
            mDialog = new QrCodeInviteDialog(this, R.style.dialog, img, new QrCodeInviteDialog.CallBack() {
                @Override
                public void onSend() {
                    DialogUtil.showSimpleDialog(mActivity, getString(R.string.confirm_save_qr_code), new DialogUtil.SimpleCallback() {
                        @Override
                        public void onConfirmClick(Dialog dialog, String content) {
                            String fileName = "LY_IMG_" + System.currentTimeMillis() + ".png";
                            DownloadUtil.get().download(mActivity, img, DownloadUtil.VIDEO_PATH, fileName, new DownloadUtil.OnDownloadListener() {
                                @Override
                                public void onDownloadSuccess() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.show(getString(R.string.tip_qr_code_save_success));
                                        }
                                    });
                                }

                                @Override
                                public void onDownloading(int progress) {

                                }

                                @Override
                                public void onDownloadFailed() {
                                }
                            });
                        }
                    });
                }

                @Override
                public void onCopy() {
                    ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("text", url);
                    cm.setPrimaryClip(clipData);
                    ToastUtil.show(getString(R.string.copy_success));
                }
            });
        }
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
            case R.id.fl_creator:
                CommonUtil.forwardCustomer(this);
                break;
        }
    }
}

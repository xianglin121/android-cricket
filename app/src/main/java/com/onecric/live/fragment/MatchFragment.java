package com.onecric.live.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.onecric.live.AppManager;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.CommonCode;
import com.onecric.live.R;
import com.onecric.live.activity.MainActivity;
import com.onecric.live.activity.MatchFilterActivity;
import com.onecric.live.activity.SearchMatchActivity;
import com.onecric.live.custom.CustomPagerTitleView;
import com.onecric.live.custom.FootballGoalDialog;
import com.onecric.live.model.FootballMatchBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.MatchSocketBean;
import com.onecric.live.presenter.match.MatchPresenter;
import com.onecric.live.socket.JWebSocketClient;
import com.onecric.live.socket.WebSocketMsgListener;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.match.MatchView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MatchFragment extends MvpFragment<MatchPresenter> implements MatchView, View.OnClickListener {

    private ImageView iv_filter;
    private MagicIndicator magicIndicator;
    private List<String> mTitles;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;

    //socket
    private JWebSocketClient mWebSocketClient;
    //socket重连数
    protected int mConnCount = 10;
    protected int mCurrConnCount = 0;

    //定时器修改时间
    private Handler mHandler;

    private FootballGoalDialog mGoalDialog;

    //新消息声音
    private SoundPool mSoundPool;
    private int mGoalSoundId;//进球声音
    private int mRedCardSoundId;//红牌声音

    //app停留30分钟任务
    private long mStayTime;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match;
    }

    @Override
    protected MatchPresenter createPresenter() {
        return new MatchPresenter(this);
    }

    @Override
    protected void initUI() {
        mGoalDialog = new FootballGoalDialog(getContext(), R.style.dialog2);
        mGoalDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mGoalDialog.removeData();
            }
        });
        iv_filter = rootView.findViewById(R.id.iv_filter);
        magicIndicator = rootView.findViewById(R.id.magicIndicator);
        mViewPager = rootView.findViewById(R.id.viewpager);

        iv_filter.setOnClickListener(this);
        findViewById(R.id.iv_search).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        initSoundPool();//初始化SoundPool
        mTitles = new ArrayList<>();
        mViewList = new ArrayList<>();
        mTitles.add(WordUtil.getString(getActivity(), R.string.match_hot));
        mTitles.add(WordUtil.getString(getActivity(), R.string.match_football));
        mTitles.add(WordUtil.getString(getActivity(), R.string.match_basketball));
        mTitles.add(WordUtil.getString(getActivity(), R.string.match_data));
        mViewList.add(HotMatchFragment.newInstance());
        mViewList.add(FootballMatchFragment.newInstance());
        mViewList.add(BasketballMatchFragment.newInstance());
        mViewList.add(MatchDataFragment.newInstance());
        initViewPager();

        linkWebSocket();

        mHandler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 0) {
//                    ((HotMatchFragment)mViewList.get(0)).updateTime();
//                    ((FootballMatchFragment)mViewList.get(1)).updateTime();
//                    mHandler.sendEmptyMessageDelayed(0, 60000);
                }else if (msg.what == 1) {//进球弹窗消失
                    if (mGoalDialog != null && mGoalDialog.isShowing()) {
                        mGoalDialog.dismiss();
                    }
                }else if (msg.what == 2) {//app停留30分钟任务
                    mStayTime = mStayTime + 1000;
                    if (mStayTime >= 1800000) {
                        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
                            mvpPresenter.doTask();
                        }
                    }else {
                        if (mHandler != null) {
                            mHandler.sendEmptyMessageDelayed(2, 1000);
                        }
                    }
                }
            }
        };
//        mHandler.sendEmptyMessageDelayed(0, 60000);
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
            if (mHandler != null) {
                mHandler.sendEmptyMessageDelayed(2, 1000);
            }
        }
    }

    private void linkWebSocket() {
        if (CommonAppConfig.getInstance().getConfig() != null && !TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getWebsocket())) {
            String url = CommonAppConfig.getInstance().getConfig().getWebsocket();
            URI uri = URI.create(url);
            mWebSocketClient = new JWebSocketClient(uri, JWebSocketClient.TYPE_CONNECT, new WebSocketMsgListener() {
                @Override
                public void onReconnection() {
                    if (mCurrConnCount <= mConnCount) {
                        mCurrConnCount++;
                        linkWebSocket();
                    }
                }

                @Override
                public void onMessage(MatchSocketBean dataBean) {
                    if (!TextUtils.isEmpty(dataBean.getType())) {
                        if ("football_match".equals(dataBean.getType())) {
                            showGoalDialog(dataBean);
                            ((HotMatchFragment)mViewList.get(0)).updateData(dataBean);
                            ((FootballMatchFragment)mViewList.get(1)).updateData(dataBean);
                        }else if ("basketball_match".equals(dataBean.getType())) {
                            ((BasketballMatchFragment)mViewList.get(2)).updateData(dataBean);
                        }
                    }
                }
            });
            mWebSocketClient.conn();
        }
    }

    private void showGoalDialog(MatchSocketBean dataBean) {
        if (mViewList != null) {
//            List<MatchListBean> hotList = ((HotMatchFragment) mViewList.get(0)).getData();
            List<FootballMatchBean> footballList = null;
            if (SpUtil.getInstance().getIntValue(SpUtil.EVENT_NOTIFICATION_RANGE) == 1) {
                footballList = ((FootballMatchFragment) mViewList.get(1)).getCollectData();
            }else {
                footballList = ((FootballMatchFragment) mViewList.get(1)).getData();
            }
            boolean isHasGoal = false;
            boolean isHasRedCard = false;
//            if (hotList != null) {
//                for (int i = 0; i < hotList.size(); i++) {
//                    MatchListBean matchListBean = hotList.get(i);
//                    if (matchListBean.getSourceid() == dataBean.getId()) {
//                        if ((matchListBean.getHome_scores()+matchListBean.getAway_scores()) < (dataBean.getHome_score() + dataBean.getAway_score())) {
//                            isHasGoal = true;
//                            dataBean.setHome_team_name(matchListBean.getHome_team_name());
//                            dataBean.setAway_team_name(matchListBean.getAway_team_name());
//                            break;
//                        }
//                    }
//                }
//            }
            if (footballList != null) {
                for (int i = 0; i < footballList.size(); i++) {
                    FootballMatchBean footballMatchBean = footballList.get(i);
                    if (footballMatchBean.getId() == dataBean.getId()) {
                        if ((Integer.parseInt(footballMatchBean.getHome_score())+Integer.parseInt(footballMatchBean.getAway_score())) < (dataBean.getHome_score() + dataBean.getAway_score())) {
                            isHasGoal = true;
                            dataBean.setHome_team_name(footballMatchBean.getHome_team_name_zh());
                            dataBean.setAway_team_name(footballMatchBean.getAway_team_name_zh());
                        }
                        if ((footballMatchBean.getHome_red_card() + footballMatchBean.getAway_red_card()) < (dataBean.getHome_red_card() + dataBean.getAway_red_card())) {
                            isHasRedCard = true;
                        }
                    }
                }
            }
            if (isHasGoal) {
                boolean isHave = false;
                if (mGoalDialog != null) {
                    List<MatchSocketBean> data = mGoalDialog.getData();
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getId() == dataBean.getId()) {
                            isHave = true;
                        }
                    }
                    if (!isHave) {
                        mGoalDialog.addData(dataBean);
                        if (!mGoalDialog.isShowing()) {
                            mHandler.removeMessages(1);
                            if (getActivity() instanceof  MainActivity) {
                                if (((MainActivity)getActivity()).mPosition == 0) {
                                    mGoalDialog.show();
                                }
                            }
                            mHandler.sendEmptyMessageDelayed(1, 6000);
                        }
                    }
                }
                playGoalMusicAndVibrate();
            }
            if (isHasRedCard) {
                playRedCardMusicAndVibrate();
            }
        }
    }

    private void initSoundPool() {
        if (mSoundPool == null) {
            //初始化SoundPool
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes aab = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build();
                mSoundPool = new SoundPool.Builder()
                        .setMaxStreams(5)
                        .setAudioAttributes(aab)
                        .build();
            } else {
                mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 8);
            }
            //设置资源加载监听
            mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//                    if (mSoundPool != null && mRedCardSoundId > 0) {
//                        mSoundPool.play(mRedCardSoundId, 1, 1, 0, 0, 1);
//                    }
                }
            });
            //加载deep 音频文件
            mGoalSoundId = mSoundPool.load(AppManager.mContext, R.raw.xiaohao, 1);
            mRedCardSoundId = mSoundPool.load(AppManager.mContext, R.raw.horn, 1);
        }
    }

    /**
     * 播放进球音频 震动
     */
    private void playGoalMusicAndVibrate() {
        try {
            if (SpUtil.getInstance().getBooleanValue(SpUtil.GOAL_SOUND)) {
                if (mSoundPool != null && mGoalSoundId > 0) {
                    mSoundPool.play(mGoalSoundId, 1, 1, 0, 0, 1);
                }
            }
            if (SpUtil.getInstance().getBooleanValue(SpUtil.GOAL_VIBRATOR)) {
                Vibrator vibrator = (Vibrator) getContext().getSystemService(getActivity().VIBRATOR_SERVICE);
                if (vibrator != null) {
                    vibrator.vibrate(400);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放红牌音频 震动
     */
    private void playRedCardMusicAndVibrate() {
        try {
            if (SpUtil.getInstance().getBooleanValue(SpUtil.RED_CARD_SOUND)) {
                if (mSoundPool != null && mRedCardSoundId > 0) {
                    mSoundPool.play(mRedCardSoundId, 1, 1, 0, 0, 1);
                }
            }
            if (SpUtil.getInstance().getBooleanValue(SpUtil.RED_CARD_VIBRATOR)) {
                Vibrator vibrator = (Vibrator) getContext().getSystemService(getActivity().VIBRATOR_SERVICE);
                if (vibrator != null) {
                    vibrator.vibrate(400);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_filter:
                if (mViewPager.getCurrentItem() == 1) {
                    MatchFilterActivity.forwardForFootball(getActivity());
                }else if (mViewPager.getCurrentItem() == 2) {
                    MatchFilterActivity.forwardForBasketball(getActivity());
                }
                break;
            case R.id.iv_search:
                SearchMatchActivity.forward(getContext());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CommonCode.RESULT_CODE_MATCH_FILTER) {
            if (requestCode == CommonCode.REQUEST_CODE_FOOTBALL_MATCH_FILTER) {
                ((FootballMatchFragment)mViewList.get(1)).updateId(data.getStringExtra("id"));
            }
            if (requestCode == CommonCode.REQUEST_CODE_BASKETBALL_MATCH_FILTER) {
                ((BasketballMatchFragment)mViewList.get(2)).updateId(data.getStringExtra("id"));
            }
        }
    }

    private void initViewPager() {
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
                titleView.setNormalColor(getResources().getColor(R.color.c_999999));
                titleView.setSelectedColor(getResources().getColor(R.color.black));
                titleView.setText(mTitles.get(index));
                titleView.setTextSize(17);
                titleView.getPaint().setFakeBoldText(true);
                titleView.setOnPagerTitleChangeListener(new CustomPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleView.setTextSize(22);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleView.setTextSize(17);
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
                        if (mViewPager != null) {
                            mViewPager.setCurrentItem(index);
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
                linePagerIndicator.setYOffset(DpUtil.dp2px(7));
                linePagerIndicator.setRoundRadius(DpUtil.dp2px(2));
                linePagerIndicator.setColors(getResources().getColor(R.color.c_E3AC72));
                return linePagerIndicator;
            }
        });
        //初始化viewpager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0 || i == 3) {
                    iv_filter.setVisibility(View.GONE);
                }else {
                    iv_filter.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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
        ViewPagerHelper.bind(magicIndicator, mViewPager);
        mViewPager.setCurrentItem(1);
    }

    @Override
    public void onDestroy() {
        if (mSoundPool != null) {
            mSoundPool.release();
            mSoundPool = null;
        }
        if (mWebSocketClient != null) {
            mWebSocketClient.disconn();
        }
        mWebSocketClient = null;
        if (mHandler != null) {
            mHandler.removeMessages(0);
            mHandler.removeMessages(1);
            mHandler.removeMessages(2);
            mHandler = null;
        }
        super.onDestroy();
    }
}

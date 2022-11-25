package com.longya.live.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.R;
import com.longya.live.custom.CustomPagerTitleView;
import com.longya.live.custom.listener.SoftKeyBoardListener;
import com.longya.live.model.BasketballDetailBean;
import com.longya.live.model.CustomMsgBean;
import com.longya.live.model.FootballDetailBean;
import com.longya.live.model.NormalMsgBean;
import com.longya.live.util.DpUtil;
import com.longya.live.util.ToastUtil;
import com.longya.live.util.WordUtil;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMSendCallback;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;
import com.tencent.qcloud.tuikit.tuichat.component.face.Emoji;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;
import com.tencent.qcloud.tuikit.tuichat.ui.view.input.face.FaceFragment;
import com.tencent.qcloud.tuikit.tuichat.util.ChatMessageInfoUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/25
 */
public class LiveDetailBasketballFragment extends Fragment implements View.OnClickListener {
    public static LiveDetailBasketballFragment newInstance(int id) {
        LiveDetailBasketballFragment fragment = new LiveDetailBasketballFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mId;
    protected View rootView;
    private MagicIndicator magicIndicator;
    private List<String> mTitles;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;

    private FrameLayout fl_input;
    private EditText et_input;
    private InputMethodManager imm;
    private RelativeLayout more_container;
    private FaceFragment mFaceFragment;//表情界面
    private FragmentManager mFragmentManager;
    private String mRoomId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_live_detail_basketball, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
        initData();
    }

    private void initUI() {
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mId = getArguments().getInt("id");

        magicIndicator = rootView.findViewById(R.id.magicIndicator);
        mViewPager = rootView.findViewById(R.id.viewpager);
        et_input = rootView.findViewById(R.id.et_input);
        more_container = rootView.findViewById(R.id.more_container);
        fl_input = rootView.findViewById(R.id.fl_input);

        rootView.findViewById(R.id.iv_emoji).setOnClickListener(this);
        rootView.findViewById(R.id.tv_send).setOnClickListener(this);

        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(fl_input.getWidth(), fl_input.getHeight());
                layoutParams.bottomMargin = height;
                fl_input.setLayoutParams(layoutParams);
            }

            @Override
            public void keyBoardHide(int height) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(fl_input.getWidth(), fl_input.getHeight());
                layoutParams.bottomMargin = 0;
                fl_input.setLayoutParams(layoutParams);
            }
        });
        et_input.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    more_container.setVisibility(View.GONE);
                    et_input.requestFocus();
                    imm.showSoftInput(et_input, 0);
                }
                return false;
            }
        });
    }

    private void initData() {
        mRoomId = "b" + mId;
        mTitles = new ArrayList<>();
        mViewList = new ArrayList<>();
        mTitles.add(WordUtil.getString(getContext(), R.string.match_status));
        mTitles.add(WordUtil.getString(getContext(), R.string.match_live));
        mTitles.add(WordUtil.getString(getContext(), R.string.match_index));
        mTitles.add(WordUtil.getString(getContext(), R.string.match_chat));
        mViewList.add(BasketballMatchStatusFragment.newInstance());
        mViewList.add(BasketballMatchLiveFragment.newInstance(mId));
        mViewList.add(BasketballMatchIndexFragment.newInstance(mId));
        mViewList.add(MatchChatFragment.newInstance(1, mId));
        initViewPager();
    }

    public void setData(BasketballDetailBean detailBean) {
        if (detailBean != null) {
            ((BasketballMatchStatusFragment)mViewList.get(0)).setData(detailBean);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_emoji:
                if (more_container.getVisibility() == View.GONE) {
                    showFaceViewGroup();
                }else {
                    hideFaceViewGroup();
                }
                break;
            case R.id.tv_send:
                if (TextUtils.isEmpty(et_input.getText().toString())) {
                    ToastUtil.show(getString(R.string.tip_input_chat_msg_empty));
                    return;
                }
                sendMessage(et_input.getText().toString());
                et_input.setText("");
                break;
        }
    }

    public void hideSoftInput() {
        imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
        et_input.clearFocus();
        more_container.setVisibility(View.GONE);
    }

    private void hideFaceViewGroup() {
        more_container.setVisibility(View.GONE);
        et_input.requestFocus();
        imm.showSoftInput(et_input, 0);
    }

    private void showFaceViewGroup() {
        if (mFragmentManager == null) {
            mFragmentManager = getChildFragmentManager();
        }
        if (mFaceFragment == null) {
            mFaceFragment = new FaceFragment();
        }
        hideSoftInput();
        more_container.setVisibility(View.VISIBLE);
        et_input.requestFocus();
        mFaceFragment.setListener(new FaceFragment.OnEmojiClickListener() {
            @Override
            public void onEmojiDelete() {
                int index = et_input.getSelectionStart();
                Editable editable = et_input.getText();
                boolean isFace = false;
                if (index <= 0) {
                    return;
                }
                if (editable.charAt(index - 1) == ']') {
                    for (int i = index - 2; i >= 0; i--) {
                        if (editable.charAt(i) == '[') {
                            String faceChar = editable.subSequence(i, index).toString();
                            if (FaceManager.isFaceChar(faceChar)) {
                                editable.delete(i, index);
                                isFace = true;
                            }
                            break;
                        }
                    }
                }
                if (!isFace) {
                    editable.delete(index - 1, index);
                }
            }

            @Override
            public void onEmojiClick(Emoji emoji) {
                int index = et_input.getSelectionStart();
                Editable editable = et_input.getText();
                editable.insert(index, emoji.getFilter());
                FaceManager.handlerEmojiText(et_input, editable.toString(), true);
            }

            @Override
            public void onCustomFaceClick(int groupIndex, Emoji emoji) {
//                mMessageHandler.sendMessage(ChatMessageInfoUtil.buildCustomFaceMessage(groupIndex, emoji.getFilter()));
            }
        });
        mFragmentManager.beginTransaction().replace(R.id.more_container, mFaceFragment).commitAllowingStateLoss();
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK){
                    if (more_container.getVisibility() == View.VISIBLE) {
                        hideFaceViewGroup();
                        return false;
                    }else {
                        return true;
                    }
                }
                return false;
            }
        });
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
                titleView.setNormalColor(getResources().getColor(R.color.c_666666));
                titleView.setSelectedColor(getResources().getColor(R.color.c_333333));
                titleView.setText(mTitles.get(index));
                titleView.setTextSize(16);
//                titleView.getPaint().setFakeBoldText(true);
                titleView.setOnPagerTitleChangeListener(new CustomPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
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
                linePagerIndicator.setYOffset(DpUtil.dp2px(1));
                linePagerIndicator.setRoundRadius(DpUtil.dp2px(2));
                linePagerIndicator.setColors(getResources().getColor(R.color.c_E3AC72));
                return linePagerIndicator;
            }
        });
        commonNavigator.setAdjustMode(true);
        //初始化viewpager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 3) {
                    fl_input.setVisibility(View.VISIBLE);
                }else {
                    fl_input.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setOffscreenPageLimit(4);
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
    }

    //发送普通消息
    public void sendMessage(String content) {
        if (!TextUtils.isEmpty(mRoomId)) {
            NormalMsgBean msgBean = new NormalMsgBean();
            msgBean.setText(content);
            CustomMsgBean customMsgBean = new CustomMsgBean();
            customMsgBean.setType(MessageInfo.MSG_TYPE_BG_DANMU);
            customMsgBean.setNormal(msgBean);
            MessageInfo messageInfo = ChatMessageInfoUtil.buildCustomMessage(JSONObject.toJSONString(customMsgBean), "", null);
            messageInfo.setNickName(CommonAppConfig.getInstance().getUserBean().getUser_nickname());
            messageInfo.setStatus(MessageInfo.MSG_STATUS_SEND_SUCCESS);
            messageInfo.setSelf(true);
            messageInfo.setRead(true);
            V2TIMManager.getMessageManager().sendMessage(messageInfo.getTimMessage(), null, mRoomId, V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null,
                    new V2TIMSendCallback<V2TIMMessage>() {
                        @Override
                        public void onProgress(int i) {

                        }

                        @Override
                        public void onSuccess(V2TIMMessage v2TIMMessage) {
                            if (mViewList != null && mViewList.size() > 0) {
                                if (mViewList.get(3) instanceof  MatchChatFragment) {
                                    ((MatchChatFragment)mViewList.get(3)).updateMsg(messageInfo);
                                }
                            }
                        }

                        @Override
                        public void onError(int i, String s) {
                        }
                    });
        }
    }
}

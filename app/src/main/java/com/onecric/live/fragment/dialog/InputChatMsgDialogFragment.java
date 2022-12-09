package com.onecric.live.fragment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.OpenNobleActivity;
import com.onecric.live.adapter.ColorDanmuAdapter;
import com.onecric.live.model.BlockFunctionBean;
import com.onecric.live.model.ColorMsgBean;
import com.onecric.live.model.GiftBean;
import com.onecric.live.model.LiveUserBean;
import com.onecric.live.model.NobelBean;
import com.onecric.live.model.NobelListBean;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.ToastUtil;
import com.tencent.qcloud.tuikit.tuichat.component.face.Emoji;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;
import com.tencent.qcloud.tuikit.tuichat.ui.view.input.face.FaceFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/6/12
 */
public class InputChatMsgDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final int STATE_NONE_INPUT = 0;
    public static final int STATE_SOFT_INPUT = 1;
    public static final int STATE_FACE_INPUT = 2;

    private Context mContext;
    private FragmentManager mFragmentManager;
    private int mCurrentState;
    private InputMethodManager imm;

    private ImageView iv_normal_danmu, iv_color_danmu, iv_broadcast_danmu;
    private TextView tv_bg_danmu;
    private FrameLayout fl_bg_danmu;
    private ImageView iv_bg_danmu;
    private TextView tv_bg_danmu_text;
    private RecyclerView rv_color;
    private EditText et_input;
    private RelativeLayout more_groups;
    private FaceFragment mFaceFragment;//表情界面

    private int mDanmuType = 0;//0:普通弹幕  1:彩色弹幕  2:广播弹幕  3:炫彩弹幕
    private LiveUserBean mLiveUserBean;
    private NobelBean mNobelBean;
    private ColorMsgBean mSelectColor;//选中的彩色弹幕
    private List<GiftBean> mDanmuList;//炫彩气泡
    private GiftBean mSelectBgDanmu;//选中的炫彩弹幕

    private ImageView iv_block;
//    private EasyPopup popup;
    private PopupWindow popup;
    private ImageView iv_envelope;
    private TextView tv_envelope;
    private ImageView iv_noble;
    private TextView tv_noble;
    private ImageView iv_gift;
    private TextView tv_gift;
    private ImageView iv_enter;
    private TextView tv_enter;

    private PopupWindow danmuPopup;
    private TextView tv_five;
    private TextView tv_four;
    private TextView tv_three;
    private TextView tv_two;
    private TextView tv_one;
    private TextView tv_cancel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mContext = getContext();
        Dialog dialog  = new Dialog(getContext(), R.style.dialog2);
        dialog.setContentView(R.layout.dialog_chat_text_input);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        setLayout(dialog);
        initView(dialog);
        return dialog;
    }

    private void setLayout(Dialog dialog) {
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void initView(Dialog dialog) {
        mCurrentState = getArguments().getInt("state");
        if (getArguments().getSerializable("user") != null) {
            mLiveUserBean = (LiveUserBean) getArguments().getSerializable("user");
        }
        mNobelBean = (NobelBean) getArguments().getSerializable("bean");
        mDanmuList = (List<GiftBean>) getArguments().getSerializable("danmu");
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        iv_normal_danmu = dialog.findViewById(R.id.iv_normal_danmu);
        iv_color_danmu = dialog.findViewById(R.id.iv_color_danmu);
        iv_broadcast_danmu = dialog.findViewById(R.id.iv_broadcast_danmu);
        tv_bg_danmu = dialog.findViewById(R.id.tv_bg_danmu);
        fl_bg_danmu = dialog.findViewById(R.id.fl_bg_danmu);
        iv_bg_danmu = dialog.findViewById(R.id.iv_bg_danmu);
        tv_bg_danmu_text = dialog.findViewById(R.id.tv_bg_danmu_text);
        et_input = dialog.findViewById(R.id.et_input);
        more_groups = dialog.findViewById(R.id.more_groups);
        rv_color = dialog.findViewById(R.id.rv_color);
        iv_block = dialog.findViewById(R.id.iv_block);

        iv_normal_danmu.setOnClickListener(this);
        iv_color_danmu.setOnClickListener(this);
        iv_broadcast_danmu.setOnClickListener(this);
        iv_block.setOnClickListener(this);
        dialog.findViewById(R.id.fl_danmu_root).setOnClickListener(this);
        dialog.findViewById(R.id.iv_emoji).setOnClickListener(this);
        dialog.findViewById(R.id.iv_noble).setOnClickListener(this);
        dialog.findViewById(R.id.tv_send).setOnClickListener(this);
        dialog.findViewById(R.id.empty_view).setOnClickListener(this);
        et_input.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mCurrentState = STATE_SOFT_INPUT;
                    more_groups.setVisibility(View.GONE);
                    et_input.requestFocus();
                    imm.showSoftInput(et_input, 0);
                }
                return false;
            }
        });
        et_input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                return false;
            }
        });
        et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return false;
            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0) {
                    dismiss();
                }
                return false;
            }
        });

        //初始化彩色弹幕列表
        List<ColorMsgBean> list = new ArrayList<>();
        if (mNobelBean.getGoard() != null) {
            for (int i = 0; i < mNobelBean.getGoard().size(); i++) {
                NobelListBean nobelListBean = mNobelBean.getGoard().get(i);
                ColorMsgBean colorMsgBean = new ColorMsgBean();
                colorMsgBean.setName(nobelListBean.getName());
                colorMsgBean.setColor(nobelListBean.getColor());
                colorMsgBean.setOrder(nobelListBean.getOrder());
                colorMsgBean.setSelect(false);
                list.add(colorMsgBean);
            }
        }
        if (list.size() > 0) {
            Collections.reverse(list);
        }
        ColorMsgBean freeColor = new ColorMsgBean();
        freeColor.setName(getString(R.string.free));
        freeColor.setColor("#1A8FFA");
        freeColor.setOrder(0);
        freeColor.setSelect(true);
        list.add(0, freeColor);
        mSelectColor = list.get(0);//彩色弹幕默认第一个
        ColorDanmuAdapter danmuAdapter = new ColorDanmuAdapter(R.layout.item_color_danmu, list);
        danmuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (CommonAppConfig.getInstance().getUserBean() != null && CommonAppConfig.getInstance().getUserBean().getGuard() != null) {
                    if (danmuAdapter.getData().get(position).getOrder() > CommonAppConfig.getInstance().getUserBean().getGuard().getList_order()) {
                        return;
                    }
                }
                for (int i = 0; i < danmuAdapter.getData().size(); i++) {
                    if (i == position) {
                        danmuAdapter.getData().get(i).setSelect(true);
                        mSelectColor = danmuAdapter.getData().get(i);
                        et_input.setTextColor(Color.parseColor(danmuAdapter.getData().get(i).getColor()));
                    }else {
                        danmuAdapter.getData().get(i).setSelect(false);
                    }
                }
                danmuAdapter.notifyDataSetChanged();
            }
        });
        rv_color.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_color.setAdapter(danmuAdapter);

        if (mCurrentState == STATE_SOFT_INPUT) {
            et_input.requestFocus();
        }else if (mCurrentState == STATE_FACE_INPUT) {
            showFaceViewGroup();
        }
        iv_normal_danmu.setSelected(true);//默认普通弹幕

        //初始化屏蔽特效弹窗
        initPopup();
        popup.getContentView().findViewById(R.id.ll_envelope).setOnClickListener(this);
        popup.getContentView().findViewById(R.id.ll_noble).setOnClickListener(this);
        popup.getContentView().findViewById(R.id.ll_gift).setOnClickListener(this);
        popup.getContentView().findViewById(R.id.ll_enter).setOnClickListener(this);
        //初始化炫彩气泡弹窗
        initDanmuPopup();
    }

    private void initPopup() {
//        popup = EasyPopup.create().setContentView(getContext(), R.layout.view_live_block_function, DpUtil.dp2px(167), DpUtil.dp2px(178))
//                .setFocusAndOutsideEnable(true).apply();
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_live_block_function, null);
        popup = new PopupWindow(v, DpUtil.dp2px(187), DpUtil.dp2px(38), true);
        popup.setOutsideTouchable(true);
        iv_envelope = popup.getContentView().findViewById(R.id.iv_envelope);
        tv_envelope = popup.getContentView().findViewById(R.id.tv_envelope);
        iv_noble = popup.getContentView().findViewById(R.id.iv_noble);
        tv_noble = popup.getContentView().findViewById(R.id.tv_noble);
        iv_gift = popup.getContentView().findViewById(R.id.iv_gift);
        tv_gift = popup.getContentView().findViewById(R.id.tv_gift);
        iv_enter = popup.getContentView().findViewById(R.id.iv_enter);
        tv_enter = popup.getContentView().findViewById(R.id.tv_enter);

        BlockFunctionBean blockFunctionInfo = CommonAppConfig.getInstance().getBlockFunctionInfo();
        iv_envelope.setSelected(blockFunctionInfo.isBlockEnvelope());
        iv_noble.setSelected(blockFunctionInfo.isBlockNoble());
        iv_gift.setSelected(blockFunctionInfo.isBlockGift());
        iv_enter.setSelected(blockFunctionInfo.isBlockEnter());
        if (blockFunctionInfo.isBlockEnvelope()) {
            tv_envelope.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
        }else {
            tv_envelope.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
        }
        if (blockFunctionInfo.isBlockNoble()) {
            tv_noble.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
        }else {
            tv_noble.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
        }
        if (blockFunctionInfo.isBlockGift()) {
            tv_gift.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
        }else {
            tv_gift.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
        }
        if (blockFunctionInfo.isBlockEnter()) {
            tv_enter.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
        }else {
            tv_enter.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
        }
    }

    private void initDanmuPopup() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_background_danmu, null);
        danmuPopup = new PopupWindow(v, DpUtil.dp2px(123), DpUtil.dp2px(281), true);
        danmuPopup.setOutsideTouchable(true);
        tv_five = danmuPopup.getContentView().findViewById(R.id.tv_five);
        tv_four = danmuPopup.getContentView().findViewById(R.id.tv_four);
        tv_three = danmuPopup.getContentView().findViewById(R.id.tv_three);
        tv_two = danmuPopup.getContentView().findViewById(R.id.tv_two);
        tv_one = danmuPopup.getContentView().findViewById(R.id.tv_one);
        tv_cancel = danmuPopup.getContentView().findViewById(R.id.tv_cancel);
        if (mDanmuList != null) {
            for (int i = 0; i < mDanmuList.size(); i++) {
                if (mDanmuList.get(i).getList_order() == 1) {
                    tv_one.setText("Level 1 Barrage x" + mDanmuList.get(i).getNum());
                }
                if (mDanmuList.get(i).getList_order() == 2) {
                    tv_two.setText("Level 2 Barrage x" + mDanmuList.get(i).getNum());
                }
                if (mDanmuList.get(i).getList_order() == 3) {
                    tv_three.setText("Level 3 Barrage x" + mDanmuList.get(i).getNum());
                }
                if (mDanmuList.get(i).getList_order() == 4) {
                    tv_four.setText("Level 4 Barrage x" + mDanmuList.get(i).getNum());
                }
                if (mDanmuList.get(i).getList_order() == 5) {
                    tv_five.setText("Level 5 Barrage x" + mDanmuList.get(i).getNum());
                }
            }
        }
        tv_five.setOnClickListener(this);
        tv_four.setOnClickListener(this);
        tv_three.setOnClickListener(this);
        tv_two.setOnClickListener(this);
        tv_one.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        BlockFunctionBean blockFunctionInfo = CommonAppConfig.getInstance().getBlockFunctionInfo();
        switch (v.getId()) {
            case R.id.empty_view:
                dismiss();
                break;
            case R.id.iv_normal_danmu:
                if (iv_normal_danmu.isSelected()) {
                    return;
                }
                mDanmuType = 0;
                iv_normal_danmu.setSelected(true);
                iv_color_danmu.setSelected(false);
                iv_broadcast_danmu.setSelected(false);
                rv_color.setVisibility(View.GONE);
                if (mSelectColor != null) {
                    et_input.setTextColor(Color.BLACK);
                }
                break;
            case R.id.iv_color_danmu:
                if (iv_color_danmu.isSelected()) {
                    return;
                }
                mDanmuType = 1;
                iv_normal_danmu.setSelected(false);
                iv_color_danmu.setSelected(true);
                iv_broadcast_danmu.setSelected(false);
                rv_color.setVisibility(View.VISIBLE);
                if (mSelectColor != null) {
                    et_input.setTextColor(Color.parseColor(mSelectColor.getColor()));
                }
                break;
            case R.id.iv_broadcast_danmu:
                if (iv_broadcast_danmu.isSelected()) {
                    return;
                }
                mDanmuType = 2;
                iv_normal_danmu.setSelected(false);
                iv_color_danmu.setSelected(false);
                iv_broadcast_danmu.setSelected(true);
                rv_color.setVisibility(View.GONE);
                if (mSelectColor != null) {
                    et_input.setTextColor(Color.BLACK);
                }
                break;
            case R.id.iv_emoji:
                if (mCurrentState == STATE_FACE_INPUT) {
                    mCurrentState = STATE_NONE_INPUT;
                    more_groups.setVisibility(View.GONE);
                }else {
                    mCurrentState = STATE_FACE_INPUT;
                    showFaceViewGroup();
                }
                break;
            case R.id.tv_send:
                if (TextUtils.isEmpty(et_input.getText().toString())) {
                    ToastUtil.show(getActivity().getString(R.string.tip_input_chat_msg_empty));
                    return;
                }
                if (mDanmuType == 0) {//普通弹幕
                    if (getActivity() instanceof LiveDetailActivity) {
                        ((LiveDetailActivity)getActivity()).sendMessage(et_input.getText().toString());
                        et_input.setText("");
                    }
                }else if (mDanmuType == 1) {//彩色弹幕
                    if (getActivity() instanceof LiveDetailActivity && mSelectColor != null) {
                        mSelectColor.setText(et_input.getText().toString());
                        ((LiveDetailActivity)getActivity()).sendColorMessage(mSelectColor);
                        et_input.setText("");
                    }
                }else if (mDanmuType == 2) {//喇叭消息
                    if (getActivity() instanceof LiveDetailActivity) {
                        ((LiveDetailActivity)getActivity()).sendBroadcast(et_input.getText().toString());
                        et_input.setText("");
                    }
                }else if (mDanmuType == 3) {//炫彩弹幕
                    if (mSelectBgDanmu == null) {
                        return;
                    }
                    if (getActivity() instanceof LiveDetailActivity && mSelectBgDanmu != null) {
                        ((LiveDetailActivity)getActivity()).sendBgMessage(mSelectBgDanmu.getGift_id(), mLiveUserBean.getUid(), mSelectBgDanmu.getList_order(), et_input.getText().toString());
                        et_input.setText("");
                        if (mDanmuList != null) {
                            GiftBean temp= null;
                            for (int i = 0; i < mDanmuList.size(); i++) {
                                if (mDanmuList.get(i).getList_order() == mSelectBgDanmu.getList_order()) {
                                    temp = mDanmuList.get(i);
                                }
                            }
                            if (temp != null && temp.getNum() > 0){
                                int num = temp.getNum();
                                num--;
                                temp.setNum(num);
                                if (mSelectBgDanmu.getList_order() == 1) {
                                    tv_bg_danmu_text.setText("Level 1 Barrage x" + num);
                                }else if (mSelectBgDanmu.getList_order() == 2) {
                                    tv_bg_danmu_text.setText("Level 2 Barrage x" + num);
                                }else if (mSelectBgDanmu.getList_order() == 3) {
                                    tv_bg_danmu_text.setText("Level 3 Barrage x" + num);
                                }else if (mSelectBgDanmu.getList_order() == 4) {
                                    tv_bg_danmu_text.setText("Level 4 Barrage x" + num);
                                }else if (mSelectBgDanmu.getList_order() == 5) {
                                    tv_bg_danmu_text.setText("Level 5 Barrage x" + num);
                                }
                            }
                        }
                    }
                }
                hideSoftInput();
                break;
            case R.id.iv_noble:
                OpenNobleActivity.forward(getContext(), mLiveUserBean, mNobelBean);
                break;
            case R.id.iv_block:
//                popup.showAtAnchorView(iv_block, YGravity.ALIGN_TOP, XGravity.CENTER, 0, DpUtil.dp2px(350));
                popup.showAtLocation(iv_block, Gravity.TOP, -DpUtil.dp2px(118), DpUtil.dp2px(220));
                break;
            case R.id.fl_danmu_root:
//                popup.showAtAnchorView(iv_block, YGravity.ALIGN_TOP, XGravity.CENTER, 0, DpUtil.dp2px(350));
                danmuPopup.showAtLocation(tv_bg_danmu, Gravity.TOP, DpUtil.dp2px(150), 0);
                break;
            case R.id.ll_envelope:
                blockFunctionInfo.setBlockEnvelope(!blockFunctionInfo.isBlockEnvelope());
                CommonAppConfig.getInstance().saveBlockFunctionInfo(blockFunctionInfo);
                iv_envelope.setSelected(blockFunctionInfo.isBlockEnvelope());
                if (blockFunctionInfo.isBlockEnvelope()) {
                    tv_envelope.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
                }else {
                    tv_envelope.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
                }
                break;
            case R.id.ll_noble:
                blockFunctionInfo.setBlockNoble(!blockFunctionInfo.isBlockNoble());
                CommonAppConfig.getInstance().saveBlockFunctionInfo(blockFunctionInfo);
                iv_noble.setSelected(blockFunctionInfo.isBlockNoble());
                if (blockFunctionInfo.isBlockNoble()) {
                    tv_noble.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
                }else {
                    tv_noble.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
                }
                break;
            case R.id.ll_gift:
                blockFunctionInfo.setBlockGift(!blockFunctionInfo.isBlockGift());
                CommonAppConfig.getInstance().saveBlockFunctionInfo(blockFunctionInfo);
                iv_gift.setSelected(blockFunctionInfo.isBlockGift());
                if (blockFunctionInfo.isBlockGift()) {
                    tv_gift.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
                }else {
                    tv_gift.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
                }
                break;
            case R.id.ll_enter:
                blockFunctionInfo.setBlockEnter(!blockFunctionInfo.isBlockEnter());
                CommonAppConfig.getInstance().saveBlockFunctionInfo(blockFunctionInfo);
                iv_enter.setSelected(blockFunctionInfo.isBlockEnter());
                if (blockFunctionInfo.isBlockEnter()) {
                    tv_enter.setTextColor(getActivity().getResources().getColor(R.color.c_E3AC72));
                }else {
                    tv_enter.setTextColor(getActivity().getResources().getColor(R.color.c_999999));
                }
                break;
            case R.id.tv_five:
                if (mDanmuList != null) {
                    GiftBean temp= null;
                    for (int i = 0; i < mDanmuList.size(); i++) {
                        if (mDanmuList.get(i).getList_order() == 5) {
                            temp = mDanmuList.get(i);
                        }
                    }
                    if (temp != null && temp.getNum() > 0){
                        danmuPopup.dismiss();
                        mDanmuType = 3;
                        tv_bg_danmu.setVisibility(View.GONE);
                        fl_bg_danmu.setVisibility(View.VISIBLE);
                        iv_bg_danmu.setBackgroundResource(R.mipmap.img_background_danmu_five);
                        tv_bg_danmu_text.setText("Level 5 Barrage x" + temp.getNum());
                        mSelectBgDanmu = temp;
                    }
                }
                break;
            case R.id.tv_four:
                if (mDanmuList != null) {
                    GiftBean temp= null;
                    for (int i = 0; i < mDanmuList.size(); i++) {
                        if (mDanmuList.get(i).getList_order() == 4) {
                            temp = mDanmuList.get(i);
                        }
                    }
                    if (temp != null && temp.getNum() > 0){
                        danmuPopup.dismiss();
                        mDanmuType = 3;
                        tv_bg_danmu.setVisibility(View.GONE);
                        fl_bg_danmu.setVisibility(View.VISIBLE);
                        iv_bg_danmu.setBackgroundResource(R.mipmap.img_background_danmu_four);
                        tv_bg_danmu_text.setText("Level 4 Barrage x" + temp.getNum());
                        mSelectBgDanmu = temp;
                    }
                }
                break;
            case R.id.tv_three:
                if (mDanmuList != null) {
                    GiftBean temp= null;
                    for (int i = 0; i < mDanmuList.size(); i++) {
                        if (mDanmuList.get(i).getList_order() == 3) {
                            temp = mDanmuList.get(i);
                        }
                    }
                    if (temp != null && temp.getNum() > 0){
                        danmuPopup.dismiss();
                        mDanmuType = 3;
                        tv_bg_danmu.setVisibility(View.GONE);
                        fl_bg_danmu.setVisibility(View.VISIBLE);
                        iv_bg_danmu.setBackgroundResource(R.mipmap.img_background_danmu_three);
                        tv_bg_danmu_text.setText("Level 3 Barrage x" + temp.getNum());
                        mSelectBgDanmu = temp;
                    }
                }
                break;
            case R.id.tv_two:
                if (mDanmuList != null) {
                    GiftBean temp= null;
                    for (int i = 0; i < mDanmuList.size(); i++) {
                        if (mDanmuList.get(i).getList_order() == 2) {
                            temp = mDanmuList.get(i);
                        }
                    }
                    if (temp != null && temp.getNum() > 0){
                        danmuPopup.dismiss();
                        mDanmuType = 3;
                        tv_bg_danmu.setVisibility(View.GONE);
                        fl_bg_danmu.setVisibility(View.VISIBLE);
                        iv_bg_danmu.setBackgroundResource(R.mipmap.img_background_danmu_two);
                        tv_bg_danmu_text.setText("Level 2 Barrage x" + temp.getNum());
                        mSelectBgDanmu = temp;
                    }
                }
                break;
            case R.id.tv_one:
                if (mDanmuList != null) {
                    GiftBean temp= null;
                    for (int i = 0; i < mDanmuList.size(); i++) {
                        if (mDanmuList.get(i).getList_order() == 1) {
                            temp = mDanmuList.get(i);
                        }
                    }
                    if (temp != null && temp.getNum() > 0){
                        danmuPopup.dismiss();
                        mDanmuType = 3;
                        tv_bg_danmu.setVisibility(View.GONE);
                        fl_bg_danmu.setVisibility(View.VISIBLE);
                        iv_bg_danmu.setBackgroundResource(R.mipmap.img_background_danmu_one);
                        tv_bg_danmu_text.setText("Level 1 Barrage x" + temp.getNum());
                        mSelectBgDanmu = temp;
                    }
                }
                break;
            case R.id.tv_cancel:
                danmuPopup.dismiss();
                mDanmuType = 0;
                tv_bg_danmu.setVisibility(View.VISIBLE);
                fl_bg_danmu.setVisibility(View.GONE);
                mSelectBgDanmu = null;
                break;

        }
    }

    public void hideSoftInput() {
        imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
        et_input.clearFocus();
        more_groups.setVisibility(View.GONE);
    }

    private void showFaceViewGroup() {
        if (mFragmentManager == null) {
            mFragmentManager = getChildFragmentManager();
        }
        if (mFaceFragment == null) {
            mFaceFragment = new FaceFragment();
        }
        more_groups.setVisibility(View.VISIBLE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//阻止键盘弹出
//            et_input.setShowSoftInputOnFocus(false);
//        }
        et_input.requestFocus();
        more_groups.post(new Runnable() {
            @Override
            public void run() {
                imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
            }
        });
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
        mFragmentManager.beginTransaction().replace(R.id.more_groups, mFaceFragment).commitAllowingStateLoss();
//        if (mChatInputHandler != null) {
//            et_input.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mChatInputHandler.onInputAreaClick();
//                }
//            }, 100);
    }
}
